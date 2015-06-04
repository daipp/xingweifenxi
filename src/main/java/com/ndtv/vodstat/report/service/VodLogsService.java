package com.ndtv.vodstat.report.service;

import java.util.List;

import com.ndtv.vodstat.common.pagemodel.PageHelper;
import com.ndtv.vodstat.common.pagemodel.PageResult;
import com.ndtv.vodstat.report.model.EpgLog;
import com.ndtv.vodstat.report.model.WasunLog;


public interface VodLogsService {
	
	public static final String IMP_BEAN = "VodLogsServiceImpl";

	public PageResult getSelectEpglog(EpgLog e,PageHelper ph);
	
	public PageResult getSelectWasunlog(WasunLog e,PageHelper ph);
	

	public List getCatergoryFrequency(WasunLog w);
	
	public List getCatergoryDuration(WasunLog w);
	
	public List getEverydayFrequency(WasunLog w);
	
	public List getEverydayDuration(WasunLog w);
}
