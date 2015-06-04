package com.ndtv.vodstat.sys.service;

import java.util.List;

import com.ndtv.vodstat.common.pagemodel.PageHelper;
import com.ndtv.vodstat.common.pagemodel.PageResult;
import com.ndtv.vodstat.common.pagemodel.SessionInfo;
import com.ndtv.vodstat.sys.entity.SysUser;

/**
 * 系统用户Service
 * 
 */
public interface ISysUserService {

	/**
	 * 用户登录
	 * @param user 里面包含登录名和密码
	 * @return 用户对象
	 */
	public SysUser login(SysUser user);

	/**
	 * 获取用户数据表格
	 * @param user
	 * @return
	 */
	public PageResult findPage(SysUser user, PageHelper ph);

	/**
	 * 获得用户对象
	 * @param id
	 * @return
	 */
	public SysUser get(Long id);

	/**
	 * 删除用户
	 * @param id
	 */
	public void delete(Long id);

	/**
	 * 用户授权
	 * @param ids
	 * @param user
	 *            需要user.roleIds的属性值
	 */
	public void grant(String ids, SysUser user);

	/**
	 * 获得用户能访问的资源地址
	 * @param id 用户ID
	 * @return
	 */
	public List<String> resourceList(Long id);

	/**
	 * 编辑用户密码
	 * @param user
	 */
	public void updatePwd(SysUser user);

	/**
	 * 修改用户自己的密码
	 * @param sessionInfo
	 * @param oldPwd
	 * @param pwd
	 * @return
	 */
	public boolean updateCurrentSysUserPwd(SessionInfo sessionInfo, String oldPwd, String pwd);

	/**
	 * 用户登录时的autocomplete
	 * @param q 参数
	 * @return
	 */
	public List<SysUser> loginCombobox(String q);

	/**
	 * 用户登录时的combogrid
	 * @param q
	 * @param ph
	 * @return
	 */
	public PageResult loginCombogrid(String q, PageHelper ph);

	/**
	 * 用户创建时间图表
	 * @return
	 */
	public List<Long> sysUserCreateDatetimeChart();

	
	public void add(SysUser user);
	
	public void update(SysUser user);

	public void addRole(Long roleId, Long userId);
	public void deleteRole(Long roleId, Long userId);
	
}
