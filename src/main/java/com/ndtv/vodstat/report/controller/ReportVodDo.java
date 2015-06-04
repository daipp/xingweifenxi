package com.ndtv.vodstat.report.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ndtv.vodstat.common.pagemodel.DataGrid;
import com.ndtv.vodstat.common.pagemodel.PageHelper;
import com.ndtv.vodstat.common.pagemodel.PageResult;
import com.ndtv.vodstat.common.util.DateFunctions;
import com.ndtv.vodstat.common.util.FastJsonUtils;
import com.ndtv.vodstat.common.util.ResponseFileWriter;
import com.ndtv.vodstat.core.controller.BaseController;
import com.ndtv.vodstat.report.model.BossCustomerInfo;
import com.ndtv.vodstat.report.model.BossUserInfo;
import com.ndtv.vodstat.report.model.EpgLog;
import com.ndtv.vodstat.report.model.VodArea;
import com.ndtv.vodstat.report.model.VodAreaCondition;
import com.ndtv.vodstat.report.model.WasunLog;
import com.ndtv.vodstat.report.service.BossService;
import com.ndtv.vodstat.report.service.IReportAreaService;
import com.ndtv.vodstat.report.service.IReportVodService;
import com.ndtv.vodstat.report.service.VodLogsService;

@Controller
@RequestMapping("/reportvodDo")
public class ReportVodDo extends BaseController {

	private static final Logger logger = Logger.getLogger(ReportVodDo.class);
	
	@Resource
	private IReportAreaService reportAreaService;
	@Resource
	private IReportVodService reportService;
	@Resource
	private BossService bossService;
	@Resource 
	private VodLogsService vodLogsService;
	
	/**
	 * 显示页面
	 * @param request
	 * @param mord
	 * @return
	 */
	@RequestMapping("/vodArea/{mord}")
	public String openVodArea(HttpServletRequest request,@PathVariable String mord) {
		AuthorityHelper.initSelectionAttribute(request, bossService);
		if ("d".equals(mord)) {
			return "/report/vodarea_d";
		}
		if ("m".equals(mord)) {
			return "/report/vodarea_m";
		}
		if ("now".equals(mord)) {
			return "/report/vodarea_now";
		}
		if ("detail".equals(mord)) {
			return "/report/vodarea_detail";
		}
		if ("actives".equals(mord)) {
			return "/report/vodarea_actives";
		}
		return null;
	}
	
//	@InitBinder  
//	public void setAllowedFields(WebDataBinder dataBinder) {  
//	    dataBinder.setDisallowedFields(new String[] {"id","townId","communityId","villageId"});  
//	}  
	
	/**
	 *  获取表格数据
	 * @param bug
	 * @param ph
	 * @return
	 */
	@RequestMapping("/getVodArea/{mord}")
	@ResponseBody
	public DataGrid getVodArea(HttpServletRequest request,  PageHelper ph,	VodAreaCondition va,@PathVariable String mord) {
		if(va.getRepDate1() == null && va.getActiveDate1() == null && va.getExpiredDate1() == null){
			return new DataGrid();
		}
		logger.info("=================");
		AuthorityHelper.limitVodAreaAuth(request, va, bossService);
		try {
			PageResult pr = null;
			if ("d".equals(mord)) {
				pr = reportAreaService.findAreaDateReport(va, ph);
			}
			if ("m".equals(mord)) {
				pr = reportAreaService.findAreaMonthReport(va, ph);
			}
			if ("now".equals(mord)) {
				pr = reportService.findVodAreaNow(va, ph);
			}
			if ("detail".equals(mord)) {
				pr = reportService.findVodAreaDetail(va, ph);
			}
			if ("actives".equals(mord)) {
				pr = reportService.findVodAreaActives(va, ph);
			}
			logger.info(FastJsonUtils.obj2json(new DataGrid(pr)));
			return new DataGrid(pr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/downLoadVodArea/{mord}")
	public void downLoadVodArea(HttpServletRequest request, HttpServletResponse response, VodAreaCondition va,@PathVariable String mord) {
		if(va.getRepDate1() == null && va.getActiveDate1() == null && va.getExpiredDate1() == null){
			return;
		}
		logger.info("=================");
		AuthorityHelper.limitVodAreaAuth(request, va, bossService);
		List<Object[]> dataList = new ArrayList();
		try {
			PageResult pr = null;
//			if ("d".equals(mord)) {
//				pr = reportService.findVodAreaD(va,null);
//			}
//			if ("m".equals(mord)) {
//				pr = reportService.findVodAreaM(va,null);
//			}
//			if ("now".equals(mord)) {
//				pr = reportService.findVodAreaNow(va,null);
//			}
//			if ("actives".equals(mord)) {
//				pr = reportService.findVodAreaActives(va,null);
//			}
			if ("detail".equals(mord)) {
				pr = reportService.findVodAreaDetail(va,null);
				List<BossUserInfo> ls = pr.getRows();
				dataList.add(new Object[]{
						"街道","社区","小区","客户类型","用户编号","客户名称","具体地址","电话","手机",
						"机顶盒","用户群","开户日期","最大截止日期","最大订购日期",
						"用户状态","登录次数","活页次数",
						"宽带未到期","宽带将到期","宽带已到期","点播未到期","点播将到期","点播已到期"
				});
				for(BossUserInfo bi : ls){
					dataList.add(new Object[]{
						bi.getTown(),bi.getCommunity(),bi.getVillage(),bi.getCustomerType(),
						bi.getUserId(),bi.getCustomerName(),bi.getFulladdress(),bi.getPhone(),bi.getMobile(),
						
						bi.getStb(),bi.getUserGroup(),bi.getCrTime(),bi.getMaxEndTime(),bi.getMaxBookTime(),
						bi.getUserState(),bi.getOnlineTimes(),bi.getActiveTimes(),
						bi.getBbUnExpiredUsers(),bi.getBbExpiringUsers(),bi.getBbExpiredUsers(),
						bi.getVodUnExpiredUsers(),bi.getVodExpiringUsers(),bi.getVodExpiredUsers()
						
					});
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(!dataList.isEmpty()){
			ResponseFileWriter.writeExcel(response, "实时清单", dataList);
		}
	}
	
	/**
	 *  获取数据图形数据
	 */
	@RequestMapping("/getVodAreaChart/{mord}")
	//@ResponseBody
	public String getVodAreaChart(HttpServletRequest request, VodAreaCondition condition, @PathVariable String mord) {
		logger.info("mord="+mord);
		String url = "/report/vodareachart";
		if(condition.getRepDate1() == null){
			return url;
		}
		//logger.info("=================");
		AuthorityHelper.limitVodAreaAuth(request, condition, bossService);
		boolean inOneDate = true;
		if(condition.getRepDate2() != null 
		&& DateFunctions.dateCompare(condition.getRepDate1(), condition.getRepDate2()) != 0){
			inOneDate = false;
		}
		
		PageResult pr;
		if("d".equals(mord)){
			pr = reportAreaService.findAreaDateReport(condition, null);
		} else {
			pr = reportAreaService.findAreaMonthReport(condition, null);
		}
		
		List<VodArea> ls = pr.getRows();
		
		if(inOneDate){
			 //如果是相同日期的:
			Map spotDataMap = new HashMap();
			if(condition.isShowCustomerType()){
				Map<String,VodArea> cc = new HashMap<String,VodArea>();
				for(VodArea ta : ls){
					if(cc.containsKey(ta.getCustomerType())){
						cc.get(ta.getCustomerType()).add(ta);
					} else {
						cc.put(ta.getCustomerType(), (VodArea)ta.clone());
					}
				}
				spotDataMap.put("customerType", getPercentMap(cc,mord));
				//request.setAttribute("customerTypeMap", FastJsonUtils.obj2json(getPercentMap(cc)));
			}
			if(condition.isShowTown()){
				Map<String,VodArea> cc = new HashMap();
				for(VodArea ta : ls){
					if(cc.containsKey(ta.getTown())){
						cc.get(ta.getTown()).add(ta);
					} else {
						cc.put(ta.getTown(), (VodArea)ta.clone());
					}
				}
				spotDataMap.put("town", getPercentMap(cc,mord));
				//request.setAttribute("townMap", FastJsonUtils.obj2json(getPercentMap(cc)));
			}
			if(condition.isShowCommunity()){
				Map<String,VodArea> cc = new HashMap();
				for(VodArea ta : ls){
					if(cc.containsKey(ta.getCommunity())){
						cc.get(ta.getCommunity()).add(ta);
					} else {
						cc.put(ta.getCommunity(), (VodArea)ta.clone());
					}
				}
				spotDataMap.put("community", getPercentMap(cc,mord));
				//request.setAttribute("communityMap", FastJsonUtils.obj2json(getPercentMap(cc)));
			}
			if(condition.isShowVillage()){
				Map<String,VodArea> cc = new HashMap();
				for(VodArea ta : ls){
					if(cc.containsKey(ta.getVillage())){
						cc.get(ta.getVillage()).add(ta);
					} else {
						cc.put(ta.getVillage(), (VodArea)ta.clone());
					}
				}
				spotDataMap.put("village", getPercentMap(cc,mord));
				//request.setAttribute("villageMap", FastJsonUtils.obj2json(getPercentMap(cc)));
			}
			request.setAttribute("spotDataMap", FastJsonUtils.obj2json(spotDataMap));
			return url;
		}
		
		//如果是跨日期的:
		Map seriesDataMap = new HashMap();
		String fromDate = new SimpleDateFormat("yyyy/MM/dd").format(ls.get(0).getRepDate());
		seriesDataMap.put("fromDate", fromDate);
		
		//if(!condition.isShowCustomerType() && !condition.isShowTown() && !condition.isShowCommunity() && !condition.isShowVillage()) {
		if(condition.isShowRepDate()) {
			List<VodArea> tmpls = new ArrayList();
			for(VodArea ta : ls){
				combineVodareaListByRepdate(tmpls,(VodArea)ta.clone());
			}
			Map cc = new HashMap();
			cc.put("不限", tmpls);
			seriesDataMap.put("repDate", getDateSeriesMap(cc,mord));
			logger.info(FastJsonUtils.obj2json(seriesDataMap.get("repDate")));
			//request.setAttribute("repDateMap", FastJsonUtils.obj2json(repDateMap));
		}
		if(condition.isShowCustomerType()){
			Map<String,List<VodArea>> cc = new HashMap();
			for(VodArea ta : ls){
				if(cc.containsKey(ta.getCustomerType())){
					List<VodArea> tmpls = cc.get(ta.getCustomerType());
					combineVodareaListByRepdate(tmpls,(VodArea)ta.clone());
				} else {
					List<VodArea> tmpls = new ArrayList();
					tmpls.add((VodArea)ta.clone());
					cc.put(ta.getCustomerType(), tmpls);
				}
			}
			seriesDataMap.put("customerType", getDateSeriesMap(cc,mord));
			logger.info(FastJsonUtils.obj2json(seriesDataMap.get("customerType")));
		}
		if(condition.isShowTown()){
			Map<String,List<VodArea>> cc = new HashMap();
			for(VodArea ta : ls){
				if(cc.containsKey(ta.getTown())){
					List<VodArea> tmpls = cc.get(ta.getTown());
					combineVodareaListByRepdate(tmpls,(VodArea)ta.clone());
				} else {
					List<VodArea> tmpls = new ArrayList();
					tmpls.add((VodArea)ta.clone());
					cc.put(ta.getTown(), tmpls);
				}
			}
			seriesDataMap.put("town", getDateSeriesMap(cc,mord));
			logger.info(FastJsonUtils.obj2json(seriesDataMap.get("town")));
		}
		if(condition.isShowCommunity()){
			Map<String,List<VodArea>> cc = new HashMap();
			for(VodArea ta : ls){
				if(cc.containsKey(ta.getCommunity())){
					List<VodArea> tmpls = cc.get(ta.getCommunity());
					combineVodareaListByRepdate(tmpls,(VodArea)ta.clone());
				} else {
					List<VodArea> tmpls = new ArrayList();
					tmpls.add((VodArea)ta.clone());
					cc.put(ta.getCommunity(), tmpls);
				}
			}
			seriesDataMap.put("community", getDateSeriesMap(cc,mord));
			logger.info(FastJsonUtils.obj2json(seriesDataMap.get("community")));
		}
		if(condition.isShowVillage()){
			Map<String,List<VodArea>> cc = new HashMap();
			for(VodArea ta : ls){
				if(cc.containsKey(ta.getVillage())){
					List<VodArea> tmpls = cc.get(ta.getVillage());
					combineVodareaListByRepdate(tmpls,(VodArea)ta.clone());
				} else {
					List<VodArea> tmpls = new ArrayList();
					tmpls.add((VodArea)ta.clone());
					cc.put(ta.getVillage(), tmpls);
				}
			}
			seriesDataMap.put("village", getDateSeriesMap(cc,mord));
			logger.info(FastJsonUtils.obj2json(seriesDataMap.get("village")));
		}
		logger.info(FastJsonUtils.obj2json(seriesDataMap));
		request.setAttribute("seriesDataMap", FastJsonUtils.obj2json(seriesDataMap));
		return url;
	}
	
	private void combineVodareaListByRepdate(List<VodArea> tmpls, VodArea ta){
		boolean isNewDate = true;
		for(VodArea va : tmpls){
			if(DateFunctions.dateCompare(va.getRepDate(), ta.getRepDate()) == 0){
				va.add(ta);
				isNewDate = false;
				break;
			}
		}
		if(isNewDate) {
			tmpls.add(ta);
		}
	}

	private Map getDateSeriesMap(Map<String,List<VodArea>> aa,String mord){
		List<Map<String,Object>> customerList0 = new ArrayList();
		List<Map<String,Object>> dvbList0 = new ArrayList();
		List<Map<String,Object>> bbList0 = new ArrayList();
		List<Map<String,Object>> vodList0 = new ArrayList();
		List<Map<String,Object>> hdstbList0 = new ArrayList();
		List<Map<String,Object>> bookList0 = new ArrayList();
		List<Map<String,Object>> activeList0 = new ArrayList();
		
		List<Map<String,Object>> onlineBList0 = new ArrayList();
		List<Map<String,Object>> onlineUList0 = new ArrayList();
		List<Map<String,Object>> offlineBList0 = new ArrayList();
		List<Map<String,Object>> offlineUList0 = new ArrayList();
		List<Map<String,Object>> inactiveBList0 = new ArrayList();
		List<Map<String,Object>> inactiveUList0 = new ArrayList();
		
		for(String k : aa.keySet()){
			List<VodArea> ls = aa.get(k);
			Collections.sort(ls);
			List<Long> customerList = new ArrayList();
			List<Long> dvbList = new ArrayList();
			List<Long> bbList = new ArrayList();
			List<Long> vodList = new ArrayList();
			List<Long> hdstbList = new ArrayList();
			List<Long> bookList = new ArrayList();
			
			List<Long> activeList = new ArrayList();
			List<Long> onlineBList = new ArrayList();
			List<Long> onlineUList = new ArrayList();
			
			List<Long> offlineBList = new ArrayList();
			List<Long> offlineUList = new ArrayList();
			List<Long> inactiveBList = new ArrayList();
			List<Long> inactiveUList = new ArrayList();
			
			for(VodArea va : ls){
				customerList.add(va.getCustomers());
				dvbList.add(va.getDvbUsers());
				bbList.add(va.getBbUsers());
				vodList.add(va.getVodUsers());
				hdstbList.add(va.getHdstbs());
				bookList.add(va.getBookUsers());
				
				activeList.add(va.getActiveUsers());
				onlineBList.add(va.getOnlineBookedUsers());
				onlineUList.add(va.getOnlineUnbookUsers());
				offlineBList.add(va.getOfflineBookedUsers());
				offlineUList.add(va.getOfflineUnbookUsers());
				inactiveBList.add(va.getInactiveOnlineUsers());
				inactiveUList.add(va.getInactiveOfflineUsers());
			}
			Map mp0 = new HashMap();
			mp0.put("name", k);
			mp0.put("data", customerList.toArray());
			customerList0.add(mp0);

			Map mp1 = new HashMap();
			mp1.put("name", k);
			mp1.put("data", dvbList.toArray());
			dvbList0.add(mp1);

			Map mp2 = new HashMap();
			mp2.put("name", k);
			mp2.put("data", bbList.toArray());
			bbList0.add(mp2);
			
			Map mp3 = new HashMap();
			mp3.put("name", k);
			mp3.put("data", vodList.toArray());
			vodList0.add(mp3);
			
			Map mp4 = new HashMap();
			mp4.put("name", k);
			mp4.put("data", hdstbList.toArray());
			hdstbList0.add(mp4);
			
			Map mp5 = new HashMap();
			mp5.put("name", k);
			mp5.put("data", bookList.toArray());
			bookList0.add(mp5);
			
			Map mp6 = new HashMap();
			mp6.put("name", k);
			mp6.put("data", activeList.toArray());
			activeList0.add(mp6);
			
			Map mp7 = new HashMap();
			mp7.put("name", k);
			mp7.put("data", onlineBList.toArray());
			onlineBList0.add(mp7);
			
			Map mp8 = new HashMap();
			mp8.put("name", k);
			mp8.put("data", onlineUList.toArray());
			onlineUList0.add(mp8);
			
			Map mp9 = new HashMap();
			mp9.put("name", k);
			mp9.put("data", offlineBList.toArray());
			offlineBList0.add(mp9);
			
			Map mp10 = new HashMap();
			mp10.put("name", k);
			mp10.put("data", offlineUList.toArray());
			offlineUList0.add(mp10);
			
			Map mp11 = new HashMap();
			mp11.put("name", k);
			mp11.put("data", inactiveBList.toArray());
			inactiveBList0.add(mp11);
			
			Map mp12 = new HashMap();
			mp12.put("name", k);
			mp12.put("data", inactiveUList.toArray());
			inactiveUList0.add(mp12);
		}
		Map mp = new HashMap();
		if("d".equals(mord)){
			mp.put("activeUsers", activeList0);
			mp.put("onlineBookedUsers", onlineBList0);
			mp.put("onlineUnbookUsers", onlineUList0);
			mp.put("bookUsers", bookList0);
			mp.put("hdstbs", hdstbList0);
			mp.put("vodUsers", vodList0);
			mp.put("dvbUsers", dvbList0);
			mp.put("bbUsers", bbList0);
			mp.put("customers", customerList0);
		} else if("m".equals(mord)) {
			mp.put("activeUsers", activeList0);
			mp.put("onlineBookedUsers", onlineBList0);
			mp.put("onlineUnbookUsers", onlineUList0);
			mp.put("offlineBookedUsers", offlineBList0);
			mp.put("offlineUnbookUsers", offlineUList0);
			mp.put("inactiveOnlineUsers", inactiveBList0);
			mp.put("inactiveOfflineUsers", inactiveUList0);
		}
		return mp;
		
	}

	private Map getPercentMap(Map<String,VodArea> aa ,String mord){
		List<Object[]> customerList = new ArrayList();
		List<Object[]> bbList = new ArrayList();
		List<Object[]> dvbList = new ArrayList();
		List<Object[]> vodList = new ArrayList();
		List<Object[]> hdstbList = new ArrayList();
		List<Object[]> bookList = new ArrayList();
		List<Object[]> activeList = new ArrayList();

		List<Object[]> onlineBList = new ArrayList();
		List<Object[]> onlineUList = new ArrayList();
		List<Object[]> offlineBList = new ArrayList();
		List<Object[]> offlineUList = new ArrayList();
		List<Object[]> inactiveBList = new ArrayList();
		List<Object[]> inactiveUList = new ArrayList();
		
		for(String k : aa.keySet()){
			customerList.add(new Object[]{k, aa.get(k).getCustomers()});
			bbList.add(new Object[]{k, aa.get(k).getBbUsers()});
			dvbList.add(new Object[]{k, aa.get(k).getDvbUsers()});
			vodList.add(new Object[]{k, aa.get(k).getVodUsers()});
			hdstbList.add(new Object[]{k, aa.get(k).getHdstbs()});
			bookList.add(new Object[]{k, aa.get(k).getBookUsers()});
			activeList.add(new Object[]{k, aa.get(k).getActiveUsers()});
			
			onlineBList.add(new Object[]{k, aa.get(k).getOnlineBookedUsers()});
			onlineUList.add(new Object[]{k, aa.get(k).getOnlineUnbookUsers()});
			offlineBList.add(new Object[]{k, aa.get(k).getOfflineBookedUsers()});
			offlineUList.add(new Object[]{k, aa.get(k).getOfflineUnbookUsers()});
			inactiveBList.add(new Object[]{k, aa.get(k).getInactiveOnlineUsers()});
			inactiveUList.add(new Object[]{k, aa.get(k).getInactiveOfflineUsers()});
		}
		
		Map mp = new HashMap();
		mp.put("activeUsers", activeList);
		mp.put("onlineBookedUsers", onlineBList);
		mp.put("onlineUnbookUsers", onlineUList);
		if("d".equals(mord)){
			mp.put("bookUsers", bookList);
			mp.put("hdstbs", hdstbList);
			mp.put("vodUsers", vodList);
			mp.put("dvbUsers", dvbList);
			mp.put("bbUsers", bbList);
			mp.put("customers", customerList);
		} else {
			mp.put("offlineBookedUsers", offlineBList);
			mp.put("offlineUnbookUsers", offlineUList);
			mp.put("inactiveOnlineUsers", inactiveBList);
			mp.put("inactiveOfflineUsers", inactiveUList);
		}
		return mp;
	}
	
	/**
	 * 查用户清单
	 * @param va
	 * @param getWhat
	 * @param ph
	 * @return
	 */
	@RequestMapping("/getVodAreaDetail")
	@ResponseBody
	public DataGrid getVodAreaDetail(HttpServletRequest request, VodAreaCondition va, String getWhat, PageHelper ph) {
		//logger.info("queryMonthParam="+queryMonthParam);
		if(ph.getRows() <= 0){
			ph.setRows(20);
		}
		if(ph.getPage() <= 0){
			ph.setPage(1);
		}
		
		logger.info("getWhat="+getWhat);
		logger.info("VodArea:"+FastJsonUtils.obj2json(va));
		logger.info("===================");

		AuthorityHelper.limitVodAreaAuth(request, va, bossService);
		
		PageResult pr = null;
		
		pr = reportService.getUserInfoDetail(va, ph, getWhat);
		
//		try{	//根据getWhat自动映射为方法调用
//			Method method = reportService.getClass().getMethod(getWhat, new Class[] { VodArea.class, PageHelper.class });
//			pr = (PageResult)method.invoke(reportService, new Object[]{va, ph});
//		} catch(Exception ex){
//			ex.printStackTrace();
//		}
		
		if(pr == null){
			return new DataGrid();
		}
		logger.info(FastJsonUtils.obj2json(new DataGrid(pr)));
		return new DataGrid(pr);
	}
	
	/**
	 * 下载用户清单
	 * @param request
	 * @param response
	 * @param va
	 * @param getWhat
	 */
	@RequestMapping("/downLoadVodAreaDetail")
	public void downLoadVodAreaDetail(HttpServletRequest request,HttpServletResponse response,VodAreaCondition va, String getWhat) {

		AuthorityHelper.limitVodAreaAuth(request, va, bossService);
		PageResult pr = null;
		pr = reportService.getUserInfoDetail(va, null, getWhat);
		if(pr == null){
			return;
		}
		
		List<Object[]> dataList = new ArrayList();
		if(pr.getRows()!=null && !pr.getRows().isEmpty()){
			if("getActives".equals(getWhat)){
				dataList.add(new Object[]{
						"用户编号","客户编号","客户名称","客户类型","机顶盒","开户日期","街道","社区","小区","具体地址","电话","手机","最大截止日期","点播次数","登录次数"
				});
				List<BossUserInfo> ls = pr.getRows();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				for(BossUserInfo bo : ls){
					dataList.add(new Object[]{
							bo.getUserId(),
							bo.getCustomerId(),
							bo.getCustomerName(),
							bo.getCustomerType(),
							bo.getStb(),
							(bo.getCrTime()==null?"":sdf.format(bo.getCrTime())),
							bo.getTown(),
							bo.getCommunity(),
							bo.getVillage(),
							bo.getFulladdress(),bo.getPhone(),bo.getMobile(),
							bo.getMaxEndTime(),bo.getActiveTimes(),bo.getOnlineTimes()
						});
				}
			}
			
			else if("getCustomers".equals(getWhat)){
				dataList.add(new Object[]{
						"用户编号","客户编号","客户类型","机顶盒","开户日期","街道","社区","小区","具体地址"					
				});
				
				//bc.customerid,bc.servicecardid,bc.customername,bc.customertype,bc.town,bc.community,bc.village,bc.fulladdress 
				List<BossCustomerInfo> ls = pr.getRows();
				for(BossCustomerInfo bo : ls){
					dataList.add(new Object[]{
						bo.getCustomerId(),
						bo.getServiceCardId(),
						bo.getCustomerName(),
						bo.getCustomerType(),
						bo.getTown(),
						bo.getCommunity(),
						bo.getVillage(),
						bo.getFulladdress()						
					});
				}
			}
			
			else if(getWhat.indexOf("get")==0 
			&& "Users".equals(getWhat.substring(getWhat.length()-5, getWhat.length()))){
				dataList.add(new Object[]{
						"用户编号","客户编号","客户类型","机顶盒","开户日期","街道","社区","小区","具体地址","电话","手机"
				});
				List<BossUserInfo> ls = pr.getRows();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				for(BossUserInfo bo : ls){
					dataList.add(new Object[]{
						bo.getUserId(),
						bo.getCustomerId(),
						bo.getCustomerType(),
						bo.getStb(),
						(bo.getCrTime()==null?"":sdf.format(bo.getCrTime())),
						bo.getTown(),
						bo.getCommunity(),
						bo.getVillage(),
						bo.getFulladdress(),bo.getPhone(),bo.getMobile()
					});
				}
			}
		}
		
		if(!dataList.isEmpty()){
			ResponseFileWriter.writeExcel(response, getWhat, dataList);
		}
	}
	
	
	/**
	 * 查单个用户信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/getUser/{userId}")
	public String getUser(HttpServletRequest request,@PathVariable String userId) {
		//logger.info("queryMonthParam="+queryMonthParam);
		if(userId!=null){
			BossUserInfo u = reportService.getUser(Long.parseLong(userId));
			if(u!=null){
			request.setAttribute("user", u);
			return "/report/userDetail";}
		}
		return null;
	}
	
	/**
	 * 查询单个用户的登录清单
	 * @param id
	 * @return
	 */
	@RequestMapping("/getSelectEpglog")
	@ResponseBody
	public String getSelectEpglog(HttpServletRequest request,EpgLog e,PageHelper ph) {
		//logger.info("queryMonthParam="+queryMonthParam);
		if(e!=null)
		{
			PageResult pr=new PageResult();
			if(e.getRepDate1()==null){
				return FastJsonUtils.obj2json(new DataGrid(pr));
			}
			pr =  vodLogsService.getSelectEpglog(e,ph);
			logger.info(FastJsonUtils.obj2json(new DataGrid(pr)));
			return (FastJsonUtils.obj2json(new DataGrid(pr)));
		}
		return null;
	}
	
	/**
	 * 查询单个用户的点播清单
	 * @param request
	 * @param w
	 * @param ph
	 * @return
	 */
	@RequestMapping("/getSelectWasunlog")
	@ResponseBody
	public String getSelectWasunlog(HttpServletRequest request,WasunLog w,PageHelper ph) {
		//logger.info("queryMonthParam="+queryMonthParam);
		if(w!=null)
		{
			PageResult pr=new PageResult();
			if(w.getRepDate1()==null){
				return FastJsonUtils.obj2json(new DataGrid(pr));
			}
			pr =  vodLogsService.getSelectWasunlog(w,ph);
			logger.info(FastJsonUtils.obj2json(new DataGrid(pr)));
			return (FastJsonUtils.obj2json(new DataGrid(pr)));
		}
		return null;
	}

	@RequestMapping("/getUserCatergoryChart")
	@ResponseBody
	public String getCatergoryChart(HttpServletRequest request, WasunLog w) {
		if(w!=null) {
			PageResult pr=new PageResult();
			if(w.getRepDate1()==null){
				return FastJsonUtils.obj2json(new DataGrid(pr));
			}
		    double sumTimes1=0;
		    long w1Times=0;		    
		    double sumTimes2=0;
		    long w2Times=0;
			List<WasunLog> ls1 =  vodLogsService.getCatergoryFrequency(w);
			List<WasunLog> ls2 =  vodLogsService.getCatergoryDuration(w);
			List<Object[]> la1 = new ArrayList();
			List<Object[]> la2 = new ArrayList();
			for(WasunLog wl : ls1){
				sumTimes1=sumTimes1+wl.getTimes();
			}
			for(WasunLog wl : ls2){
				sumTimes2=sumTimes2+wl.getTimes();
			}
			for(WasunLog wl : ls1){				
				if(ls1.size()>15&&wl.getTimes()/sumTimes1<0.01){
					w1Times=w1Times+wl.getTimes();					
				}else{
				la1.add(new Object[]{wl.getCatergory(),wl.getTimes()});
			}
				
			}
			la1.add(new Object[]{"其它",w1Times});
			for(WasunLog wl : ls2){
				if(ls2.size()>15&&wl.getTimes()/sumTimes2<0.01){
					w2Times=w2Times+wl.getTimes();					
				}else{
				la2.add(new Object[]{wl.getCatergory(),wl.getTimes()});
			}
			}
			la2.add(new Object[]{"其它",w2Times});
			Map mp = new HashMap();
			mp.put("frequency", la1);
			mp.put("duration", la2);
			return (FastJsonUtils.obj2json(mp));
		}
		return null;
	}

	@RequestMapping("/getUserEverydayChart")
	@ResponseBody
	public String getEverydayChart(HttpServletRequest request, WasunLog w) {
		if(w!=null) {
			PageResult pr=new PageResult();
			if(w.getRepDate1()==null){
				return FastJsonUtils.obj2json(new DataGrid(pr));
			}
			List<WasunLog> ls1 =  vodLogsService.getEverydayFrequency(w);
			List<WasunLog> ls2 =  vodLogsService.getEverydayDuration(w);
			List<Long> la1 = new ArrayList();
			List<Long> la2 = new ArrayList();
			for(WasunLog wl : ls1){
				la1.add(wl.getTimes());
			}
			for(WasunLog wl : ls2){
				la2.add(wl.getTimes());
			}
			Map mp = new HashMap();
			String fromDate = new SimpleDateFormat("yyyy/MM/dd").format(w.getRepDate1());
			mp.put("fromDate", fromDate);
			mp.put("frequency", la1.toArray());
			mp.put("duration", la2.toArray());
			return (FastJsonUtils.obj2json(mp));
		}
		return null;
	}

}
