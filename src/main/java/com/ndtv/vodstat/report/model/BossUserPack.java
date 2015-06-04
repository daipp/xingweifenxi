package com.ndtv.vodstat.report.model;

import java.io.Serializable;
import java.util.Date;

public class BossUserPack implements Serializable {
	public static final String TABLE_NAME = "boss_userpack";
	public static final String VIEW_NAME = "view_vodstat_userpack";
	
    private Long userid;

    private Long bookid;

    private Long packid;

    private String packname;

    private Long fee;

    private Date begintime;

    private Date endtime;

    private Date crtime;

    private Long giftcardid;

    private String packtype;

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getBookid() {
        return bookid;
    }

    public void setBookid(Long bookid) {
        this.bookid = bookid;
    }

    public Long getPackid() {
        return packid;
    }

    public void setPackid(Long packid) {
        this.packid = packid;
    }

    public String getPackname() {
        return packname;
    }

    public void setPackname(String packname) {
        this.packname = packname == null ? null : packname.trim();
    }

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }

    public Date getBegintime() {
        return begintime;
    }

    public void setBegintime(Date begintime) {
        this.begintime = begintime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Date getCrtime() {
        return crtime;
    }

    public void setCrtime(Date crtime) {
        this.crtime = crtime;
    }

    public Long getGiftcardid() {
        return giftcardid;
    }

    public void setGiftcardid(Long giftcardid) {
        this.giftcardid = giftcardid;
    }

    public String getPacktype() {
        return packtype;
    }

    public void setPacktype(String packtype) {
        this.packtype = packtype == null ? null : packtype.trim();
    }
}