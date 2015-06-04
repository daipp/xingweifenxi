package com.ndtv.vodstat.report.model;

import java.io.Serializable;

public class BossPack implements Serializable {
	
	long packId;
	String packName;
	long itemtypeId;
	String itemtype;
	long unitId;
	String unit;
	long price;
	
	public static final String TABLE_NAME = "boss_pack";
	public static final String VIEW_NAME = "view_vodstat_pack";
	
	
	public long getPackId() {
		return packId;
	}
	public void setPackId(long packId) {
		this.packId = packId;
	}
	public String getPackName() {
		return packName;
	}
	public void setPackName(String packName) {
		this.packName = packName;
	}
	public long getItemtypeId() {
		return itemtypeId;
	}
	public void setItemtypeId(long itemtypeId) {
		this.itemtypeId = itemtypeId;
	}
	public String getItemtype() {
		return itemtype;
	}
	public void setItemtype(String itemtype) {
		this.itemtype = itemtype;
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
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	
}
