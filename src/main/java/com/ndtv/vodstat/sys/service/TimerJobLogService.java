package com.ndtv.vodstat.sys.service;

import java.util.Date;

import com.ndtv.vodstat.common.pagemodel.PageHelper;
import com.ndtv.vodstat.common.pagemodel.PageResult;
import com.ndtv.vodstat.sys.entity.TimerJobLog;

public interface TimerJobLogService {

	public PageResult findPage(TimerJobLog condition,Date date1,Date date2, PageHelper ph);
}
