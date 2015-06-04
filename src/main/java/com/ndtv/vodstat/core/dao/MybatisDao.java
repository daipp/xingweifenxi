package com.ndtv.vodstat.core.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

public interface MybatisDao {
	
	public <E> List<E> selectList(String statement, Object parameter, RowBounds rowBounds);
	
}

