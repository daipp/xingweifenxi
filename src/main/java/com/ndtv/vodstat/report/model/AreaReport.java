package com.ndtv.vodstat.report.model;

import java.io.Serializable;
import java.util.Date;

public class AreaReport implements Serializable,Cloneable {

	private AreaCondition queryCondtion;
	public AreaCondition getCondition() {
		return queryCondtion;
	}
	public void setCondition(AreaCondition queryCondtion) {
		this.queryCondtion = queryCondtion;
	}
	
	//查询结果:======================================
	long id;	//无具体意义(报表记录的主键)
	Date repDate;
	String customerType;
	Long customerTypeId;
	String town;
	Long townId;
	String community;
	Long communityId;
	String village;
	Long villageId;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getRepDate() {
		return repDate;
	}
	public void setRepDate(Date repDate) {
		this.repDate = repDate;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public Long getCustomerTypeId() {
		return customerTypeId;
	}
	public void setCustomerTypeId(Long customerTypeId) {
		this.customerTypeId = customerTypeId;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public Long getTownId() {
		return townId;
	}
	public void setTownId(Long townId) {
		this.townId = townId;
	}
	public String getCommunity() {
		return community;
	}
	public void setCommunity(String community) {
		this.community = community;
	}
	public Long getCommunityId() {
		return communityId;
	}
	public void setCommunityId(Long communityId) {
		this.communityId = communityId;
	}
	public String getVillage() {
		return village;
	}
	public void setVillage(String village) {
		this.village = village;
	}
	public Long getVillageId() {
		return villageId;
	}
	public void setVillageId(Long villageId) {
		this.villageId = villageId;
	}
	
	public Object clone() {
		AreaReport o = null;
		try {
			o = (AreaReport)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}
	
	public void setKey(AreaReport va) {
		this.repDate = va.getRepDate();
		this.customerTypeId = va.getCustomerTypeId();
		this.townId = va.getTownId();
		this.communityId = va.getCommunityId();
		this.villageId = va.getVillageId();
	}
	
	public boolean isSameKey(AreaReport va) {
		if((this.customerTypeId == null) != (va.getCustomerTypeId() == null)){
			//System.out.println(this.customerTypeId + " vs " + va.getCustomerTypeId());
			return false;
		}
		if((this.townId == null) != (va.getTownId() == null)){
			//System.out.println(this.townId + " vs " + va.getTownId());
			return false;
		}
		if((this.communityId == null) != (va.getCommunityId() == null)){
			//System.out.println(this.communityId + " vs " + va.getCommunityId());
			return false;
		}
		if((this.villageId == null) != (va.getVillageId() == null)){
			//System.out.println(this.villageId + " vs " + va.getVillageId());
			return false;
		}
		
		if(this.customerTypeId!=null && !this.customerTypeId.equals(va.getCustomerTypeId())){
			//System.out.println(this.customerTypeId + " vs " + va.getCustomerTypeId());
			return false;
		}
		if(this.townId!=null && !this.townId.equals(va.getTownId())){
			//System.out.println(this.townId + " vs " + va.getTownId());
			return false;
		}
		if(this.communityId!=null && !this.communityId.equals(va.getCommunityId())){
			//System.out.println(this.communityId + " vs " + va.getCommunityId());
			return false;
		}
		if(this.villageId!=null && !this.villageId.equals(va.getVillageId())){
			//System.out.println(this.villageId + " vs " + va.getVillageId());
			return false;
		}
		return true;
	}
	
	
}
