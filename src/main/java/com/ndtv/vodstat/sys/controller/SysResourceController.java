package com.ndtv.vodstat.sys.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ndtv.vodstat.common.pagemodel.Json;
import com.ndtv.vodstat.common.pagemodel.SessionInfo;
import com.ndtv.vodstat.common.pagemodel.Tree;
import com.ndtv.vodstat.common.util.ConfigUtil;
import com.ndtv.vodstat.core.controller.BaseController;
import com.ndtv.vodstat.sys.entity.SysCode;
import com.ndtv.vodstat.sys.entity.SysResource;
import com.ndtv.vodstat.sys.service.ISysCodeService;
import com.ndtv.vodstat.sys.service.ISysResourceService;


/**
 * 资源控制器
 * 
 */
@Controller
@RequestMapping("/resourceController")
public class SysResourceController extends BaseController {

	@Resource
	private ISysResourceService resourceService;

	@Resource
	private ISysCodeService sysCodeService;
	
	private static final long codeType_resourceType = ConfigUtil.getLong("codeType_resourceType");
	private static final long codeType_topMenu = ConfigUtil.getLong("codeType_topMenu");

	
	@RequestMapping("/listTopMenus")
	public List getTopMenus(){
		List<SysCode> tmpls = sysCodeService.getSysCodeList(ConfigUtil.getLong("codeType_topMenu"), SysCode.STATUS_VALID);
		
		List<String> topMenus = new ArrayList();
		for(SysCode sc : tmpls){
			topMenus.add(sc.getCodeContent());
		}
		return topMenus;
	}
	
	/**
	 * 获得资源树(资源类型为菜单类型)
	 * 
	 * 通过用户ID判断，他能看到的资源
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/tree")
	@ResponseBody
	public List<Tree> tree(HttpSession session,Long topMenuId) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		return resourceService.tree(sessionInfo,topMenuId);
	}

	/**
	 * 获得资源树(包括所有资源类型)
	 * 
	 * 通过用户ID判断，他能看到的资源
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/allTree")
	@ResponseBody
	public List<Tree> allTree(HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		return resourceService.allTree(sessionInfo);
	}

	/**
	 * 跳转到资源管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager() {
		return "/sys/resource";
	}

	/**
	 * 跳转到资源添加页面
	 * 
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		request.setAttribute("resourceTypeList", getResourceTypeList());
		request.setAttribute("topMenuList", getTopMenuList());
		return "/sys/resourceAdd";
	}
	
	private List getResourceTypeList(){
		return sysCodeService.getSysCodeList(codeType_resourceType, SysCode.STATUS_VALID);
	}
	private List getTopMenuList(){
		return sysCodeService.getSysCodeList(codeType_topMenu, SysCode.STATUS_VALID);
	}

	/**
	 * 添加资源
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(SysResource resource, HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		Json j = new Json();
		resourceService.add(resource, sessionInfo);
		j.setSuccess(true);
		j.setMsg("添加成功！");
		return j;
	}

	/**
	 * 跳转到资源编辑页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Long id) {
		request.setAttribute("resourceTypeList", getResourceTypeList());
		request.setAttribute("topMenuList", getTopMenuList());
		SysResource r = resourceService.get(id);
		request.setAttribute("resource", r);
		return "/sys/resourceEdit";
	}

	/**
	 * 编辑资源
	 * 
	 * @param resource
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(SysResource resource) {
		Json j = new Json();
		resourceService.update(resource);
		j.setSuccess(true);
		j.setMsg("编辑成功！");
		return j;
	}

	/**
	 * 获得资源列表
	 * 
	 * 通过用户ID判断，他能看到的资源
	 * 
	 * @return
	 */
	@RequestMapping("/treeGrid")
	@ResponseBody
	public List<SysResource> treeGrid(HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		List<SysResource> ls = resourceService.treeGrid(sessionInfo);
		return ls;
	}

	/**
	 * 删除资源
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		resourceService.delete(Long.parseLong(id));
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
