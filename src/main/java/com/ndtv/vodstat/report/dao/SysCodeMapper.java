package com.ndtv.vodstat.report.dao;

import org.apache.ibatis.annotations.Param;

import com.ndtv.vodstat.sys.entity.SysCode;

public interface SysCodeMapper extends MybatisSuperMapper {

	/**
	 * 查询单个系统代码
	 * @param codeId
	 * @return
	 */
	public SysCode get(@Param("codeid")Long id);
	

	/**
	 * 修改系统代码
	 * @param codeId
	 * @return
	 */
	public void update(SysCode code);
	
}
