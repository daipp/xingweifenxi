package com.ndtv.vodstat.report.model;

import java.io.Serializable;

public class BossWasuSubCode implements Serializable {
	public static final String TABLE_NAME = "boss_wasusubcode";
	public static final String VIEW_NAME = "view_vodstat_wasusubcode";
	
    private Long userid;

    private String subcode;

    private Long stateid;

    private String state;

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getSubcode() {
        return subcode;
    }

    public void setSubcode(String subcode) {
        this.subcode = subcode == null ? null : subcode.trim();
    }

    public Long getStateid() {
        return stateid;
    }

    public void setStateid(Long stateid) {
        this.stateid = stateid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }
}