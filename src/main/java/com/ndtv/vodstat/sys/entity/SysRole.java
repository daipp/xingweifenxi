package com.ndtv.vodstat.sys.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.alibaba.fastjson.annotation.JSONField;
import com.ndtv.vodstat.core.entity.BaseEntity;

@Entity
@Table(name = "SYS_ROLE")
@DynamicInsert(true)
@DynamicUpdate(true)
public class SysRole extends BaseEntity {
	
	private String roleName;
	private String remark;
	private Integer seq;
	private SysRole role;
	private Set<SysRole> roles = new HashSet<SysRole>();
	private Set<SysResource> resources = new HashSet<SysResource>();
	private Set<SysUser> users = new HashSet<SysUser>();
	
	@Column(name = "ROLE_NAME")
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return this.remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "SEQ")
	public Integer getSeq() {
		return this.seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
	@JSONField(serialize=false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PID")
	public SysRole getRole() {
		return this.role;
	}
	public void setRole(SysRole role) {
		this.role = role;
	}

	@JSONField(serialize=false)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
	public Set<SysRole> getRoles() {
		return this.roles;
	}
	public void setRoles(Set<SysRole> roles) {
		this.roles = roles;
	}

	@JSONField(serialize=false)
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "SYS_ROLE_RESOURCE", 
	joinColumns = { @JoinColumn(name = "ROLE_ID") }, 
	inverseJoinColumns = { @JoinColumn(name = "RESOURCE_ID") })
	public Set<SysResource> getResources() {
		return resources;
	}
	public void setResources(Set<SysResource> resources) {
		this.resources = resources;
	}
	
	@JSONField(serialize=false)
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
//	@JoinTable(name = "SYS_USER_ROLE", joinColumns = {@JoinColumn(name = "ROLE_ID")}, inverseJoinColumns = {@JoinColumn(name = "USER_ID")} ) 
	public Set<SysUser> getUsers() {
		return users;
	}
	public void setUsers(Set<SysUser> users) {
		this.users = users;
	}
	
	private Long pid;
	private String pname;
	private String resourceIds;
	private String resourceNames;
	private String iconCls;

	@Transient
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	@Transient
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	@Transient
	public String getResourceIds() {
		return resourceIds;
	}
	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}
	@Transient
	public String getResourceNames() {
		return resourceNames;
	}
	public void setResourceNames(String resourceNames) {
		this.resourceNames = resourceNames;
	}
	@Transient
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	
	
}
