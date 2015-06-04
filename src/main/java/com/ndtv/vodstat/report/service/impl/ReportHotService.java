package com.ndtv.vodstat.report.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.ndtv.vodstat.common.pagemodel.Page;
import com.ndtv.vodstat.common.pagemodel.PageHelper;
import com.ndtv.vodstat.common.pagemodel.PageResult;
import com.ndtv.vodstat.report.dao.ReportHotMapper;
import com.ndtv.vodstat.report.model.AreaCondition;
import com.ndtv.vodstat.report.model.AreaDateReport;
import com.ndtv.vodstat.report.model.TopResult;
import com.ndtv.vodstat.report.model.VodAreaCondition;
import com.ndtv.vodstat.report.service.IReportHotService;
import com.ndtv.vodstat.report.model.VodArea;
@Service
public class ReportHotService implements IReportHotService {
	
	@Resource
	private ReportHotMapper hotMapper;
	public PageResult findHotUserByTime(VodAreaCondition vv, PageHelper ph){
		List<TopResult> ls0 = new ArrayList();
		if(vv.getBookDate1() != null || vv.getBookDate2() != null){
			List<TopResult> ls1= hotMapper.findHotUserByTime(vv);
			for(TopResult vx : ls1){
				ls0.add((TopResult)vx.clone());
			}
		}
		PageResult pr = new PageResult();
		pr.setRows(ls0);
		pr.setTotal(ls0.size());
		for(TopResult v : ls0){
			v.setCondition((VodAreaCondition)vv.clone());
		}
		return pr;
		
	}
	public PageResult findHotUserByTimes(VodAreaCondition vv, PageHelper ph){
		List<TopResult> ls0 = new ArrayList();
		if(vv.getBookDate1() != null || vv.getBookDate2() != null){
			List<TopResult> ls1= hotMapper.findHotUserByTimes(vv);
			for(TopResult vx : ls1){
				ls0.add((TopResult)vx.clone());
			}
		}	
		for(TopResult v : ls0){
			v.setCondition((VodAreaCondition)vv.clone());
		}
		PageResult pr = new PageResult();
		pr.setRows(ls0);
		pr.setTotal(ls0.size());
		return pr;
	}
	public PageResult findHotCommunityByTime(VodAreaCondition vv, PageHelper ph){
		List<TopResult> ls0 = new ArrayList();
		if(vv.getBookDate1() != null || vv.getBookDate2() != null){
			List<TopResult> ls1= hotMapper.findHotCommunityByTime(vv);
			for(TopResult vx : ls1){
				ls0.add((TopResult)vx.clone());
			}
		}
		PageResult pr = new PageResult();
		pr.setRows(ls0);
		pr.setTotal(ls0.size());
		for(TopResult v : ls0){
			v.setCondition((VodAreaCondition)vv.clone());
		}
		return pr;
		
	}
	public PageResult findHotCommunityByTimes(VodAreaCondition vv, PageHelper ph){
		List<TopResult> ls0 = new ArrayList();
		if(vv.getBookDate1() != null || vv.getBookDate2() != null){
			List<TopResult> ls1= hotMapper.findHotCommunityByTimes(vv);
			for(TopResult vx : ls1){
				ls0.add((TopResult)vx.clone());
			}
		}	
		for(TopResult v : ls0){
			v.setCondition((VodAreaCondition)vv.clone());
		}
		PageResult pr = new PageResult();
		pr.setRows(ls0);
		pr.setTotal(ls0.size());
		return pr;
	}
	
	
	public List<TopResult> findHotProgramsByTime(VodAreaCondition vv){		
		List<TopResult> ls0 = new ArrayList();
		if(vv.getBookDate1() != null || vv.getBookDate2() != null){
			List<TopResult> ls1= hotMapper.findHotProgramsByTime(vv);
			for(TopResult vx : ls1){
				ls0.add((TopResult)vx.clone());
			}
		}	
		for(TopResult v : ls0){
			v.setCondition((VodAreaCondition)vv.clone());
		}
		return ls0;
	}
	public List<TopResult> findHotProgramsByTimes(VodAreaCondition vv){
		List<TopResult> ls0 = new ArrayList();
		if(vv.getBookDate1() != null || vv.getBookDate2() != null){
			List<TopResult> ls1= hotMapper.findHotProgramsByTimes(vv);
			for(TopResult vx : ls1){
				ls0.add((TopResult)vx.clone());
			}
		}	
		for(TopResult v : ls0){
			v.setCondition((VodAreaCondition)vv.clone());
		}
		return ls0;
	}
	public List<TopResult> findHotTypesByTime(VodAreaCondition vv){
		List<TopResult> ls0 = new ArrayList();
		if(vv.getBookDate1() != null || vv.getBookDate2() != null){
			List<TopResult> ls1= hotMapper.findHotTypesByTime(vv);
			for(TopResult vx : ls1){
				ls0.add((TopResult)vx.clone());
			}
		}	
		for(TopResult v : ls0){
			v.setCondition((VodAreaCondition)vv.clone());
		}
		return ls0;
	}
	public List<TopResult> findHotTypesByTimes(VodAreaCondition vv){
		List<TopResult> ls0 = new ArrayList();
		if(vv.getBookDate1() != null || vv.getBookDate2() != null){
			List<TopResult> ls1= hotMapper.findHotTypesByTimes(vv);
			for(TopResult vx : ls1){
				ls0.add((TopResult)vx.clone());
			}
		}	
		for(TopResult v : ls0){
			v.setCondition((VodAreaCondition)vv.clone());
		}
		return ls0;
	}
	
	public PageResult findManagerViewData(AreaCondition condition, PageHelper ph){
		List<AreaDateReport> ls0 = new ArrayList();
		PageResult pr = new PageResult();
		if(condition.getRepDate1() != null || condition.getRepDate2() != null){
			ls0= hotMapper.findManagerViewData(condition);
		}			
		pr.setRows(ls0);
		pr.setTotal(ls0.size());		
		return pr;
	}
	
	public List<AreaDateReport> getManagerViewLine(AreaCondition condition){
		List<AreaDateReport> ls0 = new ArrayList();
		if(condition.getRepDate1() != null || condition.getRepDate2() != null){
			ls0= hotMapper.findManagerViewData(condition);
		}			
		return ls0;
	}
	
	public List<AreaDateReport> getManagerViewPie(AreaCondition condition){
		List<AreaDateReport> ls0 = new ArrayList();
		if(condition.getRepDate1() != null || condition.getRepDate2() != null){
			ls0= hotMapper.findManagerViewPie(condition);
		}			
		return ls0;
	}
}
