package com.ndtv.vodstat.report.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.ndtv.vodstat.report.model.AreaCondition;
import com.ndtv.vodstat.report.model.AreaDateReport;
import com.ndtv.vodstat.report.model.TopResult;
import com.ndtv.vodstat.report.model.VodAreaCondition;

public interface ReportHotMapper extends MybatisSuperMapper{
	
	public List<TopResult> findHotUserByTime(VodAreaCondition v);
	public List<TopResult> findHotUserByTime(VodAreaCondition v,RowBounds rowBounds);	
	public List<TopResult> findHotUserByTimes(VodAreaCondition v);
	public List<TopResult> findHotUserByTimes(VodAreaCondition v,RowBounds rowBounds);
	
	public List<TopResult> findHotCommunityByTime(VodAreaCondition v);
	public List<TopResult> findHotCommunityByTimes(VodAreaCondition v);
	public List<TopResult> findHotCommunityByTime(VodAreaCondition v,RowBounds rowBounds);
	public List<TopResult> findHotCommunityByTimes(VodAreaCondition v,RowBounds rowBounds);
	
	
	public List<TopResult> findHotProgramsByTime(VodAreaCondition vv);
	public List<TopResult> findHotProgramsByTimes(VodAreaCondition vv);
	public List<TopResult> findHotTypesByTime(VodAreaCondition vv);
	public List<TopResult> findHotTypesByTimes(VodAreaCondition vv);
	
	public List<AreaDateReport> findManagerViewData(AreaCondition vv);
	public List<AreaDateReport> findManagerViewPie(AreaCondition vv);
	
}
