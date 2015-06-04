package com.ndtv.vodstat.report.model;

import java.io.Serializable;

public class BossProduct implements Serializable {
	
	long productId;
	String productName;
	long feesystemId;
	String feesystem;
	
	public static final String TABLE_NAME = "boss_product";
	public static final String VIEW_NAME = "view_vodstat_product";
	
	
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public long getFeesystemId() {
		return feesystemId;
	}
	public void setFeesystemId(long feesystemId) {
		this.feesystemId = feesystemId;
	}
	public String getFeesystem() {
		return feesystem;
	}
	public void setFeesystem(String feesystem) {
		this.feesystem = feesystem;
	}

}
