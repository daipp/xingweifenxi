package com.ndtv.vodstat.report.controller;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ndtv.vodstat.common.pagemodel.Json;
import com.ndtv.vodstat.common.util.ConfigUtil;
import com.ndtv.vodstat.common.util.DateFunctions;
import com.ndtv.vodstat.report.service.BossCustomerUserService;
import com.ndtv.vodstat.report.service.BossService;
import com.ndtv.vodstat.report.service.EpgLogsService;
import com.ndtv.vodstat.report.service.IReportAreaService;
import com.ndtv.vodstat.report.service.IReportVodService;
import com.ndtv.vodstat.sys.entity.SysCode;
import com.ndtv.vodstat.sys.service.ISysCodeService;

@Controller
@RequestMapping("/reportGen")
public class ReportGenerate {
	
	private static final Logger logger = Logger.getLogger(ReportGenerate.class);

	@Resource
	private BossService bossService;
	@Resource
	private BossCustomerUserService bossCustomerUserService;
	@Resource
	private IReportVodService reportVodService;
	@Resource
	private EpgLogsService epgLogsService;
	@Resource
	private ISysCodeService codeService;
	@Resource
	private IReportAreaService reportAreaService;
	
	//private static final long SYN_BOSS_TIME = ConfigUtil.getLong("syscode_sysConfig_SYNBOSS_TIME");
	private static final long EPGLOG_lasttime = ConfigUtil.getLong("syscode_sysConfig_epglogFile_TIME");
	private static final long VODAREA_TIME = ConfigUtil.getLong("syscode_sysConfig_VODAREA_TIME");
	private static final long CUSTUSER_LASTTIME = ConfigUtil.getLong("syscode_sysConfig_custUserReport_TIME");
	
	
	@RequestMapping("/synBossTab")
	@ResponseBody
	public Json synBossTables() {
		bossService.synBossTables();
		Json j = new Json();
		j.setSuccess(true);
		j.setMsg("更新成功！");
		return j;
	}
	
	@RequestMapping("/genReportBossCustUserNow")
	@ResponseBody
	public Json genBossCustomerUserServiceNow(HttpServletRequest request) {
		bossCustomerUserService.genReport();
		Json j = new Json();
		j.setSuccess(true);
		j.setMsg("更新成功！");
		return j;
	}

	@RequestMapping("/genAreaReportNow")
	@ResponseBody
	public String genAreaReportNow(HttpServletRequest request) {
		reportAreaService.genAreaReport();
		return "success";
	}
	
	@RequestMapping("/delAreaReportD")
	@ResponseBody
	public String delAreaReportD(HttpServletRequest request,Date repDate) {
		reportAreaService.deleteAreaDateReport(repDate);
		return "success";
	}
	
	@RequestMapping("/delAreaReportM")
	@ResponseBody
	public String delAreaReportM(HttpServletRequest request,Date repDate) {
		reportAreaService.deleteAreaMonthReport(repDate);
		return "success";
	}
	
	
//	============================================以下是异步批量生成:
	//线程池大小
	private static final int CORE_POOL_SIZE = 1;
	//线程池
	private static ExecutorService threadPool = null;
	
	@RequestMapping("/initEpgLog")
	@ResponseBody
	public String initEpgLog(HttpServletRequest request, Date d1, Date d2) {
		if(threadPool == null || threadPool.isShutdown()){
			threadPool = Executors.newFixedThreadPool(CORE_POOL_SIZE);
		}
		Map mp = new HashMap();
		if(d1!=null){
			mp.put("beginTime", d1);
		}
		if(d2!=null){
			mp.put("endTime", d2);
		}
		threadPool.submit(new ReportGenerateThread("initEpgLog",mp));
		return "success";
	}

	@RequestMapping("/genAreaReport")
	@ResponseBody
	public String genAreaReport(HttpServletRequest request) {
		if(threadPool == null || threadPool.isShutdown()){
			threadPool = Executors.newFixedThreadPool(CORE_POOL_SIZE);
		}
		threadPool.submit(new ReportGenerateThread("genAreaReport",new HashMap()));
		return "success";
	}
	
	@RequestMapping("/updateAreaReportD")
	@ResponseBody
	public String updateAreaReportD(HttpServletRequest request,Date d1, Date d2) {
		if(threadPool == null || threadPool.isShutdown()){
			threadPool = Executors.newFixedThreadPool(CORE_POOL_SIZE);
		}
		Map mp = new HashMap();
		mp.put("beginTime", d1);
		mp.put("endTime", d2);
		threadPool.submit(new ReportGenerateThread("updateAreaReportD", mp));
		return "success";
	}
	
	@RequestMapping("/updateAreaReportM")
	@ResponseBody
	public String updateAreaReportM(HttpServletRequest request,Date d0) {
		if(threadPool == null || threadPool.isShutdown()){
			threadPool = Executors.newFixedThreadPool(CORE_POOL_SIZE);
		}
		Map mp = new HashMap();
		mp.put("repDate", d0);
		threadPool.submit(new ReportGenerateThread("updateAreaReportM", mp));
		return "success";
	}
	
	@RequestMapping("/genReportBossCustUser")
	@ResponseBody
	public String genBossCustomerUserService(HttpServletRequest request) {
		if(threadPool == null || threadPool.isShutdown()){
			threadPool = Executors.newFixedThreadPool(CORE_POOL_SIZE);
		}
		threadPool.submit(new ReportGenerateThread("genReportBossCustUser",new HashMap()));
		return "success";
	}
	
	class ReportGenerateThread implements Callable<Integer>, Serializable {
		private static final long serialVersionUID = 0;
		private Log log = LogFactory.getLog(ReportGenerateThread.class);
		private String dowhat;
		
		private Map params;
		
		public ReportGenerateThread(String dowhat,Map params){
			this.dowhat  = dowhat;
			this.params = params;
		}

		public Integer call() {
			log.info("call...");
			
			try{
				if(dowhat!=null && dowhat.equals("initEpgLog")){
					initEpgLog(params);
				} else if(dowhat!=null && dowhat.equals("genAreaReport")){
					genAreaReport(params);
				} else if(dowhat!=null && dowhat.equals("genReportBossCustUser")){
					genBossCustomerUserService(params);
				} else if(dowhat!=null && dowhat.equals("updateAreaReportD")) {
					updateAreaReportD(params);
				} else if(dowhat!=null && dowhat.equals("updateAreaReportM")) {
					updateAreaReportM(params);
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}

			log.info("call over!");
			return 0;
		}
		
		//EPG日志抓取
		private void initEpgLog(Map params){
			if(true){
				epgLogsService.genEpgLog();
				return;
			}
			
			Date endTime = DateFunctions.addCalendarFeild(new Date(), Calendar.DATE, -1);
			SimpleDateFormat markdf = new SimpleDateFormat("yyyy-MM-dd");
			SysCode sc = codeService.get(EPGLOG_lasttime);
			Date lastFileTime = new Date();
			try {
				lastFileTime = markdf.parse(sc.getCodeContent());
			} catch (ParseException e) {
				e.printStackTrace();
				return;
			}
			lastFileTime = DateFunctions.addCalendarFeild(lastFileTime, Calendar.DATE, 1);
			while (DateFunctions.dateCompare(lastFileTime, endTime)<=0){
				try{
					logger.info("initEpgLog:"+markdf.format(lastFileTime));
					epgLogsService.genEpgLog();
					sc = codeService.get(EPGLOG_lasttime);
					lastFileTime = markdf.parse(sc.getCodeContent());
					lastFileTime = DateFunctions.addCalendarFeild(lastFileTime, Calendar.DATE, 1);
				} catch(Exception ex){
					ex.printStackTrace();
					break;
				}
			}
			
		}

		//活跃度分析报表
		private void genAreaReport(Map params){
			if(true){
				try{
					reportAreaService.genAreaReport();
				}catch(Exception ex){
					ex.printStackTrace();
				}
				return;
			}
			SimpleDateFormat markdf = new SimpleDateFormat("yyyy-MM-dd");
			SysCode sc = codeService.get(VODAREA_TIME);
			Date lastVodStatTime = new Date();
			try {
				lastVodStatTime = markdf.parse(sc.getCodeContent());
			} catch (ParseException e) {
				e.printStackTrace();
				return;
			}
			lastVodStatTime = DateFunctions.addCalendarFeild(lastVodStatTime, Calendar.DATE, 1);
			
			while (DateFunctions.dateCompare(lastVodStatTime, new Date())<0){
				try{
					logger.info("genAreaReport:"+markdf.format(lastVodStatTime));
					
					reportAreaService.genAreaReport();
					sc = codeService.get(VODAREA_TIME);
					lastVodStatTime = markdf.parse(sc.getCodeContent());
				} catch(Exception ex){
					ex.printStackTrace();
					break;
				}
			}
		}
		
		//客户用户情况汇总表
		private void genBossCustomerUserService(Map params){
			SimpleDateFormat markdf = new SimpleDateFormat("yyyy-MM-dd");
			SysCode sc = codeService.get(CUSTUSER_LASTTIME);
			Date lastRepTime = new Date();
			try {
				lastRepTime = markdf.parse(sc.getCodeContent());
			} catch (ParseException e) {
				e.printStackTrace();
				return;
			}
			
			Date repDate = DateFunctions.addCalendarFeild(lastRepTime, Calendar.MONTH, 1);
			repDate = DateFunctions.getMonthFirstDay(repDate);

			while(DateFunctions.dateCompare(repDate, new Date()) < 0){
				try{
					logger.info("genBossCustomerUserService:"+markdf.format(repDate));
					
					bossCustomerUserService.genReport();
					sc = codeService.get(CUSTUSER_LASTTIME);
					lastRepTime = markdf.parse(sc.getCodeContent());
					repDate = DateFunctions.addCalendarFeild(lastRepTime, Calendar.MONTH, 1);
					repDate = DateFunctions.getMonthFirstDay(repDate);
				} catch(Exception ex){
					ex.printStackTrace();
					break;
				}
			}
		}
		
		private void updateAreaReportD(Map params){
			SimpleDateFormat markdf = new SimpleDateFormat("yyyy-MM-dd");
			Date repDate = (Date)params.get("beginTime");
			Date d2 = (Date)params.get("endTime");

			while(DateFunctions.dateCompare(repDate, d2) <= 0){
				logger.info("updateReportVodAreaD:"+markdf.format(repDate));
				reportAreaService.updateAreaDateReport(repDate);
				repDate = DateFunctions.addCalendarFeild(repDate, Calendar.DATE, 1);
			}
		}
		private void updateAreaReportM(Map params){
			SimpleDateFormat markdf = new SimpleDateFormat("yyyy-MM-dd");
			Date repDate = (Date)params.get("repDate");
			logger.info("updateAreaMonthReport:"+markdf.format(repDate));
			reportAreaService.updateAreaMonthReport(repDate);
		}
		
		
		
	}



}
