package com.ndtv.vodstat.report.model;

import java.util.Date;

public class EpgLog extends LogCondtion {
	String resno;
	Date logTime;
	
	public EpgLog(){super();}
	public EpgLog(String resno,Date logTime){
		super();
		this.resno = resno;
		this.logTime = logTime;
	}
	
	public String getResno() {
		return resno;
	}
	public void setResno(String resno) {
		this.resno = resno;
	}
	public Date getLogTime() {
		return logTime;
	}
	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}

}
