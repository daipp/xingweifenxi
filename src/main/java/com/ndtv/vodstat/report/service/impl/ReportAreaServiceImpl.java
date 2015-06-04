package com.ndtv.vodstat.report.service.impl;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ndtv.vodstat.common.pagemodel.Page;
import com.ndtv.vodstat.common.pagemodel.PageHelper;
import com.ndtv.vodstat.common.pagemodel.PageResult;
import com.ndtv.vodstat.common.util.ConfigUtil;
import com.ndtv.vodstat.common.util.DateFunctions;
import com.ndtv.vodstat.report.dao.ReportAreaDateMapper;
import com.ndtv.vodstat.report.dao.ReportAreaMonthMapper;
import com.ndtv.vodstat.report.dao.SysCodeMapper;
import com.ndtv.vodstat.report.dao.TimerJobLogMapper;
import com.ndtv.vodstat.report.model.AreaCondition;
import com.ndtv.vodstat.report.model.AreaDateReport;
import com.ndtv.vodstat.report.model.AreaMonthReport;
import com.ndtv.vodstat.report.service.IReportAreaService;
import com.ndtv.vodstat.sys.entity.SysCode;
import com.ndtv.vodstat.sys.entity.TimerJobLog;

@Service
public class ReportAreaServiceImpl implements IReportAreaService{
	
	private static final Logger logger = Logger.getLogger(ReportAreaServiceImpl.class);

	@Resource
	private ReportAreaDateMapper dReportMapper;
	@Resource
	private ReportAreaMonthMapper mReportMapperNew;
	@Resource
	private TimerJobLogMapper timerJobLogMapper;
	@Resource
	private SysCodeMapper sysCodeMapper;
	
	private static final long VODAREA_TIME = ConfigUtil.getLong("syscode_sysConfig_VODAREA_TIME");

	private static long USERTYPE_DVB = 0;
	private static long USERTYPE_VOD = 0;
	private static long USERTYPE_BB = 0;
	private static long USERTYPE_ANALOG = 0;
	private static long USERSTATE_NORMAL = 0;
	private static long USERSTATE_STOP = 0;
	private static long USERSTATE_QUIT = 0;
	
	@PostConstruct
	public void init() {
		USERTYPE_DVB = ConfigUtil.getLong("BOSS_CODE_USERTYPE_DVB");
		USERTYPE_VOD = ConfigUtil.getLong("BOSS_CODE_USERTYPE_VOD");
		USERTYPE_BB = ConfigUtil.getLong("BOSS_CODE_USERTYPE_BB");
		USERTYPE_ANALOG = ConfigUtil.getLong("BOSS_CODE_USERTYPE_AN");
		USERSTATE_NORMAL = ConfigUtil.getLong("BOSS_CODE_USERSTATE_NORMAL");
		USERSTATE_STOP = ConfigUtil.getLong("BOSS_CODE_USERSTATE_STOP");
		USERSTATE_QUIT = ConfigUtil.getLong("BOSS_CODE_USERSTATE_QUIT");
	}
	
	private void printOut(Object o){
		//System.out.println(o);
		logger.info(o);
	}
	
	/**
	 * 查询报表
	 */
	public PageResult findAreaDateReport(AreaCondition condition, PageHelper ph) {
		List<AreaDateReport> ls = null;
		
		//可以这样:
		//List<VodArea> ls = reportDao.selectList("selectPage", null, new RowBounds(ph.getPage(), ph.getRows()));
		
		//也可以这样:
		//com.ndtv.vodstat.core.interceptors.PageHelper.startPage(ph.getPage(), ph.getRows(), true);
		//List<VodArea> ls = reportMapper.selectVodAreaPage(vv);

		
		
		PageResult pr = new PageResult();
		if(DateFunctions.getCalendarFeild(condition.getRepDate1(), Calendar.YEAR) != DateFunctions.getCalendarFeild(condition.getRepDate2(), Calendar.YEAR)) {
			return pr;
		}
		if(ph == null){
			ls = dReportMapper.findAreaDateReport(condition);
			pr.setRows(ls);
			pr.setTotal(ls.size());
		} else {
//			logger.info(ph.getStartRow() + "===ph.getStartRow()");
//			logger.info(ph.getRows() + "===ph.getRows()");
			ls = dReportMapper.findAreaDateReport(condition,new RowBounds(ph.getPage(), ph.getRows()));
			Page<AreaDateReport> pp = ((Page) ls);
			pr.setRows(pp.getResult());
			pr.setTotal(pp.getTotal());
		}
		for(AreaDateReport v : ls){
			v.setCondition((AreaCondition)condition.clone());
		}
		return pr;
	}

	public PageResult findAreaMonthReport(AreaCondition condition, PageHelper ph) {
		List<AreaMonthReport> ls = null;
		PageResult pr = new PageResult();
		if(ph == null){
			ls = mReportMapperNew.findAreaMonthReport(condition);
			pr.setRows(ls);
			pr.setTotal(ls.size());
		} else {
//			logger.info(ph.getStartRow() + "===ph.getStartRow()");
//			logger.info(ph.getRows() + "===ph.getRows()");
			ls = mReportMapperNew.findAreaMonthReport(condition,new RowBounds(ph.getPage(), ph.getRows()));
			Page<AreaMonthReport> pp = ((Page) ls);
			pr.setRows(pp.getResult());
			pr.setTotal(pp.getTotal());
		}
		for(AreaMonthReport v : ls){
			v.setCondition((AreaCondition)condition.clone());
		}
		return pr;
	}
	
	@Override
	public void deleteAreaMonthReport(Date repDate) {
		Date firstDate = DateFunctions.getMonthFirstDay(repDate);
		mReportMapperNew.deleteAreaMonthReport(firstDate);
	}

	@Override
	public void deleteAreaDateReport(Date repDate) {
		dReportMapper.deleteAreaDateReport(repDate, new SimpleDateFormat("yyyy").format(repDate));
	}

	@Override
	synchronized public void updateAreaDateReport(Date repDate) {
		SimpleDateFormat monthFormat = new SimpleDateFormat("yyyyMM");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		logger.info("更新日报[" + dateFormat.format(repDate) +"]..");
		long d0 = System.currentTimeMillis();
		
//		dReportMapper.update_BookVODs(repDate, false);
//		dReportMapper.update_BookVODs(repDate, true);

		dReportMapper.update_HostStateChange(repDate,"start");
		dReportMapper.update_HostStateChange(repDate,"stop");
		dReportMapper.update_HostStateChange(repDate,"quit");
		if(true)
			return;

		//生成宽带用户数
		printOut("update_UserTypes:BB_USERS");
		dReportMapper.update_UserTypes(repDate, "BB_USERS");
		//生成基本型用户数
		printOut("update_UserTypes:DVB_USERS");
		dReportMapper.update_UserTypes(repDate, "DVB_USERS");
		//生成交互型用户数
		printOut("update_UserTypes:VOD_USERS");
		dReportMapper.update_UserTypes(repDate, "VOD_USERS");
		//生成模拟用户数
		printOut("update_UserTypes:ANALOG_USERS");
		dReportMapper.update_UserTypes(repDate, "ANALOG_USERS");
		//生成高清用户数
		printOut("update_Hdstbs");
		dReportMapper.update_Hdstbs(repDate);
		
		
		//生成在线用户数(订购的)
		printOut("update_OnlineUsersBooked");
		dReportMapper.update_OnlineBookedUsers(repDate,monthFormat.format(repDate));
		//生成在线用户数(未订购的)
		printOut("update_OnlineUsersUnbook");
		dReportMapper.update_OnlineUnbookUsers(repDate, monthFormat.format(repDate));
		
		//用户类型(正常的)栏更新
		printOut("update_UserTypes0...");
		dReportMapper.update_UserTypes0(repDate,"BB_USERS");
		dReportMapper.update_UserTypes0(repDate,"DVB_USERS");
		dReportMapper.update_UserTypes0(repDate,"VOD_USERS");
		dReportMapper.update_UserTypes0(repDate,"ANALOG_USERS");
		
		//点播订购用户数量更新
		printOut("update_BookVODs...");
		dReportMapper.update_BookVODs(repDate,true);
		dReportMapper.update_BookVODs(repDate,false);

		//宽带订购用户数量更新
		printOut("update_BookBBs...");
		dReportMapper.update_BookBBs(repDate,true);
		dReportMapper.update_BookBBs(repDate,false);
				
		//付费频道订购用户数量更新
		printOut("update_BookDVBs...");
		dReportMapper.update_BookDVBs(repDate,true);
		dReportMapper.update_BookDVBs(repDate,false);
		
		//主终端状态变更数量更新
		printOut("update_HostStateChange...");
		dReportMapper.update_HostStateChange(repDate,"start");
		dReportMapper.update_HostStateChange(repDate,"stop");
		dReportMapper.update_HostStateChange(repDate,"quit");
		
		//主终端状态数量更新
		printOut("update_HostState...");
		dReportMapper.update_HostState(repDate, "normal");
		dReportMapper.update_HostState(repDate, "stoped");
		
		//正常主终端未缴视听维护费的数量
		printOut("update_HostUnpay...");
		dReportMapper.update_HostUnpay(repDate,1);
		dReportMapper.update_HostUnpay(repDate,2);
		
		//生成活跃用户
		printOut("update_ActiveUsers");
		dReportMapper.update_ActiveUsers(repDate,monthFormat.format(repDate));

	}
	
	synchronized public void updateAreaMonthReport(Date repDate) {
		SimpleDateFormat monthFormat = new SimpleDateFormat("yyyyMM");
		repDate = DateFunctions.getMonthFirstDay(repDate);

		Date repDateLast = DateFunctions.addCalendarFeild(repDate, Calendar.MONTH, -1);
		
		

		printOut("update_UserTypeExpiringBook");
		mReportMapperNew.update_UserTypeExpiringBook(repDate, repDate, DateFunctions.getMonthLastDay(repDate), AreaMonthReport.REPORTRANGE_MONTH, "VOD");
		mReportMapperNew.update_UserTypeExpiringBook(repDate, repDate, DateFunctions.getMonthLastDay(repDate), AreaMonthReport.REPORTRANGE_MONTH, "BB");
		printOut("update_UserTypeExpiredBook");
		mReportMapperNew.update_UserTypeExpiredBook(repDate, repDate, DateFunctions.getMonthLastDay(repDate), AreaMonthReport.REPORTRANGE_MONTH, "VOD");
		mReportMapperNew.update_UserTypeExpiredBook(repDate, repDate, DateFunctions.getMonthLastDay(repDate), AreaMonthReport.REPORTRANGE_MONTH, "BB");
		printOut("update_UserTypePreBook");
		mReportMapperNew.update_UserTypePreBook(repDate, repDate, DateFunctions.getMonthLastDay(repDate), AreaMonthReport.REPORTRANGE_MONTH, "VOD");
		mReportMapperNew.update_UserTypePreBook(repDate, repDate, DateFunctions.getMonthLastDay(repDate), AreaMonthReport.REPORTRANGE_MONTH, "BB");


		if(true)
			return;
		
		

		
		
		
		
		
		
		
		//客户-用户情况汇总字段相关的更新:
		printOut("update_Customers");
		mReportMapperNew.update_Customers(repDate, DateFunctions.getMonthLastDay(repDate), AreaMonthReport.REPORTRANGE_MONTH);
		printOut("update_UserTypes");
		mReportMapperNew.update_UserTypes(repDate, DateFunctions.getMonthLastDay(repDate), AreaMonthReport.REPORTRANGE_MONTH, "BB_USERS");
		mReportMapperNew.update_UserTypes(repDate, DateFunctions.getMonthLastDay(repDate), AreaMonthReport.REPORTRANGE_MONTH, "DVB_USERS");
		mReportMapperNew.update_UserTypes(repDate, DateFunctions.getMonthLastDay(repDate), AreaMonthReport.REPORTRANGE_MONTH, "VOD_USERS");
		printOut("update_UserTypeBooked");
		mReportMapperNew.update_UserTypeBooked(repDate, repDate, DateFunctions.getMonthLastDay(repDate), AreaMonthReport.REPORTRANGE_MONTH, "VOD");
		mReportMapperNew.update_UserTypeBooked(repDate, repDate, DateFunctions.getMonthLastDay(repDate), AreaMonthReport.REPORTRANGE_MONTH, "BB");
		printOut("update_UserTypeUnbook");
		mReportMapperNew.update_UserTypeUnbook(repDate, DateFunctions.getMonthLastDay(repDate), AreaMonthReport.REPORTRANGE_MONTH, "VOD");
		mReportMapperNew.update_UserTypeUnbook(repDate, DateFunctions.getMonthLastDay(repDate), AreaMonthReport.REPORTRANGE_MONTH, "BB");
		
		printOut("update_NewUserTypes");
		mReportMapperNew.update_NewUserTypes(repDateLast, repDate, DateFunctions.getMonthLastDay(repDate), AreaMonthReport.REPORTRANGE_MONTH, "DVB");
		mReportMapperNew.update_NewUserTypes(repDateLast, repDate, DateFunctions.getMonthLastDay(repDate), AreaMonthReport.REPORTRANGE_MONTH, "VOD");
		mReportMapperNew.update_NewUserTypes(repDateLast, repDate, DateFunctions.getMonthLastDay(repDate), AreaMonthReport.REPORTRANGE_MONTH, "BB");
		printOut("update_NewUserTypeBooked");
		mReportMapperNew.update_NewUserTypeBooked(repDateLast, repDate, DateFunctions.getMonthLastDay(repDate), AreaMonthReport.REPORTRANGE_MONTH, "VOD");
		mReportMapperNew.update_NewUserTypeBooked(repDateLast, repDate, DateFunctions.getMonthLastDay(repDate), AreaMonthReport.REPORTRANGE_MONTH, "BB");

		//续费率相关的更新:
		printOut("update_UserTypeExpiring");
		mReportMapperNew.update_UserTypeExpiring(repDate, repDate, DateFunctions.getMonthLastDay(repDate), AreaMonthReport.REPORTRANGE_MONTH, "VOD");
		mReportMapperNew.update_UserTypeExpiring(repDate, repDate, DateFunctions.getMonthLastDay(repDate), AreaMonthReport.REPORTRANGE_MONTH, "BB");
		printOut("update_UserTypeExpiringBook");
		mReportMapperNew.update_UserTypeExpiringBook(repDate, repDate, DateFunctions.getMonthLastDay(repDate), AreaMonthReport.REPORTRANGE_MONTH, "VOD");
		mReportMapperNew.update_UserTypeExpiringBook(repDate, repDate, DateFunctions.getMonthLastDay(repDate), AreaMonthReport.REPORTRANGE_MONTH, "BB");
		printOut("update_UserTypeExpiredBook");
		mReportMapperNew.update_UserTypeExpiredBook(repDate, repDate, DateFunctions.getMonthLastDay(repDate), AreaMonthReport.REPORTRANGE_MONTH, "VOD");
		mReportMapperNew.update_UserTypeExpiredBook(repDate, repDate, DateFunctions.getMonthLastDay(repDate), AreaMonthReport.REPORTRANGE_MONTH, "BB");
		printOut("update_UserTypePreBook");
		mReportMapperNew.update_UserTypePreBook(repDate, repDate, DateFunctions.getMonthLastDay(repDate), AreaMonthReport.REPORTRANGE_MONTH, "VOD");
		mReportMapperNew.update_UserTypePreBook(repDate, repDate, DateFunctions.getMonthLastDay(repDate), AreaMonthReport.REPORTRANGE_MONTH, "BB");

		//活跃度相关的更新:
		String repDateMonthDesc = monthFormat.format(repDate);
		printOut("update_ActiveUsers");
		mReportMapperNew.update_ActiveUsers(repDateMonthDesc);
		printOut("update_OnlineBooked");
		mReportMapperNew.update_OnlineBooked(repDate, repDateMonthDesc);
		printOut("update_OnlineUnbook");
		mReportMapperNew.update_OnlineUnbook(repDate, repDateMonthDesc);
		printOut("update_OfflineBooked");
		mReportMapperNew.update_OfflineBooked(repDate, repDateMonthDesc);
		printOut("update_OfflineUnbook");
		mReportMapperNew.update_OfflineUnbook(repDate, repDateMonthDesc);
		printOut("update_InactiveOnline");
		mReportMapperNew.update_InactiveOnline(repDate, repDateMonthDesc);
		printOut("update_InactiveOffline");
		mReportMapperNew.update_InactiveOffline(repDate, repDateMonthDesc);
		
	}
	
	@Override
	synchronized public void genAreaReport() {
		SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
		SimpleDateFormat monthFormat = new SimpleDateFormat("yyyyMM");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		Date lastRepDate = null;
		SysCode sc = sysCodeMapper.get(VODAREA_TIME);
		if(sc != null){
			try {
				lastRepDate = dateFormat.parse(sc.getCodeContent());
			} catch (ParseException e) {
				e.printStackTrace();
				return;
			}
		}
		Date repDate = DateFunctions.addCalendarFeild(lastRepDate, Calendar.DATE, 1);
		if(DateFunctions.dateCompare(repDate, new Date()) >= 0){
			return;
		}
		if(false){	//生成日报
			logger.info("交互点播区域活跃度报表[" + dateFormat.format(repDate) + "]开始生成...");
			List ls = dReportMapper.getAreaDateReport(repDate,yearFormat.format(repDate));
			if(!ls.isEmpty()) {
				throw new RuntimeException(dateFormat.format(repDate)+"日报表数据已存在!");
			}
			logger.info("生成日报[" + dateFormat.format(repDate) +"]..");
			Date d0 = new Date();
			genAreaDateReport(repDate,monthFormat.format(repDate));

			TimerJobLog log = new TimerJobLog(VODAREA_TIME, "区域活跃度报表(日报)", new Date(),
					(new Date().getTime() - d0.getTime()), dateFormat.format(repDate));
			timerJobLogMapper.insert(log);
			
			logger.info("生成日报[" + dateFormat.format(repDate) +"]结束! in[" + (new Date().getTime() - d0.getTime()) + "ms]");
		}
		
		//生成月报
		if(DateFunctions.getCalendarFeild(repDate, Calendar.DATE) == 1) {
			Date lastDate = DateFunctions.addCalendarFeild(repDate,Calendar.DATE, -1);
			lastDate = DateFunctions.getMonthFirstDay(lastDate);
			
			List lsm = mReportMapperNew.getAreaMonthReport(lastDate);
			if(!lsm.isEmpty()) {
				throw new RuntimeException(monthFormat.format(lastDate)+"月报表数据已存在!");
			}
			logger.info("生成月报[" + dateFormat.format(lastDate) +"]");
			Date d0 = new Date();
			genAreaMonthReport(lastDate,monthFormat.format(lastDate));
			
			TimerJobLog logM = new TimerJobLog(VODAREA_TIME, "区域活跃度报表(月报)", new Date(),
					(new Date().getTime() - d0.getTime()), monthFormat.format(repDate));
			timerJobLogMapper.insert(logM);
			
			logger.info("生成月报[" + dateFormat.format(lastDate) +"]结束! in[" + (new Date().getTime() - d0.getTime()) + "ms]");
		}
		
		//更新标记
		SysCode newCode = new SysCode();
		newCode.setId(VODAREA_TIME);
		newCode.setUpdateTime(new Date());
		newCode.setCodeContent(dateFormat.format(repDate));
		newCode.setUpdateUserId(1l);
		sysCodeMapper.update(newCode);
		
		logger.info("交互点播区域活跃度报表生成结束!");
	}
	
	private void genAreaDateReport(Date repDate,String repDateMonthDesc){
		//日报表生成: 初始化各区域客户类型
		printOut("insertReportVodAreaD");
		dReportMapper.insertAreaDateReport(repDate);

		//生成客户数量
		printOut("update_Customers");
		dReportMapper.update_Customers(repDate);
		
		//生成宽带用户数
		printOut("update_UserTypes:BB_USERS");
		dReportMapper.update_UserTypes(repDate, "BB_USERS");
		//生成基本型用户数
		printOut("update_UserTypes:DVB_USERS");
		dReportMapper.update_UserTypes(repDate, "DVB_USERS");
		//生成交互型用户数
		printOut("update_UserTypes:VOD_USERS");
		dReportMapper.update_UserTypes(repDate, "VOD_USERS");
		//生成模拟用户数
		printOut("update_UserTypes:ANALOG_USERS");
		dReportMapper.update_UserTypes(repDate, "ANALOG_USERS");
		//生成高清用户数
		printOut("update_Hdstbs");
		dReportMapper.update_Hdstbs(repDate);
		
		
		//生成在线用户数(订购的)
		printOut("update_OnlineUsersBooked");
		dReportMapper.update_OnlineBookedUsers(repDate,repDateMonthDesc);
		//生成在线用户数(未订购的)
		printOut("update_OnlineUsersUnbook");
		dReportMapper.update_OnlineUnbookUsers(repDate, repDateMonthDesc);
		
		//用户类型(正常的)栏更新
		printOut("update_UserTypes0...");
		dReportMapper.update_UserTypes0(repDate,"BB_USERS");
		dReportMapper.update_UserTypes0(repDate,"DVB_USERS");
		dReportMapper.update_UserTypes0(repDate,"VOD_USERS");
		dReportMapper.update_UserTypes0(repDate,"ANALOG_USERS");
		
		//点播订购用户数量更新
		printOut("update_BookVODs...");
		dReportMapper.update_BookVODs(repDate,true);
		dReportMapper.update_BookVODs(repDate,false);

		//宽带订购用户数量更新
		printOut("update_BookBBs...");
		dReportMapper.update_BookBBs(repDate,true);
		dReportMapper.update_BookBBs(repDate,false);
				
		//付费频道订购用户数量更新
		printOut("update_BookDVBs...");
		dReportMapper.update_BookDVBs(repDate,true);
		dReportMapper.update_BookDVBs(repDate,false);
		
		//主终端状态变更数量更新
		printOut("update_HostStateChange...");
		dReportMapper.update_HostStateChange(repDate,"start");
		dReportMapper.update_HostStateChange(repDate,"stop");
		dReportMapper.update_HostStateChange(repDate,"quit");
		
		//主终端状态数量更新
		printOut("update_HostState...");
		dReportMapper.update_HostState(repDate, "normal");
		dReportMapper.update_HostState(repDate, "stoped");
		
		//正常主终端未缴视听维护费的数量
		printOut("update_HostUnpay...");
		dReportMapper.update_HostUnpay(repDate,1);
		dReportMapper.update_HostUnpay(repDate,2);
		
		//生成活跃用户
		printOut("update_ActiveUsers");
		dReportMapper.update_ActiveUsers(repDate,repDateMonthDesc);
		
		dReportMapper.insertAreaDateReportFinal(repDate, new SimpleDateFormat("yyyy").format(repDate));
	}
	
	private void genAreaMonthReport(Date repDate, String repDateMonthDesc) {
		//月报表生成: 初始化各区域客户类型
		printOut("insertReportVodAreaM");
		mReportMapperNew.insertAreaMonthReport(repDate);

		//客户-用户情况汇总字段相关的更新:
		printOut("update_Customers");
		mReportMapperNew.update_Customers(repDate, DateFunctions.getMonthLastDay(repDate), AreaMonthReport.REPORTRANGE_MONTH);
		printOut("update_UserTypes");
		mReportMapperNew.update_UserTypes(repDate, DateFunctions.getMonthLastDay(repDate), AreaMonthReport.REPORTRANGE_MONTH, "BB_USERS");
		mReportMapperNew.update_UserTypes(repDate, DateFunctions.getMonthLastDay(repDate), AreaMonthReport.REPORTRANGE_MONTH, "DVB_USERS");
		mReportMapperNew.update_UserTypes(repDate, DateFunctions.getMonthLastDay(repDate), AreaMonthReport.REPORTRANGE_MONTH, "VOD_USERS");
		printOut("update_UserTypeBooked");
		mReportMapperNew.update_UserTypeBooked(repDate, repDate, DateFunctions.getMonthLastDay(repDate), AreaMonthReport.REPORTRANGE_MONTH, "VOD");
		mReportMapperNew.update_UserTypeBooked(repDate, repDate, DateFunctions.getMonthLastDay(repDate), AreaMonthReport.REPORTRANGE_MONTH, "BB");
		printOut("update_UserTypeUnbook");
		mReportMapperNew.update_UserTypeUnbook(repDate, DateFunctions.getMonthLastDay(repDate), AreaMonthReport.REPORTRANGE_MONTH, "VOD");
		mReportMapperNew.update_UserTypeUnbook(repDate, DateFunctions.getMonthLastDay(repDate), AreaMonthReport.REPORTRANGE_MONTH, "BB");
		Date repDateLast = DateFunctions.addCalendarFeild(repDate, Calendar.MONTH, -1);
		
		printOut("update_NewUserTypes");
		mReportMapperNew.update_NewUserTypes(repDateLast, repDate, DateFunctions.getMonthLastDay(repDate), AreaMonthReport.REPORTRANGE_MONTH, "VOD");
		mReportMapperNew.update_NewUserTypes(repDateLast, repDate, DateFunctions.getMonthLastDay(repDate), AreaMonthReport.REPORTRANGE_MONTH, "BB");
		printOut("update_NewUserTypeBooked");
		mReportMapperNew.update_NewUserTypeBooked(repDateLast, repDate, DateFunctions.getMonthLastDay(repDate), AreaMonthReport.REPORTRANGE_MONTH, "VOD");
		mReportMapperNew.update_NewUserTypeBooked(repDateLast, repDate, DateFunctions.getMonthLastDay(repDate), AreaMonthReport.REPORTRANGE_MONTH, "BB");
		
		//活跃度相关的更新:
		printOut("update_ActiveUsers");
		mReportMapperNew.update_ActiveUsers(repDateMonthDesc);
		printOut("update_OnlineBooked");
		mReportMapperNew.update_OnlineBooked(repDate, repDateMonthDesc);
		printOut("update_OnlineUnbook");
		mReportMapperNew.update_OnlineUnbook(repDate, repDateMonthDesc);
		printOut("update_OfflineBooked");
		mReportMapperNew.update_OfflineBooked(repDate, repDateMonthDesc);
		printOut("update_OfflineUnbook");
		mReportMapperNew.update_OfflineUnbook(repDate, repDateMonthDesc);
		printOut("update_InactiveOnline");
		mReportMapperNew.update_InactiveOnline(repDateLast, repDateMonthDesc);
		printOut("update_InactiveOffline");
		mReportMapperNew.update_InactiveOffline(repDateLast, repDateMonthDesc);
		
		//续费率相关的更新:
		printOut("update_UserTypeExpiring");
		mReportMapperNew.update_UserTypeExpiring(repDateLast, repDate, DateFunctions.getMonthLastDay(repDate), AreaMonthReport.REPORTRANGE_MONTH, "VOD");
		mReportMapperNew.update_UserTypeExpiring(repDateLast, repDate, DateFunctions.getMonthLastDay(repDate), AreaMonthReport.REPORTRANGE_MONTH, "BB");
		printOut("update_UserTypeExpiringBook");
		mReportMapperNew.update_UserTypeExpiringBook(repDateLast, repDate, DateFunctions.getMonthLastDay(repDate), AreaMonthReport.REPORTRANGE_MONTH, "VOD");
		mReportMapperNew.update_UserTypeExpiringBook(repDateLast, repDate, DateFunctions.getMonthLastDay(repDate), AreaMonthReport.REPORTRANGE_MONTH, "BB");
		printOut("update_UserTypeExpiredBook");
		mReportMapperNew.update_UserTypeExpiredBook(repDateLast, repDate, DateFunctions.getMonthLastDay(repDate), AreaMonthReport.REPORTRANGE_MONTH, "VOD");
		mReportMapperNew.update_UserTypeExpiredBook(repDateLast, repDate, DateFunctions.getMonthLastDay(repDate), AreaMonthReport.REPORTRANGE_MONTH, "BB");
		printOut("update_UserTypePreBook");
		mReportMapperNew.update_UserTypePreBook(repDateLast, repDate, DateFunctions.getMonthLastDay(repDate), AreaMonthReport.REPORTRANGE_MONTH, "VOD");
		mReportMapperNew.update_UserTypePreBook(repDateLast, repDate, DateFunctions.getMonthLastDay(repDate), AreaMonthReport.REPORTRANGE_MONTH, "BB");
		
	}
	
	//查月报表里的用户清单
	public PageResult getAreaMonthReport_userList(AreaCondition vv, PageHelper ph,String getWhat){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
		logger.info("getWhat="+getWhat);
		if("getDvbUsers".equals(getWhat)){
			getWhat = "getUserTypes";
			vv.setUserTypeId(USERTYPE_DVB);
		}
		if("getVodUsers".equals(getWhat)){
			getWhat = "getUserTypes";
			vv.setUserTypeId(USERTYPE_VOD);
		}
		if("getBbUsers".equals(getWhat)){
			getWhat = "getUserTypes";
			vv.setUserTypeId(USERTYPE_BB);
		}
		if("getAnalogUsers".equals(getWhat)){
			getWhat = "getUserTypes";
			vv.setUserTypeId(USERTYPE_ANALOG);
		}
		if("getVodBooked".equals(getWhat)){
			getWhat = "getUserTypeBooked";
		}
		if("getBbBooked".equals(getWhat)){
			getWhat = "getUserTypeBooked";
		}
		if("getVodUnbook".equals(getWhat)){
			getWhat = "getUserTypeUnbook";
		}
		if("getBbUnbook".equals(getWhat)){
			getWhat = "getUserTypeUnbook";
		}
		if(getWhat.indexOf("Expiring") > 0) {
			vv.setRepDate1(DateFunctions.getMonthFirstDay(vv.getRepDate()));
			vv.setRepDate2(DateFunctions.getMonthLastDay(vv.getRepDate()));
		}
		if("getVodExpiring".equals(getWhat)){
			getWhat = "getUserTypeExpiring";
		}
		if("getBbExpiring".equals(getWhat)){
			getWhat = "getUserTypeExpiring";
		}
		if("getVodExpiringBook".equals(getWhat)){
			getWhat = "getUserTypeExpiringBook";
		}
		if("getBbExpiringBook".equals(getWhat)){
			getWhat = "getUserTypeExpiringBook";
		}
		if("getVodExpiredBook".equals(getWhat)){
			getWhat = "getUserTypeExpiredBook";
			vv.setRepDate1(DateFunctions.getMonthFirstDay(vv.getRepDate()));
			vv.setRepDate2(DateFunctions.getMonthLastDay(vv.getRepDate()));
		}
		if("getBbExpiredBook".equals(getWhat)){
			getWhat = "getUserTypeExpiredBook";
			vv.setRepDate1(DateFunctions.getMonthFirstDay(vv.getRepDate()));
			vv.setRepDate2(DateFunctions.getMonthLastDay(vv.getRepDate()));
		}
		if("getVodPreBook".equals(getWhat)){
			getWhat = "getUserTypePreBook";
			vv.setRepDate1(DateFunctions.getMonthFirstDay(vv.getRepDate()));
			vv.setRepDate2(DateFunctions.getMonthLastDay(vv.getRepDate()));
		}
		if("getBbPreBook".equals(getWhat)){
			getWhat = "getUserTypePreBook";
			vv.setRepDate1(DateFunctions.getMonthFirstDay(vv.getRepDate()));
			vv.setRepDate2(DateFunctions.getMonthLastDay(vv.getRepDate()));
		}
		
		
		try {	//根据getWhat自动映射为方法调用
			PageResult pr = new PageResult();
			if(ph != null) {
				Method method = mReportMapperNew.getClass().getMethod(getWhat, new Class[] { AreaCondition.class, RowBounds.class });
				List ls = (List)method.invoke(mReportMapperNew, new Object[]{vv, new RowBounds(ph.getPage(), ph.getRows())});
				Page pp = ((Page) ls);
				pr.setRows(pp.getResult());
				pr.setTotal(pp.getTotal());
			} else {
				Method method = mReportMapperNew.getClass().getMethod(getWhat, new Class[] { AreaCondition.class });
				List ls = (List)method.invoke(mReportMapperNew, new Object[]{vv});
				pr.setRows(ls);
				pr.setTotal(ls.size());
			}
			return pr;
		} catch(Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("查询失败",ex);
		}
	}

	//查日报表里的用户清单
	public PageResult getAreaDateReport_userList(AreaCondition vv, PageHelper ph,String getWhat){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
		if(vv.getRepDate() != null){
			vv.setQueryMonth(df.format(vv.getRepDate()));
		}
		logger.info("getWhat="+getWhat);
		if("getDvbUsers".equals(getWhat)){
			getWhat = "getUserTypes";
			vv.setUserTypeId(USERTYPE_DVB);
		}
		if("getVodUsers".equals(getWhat)){
			getWhat = "getUserTypes";
			vv.setUserTypeId(USERTYPE_VOD);
		}
		if("getBbUsers".equals(getWhat)){
			getWhat = "getUserTypes";
			vv.setUserTypeId(USERTYPE_BB);
		}
		if("getAnalogUsers".equals(getWhat)){
			getWhat = "getUserTypes";
			vv.setUserTypeId(USERTYPE_ANALOG);
		}
		if("getDvbUsers0".equals(getWhat)){
			getWhat = "getUserTypes0";
			vv.setUserTypeId(USERTYPE_DVB);
		}
		if("getVodUsers0".equals(getWhat)){
			getWhat = "getUserTypes0";
			vv.setUserTypeId(USERTYPE_VOD);
		}
		if("getBbUsers0".equals(getWhat)){
			getWhat = "getUserTypes0";
			vv.setUserTypeId(USERTYPE_BB);
		}
		if("getAnalogUsers0".equals(getWhat)){
			getWhat = "getUserTypes0";
			vv.setUserTypeId(USERTYPE_ANALOG);
		}
		if("getHostStops".equals(getWhat)){
			getWhat = "getHostStateChange";
			vv.setUserStateId(USERSTATE_STOP);
		}
		if("getHostStarts".equals(getWhat)){
			getWhat = "getHostStateChange";
			vv.setUserStateId(USERSTATE_NORMAL);
		}
		if("getHostQuits".equals(getWhat)){
			getWhat = "getHostStateChange";
			vv.setUserStateId(USERSTATE_QUIT);
		}
		if("getHostStoped".equals(getWhat)){
			getWhat = "getHostState";
			vv.setUserStateId(USERSTATE_STOP);
		}
		if("getHostNormal".equals(getWhat)){
			getWhat = "getHostState";
			vv.setUserStateId(USERSTATE_NORMAL);
		}
		
		try {	//根据getWhat自动映射为方法调用
			PageResult pr = new PageResult();
			if(ph != null) {
				Method method = dReportMapper.getClass().getMethod(getWhat, new Class[] { AreaCondition.class, RowBounds.class });
				List ls = (List)method.invoke(dReportMapper, new Object[]{vv, new RowBounds(ph.getPage(), ph.getRows())});
				Page pp = ((Page) ls);
				pr.setRows(pp.getResult());
				pr.setTotal(pp.getTotal());
			} else {
				Method method = dReportMapper.getClass().getMethod(getWhat, new Class[] { AreaCondition.class });
				List ls = (List)method.invoke(dReportMapper, new Object[]{vv});
				pr.setRows(ls);
				pr.setTotal(ls.size());
			}
			return pr;
		} catch(Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("查询失败",ex);
		}
	
	}
	
}
