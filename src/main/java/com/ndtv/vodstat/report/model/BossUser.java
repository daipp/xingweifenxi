package com.ndtv.vodstat.report.model;

import java.io.Serializable;
import java.util.Date;

public class BossUser implements Serializable {
	public static final String TABLE_NAME = "boss_user";
	public static final String VIEW_NAME = "view_vodstat_user";
	
    private Long userid;

    private Long customerid;

    private Long accountid;

    private Long contactid;

    private Long customertypeid;

    private String customertype;

    private Long importancelevelid;

    private String importancelevel;

    private String resno;

    private String shortsim;

    private String simserialno;

    private String stbserialno;

    private Long stbtypeid;

    private String stbtype;

    private Long stbid;

    private String stb;

    private Long obtainwayid;

    private String obtainway;

    private String operator;

    private Date crtime;

    private Long shopid;

    private String shop;

    private Long userstateid;

    private String userstate;
    
    private Date userstatechangetime;

    private Long usertypeid;

    private String usertype;

    private Long usergroupid;

    private String usergroup;

    private Long districtid;

    private Long townid;

    private Long communityid;

    private Long villageid;

    private String district;

    private String town;

    private String community;

    private String village;

    private String fulladdress;

    private String customername;

    private String phone;

    private String mobile;

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getCustomerid() {
        return customerid;
    }

    public void setCustomerid(Long customerid) {
        this.customerid = customerid;
    }

    public Long getAccountid() {
        return accountid;
    }

    public void setAccountid(Long accountid) {
        this.accountid = accountid;
    }

    public Long getContactid() {
        return contactid;
    }

    public void setContactid(Long contactid) {
        this.contactid = contactid;
    }

    public Long getCustomertypeid() {
        return customertypeid;
    }

    public void setCustomertypeid(Long customertypeid) {
        this.customertypeid = customertypeid;
    }

    public String getCustomertype() {
        return customertype;
    }

    public void setCustomertype(String customertype) {
        this.customertype = customertype == null ? null : customertype.trim();
    }

    public Long getImportancelevelid() {
        return importancelevelid;
    }

    public void setImportancelevelid(Long importancelevelid) {
        this.importancelevelid = importancelevelid;
    }

    public String getImportancelevel() {
        return importancelevel;
    }

    public void setImportancelevel(String importancelevel) {
        this.importancelevel = importancelevel == null ? null : importancelevel.trim();
    }

    public String getResno() {
        return resno;
    }

    public void setResno(String resno) {
        this.resno = resno == null ? null : resno.trim();
    }

    public String getShortsim() {
        return shortsim;
    }

    public void setShortsim(String shortsim) {
        this.shortsim = shortsim == null ? null : shortsim.trim();
    }

    public String getSimserialno() {
        return simserialno;
    }

    public void setSimserialno(String simserialno) {
        this.simserialno = simserialno == null ? null : simserialno.trim();
    }

    public String getStbserialno() {
        return stbserialno;
    }

    public void setStbserialno(String stbserialno) {
        this.stbserialno = stbserialno == null ? null : stbserialno.trim();
    }

    public Long getStbtypeid() {
        return stbtypeid;
    }

    public void setStbtypeid(Long stbtypeid) {
        this.stbtypeid = stbtypeid;
    }

    public String getStbtype() {
        return stbtype;
    }

    public void setStbtype(String stbtype) {
        this.stbtype = stbtype == null ? null : stbtype.trim();
    }

    public Long getStbid() {
        return stbid;
    }

    public void setStbid(Long stbid) {
        this.stbid = stbid;
    }

    public String getStb() {
        return stb;
    }

    public void setStb(String stb) {
        this.stb = stb == null ? null : stb.trim();
    }

    public Long getObtainwayid() {
        return obtainwayid;
    }

    public void setObtainwayid(Long obtainwayid) {
        this.obtainwayid = obtainwayid;
    }

    public String getObtainway() {
        return obtainway;
    }

    public void setObtainway(String obtainway) {
        this.obtainway = obtainway == null ? null : obtainway.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public Date getCrtime() {
        return crtime;
    }

    public void setCrtime(Date crtime) {
        this.crtime = crtime;
    }

    public Long getShopid() {
        return shopid;
    }

    public void setShopid(Long shopid) {
        this.shopid = shopid;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop == null ? null : shop.trim();
    }

    public Long getUserstateid() {
        return userstateid;
    }

    public void setUserstateid(Long userstateid) {
        this.userstateid = userstateid;
    }

    public String getUserstate() {
        return userstate;
    }

    public void setUserstate(String userstate) {
        this.userstate = userstate == null ? null : userstate.trim();
    }

    public Long getUsertypeid() {
        return usertypeid;
    }

	public Date getUserstatechangetime() {
		return userstatechangetime;
	}

	public void setUserstatechangetime(Date userstatechangetime) {
		this.userstatechangetime = userstatechangetime;
	}

    public void setUsertypeid(Long usertypeid) {
        this.usertypeid = usertypeid;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype == null ? null : usertype.trim();
    }

    public Long getUsergroupid() {
        return usergroupid;
    }

    public void setUsergroupid(Long usergroupid) {
        this.usergroupid = usergroupid;
    }

    public String getUsergroup() {
        return usergroup;
    }

    public void setUsergroup(String usergroup) {
        this.usergroup = usergroup == null ? null : usergroup.trim();
    }

    public Long getDistrictid() {
        return districtid;
    }

    public void setDistrictid(Long districtid) {
        this.districtid = districtid;
    }

    public Long getTownid() {
        return townid;
    }

    public void setTownid(Long townid) {
        this.townid = townid;
    }

    public Long getCommunityid() {
        return communityid;
    }

    public void setCommunityid(Long communityid) {
        this.communityid = communityid;
    }

    public Long getVillageid() {
        return villageid;
    }

    public void setVillageid(Long villageid) {
        this.villageid = villageid;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district == null ? null : district.trim();
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town == null ? null : town.trim();
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community == null ? null : community.trim();
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village == null ? null : village.trim();
    }

    public String getFulladdress() {
        return fulladdress;
    }

    public void setFulladdress(String fulladdress) {
        this.fulladdress = fulladdress == null ? null : fulladdress.trim();
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername == null ? null : customername.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }
}