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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.alibaba.fastjson.annotation.JSONField;
import com.ndtv.vodstat.core.entity.BaseEntity;

@Entity
@Table(name = "SYS_NOTICE")
@DynamicInsert(true)
@DynamicUpdate(true)
public class SysNotice extends BaseEntity {
	
	private String title;
	private String content;
	private Date time;
	private String loginName;

	
	@Column(name = "LOGIN_NAME")
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	@Column(name = "TITLE")
	public String getTitle() {
		return this.title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "CONTENT")
	public String getContent() {
		return this.content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column(name = "TIME")
	public Date getTime() {
		return this.time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
	
	
	
	
	
}
