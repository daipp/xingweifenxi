package com.ndtv.vodstat.sys.service;

import java.util.List;

import com.ndtv.vodstat.common.pagemodel.SessionInfo;
import com.ndtv.vodstat.common.pagemodel.Tree;
import com.ndtv.vodstat.sys.entity.SysRole;

/**
 * 角色业务逻辑
 * 
 * @author 孙宇
 * 
 */
public interface ISysRoleService {

	/**
	 * 保存角色
	 * 
	 * @param role
	 */
	public void add(SysRole role, SessionInfo sessionInfo);

	/**
	 * 获得角色
	 * 
	 * @param id
	 * @return
	 */
	public SysRole get(long id);

	/**
	 * 编辑角色
	 * 
	 * @param role
	 */
	public void update(SysRole role);

	/**
	 * 获得角色treeGrid
	 * 
	 * @return
	 */
	public List<SysRole> treeGrid(SessionInfo sessionInfo);

	/**
	 * 删除角色
	 * 
	 * @param id
	 */
	public void delete(long id);

	/**
	 * 获得角色树(只能看到自己拥有的角色)
	 * 
	 * @return
	 */
	public List<Tree> tree(SessionInfo sessionInfo);

	/**
	 * 获得角色树
	 * 
	 * @return
	 */
	public List<Tree> allTree();

	/**
	 * 为角色授权
	 * 
	 * @param role
	 */
	public void grant(SysRole role);

	public void addResource(Long roleId,Long resourceId);
	
	public void deleteResource(Long roleId,Long resourceId);
}
