package com.ndtv.vodstat.report.dao;

import java.util.List;

import com.ndtv.vodstat.report.model.VodArea;
import com.ndtv.vodstat.report.model.VodAreaCondition;

public interface CustomerUserNowReportMapper extends MybatisSuperMapper {
	/**
	 * 点播立即开通
	 * @param v
	 * @return
	 */
	public List<VodArea> countVodOpenNowUsers(VodAreaCondition v);
	/**
	 * 点播延迟开通
	 * @param v
	 * @return
	 */
	public List<VodArea> countVodOpenDelayUsers(VodAreaCondition v);
	/**
	 * 点播未开通
	 * @param v
	 * @return
	 */
	public List<VodArea> countVodNotOpenUsers(VodAreaCondition v);
	/**
	 * 宽带立即开通
	 * @param v
	 * @return
	 */
	public List<VodArea> countbbOpenNowUsers(VodAreaCondition v);
	/**
	 * 宽带延迟开通
	 * @param v
	 * @return
	 */
	public List<VodArea> countbbOpenDelayUsers(VodAreaCondition v);
	/**
	 * 宽带未开通
	 * @param v
	 * @return
	 */
	public List<VodArea> countbbNotOpenUsers(VodAreaCondition v);

	
}
