package com.ndtv.vodstat.sys.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.ndtv.vodstat.common.pagemodel.PageHelper;
import com.ndtv.vodstat.common.pagemodel.PageResult;
import com.ndtv.vodstat.sys.entity.SysCode;

public interface ISysCodeService {

	public PageResult findPage(SysCode sysCode, PageHelper ph);
	
	public Serializable add(SysCode o);

	public void delete(Serializable id);

	public void update(SysCode o);

	public SysCode get(Serializable id);
	
	
	/**
	 * 获取指定类型的系统代码
	 * @param typeId
	 * @param status -1表示不限；1表示正常；0表示不正常
	 * @return
	 */
	public List<SysCode> getSysCodeList(long typeId,int status);
	
	/**
	 * 获取指定类型的系统代码
	 * @param typeId
	 * @param status -1表示不限；1表示正常；0表示不正常
	 * @return
	 */
	public Map getSysCodeMap(long typeId,int status);
	
	/**
	 * 获取系统代码
	 * @param codeId
	 * @return
	 */
	public SysCode getSysCode(long codeId);
}
