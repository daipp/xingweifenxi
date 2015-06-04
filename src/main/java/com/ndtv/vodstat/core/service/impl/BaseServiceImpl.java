package com.ndtv.vodstat.core.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;

import com.ndtv.vodstat.common.pagemodel.PageResult;
import com.ndtv.vodstat.core.dao.IBaseDao;
import com.ndtv.vodstat.core.service.IBaseService;

@Service
public class BaseServiceImpl<T> implements IBaseService<T> {

	@Resource
	IBaseDao baseDao;
	
	@Override
	public void add(T obj) {
		baseDao.save(obj);
	}

	@Override
	public void update(T obj) {
		baseDao.update(obj);
	}

	@Override
	public void save(T obj) {
		baseDao.saveOrUpdate(obj);
	}

	@Override
	public void delete(T obj) {
		baseDao.delete(obj);
	}
	
	@Override
	public void delete(Class<T> clz, Serializable id) {
		baseDao.delete(clz, id);
	}

	@Override
	public T get(Class<T> clz, Serializable id) {
		return (T)baseDao.get(clz, id);
	}

	@Override
	public List find(DetachedCriteria criteria) {
		
		return baseDao.findByCriteria(criteria);
	}

	@Override
	public PageResult findPage(DetachedCriteria detachedCriteria, int start,
			int pageSize) {
		return baseDao.findByCriteria(detachedCriteria, start, pageSize);
	}

	@Override
	public List find(String hql, Map params, int page, int rows) {
		return baseDao.find(hql, params, page, rows);
	}

	@Override
	public Long count(String hql, Map params) {
		return baseDao.count(hql, params);
	}

}
