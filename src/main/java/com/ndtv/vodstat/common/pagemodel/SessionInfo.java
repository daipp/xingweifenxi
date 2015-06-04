package com.ndtv.vodstat.common.pagemodel;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * session信息模型
 * 
 */
public class SessionInfo implements java.io.Serializable {

	private Long id;			//用户ID
	private String ip;			//用户IP
	private String loginName;	//用户登录名
	private String realName;	//真实名
	
	private List<String> resourceList;// 用户可以访问的资源地址列表
	
	private Map<Long,Collection> bossSysCodes;	//跟BOSS相关的代码

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}

	public List<String> getResourceList() {
		return resourceList;
	}
	public void setResourceList(List<String> resourceList) {
		this.resourceList = resourceList;
	}
	
	public Map<Long, Collection> getBossSysCodes() {
		return bossSysCodes;
	}
	public Collection getBossSysCodes(Long typeId) {
		if(bossSysCodes!=null){
			return bossSysCodes.get(typeId);
		} else {
			return null;
		}
	}
	public void setBossSysCodes(Map<Long, Collection> bossSysCodes) {
		this.bossSysCodes = bossSysCodes;
	}
	
	@Override
	public String toString() {
		return this.loginName;
	}
}
