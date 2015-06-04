package com.ndtv.vodstat.report.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ndtv.vodstat.common.util.DateFunctions;


public class AreaCondition implements Serializable,Cloneable {

	public static final String TABLE_NAME1 = "REPD_VODAREA";
	public static final String TABLE_NAME2 = "REPM_VODAREA";
	
	public static final String REPORT_AreaMonthReport = "AreaMonthReport";
	public static final String REPORT_AreaDateReport = "AreaDateReport";

	//以下是查询条件:======================================
	
	//报表类型
	String report;

	//统计的月份
	String[] queryMonths;
	String queryMonth;
	
	//查当天
	Date repDate;
	
	//统计日期的岂止
	Date repDate1;
	Date repDate2;
	
	//上个周期末的日期
	Date repDateLast;

	//多选的客户类型编号,街道,社区,小区
	long[] customerTypeIds;
	long[] userTypeIds;
	long[] townIds;
	long[] communityIds;
	long[] villageIds;
	//单选的用户类型
	long userTypeId;
	//单选的用户状态;
	long userStateId;

	//是否分行显示:
	boolean showRepDate;
	boolean showCustomerType;
	boolean showTown;
	boolean showCommunity;
	boolean showVillage;
	boolean showUserType;
	//是否显示该列数据的折线图；
	boolean showActiveUsers;
	boolean showOnlineBookedUsers;
	boolean showOnlineUnbookUsers;
	boolean showCustomers;
	boolean showDvbUsers;
	boolean showBbUsers;
	boolean showVodUsers;
	boolean showHdstbs;
	//饼状图数据依据
	String field;//数据库字段对应
	String field2;
	//所查的报表周期
	String reportRange;
	
	//查询结果范围(for 某些分页)
	int firstResult;
	int maxResults;

	//所查表格名称
	String tableName;
	//查询操作员
	long queryUserId;
	
	String getWhat;
	
	//用户查询入口
	String conditionType1;
	String conditionValue1;
	String conditionType2;
	String conditionValue2;
	
	public String getReport() {
		return report;
	}
	public void setReport(String report) {
		this.report = report;
	}
	public Date getRepDate() {
		return repDate;
	}
	public void setRepDate(Date repDate) {
		this.repDate = repDate;
	}
	public String[] getQueryMonths() {
		return queryMonths;
	}
	public void setQueryMonths(String[] queryMonths) {
		this.queryMonths = queryMonths;
	}
	public String getQueryMonth() {
		return queryMonth;
	}
	public void setQueryMonth(String queryMonth) {
		this.queryMonth = queryMonth;
	}
	public Date getRepDate1() {
		return repDate1;
	}
	public void setRepDate1(Date repDate1) {
		this.repDate1 = repDate1;
	}
	public Date getRepDate2() {
		return repDate2;
	}
	public void setRepDate2(Date repDate2) {
		this.repDate2 = repDate2;
	}
	public Date getRepDateLast() {
		return repDateLast;
	}
	public void setRepDateLast(Date repDateLast) {
		this.repDateLast = repDateLast;
	}
	public String getRepYear() {
		if(this.repDate == null){
			return null;
		}
		return new SimpleDateFormat("yyyy").format(this.repDate);
	}
	public String getRepMonth() {
		if(this.repDate == null){
			return null;
		}
		return new SimpleDateFormat("yyyyMM").format(this.repDate);
	}
	public void setRepMonth(String repDateMonth) {
		if(repDateMonth == null){
			return;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
		if(repDateMonth.indexOf("-")>0){
			df = new SimpleDateFormat("yyyy-MM");
		}
		try{
			this.repDate = df.parse(repDateMonth);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public String getRepYear1() {
		if(this.repDate1 == null){
			return null;
		}
		return new SimpleDateFormat("yyyy").format(this.repDate1);
	}
	public void setRepMonth1(String repDateMonth) {
		if(repDateMonth == null){
			return;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
		if(repDateMonth.indexOf("-")>0){
			df = new SimpleDateFormat("yyyy-MM");
		}
		try{
			this.repDate1 = df.parse(repDateMonth);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public void setRepMonth2(String repDateMonth) {
		if(repDateMonth == null){
			return;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
		if(repDateMonth.indexOf("-")>0){
			df = new SimpleDateFormat("yyyy-MM");
		}
		try{
			this.repDate2 = DateFunctions.getMonthLastDay(df.parse(repDateMonth));
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public long[] getCustomerTypeIds() {
		return customerTypeIds;
	}
	public void setCustomerTypeIds(Long[] customerTypeIds) {
		this.customerTypeIds = new long[customerTypeIds.length];
		for(int i=0;i<customerTypeIds.length;i++){
			this.customerTypeIds[i]=(customerTypeIds[i]==null?0:customerTypeIds[i]);
		}
	}
	public void setCustomerTypeIds(long[] customerTypeIds) {
		this.customerTypeIds = customerTypeIds;
	}
	public long[] getTownIds() {
		return townIds;
	}
	public void setTownIds(long[] townIds) {
		this.townIds = townIds;
	}
	public void setTownIds(Long[] townIds) {
		this.townIds = new long[townIds.length];
		for(int i=0;i<townIds.length;i++){
			this.townIds[i]=(townIds[i]==null?0:townIds[i]);
		}
	}
	public long[] getCommunityIds() {
		return communityIds;
	}
	public void setCommunityIds(long[] communityIds) {
		this.communityIds = communityIds;
	}
	public void setCommunityIds(Long[] communityIds) {
		this.communityIds = new long[communityIds.length];
		for(int i=0;i<communityIds.length;i++){
			this.communityIds[i]=(communityIds[i]==null?0:communityIds[i]);
		}
	}
	public long[] getVillageIds() {
		return villageIds;
	}
	public void setVillageIds(long[] villageIds) {
		this.villageIds = villageIds;
	}
	public void setVillageIds(Long[] villageIds) {
		this.villageIds = new long[villageIds.length];
		for(int i=0;i<villageIds.length;i++){
			this.villageIds[i]=(villageIds[i]==null?0:villageIds[i]);
		}
	}

	public long[] getUserTypeIds() {
		return userTypeIds;
	}
	public void setUserTypeIds(long[] userTypeIds) {
		this.userTypeIds = userTypeIds;
	}
	public long getUserTypeId() {
		return userTypeId;
	}
	public void setUserTypeId(long userTypeId) {
		this.userTypeId = userTypeId;
	}
	public long getUserStateId() {
		return userStateId;
	}
	public void setUserStateId(long userStateId) {
		this.userStateId = userStateId;
	}
	public boolean isShowRepDate() {
		return showRepDate;
	}
	public void setShowRepDate(boolean showRepDate) {
		this.showRepDate = showRepDate;
	}
	public boolean isShowCustomerType() {
		return showCustomerType;
	}
	public void setShowCustomerType(boolean showCustomerType) {
		this.showCustomerType = showCustomerType;
	}
	public boolean isShowTown() {
		return showTown;
	}
	public void setShowTown(boolean showTown) {
		this.showTown = showTown;
	}
	public boolean isShowCommunity() {
		return showCommunity;
	}
	public void setShowCommunity(boolean showCommunity) {
		this.showCommunity = showCommunity;
	}
	public boolean isShowVillage() {
		return showVillage;
	}
	public void setShowVillage(boolean showVillage) {
		this.showVillage = showVillage;
	}
	public boolean isShowUserType() {
		return showUserType;
	}
	public void setShowUserType(boolean showUserType) {
		this.showUserType = showUserType;
	}

	public String getReportRange() {
		return reportRange;
	}
	public void setReportRange(String reportRange) {
		this.reportRange = reportRange;
	}
	
	public int getFirstResult() {
		return firstResult;
	}
	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}
	public int getMaxResults() {
		return maxResults;
	}
	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}
	
	public long getQueryUserId() {
		return queryUserId;
	}
	public void setQueryUserId(long queryUserId) {
		this.queryUserId = queryUserId;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getGetWhat() {
		return getWhat;
	}
	public void setGetWhat(String getWhat) {
		this.getWhat = getWhat;
	}
	
	
	public Object clone() {
		AreaCondition o = null;
		try {
			o = (AreaCondition)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}
	public String getConditionType1() {
		return conditionType1;
	}
	public void setConditionType1(String conditionType1) {
		this.conditionType1 = conditionType1;
	}
	public String getConditionValue1() {
		return conditionValue1;
	}
	public void setConditionValue1(String conditionValue1) {
		this.conditionValue1 = conditionValue1;
	}
	public String getConditionType2() {
		return conditionType2;
	}
	public void setConditionType2(String conditionType2) {
		this.conditionType2 = conditionType2;
	}
	public String getConditionValue2() {
		return conditionValue2;
	}
	public void setConditionValue2(String conditionValue2) {
		this.conditionValue2 = conditionValue2;
	}
	public boolean isShowActiveUsers() {
		return showActiveUsers;
	}
	public void setShowActiveUsers(boolean showActiveUsers) {
		this.showActiveUsers = showActiveUsers;
	}
	public boolean isShowOnlineBookedUsers() {
		return showOnlineBookedUsers;
	}
	public void setShowOnlineBookedUsers(boolean showOnlineBookedUsers) {
		this.showOnlineBookedUsers = showOnlineBookedUsers;
	}
	public boolean isShowOnlineUnbookUsers() {
		return showOnlineUnbookUsers;
	}
	public void setShowOnlineUnbookUsers(boolean showOnlineUnbookUsers) {
		this.showOnlineUnbookUsers = showOnlineUnbookUsers;
	}
	public boolean isShowCustomers() {
		return showCustomers;
	}
	public void setShowCustomers(boolean showCustomers) {
		this.showCustomers = showCustomers;
	}
	public boolean isShowDvbUsers() {
		return showDvbUsers;
	}
	public void setShowDvbUsers(boolean showDvbUsers) {
		this.showDvbUsers = showDvbUsers;
	}
	public boolean isShowBbUsers() {
		return showBbUsers;
	}
	public void setShowBbUsers(boolean showBbUsers) {
		this.showBbUsers = showBbUsers;
	}
	public boolean isShowHdstbs() {
		return showHdstbs;
	}
	public void setShowHdstbs(boolean showHdstbs) {
		this.showHdstbs = showHdstbs;
	}
	public boolean isShowVodUsers() {
		return showVodUsers;
	}
	public void setShowVodUsers(boolean showVodUsers) {
		this.showVodUsers = showVodUsers;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		if(field.equals("activeUsers")){
			this.field="active_users";
		}
		if(field.equals("onlineBookedUsers")){
			this.field="online_booked_users";
		}
		if(field.equals("onlineUnbookUsers")){
			this.field="online_unbook_users";
		}
		if(field.equals("customers")){
			this.field="customers";
		}
		if(field.equals("hdstbs")){
			this.field="hdstbs";
		}
		if(field.equals("dvbUsers")){
			this.field="dvb_users";
		}		
		if(field.equals("vodUsers")){
			this.field="vod_users";
		}
		if(field.equals("bbUsers")){
			this.field="bb_users";
		}
		if(field.equals("analogUsers")){
			this.field="analog_users";
		}
		if(field.equals("dvbUsers0")){
			this.field="dvb_users0";
		}
		if(field.equals("vodUsers0")){
			this.field="vod_users0";
		}
		if(field.equals("bbUsers0")){
			this.field="bb_users0";
		}		
		if(field.equals("analogUsers0")){
			this.field="analog_users0";
		}
		if(field.equals("dvbBooks")){
			this.field="dvb_books";
		}
		if(field.equals("vodBooks")){
			this.field="vod_books";
		}
		if(field.equals("bbBooks")){
			this.field="bb_books";
		}
		if(field.equals("dvbBooksNew")){
			this.field="dvb_books_new";
		}		
		if(field.equals("vodBooksNew")){
			this.field="vod_books_new";
		}
		if(field.equals("bbBooksNew")){
			this.field="bb_books_new";
		}
		if(field.equals("hostStarts")){
			this.field="host_starts";
		}
		if(field.equals("hostStops")){
			this.field="host_stops";
		}
		if(field.equals("hostQuits")){
			this.field="host_quits";
		}		
		if(field.equals("hostNormal")){
			this.field="host_normal";
		}
		if(field.equals("hostStoped")){
			this.field="host_stoped";
		}
		if(field.equals("hostUnpay1")){
			this.field="host_unpay1";
		}
		if(field.equals("hostUnpay2")){
			this.field="host_unpay2";
		}
	}
	public String getField2() {
		return field2;
	}
	public void setField2(String field2) {
		this.field2 = field2;
	}
}
