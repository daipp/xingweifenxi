package com.ndtv.vodstat.boss.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

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

public interface BossViewMapper extends BossMybatisSuperMapper{

	public BossCustomer getCustomer(@Param("customerId")Long customerId);
	
	public List<BossSysCode> getSysCodes();
	
	public List<BossSysCodeRelation> getSysCodeRelations();

	public List<BossProduct> getProductDefs();

	public List<BossPack> getPackDefs();
	
	public List<BossPackdeal> getPackdealDefs();
	
	public List<Long> getModifiedCustomerIds(@Param("queryMonth")String queryMonth,
			@Param("beginTime")Date beginTime,@Param("endTime")Date endTime);
	
	public List<BossCustomer> getCustomers(List<Long> customerIds);
	
	public List<BossUser> getUsersByCustomerIds(List<Long> customerIds);

	public List<BossUserPack> getUserPacksByUserIds(List<Long> userIds);

	public List<BossUserProduct> getUserProductsByUserIds(List<Long> userIds);

	public List<BossWasuSubCode> getWasuSubCodesByUserIds(List<Long> userIds);
	
	
	

}
