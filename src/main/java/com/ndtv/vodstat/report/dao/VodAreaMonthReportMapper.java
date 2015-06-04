package com.ndtv.vodstat.report.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.ndtv.vodstat.report.model.VodArea;
import com.ndtv.vodstat.report.model.VodAreaCondition;

public interface VodAreaMonthReportMapper extends MybatisSuperMapper {
	
	public List<VodArea> findVodAreaM(VodAreaCondition v);
	public List<VodArea> findVodAreaM(VodAreaCondition v,RowBounds rowBounds);
	//提取月报表数据
	public List<VodArea> getReportVodAreaM(Date repDate);
	public void deleteReportVodAreaM(Date repDate);
	//以下生成月报表:
	public void insertReportVodAreaM(Date repDate);
	//活跃用户
	public void updateReportVodAreaM_ActiveUsers(String repDateMonthDesc);
	//登录用户(在线)
	public void updateReportVodAreaM_OnlineUsersBooked(@Param("repDate")Date repDate,@Param("repDateMonthDesc")String repDateMonthDesc);
	//登录用户(离线)
	public void updateReportVodAreaM_OnlineUsersUnbook(@Param("repDate")Date repDate,@Param("repDateMonthDesc")String repDateMonthDesc);
	//未登录用户(在线)
	public void updateReportVodAreaM_OfflineUsersBooked(@Param("repDate")Date repDate,@Param("repDateMonthDesc")String repDateMonthDesc);
	//未登录用户(离线)
	public void updateReportVodAreaM_OfflineUsersUnbook(@Param("repDate")Date repDate,@Param("repDateMonthDesc")String repDateMonthDesc);
	//不活跃用户(登录过)
	public void updateReportVodAreaM_InactiveUsersOnline(@Param("repDate")Date repDate,@Param("repDateMonthDesc")String repDateMonthDesc);
	//不活跃用户(未登录)
	public void updateReportVodAreaM_InactiveUsersOffline(@Param("repDate")Date repDate,@Param("repDateMonthDesc")String repDateMonthDesc);
	
}
