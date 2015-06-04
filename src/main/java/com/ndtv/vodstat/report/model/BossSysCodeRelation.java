package com.ndtv.vodstat.report.model;

import java.io.Serializable;

public class BossSysCodeRelation implements Serializable {
	public static final String TABLE_NAME = "boss_syscode_syscode";
	public static final String VIEW_NAME = "view_vodstat_syscodesyscode";
	
    private Long codeid;

    private Long codeidex;

    public Long getCodeid() {
        return codeid;
    }

    public void setCodeid(Long codeid) {
        this.codeid = codeid;
    }

    public Long getCodeidex() {
        return codeidex;
    }

    public void setCodeidex(Long codeidex) {
        this.codeidex = codeidex;
    }
}