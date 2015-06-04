package com.ndtv.vodstat.report.model;

import java.io.Serializable;

public class TopResult extends AreaReport implements Serializable {
	
	//一下针对TopN热度查询
		long clickTimes;//登录次数
		long viewTime;//观看时长
	    long userId;
	    String fullAddress;
	    String customername;
	    String filmname;
	    String catergory;
		public long getClickTimes() {
			return clickTimes;
		}
		public void setClickTimes(long clickTimes) {
			this.clickTimes = clickTimes;
		}
		public long getViewTime() {
			return viewTime;
		}
		public void setViewTime(long viewTime) {
			this.viewTime = viewTime;
		}
		public long getUserId() {
			return userId;
		}
		public void setUserId(long userId) {
			this.userId = userId;
		}
		public String getFullAddress() {
			return fullAddress;
		}
		public void setFullAddress(String fullAddress) {
			this.fullAddress = fullAddress;
		}
		public String getCustomername() {
			return customername;
		}
		public void setCustomername(String customername) {
			this.customername = customername;
		}
		public String getFilmname() {
			return filmname;
		}
		public void setFilmname(String filmname) {
			this.filmname = filmname;
		}
		public String getCatergory() {
			return catergory;
		}
		public void setCatergory(String catergory) {
			this.catergory = catergory;
		}

}
