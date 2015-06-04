package com.ndtv.vodstat.report.service;

import java.util.List;

import com.ndtv.vodstat.common.pagemodel.PageHelper;
import com.ndtv.vodstat.common.pagemodel.PageResult;
import com.ndtv.vodstat.report.model.AreaCondition;
import com.ndtv.vodstat.report.model.AreaDateReport;
import com.ndtv.vodstat.report.model.TopResult;
import com.ndtv.vodstat.report.model.VodAreaCondition;

public interface IReportHotService {


	public PageResult findHotUserByTime(VodAreaCondition vv, PageHelper ph);
	public PageResult findHotUserByTimes(VodAreaCondition vv, PageHelper ph);
	
	public PageResult findHotCommunityByTime(VodAreaCondition vv, PageHelper ph);
	public PageResult findHotCommunityByTimes(VodAreaCondition vv, PageHelper ph);
	
	public PageResult findManagerViewData(AreaCondition vv, PageHelper ph);
	public List<AreaDateReport> getManagerViewLine(AreaCondition vv);
	public List<AreaDateReport> getManagerViewPie(AreaCondition vv);
	
	public List<TopResult> findHotProgramsByTime(VodAreaCondition vv);
	public List<TopResult> findHotProgramsByTimes(VodAreaCondition vv);
	public List<TopResult> findHotTypesByTime(VodAreaCondition vv);
	public List<TopResult> findHotTypesByTimes(VodAreaCondition vv);
}
