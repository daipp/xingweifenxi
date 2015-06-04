package com.ndtv.vodstat.sys.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ndtv.vodstat.common.pagemodel.DataGrid;
import com.ndtv.vodstat.common.pagemodel.Json;
import com.ndtv.vodstat.common.pagemodel.PageHelper;
import com.ndtv.vodstat.common.pagemodel.PageResult;
import com.ndtv.vodstat.common.pagemodel.SessionInfo;
import com.ndtv.vodstat.common.util.ConfigUtil;
import com.ndtv.vodstat.core.controller.BaseController;
import com.ndtv.vodstat.sys.entity.SysCode;
import com.ndtv.vodstat.sys.service.ISysCodeService;

@Controller
@RequestMapping("/syscodeController")
public class SysCodeController extends BaseController {

	@Resource
	private ISysCodeService codeService;
	
	/**
	 * 跳转管理页面
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request, Long typeId) {
		if(typeId == null){
			typeId = 0L;
		}
		SysCode codeType = codeService.getSysCode(typeId);
		
		request.setAttribute("codeType", codeType);
		return "/sys/syscode";
	}
	
	/**
	 *  获取数据表格
	 * @param bug
	 * @param ph
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(SysCode sysCode, PageHelper ph) {
		/*
		DetachedCriteria criteria = DetachedCriteria.forClass(SysCode.class);
		if(codeType.getTypeName() != null && !"".equals(codeType.getTypeName().trim())){
			criteria.add(Restrictions.like("typeName", codeType.getTypeName(), MatchMode.ANYWHERE));
		}
		PageResult pr= codeTypeService.findPage(criteria, ph.getStartRow(), ph.getRows());
		dg.setRows(pr.getRows());
		dg.setTotal(pr.getTotalRows());
		*/
		
		/*
		Map<String, Object> params = new HashMap();
		String hql = " from SysCode sc ";
		if(codeType.getTypeName() != null && !"".equals(codeType.getTypeName().trim())){
			hql += "  where sc.typeName like '" + SqlHelper.blurQuery(codeType.getTypeName()) + "' escape '/'";
		}
		List ls = codeTypeService.find(hql, params, ph.getPage(), ph.getRows());
		Long cc = codeTypeService.count(hql, params);
		dg.setRows(ls);
		dg.setTotal(cc);
		*/
		
		PageResult pr = codeService.findPage(sysCode, ph);
		
		return new DataGrid(pr);
	}

	/**
	 * 跳转到添加页面
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request,Long typeId) {
		if(typeId == null){
			typeId = 0L;
		}
		SysCode codeType = codeService.getSysCode(typeId);
		
		request.setAttribute("codeType", codeType);
		return "/sys/syscodeAdd";
	}

	/**
	 * 添加
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(SysCode sysCode,HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		sysCode.setUpdateTime(new Date());
		sysCode.setUpdateUserId(sessionInfo.getId());
		codeService.add(sysCode);
		Json j = new Json();
		j.setSuccess(true);
		j.setMsg("添加成功！");
		return j;
	}

	/**
	 * 跳转到编辑页面
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, long id) {
		SysCode sc = codeService.get(id);
		request.setAttribute("sysCode", sc);
		return "/sys/syscodeEdit";
	}

	/**
	 * 更新
	 * @param resource
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json update(SysCode codeType,HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		codeType.setUpdateTime(new Date());
		codeType.setUpdateUserId(sessionInfo.getId());
		codeService.update(codeType);
		Json j = new Json();
		j.setSuccess(true);
		j.setMsg("更新成功！");
		return j;
	}

	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(long id) {
		codeService.delete(id);
		Json j = new Json();
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}
		
}
