package com.ndtv.vodstat.report.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ndtv.vodstat.common.pagemodel.SessionInfo;
import com.ndtv.vodstat.common.util.ConfigUtil;
import com.ndtv.vodstat.report.model.AreaCondition;
import com.ndtv.vodstat.report.model.BossSysCode;
import com.ndtv.vodstat.report.service.BossService;

public class AuthorityHelper {

	public static final long customerTypeId = ConfigUtil.getLong("BOSS_CODETYPE_CUSTOMERTYPE");
	public static final long townId = ConfigUtil.getLong("BOSS_CODETYPE_TOWN");
	public static final long communityId = ConfigUtil.getLong("BOSS_CODETYPE_COMMUNITY");
	public static final long villageId = ConfigUtil.getLong("BOSS_CODETYPE_VILLAGE");
	public static final long userTypeId = ConfigUtil.getLong("BOSS_CODETYPE_USERTYPE");
	
	/**
	 * 限制查询条件
	 * @param request
	 * @param condition
	 */
	public static void limitVodAreaAuth(HttpServletRequest request, AreaCondition condition,BossService bossService){
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ConfigUtil.getSessionInfoName());
		condition.setQueryUserId(sessionInfo.getId());
		
		//如果sessionInfo里没有权限信息的话, 则set入
		if(sessionInfo.getBossSysCodes() == null
	    || sessionInfo.getBossSysCodes().get(customerTypeId) == null
	    || sessionInfo.getBossSysCodes().get(townId) == null
	    || sessionInfo.getBossSysCodes().get(communityId) == null
	    || sessionInfo.getBossSysCodes().get(villageId) == null){
			Map<Long,Collection> codeIdsMap = new HashMap();
			
			List<BossSysCode> customerTypes = bossService.getCodeListBySysUser(sessionInfo.getId(), customerTypeId, 1);
			codeIdsMap.put(customerTypeId, getCodeIdList(customerTypes));
			
			List<BossSysCode> towns = bossService.getCodeListBySysUser(sessionInfo.getId(), townId, 1);
			codeIdsMap.put(townId, getCodeIdList(towns));
			
			List<BossSysCode> communitys = bossService.getCodeListBySysUser(sessionInfo.getId(), communityId, 1);
			codeIdsMap.put(communityId, getCodeIdList(communitys));
			
			List<BossSysCode> villages = bossService.getCodeListBySysUser(sessionInfo.getId(), villageId, 1);
			codeIdsMap.put(villageId, getCodeIdList(villages));
			
			sessionInfo.setBossSysCodes(codeIdsMap);
		}
		Map<Long,Collection> codeIdsMap = sessionInfo.getBossSysCodes();

		//限制不为空
		if(!codeIdsMap.get(customerTypeId).isEmpty()){
			//条件为空
			if(condition.getCustomerTypeIds() == null || condition.getCustomerTypeIds().length == 0){
				Collection<Long> c = codeIdsMap.get(customerTypeId); 
				Long[] a = c.toArray(new Long[0]);
				condition.setCustomerTypeIds(a);
			} else {	//条件不为空
				List<Long> a = Arrays.asList(longArrayToLongArray(condition.getCustomerTypeIds()));
				a.retainAll(codeIdsMap.get(customerTypeId));
				condition.setCustomerTypeIds(a.toArray(new Long[0]));
			}
		}
		
		//限制不为空
		if(!codeIdsMap.get(townId).isEmpty()){
			//条件为空
			if(condition.getTownIds() == null || condition.getTownIds().length == 0){
				Collection<Long> c = codeIdsMap.get(townId); 
				Long[] a = c.toArray(new Long[0]);
				condition.setTownIds(a);
			} else {	//条件不为空
				List<Long> a = Arrays.asList(longArrayToLongArray(condition.getTownIds()));
				a.retainAll(codeIdsMap.get(townId));
				condition.setTownIds(a.toArray(new Long[0]));
			}
		}

		//限制不为空
		if(!codeIdsMap.get(communityId).isEmpty()){
			//条件为空
			if(condition.getCommunityIds() == null || condition.getCommunityIds().length == 0){
				Collection<Long> c = codeIdsMap.get(communityId); 
				Long[] a = c.toArray(new Long[0]);
				condition.setCommunityIds(a);
			} else {	//条件不为空
				List<Long> a = Arrays.asList(longArrayToLongArray(condition.getCommunityIds()));
				a.retainAll(codeIdsMap.get(communityId));
				condition.setCommunityIds(a.toArray(new Long[0]));
			}
		}

		//限制不为空
		if(!codeIdsMap.get(villageId).isEmpty()){
			//条件为空
			if(condition.getVillageIds() == null || condition.getVillageIds().length == 0){
				Collection<Long> c = codeIdsMap.get(villageId); 
				Long[] a = c.toArray(new Long[0]);
				condition.setVillageIds(a);
			} else {	//条件不为空
				List<Long> a = Arrays.asList(longArrayToLongArray(condition.getVillageIds()));
				a.retainAll(codeIdsMap.get(villageId));
				condition.setVillageIds(a.toArray(new Long[0]));
			}
		}
	}
	
	public static void initSelectionAttribute(HttpServletRequest request, BossService bossService ){
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ConfigUtil.getSessionInfoName());
		
		List<BossSysCode> customerTypes = bossService.getCodeListBySysUser(sessionInfo.getId(), customerTypeId, 1);
		List<BossSysCode> towns = bossService.getCodeListBySysUser(sessionInfo.getId(), townId, 1);
		List<BossSysCode> communitys = bossService.getCodeListBySysUser(sessionInfo.getId(), communityId, 1);
		List<BossSysCode> villages = bossService.getCodeListBySysUser(sessionInfo.getId(), villageId, 1);
		
		if(customerTypes.isEmpty()){
			customerTypes = bossService.getCodeList(customerTypeId, 1);
		}
		if(towns.isEmpty()){
			towns = bossService.getCodeList(townId, 1);
		}
		if(communitys.isEmpty()){
			communitys = bossService.getCodeList(communityId, 1);
		}
		if(villages.isEmpty()){
			villages = bossService.getCodeList(villageId, 1);
		}

		request.setAttribute("customerTypes", customerTypes);
		request.setAttribute("towns", towns);
		request.setAttribute("communitys", communitys);
		request.setAttribute("villages", villages);
	}
	
	private static Long[] longArrayToLongArray(long[] a){
		Long[] b = new Long[a.length];
		for(int i=0;i<a.length;i++){
			b[i]=a[i];
		}
		return b;
	}
	
	private static List<Long> getCodeIdList(List<BossSysCode> codes){
		List<Long> codeIds = new ArrayList();
		if(codes!=null){
			for(BossSysCode c : codes){
				codeIds.add(c.getCodeId());
			}
		}
		return codeIds;
	}
	
	
	
}
