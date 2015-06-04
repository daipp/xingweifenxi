package com.ndtv.vodstat.sys.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ndtv.vodstat.common.pagemodel.PageHelper;
import com.ndtv.vodstat.common.pagemodel.PageResult;
import com.ndtv.vodstat.core.dao.IBaseDao;
import com.ndtv.vodstat.sys.entity.SysNotice;
import com.ndtv.vodstat.sys.entity.SysResource;
import com.ndtv.vodstat.sys.entity.SysRole;
import com.ndtv.vodstat.sys.entity.SysUser;
import com.ndtv.vodstat.sys.service.ISysNoticeService;

@Service
public class SysNoticeImpl implements ISysNoticeService {
	
	@Resource
	private IBaseDao<SysNotice> noticeDao;
	@Override
	public List<SysNotice> getNotice(){
	
		
		List<SysNotice> tl = noticeDao.find("from SysNotice t");
		List<SysNotice> ul = new ArrayList<SysNotice>();
		if (tl != null && tl.size() > 0) {
			for (SysNotice t : tl) {
				SysNotice u = new SysNotice();
				u=t;
				ul.add(u);
			}
		}
		return ul;
	};
	@Override
	public void add(SysNotice notice){
		notice.setTime(new Date());
		noticeDao.save(notice);
	};
	
	@Override
	public void update(SysNotice notice){
		/*Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", notice.getTitle());
		params.put("name", notice.getContent());*/
		notice.setTime(new Date());
		noticeDao.update(notice);
	};
	
	@Override
	public void delete(Long id){
		noticeDao.delete(SysNotice.class,id);
	};
	
	@Override
	public SysNotice get(Long id){
		return noticeDao.get(SysNotice.class, id);
	};
	
	@Override
	public List<SysNotice> treeGrid(){

		List<SysNotice> tl = noticeDao.find("from SysNotice t");
		List<SysNotice> ul = new ArrayList<SysNotice>();
		if (tl != null && tl.size() > 0) {
			for (SysNotice t : tl) {
				SysNotice u = new SysNotice();
				u=t;
				ul.add(u);
			}
		}
		return ul;
	};
	
	
	@Override
	public PageResult findPage(SysNotice notice, PageHelper ph) {
		List<SysNotice> ul = new ArrayList<SysNotice>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from SysNotice t ";
		List<SysNotice> l = noticeDao.find(hql + whereHql(notice, params) + orderHql(ph), params, ph.getPage(), ph.getRows());
		if (l != null && l.size() > 0) {
			for (SysNotice t : l) {
				SysNotice u = new SysNotice();
				u=t;
				ul.add(u);
			}
		}
		PageResult pr = new PageResult();
		pr.setRows(ul);
		pr.setTotal(noticeDao.count("select count(*) " + hql + whereHql(notice, params), params));
		return pr;
	}

	private String whereHql(SysNotice notice, Map<String, Object> params) {
		String hql = "";
		if (notice != null) {
			hql += " where 1=1 ";
			if (notice.getTitle() != null) {
				hql += " and t.title like :tit";
				params.put("tit", "%%" + notice.getTitle() + "%%");
			}
		/*	if (notice.getCreateTimeBegin() != null) {
				hql += " and t.createTime >= :createdatetimeStart";
				params.put("createdatetimeStart", user.getCreateTimeBegin());
			}
			if (notice.getCreateTimeEnd() != null) {
				hql += " and t.createTime <= :createdatetimeEnd";
				params.put("createdatetimeEnd", user.getCreateTimeEnd());
			}
			if (notice.getModifyTimeBegin()!= null) {
				hql += " and t.modifyTime >= :modifydatetimeStart";
				params.put("modifydatetimeStart", user.getModifyTimeBegin());
			}
			if (notice.getModifyTimeEnd() != null) {
				hql += " and t.modifyTime <= :modifydatetimeEnd";
				params.put("modifydatetimeEnd", notice.getModifyTimeEnd());
			}*/
		}
		return hql;
	}
	private String orderHql(PageHelper ph) {
		String orderString = "";
		if (ph.getSort() != null && ph.getOrder() != null) {
			orderString = " order by t." + ph.getSort() + " " + ph.getOrder();
		}
		return orderString;
	}
}
