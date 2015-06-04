package com.ndtv.vodstat.sys.service;

import java.util.List;

import com.ndtv.vodstat.common.pagemodel.PageHelper;
import com.ndtv.vodstat.common.pagemodel.PageResult;
import com.ndtv.vodstat.common.pagemodel.SessionInfo;
import com.ndtv.vodstat.sys.entity.SysNotice;
import com.ndtv.vodstat.sys.entity.SysRole;
import com.ndtv.vodstat.sys.entity.SysUser;

public interface ISysNoticeService {

	public List<SysNotice> getNotice();

	public void add(SysNotice notice);
	
	public void update(SysNotice notice);
	
	public void delete(Long id);
	
	public SysNotice get(Long id);
	
	public List<SysNotice> treeGrid();
	
	public PageResult findPage(SysNotice notice, PageHelper ph);
}
