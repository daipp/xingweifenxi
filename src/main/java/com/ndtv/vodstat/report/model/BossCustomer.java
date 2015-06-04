package com.ndtv.vodstat.report.model;

import java.io.Serializable;
import java.util.Date;

public class BossCustomer implements Serializable {
	public static final String TABLE_NAME = "boss_customer";
	public static final String VIEW_NAME = "";
	
    private Long customerid;

    private String customername;

    private Long servicecardid;

    private String operator;

    private Date crtime;

    private Long districtid;

    private String district;

    private String town;

    private Long townid;

    private String community;

    private Long communityid;

    private String village;

    private Long villageid;

    private String fulladdress;

    private Long customertypeid;

    private String customertype;

    private Long importancelevelid;

    private String importancelevel;

    private Long securitylevelid;

    private String securitylevel;

    private Long certificatetypeid;

    private String certificatetype;

    private String certificatecode;

    private Long shopid;

    private String shop;

    private String zipcode;

    private String memo;

    public Long getCustomerid() {
        return customerid;
    }

    public void setCustomerid(Long customerid) {
        this.customerid = customerid;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername == null ? null : customername.trim();
    }

    public Long getServicecardid() {
        return servicecardid;
    }

    public void setServicecardid(Long servicecardid) {
        this.servicecardid = servicecardid;
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

    public Long getDistrictid() {
        return districtid;
    }

    public void setDistrictid(Long districtid) {
        this.districtid = districtid;
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

    public Long getTownid() {
        return townid;
    }

    public void setTownid(Long townid) {
        this.townid = townid;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community == null ? null : community.trim();
    }

    public Long getCommunityid() {
        return communityid;
    }

    public void setCommunityid(Long communityid) {
        this.communityid = communityid;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village == null ? null : village.trim();
    }

    public Long getVillageid() {
        return villageid;
    }

    public void setVillageid(Long villageid) {
        this.villageid = villageid;
    }

    public String getFulladdress() {
        return fulladdress;
    }

    public void setFulladdress(String fulladdress) {
        this.fulladdress = fulladdress == null ? null : fulladdress.trim();
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

    public Long getSecuritylevelid() {
        return securitylevelid;
    }

    public void setSecuritylevelid(Long securitylevelid) {
        this.securitylevelid = securitylevelid;
    }

    public String getSecuritylevel() {
        return securitylevel;
    }

    public void setSecuritylevel(String securitylevel) {
        this.securitylevel = securitylevel == null ? null : securitylevel.trim();
    }

    public Long getCertificatetypeid() {
        return certificatetypeid;
    }

    public void setCertificatetypeid(Long certificatetypeid) {
        this.certificatetypeid = certificatetypeid;
    }

    public String getCertificatetype() {
        return certificatetype;
    }

    public void setCertificatetype(String certificatetype) {
        this.certificatetype = certificatetype == null ? null : certificatetype.trim();
    }

    public String getCertificatecode() {
        return certificatecode;
    }

    public void setCertificatecode(String certificatecode) {
        this.certificatecode = certificatecode == null ? null : certificatecode.trim();
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

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode == null ? null : zipcode.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }
}