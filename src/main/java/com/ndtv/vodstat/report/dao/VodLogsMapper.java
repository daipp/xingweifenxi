package com.ndtv.vodstat.report.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.ndtv.vodstat.report.model.EpgLog;
import com.ndtv.vodstat.report.model.WasunLog;

public interface VodLogsMapper extends MybatisSuperMapper {
	
	
	//EPG-Log:
	public String getLastTabOfEpglog();
	
	public Date getLastLogtimeOfEpglog(String tabName);
	
	//查询登录记录
	public List<EpgLog> getSelectEpglog(EpgLog e, RowBounds rowBounds);
	
	public void batchInsertEpgLog(@Param("theMonth")String theMonth,@Param("logs")List<EpgLog> logs);

	public void updateEpglogByResno(@Param("theMonth")String theMonth,@Param("logDate")Date logDate);
	
	public void updateEpglogByShortSim(@Param("theMonth")String theMonth,@Param("logDate")Date logDate);
	
	public void batchInsertEpgLogA(@Param("theMonth")String theMonth,@Param("logDate")Date logDate);

	
	//WASU-Log:
	//查询点播记录
	public List<WasunLog> getSelectWasunlog(WasunLog e, RowBounds rowBounds);
	
	//统计分类频繁度(按次数)
	public List<WasunLog> getCatergoryFrequency(WasunLog e);
	
	//统计分类持续度(按时长)
	public List<WasunLog> getCatergoryDuration(WasunLog e);
	
	//统计每天频繁度(按次数)
	public List<WasunLog> getEverydayFrequency(WasunLog e);
	
	//统计每天持续度(按时长)
	public List<WasunLog> getEverydayDuration(WasunLog e);
	
	
}
