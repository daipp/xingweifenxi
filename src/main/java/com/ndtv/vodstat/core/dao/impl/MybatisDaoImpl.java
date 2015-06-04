package com.ndtv.vodstat.core.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import com.ndtv.vodstat.core.dao.MybatisDao;

@Repository
public class MybatisDaoImpl implements MybatisDao{

	//@Resource
	private SqlSessionFactory sqlSessionFactory;

	public <E> List<E> selectList(String statement, Object parameter, RowBounds rowBounds){
		return getSqlSession().selectList(statement, parameter, rowBounds);
	}
	
	public SqlSession getSqlSession() {
		return sqlSessionFactory.openSession();
	}
}
