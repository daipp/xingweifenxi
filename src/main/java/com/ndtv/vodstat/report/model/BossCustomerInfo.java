package com.ndtv.vodstat.report.model;

import java.io.Serializable;
import java.util.Date;

public class BossCustomerInfo implements Serializable {
	
	public static final String TABLE_NAME = "boss_customer";
	
	long customerId;
	String customerName;
	
	long customerTypeId;
	String customerType;
	
	long importanceLevelId;
	String importanceLevel;
	
	long securityLevelId;
	String securityLevel;
	
	long serviceCardId;
	
	long certificatetypeId;
	String certificatetype;
	String certificatecode;
	String memo;

	Date crTime;
	long shopId;
	String shop;
	
	long townId;
	String town;
	long communityId;
	String community;
	long villageId;
	String village;
	String fulladdress;
	
	
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public long getCustomerTypeId() {
		return customerTypeId;
	}
	public void setCustomerTypeId(long customerTypeId) {
		this.customerTypeId = customerTypeId;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public long getImportanceLevelId() {
		return importanceLevelId;
	}
	public void setImportanceLevelId(long importanceLevelId) {
		this.importanceLevelId = importanceLevelId;
	}
	public String getImportanceLevel() {
		return importanceLevel;
	}
	public void setImportanceLevel(String importanceLevel) {
		this.importanceLevel = importanceLevel;
	}
	public long getSecurityLevelId() {
		return securityLevelId;
	}
	public void setSecurityLevelId(long securityLevelId) {
		this.securityLevelId = securityLevelId;
	}
	public String getSecurityLevel() {
		return securityLevel;
	}
	public void setSecurityLevel(String securityLevel) {
		this.securityLevel = securityLevel;
	}
	public long getServiceCardId() {
		return serviceCardId;
	}
	public void setServiceCardId(long serviceCardId) {
		this.serviceCardId = serviceCardId;
	}
	public long getCertificatetypeId() {
		return certificatetypeId;
	}
	public void setCertificatetypeId(long certificatetypeId) {
		this.certificatetypeId = certificatetypeId;
	}
	public String getCertificatetype() {
		return certificatetype;
	}
	public void setCertificatetype(String certificatetype) {
		this.certificatetype = certificatetype;
	}
	public String getCertificatecode() {
		return certificatecode;
	}
	public void setCertificatecode(String certificatecode) {
		this.certificatecode = certificatecode;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Date getCrTime() {
		return crTime;
	}
	public void setCrTime(Date crTime) {
		this.crTime = crTime;
	}
	public long getShopId() {
		return shopId;
	}
	public void setShopId(long shopId) {
		this.shopId = shopId;
	}
	public String getShop() {
		return shop;
	}
	public void setShop(String shop) {
		this.shop = shop;
	}
	public long getTownId() {
		return townId;
	}
	public void setTownId(long townId) {
		this.townId = townId;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public long getCommunityId() {
		return communityId;
	}
	public void setCommunityId(long communityId) {
		this.communityId = communityId;
	}
	public String getCommunity() {
		return community;
	}
	public void setCommunity(String community) {
		this.community = community;
	}
	public long getVillageId() {
		return villageId;
	}
	public void setVillageId(long villageId) {
		this.villageId = villageId;
	}
	public String getVillage() {
		return village;
	}
	public void setVillage(String village) {
		this.village = village;
	}
	public String getFulladdress() {
		return fulladdress;
	}
	public void setFulladdress(String fulladdress) {
		this.fulladdress = fulladdress;
	}
	
	
}
