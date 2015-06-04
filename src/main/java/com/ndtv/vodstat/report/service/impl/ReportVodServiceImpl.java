package com.ndtv.vodstat.report.service.impl;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ndtv.vodstat.common.pagemodel.Page;
import com.ndtv.vodstat.common.pagemodel.PageHelper;
import com.ndtv.vodstat.common.pagemodel.PageResult;
import com.ndtv.vodstat.common.util.DateFunctions;
import com.ndtv.vodstat.report.dao.BossUserInfoMapper;
import com.ndtv.vodstat.report.dao.SysCodeMapper;
import com.ndtv.vodstat.report.dao.TimerJobLogMapper;
import com.ndtv.vodstat.report.dao.VodAreaNowReportMapper;
import com.ndtv.vodstat.report.model.AreaMonthReport;
import com.ndtv.vodstat.report.model.BossUserInfo;
import com.ndtv.vodstat.report.model.VodArea;
import com.ndtv.vodstat.report.model.VodAreaCondition;
import com.ndtv.vodstat.report.service.IReportVodService;

@Service
public class ReportVodServiceImpl implements IReportVodService{
	
	private static final Logger logger = Logger.getLogger(ReportVodServiceImpl.class);

	@Resource
	private VodAreaNowReportMapper rReportMapper;
	@Resource
	private BossUserInfoMapper bossUserInfoMapper;
	@Resource
	private TimerJobLogMapper timerJobLogMapper;
	@Resource
	private SysCodeMapper sysCodeMapper;
	
//	@Resource
//	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	synchronized public void testTask(){
		System.out.println("==============testTask in:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		logger.info("==============testTask in:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	}
	
	public PageResult findVodAreaNow(VodAreaCondition condition, PageHelper ph) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
		if(condition.getRepDate1() != null && condition.getRepDate2() != null) {
			if(df.format(condition.getRepDate1()).compareTo(df.format(condition.getRepDate2()))<0){
				List<String> queryMonths = new ArrayList();
				Date tmpa = (Date)condition.getRepDate1().clone();
				queryMonths.add(df.format(tmpa));
				while (!df.format(tmpa).equals(df.format(condition.getRepDate2()))){
					tmpa = DateFunctions.addCalendarFeild(tmpa, Calendar.MONTH, 1);
					queryMonths.add(df.format(tmpa));
				}
				if(!queryMonths.isEmpty()){
					String[] sa = new String[queryMonths.size()];
					for(int i=0; i< queryMonths.size(); i++){
						sa[i] = queryMonths.get(i);
					}
					condition.setQueryMonths(sa);
				}
			} else {
				condition.setQueryMonth(df.format(condition.getRepDate1()));
			}
		}

		List<VodArea> ls1 = rReportMapper.countActiveUsers(condition);
		
		List<VodArea> ls2 = rReportMapper.countOnlineBookedUsers(condition);
		List<VodArea> ls3 = rReportMapper.countOnlineUnbookUsers(condition);
		
		List<VodArea> ls4 = rReportMapper.countOfflineBookedUsers(condition);
		List<VodArea> ls5 = rReportMapper.countOfflineUnbookUsers(condition);
		
		List<VodArea> ls6 = rReportMapper.countInactiveOnlineUser(condition);
		List<VodArea> ls7 = rReportMapper.countInactiveOfflineUsers(condition);
		
		List<VodArea> ls0 = new ArrayList();
		for(VodArea vx : ls1){
			ls0.add((VodArea)vx.clone());
		}
		ls0 = combineVodAreaList(ls0,ls2);
		ls0 = combineVodAreaList(ls0,ls3);
		ls0 = combineVodAreaList(ls0,ls4);
		ls0 = combineVodAreaList(ls0,ls5);
		ls0 = combineVodAreaList(ls0,ls6);
		ls0 = combineVodAreaList(ls0,ls7);
		
		PageResult pr = new PageResult();
		pr.setRows(ls0);
		pr.setTotal(ls0.size());
		
		for(VodArea v : ls0){
			v.setCondition((VodAreaCondition)condition.clone());
		}
		return pr;
	}

	public PageResult findVodAreaDetail(VodAreaCondition vv, PageHelper ph) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
		if(vv.getActiveDate1() != null && vv.getActiveDate2() != null){
			List<String> queryMonths = new ArrayList();
			Date tmpa = (Date)vv.getActiveDate1().clone();
			queryMonths.add(df.format(tmpa));
			if(df.format(vv.getActiveDate1()).compareTo(df.format(vv.getActiveDate2()))<0){
				while (!df.format(tmpa).equals(df.format(vv.getActiveDate2()))){
					tmpa = DateFunctions.addCalendarFeild(tmpa, Calendar.MONTH, 1);
					queryMonths.add(df.format(tmpa));
				}
			}
			if(!queryMonths.isEmpty()){
				String[] sa = new String[queryMonths.size()];
				for(int i=0; i< queryMonths.size(); i++){
					sa[i] = queryMonths.get(i);
				}
				vv.setQueryMonths(sa);
			}
		}
		
		List<VodArea> ls = null;
		PageResult pr = new PageResult();
		if(ph == null){
			ls = bossUserInfoMapper.findVodAreaDetail(vv);
			pr.setRows(ls);
			pr.setTotal(ls.size());
		} else {
			int a = bossUserInfoMapper.findVodAreaDetailCount(vv);
			vv.setFirstResult((ph.getPage()-1)*ph.getRows());
			vv.setMaxResults(ph.getRows());
			ls = bossUserInfoMapper.findVodAreaDetail(vv);
			pr.setRows(ls);
			pr.setTotal(a);
		}
		return pr;
	}
	
	public PageResult findVodAreaActives(VodAreaCondition vv, PageHelper ph) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
		if(vv.getActiveDate1() != null && vv.getActiveDate2() != null){
			List<String> queryMonths = new ArrayList();
			Date tmpa = (Date)vv.getActiveDate1().clone();
			queryMonths.add(df.format(tmpa));
			if(df.format(vv.getActiveDate1()).compareTo(df.format(vv.getActiveDate2()))<0){
				while (!df.format(tmpa).equals(df.format(vv.getActiveDate2()))){
					tmpa = DateFunctions.addCalendarFeild(tmpa, Calendar.MONTH, 1);
					queryMonths.add(df.format(tmpa));
				}
			}
			if(!queryMonths.isEmpty()){
				String[] sa = new String[queryMonths.size()];
				for(int i=0; i< queryMonths.size(); i++){
					sa[i] = queryMonths.get(i);
				}
				vv.setQueryMonths(sa);
			}
		}
		
		List<AreaMonthReport> ls = null;
		PageResult pr = new PageResult();
		if(ph == null){
			ls = rReportMapper.findVodAreaActiveUsers(vv);
			pr.setRows(ls);
			pr.setTotal(ls.size());
		} else {
			ls = rReportMapper.findVodAreaActiveUsers(vv, new RowBounds(ph.getPage(), ph.getRows()));
			Page<VodArea> pp = ((Page) ls);
			pr.setRows(pp.getResult());
			pr.setTotal(pp.getTotal());
		}
		for(AreaMonthReport v : ls){
			v.setCondition((VodAreaCondition)vv.clone());
		}
		return pr;
	}
	
	
	private List<VodArea> combineVodAreaList(List<VodArea> ls0,List<VodArea> lsx){
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
	
//	以下查报表里的用户清单:======================================
	public PageResult getUserInfoDetail(VodAreaCondition vv, PageHelper ph,String getWhat){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
		SimpleDateFormat _df = new SimpleDateFormat("yyyy-MM");
		if(IsCustomeruserReport(getWhat)){
			if(vv.getRepDate()!=null){
				vv.setRepDate2(vv.getRepDate());
			}
			Calendar cd = Calendar.getInstance();
			cd.setTime(vv.getRepDate2());
			cd.set(Calendar.DATE, 1);
			if(vv.getReportRange().equals("season")){
				cd.add(Calendar.MONTH, -2);
			}
			if(vv.getReportRange().equals("year")){
				cd.set(Calendar.MONTH, 1);
			}
			vv.setRepDate1(cd.getTime());
		}
		if(vv.getRepDate() != null){
			vv.setQueryMonth(df.format(vv.getRepDate()));
		}
		if(vv.getRepDate1() != null && vv.getRepDate2() == null){
			vv.setRepDate(vv.getRepDate1());
			vv.setQueryMonth(df.format(vv.getRepDate1()));
		}
		if(vv.getRepDate1() == null && vv.getRepDate2() == null){
			if(vv.getRepDate() != null){
				vv.setRepDate1(vv.getRepDate());
				vv.setRepDate2(vv.getRepDate());
			} else if(vv.getQueryMonth() != null){	//月报
				try {
					if(vv.getQueryMonth().indexOf("-")>0){
						vv.setRepDate1(_df.parse(vv.getQueryMonth()));
						vv.setQueryMonth(df.format(vv.getRepDate1()));
					} else {
						vv.setRepDate1(df.parse(vv.getQueryMonth()));
					}
					vv.setRepDate2(DateFunctions.getMonthLastDay(vv.getRepDate1()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		//实时查询
		if(vv.getRepDate1() != null && vv.getRepDate2() != null) {
			if(DateFunctions.dateCompare(vv.getRepDate1(), vv.getRepDate2())==0){
				vv.setRepDate(vv.getRepDate1());
			} else if(df.format(vv.getRepDate1()).equals(df.format(vv.getRepDate2()))){
				vv.setQueryMonth(df.format(vv.getRepDate1()));
			} else if(df.format(vv.getRepDate1()).compareTo(df.format(vv.getRepDate2()))<0){
				List<String> queryMonths = new ArrayList();
				Date tmpa = (Date)vv.getRepDate1().clone();
				queryMonths.add(df.format(tmpa));
				while (!df.format(tmpa).equals(df.format(vv.getRepDate2()))){
					tmpa = DateFunctions.addCalendarFeild(tmpa, Calendar.MONTH, 1);
					queryMonths.add(df.format(tmpa));
				}
				if(!queryMonths.isEmpty()){
					String[] sa = new String[queryMonths.size()];
					for(int i=0; i< queryMonths.size(); i++){
						sa[i] = queryMonths.get(i);
					}
					vv.setQueryMonths(sa);
				}
			}
		} 
		if(vv.getActiveDate1() != null && vv.getActiveDate2() != null){
			List<String> queryMonths = new ArrayList();
			Date tmpa = (Date)vv.getActiveDate1().clone();
			queryMonths.add(df.format(tmpa));
			if(df.format(vv.getActiveDate1()).equals(df.format(vv.getActiveDate2()))){
				vv.setQueryMonth(df.format(vv.getActiveDate1()));
			} else if(df.format(vv.getActiveDate1()).compareTo(df.format(vv.getActiveDate2()))<0){
				while (!df.format(tmpa).equals(df.format(vv.getActiveDate2()))){
					tmpa = DateFunctions.addCalendarFeild(tmpa, Calendar.MONTH, 1);
					queryMonths.add(df.format(tmpa));
				}
			}
			if(!queryMonths.isEmpty()){
				String[] sa = new String[queryMonths.size()];
				for(int i=0; i< queryMonths.size(); i++){
					sa[i] = queryMonths.get(i);
				}
				vv.setQueryMonths(sa);
			}
		}

		logger.info("getWhat="+getWhat);
		try {	//根据getWhat自动映射为方法调用
			if(ph != null) {
				Method method = bossUserInfoMapper.getClass().getMethod(getWhat, new Class[] { VodAreaCondition.class, RowBounds.class });
				List ls = (List)method.invoke(bossUserInfoMapper, new Object[]{vv, new RowBounds(ph.getPage(), ph.getRows())});
				Page pp = ((Page) ls);
				PageResult pr = new PageResult();
				pr.setRows(pp.getResult());
				pr.setTotal(pp.getTotal());
				return pr;
			} else {
				Method method = bossUserInfoMapper.getClass().getMethod(getWhat, new Class[] { VodAreaCondition.class });
				List ls = (List)method.invoke(bossUserInfoMapper, new Object[]{vv});
				PageResult pr = new PageResult();
				pr.setRows(ls);
				pr.setTotal(ls.size());
				return pr;
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	private boolean IsCustomeruserReport(String getWhat){
		if(getWhat.equals("getOnlineVodUsers") || getWhat.equals("getOnlineBbUsers") 
				|| getWhat.equals("getOfflineVodUsers") || getWhat.equals("getOfflineBbUsers")){
			return true;
		}
		return false;
	}
	
	// 以下查报用户详细信息:======================================
	public BossUserInfo getUser(long userId) {
		BossUserInfo user = bossUserInfoMapper.getUser(userId);
		if (user != null) {
			return user;
		}
		return null;
	}
	
}
