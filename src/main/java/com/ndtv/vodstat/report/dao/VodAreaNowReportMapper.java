package com.ndtv.vodstat.report.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.ndtv.vodstat.report.model.AreaMonthReport;
import com.ndtv.vodstat.report.model.VodArea;
import com.ndtv.vodstat.report.model.VodAreaCondition;

public interface VodAreaNowReportMapper extends MybatisSuperMapper {

	public List<AreaMonthReport> findVodAreaActiveUsers(VodAreaCondition v);
	public List<AreaMonthReport> findVodAreaActiveUsers(VodAreaCondition v, RowBounds rowBounds);
	
	public List<VodArea> countActiveUsers(VodAreaCondition v);
	
	public List<VodArea> countOnlineBookedUsers(VodAreaCondition v);
	public List<VodArea> countOnlineUnbookUsers(VodAreaCondition v);

	public List<VodArea> countOfflineBookedUsers(VodAreaCondition v);
	public List<VodArea> countOfflineUnbookUsers(VodAreaCondition v);

	public List<VodArea> countInactiveOnlineUser(VodAreaCondition v);
	public List<VodArea> countInactiveOfflineUsers(VodAreaCondition v);
	
}
