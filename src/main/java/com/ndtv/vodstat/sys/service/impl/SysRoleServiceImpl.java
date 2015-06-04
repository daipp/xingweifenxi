package com.ndtv.vodstat.sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.ndtv.vodstat.common.pagemodel.SessionInfo;
import com.ndtv.vodstat.common.pagemodel.Tree;
import com.ndtv.vodstat.core.dao.IBaseDao;
import com.ndtv.vodstat.sys.entity.SysResource;
import com.ndtv.vodstat.sys.entity.SysRole;
import com.ndtv.vodstat.sys.entity.SysUser;
import com.ndtv.vodstat.sys.service.ISysRoleService;


@Service
public class SysRoleServiceImpl implements ISysRoleService {

	@Resource
	private IBaseDao<SysRole> roleDao;

	@Resource
	private IBaseDao<SysUser> userDao;

	@Resource
	private IBaseDao<SysResource> resourceDao;

	@Override
	public void add(SysRole role, SessionInfo sessionInfo) {
		if (role.getPid() != null) {
			role.setRole(roleDao.get(SysRole .class, role.getPid()));
		}
		roleDao.save(role);

		// 刚刚添加的角色，赋予给当前的用户
		
		SysUser user = userDao.get(SysUser.class, sessionInfo.getId());
		user.getRoles().add(role);
//		userDao.save(user);
//		role.getUsers().add(user);
		
	}

	@Override
	public SysRole get(long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		SysRole t = roleDao.get("select distinct t from SysRole  t left join fetch t.resources resource where t.id = :id", params);
		if (t != null) {
			if (t.getRole() != null) {
				t.setPid(t.getRole().getId());
				t.setPname(t.getRole().getRoleName());
			}
			Set<SysResource> s = t.getResources();
			if (s != null && !s.isEmpty()) {
				boolean b = false;
				String ids = "";
				String names = "";
				for (SysResource tr : s) {
					if (b) {
						ids += ",";
						names += ",";
					} else {
						b = true;
					}
					ids += tr.getId();
					names += tr.getResourceName();
				}
				t.setResourceIds(ids);
				t.setResourceNames(names);
			}
		}
		return t;
	}

	@Override
	public void update(SysRole role) {
		SysRole  t = get(role.getId()); //roleDao.get(SysRole.class, role.getId());
		if (t != null) {
			//BeanUtils.copyProperties(role, t);
			BeanUtils.copyProperties(role, t, new String[]{"roles","resources","users"});
			
			if (role.getPid() != null) { // 说明前台选中了上级资源
				SysRole pt = roleDao.get(SysRole.class, role.getPid());
				isChildren(t, pt);// 说明要将当前资源修改到当前资源的子/孙子资源下
				t.setRole(pt);
			} else {
				t.setRole(null);// 前台没有选中上级资源，所以就置空
			}
		}
	}

	/**
	 * 判断是否是将当前节点修改到当前节点的子节点
	 * 
	 * @param t
	 *            当前节点
	 * @param pt
	 *            要修改到的节点
	 * @return
	 */
	private boolean isChildren(SysRole t, SysRole pt) {
		if (pt != null && pt.getRole() != null) {
			if (pt.getRole().getId() == t.getId()) {
				pt.setRole(null);
				return true;
			} else {
				return isChildren(t, pt.getRole());
			}
		}
		return false;
	}

	@Override
	public List<SysRole> treeGrid(SessionInfo sessionInfo) {
		List<SysRole> tl = new ArrayList<SysRole>();
		
/*		Map<String, Object> params = new HashMap<String, Object>();
		if (sessionInfo != null) {
			params.put("userId", sessionInfo.getId());// 查自己有权限的角色
			tl = roleDao.find("select distinct t from SysRole t left join fetch t.resources resource join fetch t.users user where user.id = :userId order by t.seq", params);
		} else {
			tl = roleDao.find("select distinct t from SysRole t left join fetch t.resources resource order by t.seq");
		}
*/		
		tl = roleDao.find("select distinct t from SysRole t order by t.seq");
		
		if (tl != null && tl.size() > 0) {
			for (SysRole t : tl) {
				if (t.getRole() != null) {
					t.setPid(t.getRole().getId());
					t.setPname(t.getRole().getRoleName());
				}
				t.setIconCls("status_online");
				Set<SysResource> s = t.getResources();
				if (s != null && !s.isEmpty()) {
					boolean b = false;
					String ids = "";
					String names = "";
					for (SysResource tr : s) {
						if (b) {
							ids += ",";
							names += ",";
						} else {
							b = true;
						}
						ids += tr.getId();
						names += tr.getResourceName();
					}
					t.setResourceIds(ids);
					t.setResourceNames(names);
				}
			}
		}
		return tl;
	}

	@Override
	public List<Tree> tree(SessionInfo sessionInfo) {
		List<SysRole> l = null;
		List<Tree> lt = new ArrayList<Tree>();

/*		Map<String, Object> params = new HashMap<String, Object>();
		if (sessionInfo != null) {
			params.put("userId", sessionInfo.getId());// 查自己有权限的角色
			l = roleDao.find("select distinct t from SysRole t join fetch t.users user where user.id = :userId order by t.seq", params);
		} else {
			l = roleDao.find("from SysRole t order by t.seq");
		}
*/
		l = roleDao.find("from SysRole t order by t.seq");
		if (l != null && l.size() > 0) {
			for (SysRole t : l) {
				Tree tree = new Tree();
				BeanUtils.copyProperties(t, tree);
				tree.setText(t.getRoleName());
				tree.setIconCls("status_online");
				if (t.getRole() != null) {
					tree.setPid(t.getRole().getId());
				}
				lt.add(tree);
			}
		}
		return lt;
	}

	@Override
	public void delete(long id) {
		SysRole t = roleDao.get(SysRole.class, id);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("rId", t.getId());// 查自己有权限的角色
		
		List<SysUser> tl = userDao.find("select u from SysUser u left join u.roles r where r.id = :rId", params);
		for(SysUser u : tl){
			u.getRoles().remove(t);
			userDao.save(u);
		}
		
		del(t);
	}

	private void del(SysRole t) {
		if (t.getRoles() != null && t.getRoles().size() > 0) {
			for (SysRole r : t.getRoles()) {
				del(r);
			}
		}
		roleDao.delete(t);
	}

	@Override
	public List<Tree> allTree() {
		return this.tree(null);
	}

	@Override
	public void grant(SysRole role) {
		SysRole t = roleDao.get(SysRole.class, role.getId());
		if (role.getResourceIds() != null && !role.getResourceIds().equalsIgnoreCase("")) {
			String ids = "";
			boolean b = false;
			for (String id : role.getResourceIds().split(",")) {
				if (b) {
					ids += ",";
				} else {
					b = true;
				}
				ids += "'" + id + "'";
			}
			t.setResources(new HashSet<SysResource>(resourceDao.find("select distinct t from SysResource t where t.id in (" + ids + ")")));
		} else {
			t.setResources(null);
		}
	}
	
	@Override
	public void addResource(Long roleId,Long resourceId) {
		SysRole role = roleDao.get(SysRole.class, roleId);
		SysResource resource = resourceDao.get(SysResource.class, resourceId);
		
		role.getResources().add(resource);
		roleDao.save(role);
	}

	@Override
	public void deleteResource(Long roleId,Long resourceId) {
		SysRole role = roleDao.get(SysRole.class, roleId);
		SysResource resource = resourceDao.get(SysResource.class, resourceId);
		
		role.getResources().remove(resource);
		roleDao.save(role);
	}

}
