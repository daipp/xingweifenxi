package com.ndtv.vodstat.report.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.ndtv.vodstat.common.pagemodel.Page;
import com.ndtv.vodstat.common.pagemodel.PageHelper;
import com.ndtv.vodstat.common.pagemodel.PageResult;
import com.ndtv.vodstat.common.util.ConfigUtil;
import com.ndtv.vodstat.common.util.DateFunctions;
import com.ndtv.vodstat.report.dao.BossCustmoerUserReportMapper;
import com.ndtv.vodstat.report.dao.BossUserInfoMapper;
import com.ndtv.vodstat.report.dao.CustomerUserNowReportMapper;
import com.ndtv.vodstat.report.dao.SysCodeMapper;
import com.ndtv.vodstat.report.dao.TimerJobLogMapper;
import com.ndtv.vodstat.report.model.BossUser;
import com.ndtv.vodstat.report.model.VodArea;
import com.ndtv.vodstat.report.model.VodAreaCondition;
import com.ndtv.vodstat.report.service.BossCustomerUserService;
import com.ndtv.vodstat.sys.entity.SysCode;
import com.ndtv.vodstat.sys.entity.TimerJobLog;

@Service
public class BossCustomerUserServiceImpl implements BossCustomerUserService {

	private Log logger = LogFactory.getLog(BossCustomerUserServiceImpl.class);
	
	@Resource
	private BossCustmoerUserReportMapper bossCustomerUserMapper;
	
	@Resource
	private CustomerUserNowReportMapper customerUserNowReportMapper;
	
	@Resource
	private BossUserInfoMapper bossUserInfoMapper;
	
	@Resource
	private TimerJobLogMapper timerJobLogMapper;
	@Resource
	private SysCodeMapper syscodeMapper;
	
	private static final long CUSTUSER_LASTTIME = ConfigUtil.getLong("syscode_sysConfig_custUserReport_TIME");
	
	private void printOut(Object o){
		//System.out.println(o);
		logger.info(o);
	}
	
	public PageResult findReportBOSSCustomerUser(VodAreaCondition condition, PageHelper ph){
		List<VodArea> ls = null;
		PageResult pr = new PageResult();
		if(ph == null){
			ls = bossCustomerUserMapper.findReportBOSSCustomerUser(condition);
			pr.setRows(ls);
			pr.setTotal(ls.size());
		} else {
			ls = bossCustomerUserMapper.findReportBOSSCustomerUser(condition,new RowBounds(ph.getPage(), ph.getRows()));
			Page<VodArea> pp = ((Page) ls);
			pr.setRows(pp.getResult());
			pr.setTotal(pp.getTotal());
		}
		for(VodArea v : ls){
			v.setCondition((VodAreaCondition)condition.clone());
		}
		return pr;
	}
	
	public void killReport(String reportRange, Date repDate) {
		bossCustomerUserMapper.deleteReportBOSSCustomerUser(repDate, reportRange);
	}

	synchronized public void genReport() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		Date lastRepDate = null;
		SysCode sc = syscodeMapper.get(CUSTUSER_LASTTIME);
		if(sc != null){
			try {
				lastRepDate = dateFormat.parse(sc.getCodeContent());
			} catch (ParseException e) {
				e.printStackTrace();
				return;
			}
		}
		Date repDate = DateFunctions.addCalendarFeild(lastRepDate, Calendar.MONTH, 1);
		repDate = DateFunctions.getMonthFirstDay(repDate);
		if(DateFunctions.dateCompare(repDate, new Date()) >= 0){
			return;
		}
		
		//上个月月底
		Date yesterday = DateFunctions.addCalendarFeild(repDate, Calendar.DATE, -1);
		yesterday = DateFunctions.dateMax(yesterday);
		
		VodAreaCondition condition = new VodAreaCondition();
		condition.setRepDate(yesterday);
		condition.setRepDate2(yesterday);
		
		List lsM = bossCustomerUserMapper.getReportBOSSCustomerUser(yesterday,"month");
		if(!lsM.isEmpty()) {
			throw new RuntimeException(dateFormat.format(yesterday)+"月报表数据已存在!");
		}
		Date d00 = new Date();
		logger.info("生成月报[" + dateFormat.format(yesterday) +"]..");
		condition.setRepDate1(DateFunctions.dateTruncate(DateFunctions.getMonthFirstDay(yesterday)));
		condition.setReportRange("month");
		genReport0(condition);
		logger.info("生成月报[" + dateFormat.format(yesterday) +"]结束!");
		
		TimerJobLog logInfo = new TimerJobLog(CUSTUSER_LASTTIME, "客户用户情况汇总报表(月报)", new Date(),
				(new Date().getTime()-d00.getTime()), dateFormat.format(yesterday));
		timerJobLogMapper.insert(logInfo);

		int month = DateFunctions.getCalendarFeild(yesterday, Calendar.MONTH);
		if((month+1) % 3 == 0){
			List lsS = bossCustomerUserMapper.getReportBOSSCustomerUser(yesterday,"season");
			if(!lsS.isEmpty()) {
				throw new RuntimeException(dateFormat.format(yesterday)+"季报表数据已存在!");
			}
			d00 = new Date();
			logger.info("生成季报[" + dateFormat.format(yesterday) +"]..");
			Date d0 = DateFunctions.addCalendarFeild(repDate, Calendar.MONTH, -3);
			condition.setRepDate1(DateFunctions.dateTruncate(d0));
			condition.setReportRange("season");
			genReport0(condition);
			logger.info("生成季报[" + dateFormat.format(yesterday) +"]结束!");

			logInfo = new TimerJobLog(CUSTUSER_LASTTIME, "客户用户情况汇总报表(季报)", new Date(),
					(new Date().getTime()-d00.getTime()), dateFormat.format(yesterday));
			timerJobLogMapper.insert(logInfo);
			
			if(month == Calendar.DECEMBER){
				List lsY = bossCustomerUserMapper.getReportBOSSCustomerUser(yesterday,"year");
				if(!lsY.isEmpty()) {
					throw new RuntimeException(dateFormat.format(yesterday)+"年报表数据已存在!");
				}
				d00 = new Date();
				logger.info("生成年报[" + dateFormat.format(yesterday) +"]..");
				Calendar c0 = Calendar.getInstance();
				c0.setTime(yesterday);
				c0.set(Calendar.MONTH, Calendar.JANUARY);
				c0.set(Calendar.DATE, 1);
				condition.setRepDate1(DateFunctions.dateTruncate(c0.getTime()));
				condition.setReportRange("year");
				genReport0(condition);
				logger.info("生成年报[" + dateFormat.format(yesterday) +"]结束!");
				
				logInfo = new TimerJobLog(CUSTUSER_LASTTIME, "客户用户情况汇总报表(年报)", new Date(),
						(new Date().getTime()-d00.getTime()), dateFormat.format(yesterday));
				timerJobLogMapper.insert(logInfo);
			}
		}
		
		//更新标记
		SysCode scNew = new SysCode();
		scNew.setId(CUSTUSER_LASTTIME);
		scNew.setUpdateTime(new Date());
		scNew.setCodeContent(DateFunctions.dateToStr(repDate, "yyyy-MM-dd"));
		scNew.setUpdateUserId(1l);
		syscodeMapper.update(scNew);
		logger.info("BOSS客户用户信息汇总报表生成结束!");
	}
	
	private void genReport0(VodAreaCondition condition){
		Date repDate = condition.getRepDate();
		String reportRange = condition.getReportRange();
		Date repDate1 = condition.getRepDate1();
		Date repDate2 = condition.getRepDate2();
		Date d0 = new Date();
		
		printOut("insertReportBOSSCustomerUser");
		bossCustomerUserMapper.insertReportBOSSCustomerUser(repDate, reportRange);
		printOut("insertReportBOSSCustomerUser in" + (new Date().getTime() - d0.getTime()));
		
		printOut("update_Customers");
		bossCustomerUserMapper.update_Customers(repDate, reportRange);
		
		printOut("update_UserTypes");
		bossCustomerUserMapper.update_UserTypes(repDate, reportRange, "VOD_USERS");
		bossCustomerUserMapper.update_UserTypes(repDate, reportRange, "DVB_USERS");
		bossCustomerUserMapper.update_UserTypes(repDate, reportRange, "BB_USERS");
		
		d0 = new Date();
		printOut("update_OnlineUserTypes");
		bossCustomerUserMapper.update_OnlineUserTypes(repDate, reportRange, "VOD_USERS",repDate1,repDate2);
		bossCustomerUserMapper.update_OnlineUserTypes(repDate, reportRange, "BB_USERS",repDate1,repDate2);
		printOut("update_OnlineUserTypes in" + (new Date().getTime() - d0.getTime()));
		
		printOut("update_OfflineUserTypes");
		bossCustomerUserMapper.update_OfflineUserTypes(repDate, reportRange, "VOD_USERS");
		bossCustomerUserMapper.update_OfflineUserTypes(repDate, reportRange, "BB_USERS");

		Date repDateLast = DateFunctions.getMonthFirstDay(repDate);
		if("month".equals(reportRange)){
			repDateLast = DateFunctions.addCalendarFeild(repDateLast, Calendar.MONTH, -1);
		}
		if("season".equals(reportRange)){
			repDateLast = DateFunctions.addCalendarFeild(repDateLast, Calendar.MONTH, -3);
		}
		if("year".equals(reportRange)){
			repDateLast = DateFunctions.addCalendarFeild(repDateLast, Calendar.MONTH, -12);
		}
		repDateLast = DateFunctions.getMonthLastDay(repDateLast);
		
		printOut("update_NewUserTypes");
		bossCustomerUserMapper.update_NewUserTypes(repDateLast, repDate, reportRange, "VOD_USERS");
		bossCustomerUserMapper.update_NewUserTypes(repDateLast, repDate, reportRange, "DVB_USERS");
		bossCustomerUserMapper.update_NewUserTypes(repDateLast, repDate, reportRange, "BB_USERS");

		d0 = new Date();
		printOut("update_NewOnlineUserTypes");
		bossCustomerUserMapper.update_NewOnlineUserTypes(repDateLast, repDate, reportRange, "VOD_USERS");
		bossCustomerUserMapper.update_NewOnlineUserTypes(repDateLast, repDate, reportRange, "BB_USERS");
		printOut("update_NewOnlineUserTypes in" + (new Date().getTime() - d0.getTime()));
	}

	@Override
	public PageResult findCustomerUserNow(VodAreaCondition condition, PageHelper ph) {
		List<VodArea> ls1 = customerUserNowReportMapper.countVodOpenNowUsers(condition);
		List<VodArea> ls2 = customerUserNowReportMapper.countVodOpenDelayUsers(condition);
		List<VodArea> ls3 = customerUserNowReportMapper.countVodNotOpenUsers(condition);
		
		List<VodArea> ls4 = customerUserNowReportMapper.countbbOpenNowUsers(condition);
		List<VodArea> ls5 = customerUserNowReportMapper.countbbOpenDelayUsers(condition);
		List<VodArea> ls6 = customerUserNowReportMapper.countbbNotOpenUsers(condition);
		
		List<VodArea> ls0 = new ArrayList();
		for(VodArea cu : ls1){
			ls0.add((VodArea)cu.clone());
		}
		ls0 = combineCustomerUserNowList(ls0,ls2);
		ls0 = combineCustomerUserNowList(ls0,ls3);
		ls0 = combineCustomerUserNowList(ls0,ls4);
		ls0 = combineCustomerUserNowList(ls0,ls5);
		ls0 = combineCustomerUserNowList(ls0,ls6);
		
		PageResult pr = new PageResult();
		pr.setRows(ls0);
		pr.setTotal(ls0.size());
		
		for(VodArea v : ls0){
			v.setCondition((VodAreaCondition)condition.clone());
		}
		return pr;
	}
	
	public PageResult findConditionCustomer(VodAreaCondition condition, PageHelper ph){
		List<BossUser> ls = null;
		PageResult pr = new PageResult();
		if(ph==null)
		{
			ls = bossUserInfoMapper.findConditionCustomer(condition);
			pr.setRows(ls);
			pr.setTotal(ls.size());
		}else{
			ls = bossUserInfoMapper.findConditionCustomer(condition,new RowBounds(ph.getPage(), ph.getRows()));
			Page<BossUser> pp = ((Page) ls);
			pr.setRows(pp.getResult());
			pr.setTotal(pp.getTotal());
		}
		return pr;
	}
	private List<VodArea> combineCustomerUserNowList(List<VodArea> ls0,List<VodArea> lsx) {
		for(VodArea v2 : lsx){
			boolean hasSameKey = false;
			for(VodArea v0 : ls0){
				if(v0.isSameKey(v2)){
					v0.add(v2);
					hasSameKey = true;
				}
			}
			if(!hasSameKey){
				ls0.add((VodArea)v2.clone());
			}
		}
		return ls0;
	}

}
