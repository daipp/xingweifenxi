package com.ndtv.vodstat.report.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.ndtv.vodstat.report.model.AreaMonthReport;
import com.ndtv.vodstat.report.model.VodArea;
import com.ndtv.vodstat.report.model.VodAreaCondition;

public interface VodReportRefeeMapper extends MybatisSuperMapper{
	
	
	public List<VodArea> countRefeeBookUsers(VodAreaCondition v);
	public List<VodArea> countRefeeBookUsers(VodAreaCondition v,RowBounds rowBounds);
	
	public List<VodArea> countRefeeExpiredUsers(VodAreaCondition v);
	public List<VodArea> countRefeeExpiredUsers(VodAreaCondition v,RowBounds rowBounds);
	
	
	
	
	
	public List<AreaMonthReport> countVodExpiredBook(VodAreaCondition v);
	public List<AreaMonthReport> countVodExpiredBook(VodAreaCondition v,RowBounds rowBounds);
	
	public List<AreaMonthReport> countVodExpiringBook(VodAreaCondition v);
	public List<AreaMonthReport> countVodExpiringBook(VodAreaCondition v,RowBounds rowBounds);
	
	public List<AreaMonthReport> countVodPrebook(VodAreaCondition v);
	public List<AreaMonthReport> countVodPrebook(VodAreaCondition v,RowBounds rowBounds);
	
	public List<AreaMonthReport> countBbExpiredBook(VodAreaCondition v);
	public List<AreaMonthReport> countBbExpiredBook(VodAreaCondition v,RowBounds rowBounds);
	
	public List<AreaMonthReport> countBbExpiringBook(VodAreaCondition v);
	public List<AreaMonthReport> countBbExpiringBook(VodAreaCondition v,RowBounds rowBounds);

	public List<AreaMonthReport> countBbPrebook(VodAreaCondition v);
	public List<AreaMonthReport> countBbPrebook(VodAreaCondition v,RowBounds rowBounds);
}
