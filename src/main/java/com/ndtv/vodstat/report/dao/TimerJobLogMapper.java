package com.ndtv.vodstat.report.dao;

import com.ndtv.vodstat.sys.entity.TimerJobLog;

public interface TimerJobLogMapper extends MybatisSuperMapper {
	
	public void insert(TimerJobLog log);
	
}
