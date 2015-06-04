package com.ndtv.vodstat.report.service;

import java.util.List;

import com.ndtv.vodstat.report.model.BossSysCode;

public interface BossService {
	
	public static final String IMP_BEAN = "BossServiceImpl";
	
	public BossSysCode getBossSysCode(Long codeId,Long typeId,String codeName) ;
	
	public List<BossSysCode> getCodeList(Long typeId, Integer state);

	public List<BossSysCode> getCodeListByRelation(Long codeId, Long typeId, Integer state);

	public List<BossSysCode> getCodeListBySysUser(Long userId, Long typeId, Integer state);

	public List<BossSysCode> getCodeListBySysUserEx(Long userId, Long typeId, Integer state);
	
	public void mergeSysUserBossSysCode(Long userId,Long[] typeIds,Long[] codeIds);
	
	public void synBossTables();
}
