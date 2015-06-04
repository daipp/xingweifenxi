package com.ndtv.vodstat.sys.service;

import java.util.List;

import com.ndtv.vodstat.common.pagemodel.SessionInfo;
import com.ndtv.vodstat.common.pagemodel.Tree;
import com.ndtv.vodstat.sys.entity.SysResource;

/**
 * 资源Service
 * 
 * @author 孙宇
 * 
 */
public interface ISysResourceService {

	/**
	 * 获得资源树(资源类型为菜单类型)
	 * 
	 * 通过用户ID判断，他能看到的资源
	 * 
	 * @param sessionInfo
	 * @return
	 */
	public List<Tree> tree(SessionInfo sessionInfo,Long topMenuId);

	/**
	 * 获得资源树(包括所有资源类型)
	 * 
	 * 通过用户ID判断，他能看到的资源
	 * 
	 * @param sessionInfo
	 * @return
	 */
	public List<Tree> allTree(SessionInfo sessionInfo);

	/**
	 * 获得资源列表
	 * 
	 * @param sessionInfo
	 * 
	 * @return
	 */
	public List<SysResource> treeGrid(SessionInfo sessionInfo);

	/**
	 * 添加资源
	 * 
	 * @param resource
	 * @param sessionInfo
	 */
	public void add(SysResource resource, SessionInfo sessionInfo);

	/**
	 * 删除资源
	 * 
	 * @param id
	 */
	public void delete(Long id);

	/**
	 * 修改资源
	 * 
	 * @param resource
	 */
	public void update(SysResource resource);

	/**
	 * 获得一个资源
	 * 
	 * @param id
	 * @return
	 */
	public SysResource get(Long id);

}
