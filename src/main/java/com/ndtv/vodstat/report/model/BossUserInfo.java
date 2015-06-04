package com.ndtv.vodstat.report.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BossUserInfo implements Serializable {

	public static final String TABLE_NAME = "boss_user";
	
	long userId;
	long customerId;
	String customerName;
	String customerType;
	
	String stb;
	String stbType;
	String obtainWay;
	String simserialNo;
	String stbserialNo;
	String importanceLevel;
	String operator;
	
	long userTypeId;
	String userType;
	long userGroupId;
	String userGroup;
	long userStateId;
	String userState;
	Date userStateChangeTime;
	
	String fulladdress;
	Date crTime;
	long shopId;
	String shop;
	long townId;
	String town;
	long communityId;
	String community;
	long villageId;
	String village;
	
	String phone;
	String mobile;
	
	//最大截止日期
	Date maxEndTime;
	//最大订购日期
	Date maxBookTime;
	
	//宽带未到期
	long bbUnExpiredUsers;
	//宽带已到期
	long bbExpiredUsers;
	//宽带将到期
	long bbExpiringUsers;

	//点播未到期
	long vodUnExpiredUsers;
	//点播已到期
	long vodExpiredUsers;
	//点播将到期
	long vodExpiringUsers;
	
	//登录次数
	long onlineTimes;
	//点播次数
	long activeTimes;
	
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
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
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getStb() {
		return stb;
	}
	public void setStb(String stb) {
		this.stb = stb;
	}
	public String getStbType() {
		return stbType;
	}
	public void setStbType(String stbType) {
		this.stbType = stbType;
	}
	public long getUserTypeId() {
		return userTypeId;
	}
	public void setUserTypeId(long userTypeId) {
		this.userTypeId = userTypeId;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public long getUserGroupId() {
		return userGroupId;
	}
	public void setUserGroupId(long userGroupId) {
		this.userGroupId = userGroupId;
	}
	public String getUserGroup() {
		return userGroup;
	}
	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}
	public long getUserStateId() {
		return userStateId;
	}
	public void setUserStateId(long userStateId) {
		this.userStateId = userStateId;
	}
	public String getUserState() {
		return userState;
	}
	public void setUserState(String userState) {
		this.userState = userState;
	}
	public Date getUserStateChangeTime() {
		return userStateChangeTime;
	}
	public void setUserStateChangeTime(Date userStateChangeTime) {
		this.userStateChangeTime = userStateChangeTime;
	}
	public String getFulladdress() {
		return fulladdress;
	}
	public void setFulladdress(String fulladdress) {
		this.fulladdress = fulladdress;
	}
	public String getCrTimeDesc() {
		if(crTime == null)
			return "";
		return new SimpleDateFormat("yyyy-MM-dd").format(crTime);
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Date getMaxEndTime() {
		return maxEndTime;
	}
	public void setMaxEndTime(Date maxEndTime) {
		this.maxEndTime = maxEndTime;
	}
	public Date getMaxBookTime() {
		return maxBookTime;
	}
	public void setMaxBookTime(Date maxBookTime) {
		this.maxBookTime = maxBookTime;
	}
	public long getOnlineTimes() {
		return onlineTimes;
	}
	public void setOnlineTimes(long onlineTimes) {
		this.onlineTimes = onlineTimes;
	}
	public long getActiveTimes() {
		return activeTimes;
	}
	public void setActiveTimes(long activeTimes) {
		this.activeTimes = activeTimes;
	}
	public long getBbUnExpiredUsers() {
		return bbUnExpiredUsers;
	}
	public void setBbUnExpiredUsers(long bbUnExpiredUsers) {
		this.bbUnExpiredUsers = bbUnExpiredUsers;
	}
	public long getBbExpiredUsers() {
		return bbExpiredUsers;
	}
	public void setBbExpiredUsers(long bbExpiredUsers) {
		this.bbExpiredUsers = bbExpiredUsers;
	}
	public long getBbExpiringUsers() {
		return bbExpiringUsers;
	}
	public void setBbExpiringUsers(long bbExpiringUsers) {
		this.bbExpiringUsers = bbExpiringUsers;
	}
	public long getVodUnExpiredUsers() {
		return vodUnExpiredUsers;
	}
	public void setVodUnExpiredUsers(long vodUnExpiredUsers) {
		this.vodUnExpiredUsers = vodUnExpiredUsers;
	}
	public long getVodExpiredUsers() {
		return vodExpiredUsers;
	}
	public void setVodExpiredUsers(long vodExpiredUsers) {
		this.vodExpiredUsers = vodExpiredUsers;
	}
	public long getVodExpiringUsers() {
		return vodExpiringUsers;
	}
	public void setVodExpiringUsers(long vodExpiringUsers) {
		this.vodExpiringUsers = vodExpiringUsers;
	}
	public String getObtainWay() {
		return obtainWay;
	}
	public void setObtainWay(String obtainWay) {
		this.obtainWay = obtainWay;
	}
	public String getSimserialNo() {
		return simserialNo;
	}
	public void setSimserialNo(String simserialNo) {
		this.simserialNo = simserialNo;
	}
	public String getStbserialNo() {
		return stbserialNo;
	}
	public void setStbserialNo(String stbserialNo) {
		this.stbserialNo = stbserialNo;
	}
	public String getImportanceLevel() {
		return importanceLevel;
	}
	public void setImportanceLevel(String importanceLevel) {
		this.importanceLevel = importanceLevel;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
}
