package com.ndtv.vodstat.sys.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.ndtv.vodstat.core.entity.BaseEntity;


/**
 * 
 * @author infun
 *
 */

@Entity
@Table(name = "SYS_USER")
@DynamicInsert(true)
@DynamicUpdate(true)
public class SysUser extends BaseEntity {
	
	private String loginName;
	private String realName;
	private String pwd;
	private SysCode status;
	private Date createTime;
	private Date modifyTime;
	
	private Set<SysRole> roles = new HashSet<SysRole>();
	private String roleIds = "";
	private String roleNames = "";
	
	private Date createTimeBegin;
	private Date createTimeEnd;
	private Date modifyTimeBegin;
	private Date modifyTimeEnd;
	
	/**
	 * unique：唯一索引字段
	 * @return
	 */
	@Column(name = "LOGIN_NAME", unique = true)
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	@Column(name = "REAL_NAME")
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	@Column(name = "pwd")
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	@ManyToOne
	@JoinColumn(name = "STATUS")
	public SysCode getStatus() {
		return status;
	}
	public void setStatus(SysCode status) {
		this.status = status;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME", length = 19)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFY_TIME", length = 19)
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "SYS_USER_ROLE", 
	joinColumns = { @JoinColumn(name = "USER_ID") }, 
	inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
	public Set<SysRole> getRoles() {
		return roles;
	}
	public void setRoles(Set<SysRole> roles) {
		this.roles = roles;
	}

	@Transient
	public boolean containsRoleId(long roleId){
		if(roles!=null && roles.size() > 0){
			for(SysRole d : roles){
				if(d.getId() == roleId){
					return true;
				}
			}
		} else if(roleIds != null && roleIds.trim().length()>0){
			if(roleIds.indexOf(roleId + ",")>=0){
				return true;
			}
		}
		return false;
	}

	@Transient
	public String getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(Set<SysRole> roles) {
		String str = "";
		if(roles!=null){
			for(SysRole d : roles){
				str = str+d.getId()+",";
			}
		}
		this.roleIds = str;
	}
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	
	@Transient
	public String getRoleNames() {
		return roleNames;
	}
	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}
	
	@Transient	
	public Date getCreateTimeBegin() {
		return createTimeBegin;
	}
	public void setCreateTimeBegin(Date createTimeBegin) {
		this.createTimeBegin = createTimeBegin;
	}
	@Transient	
	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}
	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}
	@Transient	
	public Date getModifyTimeBegin() {
		return modifyTimeBegin;
	}
	public void setModifyTimeBegin(Date modifyTimeBegin) {
		this.modifyTimeBegin = modifyTimeBegin;
	}
	@Transient	
	public Date getModifyTimeEnd() {
		return modifyTimeEnd;
	}
	public void setModifyTimeEnd(Date modifyTimeEnd) {
		this.modifyTimeEnd = modifyTimeEnd;
	}
	
	
}
