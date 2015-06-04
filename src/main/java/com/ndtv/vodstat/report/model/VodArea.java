package com.ndtv.vodstat.report.model;

import java.text.SimpleDateFormat;

public class VodArea extends AreaReport implements Comparable<VodArea> {

	//查询条件:======================================
	VodAreaCondition condition;
	public VodAreaCondition getCondition() {
		return condition;
	}
	public void setCondition(VodAreaCondition condition) {
		this.condition = condition;
	}
	
	//查询结果:======================================

	private long customers;		//客户数
	private long dvbUsers;		//基本型用户数
	private long vodUsers;		//交互型用户数
	private long bbUsers;		//宽带用户数
	private long analogUsers;	//模拟用户数
	
	long dvbUsers0;		//正常基本型用户数
	long vodUsers0;		//正常交互型用户数
	long bbUsers0;		//正常宽带用户数
	long analogUsers0;	//正常模拟用户数
	
	long hdstbs;		//高清机顶盒数
	
	//以下都是针对交互型用户而言的查询结果
	long bookUsers;		//订购用户(在线用户)	
	long activeUsers;	//活跃用户(有华数点播记录)	
	long onlineBookedUsers;	//登录过且订购的用户
	long onlineUnbookUsers;	//登录过且未订购的用户
	long offlineBookedUsers;	//登录过且订购的用户
	long offlineUnbookUsers;	//登录过且未订购的用户
	long inactiveOnlineUsers;	//非活跃且登录的用户
	long inactiveOfflineUsers;	//非活跃且未登录的用户

	//以下都是针对客户-用户情况汇总报表
	String reportRange;	//报表周期:month,season,year
	long newDvbUsers;	//净增基本型用户数
	long newVodUsers;	//净增交互型用户数
	long newBbUsers;	//净增宽带用户数
	long onlineVodUsers;	//在线的交互型用户数
	long onlineBbUsers;		//在线的宽带用户数
	long newOnlineVodUsers;	//净增的在线交互型用户数
	long newOnlineBbUsers;	//净增的在线宽带用户数
	long offlineVodUsers;	//离线的交互型用户数
	long offlineBbUsers;	//离线的宽带用户数
	
	
	//以下都是针对客户-用户情况实时报表
	long vodOpenNowUsers;		//点播立即开通
	long vodOpenDelayUsers;		//点播延迟开通
	long vodNotOpenUsers;		//未开通点播
	long bbOpenNowUsers;		//宽带立即开通
	long bbOpenDelayUsers;		//宽带延迟开通
	long bbNotOpenUsers;		//宽带未开通

	//以下都是针对续费率的查询结果
	//long bookUsers;	//订购用户数
	long expiredUsers;	//到期用户数
	long vodOfflineBookUsers;	//互动离线订购数
	long vodExpiredBookUsers;	//互动到期续订数
	long vodUnExpiredBookUsers;	//互动未到期续订数
	long bbOfflineBookUsers;	//宽带离线订购数
	long bbExpiredBookUsers;	//宽带到期续订数
	long bbUnExpiredBookUsers;	//宽带未到期续订数
	
	/*//一下针对TopN热度查询
	long clickTimes;//登录次数
	long viewTime;//观看时长
    long userId;
    String fullAddress;
    String customername;
    String filmname;
    String catergory;*/

    public String getRepDateMonth() {
		if(repDate!=null){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
			return df.format(repDate);
		} else {
			return "";
		}
	}
	public String getRepDateDesc() {
		if(repDate!=null){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			return df.format(repDate);
		} else {
			return "";
		}
	}

	public long getCustomers() {
		return customers;
	}
	public void setCustomers(long customers) {
		this.customers = customers;
	}
	public long getDvbUsers() {
		return dvbUsers;
	}
	public void setDvbUsers(long dvbUsers) {
		this.dvbUsers = dvbUsers;
	}
	public long getVodUsers() {
		return vodUsers;
	}
	public void setVodUsers(long vodUsers) {
		this.vodUsers = vodUsers;
	}
	public long getBbUsers() {
		return bbUsers;
	}
	public void setBbUsers(long bbUsers) {
		this.bbUsers = bbUsers;
	}
	public long getHdstbs() {
		return hdstbs;
	}
	public void setHdstbs(long hdstbs) {
		this.hdstbs = hdstbs;
	}
	public long getBookUsers() {
		return bookUsers;
	}
	public void setBookUsers(long bookUsers) {
		this.bookUsers = bookUsers;
	}
	public long getActiveUsers() {
		return activeUsers;
	}
	public void setActiveUsers(long activeUsers) {
		this.activeUsers = activeUsers;
	}
	public long getOnlineBookedUsers() {
		return onlineBookedUsers;
	}
	public void setOnlineBookedUsers(long onlineBookedUsers) {
		this.onlineBookedUsers = onlineBookedUsers;
	}
	public long getOnlineUnbookUsers() {
		return onlineUnbookUsers;
	}
	public void setOnlineUnbookUsers(long onlineUnbookUsers) {
		this.onlineUnbookUsers = onlineUnbookUsers;
	}
	public long getOfflineBookedUsers() {
		return offlineBookedUsers;
	}
	public void setOfflineBookedUsers(long offlineBookedUsers) {
		this.offlineBookedUsers = offlineBookedUsers;
	}
	public long getOfflineUnbookUsers() {
		return offlineUnbookUsers;
	}
	public void setOfflineUnbookUsers(long offlineUnbookUsers) {
		this.offlineUnbookUsers = offlineUnbookUsers;
	}
	public long getInactiveOnlineUsers() {
		return inactiveOnlineUsers;
	}
	public void setInactiveOnlineUsers(long inactiveOnlineUsers) {
		this.inactiveOnlineUsers = inactiveOnlineUsers;
	}
	public long getInactiveOfflineUsers() {
		return inactiveOfflineUsers;
	}
	public void setInactiveOfflineUsers(long inactiveOfflineUsers) {
		this.inactiveOfflineUsers = inactiveOfflineUsers;
	}

	public String getReportRange() {
		return reportRange;
	}
	public void setReportRange(String reportRange) {
		this.reportRange = reportRange;
	}
	public long getNewDvbUsers() {
		return newDvbUsers;
	}
	public void setNewDvbUsers(long newDvbUsers) {
		this.newDvbUsers = newDvbUsers;
	}
	public long getNewVodUsers() {
		return newVodUsers;
	}
	public void setNewVodUsers(long newVodUsers) {
		this.newVodUsers = newVodUsers;
	}
	public long getNewBbUsers() {
		return newBbUsers;
	}
	public void setNewBbUsers(long newBbUsers) {
		this.newBbUsers = newBbUsers;
	}
	public long getOnlineVodUsers() {
		return onlineVodUsers;
	}
	public void setOnlineVodUsers(long onlineVodUsers) {
		this.onlineVodUsers = onlineVodUsers;
	}
	public long getOnlineBbUsers() {
		return onlineBbUsers;
	}
	public void setOnlineBbUsers(long onlineBbUsers) {
		this.onlineBbUsers = onlineBbUsers;
	}
	public long getNewOnlineVodUsers() {
		return newOnlineVodUsers;
	}
	public void setNewOnlineVodUsers(long newOnlineVodUsers) {
		this.newOnlineVodUsers = newOnlineVodUsers;
	}
	public long getNewOnlineBbUsers() {
		return newOnlineBbUsers;
	}
	public void setNewOnlineBbUsers(long newOnlineBbUsers) {
		this.newOnlineBbUsers = newOnlineBbUsers;
	}
	public long getOfflineVodUsers() {
		return offlineVodUsers;
	}
	public void setOfflineVodUsers(long offlineVodUsers) {
		this.offlineVodUsers = offlineVodUsers;
	}
	public long getOfflineBbUsers() {
		return offlineBbUsers;
	}
	public void setOfflineBbUsers(long offlineBbUsers) {
		this.offlineBbUsers = offlineBbUsers;
	}

	public long getExpiredUsers() {
		return expiredUsers;
	}
	public void setExpiredUsers(long expiredUsers) {
		this.expiredUsers = expiredUsers;
	}
	public long getVodOfflineBookUsers() {
		return vodOfflineBookUsers;
	}
	public void setVodOfflineBookUsers(long vodOfflineBookUsers) {
		this.vodOfflineBookUsers = vodOfflineBookUsers;
	}
	public long getVodExpiredBookUsers() {
		return vodExpiredBookUsers;
	}
	public void setVodExpiredBookUsers(long vodExpiredBookUsers) {
		this.vodExpiredBookUsers = vodExpiredBookUsers;
	}
	public long getVodUnExpiredBookUsers() {
		return vodUnExpiredBookUsers;
	}
	public void setVodUnExpiredBookUsers(long vodUnExpiredBookUsers) {
		this.vodUnExpiredBookUsers = vodUnExpiredBookUsers;
	}
	public long getBbOfflineBookUsers() {
		return bbOfflineBookUsers;
	}
	public void setBbOfflineBookUsers(long bbOfflineBookUsers) {
		this.bbOfflineBookUsers = bbOfflineBookUsers;
	}
	public long getBbExpiredBookUsers() {
		return bbExpiredBookUsers;
	}
	public void setBbExpiredBookUsers(long bbExpiredBookUsers) {
		this.bbExpiredBookUsers = bbExpiredBookUsers;
	}
	public long getBbUnExpiredBookUsers() {
		return bbUnExpiredBookUsers;
	}
	public void setBbUnExpiredBookUsers(long bbUnExpiredBookUsers) {
		this.bbUnExpiredBookUsers = bbUnExpiredBookUsers;
	}
	
	public long getVodOpenNowUsers() {
		return vodOpenNowUsers;
	}
	public void setVodOpenNowUsers(long vodOpenNowUsers) {
		this.vodOpenNowUsers = vodOpenNowUsers;
	}
	public long getVodOpenDelayUsers() {
		return vodOpenDelayUsers;
	}
	public void setVodOpenDelayUsers(long vodOpenDelayUsers) {
		this.vodOpenDelayUsers = vodOpenDelayUsers;
	}
	public long getVodNotOpenUsers() {
		return vodNotOpenUsers;
	}
	public void setVodNotOpenUsers(long vodNotOpenUsers) {
		this.vodNotOpenUsers = vodNotOpenUsers;
	}
	public long getBbOpenNowUsers() {
		return bbOpenNowUsers;
	}
	public void setBbOpenNowUsers(long bbOpenNowUsers) {
		this.bbOpenNowUsers = bbOpenNowUsers;
	}
	public long getBbOpenDelayUsers() {
		return bbOpenDelayUsers;
	}
	public void setBbOpenDelayUsers(long bbOpenDelayUsers) {
		this.bbOpenDelayUsers = bbOpenDelayUsers;
	}
	public long getBbNotOpenUsers() {
		return bbNotOpenUsers;
	}
	public void setBbNotOpenUsers(long bbNotOpenUsers) {
		this.bbNotOpenUsers = bbNotOpenUsers;
	}
	//以下是为处理查询结果:======================================
	public void add(VodArea va){
		this.customers = this.customers + va.getCustomers();
		this.dvbUsers = this.dvbUsers + va.getDvbUsers();
		this.bbUsers = this.bbUsers + va.getBbUsers();
		this.vodUsers = this.vodUsers + va.getVodUsers();
		this.hdstbs = this.hdstbs + va.getHdstbs();
		
		this.bookUsers = this.bookUsers + va.getBookUsers();
		this.activeUsers = this.activeUsers + va.getActiveUsers();
		this.onlineBookedUsers = this.onlineBookedUsers + va.getOnlineBookedUsers();
		this.onlineUnbookUsers = this.onlineUnbookUsers + va.getOnlineUnbookUsers();
		this.offlineBookedUsers = this.offlineBookedUsers + va.getOfflineBookedUsers();
		this.offlineUnbookUsers = this.offlineUnbookUsers + va.getOfflineUnbookUsers();
		this.inactiveOnlineUsers = this.inactiveOnlineUsers + va.getInactiveOnlineUsers();
		this.inactiveOfflineUsers = this.inactiveOfflineUsers + va.getInactiveOfflineUsers();
		
		this.newDvbUsers = this.newDvbUsers + va.getNewDvbUsers();
		this.newVodUsers = this.newVodUsers + va.getNewVodUsers();
		this.newBbUsers = this.newBbUsers + va.getNewBbUsers();
		this.onlineVodUsers = this.onlineVodUsers + va.getOnlineVodUsers();
		this.onlineBbUsers = this.onlineBbUsers + va.getOnlineBbUsers();
		this.newOnlineVodUsers = this.newOnlineVodUsers + va.getNewOnlineVodUsers();
		this.newOnlineBbUsers = this.newOnlineBbUsers + va.getNewOnlineBbUsers();
		this.offlineVodUsers = this.offlineVodUsers + va.getOfflineVodUsers();
		this.offlineBbUsers = this.offlineBbUsers + va.getOfflineBbUsers();
		this.expiredUsers = this.expiredUsers+va.getExpiredUsers();
		this.vodOpenNowUsers = this.vodOpenNowUsers + va.getVodOpenNowUsers();
		this.vodOpenDelayUsers = this.vodOpenDelayUsers + va.getVodOpenDelayUsers();
		this.vodNotOpenUsers = this.vodNotOpenUsers + va.getVodNotOpenUsers();
		this.bbOpenNowUsers = this.bbOpenNowUsers + va.getBbOpenNowUsers();
		this.bbOpenDelayUsers = this.bbOpenDelayUsers + va.getBbOpenDelayUsers();
		this.bbNotOpenUsers = this.bbNotOpenUsers + va.getBbNotOpenUsers();
		
		this.vodOfflineBookUsers = this.vodOfflineBookUsers+va.getVodOfflineBookUsers();
		this.vodExpiredBookUsers = this.vodExpiredBookUsers+va.getVodExpiredBookUsers();
		this.vodUnExpiredBookUsers = this.vodUnExpiredBookUsers+va.getVodUnExpiredBookUsers();
		this.bbOfflineBookUsers = this.bbOfflineBookUsers+va.getBbOfflineBookUsers();
		this.bbExpiredBookUsers = this.bbExpiredBookUsers+va.getBbExpiredBookUsers();
		this.bbUnExpiredBookUsers = this.bbUnExpiredBookUsers+va.getBbUnExpiredBookUsers();
	}

	public void setKey(VodArea va) {
		this.repDate = va.getRepDate();
		this.customerTypeId = va.getCustomerTypeId();
		this.townId = va.getTownId();
		this.communityId = va.getCommunityId();
		this.villageId = va.getVillageId();
	}
	
	public boolean isSameKey(VodArea va) {
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
	
	@Override
	public int compareTo(VodArea o) {
		if(o != null){
			return this.getRepDate().compareTo((o).getRepDate());
		}
		return 0;
	}
	public Object clone() {
		VodArea o = (VodArea)super.clone();
		return o;
	}
	
	/*public long getClickTimes() {
		return clickTimes;
	}
	public void setClickTimes(long clickTimes) {
		this.clickTimes = clickTimes;
	}
	public long getViewTime() {
		return viewTime;
	}
	public void setViewTime(long viewTime) {
		this.viewTime = viewTime;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getFullAddress() {
		return fullAddress;
	}
	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}
	public String getCustomername() {
		return customername;
	}
	public void setCustomername(String customername) {
		this.customername = customername;
	}
	public String getFilmname() {
		return filmname;
	}
	public void setFilmname(String filmname) {
		this.filmname = filmname;
	}
	public String getCatergory() {
		return catergory;
	}
	public void setCatergory(String catergory) {
		this.catergory = catergory;
	}*/
}
