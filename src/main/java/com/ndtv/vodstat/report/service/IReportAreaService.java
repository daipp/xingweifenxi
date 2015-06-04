package com.ndtv.vodstat.report.service;

import java.util.Date;

import com.ndtv.vodstat.common.pagemodel.PageHelper;
import com.ndtv.vodstat.common.pagemodel.PageResult;
import com.ndtv.vodstat.report.model.AreaCondition;


public interface IReportAreaService {
	
	public static final String IMP_BEAN = "ReportAreaServiceImpl";
	
	public PageResult findAreaDateReport(AreaCondition vv, PageHelper ph);
	public PageResult findAreaMonthReport(AreaCondition vv, PageHelper ph);
	
	public void deleteAreaDateReport(Date repDate);
	public void deleteAreaMonthReport(Date repDate);
	
	public void genAreaReport();

	public void updateAreaDateReport(Date repDate);
	public void updateAreaMonthReport(Date repDate);
	
	public PageResult getAreaMonthReport_userList(AreaCondition vv, PageHelper ph,String getWhat);
	public PageResult getAreaDateReport_userList(AreaCondition vv, PageHelper ph,String getWhat);
	
}
