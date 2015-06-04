package com.ndtv.vodstat.report.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ndtv.vodstat.common.pagemodel.PageHelper;
import com.ndtv.vodstat.common.pagemodel.PageResult;
import com.ndtv.vodstat.report.dao.VodReportRefeeMapper;
import com.ndtv.vodstat.report.model.AreaMonthReport;
import com.ndtv.vodstat.report.model.VodArea;
import com.ndtv.vodstat.report.model.VodAreaCondition;
import com.ndtv.vodstat.report.service.IReportRefeeService;

@Service
public class ReportRefeeServiceImpl implements IReportRefeeService{

	private static final Logger logger = Logger.getLogger(ReportRefeeServiceImpl.class);

	@Resource
	private VodReportRefeeMapper dReportMapper;
	
	
	public PageResult findExpires(VodAreaCondition vv, PageHelper ph){
		List<VodArea> ls0 = new ArrayList();
		if(vv.getBookDate1() != null || vv.getBookDate2() != null){
			List<VodArea> ls1= dReportMapper.countRefeeBookUsers(vv);
			for(VodArea vx : ls1){
				ls0.add((VodArea)vx.clone());
			}
		}
		if(vv.getExpiredDate1() != null || vv.getExpiredDate2() != null){
			List<VodArea> ls2= dReportMapper.countRefeeExpiredUsers(vv);
			//ls0 = combineVodAreaList(ls0,ls2);
		}

		PageResult pr = new PageResult();
		pr.setRows(ls0);
		pr.setTotal(ls0.size());
		
		for(VodArea v : ls0){
			v.setCondition((VodAreaCondition)vv.clone());
		}
		return pr;
	}
	
	public PageResult findBooks(VodAreaCondition vv, PageHelper ph){
		
		List<AreaMonthReport> ls1= dReportMapper.countVodExpiredBook(vv);
		List<AreaMonthReport> ls2= dReportMapper.countVodExpiringBook(vv);
		List<AreaMonthReport> ls3= dReportMapper.countVodPrebook(vv);
		List<AreaMonthReport> ls4= dReportMapper.countBbExpiredBook(vv);
		List<AreaMonthReport> ls5= dReportMapper.countBbExpiringBook(vv);
		List<AreaMonthReport> ls6= dReportMapper.countBbPrebook(vv);
		
		List<AreaMonthReport> ls0 = new ArrayList();
		for(AreaMonthReport vx : ls1){
			ls0.add((AreaMonthReport)vx.clone());
		}
		ls0 = combineVodAreaList(ls0,ls2);
		ls0 = combineVodAreaList(ls0,ls3);
		ls0 = combineVodAreaList(ls0,ls4);
		ls0 = combineVodAreaList(ls0,ls5);
		ls0 = combineVodAreaList(ls0,ls6);

		PageResult pr = new PageResult();
		pr.setRows(ls0);
		pr.setTotal(ls0.size());
		
		for(AreaMonthReport v : ls0){
			v.setCondition((VodAreaCondition)vv.clone());
		}
		return pr;
	}
	
	private List<AreaMonthReport> combineVodAreaList(List<AreaMonthReport> ls0,List<AreaMonthReport> lsx){
		for(AreaMonthReport v2 : lsx){
			boolean hasSameKey = false;
			for(AreaMonthReport v0 : ls0){
				if(v0.isSameKey(v2)){
					v0.add(v2);
					hasSameKey = true;
				}
			}
			if(!hasSameKey){
				ls0.add((AreaMonthReport)v2.clone());
			}
		}
		return ls0;
	}
}
