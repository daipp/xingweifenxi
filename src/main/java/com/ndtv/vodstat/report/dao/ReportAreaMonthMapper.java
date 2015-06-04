package com.ndtv.vodstat.report.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.ndtv.vodstat.report.model.AreaCondition;
import com.ndtv.vodstat.report.model.AreaMonthReport;

public interface ReportAreaMonthMapper extends MybatisSuperMapper {
	
	public List<AreaMonthReport> findAreaMonthReport(AreaCondition v);
	public List<AreaMonthReport> findAreaMonthReport(AreaCondition v,RowBounds rowBounds);

	//提取日报表数据
	public List<AreaMonthReport> getAreaMonthReport(Date repDate);
	public void deleteAreaMonthReport(Date repDate);
	
	///////////////////以下生成日报表:
	//月报初始化
	public void insertAreaMonthReport(Date repDate);
	
	//更新客户数
	public void update_Customers(@Param("repDate")Date repDate,@Param("repDate2")Date repDate2,@Param("reportRange")String reportRange);
	//更新用户类型
	public void update_UserTypes(@Param("repDate")Date repDate,@Param("repDate2")Date repDate2,@Param("reportRange")String reportRange,@Param("columnName") String columnName);
	//更新用户类型在线
	public void update_UserTypeBooked(@Param("repDate")Date repDate, @Param("repDate1")Date repDate1,@Param("repDate2")Date repDate2,@Param("reportRange")String reportRange,@Param("userType")String userType);
	//更新离线用户类型数量(月末这一天)
	public void update_UserTypeUnbook(@Param("repDate")Date repDate,@Param("repDate2")Date repDate2,@Param("reportRange")String reportRange,@Param("userType")String userType);
	//更新净增用户类型数量
	public void update_NewUserTypes(@Param("repDateLast")Date repDateLast,@Param("repDate")Date repDate,@Param("repDate2")Date repDate2,@Param("reportRange")String reportRange,@Param("userType")String userType);
	//更新净增在线用户类型数量
	public void update_NewUserTypeBooked(@Param("repDateLast")Date repDateLast,@Param("repDate")Date repDate,@Param("repDate2")Date repDate2,@Param("reportRange")String reportRange,@Param("userType")String userType);

	///////////////////以下查询清单：
	public List getCustomers(AreaCondition v);
	public List getCustomers(AreaCondition v, RowBounds rowBounds);
	public List getUserTypes(AreaCondition v);
	public List getUserTypes(AreaCondition v, RowBounds rowBounds);
	public List getUserTypeBooked(AreaCondition v);
	public List getUserTypeBooked(AreaCondition v, RowBounds rowBounds);
	public List getUserTypeUnbook(AreaCondition v);
	public List getUserTypeUnbook(AreaCondition v, RowBounds rowBounds);
	public List getNewUserTypes(AreaCondition v);
	public List getNewUserTypes(AreaCondition v, RowBounds rowBounds);
	public List getNewUserTypeBooked(AreaCondition v);
	public List getNewUserTypeBooked(AreaCondition v, RowBounds rowBounds);
	
	
	//更新:活跃用户 (月)
	public void update_ActiveUsers(String repDateMonthDesc);
	//更新:登录用户(订购)
	public void update_OnlineBooked(@Param("repDate")Date repDate,@Param("repDateMonthDesc")String repDateMonthDesc);
	//更新:登录用户(未订)
	public void update_OnlineUnbook(@Param("repDate")Date repDate,@Param("repDateMonthDesc")String repDateMonthDesc);
	//更新:未登录用户(订购)
	public void update_OfflineBooked(@Param("repDate")Date repDate,@Param("repDateMonthDesc")String repDateMonthDesc);
	//更新:未登录用户(未订)
	public void update_OfflineUnbook(@Param("repDate")Date repDate,@Param("repDateMonthDesc")String repDateMonthDesc);
	//更新:非活跃用户(登录过)
	public void update_InactiveOnline(@Param("repDate")Date repDate,@Param("repDateMonthDesc")String repDateMonthDesc);
	//更新:非活跃用户(未登录过)
	public void update_InactiveOffline(@Param("repDate")Date repDate,@Param("repDateMonthDesc")String repDateMonthDesc);

	///////////////////以下查询清单：
	public List getActiveUsers(AreaCondition v);
	public List getActiveUsers(AreaCondition v, RowBounds rowBounds);
	public List getOnlineBooked(AreaCondition v);
	public List getOnlineBooked(AreaCondition v, RowBounds rowBounds);
	public List getOnlineUnbook(AreaCondition v);
	public List getOnlineUnbook(AreaCondition v, RowBounds rowBounds);
	public List getOfflineBooked(AreaCondition v);
	public List getOfflineBooked(AreaCondition v, RowBounds rowBounds);
	public List getOfflineUnbook(AreaCondition v);
	public List getOfflineUnbook(AreaCondition v, RowBounds rowBounds);
	public List getInactiveOnline(AreaCondition v);
	public List getInactiveOnline(AreaCondition v, RowBounds rowBounds);
	public List getInactiveOffline(AreaCondition v);
	public List getInactiveOffline(AreaCondition v, RowBounds rowBounds);
	
	
	//更新用户类型到期 
	public void update_UserTypeExpiring(@Param("repDate")Date repDate, @Param("repDate1")Date repDate1,@Param("repDate2")Date repDate2,@Param("reportRange")String reportRange,@Param("userType")String userType);
	//更新用户类型到期续费的
	public void update_UserTypeExpiringBook(@Param("repDate")Date repDate, @Param("repDate1")Date repDate1,@Param("repDate2")Date repDate2,@Param("reportRange")String reportRange,@Param("userType")String userType);
	//更新离线或未订购续费用户(激活数)
	public void update_UserTypeExpiredBook(@Param("repDate")Date repDate, @Param("repDate1")Date repDate1,@Param("repDate2")Date repDate2,@Param("reportRange")String reportRange,@Param("userType")String userType);
	//更新:提前订购的
	public void update_UserTypePreBook(@Param("repDate")Date repDate, @Param("repDate1")Date repDate1,@Param("repDate2")Date repDate2,@Param("reportRange")String reportRange,@Param("userType")String userType);

	///////////////////以下查询清单：
	public List getUserTypeExpiring(AreaCondition v);
	public List getUserTypeExpiring(AreaCondition v, RowBounds rowBounds);
	public List getUserTypeExpiringBook(AreaCondition v);
	public List getUserTypeExpiringBook(AreaCondition v, RowBounds rowBounds);
	public List getUserTypeExpiredBook(AreaCondition v);
	public List getUserTypeExpiredBook(AreaCondition v, RowBounds rowBounds);
	public List getUserTypePreBook(AreaCondition v);
	public List getUserTypePreBook(AreaCondition v, RowBounds rowBounds);
	
}
