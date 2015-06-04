package com.ndtv.vodstat.report.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ndtv.vodstat.report.model.BossSysCode;

public interface BossSysCodeMapper extends MybatisSuperMapper {

	/**
	 * 查询单个boss系统代码
	 * @param codeId
	 * @return
	 */
	public BossSysCode getBossSysCode(@Param("codeid")Long codeId,@Param("typeid")Long typeId,@Param("codename")String codeName);
	
	/**
	 * 查询单个类别下的所有boss系统代码
	 * @param typeId
	 * @param state
	 * @return
	 */
	public List<BossSysCode> getCodeList(@Param("typeid")Long typeId,@Param("state")Integer state);

	/**
	 * 查询与指定boss系统代码有关联的、对应类别下的所有boss系统代码
	 * @param codeId
	 * @param typeId
	 * @param state
	 * @return
	 */
	public List<BossSysCode> getCodeListByRelation(@Param("codeid")Long codeId,
			@Param("typeid")Long typeId,
			@Param("state")Integer state);
	
	/**
	 * 查询指定系统操作员有关联的、对应类别下的所有boss系统代码
	 * @param userId
	 * @param typeId
	 * @param state
	 * @return
	 */
	public List<BossSysCode> getCodeListBySysUser(@Param("userid")Long userId,
			@Param("typeid")Long typeId,
			@Param("state")Integer state);

	/**
	 * 查询指定系统操作员无关联的、对应类别下的所有boss系统代码
	 * @param userId
	 * @param typeId
	 * @param state
	 * @return
	 */
	public List<BossSysCode> getCodeListBySysUserEx(@Param("userid")Long userId,
			@Param("typeid")Long typeId,
			@Param("state")Integer state);

	/**
	 * 清除指定系统操作员的与对应类别有关联的所有boss系统代码
	 * @param userId
	 * @param typeId
	 */
	public void cleanSysUserBossSysCode(@Param("userid")Long userId,
			@Param("typeid")Long typeId);

	/**
	 * 增加指定系统操作员与BOSS系统代码的关联
	 * @param userId
	 * @param codeIds
	 */
	public void addSysUserBossSysCode(@Param("userid")Long userId,
			@Param("codeids")List<Long> codeIds);
	
}
