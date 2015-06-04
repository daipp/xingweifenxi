package com.ndtv.vodstat.report.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.ndtv.vodstat.report.model.VodArea;
import com.ndtv.vodstat.report.model.VodAreaCondition;

public interface BossCustmoerUserReportMapper extends MybatisSuperMapper {
	
	public List<VodArea> findReportBOSSCustomerUser(VodAreaCondition v);
	public List<VodArea> findReportBOSSCustomerUser(VodAreaCondition v,RowBounds rowBounds);

	//提取日报表数据
	public List<VodArea> getReportBOSSCustomerUser(@Param("repDate")Date repDate,@Param("reportRange")String reportRange);
	public void deleteReportBOSSCustomerUser(@Param("repDate")Date repDate,@Param("reportRange")String reportRange);
	
	//以下生成日报表:
	public void insertReportBOSSCustomerUser(@Param("repDate")Date repDate,@Param("reportRange")String reportRange);
	
	//更新客户数量
	public void update_Customers(@Param("repDate")Date repDate,@Param("reportRange")String reportRange);

	//更新用户类型数量
	public void update_UserTypes(@Param("repDate")Date repDate,@Param("reportRange")String reportRange,@Param("userType")String userType);
	
	//更新在线用户类型数量
	public void update_OnlineUserTypes(@Param("repDate")Date repDate,@Param("reportRange")String reportRange,@Param("userType")String userType,
			@Param("repDate1")Date repDate1,@Param("repDate2")Date repDate2);

	//更新离线用户类型数量
	public void update_OfflineUserTypes(@Param("repDate")Date repDate,@Param("reportRange")String reportRange,@Param("userType")String userType);
	
	//更新净增用户类型数量
	public void update_NewUserTypes(@Param("repDateLast")Date repDateLast,@Param("repDate")Date repDate,@Param("reportRange")String reportRange,@Param("userType")String userType);
	
	//更新净增在线用户类型数量
	public void update_NewOnlineUserTypes(@Param("repDateLast")Date repDateLast,@Param("repDate")Date repDate,@Param("reportRange")String reportRange,@Param("userType")String userType);
	
}
