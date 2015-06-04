package com.ndtv.vodstat.core.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.ndtv.vodstat.common.pagemodel.PageResult;

public interface IBaseService<T> {
	
	/**
	 * 增加
	 * @param obj
	 * @return
	 */
	public void add(T obj);
	
	/**
	 * 修改
	 * @param obj
	 * @return
	 */
	public void update(T obj);	
	
	/**
	 * 增加，修改
	 * @param obj
	 * @return
	 */
	public void save(T obj);
	
	/**
	 * 删除
	 * @param obj
	 * @return
	 */
	public void delete(T obj);	
	
	/**
	 * 删除
	 */
	public void delete(Class<T> clz, Serializable id);
	
	/**
	 * 加载
	 * @param obj
	 * @return
	 */
	public T get(Class<T> clz, Serializable id);
	
	/**
	 * 查询所有
	 * @param criteria：离线criteria
	 * @return
	 */
	public List find(DetachedCriteria criteria);
	
	
	/**
	 * 分页查询
	 * @param criteria：离线criteria, 在具体的Action中构件好，处理好条件，传给service，传给dao
	 * @param start：起始下标
	 * @param pageSize：每页行数
	 * @return pageResult，result,一页数据,rowCount：总页数
	 */
	public PageResult findPage(DetachedCriteria detachedCriteria, int start, int pageSize);
	
	public List find(String hql, Map params, int page, int rows);
	
	public Long count(String hql, Map params);
}
