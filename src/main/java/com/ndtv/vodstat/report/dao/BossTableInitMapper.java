package com.ndtv.vodstat.report.dao;

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

public interface BossTableInitMapper extends MybatisSuperMapper {

	public void batchDeleteBossSysCode();
	public void batchInsertBossSysCode(List<BossSysCode> list);

	public void batchDeleteBossSysCodeRelation();
	public void batchInsertBossSysCodeRelation(List<BossSysCodeRelation> list);


	public void batchDeleteBossProduct();
	public void batchInsertBossProduct(List<BossProduct> list);

	public void batchDeleteBossPack();
	public void batchInsertBossPack(List<BossPack> list);

	public void batchDeleteBossPackdeal();
	public void batchInsertBossPackdeal(List<BossPackdeal> list);
	
	
	public void batchDeleteBossCustomer(List<Long> list);
	public void batchInsertBossCustomer(List<BossCustomer> list);

	public void batchDeleteBossUser(List<Long> list);
	public void batchInsertBossUser(List<BossUser> list);

	public void batchDeleteBossUserPack(List<Long> list);
	public void batchInsertBossUserPack(List<BossUserPack> list);

	public void batchDeleteBossUserProduct(List<Long> list);
	public void batchInsertBossUserProduct(List<BossUserProduct> list);

	public void batchDeleteBossWasuSubCode(List<Long> list);
	public void batchInsertBossWasuSubCode(List<BossWasuSubCode> list);
	
}
