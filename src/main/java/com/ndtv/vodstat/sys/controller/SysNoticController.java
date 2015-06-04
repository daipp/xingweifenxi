package com.ndtv.vodstat.sys.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ndtv.vodstat.common.pagemodel.DataGrid;
import com.ndtv.vodstat.common.pagemodel.Json;
import com.ndtv.vodstat.common.pagemodel.PageHelper;
import com.ndtv.vodstat.common.pagemodel.PageResult;
import com.ndtv.vodstat.common.pagemodel.SessionInfo;
import com.ndtv.vodstat.common.util.ConfigUtil;
import com.ndtv.vodstat.common.util.FastJsonUtils;
import com.ndtv.vodstat.common.util.IpUtil;
import com.ndtv.vodstat.core.controller.BaseController;
import com.ndtv.vodstat.sys.entity.SysNotice;
import com.ndtv.vodstat.sys.entity.SysRole;
import com.ndtv.vodstat.sys.entity.SysUser;
import com.ndtv.vodstat.sys.service.ISysNoticeService;
@Controller
@RequestMapping("/noticeController")
public class SysNoticController extends BaseController {
	
	@Resource
    private ISysNoticeService noticeService;
	
	@ResponseBody
	@RequestMapping("/getNotice")
	public String getNotice()
	throws Exception {
		String t="";
		List<SysNotice> notices= noticeService.getNotice();
		if(notices!=null&&notices.size()>0){
			
			t = FastJsonUtils.obj2json(notices);
		}
		return t;
	}
	
	/**
	 * 跳转到公告管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager() {
		return "/sys/notice";
	}
	/**
	 * 获得公告列表
	 * 
	 * @return
	 */
	@RequestMapping("/treeGrid")
	@ResponseBody
	public DataGrid treeGrid(SysNotice notice, PageHelper ph) {
/*		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
*/		
		PageResult pr = noticeService.findPage(notice, ph);
		return new DataGrid(pr);
	}
	

	/**
	 * 跳转到公告添加页面
	 * 
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		return "/sys/noticeAdd";
	}

	/**
	 * 添加公告
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(SysNotice notice) {
		/*SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		*/Json j = new Json();
		noticeService.add(notice);
		j.setSuccess(true);
		j.setMsg("添加成功！");
		return j;
	}

	/**
	 * 跳转到公告修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, long id) {
		SysNotice r = noticeService.get(id);
		request.setAttribute("notice", r);
		return "/sys/noticeEdit";
	}

	/**
	 * 修改公告
	 * 
	 * @param notice
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(SysNotice notice) {
		Json j = new Json();
		noticeService.update(notice);
		j.setSuccess(true);
		j.setMsg("编辑成功！");
		return j;
	}

	/**
	 * 删除用户
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id, HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		Json j = new Json();
		if (id != null && Long.parseLong(id) != sessionInfo.getId()) {// 不能删除自己
			noticeService.delete(Long.parseLong(id));
		}
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

	/**
	 * 批量删除用户
	 * 
	 * @param ids
	 *            ('0','1','2')
	 * @return
	 */
	@RequestMapping("/batchDelete")
	@ResponseBody
	public Json batchDelete(String ids, HttpSession session) {
		Json j = new Json();
		if (ids != null && ids.length() > 0) {
			for (String id : ids.split(",")) {
				if (id != null) {
					this.delete(id, session);
				}
			}
		}
		j.setMsg("批量删除成功！");
		j.setSuccess(true);
		return j;
	}


}
