package com.ndtv.vodstat.sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.ndtv.vodstat.common.pagemodel.SessionInfo;
import com.ndtv.vodstat.common.pagemodel.Tree;
import com.ndtv.vodstat.common.util.ConfigUtil;
import com.ndtv.vodstat.core.dao.IBaseDao;
import com.ndtv.vodstat.sys.entity.SysCode;
import com.ndtv.vodstat.sys.entity.SysResource;
import com.ndtv.vodstat.sys.entity.SysRole;
import com.ndtv.vodstat.sys.entity.SysUser;
import com.ndtv.vodstat.sys.service.ISysResourceService;
import com.ndtv.vodstat.sys.service.ISysRoleService;


@Service
public class SysResourceServiceImpl implements ISysResourceService {

	@Resource
	private IBaseDao<SysCode> resourceTypeDao;

	@Resource
	private IBaseDao<SysResource> resourceDao;
	
	@Resource
	private IBaseDao<SysUser> userDao;
	
	@Resource
	private ISysRoleService roleService;
	
	private final long codeIdResourceTypeMenu = ConfigUtil.getLong("sysCode_resourceType_menu");

	@Override
	public List<Tree> tree(SessionInfo sessionInfo,Long topMenuId) {
		long resourceTypeId = codeIdResourceTypeMenu;
		
		List<SysResource> l = null;
		List<Tree> lt = new ArrayList<Tree>();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("resourceTypeId", resourceTypeId);// 菜单类型的资源

		if(topMenuId != null){
			params.put("topMenuId", topMenuId);// 菜单类型的资源
		}
		
		if (sessionInfo != null) {
			params.put("userId", sessionInfo.getId());// 自查自己有权限的资源
			l = resourceDao.find("select distinct t from SysResource t join fetch t.resourceType type join fetch t.roles role join role.users user"
					+ " where type.id = :resourceTypeId and user.id = :userId"
					+ (topMenuId == null?"":" and t.topMenu.id = :topMenuId")
					+ " order by t.seq", params);
		} else {
			l = resourceDao.find("select distinct t from SysResource t join fetch t.resourceType type"
					+ " where type.id = :resourceTypeId"
					+ (topMenuId == null?"":" and t.topMenu.id = :topMenuId")
					+ " order by t.seq", params);
		}

		if (l != null && l.size() > 0) {
			for (SysResource r : l) {
				Tree tree = new Tree();
				BeanUtils.copyProperties(r, tree);
				if (r.getResource() != null) {	//父节点不为空
					tree.setPid(r.getResource().getId());
				}
				tree.setText(r.getResourceName());
				tree.setIconCls(r.getIcon());
				Map<String, Object> attr = new HashMap<String, Object>();
				attr.put("url", r.getUrl());
				tree.setAttributes(attr);
				lt.add(tree);
			}
		}
		return lt;
	}

	@Override
	public List<SysResource> treeGrid(SessionInfo sessionInfo) {
		List<SysResource> l = null;
		List<SysResource> lr = new ArrayList<SysResource>();

		Map<String, Object> params = new HashMap<String, Object>();
		if (sessionInfo != null) {
			params.put("userId", sessionInfo.getId());// 自查自己有权限的资源
			l = resourceDao.find("select distinct t from SysResource t join fetch t.resourceType type join fetch t.roles role join role.users user where user.id = :userId order by t.seq", params);
		} else {
			l = resourceDao.find("select distinct t from SysResource t join fetch t.resourceType type order by t.seq", params);
		}

		if (l != null && l.size() > 0) {
			for (SysResource t : l) {
				if (t.getResource() != null) {
					t.setPid(t.getResource().getId());
					t.setPname(t.getResource().getResourceName());
					t.setPcode(t.getResourceCode());
				}
				t.setTypeId(t.getResourceType().getId());
				t.setTypeName(t.getResourceType().getCodeContent());
				if (t.getIcon() != null && !t.getIcon().equalsIgnoreCase("")) {
					t.setIconCls(t.getIcon());
				}
				lr.add(t);
			}
		}

		return lr;
	}

	@Override
	public List<Tree> allTree(SessionInfo sessionInfo) {
		List<SysResource> l = null;
		List<Tree> lt = new ArrayList<Tree>();

		Map<String, Object> params = new HashMap<String, Object>();
		if (sessionInfo != null) {
			params.put("userId", sessionInfo.getId());// 查自己有权限的资源
			l = resourceDao.find("select distinct t from SysResource t join fetch t.resourceType type join fetch t.roles role join role.users user where user.id = :userId order by t.seq", params);
		} else {
			l = resourceDao.find("select distinct t from SysResource t join fetch t.resourceType type order by t.seq", params);
		}

		if (l != null && l.size() > 0) {
			for (SysResource r : l) {
				Tree tree = new Tree();
				BeanUtils.copyProperties(r, tree);
				if (r.getResource() != null) {	//父节点不为空
					tree.setPid(r.getResource().getId());
				}
				tree.setText(r.getResourceName());
				tree.setIconCls(r.getIcon());
				Map<String, Object> attr = new HashMap<String, Object>();
				attr.put("url", r.getUrl());
				tree.setAttributes(attr);
				lt.add(tree);
			}
		}
		return lt;
	}

	@Override
	public void add(SysResource resource, SessionInfo sessionInfo) {
		if (resource.getTopMenuId() != null) {
			resource.setTopMenu(resourceTypeDao.get(SysCode.class, resource.getTopMenuId()));
		}
		if (resource.getTypeId() != null) {
			resource.setResourceType(resourceTypeDao.get(SysCode.class, resource.getTypeId()));
		}
		if (resource.getPid() != null) {
			resource.setResource(resourceDao.get(SysResource.class, resource.getPid()));
			//子节点的顶级菜单与父节点一致
			if(resource.getResource().getTopMenu() != null){
				resource.setTopMenu(resource.getResource().getTopMenu());
			}
		}
		resourceDao.save(resource);

		// 由于当前用户所属的角色，没有访问新添加的资源权限，所以在新添加资源的时候，将当前资源授权给当前用户的所有角色，以便添加资源后在资源列表中能够找到
		SysUser user = userDao.get(SysUser.class, sessionInfo.getId());
		Set<SysRole> roles = user.getRoles();
		for (SysRole r : roles) {
			r.getResources().add(resource);
		}
	}

	@Override
	public void delete(Long id) {
		SysResource t = resourceDao.get(SysResource.class, id);
		del(t);
	}

	private void del(SysResource t) {
		if (t.getResources() != null && t.getResources().size() > 0) {
			for (SysResource r : t.getResources()) {
				del(r);
			}
		}
		if(t.getRoles() != null && !t.getRoles().isEmpty()){
			for(SysRole r : t.getRoles()){
				roleService.deleteResource(r.getId(), t.getId());
			}
		}
		resourceDao.delete(t);
	}

	@Override
	public void update(SysResource resource) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", resource.getId());
		SysResource t = resourceDao.get("select distinct t from SysResource t where t.id = :id", params);
		if (t != null) {
			BeanUtils.copyProperties(resource, t);
			if (resource.getTypeId() != null) {
				t.setResourceType(resourceTypeDao.get(SysCode.class,resource.getTypeId()));// 赋值资源类型
			}
			if (resource.getTopMenuId() != null) {
				t.setTopMenu(resourceTypeDao.get(SysCode.class, resource.getTopMenuId()));
			}
			if (resource.getPid() != null) {// 说明前台选中了上级资源
				SysResource pt = resourceDao.get(SysResource.class, resource.getPid());
				isChildren(t, pt);// 说明要将当前资源修改到当前资源的子/孙子资源下
				t.setResource(pt);
				if(pt.getTopMenu() != null){
					t.setTopMenu(pt.getTopMenu());
					resourceDao.save(t);
				}
			} else {
				t.setResource(null);// 前台没有选中上级资源，所以就置空
			}
		}
	}

	/**
	 * 判断是否是将当前节点修改到当前节点的子节点
	 * 
	 * @param t 当前节点
	 * @param pt 要修改到的节点
	 * @return
	 */
	private boolean isChildren(SysResource t, SysResource pt) {
		if (pt != null && pt.getResource() != null) {
			if (pt.getResource().getId() == t.getId()) {
				pt.setResource(null);
				return true;
			} else {
				return isChildren(t, pt.getResource());
			}
		}
		return false;
	}

	@Override
	public SysResource get(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		SysResource t = resourceDao.get("from SysResource t left join fetch t.resource resource left join fetch t.resourceType resourceType where t.id = :id", params);
		if (t.getResource() != null) {
			t.setPid(t.getResource().getId());
			t.setPname(t.getResource().getResourceName());
		}
		t.setTypeId(t.getResourceType().getId());
		t.setTypeName(t.getResourceType().getCodeContent());
		return t;
	}

}
