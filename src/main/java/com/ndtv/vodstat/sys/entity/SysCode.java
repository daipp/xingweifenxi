package com.ndtv.vodstat.sys.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.alibaba.fastjson.annotation.JSONField;
import com.ndtv.vodstat.core.entity.BaseEntity;


/**
 * 系统代码实体
 */

@Entity
@Table(name = "SYS_CODE")
@DynamicInsert(true)
@DynamicUpdate(true)
//@Inheritance(strategy = InheritanceType.JOINED)
public class SysCode  extends BaseEntity {
			
	public static final int STATUS_VALID = 1;
	public static final int STATUS_INVALID = 0;
	
	private String codeName;		//代码名称
	private String codeContent;		//代码内容
	private String memo;			//备注
	private Integer status;			//状态
	
	private Date updateTime;		//最后时间
	private SysUser updateUser;		//最后修改人员
	
	private SysCode sysCodeType;	//所属类型	//当typeid=0时表示代码类型
	
	private List<SysCode> sceList;	//关联系统代码实体列表
	private List<SysCode> sceListEx;//被关联系统代码实体列表
	
	public SysCode(){}
	
	public SysCode(long codeid,String codeName,String codeContent){
		this.setId(codeid);
		this.codeName = codeName;
		this.codeContent = codeContent;
	}
	
	@Column(name = "CODE_NAME")
	public String getCodeName() {
		return codeName;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	
	@Column(name = "CODE_CONTENT")
	public String getCodeContent() {
		return codeContent;
	}
	public void setCodeContent(String codeContent) {
		this.codeContent = codeContent;
	}
	
	@Column(name = "MEMO")
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	@Column(name = "STATUS")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name = "UPDATE_TIME")
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	@Transient
	public String getUpdateTimeDesc() {
		if(updateTime != null){
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(updateTime);
		}
		return "";
	}

	@JSONField(serialize=false)
	@ManyToOne
	@JoinColumn(name = "UPDATE_USER_ID")
	public SysUser getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(SysUser updateUser) {
		this.updateUser = updateUser;
	}
	public void setUpdateUserId(Long userId) {
		this.updateUser = new SysUser();
		this.updateUser.setId(userId);
	}
	@Transient
	public Long getUpdateUserId() {
		if(this.updateUser==null){
			return null;
		}
		return this.updateUser.getId();
	}

	@JSONField(serialize=false)
	@ManyToOne
	@JoinColumn(name = "TYPE_ID", nullable = true)
	public SysCode getSysCodeType() {
		return sysCodeType;
	}
	public void setSysCodeType(SysCode cte) {
		this.sysCodeType = cte;
	}

	@JSONField(serialize=false)
	@ManyToMany(fetch = FetchType.LAZY, targetEntity = SysCode.class)
	@JoinTable(name = "SysCode_SysCode", joinColumns = @JoinColumn(name = "codeId"), inverseJoinColumns = @JoinColumn(name = "codeIdEx"))
	public List<SysCode> getSysCodeList() {
		return sceList;
	}

	public void setSysCodeList(List<SysCode> Value) {
		sceList = Value;
	}

	@JSONField(serialize=false)
	@ManyToMany(fetch = FetchType.LAZY, targetEntity = SysCode.class, mappedBy = "sysCodeList")
	public List<SysCode> getSysCodeListEx() {
		return sceListEx;
	}

	public void setSysCodeListEx(List<SysCode> Value) {
		sceListEx = Value;
	}

//	@Transient----------------------------------------------

	@Transient
	public String getStatusDesc(){
		if(status != null && status == STATUS_VALID){
			return "有效";
		} else if(status != null && status == STATUS_INVALID){
			return "无效";
		} else {
			return "未知";
		}
	}
	
	@Transient
	public String getUpdateLoginName(){
		if(updateUser != null){
			return updateUser.getLoginName();
		} else {
			return "";
		}
	}

	@Transient
	public Long getTypeId(){
		if(sysCodeType != null){
			return sysCodeType.getId();
		} else {
			return null;
		}
	}
	public void setTypeId(Long tId) {
		this.sysCodeType = new SysCode();
		this.sysCodeType.setId(tId);
	}

	@Transient
	public String getTypeName(){
		if(sysCodeType != null){
			return sysCodeType.getCodeContent();
		} else {
			return null;
		}
	}
}
