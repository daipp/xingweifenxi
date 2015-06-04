package com.ndtv.vodstat.sys.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.ndtv.vodstat.common.pagemodel.PageHelper;
import com.ndtv.vodstat.common.pagemodel.PageResult;
import com.ndtv.vodstat.core.dao.IBaseDao;
import com.ndtv.vodstat.sys.entity.SysCode;
import com.ndtv.vodstat.sys.entity.TimerJobLog;
import com.ndtv.vodstat.sys.service.ISysCodeService;
import com.ndtv.vodstat.sys.service.TimerJobLogService;

@Service
public class TimerJobLogServiceImpl implements TimerJobLogService{
	
	@Resource
	private IBaseDao<TimerJobLog> timerJobLogDao;
	@Resource
	private ISysCodeService codeService;
	
	@Override
	public PageResult findPage(TimerJobLog condition,Date date1,Date date2, PageHelper ph) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TimerJobLog.class);
		
		if(condition.getJobId() != null && condition.getJobId() >= 0 ){
			criteria.add(Restrictions.eq("jobId", condition.getJobId()));
		}
		if(date1 != null){
			criteria.add(Restrictions.ge("crtime", date1));
		}
		if(date2 != null){
			criteria.add(Restrictions.le("crtime", date2));
		}
		if(ph.getSort() != null && !"".equals(ph.getSort())){
			criteria.addOrder(Order.asc(ph.getSort()));
		}
		PageResult pr = timerJobLogDao.findByCriteria(criteria, ph.getStartRow(),ph.getRows());
		return pr;
	}

}
