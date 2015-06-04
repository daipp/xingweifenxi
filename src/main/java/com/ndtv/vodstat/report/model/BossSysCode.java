package com.ndtv.vodstat.report.model;

import java.io.Serializable;
import java.util.Date;

public class BossSysCode implements Serializable {

	public static final String TABLE_NAME = "boss_syscode";
	public static final String VIEW_NAME = "view_vodstat_syscode";

	long typeId;
	String typeName;
	long codeId;
	String codeName;
	String codeContent;
	int state;
	Date crtime;
	String memo;
	
	public long getTypeId() {
		return typeId;
	}
	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public long getCodeId() {
		return codeId;
	}
	public void setCodeId(long codeId) {
		this.codeId = codeId;
	}
	public String getCodeName() {
		return codeName;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	public String getCodeContent() {
		return codeContent;
	}
	public void setCodeContent(String codeContent) {
		this.codeContent = codeContent;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public Date getCrtime() {
		return crtime;
	}
	public void setCrtime(Date crtime) {
		this.crtime = crtime;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public static String getTableName() {
		return TABLE_NAME;
	}
}
