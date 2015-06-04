package com.ndtv.vodstat.report.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.ndtv.vodstat.common.pagemodel.Page;
import com.ndtv.vodstat.common.pagemodel.PageHelper;
import com.ndtv.vodstat.common.pagemodel.PageResult;
import com.ndtv.vodstat.common.util.DateFunctions;
import com.ndtv.vodstat.report.dao.VodLogsMapper;
import com.ndtv.vodstat.report.model.EpgLog;
import com.ndtv.vodstat.report.model.LogCondtion;
import com.ndtv.vodstat.report.model.WasunLog;
import com.ndtv.vodstat.report.service.VodLogsService;

@Service
public class VodLogsServiceImpl implements VodLogsService{
	
	//private Log log = LogFactory.getLog(VodLogsServiceImpl.class);
	
	@Resource
	private VodLogsMapper logsMapper;

	private void initMonthCondtion(LogCondtion lc){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMM ");
		if (lc.getRepDate1() != null && lc.getRepDate2() != null) {
			//开始结束日期的月份不同:
			if (df.format(lc.getRepDate1()).compareTo(df.format(lc.getRepDate2())) < 0) {
				List<String> queryMonths = new ArrayList();
				Date tmpa = (Date) lc.getRepDate1().clone();
				queryMonths.add(df.format(tmpa));
				while (!df.format(tmpa).equals(df.format(lc.getRepDate2()))) {
					tmpa = DateFunctions.addCalendarFeild(tmpa, Calendar.MONTH, 1);
					queryMonths.add(df.format(tmpa));
				}
				String[] sa = new String[queryMonths.size()];
				for (int i = 0; i < queryMonths.size(); i++) {
					sa[i] = queryMonths.get(i);
				}
				lc.setQueryMonthParams(sa);
			} else {
				lc.setQueryMonthParam(df.format(lc.getRepDate1()));
			}
		}
	}
	
	public PageResult getSelectEpglog(EpgLog e, PageHelper ph) {
		if (e.getRepDate1() != null && e.getRepDate2() != null) {
			initMonthCondtion(e);
			PageResult pr = new PageResult();
			List<EpgLog> ls = logsMapper.getSelectEpglog(e, new RowBounds(ph.getPage(), ph.getRows()));
			Page<EpgLog> pp = ((Page) ls);
			pr.setRows(pp.getResult());
			pr.setTotal(pp.getTotal());
			return pr;
		}
		return null;
	}
	
	public PageResult getSelectWasunlog(WasunLog w, PageHelper ph){
		if (w.getRepDate1() != null && w.getRepDate2() != null) {
			initMonthCondtion(w);
			PageResult pr = new PageResult();
			List<WasunLog> ls = logsMapper.getSelectWasunlog(w, new RowBounds(ph.getPage(),ph.getRows()));
			Page<WasunLog> pp = ((Page) ls);
			pr.setRows(pp.getResult());
			pr.setTotal(pp.getTotal());
			return pr;
		}
		return null;
	}
	
	public List getCatergoryFrequency(WasunLog w){
		if (w.getRepDate1() != null && w.getRepDate2() != null) {
			initMonthCondtion(w);
			List<WasunLog> ls = logsMapper.getCatergoryFrequency(w);
			return mergeByCatergory(ls);
		}
		return new ArrayList();
	}
	
	public List getCatergoryDuration(WasunLog w){
		if (w.getRepDate1() != null && w.getRepDate2() != null) {
			initMonthCondtion(w);
			List<WasunLog> ls = logsMapper.getCatergoryDuration(w);
			return mergeByCatergory(ls);
		}
		return new ArrayList();
	}
	
	private List mergeByCatergory(List<WasunLog> ls){
		Map<String,Long> catergoryMap = new HashMap();
		for(WasunLog wl : ls){
			if(catergoryMap.containsKey(wl.getCatergory())){
				catergoryMap.put(wl.getCatergory(), 
				(catergoryMap.get(wl.getCatergory()) + wl.getTimes())	);
			} else {
				catergoryMap.put(wl.getCatergory(), wl.getTimes());
			}
		}
		List<WasunLog> lsNew = new ArrayList();
		for(String k : catergoryMap.keySet()){
			lsNew.add(new WasunLog(k,catergoryMap.get(k)));
		}
		return lsNew;
	}
	
	public List getEverydayFrequency(WasunLog w){
		if (w.getRepDate1() != null && w.getRepDate2() != null) {
			initMonthCondtion(w);
			List<WasunLog> ls = logsMapper.getEverydayFrequency(w);
			return mergeByEveryday(w.getRepDate1(),w.getRepDate2(),ls);
		}
		return new ArrayList();
	}
	
	public List getEverydayDuration(WasunLog w){
		if (w.getRepDate1() != null && w.getRepDate2() != null) {
			initMonthCondtion(w);
			List<WasunLog> ls = logsMapper.getEverydayDuration(w);
			return mergeByEveryday(w.getRepDate1(),w.getRepDate2(),ls);
		}
		return new ArrayList();
	}

	private List mergeByEveryday(Date beginDate, Date endDate, List<WasunLog> ls){
		List<WasunLog> lsNew = new ArrayList();
		Date d0 = (Date)beginDate.clone();
		boolean exists = false;
		while (DateFunctions.dateCompare(d0, endDate) < 0){
			exists = false;
			for(WasunLog wl : ls){
				if(DateFunctions.dateCompare(d0, wl.getBeginTime()) == 0){
					lsNew.add(wl);
					exists = true;
					break;
				}
			}
			if(!exists){
				lsNew.add(new WasunLog(d0,0));
			}
			d0 = DateFunctions.addCalendarFeild(d0, Calendar.DATE, 1);
		}
		return lsNew;
	}
	
/*	
	public void genEpgLogFiles() {
		for(String localPath : epgLocalPaths){
			File localDir = new File(localPath);
			File[] localFiles = localDir.listFiles();
			List<String> localFNames0 = new ArrayList();
			List<String> localFNames1 = new ArrayList();
			
			for(File f: localFiles){
				if(Pattern.matches("u_ex\\d{6}.log",f.getName())){
					localFNames0.add(f.getName());
				}
				if(Pattern.matches("u_ex\\d{6}.txt",f.getName())){
					localFNames1.add(f.getName());
				}
			}
			if(!localFNames0.isEmpty()){
				Collections.sort(localFNames0);
			}
			if(!localFNames1.isEmpty()){
				Collections.sort(localFNames0);
			}
			List<String> filesToRead = new ArrayList();
			for(String fileName0 : localFNames0){
				String fileName1 = fileName0.substring(0,10)+".txt";
				if(!localFNames1.contains(fileName1)){
					log.info(fileName0+"to be read..");
					filesToRead.add(fileName0);
				}
			}
			
			for(String fileName : filesToRead){
				log.info("read:" + fileName);
				readFile(localPath,fileName,fileName.substring(0,10)+".txt");
			}
		}
	}


	//step1:ftpDownload iislog
	//step2:read iislog and write to a local file
	//step3:load data from local file to DB

	private void readFile(String filePath,String fileRead,String fileWrite) {
		File file = new File(filePath+fileRead);
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
			fw = new FileWriter(filePath+fileWrite);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			int i = 0;
			while ((line = reader.readLine()) != null) {
				if (line.contains("stbid")) {
					i++;
					// String as = matchString(line);
					fw.append(matchString(line) + "\r\n");
					// System.out.println(matchString(line));
					// break;
				}
			}
			log.info(fileWrite + " write over:"+i);
			reader.close();
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String matchString(String str) {
		try {
			StringBuffer sb = new StringBuffer();
			// List matches = null;
			Pattern p = Pattern.compile(
							"(\\d{1,4}[-|\\/|年|\\.]\\d{1,2}[-|\\/|月|\\.]\\d{1,2}([日|号])?(\\s)*(\\d{1,2}([点|时])?((:)?\\d{1,2}(分)?((:)?\\d{1,2}(秒)?)?)?)?(\\s)*(PM|AM)?)",
							Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
			Matcher dateM = p.matcher(str);
			if (dateM.find() && dateM.groupCount() >= 1) {
				sb.append(dateM.group(1) + ",");
				
//				matches = new ArrayList(); for (int i = 1; i <=
//				matcher.groupCount(); i++) { String temp =
//				matcher.group(i); matches.add(temp); }
				 
			}
			
//			 else { matches = Collections.EMPTY_LIST; } if (matches.size()
//			 > 0) { return ((String) matches.get(0)).trim(); } else { }

			Matcher stbidM = Pattern.compile("stbid=?(.*?)(\\s+)").matcher(str);
			if (stbidM.find() && stbidM.groupCount() >= 1) {
				sb.append(stbidM.group(1));
			}
			return sb.toString();
		} catch (Exception e) {
			return "";
		}
	}
*/	
	
	
}
