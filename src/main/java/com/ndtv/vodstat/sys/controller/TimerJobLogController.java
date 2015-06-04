package com.ndtv.vodstat.sys.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ndtv.vodstat.common.pagemodel.DataGrid;
import com.ndtv.vodstat.common.pagemodel.PageHelper;
import com.ndtv.vodstat.common.pagemodel.PageResult;
import com.ndtv.vodstat.common.util.ConfigUtil;
import com.ndtv.vodstat.common.util.FastJsonUtils;
import com.ndtv.vodstat.core.controller.BaseController;
import com.ndtv.vodstat.sys.entity.SysCode;
import com.ndtv.vodstat.sys.entity.TimerJobLog;
import com.ndtv.vodstat.sys.service.ISysCodeService;
import com.ndtv.vodstat.sys.service.TimerJobLogService;

@Controller
@RequestMapping("/timerJobLogController")
public class TimerJobLogController extends BaseController {
	
	public static final long TimerJobs = ConfigUtil.getLong("codeType_timerJobs");
	
	@Resource
	private TimerJobLogService timerJobLogService;
	@Resource
	private ISysCodeService codeService;
	
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		List<SysCode> timerJobs = codeService.getSysCodeList(TimerJobs, SysCode.STATUS_VALID);
		request.setAttribute("timerJobs", FastJsonUtils.obj2json(timerJobs));
		return "/sys/timerjobs";
	}
	
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(TimerJobLog condition,String date1,String date2, PageHelper ph) {
		if(date1 == null && date2 == null){
			return new DataGrid();
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date1D,date2D;
		try {
			date1D = dateFormat.parse(date1);
			date2D = dateFormat.parse(date2);
		} catch (ParseException e) {
			e.printStackTrace();
			return new DataGrid();
		}
		
		PageResult pr = timerJobLogService.findPage(condition, date1D, date2D, ph);
		return new DataGrid(pr);
	}
		
}
