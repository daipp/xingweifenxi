package com.ndtv.vodstat.report.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;

public class AreaMonthReport extends AreaReport implements Serializable,Comparable<AreaMonthReport>,Cloneable {
	
	public static final String REPORTRANGE_MONTH = "month";
	public static final String reportRange_SEASON = "season";
	public static final String reportRange_YEAR = "year";

	//以下是活跃度方面的内容:
	long activeUsers;			//活跃用户(有华数点播记录)
	long onlineBooked;		//登录用户(在线), 登录过且订购的用户
	long onlineUnbook;		//登录用户(离线), 登录过且未订购的用户
	long offlineBooked;	//未登用户(在线), 没登录过且订购的用户
	long offlineUnbook;	//未登用户(离线), 没登录过且未订购的用户
	long inactiveOnline;	//不活跃用户(登录), 非活跃且登录的用户
	long inactiveOffline;	//不活跃用户(未登), 非活跃且未登录的用户
	
	//以下是点播续费率方面的内容:
	long vodExpiring;		//当月到期的
	long vodExpiringBook;	//当月到期续费的
	long vodExpiredBook;	//以前到期续费的
	long vodPreBook;		//未到期提前续订的
	long vodNewBook;		//未订购新订购的
	float vodBookRate;		//续费率: 当月到期续费的 / 当月到期的 
	
	//以下是宽带续费率方面的内容:
	long bbExpiring;		//当月到期的
	long bbExpiringBook;	//当月到期续费的
	long bbExpiredBook;		//以前到期续费的
	long bbPreBook;			//未到期提前续订的
	long bbNewBook;			//未订购新订购的
	float bbBookRate;		//续费率: 当月到期续费的 / 当月到期的
	
	//以下都是针对客户-用户情况汇总报表
	String reportRange;	//报表周期:month,season,year
	long customers;	//客户数
	long dvbUsers;	//基本型用户数
	long vodUsers;	//交互型用户数
	long bbUsers;	//宽带用户数
	long vodBooked;	//在线的交互型用户数
	long bbBooked;	//在线的宽带用户数
	long vodUnbook;	//离线的交互型用户数
	long bbUnbook;	//离线的宽带用户数
	long newDvb;	//净增基本型用户数
	long newVod;	//净增交互型用户数
	long newBb;	//净增宽带用户数
	long newVodBooked;	//净增交互在线用户数
	long newBbBooked;	//净增宽带在线用户数
	
	public String getRepMonthDesc() {
		if(repDate!=null){
			SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
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

	public long getActiveUsers() {
		return activeUsers;
	}
	public void setActiveUsers(long activeUsers) {
		this.activeUsers = activeUsers;
	}
	public long getOnlineBooked() {
		return onlineBooked;
	}
	public void setOnlineBooked(long onlineBooked) {
		this.onlineBooked = onlineBooked;
	}
	public long getOnlineUnbook() {
		return onlineUnbook;
	}
	public void setOnlineUnbook(long onlineUnbook) {
		this.onlineUnbook = onlineUnbook;
	}
	public long getOfflineBooked() {
		return offlineBooked;
	}
	public void setOfflineBooked(long offlineBooked) {
		this.offlineBooked = offlineBooked;
	}
	public long getOfflineUnbook() {
		return offlineUnbook;
	}
	public void setOfflineUnbook(long offlineUnbook) {
		this.offlineUnbook = offlineUnbook;
	}
	public long getInactiveOnline() {
		return inactiveOnline;
	}
	public void setInactiveOnline(long inactiveOnline) {
		this.inactiveOnline = inactiveOnline;
	}
	public long getInactiveOffline() {
		return inactiveOffline;
	}
	public void setInactiveOffline(long inactiveOffline) {
		this.inactiveOffline = inactiveOffline;
	}
	public long getVodExpiring() {
		return vodExpiring;
	}
	public void setVodExpiring(long vodExpiring) {
		this.vodExpiring = vodExpiring;
	}
	public long getVodExpiringBook() {
		return vodExpiringBook;
	}
	public void setVodExpiringBook(long vodExpiringBook) {
		this.vodExpiringBook = vodExpiringBook;
	}
	public long getVodExpiredBook() {
		return vodExpiredBook;
	}
	public void setVodExpiredBook(long vodExpiredBook) {
		this.vodExpiredBook = vodExpiredBook;
	}
	public long getVodPreBook() {
		return vodPreBook;
	}
	public void setVodPreBook(long vodPreBook) {
		this.vodPreBook = vodPreBook;
	}
	public float getVodBookRate() {
		return vodBookRate;
	}
	public void setVodBookRate(float vodBookRate) {
		this.vodBookRate = vodBookRate;
	}
	public long getVodNewBook() {
		return vodNewBook;
	}
	public void setVodNewBook(long vodNewBook) {
		this.vodNewBook = vodNewBook;
	}
	public long getBbExpiring() {
		return bbExpiring;
	}
	public void setBbExpiring(long bbExpiring) {
		this.bbExpiring = bbExpiring;
	}
	public long getBbExpiringBook() {
		return bbExpiringBook;
	}
	public void setBbExpiringBook(long bbExpiringBook) {
		this.bbExpiringBook = bbExpiringBook;
	}
	public long getBbExpiredBook() {
		return bbExpiredBook;
	}
	public void setBbExpiredBook(long bbExpiredBook) {
		this.bbExpiredBook = bbExpiredBook;
	}
	public long getBbPreBook() {
		return bbPreBook;
	}
	public void setBbPreBook(long bbPreBook) {
		this.bbPreBook = bbPreBook;
	}
	public float getBbBookRate() {
		return bbBookRate;
	}
	public void setBbBookRate(float bbBookRate) {
		this.bbBookRate = bbBookRate;
	}
	public long getBbNewBook() {
		return bbNewBook;
	}
	public void setBbNewBook(long bbNewBook) {
		this.bbNewBook = bbNewBook;
	}
	public String getReportRange() {
		return reportRange;
	}
	public void setReportRange(String reportRange) {
		this.reportRange = reportRange;
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
	public long getVodBooked() {
		return vodBooked;
	}
	public void setVodBooked(long vodBooked) {
		this.vodBooked = vodBooked;
	}
	public long getBbBooked() {
		return bbBooked;
	}
	public void setBbBooked(long bbBooked) {
		this.bbBooked = bbBooked;
	}
	public long getVodUnbook() {
		return vodUnbook;
	}
	public void setVodUnbook(long vodUnbook) {
		this.vodUnbook = vodUnbook;
	}
	public long getBbUnbook() {
		return bbUnbook;
	}
	public void setBbUnbook(long bbUnbook) {
		this.bbUnbook = bbUnbook;
	}
	public long getNewDvb() {
		return newDvb;
	}
	public void setNewDvb(long newDvb) {
		this.newDvb = newDvb;
	}
	public long getNewVod() {
		return newVod;
	}
	public void setNewVod(long newVod) {
		this.newVod = newVod;
	}
	public long getNewBb() {
		return newBb;
	}
	public void setNewBb(long newBb) {
		this.newBb = newBb;
	}
	public long getNewVodBooked() {
		return newVodBooked;
	}
	public void setNewVodBooked(long newVodBooked) {
		this.newVodBooked = newVodBooked;
	}
	public long getNewBbBooked() {
		return newBbBooked;
	}
	public void setNewBbBooked(long newBbBooked) {
		this.newBbBooked = newBbBooked;
	}
	
	//以下是为处理查询结果:======================================
	public void add(AreaMonthReport va){
		this.activeUsers = this.activeUsers + va.getActiveUsers();
		this.onlineBooked = this.onlineBooked + va.getOnlineBooked();
		this.onlineUnbook = this.onlineUnbook + va.getOnlineUnbook();
		this.offlineBooked = this.offlineBooked + va.getOfflineBooked();
		this.offlineUnbook = this.offlineUnbook + va.getOfflineUnbook();
		this.inactiveOnline = this.inactiveOnline + va.getInactiveOnline();
		this.inactiveOffline = this.inactiveOffline + va.getInactiveOffline();
		
		this.vodExpiring = this.vodExpiring + va.getVodExpiring();
		this.vodExpiringBook = this.vodExpiringBook + va.getVodExpiringBook();
		this.vodExpiredBook = this.vodExpiredBook + va.getVodExpiredBook();
		this.vodPreBook = this.vodPreBook + va.getVodPreBook();
		this.vodNewBook = this.vodNewBook + va.getVodNewBook();
		
		this.bbExpiring = this.bbExpiring + va.getBbExpiring();
		this.bbExpiringBook = this.bbExpiringBook + va.getBbExpiringBook();
		this.bbExpiredBook = this.bbExpiredBook + va.getBbExpiredBook();
		this.bbPreBook = this.bbPreBook + va.getBbPreBook();
		this.bbNewBook = this.bbNewBook + va.getBbNewBook();
		
		this.customers = this.customers + va.getCustomers();
		this.dvbUsers = this.dvbUsers + va.getDvbUsers();
		this.vodUsers = this.vodUsers + va.getVodUsers();
		this.bbUsers = this.bbUsers + va.getBbUsers();
		this.vodBooked = this.vodBooked + va.getVodBooked();
		this.bbBooked = this.bbBooked + va.getBbBooked();
		this.vodUnbook = this.vodUnbook + va.getVodUnbook();
		this.bbUnbook = this.bbUnbook + va.getBbUnbook();
		this.newDvb= this.newDvb+ va.getNewDvb();
		this.newVod= this.newVod+ va.getNewVod();
		this.newBb= this.newBb+ va.getNewBb();
		this.newVodBooked = this.newVodBooked + va.getNewVodBooked();
		this.newBbBooked = this.newBbBooked + va.getNewBbBooked();
	}

	@Override
	public int compareTo(AreaMonthReport o) {
		if(o != null){
			if(this.reportRange  != null && o.getReportRange() != null){
				if(this.reportRange.equals(o.getReportRange())){
					return this.getRepDate().compareTo(o.getRepDate());
				}
				return 0;
			} else {
				return this.getRepDate().compareTo(o.getRepDate());
			}
		}
		return 0;
	}
	
	public AreaMonthReport clone() {
		AreaMonthReport o = (AreaMonthReport)super.clone();
		return o;
	}
}
