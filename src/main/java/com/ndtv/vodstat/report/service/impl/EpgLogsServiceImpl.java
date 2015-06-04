package com.ndtv.vodstat.report.service.impl;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.stereotype.Service;

import com.ndtv.vodstat.common.util.ConfigUtil;
import com.ndtv.vodstat.common.util.DateFunctions;
import com.ndtv.vodstat.common.util.FtpClient;
import com.ndtv.vodstat.report.dao.SysCodeMapper;
import com.ndtv.vodstat.report.dao.TimerJobLogMapper;
import com.ndtv.vodstat.report.dao.VodLogsMapper;
import com.ndtv.vodstat.report.model.EpgLog;
import com.ndtv.vodstat.report.service.EpgLogsService;
import com.ndtv.vodstat.sys.entity.SysCode;
import com.ndtv.vodstat.sys.entity.TimerJobLog;


@Service
public class EpgLogsServiceImpl implements EpgLogsService{
	
	private Log log = LogFactory.getLog(EpgLogsServiceImpl.class);
	
	@Resource
	private VodLogsMapper logsMapper;
	@Resource
	private SysCodeMapper syscodeMapper;
	@Resource
	private TimerJobLogMapper timerJobLogMapper;
	
	private Long epglog_lasttime_syscodeId;
	private String[] epglogFtp;
	String epglogPath;
	Set<String> tv1EpgIPs;
	Set<String> tv2EpgIPs;
	
	@PostConstruct
	public void init(){
		epglogPath = ConfigUtil.get("epglog_path");
		epglogFtp = ConfigUtil.get("epglog_ftp").split("[,]");
		epglog_lasttime_syscodeId = ConfigUtil.getLong("syscode_sysConfig_epglogFile_TIME");
		tv1EpgIPs = ConfigUtil.getSet("[,]", "epglog1_ftp_ip");
		tv2EpgIPs = ConfigUtil.getSet("[,]", "epglog2_ftp_ip");
	}
	
	private static String matchStringEpg1(String str) {
		try {
			StringBuffer sb = new StringBuffer();
			Pattern p = Pattern
					.compile(
							"(\\d{1,4}[-|\\/|年|\\.]\\d{1,2}[-|\\/|月|\\.]\\d{1,2}([日|号])?(\\s)*(\\d{1,2}([点|时])?((:)?\\d{1,2}(分)?((:)?\\d{1,2}(秒)?)?)?)?(\\s)*(PM|AM)?)",
							Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
			Matcher dateM = p.matcher(str);
			if (dateM.find() && dateM.groupCount() >= 1) {
				sb.append(dateM.group(1) + ",");
			}
			Matcher shortSimM = Pattern.compile("/tvportal/index[.]jsp.*[Uu]ser=(\\d{8})(\\D)").matcher(str);
			if (shortSimM.find() && shortSimM.groupCount() >= 1) {
				sb.append(shortSimM.group(1));
			} else {
				return "";
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	private static String matchStringEpg2(String str) {
		try {
			StringBuffer sb = new StringBuffer();
			Pattern p = Pattern
					.compile(
							"(\\d{1,4}[-|\\/|年|\\.]\\d{1,2}[-|\\/|月|\\.]\\d{1,2}([日|号])?(\\s)*(\\d{1,2}([点|时])?((:)?\\d{1,2}(分)?((:)?\\d{1,2}(秒)?)?)?)?(\\s)*(PM|AM)?)",
							Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
			Matcher dateM = p.matcher(str);
			if (dateM.find() && dateM.groupCount() >= 1) {
				sb.append(dateM.group(1) + ",");
			}
			//Matcher stbidM = Pattern.compile("stbid=?(.*?)(\\s+)").matcher(str);
			Matcher stbidM = Pattern.compile("/tvportal/index[.]aspx.*stbid=?(\\S+)(\\s+)").matcher(str);
			if (stbidM.find() && stbidM.groupCount() >= 1) {
				sb.append(stbidM.group(1));
			} else {
				return "";
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	synchronized public void genEpgLog() {
		log.info("genEpgLog begin...");
		
		SimpleDateFormat markdf = new SimpleDateFormat("yyyy-MM-dd");
		Date lastFileTime = null;
		SysCode sc = syscodeMapper.get(epglog_lasttime_syscodeId);
		if(sc != null){
			try {
				lastFileTime = markdf.parse(sc.getCodeContent());
			} catch (ParseException e) {
				e.printStackTrace();
				return;
			}
		}
		if(lastFileTime == null){
			lastFileTime = DateFunctions.strToDate("20131201");
		}
		
		log.info("localPath="+epglogPath);
		log.info("lastTimeSysCode="+markdf.format(lastFileTime));
		log.info("epglogFtp.length="+epglogFtp.length);
		
		FtpClient[] fcc = new FtpClient[epglogFtp.length];
		for(int i=0;i<epglogFtp.length;i++) {
			String ftpURL = epglogFtp[i];
			String u_p = ftpURL.split("[@]")[0];
			String usr = u_p.split("[:]")[0];
			String pwd = u_p.split("[:]")[1];
			String url = ftpURL.split("[@]")[1];
			String ip = url.split("[:]")[0];
			String port = url.split("[:]")[1];
			
			fcc[i] = new FtpClient(ip, Integer.parseInt(port), usr, pwd);
		}
		Date today = new Date();
		
		Date d00 = new Date();
		String str;
		
		lastFileTime = DateFunctions.addCalendarFeild(lastFileTime, Calendar.DATE, 1);
		
		if (DateFunctions.dateCompare(lastFileTime, today) < 0){
			
			//读取修改日期是lastFileTime的文件
			int[] a = readRemoteFilesByFTP(fcc, lastFileTime, epglogPath);
			str = "修改日期为:"+markdf.format(lastFileTime)+"的文件,共读取文件:"+a[0]+"个,共读取"+a[1]+"行,写入数据库"+a[2]+"行";
			log.info(str);
			
			//刚刚插入的数据是昨天的, 更新的也是昨天的数据
			Date logDate = DateFunctions.dateTruncate(DateFunctions.addCalendarFeild(lastFileTime, Calendar.DATE, -1));
			String logMonth = DateFunctions.dateToStr(logDate, "yyyyMM");
			
			log.info("begin update table: EpgLog_"+logMonth+":"+DateFunctions.dateToStr(logDate, "yyyyMMdd"));
			logsMapper.updateEpglogByResno(logMonth, logDate);
			logsMapper.updateEpglogByShortSim(logMonth, logDate);
			
			log.info("begin insert table:EpgLogA_"+logMonth+":"+DateFunctions.dateToStr(logDate, "yyyyMMdd"));
			logsMapper.batchInsertEpgLogA(logMonth, logDate);

			TimerJobLog logInfo = new TimerJobLog(epglog_lasttime_syscodeId, "EPG日志读取存档", new Date(),
					(new Date().getTime()-d00.getTime()), str);
			timerJobLogMapper.insert(logInfo);
		}
		for(FtpClient fc : fcc) {
			fc.close();
		}
		
		//更新标记
		SysCode scNew = new SysCode();
		scNew.setId(epglog_lasttime_syscodeId);
		scNew.setUpdateTime(new Date());
		scNew.setUpdateUserId(1l);
		scNew.setCodeContent(markdf.format(lastFileTime));
		syscodeMapper.update(scNew);
		
		log.info("genEpgLog over!");
	}
	
	private int[] readRemoteFilesByFTP(FtpClient[] fcs, Date logDate, String localPath) {
		//写入文件后缀,写入数据
		Map<String,List<String>> dataMap = new HashMap();
		List<String> tmpDataList;
		//yyyyMM格式的日期信息,作为文件名和表名的一部分
		String tmpMonths;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String logDateDesc = sdf.format(logDate);
		//以下读取文件========
		log.info("read log-files which modified Date is"+ logDateDesc);
		int[] a = new int[]{0,0,0};
		for(FtpClient fc : fcs) {
			FTPFile[] ffs = fc.listFTPFiles();
			for(FTPFile ff : ffs) {
				if(ff.isFile() 
				&& DateFunctions.dateCompare(ff.getTimestamp().getTime(), logDate) == 0){
					a[0]++;
					log.info(fc.getFTPClient().getPassiveHost() +":"+ ff.getName()+"to be read..");
					tmpDataList = readEpgFileByFTP(fc,ff.getName());
					if(!tmpDataList.isEmpty()){
						//如果读取的文件首行是文件修改日开头的(也就是当的记录写在当天的文件里),那么忽略读入的内容
						if(tmpDataList.get(0).indexOf(logDateDesc) == 0){
							continue;
						}
						if(tmpDataList.get(0).matches("\\d{4}-\\d{2}-\\d{2}.*")){
							tmpMonths = tmpDataList.get(0).substring(0, 7).replace("-", "");
						} else if(tmpDataList.get(0).matches("\\d{8}.*")){
							tmpMonths = tmpDataList.get(0).substring(0, 6);
						} else {
							tmpMonths = ff.getName();
						}
						if(dataMap.containsKey(tmpMonths)){
							dataMap.get(tmpMonths).addAll(tmpDataList);
						} else {
							dataMap.put(tmpMonths, tmpDataList);
						}
					}
				}
				if(ff.isDirectory()){
					fc.changeDirectory(ff.getName());
					FTPFile[] ffs0 = fc.listFTPFiles();
					for(FTPFile ff0 : ffs0){
						//只进入一层,不再深入
						if(ff0.isFile() 
						&& DateFunctions.dateCompare(ff0.getTimestamp().getTime(), logDate) == 0){
							a[0]++;
							String xxx = new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(ff0.getTimestamp().getTime());
							System.out.println(ff0.getTimestamp().getTimeZone().getDisplayName()+","+xxx);
							log.info(fc.getFTPClient().getPassiveHost()+":"+ff.getName()+"/"+ ff0.getName()+" to be read..");
							tmpDataList = readEpgFileByFTP(fc,ff0.getName());
							if(!tmpDataList.isEmpty()){
								//如果读取的文件首行是文件修改日开头的(也就是当的记录写在当天的文件里),那么忽略读入的内容
								if(tmpDataList.get(0).indexOf(logDateDesc) == 0){
									continue;
								}
								if(tmpDataList.get(0).matches("\\d{4}-\\d{2}-\\d{2}.*")){
									tmpMonths = tmpDataList.get(0).substring(0, 7).replace("-", "");
								} else if(tmpDataList.get(0).matches("\\d{8}.*")){
									tmpMonths = tmpDataList.get(0).substring(0, 6);
								} else {
									tmpMonths = ff0.getName();
								}
								if(dataMap.containsKey(tmpMonths)){
									dataMap.get(tmpMonths).addAll(tmpDataList);
								} else {
									dataMap.put(tmpMonths, tmpDataList);
								}
							}
						} 
					}
					fc.changeDirectory("..");
				}
			}
		}
		//以上读取文件=======
		log.info("read success :"+ dataMap.size() + " files!");
		if(dataMap.isEmpty()){
			return a;
		}
		//以下写文件=======
		for(String key : dataMap.keySet()){
			String fileName = "epglog_"+key+".txt";
			log.info("begin writing file:"+fileName);
			List<String> dataList = dataMap.get(key);
			FileWriter fw = null;
			try {
				fw = new FileWriter(localPath+fileName,true);
				int rows = 0;
				for(String str : dataList){
					fw.append(str + "\r\n");
					rows++;
				}
				log.info(fileName + " write over!");
				fw.flush();
				fw.close();
				log.info("write success:"+rows+" rows!");
				a[1]=rows;
			} catch (IOException e) {
				e.printStackTrace();
				log.error("writing file:" + fileName + "fail!",e);
				throw new RuntimeException("写文件失败!",e);
			}
		}
		int dbwrite = 0;
		//以下写数据库=======
		for(String key : dataMap.keySet()){
			List<EpgLog> logList = new ArrayList();
			List<String> dataList = dataMap.get(key);
			for(String str : dataList){
				logList.add(toEpgLog(str));
			}
			logsMapper.batchInsertEpgLog(key, logList);
			dbwrite += logList.size();
		}
		a[2]=dbwrite;
		return a;
	}
	
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private EpgLog toEpgLog(String str){
		try {
			String[] aa = str.split("[,]");
			EpgLog el = new EpgLog();
			el.setResno(aa[1].trim());
			el.setLogTime(df.parse(aa[0].trim()));
			return el;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("转换失败:"+str);
		}
	}
	
	private List <String> readEpgFileByFTP(FtpClient fc,String fileName){
		int tv = 2;
		if(tv1EpgIPs.contains(fc.getIp())){
			tv=1;
		}
		List <String> dataList = new ArrayList();
		//以下读取文件========
		try {
			InputStream istream = fc.getFTPClient().retrieveFileStream(fileName);
			if(istream == null){
				//fc.getFTPClient().completePendingCommand();
				log.error("文件传输失败!");
				return null;
			}
			try {
				String charSet = "GBK";
				InputStreamReader isr = new InputStreamReader(istream, charSet);
				BufferedReader br = new BufferedReader(isr);
				try {
					String strLine = br.readLine();
					String datatmp = "";
					int dataSize = 0;
					if(tv==1){
						while (strLine != null) {
							if (strLine.contains("index.jsp")) {
								datatmp = matchStringEpg1(strLine);
								if(datatmp != null && !"".equals(datatmp)){
									dataList.add(datatmp);
									dataSize ++;
									if(dataSize > 0 && dataSize % 10000  == 0){
										log.info("have been read:"+dataSize+"rows");
									}
								}
							}
							strLine = br.readLine();
						}
					}
					if(tv==2){
						while (strLine != null) {
							if (strLine.contains("stbid")) {
								datatmp = matchStringEpg2(strLine);
								if(datatmp != null && !"".equals(datatmp)){
									dataList.add(datatmp);
									dataSize ++;
									if(dataSize > 0 && dataSize % 10000  == 0){
										log.info("have been read:"+dataSize+"rows");
									}
								}
							}
							strLine = br.readLine();
						}
					}
				} finally {
					br.close();
					isr.close();
				}
			} finally {
				istream.close();
				if(!fc.getFTPClient().completePendingCommand()) {
					throw new RuntimeException("读取文件失败:UnCompletePendingCommand");
				}
			}
		} catch (IOException e) {
			log.error("读取文件" + fileName + "失败!",e);
			throw new RuntimeException("读取文件失败!",e);
		}
		log.info("read file:" + fc.getFTPClient().getPassiveHost()+":"+fileName+":" + dataList.size() + " rows");
		return dataList;
	}
	
/*
	public void genEpgLogFilesLocal() {
		log.info("开始准备EPG日志...");
		//以下是
		String[] epglog1_path = ConfigUtil.get("epglog1_path").split("[,]");
		for(String localPath : epglog1_path){
			File localDir = new File(localPath);
			File[] localFiles = localDir.listFiles();
			List<String> localFNames0 = new ArrayList();
			List<String> localFNames1 = new ArrayList();
			
			for(File f: localFiles){
				//原始为文件是.log结尾的
				if(f.getName().matches("ex\\d{6}.log")){
					localFNames0.add(f.getName());
				}
				if(f.getName().matches("ex\\d{4}.txt")){
					localFNames1.add(f.getName());
				}
			}
			if(!localFNames0.isEmpty()){
				Collections.sort(localFNames0);
			}
			if(!localFNames1.isEmpty()){
				Collections.sort(localFNames1);
			}
			List<String> filesToRead = new ArrayList();
			for(String fileName0 : localFNames0){
				String fileName1 = fileName0.substring(0,6)+".txt";
				if(!localFNames1.contains(fileName1)){
					log.info(fileName0+"to be read..");
					filesToRead.add(fileName0);
				}
			}
			for(String fileName : filesToRead){
				log.info("read:" + fileName);
				readEpgFile(1,localPath+fileName,localPath+fileName.substring(0,6)+".txt");
			}
		}
		
		String[] epglog2_path = ConfigUtil.get("epglog2_path").split("[,]");
		for(String localPath : epglog2_path){
			File localDir = new File(localPath);
			File[] localFiles = localDir.listFiles();
			List<String> localFNames0 = new ArrayList();
			List<String> localFNames1 = new ArrayList();
			
			for(File f: localFiles){
				//原始为文件是.log结尾的
				if(f.getName().matches("u_ex\\d{6}.log")){
					localFNames0.add(f.getName());
				}
				if(f.getName().matches("u_ex\\d{4}.txt")){
					localFNames1.add(f.getName());
				}
			}
			if(!localFNames0.isEmpty()){
				Collections.sort(localFNames0);
			}
			if(!localFNames1.isEmpty()){
				Collections.sort(localFNames1);
			}
			List<String> filesToRead = new ArrayList();
			for(String fileName0 : localFNames0){
				String fileName1 = fileName0.substring(0,8)+".txt";
				if(!localFNames1.contains(fileName1)){
					log.info(fileName0+"to be read..");
					filesToRead.add(fileName0);
				}
			}
			for(String fileName : filesToRead){
				log.info("read:" + fileName);
				readEpgFile(2,localPath+fileName,localPath+fileName.substring(0,8)+".txt");
			}
		}
		log.info("准备EPG日志结束!");
	}
	
	//step1:ftpDownload iislog
	//step2:read iislog and write to a local file
	//step3:load data from local file to DB

	private void readEpgFile(int tv1_tv2,String filePathNameToRead,String filePathNameToWrite) {
		File file = new File(filePathNameToRead);
		BufferedInputStream fis = null;
		try {
			fis = new BufferedInputStream(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(
					new InputStreamReader(fis, "utf-8"), 5 * 1024 * 1024);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}// 用5M的缓冲读取文本文件

		String line = "";
		FileWriter fw = null;
		try {
			fw = new FileWriter(filePathNameToWrite,true);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			int i = 0;
			if(tv1_tv2 == 1){
				while ((line = reader.readLine()) != null) {
					if (line.contains("index.jsp")&&line.contains("ser=")) {
						i++;
						fw.append(matchStringEpg1(line));
					}
				}
			}
			if(tv1_tv2 == 2){
				while ((line = reader.readLine()) != null) {
					if (line.contains("stbid")) {
						i++;
						fw.append(matchStringEpg2(line));
					}
				}
			}
			log.info(filePathNameToWrite + " write over:"+i);
			reader.close();
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
*/	
	
}
