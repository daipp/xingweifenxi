package com.ndtv.vodstat.report.model;

import java.io.Serializable;
import java.util.Date;

public class LogCondtion implements Serializable{
	long userId;
	String[] queryMonthParams;
	String queryMonthParam;
	Date repDate1;
	Date repDate2;

	public LogCondtion(){super();}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String[] getQueryMonthParams() {
		return queryMonthParams;
	}
	public void setQueryMonthParams(String[] queryMonthParams) {
		this.queryMonthParams = queryMonthParams;
	}
	public String getQueryMonthParam() {
		return queryMonthParam;
	}
	public void setQueryMonthParam(String queryMonthParam) {
		this.queryMonthParam = queryMonthParam;
	}
	public Date getRepDate1() {
		return repDate1;
	}
	public void setRepDate1(Date repDate1) {
		this.repDate1 = repDate1;
	}
	public Date getRepDate2() {
		return repDate2;
	}
	public void setRepDate2(Date repDate2) {
		this.repDate2 = repDate2;
	}
	
	
}
