package com.ndtv.vodstat.boss.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ndtv.vodstat.boss.dao.BossViewMapper;
import com.ndtv.vodstat.boss.service.BossView;
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

@Service
public class BossViewImpl implements BossView{

	@Resource
	private BossViewMapper bossViewMapper;

	@Override
	public BossCustomer getCustomer(Long customerId) {
		return bossViewMapper.getCustomer(customerId);
	}

	public List<BossSysCode> getSysCodes() {
		return bossViewMapper.getSysCodes();
	}

	public List<BossSysCodeRelation> getSysCodeRelations() {
		return bossViewMapper.getSysCodeRelations();
	}

	public List<Long> getModifiedCustomerIds(String queryMonth, Date beginTime,
			Date endTime) {
		return bossViewMapper.getModifiedCustomerIds(queryMonth, beginTime,
				endTime);
	}

	public List<BossCustomer> getCustomers(List<Long> customerIds) {
		return bossViewMapper.getCustomers(customerIds);
	}

	public List<BossUser> getUsersByCustomerIds(List<Long> customerIds) {
		return bossViewMapper.getUsersByCustomerIds(customerIds);
	}

	public List<BossUserPack> getUserPacksByUserIds(List<Long> userIds) {
		return bossViewMapper.getUserPacksByUserIds(userIds);
	}

	public List<BossUserProduct> getUserProductsByUserIds(List<Long> userIds) {
		return bossViewMapper.getUserProductsByUserIds(userIds);
	}

	public List<BossWasuSubCode> getWasuSubCodesByUserIds(List<Long> userIds) {
		return bossViewMapper.getWasuSubCodesByUserIds(userIds);
	}
	
	public List<BossProduct> getProducts() {
		return bossViewMapper.getProductDefs();
	}
	
	public List<BossPack> getPacks() {
		return bossViewMapper.getPackDefs();
	}
	
	public List<BossPackdeal> getPackdeals() {
		return bossViewMapper.getPackdealDefs();
	}

}
