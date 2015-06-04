package com.ndtv.vodstat.report.model;

import java.io.Serializable;

public class BossPackdeal implements Serializable {
	
	long packdealId;
	String packdealName;
	long unitId;
	String unit;
	long unitCount;
	long price;
	
	public static final String TABLE_NAME = "boss_packdeal";
	public static final String VIEW_NAME = "view_vodstat_packdeal";
	
	public long getPackdealId() {
		return packdealId;
	}
	public void setPackdealId(long packdealId) {
		this.packdealId = packdealId;
	}
	public String getPackdealName() {
		return packdealName;
	}
	public void setPackdealName(String packdealName) {
		this.packdealName = packdealName;
	}
	public long getUnitId() {
		return unitId;
	}
	public void setUnitId(long unitId) {
		this.unitId = unitId;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public long getUnitCount() {
		return unitCount;
	}
	public void setUnitCount(long unitCount) {
		this.unitCount = unitCount;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}

}
