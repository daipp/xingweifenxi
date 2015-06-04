package com.ndtv.vodstat.sys.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
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
@Table(name = "SYS_RESOURCE")
@DynamicInsert(true)
@DynamicUpdate(true)
public class SysResource extends BaseEntity {
	
	private String resourceCode;
	private String resourceName;
	private String remark;
	private Integer seq;
	private String icon;
	private String url;		//资源地址
	private Boolean open;	//是否展开（针对菜单有效）
	
	private SysCode resourceType;	//资源类型
	private SysCode topMenu;		//顶层菜单
	private SysResource resource;
	private Set<SysResource> resources = new HashSet<SysResource>();
	private Set<SysRole> roles = new HashSet<SysRole>();
	
	
	@Column(name = "CODE", length = 100)
	public String getResourceCode() {
		return resourceCode;
	}
	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}
	
	@Column(name = "NAME", nullable = false, length = 100)
	public String getResourceName() {
		return this.resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
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

	@Column(name = "ICON", length = 100)
	public String getIcon() {
		return this.icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Column(name = "URL", length = 200)
	public String getUrl() {
		return this.url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@JSONField(serialize=false)
	@ManyToOne
	@JoinColumn(name = "RESOURCE_TYPE")
	public SysCode getResourceType() {
		return resourceType;
	}
	public void setResourceType(SysCode resourceType) {
		this.resourceType = resourceType;
	}
	
	@JSONField(serialize=false)
	@ManyToOne
	@JoinColumn(name = "TOPMENU")
	public SysCode getTopMenu() {
		return topMenu;
	}
	public void setTopMenu(SysCode topMenu) {
		this.topMenu = topMenu;
	}
	
	@JSONField(serialize=false)
	@ManyToOne
	//@JoinColumn(name = "PCODE", referencedColumnName="CODE")
	@JoinColumn(name = "PID")
	public SysResource getResource() {
		return this.resource;
	}
	public void setResource(SysResource resource) {
		this.resource = resource;
	}
	
	@JSONField(serialize=false)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "resource")
	public Set<SysResource> getResources() {
		return this.resources;
	}
	public void setResources(Set<SysResource> resources) {
		this.resources = resources;
	}

	@JSONField(serialize=false)
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "resources")
	public Set<SysRole> getRoles() {
		return roles;
	}
	public void setRoles(Set<SysRole> roles) {
		this.roles = roles;
	}
	public Boolean getOpen() {
		return open;
	}
	public void setOpen(Boolean open) {
		this.open = open;
	}
	

	private Long pid;
	private String pname;
	private String pcode;
	private Long typeId;
	private String typeName;
	private String iconCls;
	private String state;
	
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
	public void setPname(String pName) {
		this.pname = pName;
	}
	@Transient
	public String getPcode() {
		return pcode;
	}
	public void setPcode(String pCode) {
		this.pcode = pCode;
	}
	@Transient
	public Long getTypeId() {
		return typeId;
	}
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	@Transient
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	@Transient
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	@Transient
	public String getState() {
		if(open!=null&&!open){
			return "closed";
		} else {
			return "open";
		}
	}

	@Transient
	public Long getTopMenuId() {
		if(this.topMenu != null){
			return topMenu.getId();
		} else {
			return null;
		}
	}
	public void setTopMenuId(Long topMenuId) {
		this.topMenu = new SysCode();
		this.topMenu.setId(topMenuId);
	}
	@Transient
	public String getTopMenuDesc() {
		if(this.topMenu != null){
			return topMenu.getCodeContent();
		} else {
			return null;
		}
	}
	
}
