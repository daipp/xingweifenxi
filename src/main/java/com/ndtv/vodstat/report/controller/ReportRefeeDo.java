package com.ndtv.vodstat.report.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ndtv.vodstat.common.pagemodel.DataGrid;
import com.ndtv.vodstat.common.pagemodel.PageHelper;
import com.ndtv.vodstat.common.pagemodel.PageResult;
import com.ndtv.vodstat.common.util.ConfigUtil;
import com.ndtv.vodstat.common.util.FastJsonUtils;
import com.ndtv.vodstat.core.controller.BaseController;
import com.ndtv.vodstat.report.model.BossSysCode;
import com.ndtv.vodstat.report.model.VodAreaCondition;
import com.ndtv.vodstat.report.service.BossService;
import com.ndtv.vodstat.report.service.IReportRefeeService;

@Controller
@RequestMapping("/reportRefeeDo")
public class ReportRefeeDo extends BaseController {

	private static final Logger logger = Logger.getLogger(ReportVodDo.class);
	
	@Resource
	private BossService bossService;
/*	@Resource
	private IReportVodService reportService;*/
	@Resource
	private IReportRefeeService refeeService;
	
	private static final long userTypeId = ConfigUtil.getLong("BOSS_CODETYPE_USERTYPE");

	/**
	 * 显示续费率相关页面
	 * @param request
	 * @param mord
	 * @return
	 */

	@RequestMapping("/vodRefee/{mord}")
	public String openReFeeUsers(HttpServletRequest request,@PathVariable String mord) {
		logger.info("openBookUsers==="+mord);
		AuthorityHelper.initSelectionAttribute(request, bossService);

		List<BossSysCode> userTypes = bossService.getCodeList(userTypeId, 1);
		if(userTypes.isEmpty()){
			userTypes = bossService.getCodeList(userTypeId, 1);
		}
		request.setAttribute("userTypes", userTypes);
		if("books".equals(mord)){
		return "/report/vodarea_books"; 
		}
		if("expires".equals(mord)){
		return "/report/vodarea_expires"; 
		}
		return null;
	}
	
	
	/**
	 *  获取续费率相关表格数据
	 * @param bug
	 * @param ph
	 * @return
	 */
	@RequestMapping("/getVodRefee/{mord}")
	@ResponseBody
	public DataGrid getVodReFee(HttpServletRequest request,  PageHelper ph,	VodAreaCondition va,@PathVariable String mord) {
		if(va.getExpiredDate1() == null && va.getBookDate1() == null ){
			return new DataGrid();
		}
		logger.info("=================");
		AuthorityHelper.limitVodAreaAuth(request, va, bossService);
		try {
			PageResult pr = null;
			if ("books".equals(mord)) {
				pr = refeeService.findBooks(va, ph);
			}
			if ("expires".equals(mord)) {
				pr = refeeService.findExpires(va, ph);
			}
			logger.info(FastJsonUtils.obj2json(new DataGrid(pr)));
			return new DataGrid(pr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
