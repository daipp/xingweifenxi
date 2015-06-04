package com.ndtv.vodstat.report.model;

import java.util.Date;


public class VodAreaCondition extends AreaCondition {

	//以下是查询条件:======================================

	//活跃日期范围
	Date activeDate1;
	Date activeDate2;
	
	//登录次数范围
	long onlineTimes1=-1;
	long onlineTimes2=-1;
	
	//到期日期范围
	Date expiredDate1;
	Date expiredDate2;
	
	//订购日期范围
	Date bookDate1;
	Date bookDate2;
	
	//点播次数范围
	long activeTimes1=-1;
	long activeTimes2=-1;
	
	public Date getActiveDate1() {
		return activeDate1;
	}
	public void setActiveDate1(Date activeDate1) {
		this.activeDate1 = activeDate1;
	}
	public Date getActiveDate2() {
		return activeDate2;
	}
	public void setActiveDate2(Date activeDate2) {
		this.activeDate2 = activeDate2;
	}
	public Date getExpiredDate1() {
		return expiredDate1;
	}
	public void setExpiredDate1(Date expiredDate1) {
		this.expiredDate1 = expiredDate1;
	}
	public Date getExpiredDate2() {
		return expiredDate2;
	}
	public void setExpiredDate2(Date expiredDate2) {
		this.expiredDate2 = expiredDate2;
	}
	public long getOnlineTimes1() {
		return onlineTimes1;
	}
	public void setOnlineTimes1(long onlineTimes1) {
		this.onlineTimes1 = onlineTimes1;
	}
	public long getOnlineTimes2() {
		return onlineTimes2;
	}
	public void setOnlineTimes2(long onlineTimes2) {
		this.onlineTimes2 = onlineTimes2;
	}
	public long getActiveTimes1() {
		return activeTimes1;
	}
	public void setActiveTimes1(long activeTimes1) {
		this.activeTimes1 = activeTimes1;
	}
	public long getActiveTimes2() {
		return activeTimes2;
	}
	public void setActiveTimes2(long activeTimes2) {
		this.activeTimes2 = activeTimes2;
	}
	
	public Date getBookDate1() {
		return bookDate1;
	}
	public void setBookDate1(Date bookDate1) {
		this.bookDate1 = bookDate1;
	}
	public Date getBookDate2() {
		return bookDate2;
	}
	public void setBookDate2(Date bookDate2) {
		this.bookDate2 = bookDate2;
	}
	
	public Object clone() {
		VodAreaCondition o = (VodAreaCondition)super.clone();
		return o;
	}
}
