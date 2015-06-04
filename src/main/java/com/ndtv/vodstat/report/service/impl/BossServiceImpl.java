package com.ndtv.vodstat.report.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.ndtv.vodstat.boss.service.BossView;
import com.ndtv.vodstat.common.util.ConfigUtil;
import com.ndtv.vodstat.common.util.DateFunctions;
import com.ndtv.vodstat.report.dao.BossSysCodeMapper;
import com.ndtv.vodstat.report.dao.BossTableInitMapper;
import com.ndtv.vodstat.report.dao.SysCodeMapper;
import com.ndtv.vodstat.report.dao.TimerJobLogMapper;
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
import com.ndtv.vodstat.report.service.BossService;
import com.ndtv.vodstat.sys.entity.SysCode;
import com.ndtv.vodstat.sys.entity.TimerJobLog;


@Service
public class BossServiceImpl implements BossService{

	private Log log = LogFactory.getLog(BossServiceImpl.class);
	
	@Resource
	private SysCodeMapper sysCodeMapper;
	@Resource
	private TimerJobLogMapper timerJobLogMapper;
	
	@Resource
	private BossSysCodeMapper bossSysCodeMapper;
	
	@Resource
	private BossTableInitMapper bossTablesMapper;
	
	@Resource
	private BossView bossView;
	
	private static final long SYN_BOSS_TIME = ConfigUtil.getLong("syscode_sysConfig_SYNBOSS_TIME");

	public BossSysCode getBossSysCode(Long codeId,Long typeId,String codeName) {
		if((codeId!=null && codeId>0)
		|| (codeName!=null && typeId!=null && typeId>0)){
			return bossSysCodeMapper.getBossSysCode(codeId,typeId,codeName);
		} else {
			return null;
		}
	}

	public List<BossSysCode> getCodeList(Long typeId, Integer state) {
		return bossSysCodeMapper.getCodeList(typeId, state);
	}

	public List<BossSysCode> getCodeListByRelation(Long codeId, Long typeId,
			Integer state) {
		return bossSysCodeMapper.getCodeListByRelation(codeId, typeId, state);
	}

	public List<BossSysCode> getCodeListBySysUser(Long userId, Long typeId,
			Integer state) {
		return bossSysCodeMapper.getCodeListBySysUser(userId, typeId, state);
	}

	public List<BossSysCode> getCodeListBySysUserEx(Long userId, Long typeId,
			Integer state) {
		return bossSysCodeMapper.getCodeListBySysUserEx(userId, typeId, state);
	}
	
	public void mergeSysUserBossSysCode(Long userId,Long[] typeIds,Long[] codeIds){
		for(long typeId : typeIds) {
			bossSysCodeMapper.cleanSysUserBossSysCode(userId, typeId);
		}
		bossSysCodeMapper.addSysUserBossSysCode(userId,Arrays.asList(codeIds));
	}

	
//	以下是同步BOSS表数据================================
	
	synchronized public void synBossTables(){
		
		log.info("同步BOSS数据...");
		SysCode sc = sysCodeMapper.get(SYN_BOSS_TIME);
		log.info("查询上一次同步时间:"+sc.getCodeContent());
		SimpleDateFormat timeDF = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat monthDF = new SimpleDateFormat("yyyyMM");
		Date d1 = null;
		try {
			d1 = timeDF.parse(sc.getCodeContent());
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException("时间配置错误");
		}
		Date d2 = new Date();
		String m1 = monthDF.format(d1);
		String m2 = monthDF.format(d2);
		List<Long> customerIdsTmp = new ArrayList();
		Date d00 = new Date();
		while(!m1.equals(m2)){
			customerIdsTmp.addAll(bossView.getModifiedCustomerIds(m1, d1, DateFunctions.getMonthLastDay(d1)));
			d1 = DateFunctions.addCalendarFeild(d1, Calendar.MONTH, 1);
			d1 = DateFunctions.getMonthFirstDay(d1);
			m1 = monthDF.format(d1);
		}
		customerIdsTmp.addAll(bossView.getModifiedCustomerIds(m2, d1, d2));
		
		List<Long> customerIds = new ArrayList(new HashSet(customerIdsTmp));
		log.info("查询修改过的客户编号"+customerIds.size());

		List<Long> userIds = new ArrayList();
		List<BossCustomer> customers = new ArrayList();
		List<BossUser> users = new ArrayList();
		List<BossUserPack> userPacks = new ArrayList();
		List<BossUserProduct> userProducts = new ArrayList();
		List<BossWasuSubCode> bossWasuSubCodes = new ArrayList();
		
		int customerIdSize = customerIds.size();
		if(!customerIds.isEmpty()){
			if(customerIds.size()<=1000){
				customers = bossView.getCustomers(customerIds);
				log.info("查询客户VIEW:" + customers.size());
				users = bossView.getUsersByCustomerIds(customerIds);
				log.info("查询用户VIEW:" + users.size());
			} else { //解决oracle in 条件1000个的限制
				List<Long> subList;
				int index = 0, size = customerIds.size();
				while(index < size) {
					subList = customerIds.subList(index, (index + 999 >= size ? size : index + 999));
					index += 999;
					customers.addAll(bossView.getCustomers(subList));
					users.addAll(bossView.getUsersByCustomerIds(subList));
				}
				log.info("查询客户VIEW:" + customers.size());
				log.info("查询用户VIEW:" + users.size());
			}
		}
		for(BossUser b : users){
			userIds.add(b.getUserid());
		}
		int userIdSize = userIds.size();
		if(!userIds.isEmpty()){
			if(userIds.size()<=1000){
				userPacks = bossView.getUserPacksByUserIds(userIds);
				log.info("查询用户产品包VIEW:" + userPacks.size());
				userProducts = bossView.getUserProductsByUserIds(userIds);
				log.info("查询用户产品VIEW:" + userProducts.size());
				bossWasuSubCodes = bossView.getWasuSubCodesByUserIds(userIds);
				log.info("查询用户华数账号VIEW:" + bossWasuSubCodes.size());
			} else { //解决oracle in 条件1000个的限制
				List<Long> subList;
				int index = 0, size = userIds.size();
				while(index < size) {
					subList = userIds.subList(index, (index + 999 >= size ? size : index + 999));
					index += 999;
					userPacks.addAll(bossView.getUserPacksByUserIds(subList));
					userProducts.addAll(bossView.getUserProductsByUserIds(subList));
					bossWasuSubCodes.addAll(bossView.getWasuSubCodesByUserIds(subList));
				}
				log.info("查询用户产品包VIEW:" + userPacks.size());
				log.info("查询用户产品VIEW:" + userProducts.size());
				log.info("查询用户华数账号VIEW:" + bossWasuSubCodes.size());
			}
		}
		
		List<BossSysCode> sysCodes = bossView.getSysCodes();
		log.info("查询系统代码VIEW:" + sysCodes.size());
		List<BossSysCodeRelation> sysCodeRelations = bossView.getSysCodeRelations();
		log.info("查询系统代码关联VIEW:" + sysCodeRelations.size());
		
		List<BossProduct> productDefs = bossView.getProducts();
		log.info("查询产品定义VIEW:" + productDefs.size());
		List<BossPack> packDefs = bossView.getPacks();
		log.info("查询产品包定义VIEW:" + packDefs.size());
		List<BossPackdeal> packdealDefs = bossView.getPackdeals();
		log.info("查询套餐定义VIEW:" + packdealDefs.size());
		
		//同步至本地表格:
		log.info("同步至本地BOSS表格:");
		log.info("syn table BossSysCode...");
		synBossSysCode(sysCodes);
		log.info("syn table BossSysCodeRelation...");
		synBossSysCodeRelation(sysCodeRelations);
		
		log.info("syn table BossProduct...");
		synBossProduct(productDefs);
		log.info("syn table BossPack...");
		synBossPack(packDefs);
		log.info("syn table BossPackdeal...");
		synBossPackdeal(packdealDefs);
		
		log.info("syn table BossCustomer...");
		synBossCustomer(customerIds, customers);
		log.info("syn table BossUser...");
		synBossUser(userIds, users);
		log.info("syn table BossUserPack...");
		synBossUserPack(userIds, userPacks);
		log.info("syn table BossUserProduct...");
		synBossUserProduct(userIds, userProducts);
		log.info("syn table BossWasuSubCode...");
		synBossWasuSubCode(userIds, bossWasuSubCodes);
		log.info("同步至本地BOSS表格结束!");


		TimerJobLog logInfo = new TimerJobLog(SYN_BOSS_TIME, "BOSS数据同步", new Date(),
				(new Date().getTime()-d00.getTime()), "同步客户数:"+customerIdSize+",同步用户数:"+userIdSize);
		timerJobLogMapper.insert(logInfo);
		
		//更新标记
		SysCode newCode = new SysCode();
		newCode.setId(SYN_BOSS_TIME);
		newCode.setUpdateTime(new Date());
		newCode.setCodeContent(timeDF.format(DateFunctions.dateTruncate(d2)));
		newCode.setUpdateUserId(1l);
		sysCodeMapper.update(newCode);
		
		log.info("同步BOSS数据结束!");
	}
	

	private void synBossSysCode(List<BossSysCode> codes) {
		bossTablesMapper.batchDeleteBossSysCode();
		bossTablesMapper.batchInsertBossSysCode(codes);
	}

	private void synBossSysCodeRelation(List<BossSysCodeRelation> codes) {
		bossTablesMapper.batchDeleteBossSysCodeRelation();
		bossTablesMapper.batchInsertBossSysCodeRelation(codes);
	}
	
	private void synBossProduct(List<BossProduct> list) {
		bossTablesMapper.batchDeleteBossProduct();
		bossTablesMapper.batchInsertBossProduct(list);
	}
	
	private void synBossPack(List<BossPack> list) {
		bossTablesMapper.batchDeleteBossPack();
		bossTablesMapper.batchInsertBossPack(list);
	}
	
	private void synBossPackdeal(List<BossPackdeal> list) {
		bossTablesMapper.batchDeleteBossPackdeal();
		bossTablesMapper.batchInsertBossPackdeal(list);
	}

	private void synBossCustomer(List<Long> customerIds,List<BossCustomer> customers) {
		if(!customerIds.isEmpty()){
			bossTablesMapper.batchDeleteBossCustomer(customerIds);
		}
		if(!customers.isEmpty()){
			bossTablesMapper.batchInsertBossCustomer(customers);
		}
	}

	private void synBossUser(List<Long> userIds,List<BossUser> users) {
		if(!userIds.isEmpty()){
			bossTablesMapper.batchDeleteBossUser(userIds);
		}
		if(!users.isEmpty()){
			bossTablesMapper.batchInsertBossUser(users);
		}
	}

	private void synBossUserPack(List<Long> userIds,List<BossUserPack> userPacks) {
		if(!userIds.isEmpty()){
			bossTablesMapper.batchDeleteBossUserPack(userIds);
		}
		if(!userPacks.isEmpty()){
			bossTablesMapper.batchInsertBossUserPack(userPacks);
		}
	}

	private void synBossUserProduct(List<Long> userIds,List<BossUserProduct> userProducts) {
		if(!userIds.isEmpty()){
			bossTablesMapper.batchDeleteBossUserProduct(userIds);
		}
		if(!userProducts.isEmpty()){
			bossTablesMapper.batchInsertBossUserProduct(userProducts);
		}
	}

	private void synBossWasuSubCode(List<Long> userIds,List<BossWasuSubCode> bossWasuSubCodes) {
		if(!userIds.isEmpty()){
			bossTablesMapper.batchDeleteBossWasuSubCode(userIds);
		}
		if(!bossWasuSubCodes.isEmpty()){
			bossTablesMapper.batchInsertBossWasuSubCode(bossWasuSubCodes);
		}
	}

}
