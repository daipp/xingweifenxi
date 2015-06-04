package com.ndtv.vodstat.report.service;

import java.util.Date;

import com.ndtv.vodstat.common.pagemodel.PageHelper;
import com.ndtv.vodstat.common.pagemodel.PageResult;
import com.ndtv.vodstat.report.model.VodAreaCondition;

public interface BossCustomerUserService {
	public static final String IMP_BEAN = "BossCustomerUserServiceImpl";
	
	public PageResult findReportBOSSCustomerUser(VodAreaCondition condition, PageHelper ph);
	
	public void genReport();
	
	public void killReport(String reportRange, Date repDate);
	
	public PageResult findCustomerUserNow(VodAreaCondition condition, PageHelper ph);
	
	public PageResult findConditionCustomer(VodAreaCondition condition, PageHelper ph);
}
