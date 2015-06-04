package com.ndtv.vodstat.core.dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.ndtv.vodstat.common.pagemodel.PageResult;

/**
 * 基础数据库操作类
 * 
 * 其他DAO继承此类获取常用的数据库操作方法
 * 
 * @author 孙宇
 * 
 * @param <T>
 *            模型
 */
public interface IBaseDao<T> {

	/**
	 * 保存一个对象
	 * 
	 * @param o 对象
	 * @return 对象的ID
	 */
	public Serializable save(T o);

	/**
	 * 删除一个对象
	 * 
	 * @param o 对象
	 */
	public void delete(T o);
	
	public void delete(Class<T> clz, Serializable id);

	/**
	 * 更新一个对象
	 * 
	 * @param o 对象
	 */
	public void update(T o);

	/**
	 * 保存或更新一个对象
	 * 
	 * @param o 对象
	 */
	public void saveOrUpdate(T o);

	/**
	 * 通过主键获得对象
	 * 
	 * @param c 类名.class
	 * @param id 主键
	 * @return 对象
	 */
	public T get(Class<T> c, Serializable id);

	/**
	 * 通过HQL语句获取一个对象
	 * 
	 * @param hql HQL语句
	 * @return 对象
	 */
	public T get(String hql);

	/**
	 * 通过HQL语句获取一个对象
	 * 
	 * @param hql HQL语句
	 * @param params 参数
	 * @return 对象
	 */
	public T get(String hql, Map<String, Object> params);

	/**
	 * 获得对象列表
	 * 
	 * @param hql HQL语句
	 * @return List
	 */
	public List<T> find(String hql);

	/**
	 * 获得对象列表
	 * 
	 * @param hql HQL语句
	 * @param params 参数
	 * @return List
	 */
	public List<T> find(String hql, Map<String, Object> params);

	/**
	 * 获得分页后的对象列表
	 * 
	 * @param hql HQL语句
	 * @param page 要显示第几页
	 * @param rows 每页显示多少条
	 * @return List
	 */
	public List<T> find(String hql, int page, int rows);

	/**
	 * 获得分页后的对象列表
	 * 
	 * @param hql HQL语句
	 * @param params 参数
	 * @param page 要显示第几页
	 * @param rows 每页显示多少条
	 * @return List
	 */
	public List<T> find(String hql, Map<String, Object> params, int page, int rows);

	/**
	 * 统计数目
	 * 
	 * @param hql HQL语句(select count(*) from T)
	 * @return long
	 */
	public Long count(String hql);

	/**
	 * 统计数目
	 * 
	 * @param hql HQL语句(select count(*) from T where xx = :xx)
	 * @param params 参数
	 * @return long
	 */
	public Long count(String hql, Map<String, Object> params);

	/**
	 * 执行一条HQL语句
	 * 
	 * @param hql HQL语句
	 * @return 响应结果数目
	 */
	public int executeHql(String hql);

	/**
	 * 执行一条HQL语句
	 * 
	 * @param hql HQL语句
	 * @param params 参数
	 * @return 响应结果数目
	 */
	public int executeHql(String hql, Map<String, Object> params);

	/**
	 * 获得结果集
	 * 
	 * @param sql SQL语句
	 * @return 结果集
	 */
	public List<Object[]> findBySQL(String sql);

	/**
	 * 获得结果集
	 * 
	 * @param sql SQL语句
	 * @param page 要显示第几页
	 * @param rows 每页显示多少条
	 * @return 结果集
	 */
	public List<Object[]> findBySQL(String sql, int page, int rows);

	/**
	 * 获得结果集
	 * 
	 * @param sql SQL语句
	 * @param params 参数
	 * @return 结果集
	 */
	public List<Object[]> findBySQL(String sql, Map<String, Object> params);

	/**
	 * 获得结果集
	 * 
	 * @param sql SQL语句
	 * @param params 参数
	 * @param page 要显示第几页
	 * @param rows 每页显示多少条
	 * @return 结果集
	 */
	public List<Object[]> findBySQL(String sql, Map<String, Object> params, int page, int rows);

	/**
	 * 执行SQL语句
	 * 
	 * @param sql SQL语句
	 * @return 响应行数
	 */
	public int executeSQL(String sql);

	/**
	 * 执行SQL语句
	 * 
	 * @param sql SQL语句
	 * @param params 参数
	 * @return 响应行数
	 */
	public int executeSQL(String sql, Map<String, Object> params);

	/**
	 * 统计
	 * 
	 * @param sql SQL语句
	 * @return 数目
	 */
	public BigInteger countBySQL(String sql);

	/**
	 * 统计
	 * 
	 * @param sql SQL语句
	 * @param params 参数
	 * @return 数目
	 */
	public BigInteger countBySQL(String sql, Map<String, Object> params);

	/**
	 * 查询
	 * @param criteria
	 * @return
	 */
	public List findByCriteria(DetachedCriteria criteria);

	/**
	 * 分页查询
	 * @param detachedCriteria
	 * @param start
	 * @param pageSize
	 * @return
	 */
	public PageResult findByCriteria(DetachedCriteria detachedCriteria , int start, int pageSize);
}
