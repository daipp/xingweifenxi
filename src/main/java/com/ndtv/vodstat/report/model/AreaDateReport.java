package com.ndtv.vodstat.report.model;


public class AreaDateReport extends AreaReport implements Comparable<AreaDateReport> {

	public static final String TABLE_NAME = "REPD_VODAREA";
	
	long customers;		//客户数
	long dvbUsers;		//基本型用户数
	long vodUsers;		//交互型用户数
	long bbUsers;		//宽带用户数
	long analogUsers;	//模拟用户数
	
	long dvbUsers0;		//正常基本型用户数
	long vodUsers0;		//正常交互型用户数
	long bbUsers0;		//正常宽带用户数
	long analogUsers0;	//正常模拟用户数

	long hdstbs;		//高清机顶盒数
	
	long hostStarts;	//当日开机的主机数
	long hostStops;		//当日停机的主机数
	long hostQuits;		//当日销户的主机数
	long hostStoped;	//停机的主机总数
	long hostNormal;	//正常的主机总数
	
	long dvbBooks;		//订购付费频道的终端数
	long vodBooks;		//订购点播节目的终端数
	long bbBooks;		//订购宽带产品的终端数
	long dvbBooksNew;	//当日新增终端数中,订购付费频道的
	long vodBooksNew;	//当日新增终端数中,订购点播节目的
	long bbBooksNew;	//当日新增终端数中,订购宽带产品的
	
	long hostUnpay1;	//收视费1年未交的(含当年)
	long hostUnpay2;	//收视费2年未交的(含当年)

	//以下针对交互用户
	long activeUsers;	//活跃用户(有华数点播记录)	
	long onlineBookedUsers;	//登录过且订购的用户
	long onlineUnbookUsers;	//登录过且未订购的用户
	
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
	public long getAnalogUsers() {
		return analogUsers;
	}
	public void setAnalogUsers(long analogUsers) {
		this.analogUsers = analogUsers;
	}
	public long getHdstbs() {
		return hdstbs;
	}
	public void setHdstbs(long hdstbs) {
		this.hdstbs = hdstbs;
	}
	public long getDvbUsers0() {
		return dvbUsers0;
	}
	public void setDvbUsers0(long dvbUsers0) {
		this.dvbUsers0 = dvbUsers0;
	}
	public long getVodUsers0() {
		return vodUsers0;
	}
	public void setVodUsers0(long vodUsers0) {
		this.vodUsers0 = vodUsers0;
	}
	public long getBbUsers0() {
		return bbUsers0;
	}
	public void setBbUsers0(long bbUsers0) {
		this.bbUsers0 = bbUsers0;
	}
	public long getAnalogUsers0() {
		return analogUsers0;
	}
	public void setAnalogUsers0(long analogUsers0) {
		this.analogUsers0 = analogUsers0;
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
	public long getHostStarts() {
		return hostStarts;
	}
	public void setHostStarts(long hostStarts) {
		this.hostStarts = hostStarts;
	}
	public long getHostStops() {
		return hostStops;
	}
	public void setHostStops(long hostStops) {
		this.hostStops = hostStops;
	}
	public long getHostQuits() {
		return hostQuits;
	}
	public void setHostQuits(long hostQuits) {
		this.hostQuits = hostQuits;
	}
	public long getHostStoped() {
		return hostStoped;
	}
	public void setHostStoped(long hostStoped) {
		this.hostStoped = hostStoped;
	}
	public long getHostNormal() {
		return hostNormal;
	}
	public void setHostNormal(long hostNormal) {
		this.hostNormal = hostNormal;
	}
	public long getDvbBooks() {
		return dvbBooks;
	}
	public void setDvbBooks(long dvbBooks) {
		this.dvbBooks = dvbBooks;
	}
	public long getVodBooks() {
		return vodBooks;
	}
	public void setVodBooks(long vodBooks) {
		this.vodBooks = vodBooks;
	}
	public long getBbBooks() {
		return bbBooks;
	}
	public void setBbBooks(long bbBooks) {
		this.bbBooks = bbBooks;
	}
	public long getDvbBooksNew() {
		return dvbBooksNew;
	}
	public void setDvbBooksNew(long dvbBooksNew) {
		this.dvbBooksNew = dvbBooksNew;
	}
	public long getVodBooksNew() {
		return vodBooksNew;
	}
	public void setVodBooksNew(long vodBooksNew) {
		this.vodBooksNew = vodBooksNew;
	}
	public long getBbBooksNew() {
		return bbBooksNew;
	}
	public void setBbBooksNew(long bbBooksNew) {
		this.bbBooksNew = bbBooksNew;
	}
	public long getHostUnpay1() {
		return hostUnpay1;
	}
	public void setHostUnpay1(long hostUnpay1) {
		this.hostUnpay1 = hostUnpay1;
	}
	public long getHostUnpay2() {
		return hostUnpay2;
	}
	public void setHostUnpay2(long hostUnpay2) {
		this.hostUnpay2 = hostUnpay2;
	}
	
	@Override
	public int compareTo(AreaDateReport o) {
		if(o != null){
			return this.getRepDate().compareTo(o.getRepDate());
		}
		return 0;
	}
	
	public Object clone() {
		AreaDateReport o = (AreaDateReport)super.clone();
		return o;
	}
	

	//以下是为处理查询结果:======================================
	public void add(AreaDateReport va){
		this.customers = this.customers + va.getCustomers();
		this.hdstbs = this.hdstbs + va.getHdstbs();
		
		this.dvbUsers = this.dvbUsers + va.getDvbUsers();
		this.bbUsers = this.bbUsers + va.getBbUsers();
		this.vodUsers = this.vodUsers + va.getVodUsers();
		this.analogUsers = this.analogUsers + va.getAnalogUsers();
		this.dvbUsers0 = this.dvbUsers0 + va.getDvbUsers0();
		this.bbUsers0 = this.bbUsers0 + va.getBbUsers0();
		this.vodUsers0 = this.vodUsers0 + va.getVodUsers0();
		this.analogUsers0 = this.analogUsers0 + va.getAnalogUsers0();
		
		this.hostStarts = this.hostStarts + va.getHostStarts();
		this.hostStops = this.hostStops + va.getHostStops();
		this.hostQuits = this.hostQuits + va.getHostQuits();
		this.hostStoped = this.hostStoped + va.getHostStoped();
		this.hostNormal = this.hostNormal + va.getHostNormal();
		
		this.dvbBooks = this.dvbBooks + va.getDvbBooks();
		this.vodBooks = this.vodBooks + va.getVodBooks();
		this.bbBooks = this.bbBooks + va.getBbBooks();
		this.dvbBooksNew = this.dvbBooksNew + va.getDvbBooksNew();
		this.vodBooksNew = this.vodBooksNew + va.getVodBooksNew();
		this.bbBooksNew = this.bbBooksNew + va.getBbBooksNew();

		this.hostUnpay1 = this.hostUnpay1 + va.getHostUnpay1();
		this.hostUnpay2 = this.hostUnpay2 + va.getHostUnpay2();
		
		this.activeUsers = this.activeUsers + va.getActiveUsers();
		this.onlineBookedUsers = this.onlineBookedUsers + va.getOnlineBookedUsers();
		this.onlineUnbookUsers = this.onlineUnbookUsers + va.getOnlineUnbookUsers();
	}

	
}
