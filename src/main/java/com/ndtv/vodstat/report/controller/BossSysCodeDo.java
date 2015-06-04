package com.ndtv.vodstat.report.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ndtv.vodstat.common.pagemodel.Json;
import com.ndtv.vodstat.common.pagemodel.SessionInfo;
import com.ndtv.vodstat.common.util.ConfigUtil;
import com.ndtv.vodstat.core.controller.BaseController;
import com.ndtv.vodstat.report.model.BossSysCode;
import com.ndtv.vodstat.report.service.BossService;

@Controller
@RequestMapping("/boss/syscodeDo")
public class BossSysCodeDo extends BaseController {
	
	@Resource
	private BossService bossService;

	@RequestMapping("/getBossSysCode")
	@ResponseBody
	public BossSysCode getBossSysCode(Long codeId,Long typeId,String codeName) {
		return bossService.getBossSysCode(codeId,typeId,codeName);
	}
	
	@RequestMapping("/getCodeList")
	@ResponseBody
	public List getCodeList(HttpServletRequest request,long typeId,int state) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ConfigUtil.getSessionInfoName());
		List<BossSysCode> tmpls0 = bossService.getCodeListBySysUser(sessionInfo.getId(), typeId, state);
		if(tmpls0 != null && !tmpls0.isEmpty()){
			return tmpls0;
		}
		return bossService.getCodeList(typeId, state);
	}
	
	@RequestMapping("/getCodeRelation")
	@ResponseBody
	public List getCodeRelation(HttpServletRequest request,long codeId,long typeId,int state) {
		List<BossSysCode> tmpls = bossService.getCodeListByRelation(codeId, typeId, state);
		
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ConfigUtil.getSessionInfoName());
		List<BossSysCode> tmpls0 = bossService.getCodeListBySysUser(sessionInfo.getId(), typeId, state);
		if(tmpls0 != null && !tmpls0.isEmpty()){
			List<BossSysCode> rtnls = new ArrayList();
			for(BossSysCode bs : tmpls){
				for(BossSysCode b0 : tmpls0){
					if(bs.getCodeId() == b0.getCodeId()){
						rtnls.add(bs);
					}
				}
			}
			return rtnls;
		}
		return tmpls;
	}
	
	@RequestMapping("/getCodeListBySysUser")
	@ResponseBody
	public List getCodeListBySysUser(long userId,long typeId,int state) {
		return bossService.getCodeListBySysUser(userId, typeId, state);
	}
	
	@RequestMapping("/getCodeListBySysUserEx")
	@ResponseBody
	public List getCodeListBySysUserEx(long userId,long typeId,int state) {
		return bossService.getCodeListBySysUserEx(userId, typeId, state);
	}

	@RequestMapping("/mergeSysUserBossSysCode")
	@ResponseBody
	public Json mergeSysUserBossSysCode(Long userId,Long[] typeIds,Long[] codeIds) {
		bossService.mergeSysUserBossSysCode(userId, typeIds, codeIds);
		Json j = new Json();
		j.setSuccess(true);
		j.setMsg("更新成功！");
		return j;
	}
	
}
