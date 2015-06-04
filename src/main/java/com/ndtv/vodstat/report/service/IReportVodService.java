package com.ndtv.vodstat.report.service;

import com.ndtv.vodstat.common.pagemodel.PageHelper;
import com.ndtv.vodstat.common.pagemodel.PageResult;
import com.ndtv.vodstat.report.model.BossUserInfo;
import com.ndtv.vodstat.report.model.VodAreaCondition;


public interface IReportVodService {
	
	public static final String IMP_BEAN = "ReportVodServiceImpl";
	
	
	public PageResult findVodAreaNow(VodAreaCondition vv, PageHelper ph);
	public PageResult findVodAreaActives(VodAreaCondition vv, PageHelper ph);
	public PageResult findVodAreaDetail(VodAreaCondition vv, PageHelper ph);

//	public PageResult findAreaDateReport(AreaCondition vv, PageHelper ph);
//	public PageResult findAreaMonthReport(AreaCondition vv, PageHelper ph);
//	public void killReportVodAreaD(Date repDate);
//	public void killReportVodAreaM(Date repDate);
//	public void updateReportVodAreaForActiveD(Date repDate);
//	public void updateAreaMonthReport(Date repDate);
//	public void genReportVodArea();

	public PageResult getUserInfoDetail(VodAreaCondition vv, PageHelper ph, String getWhat);
	
	public BossUserInfo getUser(long userId);
	
//	public PageResult getActiveUsersD(VodArea vv, PageHelper ph);
//	public PageResult getOnlineUsersD(VodArea vv, PageHelper ph);
//	public PageResult getBookUsersD(VodArea vv, PageHelper ph);
//	public PageResult getVodUsersD(VodArea vv, PageHelper ph);
//	public PageResult getStbUsersD(VodArea vv, PageHelper ph);
//	public PageResult getCustomersD(VodArea vv, PageHelper ph);
	
	public void testTask();

}
