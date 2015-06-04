package com.ndtv.vodstat.sys.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ndtv.vodstat.common.pagemodel.PageHelper;
import com.ndtv.vodstat.common.pagemodel.PageResult;
import com.ndtv.vodstat.common.pagemodel.SessionInfo;
import com.ndtv.vodstat.common.util.MD5Util;
import com.ndtv.vodstat.core.dao.IBaseDao;
import com.ndtv.vodstat.sys.entity.SysResource;
import com.ndtv.vodstat.sys.entity.SysRole;
import com.ndtv.vodstat.sys.entity.SysUser;
import com.ndtv.vodstat.sys.service.ISysUserService;


@Service
public class SysUserServiceImpl implements ISysUserService {

	@Resource
	private IBaseDao<SysUser> userDao;

	@Resource
	private IBaseDao<SysRole> roleDao;

	@Resource
	private IBaseDao<SysResource> resourceDao;

	@Override
	public SysUser login(SysUser user) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", user.getLoginName());
		params.put("pwd1", MD5Util.md5(user.getPwd()));
		params.put("pwd2", user.getPwd());
		SysUser t = userDao.get("from SysUser t where t.loginName = :name and (t.pwd = :pwd1 or t.pwd = :pwd2)", params);
		return t;
	}

	synchronized public void add(SysUser user) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", user.getLoginName());
		if (userDao.count("select count(*) from SysUser t where t.loginName = :name", params) > 0) {
			throw new RuntimeException("登录名已存在！");
		} else {
			user.setCreateTime(new Date());
			user.setPwd(MD5Util.md5(user.getPwd()));
			userDao.save(user);
		}
	}

	synchronized public void update(SysUser user) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", user.getId());
		params.put("name", user.getLoginName());
		if (userDao.count("select count(*) from SysUser t where t.loginName = :name and t.id != :id", params) > 0) {
			throw new RuntimeException("登录名已存在！");
		} else {
			user.setCreateTime(new Date());
			user.setPwd(MD5Util.md5(user.getPwd()));
			userDao.update(user);
		}
	}

	@Override
	public void delete(Long id) {
		userDao.delete(SysUser.class, id);
	}

	@Override
	public SysUser get(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		SysUser t = userDao.get("select distinct t from SysUser t left join fetch t.roles role where t.id = :id", params);
		if (t.getRoles() != null && !t.getRoles().isEmpty()) {
			String roleIds = "";
			String roleNames = "";
			boolean b = false;
			for (SysRole role : t.getRoles()) {
				if (b) {
					roleIds += ",";
					roleNames += ",";
				} else {
					b = true;
				}
				roleIds += role.getId();
				roleNames += role.getRoleName();
			}
			t.setRoleIds(roleIds);
			t.setRoleNames(roleNames);
		}
		return t;
	}

	@Override
	public PageResult findPage(SysUser user, PageHelper ph) {
		List<SysUser> ul = new ArrayList<SysUser>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from SysUser t ";
		List<SysUser> l = userDao.find(hql + whereHql(user, params) + orderHql(ph), params, ph.getPage(), ph.getRows());
		
		if (l != null && l.size() > 0) {
			for (SysUser t : l) {
				Set<SysRole> roles = t.getRoles();
				if (roles != null && !roles.isEmpty()) {
					String roleIds = "";
					String roleNames = "";
					boolean b = false;
					for (SysRole tr : roles) {
						if (b) {
							roleIds += ",";
							roleNames += ",";
						} else {
							b = true;
						}
						roleIds += tr.getId();
						roleNames += tr.getRoleName();
					}
					t.setRoleIds(roleIds);
					t.setRoleNames(roleNames);
				}
				ul.add(t);
			}
		}
		PageResult pr = new PageResult();
		pr.setRows(ul);
		pr.setTotal(userDao.count("select count(*) " + hql + whereHql(user, params), params));
		return pr;
	}
	
	private String whereHql(SysUser user, Map<String, Object> params) {
		String hql = "";
		if (user != null) {
			hql += " where 1=1 ";
			if (user.getLoginName() != null) {
				hql += " and t.loginName like :name";
				params.put("name", "%%" + user.getLoginName() + "%%");
			}
			if (user.getCreateTimeBegin() != null) {
				hql += " and t.createTime >= :createdatetimeStart";
				params.put("createdatetimeStart", user.getCreateTimeBegin());
			}
			if (user.getCreateTimeEnd() != null) {
				hql += " and t.createTime <= :createdatetimeEnd";
				params.put("createdatetimeEnd", user.getCreateTimeEnd());
			}
			if (user.getModifyTimeBegin()!= null) {
				hql += " and t.modifyTime >= :modifydatetimeStart";
				params.put("modifydatetimeStart", user.getModifyTimeBegin());
			}
			if (user.getModifyTimeEnd() != null) {
				hql += " and t.modifyTime <= :modifydatetimeEnd";
				params.put("modifydatetimeEnd", user.getModifyTimeEnd());
			}
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


	@Override
	public void grant(String ids, SysUser user) {
		if (ids != null && ids.length() > 0) {
			List<SysRole> roles = new ArrayList<SysRole>();
			if (user.getRoleIds() != null && user.getRoleIds().length()>0) {
				for (String roleId : user.getRoleIds().split(",")) {
					roles.add(roleDao.get(SysRole.class, Long.parseLong(roleId)));
				}
			}
			for (String id : ids.split(",")) {
				if (id != null && !id.equalsIgnoreCase("")) {
					SysUser t = userDao.get(SysUser.class, Long.parseLong(id));
					t.setRoles(new HashSet<SysRole>(roles));
				}
			}
		}
	}

	@Override
	public List<String> resourceList(Long id) {
		List<String> resourceList = new ArrayList<String>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		SysUser t = userDao.get("from SysUser t join fetch t.roles role join fetch role.resources resource where t.id = :id", params);
		if (t != null) {
			Set<SysRole> roles = t.getRoles();
			if (roles != null && !roles.isEmpty()) {
				for (SysRole role : roles) {
					Set<SysResource> resources = role.getResources();
					if (resources != null && !resources.isEmpty()) {
						for (SysResource resource : resources) {
							if (resource != null && resource.getUrl() != null) {
								resourceList.add(resource.getUrl());
							}
						}
					}
				}
			}
		}
		return resourceList;
	}

	@Override
	public void updatePwd(SysUser user) {
		if (user != null && user.getPwd() != null && !user.getPwd().trim().equalsIgnoreCase("")) {
			SysUser u = userDao.get(SysUser.class, user.getId());
			u.setPwd(MD5Util.md5(user.getPwd()));
			u.setModifyTime(new Date());
		}
	}

	@Override
	public boolean updateCurrentSysUserPwd(SessionInfo sessionInfo, String oldPwd, String pwd) {
		SysUser u = userDao.get(SysUser.class, sessionInfo.getId());
		if (u.getPwd().equals(oldPwd)
		|| u.getPwd().equalsIgnoreCase(MD5Util.md5(oldPwd))) {// 说明原密码输入正确
			u.setPwd(MD5Util.md5(pwd));
			u.setModifyTime(new Date());
			return true;
		}
		return false;
	}

	@Override
	public List<SysUser> loginCombobox(String q) {
		if (q == null) {
			q = "";
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", "%%" + q.trim() + "%%");
		List<SysUser> tl = userDao.find("from SysUser t where t.loginName like :name order by loginName", params, 1, 10);
		List<SysUser> ul = new ArrayList<SysUser>();
		if (tl != null && tl.size() > 0) {
			for (SysUser t : tl) {
				SysUser u = new SysUser();
				u.setLoginName(t.getLoginName());
				ul.add(u);
			}
		}
		return ul;
	}

	@Override
	public PageResult loginCombogrid(String q, PageHelper ph) {
		if (q == null) {
			q = "";
		}
		PageResult pr = new PageResult();
		List<SysUser> ul = new ArrayList<SysUser>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", "%%" + q.trim() + "%%");
		List<SysUser> tl = userDao.find("from SysUser t where t.loginName like :name order by " + ph.getSort() + " " + ph.getOrder(), params, ph.getPage(), ph.getRows());
		if (tl != null && tl.size() > 0) {
			for (SysUser t : tl) {
				SysUser u = new SysUser();
				u.setLoginName(t.getLoginName());
				u.setCreateTime(t.getCreateTime());
				u.setModifyTime(t.getModifyTime());
				ul.add(u);
			}
		}
		pr.setRows(ul);
		pr.setTotal(userDao.count("select count(*) from SysUser t where t.loginName like :name", params));
		return pr;
	}

	@Override
	public List<Long> sysUserCreateDatetimeChart() {
		List<Long> l = new ArrayList<Long>();
		int k = 0;
		for (int i = 0; i < 12; i++) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("s", k);
			params.put("e", k + 2);
			k = k + 2;
			l.add(userDao.count("select count(*) from SysUser t where HOUR(t.createTime)>=:s and HOUR(t.createTime)<:e", params));
		}
		return l;
	}
	
	@Override
	public void addRole(Long roleId, Long userId) {
		SysUser user = userDao.get(SysUser.class, userId);
		SysRole role = roleDao.get(SysRole.class, roleId);
		
		role.getUsers().add(user);
		roleDao.save(role);
		user.getRoles().add(role);
		userDao.save(user);
	}

	@Override
	public void deleteRole(Long roleId, Long userId) {
		SysUser user = userDao.get(SysUser.class, userId);
		SysRole role = roleDao.get(SysRole.class, roleId);
		
		role.getUsers().remove(user);
		roleDao.save(role);
		user.getRoles().remove(role);
		userDao.save(user);
	}

}
