package com.ndtv.vodstat.sys.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.ndtv.vodstat.common.pagemodel.PageHelper;
import com.ndtv.vodstat.common.pagemodel.PageResult;
import com.ndtv.vodstat.core.dao.IBaseDao;
import com.ndtv.vodstat.sys.entity.SysCode;
import com.ndtv.vodstat.sys.service.ISysCodeService;

@Service
public class SysCodeServiceImpl implements ISysCodeService {

	@Resource
	private IBaseDao<SysCode> sysCodeDao;
	
	@Override
	public List getSysCodeList(long typeId,int status) {
		DetachedCriteria criteria = DetachedCriteria.forClass(SysCode.class);
		criteria.add(Restrictions.eq("sysCodeType.id", typeId));
		if(status != -1){
			criteria.add(Restrictions.eq("status", status));
		}
		criteria.addOrder(Order.asc("codeName"));
		List<SysCode> ls = sysCodeDao.findByCriteria(criteria);
		return ls;
	}

	@Override
	public Map getSysCodeMap(long typeId,int status) {
		List<SysCode> ls = getSysCodeList(typeId,status);
		Map mp = new HashMap();
		for(SysCode s : ls){
			mp.put(s.getId(), s);
		}
		return mp;			
	}

	@Override
	public SysCode getSysCode(long codeId){
		SysCode sc = sysCodeDao.get(SysCode.class, codeId);
		return sc;
	}

	@Override
	public PageResult findPage(SysCode sysCode, PageHelper ph) {

		DetachedCriteria criteria = DetachedCriteria.forClass(SysCode.class);
		
		if(sysCode.getSysCodeType() != null && sysCode.getSysCodeType().getId()>=0 ){
			criteria.add(Restrictions.eq("sysCodeType.id", sysCode.getSysCodeType().getId()));
		}
		if(sysCode.getCodeName() != null && !"".equals(sysCode.getCodeName().trim())){
			criteria.add(Restrictions.or(
				Restrictions.like("codeContent", sysCode.getCodeName(), MatchMode.ANYWHERE),
				Restrictions.like("codeName", sysCode.getCodeName(), MatchMode.ANYWHERE)
			));
		}

		criteria.addOrder(Order.desc("status"));
		if(ph.getSort() != null && !"".equals(ph.getSort())){
			criteria.addOrder(Order.asc(ph.getSort()));
		}
		
		PageResult pr = sysCodeDao.findByCriteria(criteria, ph.getStartRow(),ph.getRows());
		List<SysCode> ls = pr.getRows();
		for (SysCode s : ls) {
			Hibernate.initialize(s.getSysCodeType());
			Hibernate.initialize(s.getUpdateUser());
		}
		
		return pr;
	}

	@Override
	public Serializable add(SysCode o) {
		Serializable a = sysCodeDao.save(o);
		return a;
	}

	@Override
	public void delete(Serializable id) {
		sysCodeDao.delete(SysCode.class, id);
	}

	@Override
	public void update(SysCode o) {
		sysCodeDao.update(o);
	}

	@Override
	public SysCode get(Serializable id) {
		return sysCodeDao.get(SysCode.class, id);
	}

}
