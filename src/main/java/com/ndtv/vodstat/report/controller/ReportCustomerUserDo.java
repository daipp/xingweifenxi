package com.ndtv.vodstat.report.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
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
import com.ndtv.vodstat.common.pagemodel.SessionInfo;
import com.ndtv.vodstat.common.util.ConfigUtil;
import com.ndtv.vodstat.common.util.DateFunctions;
import com.ndtv.vodstat.common.util.FastJsonUtils;
import com.ndtv.vodstat.common.util.ResponseFileWriter;
import com.ndtv.vodstat.core.controller.BaseController;
import com.ndtv.vodstat.report.model.BossSysCode;
import com.ndtv.vodstat.report.model.VodArea;
import com.ndtv.vodstat.report.model.VodAreaCondition;
import com.ndtv.vodstat.report.service.BossCustomerUserService;
import com.ndtv.vodstat.report.service.BossService;
import com.ndtv.vodstat.report.service.IReportRefeeService;
import com.ndtv.vodstat.report.service.IReportVodService;


@Controller
@RequestMapping("/reportCustomerUserDo")
public class ReportCustomerUserDo extends BaseController{
	private static final Logger logger = Logger.getLogger(ReportVodDo.class);
	
	@Resource
	private BossService bossService;
	
	@Resource
	private BossCustomerUserService bossCustomerUserService;
	@Resource
	private IReportRefeeService refeeService;
	@Resource
	private IReportVodService reportService;
	
	long customerTypeId = ConfigUtil.getLong("BOSS_CODETYPE_CUSTOMERTYPE");
	long townId = ConfigUtil.getLong("BOSS_CODETYPE_TOWN");
	long communityId = ConfigUtil.getLong("BOSS_CODETYPE_COMMUNITY");
	long villageId = ConfigUtil.getLong("BOSS_CODETYPE_VILLAGE");

	private static final int STARTYEAR = 2014;
	
	/**
	 * 显示页面
	 * @param request
	 * @param mord
	 * @return
	 */
	@RequestMapping("/showCustomerUser/{mord}")
	public String showCustomerUser(HttpServletRequest request,@PathVariable String mord) {
		AuthorityHelper.initSelectionAttribute(request, bossService);

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

		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
		if ("now".equals(mord)) {
			return "/report/customerUser_now";
		}
		else{
			if ("month".equals(mord)) {
				List<String[]> mss = new ArrayList<String[]>();
				List<String[]> mes = new ArrayList<String[]>();
				Calendar cd = Calendar.getInstance();
				cd.set(Calendar.YEAR, STARTYEAR);
				cd.set(Calendar.MONTH, 0);
				cd.set(Calendar.DATE, 1);
				cd.set(Calendar.HOUR_OF_DAY, 0);
				cd.set(Calendar.MINUTE, 0);
				cd.set(Calendar.MILLISECOND, 0);
				Calendar cd2 = Calendar.getInstance();
				cd2.set(Calendar.DATE, 1);
				cd2.set(Calendar.HOUR_OF_DAY,23);
				cd2.set(Calendar.MINUTE, 59);
				cd2.set(Calendar.MILLISECOND, 59);
				System.out.print(sdf.format(cd.getTime()));
				System.out.print(sdf.format(cd2.getTime()));
				while(cd2.getTime().after(cd.getTime())){
					String msa = sdf.format(cd2.getTime());
					mss.add(new String[]{msa,msa});
					cd2.add(Calendar.MONTH, -1);
				}
				//////////////////////////////////////
				cd.set(Calendar.DATE, 31);
				cd2 = Calendar.getInstance();
				cd2.set(Calendar.DATE, 1);
				cd2.set(Calendar.HOUR_OF_DAY,23);
				cd2.set(Calendar.MINUTE, 59);
				cd2.set(Calendar.MILLISECOND, 59);
				cd2.add(Calendar.DATE, -1);
				cd2.add(Calendar.MONTH, 1);
				System.out.print(sdf.format(cd.getTime()));
				System.out.print(sdf.format(cd2.getTime()));
				while(cd2.getTime().after(cd.getTime())){
					String msa = sdf.format(cd2.getTime());
					mes.add(new String[]{msa,msa});
					cd2.set(Calendar.DATE, 1);
					cd2.add(Calendar.DATE, -1);
				}
				request.setAttribute("mss", mss);
				request.setAttribute("mes", mes);
			}
			else if("season".equals(mord)){
				String[] genSensonDate = {"03-31","06-30","09-30","12-31"};
				List<String[]> mss = new ArrayList<String[]>();
				Calendar cd = Calendar.getInstance();
				cd.set(Calendar.YEAR, STARTYEAR);
				cd.set(Calendar.MONTH, 2);
				cd.set(Calendar.DATE, 30);
				cd.set(Calendar.HOUR_OF_DAY, 0);
				cd.set(Calendar.MINUTE, 0);
				cd.set(Calendar.MILLISECOND, 0);
				Calendar cd2 = Calendar.getInstance();
				System.out.print(sdf.format(cd2.getTime()));
				int index = 0;
				if(cd2.get(Calendar.MONTH)<3){
					index =3;
					cd2.add(Calendar.YEAR, -1);
					try {
						Date d = sdf.parse(cd2.get(Calendar.YEAR) + "-" +genSensonDate[3]);
						cd2.setTime(d);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				else if(cd2.get(Calendar.MONTH)<6){
					index =0;
					try {
						Date d = sdf.parse(cd2.get(Calendar.YEAR)  + "-" +genSensonDate[0]);
						cd2.setTime(d);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				else if(cd2.get(Calendar.MONTH)<9){
					index =1;
					try {
						Date d = sdf.parse(cd2.get(Calendar.YEAR)  + "-" +genSensonDate[1]);
						cd2.setTime(d);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				else{
					index =2;
					try {
						Date d = sdf.parse(cd2.get(Calendar.YEAR)  + "-" +genSensonDate[2]);
						cd2.setTime(d);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				while(cd2.getTime().after(cd.getTime())){
					String[] msa = new String[2];
					msa[0] = sdf.format(cd2.getTime());
					msa[1] = cd2.get(Calendar.YEAR)  + "-0" + ((cd2.get(Calendar.MONTH)+1)/3)+"季度";
					mss.add(msa);
					if(index>0){
						try {
							Date d = sdf.parse(cd2.get(Calendar.YEAR)  + "-" +genSensonDate[--index]);
							cd2.setTime(d);
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					else{
						try {
							index = 3;
							cd2.add(Calendar.YEAR, -1);
							Date d = sdf.parse(cd2.get(Calendar.YEAR)  + "-" +genSensonDate[index]);
							cd2.setTime(d);
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
				}
				request.setAttribute("mss", mss);
				request.setAttribute("mes", mss);
			}
			if("year".equals(mord)){
				Calendar cd = Calendar.getInstance();
				List<String[]> mss = new ArrayList<String[]>();
				try {
					cd.setTime(sdf.parse(cd.get(Calendar.YEAR) + "-12-31"));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				while(cd.get(Calendar.YEAR)>STARTYEAR){
					mss.add(new String[]{sdf.format(cd.getTime()),cd.get(Calendar.YEAR)+""});
					cd.add(Calendar.YEAR, -1);
				}
				request.setAttribute("mss", mss);
				request.setAttribute("mes", mss);
			}
			return "/report/customerUser_stat";
		}
	}
	
	
	/**
	 *  获取表格数据
	 * @param bug
	 * @param ph
	 * @return
	 */
	@RequestMapping("/getCustomerUser/{mord}")
	@ResponseBody
	public DataGrid getCustomerUser(HttpServletRequest request,  PageHelper ph,	VodAreaCondition va,@PathVariable String mord) {
		if(va.getRepDate1() == null){
			return new DataGrid();
		}
		logger.info("=================");
		AuthorityHelper.limitVodAreaAuth(request, va, bossService);
		try {
			PageResult pr = null;
			if ("now".equals(mord)) {
				 pr = bossCustomerUserService.findCustomerUserNow(va, ph);
			} else {
				if(va.getRepDate1().after(va.getRepDate2())){
					return new DataGrid(pr);
				}
				va.setReportRange(mord);
				pr = bossCustomerUserService.findReportBOSSCustomerUser(va, ph);
			}
			logger.info(FastJsonUtils.obj2json(new DataGrid(pr)));
			return new DataGrid(pr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 *  获取数据图形数据
	 */
	@RequestMapping("/getCustomerUserChart/{mord}")
	//@ResponseBody
	public String getCustomerUserChart(HttpServletRequest request, VodAreaCondition condition, @PathVariable String mord) {
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
		if ("now".equals(mord)) {
			 pr = bossCustomerUserService.findCustomerUserNow(condition, null);
		} else{
			if(condition.getRepDate1().after(condition.getRepDate2())){
				return url;
			}
			condition.setReportRange(mord);
			pr = bossCustomerUserService.findReportBOSSCustomerUser(condition, null);
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
	
	private Map getDateSeriesMap(Map<String,List<VodArea>> aa,String mord){
		//实时
		List<Map<String,Object>> vodOpenNowList0 = new ArrayList();
		List<Map<String,Object>> vodOpenDelayList0 = new ArrayList();
		List<Map<String,Object>> vodNotOpenList0 = new ArrayList();
		List<Map<String,Object>> bbOpenNowList0 = new ArrayList();
		List<Map<String,Object>> bbOpenDelayList0 = new ArrayList();
		List<Map<String,Object>> bbNotOpenList0 = new ArrayList();
		
		//月报季报年报
		List<Map<String,Object>> customerList0 = new ArrayList();
		List<Map<String,Object>> dvbList0 = new ArrayList();
		List<Map<String,Object>> vodList0 = new ArrayList();
		List<Map<String,Object>> bbList0 = new ArrayList();
		List<Map<String,Object>> newDvbList0 = new ArrayList();
		List<Map<String,Object>> onlineVodList0 = new ArrayList();
		List<Map<String,Object>> onlineBbList0 = new ArrayList();
		List<Map<String,Object>> newVodList0 = new ArrayList();
		List<Map<String,Object>> offlineVodList0 = new ArrayList();
		List<Map<String,Object>> newBbList0 = new ArrayList();
		List<Map<String,Object>> newOnlineBbList0 = new ArrayList();
		List<Map<String,Object>> offlineBbList0 = new ArrayList();

		for(String k : aa.keySet()){
			List<VodArea> ls = aa.get(k);
			Collections.sort(ls);
			//实时
			List<Long> vodOpenNowList = new ArrayList();
			List<Long> vodOpenDelayList = new ArrayList();
			List<Long> vodNotOpenList = new ArrayList();
			List<Long> bbOpenNowList = new ArrayList();
			List<Long> bbOpenDelayList = new ArrayList();
			List<Long> bbNotOpenList = new ArrayList();
			
			//月报季报年报
			List<Long> customerList = new ArrayList();
			List<Long> dvbList = new ArrayList();
			List<Long> vodList = new ArrayList();
			List<Long> bbList = new ArrayList();
			List<Long> newDvbList = new ArrayList();
			List<Long> onlineVodList = new ArrayList();
			List<Long> onlineBbList = new ArrayList();
			List<Long> newVodList = new ArrayList();
			List<Long> offlineVodList = new ArrayList();
			List<Long> newBbList = new ArrayList();
			List<Long> newOnlineBbList = new ArrayList();
			List<Long> offlineBbList = new ArrayList();
			
			for(VodArea va : ls){
				vodOpenNowList.add(va.getVodOpenNowUsers());
				vodOpenDelayList.add(va.getVodOpenDelayUsers());
				vodNotOpenList.add(va.getVodNotOpenUsers());
				bbOpenNowList.add(va.getBbOpenNowUsers());
				bbOpenDelayList.add(va.getBbOpenDelayUsers());
				bbNotOpenList.add(va.getBbNotOpenUsers());
				
				customerList.add(va.getCustomers());
				dvbList.add(va.getDvbUsers());
				vodList.add(va.getVodUsers());
				bbList.add(va.getBbUsers());
				newDvbList.add(va.getNewDvbUsers());
				onlineVodList.add(va.getOnlineVodUsers());
				onlineBbList.add(va.getOnlineBbUsers());
				newVodList.add(va.getNewVodUsers());
				offlineVodList.add(va.getOfflineVodUsers());
				newBbList.add(va.getNewBbUsers());
				newOnlineBbList.add(va.getNewOnlineBbUsers());
				offlineBbList.add(va.getOfflineBbUsers());
			}
			Map mp0 = new HashMap();
			mp0.put("name", k);
			mp0.put("data", vodOpenNowList.toArray());
			vodOpenNowList0.add(mp0);

			Map mp1 = new HashMap();
			mp1.put("name", k);
			mp1.put("data", vodOpenDelayList.toArray());
			vodOpenDelayList0.add(mp1);

			Map mp2 = new HashMap();
			mp2.put("name", k);
			mp2.put("data", vodNotOpenList.toArray());
			vodNotOpenList0.add(mp2);
			
			Map mp3 = new HashMap();
			mp3.put("name", k);
			mp3.put("data", bbOpenNowList.toArray());
			bbOpenNowList0.add(mp3);
			
			Map mp4 = new HashMap();
			mp4.put("name", k);
			mp4.put("data", bbOpenDelayList.toArray());
			bbOpenDelayList0.add(mp4);
			
			Map mp5 = new HashMap();
			mp5.put("name", k);
			mp5.put("data", bbNotOpenList.toArray());
			bbNotOpenList0.add(mp5);
			
			Map mp6 = new HashMap();
			mp6.put("name", k);
			mp6.put("data", customerList.toArray());
			customerList0.add(mp6);
			
			Map mp7 = new HashMap();
			mp7.put("name", k);
			mp7.put("data", dvbList.toArray());
			dvbList0.add(mp7);
			
			Map mp8 = new HashMap();
			mp8.put("name", k);
			mp8.put("data", vodList.toArray());
			vodList0.add(mp8);
			
			Map mp9 = new HashMap();
			mp9.put("name", k);
			mp9.put("data", bbList.toArray());
			bbList0.add(mp9);
			
			Map mp10 = new HashMap();
			mp10.put("name", k);
			mp10.put("data", newDvbList.toArray());
			newDvbList0.add(mp10);
			
			Map mp11 = new HashMap();
			mp11.put("name", k);
			mp11.put("data", onlineVodList.toArray());
			onlineVodList0.add(mp11);
			
			Map mp12 = new HashMap();
			mp12.put("name", k);
			mp12.put("data", onlineBbList.toArray());
			onlineBbList0.add(mp12);
			
			Map mp13 = new HashMap();
			mp13.put("name", k);
			mp13.put("data", newVodList.toArray());
			newVodList0.add(mp13);
			
			Map mp14 = new HashMap();
			mp14.put("name", k);
			mp14.put("data", offlineVodList.toArray());
			offlineVodList0.add(mp14);
			
			Map mp15 = new HashMap();
			mp15.put("name", k);
			mp15.put("data", newBbList.toArray());
			newBbList0.add(mp15);
			
			Map mp16 = new HashMap();
			mp16.put("name", k);
			mp16.put("data", newOnlineBbList.toArray());
			newOnlineBbList0.add(mp16);
			
			Map mp17 = new HashMap();
			mp17.put("name", k);
			mp17.put("data", offlineBbList.toArray());
			offlineBbList0.add(mp17);
		}
		Map mp = new HashMap();
		if("now".equals(mord)){
			mp.put("vodOpenNowUsers", vodOpenNowList0);
			mp.put("vodOpenDelayUsers", vodOpenDelayList0);
			mp.put("vodNotOpenUsers", vodNotOpenList0);
			mp.put("bbOpenNowUsers", bbOpenNowList0);
			mp.put("bbOpenDelayUsers", bbOpenDelayList0);
			mp.put("bbNotOpenUsers", bbNotOpenList0);
		} else {
			mp.put("customers", customerList0);
			mp.put("dvbUsers", dvbList0);
			mp.put("vodUsers", vodList0);
			mp.put("bbUsers", bbList0);
			mp.put("newDvbUsers", newDvbList0);
			mp.put("onlineVodUsers", onlineVodList0);
			mp.put("onlineBbUsers", onlineBbList0);
			mp.put("newVodUsers", newVodList0);
			mp.put("offlineVodUsers", offlineVodList0);
			mp.put("newBbUsers", newBbList0);
			mp.put("newOnlineBbUsers", newOnlineBbList0);
			mp.put("offlineBbUsers", offlineBbList0);
		}
		return mp;
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
	
	private Map getPercentMap(Map<String,VodArea> aa ,String mord){
		//实时
		List<Object[]> vodOpenNowUsers = new ArrayList();
		List<Object[]> vodOpenDelayUsers = new ArrayList();
		List<Object[]> vodNotOpenUsers = new ArrayList();
		List<Object[]> bbOpenNowUsers = new ArrayList();
		List<Object[]> bbOpenDelayUsers = new ArrayList();
		List<Object[]> bbNotOpenUsers = new ArrayList();
		
		//月报季报年报
		List<Object[]> customers = new ArrayList();
		List<Object[]> dvbUsers = new ArrayList();
		List<Object[]> vodUsers = new ArrayList();
		List<Object[]> bbUsers = new ArrayList();
		List<Object[]> newDvbUsers = new ArrayList();
		List<Object[]> onlineVodUsers = new ArrayList();
		List<Object[]> onlineBbUsers = new ArrayList();
		List<Object[]> newVodUsers = new ArrayList();
		List<Object[]> offlineVodUsers = new ArrayList();
		List<Object[]> newBbUsers = new ArrayList();
		List<Object[]> newOnlineBbUsers = new ArrayList();
		List<Object[]> offlineBbUsers = new ArrayList();
		
		for(String k : aa.keySet()){
			vodOpenNowUsers.add(new Object[]{k, aa.get(k).getVodOpenNowUsers()});
			vodOpenDelayUsers.add(new Object[]{k, aa.get(k).getVodOpenDelayUsers()});
			vodNotOpenUsers.add(new Object[]{k, aa.get(k).getVodNotOpenUsers()});
			bbOpenNowUsers.add(new Object[]{k, aa.get(k).getBbOpenNowUsers()});
			bbOpenDelayUsers.add(new Object[]{k, aa.get(k).getBbOpenDelayUsers()});
			bbNotOpenUsers.add(new Object[]{k, aa.get(k).getBbNotOpenUsers()});
			
			customers.add(new Object[]{k, aa.get(k).getCustomers()});
			dvbUsers.add(new Object[]{k, aa.get(k).getDvbUsers()});
			vodUsers.add(new Object[]{k, aa.get(k).getVodUsers()});
			bbUsers.add(new Object[]{k, aa.get(k).getBbUsers()});
			newDvbUsers.add(new Object[]{k, aa.get(k).getNewDvbUsers()});
			onlineVodUsers.add(new Object[]{k, aa.get(k).getOnlineVodUsers()});
			onlineBbUsers.add(new Object[]{k, aa.get(k).getOnlineBbUsers()});
			newVodUsers.add(new Object[]{k, aa.get(k).getNewVodUsers()});
			offlineVodUsers.add(new Object[]{k, aa.get(k).getOfflineVodUsers()});
			newBbUsers.add(new Object[]{k, aa.get(k).getNewBbUsers()});
			newOnlineBbUsers.add(new Object[]{k, aa.get(k).getNewOnlineBbUsers()});
			offlineBbUsers.add(new Object[]{k, aa.get(k).getOfflineBbUsers()});
			
		}
		
		Map mp = new HashMap();
		if("now".equals(mord)){
			mp.put("vodOpenNowUsers", vodOpenNowUsers);
			mp.put("vodOpenDelayUsers", vodOpenDelayUsers);
			mp.put("vodNotOpenUsers", vodNotOpenUsers);
			mp.put("bbOpenNowUsers", bbOpenNowUsers);
			mp.put("bbOpenDelayUsers", bbOpenDelayUsers);
			mp.put("bbNotOpenUsers", bbNotOpenUsers);
		} else {
			mp.put("customers", customers);
			mp.put("dvbUsers", dvbUsers);
			mp.put("vodUsers", vodUsers);
			mp.put("bbUsers", bbUsers);
			mp.put("newDvbUsers", newDvbUsers);
			mp.put("onlineVodUsers", onlineVodUsers);
			mp.put("onlineBbUsers", onlineBbUsers);
			mp.put("newVodUsers", newVodUsers);
			mp.put("offlineVodUsers", offlineVodUsers);
			mp.put("newBbUsers", newBbUsers);
			mp.put("newOnlineBbUsers", newOnlineBbUsers);
			mp.put("offlineBbUsers", offlineBbUsers);
		}
		return mp;
	}
	
	
	/**
	 * 显示页面
	 * @param request
	 * @param mord
	 * @return
	 */
	@RequestMapping("/conditionCustomer/{mord}")
	public String conditionCustomer(HttpServletRequest request,@PathVariable String mord) {
		AuthorityHelper.initSelectionAttribute(request, bossService);
		if ("show".equals(mord)) {
			return "/report/customerUser_condition";
		}
		return "null";
	}
	
	/**
	 * 返回查询信息
	 * @param request
	 * @param mord
	 * @return
	 */
	@RequestMapping("/getConditionCustomer")
	@ResponseBody
	public DataGrid getConditionCustomer(HttpServletRequest request, VodAreaCondition va, PageHelper ph) {
		if(va.getConditionValue1()==null&&va.getConditionValue2()==null&&va.getCommunityIds()==null&&va.getVillageIds()==null&&va.getTownIds()==null&&va.getCustomerTypeIds()==null){
			return new DataGrid();
		}
		logger.info("=================");
		AuthorityHelper.limitVodAreaAuth(request, va, bossService);
		try{
		PageResult pr =null;
		pr=bossCustomerUserService.findConditionCustomer(va, ph);
		logger.info(FastJsonUtils.obj2json(new DataGrid(pr)));
		return new DataGrid(pr);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 下载表格数据
	 * @param request
	 * @param response
	 * @param va
	 * @param getWhat
	 */
	@RequestMapping("/downLoad/{mord}")
	public void downLoad(HttpServletRequest request,HttpServletResponse response,VodAreaCondition va, @PathVariable String mord) {

		AuthorityHelper.limitVodAreaAuth(request, va, bossService);
		PageResult pr = null;
		List<Object[]> dataList = new ArrayList();
/*
		//以下活跃度相关:
		if("vodarea_d".equals(mord)){
			pr = reportService.findAreaDateReport(va, null);
			if(pr==null){
				return;
			}
			if(pr.getRows()!=null && !pr.getRows().isEmpty()){
				dataList.add(new Object[]{
						"日期","行政区","社区","小区","客户类型","活跃用户","登录用户(在线)","登录用户(离线)",
						"在线用户","高清用户","交互型用户","基本型用户","宽带用户","客户数"
				});
				List<VodArea> ls=pr.getRows();
				for(VodArea v : ls){
					dataList.add(new Object[]{
						new SimpleDateFormat("yyyy-mm-dd").format(v.getRepDate()),	
						v.getTown(),
						v.getCommunity(),
						v.getVillage(),
						v.getCustomerType(),
						v.getActiveUsers(),
						v.getOnlineBookedUsers(),
						v.getOnlineUnbookUsers(),
						v.getBookUsers(),
						v.getHdstbs(),
						v.getVodUsers(),
						v.getDvbUsers(),
						v.getBbUsers(),
						v.getCustomers()
					});
				}
			}
		}
		if("vodarea_m".equals(mord)){
			pr = reportService.findVodAreaM(va, null);
			if(pr==null){
				return;
			}
			if(pr.getRows()!=null && !pr.getRows().isEmpty()){
				dataList.add(new Object[]{
						"日期","行政区","社区","小区","客户类型",
						"活跃用户","登录用户(在线)","登录用户(离线)",
						"未登用户(在线)","未登用户(离线)","不活跃用户(登录)","不活跃用户(未登)"
				});
				List<VodArea> ls=pr.getRows();
				for(VodArea v : ls){
					dataList.add(new Object[]{
						v.getRepDateMonth(),
						v.getTown(),
						v.getCommunity(),
						v.getVillage(),
						v.getCustomerType(),
						v.getActiveUsers(),
						v.getOnlineBookedUsers(),
						v.getOnlineUnbookUsers(),
						v.getOfflineBookedUsers(),
						v.getOfflineUnbookUsers(),
						v.getInactiveOnlineUsers(),
						v.getInactiveOfflineUsers()
					});
				}
			}
		}
*/
		if("vodarea_now".equals(mord)){
			pr = reportService.findVodAreaNow(va, null);
			if(pr==null){
				return;
			}
			if(pr.getRows()!=null && !pr.getRows().isEmpty()){
				dataList.add(new Object[]{
						"日期","行政区","社区","小区","客户类型",
						"活跃用户","登录用户(在线)","登录用户(离线)",
						"未登用户(在线)","未登用户(离线)",
						"不活跃用户(登录)","不活跃用户(未登)"
				});
				List<VodArea> ls=pr.getRows();
				for(VodArea v : ls){
					dataList.add(new Object[]{
						v.getRepDateMonth(),
						v.getTown(),
						v.getCommunity(),
						v.getVillage(),
						v.getCustomerType(),
						v.getActiveUsers(),
						v.getOnlineBookedUsers(),
						v.getOnlineUnbookUsers(),
						v.getOfflineBookedUsers(),
						v.getOfflineUnbookUsers(),
						v.getInactiveOnlineUsers(),
						v.getInactiveOfflineUsers()
					});
				}
			}
		}
		if("vodarea_active".equals(mord)){
			pr = reportService.findVodAreaActives(va, null);
			if(pr==null){
				return;
			}
			if(pr.getRows()!=null && !pr.getRows().isEmpty()){
				dataList.add(new Object[]{
						"日期","行政区","社区","小区","客户类型",
						"用户数"
				});
				List<VodArea> ls=pr.getRows();
				for(VodArea v : ls){
					dataList.add(new Object[]{
						v.getRepDateMonth(),
						v.getTown(),
						v.getCommunity(),
						v.getVillage(),
						v.getCustomerType(),
						v.getActiveUsers()
					});
				}
			}
		}
		
		//以下客户用户情况汇总相关:
		if("customerUser_now".equals(mord)){
			pr = bossCustomerUserService.findCustomerUserNow(va, null);
			if(pr==null){
				return;
			}
			if(pr.getRows()!=null && !pr.getRows().isEmpty()){
				dataList.add(new Object[]{
						"行政区","社区","小区","客户类型","新增交互且立即开通","新增交互且延期开通 ","新增交互且未开通的","新增宽带且立即开通 ","新增宽带且延期开通","新增宽带且未开通的"
				});
				List<VodArea> ls=pr.getRows();
				for(VodArea v : ls){
					dataList.add(new Object[]{
						v.getTown(),
						v.getCommunity(),
						v.getVillage(),
						v.getCustomerType(),
						v.getVodOpenNowUsers(),
						v.getVodOpenDelayUsers(),
						v.getVodNotOpenUsers(),
						v.getBbOpenNowUsers(),
						v.getBbOpenDelayUsers(),
						v.getBbNotOpenUsers(),
					});
				}
			}
		}
		if("month".equals(mord)||"season".equals(mord)||"year".equals(mord)){
			va.setReportRange(mord);
			pr = bossCustomerUserService.findReportBOSSCustomerUser(va, null);
			if(pr==null){
				return;
			}
			if(pr.getRows()!=null && !pr.getRows().isEmpty()){
				dataList.add(new Object[]{
						"日期","行政区","社区","小区","客户类型","客户数","基本型用户数","交互型用户数",
						"宽带用户数","基本型用户数净增","交互在线用户数","宽带在线用户数","交互型用户数净增","交互在线用户数净增",
						"交户离线用户数","宽带用户数净增","宽带在线用户数净增","宽带离线用户数"
				});
				List<VodArea> ls=pr.getRows();
				for(VodArea v : ls){
					dataList.add(new Object[]{
						v.getRepDate(),
						v.getTown(),
						v.getCommunity(),
						v.getVillage(),
						v.getCustomerType(),
						v.getCustomers(),
						v.getDvbUsers(),
						v.getVodUsers(),
						v.getBbUsers(),
						v.getNewDvbUsers(),
						v.getOnlineVodUsers(),
						v.getOnlineBbUsers(),
						v.getNewVodUsers(),
						v.getNewOnlineVodUsers(),
						v.getOfflineVodUsers(),
						v.getNewBbUsers(),
						v.getNewOnlineBbUsers(),
						v.getOfflineBbUsers()
						
					});
				}
			}
		}
		
		//以下续费率相关:
		if("vodarea_books".equals(mord)){
			pr=refeeService.findBooks(va, null);
			if(pr==null){
				return;
			}
			if(pr.getRows()!=null && !pr.getRows().isEmpty()){
				dataList.add(new Object[]{
						"行政区","社区","小区","客户类型","互动用户（离线订购）","互动用户（到期续订）","互动用户（未到期续订）","宽带用户（离线订购）","宽带用户（到期续订）","宽带用户（未到期续订）"
				});
				List<VodArea> ls=pr.getRows();
				for(VodArea v : ls){
					dataList.add(new Object[]{
						v.getTown(),
						v.getCommunity(),
						v.getVillage(),
						v.getCustomerType(),
						v.getVodOfflineBookUsers(),
						v.getVodExpiredBookUsers(),
						v.getVodUnExpiredBookUsers(),
						v.getBbOfflineBookUsers(),
						v.getBbExpiredBookUsers(),
						v.getBbUnExpiredBookUsers(),
					});
				}
			}
		}
		if("vodarea_expires".equals(mord)){
			pr=refeeService.findExpires(va, null);
			if(pr==null){
				return;
			}
			if(pr.getRows()!=null && !pr.getRows().isEmpty()){
				dataList.add(new Object[]{
						"行政区","社区","小区","客户类型","到期用户","订购用户"
				});
				List<VodArea> ls=pr.getRows();
				for(VodArea v : ls){
					dataList.add(new Object[]{
						v.getTown(),
						v.getCommunity(),
						v.getVillage(),
						v.getCustomerType(),
						v.getExpiredUsers(),
						v.getBookUsers(),
					});
				}
			}
		}
		
		if(!dataList.isEmpty()){
			ResponseFileWriter.writeExcel(response, mord, dataList);
		}
	}
	
}
