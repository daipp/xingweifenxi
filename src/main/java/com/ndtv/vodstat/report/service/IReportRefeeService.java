package com.ndtv.vodstat.report.service;

import com.ndtv.vodstat.common.pagemodel.PageHelper;
import com.ndtv.vodstat.common.pagemodel.PageResult;
import com.ndtv.vodstat.report.model.VodAreaCondition;

public interface IReportRefeeService {

public static final String IMP_BEAN = "ReportRefeeServiceImpl";
	
	public PageResult findBooks(VodAreaCondition vv, PageHelper ph);
	
	public PageResult findExpires(VodAreaCondition vv, PageHelper ph);
}
