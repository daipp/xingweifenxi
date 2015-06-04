package com.ndtv.vodstat.report.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.ndtv.vodstat.common.util.FastJsonUtils;
import com.ndtv.vodstat.core.controller.BaseController;
import com.ndtv.vodstat.report.model.AreaDateReport;
import com.ndtv.vodstat.report.model.TopResult;
import com.ndtv.vodstat.report.model.VodArea;
import com.ndtv.vodstat.report.model.VodAreaCondition;
import com.ndtv.vodstat.report.service.BossService;
import com.ndtv.vodstat.report.service.IReportHotService;

@Controller
@RequestMapping("/reportHotDo")
public class ReportHotDo extends BaseController{

	private static final Logger logger = Logger.getLogger(ReportHotDo.class);
	
	/**
	 * 显示热度相关页面
	 * @param request
	 * @param mord
	 * @return
	 */
	@Resource
	private BossService bossService;
	@Resource
	private IReportHotService hotService;

	@RequestMapping("/openHot/{mord}")
	public String openReportHot(HttpServletRequest request,@PathVariable String mord) {
		logger.info("openReportHot==="+mord);
		AuthorityHelper.initSelectionAttribute(request, bossService);
		if("hotUsers".equals(mord)){
		return "/report/hot_users"; 
		}
		if("hotPrograms".equals(mord)){
		return "/report/hot_programs"; 
		}
		if("hotCommunity".equals(mord)){
		return "/report/hot_community";
		}
		return null;
	}
	
	/**
	 *  打开社区经理视图页面
	 * @param bug
	 * @param ph
	 * @return
	 */
	@RequestMapping("/openManagerView")
	public String openCommunityManagerView(HttpServletRequest request,  PageHelper ph,	VodAreaCondition va) {	
		logger.info("=================");
		AuthorityHelper.initSelectionAttribute(request, bossService);
		return "/report/managerView";
	}
	/**
	 *  获取表格数据
	 * @param bug
	 * @param ph
	 * @return
	 */
	@RequestMapping("/getManagerViewData")
	@ResponseBody
	public DataGrid findManagerViewData(HttpServletRequest request,  PageHelper ph,	VodAreaCondition va) {
		if(va.getRepDate1() == null && va.getRepDate2() == null ){
			return new DataGrid();
		}
		logger.info("=================");
		AuthorityHelper.limitVodAreaAuth(request, va, bossService);
		try {
			PageResult pr = null;
		    pr = hotService.findManagerViewData(va, ph);
			logger.info(FastJsonUtils.obj2json(new DataGrid(pr)));
			return new DataGrid(pr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 *  获取社区经理视图折线图
	 * @param bug
	 * @param ph
	 * @return
	 */
	@RequestMapping("/getManagerViewLine")
	@ResponseBody
	public String getManagerViewLine(HttpServletRequest request, 	VodAreaCondition va) {
		if(va.getRepDate1() == null && va.getRepDate2() == null ){
			return null;
		}		
		logger.info("=================");
		AuthorityHelper.limitVodAreaAuth(request, va, bossService);
		try {
			Map mp = new HashMap();
			List<AreaDateReport> ls1 = new ArrayList();			
			ls1 = hotService.getManagerViewLine(va);
			if(va.isShowActiveUsers()){
				List<Long> ls2=new ArrayList();
				for(AreaDateReport ar:ls1){						
					ls2.add(ar.getActiveUsers());
				}
				mp.put("activeUsers",ls2.toArray());
			}
			if(va.isShowBbUsers()){
				List<Long> ls2=new ArrayList();
				for(AreaDateReport ar:ls1){						
					ls2.add(ar.getBbUsers());
				}
				mp.put("bbUsers",ls2.toArray());
			}
			if(va.isShowCustomers()){
				List<Long> ls2=new ArrayList();
				for(AreaDateReport ar:ls1){						
					ls2.add(ar.getCustomers());
				}
				mp.put("customers",ls2.toArray());
			}
			if(va.isShowDvbUsers()){
				List<Long> ls2=new ArrayList();
				for(AreaDateReport ar:ls1){						
					ls2.add(ar.getDvbUsers());
				}
				mp.put("dvbUsers",ls2.toArray());
			}
			if(va.isShowHdstbs()){
				List<Long> ls2=new ArrayList();
				for(AreaDateReport ar:ls1){						
					ls2.add(ar.getHdstbs());
				}
				mp.put("hdstbs",ls2.toArray());
			}
			if(va.isShowOnlineBookedUsers()){
				List<Long> ls2=new ArrayList();
				for(AreaDateReport ar:ls1){						
					ls2.add(ar.getOnlineBookedUsers());
				}
				mp.put("onlineBookedUsers",ls2.toArray());
			}
			if(va.isShowOnlineUnbookUsers()){
				List<Long> ls2=new ArrayList();
				for(AreaDateReport ar:ls1){						
					ls2.add(ar.getOnlineUnbookUsers());
				}
				mp.put("onlineUnbookUsers",ls2.toArray());
			}
			if(va.isShowVodUsers()){
				List<Long> ls2=new ArrayList();
				for(AreaDateReport ar:ls1){						
					ls2.add(ar.getVodUsers());
				}
				mp.put("vodUsers",ls2.toArray());
			}
		
			String fromDate = new SimpleDateFormat("yyyy/MM/dd").format(va.getRepDate1());
			mp.put("fromDate", fromDate);
			return (FastJsonUtils.obj2json(mp));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 *  获取社区经理视图折线图
	 * @param bug
	 * @param ph
	 * @return
	 */
	@RequestMapping("/getManagerViewPie")
	@ResponseBody
	public String getManagerViewPie(HttpServletRequest request, 	VodAreaCondition va) {			
		logger.info("=================");
		AuthorityHelper.limitVodAreaAuth(request, va, bossService);
		try {
			Map mp = new HashMap();	
			List<AreaDateReport> ls1 = new ArrayList();	
			List<Object[]> ls2=new ArrayList();
			ls1 = hotService.getManagerViewPie(va);			
			if(va.getField2().equals("activeUsers")){
				for(AreaDateReport ar:ls1){	
					ls2.add(new Object[]{ar.getVillage(),ar.getActiveUsers()});
				}
				mp.put("field","活跃用户--"+new SimpleDateFormat("yyyy-MM-dd").format(va.getRepDate()));
			}
			if(va.getField2().equals("onlineBookedUsers")){
				for(AreaDateReport ar:ls1){	
					ls2.add(new Object[]{ar.getVillage(),ar.getOnlineBookedUsers()});
				}
				mp.put("field","登录用户(在线)--"+new SimpleDateFormat("yyyy-MM-dd").format(va.getRepDate()));
			}
			if(va.getField2().equals("onlineUnbookUsers")){
				for(AreaDateReport ar:ls1){	
					ls2.add(new Object[]{ar.getVillage(),ar.getOnlineUnbookUsers()});
				}
				mp.put("field","登录用户(离线)--"+new SimpleDateFormat("yyyy-MM-dd").format(va.getRepDate()));
			}
			if(va.getField2().equals("customers")){
				for(AreaDateReport ar:ls1){	
					ls2.add(new Object[]{ar.getVillage(),ar.getCustomers()});
				}
				mp.put("field","客户数--"+new SimpleDateFormat("yyyy-MM-dd").format(va.getRepDate()));
			}
			if(va.getField2().equals("hdstbs")){
				for(AreaDateReport ar:ls1){	
					ls2.add(new Object[]{ar.getVillage(),ar.getHdstbs()});
				}
				mp.put("field","高清用户--"+new SimpleDateFormat("yyyy-MM-dd").format(va.getRepDate()));
			}
			if(va.getField2().equals("dvbUsers")){
				for(AreaDateReport ar:ls1){	
					ls2.add(new Object[]{ar.getVillage(),ar.getDvbUsers()});
				}
				mp.put("field","基本用户--"+new SimpleDateFormat("yyyy-MM-dd").format(va.getRepDate()));
			}		
			if(va.getField2().equals("vodUsers")){
				for(AreaDateReport ar:ls1){	
					ls2.add(new Object[]{ar.getVillage(),ar.getVodUsers()});
				}
				mp.put("field","交互用户--"+new SimpleDateFormat("yyyy-MM-dd").format(va.getRepDate()));
			}
			if(va.getField2().equals("bbUsers")){
				for(AreaDateReport ar:ls1){	
					ls2.add(new Object[]{ar.getVillage(),ar.getBbUsers()});
				}
				mp.put("field","宽带用户--"+new SimpleDateFormat("yyyy-MM-dd").format(va.getRepDate()));
			}
			if(va.getField2().equals("analogUsers")){
				for(AreaDateReport ar:ls1){	
					ls2.add(new Object[]{ar.getVillage(),ar.getAnalogUsers()});
				}
				mp.put("field","模拟用户--"+new SimpleDateFormat("yyyy-MM-dd").format(va.getRepDate()));
			}
			if(va.getField2().equals("dvbUsers0")){
				for(AreaDateReport ar:ls1){	
					ls2.add(new Object[]{ar.getVillage(),ar.getDvbUsers()});
				}
				mp.put("field","正常基本用户--"+new SimpleDateFormat("yyyy-MM-dd").format(va.getRepDate()));
			}
			if(va.getField2().equals("vodUsers0")){
				for(AreaDateReport ar:ls1){	
					ls2.add(new Object[]{ar.getVillage(),ar.getVodUsers()});
				}
				mp.put("field","正常交互用户--"+new SimpleDateFormat("yyyy-MM-dd").format(va.getRepDate()));
			}
			if(va.getField2().equals("bbUsers0")){
				for(AreaDateReport ar:ls1){	
					ls2.add(new Object[]{ar.getVillage(),ar.getBbUsers()});
				}
				mp.put("field","正常宽带用户--"+new SimpleDateFormat("yyyy-MM-dd").format(va.getRepDate()));
			}		
			if(va.getField2().equals("analogUsers0")){
				for(AreaDateReport ar:ls1){	
					ls2.add(new Object[]{ar.getVillage(),ar.getAnalogUsers0()});
				}
				mp.put("field","正常模拟用户--"+new SimpleDateFormat("yyyy-MM-dd").format(va.getRepDate()));
			}
			if(va.getField2().equals("dvbBooks")){
				for(AreaDateReport ar:ls1){	
					ls2.add(new Object[]{ar.getVillage(),ar.getDvbBooks()});
				}
				mp.put("field","付费在线用户--"+new SimpleDateFormat("yyyy-MM-dd").format(va.getRepDate()));
			}
			if(va.getField2().equals("vodBooks")){
				for(AreaDateReport ar:ls1){	
					ls2.add(new Object[]{ar.getVillage(),ar.getVodBooks()});
				}
				mp.put("field","点播在线用户--"+new SimpleDateFormat("yyyy-MM-dd").format(va.getRepDate()));
			}
			if(va.getField2().equals("bbBooks")){
				for(AreaDateReport ar:ls1){	
					ls2.add(new Object[]{ar.getVillage(),ar.getBbBooks()});
				}
				mp.put("field","宽带在线用户--"+new SimpleDateFormat("yyyy-MM-dd").format(va.getRepDate()));
			}
			if(va.getField2().equals("dvbBooksNew")){
				for(AreaDateReport ar:ls1){	
					ls2.add(new Object[]{ar.getVillage(),ar.getDvbBooksNew()});
				}
				mp.put("field","付费在线新用户--"+new SimpleDateFormat("yyyy-MM-dd").format(va.getRepDate()));
			}		
			if(va.getField2().equals("vodBooksNew")){
				for(AreaDateReport ar:ls1){	
					ls2.add(new Object[]{ar.getVillage(),ar.getVodBooksNew()});
				}
				mp.put("field","点播在线新用户--"+new SimpleDateFormat("yyyy-MM-dd").format(va.getRepDate()));
			}
			if(va.getField2().equals("bbBooksNew")){
				for(AreaDateReport ar:ls1){	
					ls2.add(new Object[]{ar.getVillage(),ar.getBbBooksNew()});
				}
				mp.put("field","宽带在线新用户--"+new SimpleDateFormat("yyyy-MM-dd").format(va.getRepDate()));
			}
			if(va.getField2().equals("hostStarts")){
				for(AreaDateReport ar:ls1){	
					ls2.add(new Object[]{ar.getVillage(),ar.getHostStarts()});
				}
				mp.put("field","主开用户--"+new SimpleDateFormat("yyyy-MM-dd").format(va.getRepDate()));
			}
			if(va.getField2().equals("hostStops")){
				for(AreaDateReport ar:ls1){	
					ls2.add(new Object[]{ar.getVillage(),ar.getHostStops()});
				}
				mp.put("field","主停用户--"+new SimpleDateFormat("yyyy-MM-dd").format(va.getRepDate()));
			}
			if(va.getField2().equals("hostQuits")){
				for(AreaDateReport ar:ls1){	
					ls2.add(new Object[]{ar.getVillage(),ar.getHostQuits()});
				}
				mp.put("field","主销用户--"+new SimpleDateFormat("yyyy-MM-dd").format(va.getRepDate()));
			}		
			if(va.getField2().equals("hostNormal")){
				for(AreaDateReport ar:ls1){	
					ls2.add(new Object[]{ar.getVillage(),ar.getHostNormal()});
				}
				mp.put("field","正常主--"+new SimpleDateFormat("yyyy-MM-dd").format(va.getRepDate()));
			}
			if(va.getField2().equals("hostStoped")){
				for(AreaDateReport ar:ls1){	
					ls2.add(new Object[]{ar.getVillage(),ar.getHostStoped()});
				}
				mp.put("field","已停主--"+new SimpleDateFormat("yyyy-MM-dd").format(va.getRepDate()));
			}
			if(va.getField2().equals("hostUnpay1")){
				for(AreaDateReport ar:ls1){	
					ls2.add(new Object[]{ar.getVillage(),ar.getHostUnpay1()});
				}
				mp.put("field","欠一年主--"+new SimpleDateFormat("yyyy-MM-dd").format(va.getRepDate()));
			}
			if(va.getField2().equals("hostUnpay2")){
				for(AreaDateReport ar:ls1){	
					ls2.add(new Object[]{ar.getVillage(),ar.getHostUnpay2()});			
				}
				mp.put("field","欠两年主--"+new SimpleDateFormat("yyyy-MM-dd").format(va.getRepDate()));
			}		
			mp.put("value", ls2.toArray());
			String fromDate = new SimpleDateFormat("yyyy/MM/dd").format(va.getRepDate1());
			mp.put("fromDate", fromDate);
			return (FastJsonUtils.obj2json(mp));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public List<Long> selectCondition(List<Long> ls2,List<AreaDateReport> ls1){
		for(AreaDateReport ar:ls1){
			
		}
		return ls2;
	}
	public long[] strArray2longArray(String str){
		long [] longs=null;
		if(str==null||str.isEmpty()){
			return null;
		}
		String[] strs=str.split(",");
	    for(int i= 0;i<strs.length;i++){
	    	longs[i]=Long.valueOf(strs[i]);
		}
	   return longs;			
	}
	
	/**
	 *  获取热度用户相关表格数据
	 * @param bug
	 * @param ph
	 * @return
	 */
	@RequestMapping("/getReportHotUsers/{mord}")
	@ResponseBody
	public DataGrid getHotReport(HttpServletRequest request,  PageHelper ph,	VodAreaCondition va,@PathVariable String mord) {
		if(va.getBookDate1() == null && va.getBookDate2() == null ){
			return new DataGrid();
		}		
		logger.info("=================");
		AuthorityHelper.limitVodAreaAuth(request, va, bossService);
		try {
			PageResult pr = null;
			if ("time".equals(mord)) {//时长
				pr = hotService.findHotUserByTime(va, ph);
			}
			if ("times".equals(mord)) {//次数
			/*	ls1 = hotService.findHotUserByTime(va, ph);*/
				pr = hotService.findHotUserByTimes(va, ph);
			}
			logger.info(FastJsonUtils.obj2json(new DataGrid(pr)));
			return new DataGrid(pr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 *  获取热度小区相关表格数据
	 * @param bug
	 * @param ph
	 * @return
	 */
	@RequestMapping("/getReportHotCommunity/{mord}")
	@ResponseBody
	public DataGrid getHotReportCommunity(HttpServletRequest request,  PageHelper ph,	VodAreaCondition va,@PathVariable String mord) {
		if(va.getBookDate1() == null && va.getBookDate2() == null ){
			return new DataGrid();
		}		
		logger.info("=================");
		AuthorityHelper.limitVodAreaAuth(request, va, bossService);
		try {
			PageResult pr = null;
			if ("time".equals(mord)) {//时长
				pr = hotService.findHotCommunityByTime(va, ph);
			}
			if ("times".equals(mord)) {//次数
			/*	ls1 = hotService.findHotUserByTime(va, ph);*/
				pr = hotService.findHotCommunityByTimes(va, ph);
			}
			logger.info(FastJsonUtils.obj2json(new DataGrid(pr)));
			return new DataGrid(pr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 *  获取热度相关表格数据
	 * @param bug
	 * @param ph
	 * @return
	 */
	@RequestMapping("/getReportHotPrograms/{mord}")
	@ResponseBody
	public String getHotReport(HttpServletRequest request, 	VodAreaCondition va,@PathVariable String mord) {
		if(va.getBookDate1() == null && va.getBookDate2() == null ){
			return null;
		}		
		logger.info("=================");
		AuthorityHelper.limitVodAreaAuth(request, va, bossService);
		try {
			List<TopResult> ls1 = new ArrayList();
			List<TopResult> ls2 = new ArrayList();
			List<Long> la1= new ArrayList();
			List<String> la2= new ArrayList();
			List<Long> la3= new ArrayList();
			List<String> la4= new ArrayList();
			if ("time".equals(mord)) {//时长
				ls1 = hotService.findHotProgramsByTime(va);
				ls2 = hotService.findHotTypesByTime(va);
				for(TopResult v:ls1){
					la1.add(v.getViewTime());
					la2.add(v.getFilmname());
				}
				for(TopResult v:ls2){
					la3.add(v.getViewTime());
					la4.add(v.getCatergory());
				}
			}
			if ("times".equals(mord)) {//次数
				ls1 = hotService.findHotProgramsByTimes(va);
				ls2 = hotService.findHotTypesByTimes(va);
				for(TopResult v:ls1){
					la1.add(v.getClickTimes());
					la2.add(v.getFilmname());
				}
				for(TopResult v:ls2){
					la3.add(v.getClickTimes());
					la4.add(v.getCatergory());
				}
				/*la2 = hotService.findHotProgramsByTimes(va);*/
			}
			/*logger.info(FastJsonUtils.obj2json(new DataGrid(pr)));*/
			
			Map mp = new HashMap();
			String fromDate = new SimpleDateFormat("yyyy/MM/dd").format(va.getBookDate1());
			mp.put("fromDate", fromDate);
			mp.put("filmTimes", la1.toArray());			
			mp.put("filmNames", la2.toArray());
			mp.put("catergoryTimes", la3.toArray());
			mp.put("catergoryNames",la4.toArray());
			return (FastJsonUtils.obj2json(mp));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	

}
