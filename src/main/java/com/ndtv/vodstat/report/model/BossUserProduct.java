package com.ndtv.vodstat.report.model;

import java.io.Serializable;
import java.util.Date;

public class BossUserProduct implements Serializable {
	public static final String TABLE_NAME = "boss_userproduct";
	public static final String VIEW_NAME = "view_vodstat_userpproduct";
	
    private Long userid;

    private Long bookid;

    private Long productid;

    private String productname;

    private Date begintime;

    private Date endtime;

    private Date crtime;
    
    private String producttype;

    private String authoritycode;

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

    public Long getProductid() {
        return productid;
    }

    public void setProductid(Long productid) {
        this.productid = productid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname == null ? null : productname.trim();
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

	public String getProducttype() {
        return producttype;
    }

    public void setProducttype(String producttype) {
        this.producttype = producttype == null ? null : producttype.trim();
    }

    public String getAuthoritycode() {
        return authoritycode;
    }

    public void setAuthoritycode(String authoritycode) {
        this.authoritycode = authoritycode == null ? null : authoritycode.trim();
    }
}