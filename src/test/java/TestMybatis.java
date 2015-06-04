import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.ndtv.vodstat.boss.service.BossView;
import com.ndtv.vodstat.report.model.BossCustomer;
import com.ndtv.vodstat.report.service.IReportVodService;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:spring.xml","classpath:spring-datasource.xml", "classpath:spring-mybatis.xml", "classpath:spring-hibernate.xml" })
public class TestMybatis {

	private static final Logger logger = Logger.getLogger(TestMybatis.class);

	private IReportVodService reportService;

	public IReportVodService getReportService() {
		return reportService;
	}
	//@Autowired
	public void setReportService(IReportVodService reportService) {
		this.reportService = reportService;
	}
	
	private BossView bv;
	public BossView getBv() {
		return bv;
	}
	//@Autowired
	public void setBv(BossView bv) {
		this.bv = bv;
	}
	
	//@Test
	public void test1() {
//		BossCustomerView bc =bv.getCustomer(123l);
//		logger.info(JSON.toJSON(bc));
		//BossUserInfo u = reportService.getUser(8800008658l);
		//logger.info(JSON.toJSONStringWithDateFormat(u, "yyyy-MM-dd HH:mm:ss"));
	}

	//@Test
	public void test2() {
		BossCustomer bcv = bv.getCustomer(123l);
		logger.info(JSON.toJSONStringWithDateFormat(bcv, "yyyy-MM-dd HH:mm:ss"));
	}

/*	@Test
	public void test3() {
		List<User> l = userService.getAll2();
		logger.info(JSON.toJSONStringWithDateFormat(l, "yyyy-MM-dd HH:mm:ss"));
	}

	@Test
	public void test4() {
		List<User> l = userService.getAll3();
		logger.info(JSON.toJSONStringWithDateFormat(l, "yyyy-MM-dd HH:mm:ss"));
	}
*/	
}
