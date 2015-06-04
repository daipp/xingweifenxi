package com.ndtv.vodstat.report.model;

import java.util.Date;

public class WasunLog extends LogCondtion {
	
	Date beginTime;
	Date endTime;
	String fee;
	String ppvId;
	String filmName;
	String displayPath;
	String subscriberCode;
	String billingNo;
	String catergory;
	long times;

	public WasunLog(){super();}
	public WasunLog(String catergory,long times){
		super();
		this.catergory = catergory;
		this.times = times;
	}
	public WasunLog(Date beginTime,long times){
		super();
		this.beginTime = beginTime;
		this.times = times;
	}
	
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getPpvId() {
		return ppvId;
	}
	public void setPpvId(String ppvId) {
		this.ppvId = ppvId;
	}
	public String getFilmName() {
		return filmName;
	}
	public void setFilmName(String filmName) {
		this.filmName = filmName;
	}
	public String getDisplayPath() {
		return displayPath;
	}
	public void setDisplayPath(String displayPath) {
		this.displayPath = displayPath;
	}
	public String getSubscriberCode() {
		return subscriberCode;
	}
	public void setSubscriberCode(String subscriberCode) {
		this.subscriberCode = subscriberCode;
	}
	public String getBillingNo() {
		return billingNo;
	}
	public void setBillingNo(String billingNo) {
		this.billingNo = billingNo;
	}
	public String getCatergory() {
		return catergory;
	}
	public void setCatergory(String catergory) {
		this.catergory = catergory;
	}
	public long getTimes() {
		return times;
	}
	public void setTimes(long times) {
		this.times = times;
	}

}
