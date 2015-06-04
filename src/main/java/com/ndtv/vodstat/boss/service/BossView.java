package com.ndtv.vodstat.boss.service;

import java.util.Date;
import java.util.List;

import com.ndtv.vodstat.report.model.BossCustomer;
import com.ndtv.vodstat.report.model.BossPack;
import com.ndtv.vodstat.report.model.BossPackdeal;
import com.ndtv.vodstat.report.model.BossProduct;
import com.ndtv.vodstat.report.model.BossSysCode;
import com.ndtv.vodstat.report.model.BossSysCodeRelation;
import com.ndtv.vodstat.report.model.BossUser;
import com.ndtv.vodstat.report.model.BossUserPack;
import com.ndtv.vodstat.report.model.BossUserProduct;
import com.ndtv.vodstat.report.model.BossWasuSubCode;


public interface BossView {
	
	public static final String IMP_BEAN = "BossViewImpl";
	
	public BossCustomer getCustomer(Long customerId) ;

	public List<BossSysCode> getSysCodes();
	
	public List<BossProduct> getProducts();
	public List<BossPack> getPacks();
	public List<BossPackdeal> getPackdeals();

	public List<BossSysCodeRelation> getSysCodeRelations();

	public List<Long> getModifiedCustomerIds(String queryMonth, Date beginTime, Date endTime);

	public List<BossCustomer> getCustomers(List<Long> customerIds);

	public List<BossUser> getUsersByCustomerIds(List<Long> customerIds);

	public List<BossUserPack> getUserPacksByUserIds(List<Long> userIds);

	public List<BossUserProduct> getUserProductsByUserIds(List<Long> userIds);

	public List<BossWasuSubCode> getWasuSubCodesByUserIds(List<Long> userIds);
	
}
