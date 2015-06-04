package com.ndtv.vodstat.report.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.ndtv.vodstat.report.model.AreaCondition;
import com.ndtv.vodstat.report.model.AreaDateReport;
import com.ndtv.vodstat.report.model.VodAreaCondition;

//区域日报:
public interface ReportAreaDateMapper extends MybatisSuperMapper {
	
	public List<AreaDateReport> findAreaDateReport(AreaCondition v);
	public List<AreaDateReport> findAreaDateReport(AreaCondition v,RowBounds rowBounds);

	//提取日报表数据
	public List<AreaDateReport> getAreaDateReport(@Param("repDate")Date repDate, @Param("repYear")String repYear);
	public void deleteAreaDateReport(@Param("repDate")Date repDate,@Param("repYear")String repYear);
	

	//提取日报表数据
	public void insertAreaDateReportFinal(@Param("repDate")Date repDate,@Param("repYear")String repYear);
	public void deleteAreaDateReportTemp(Date repDate);
	
	///////////////////以下生成日报表:
	//日报初始化
	public void insertAreaDateReport(Date repDate);
	
	//客户数更新
	public void update_Customers(Date repDate);
	//用户类型栏更新
	public void update_UserTypes(@Param("repDate")Date repDate,@Param("columnName") String columnName);
	
	//用户类型(正常的)栏更新
	public void update_UserTypes0(@Param("repDate")Date repDate,@Param("columnName") String columnName);
	//高清机顶盒更新
	public void update_Hdstbs(Date repDate);
	//点播订购用户数量更新
	public void update_BookVODs(@Param("repDate")Date repDate,@Param("isNewUser") boolean isNewUser);
	//宽带订购用户数量更新
	public void update_BookBBs(@Param("repDate")Date repDate,@Param("isNewUser") boolean isNewUser);
	//付费频道订购用户数量更新
	public void update_BookDVBs(@Param("repDate")Date repDate,@Param("isNewUser") boolean isNewUser);
	//主终端状态变更数量更新
	public void update_HostStateChange(@Param("repDate")Date repDate,@Param("columnName") String columnName);
	//主终端状态数量更新
	public void update_HostState(@Param("repDate")Date repDate,@Param("columnName") String columnName);
	//正常主终端未缴视听维护费的数量
	public void update_HostUnpay(@Param("repDate")Date repDate,@Param("unpayYears") int unpayYears);
	
	//登录用户更新(订购的)
	public void update_OnlineBookedUsers(@Param("repDate")Date repDate,@Param("repDateMonthDesc")String repDateMonthDesc);
	//登录用户更新(未订购的)
	public void update_OnlineUnbookUsers(@Param("repDate")Date repDate,@Param("repDateMonthDesc")String repDateMonthDesc);
	//活跃用户更新	
	public void update_ActiveUsers(@Param("repDate")Date repDate,@Param("repDateMonthDesc")String repDateMonthDesc);
	
	
	///////////////////以下查询清单：

	public List getCustomers(AreaCondition v);
	public List getCustomers(AreaCondition v, RowBounds rowBounds);
	
	public List getUserTypes(AreaCondition v);
	public List getUserTypes(AreaCondition v, RowBounds rowBounds);

	public List getUserTypes0(AreaCondition v);
	public List getUserTypes0(AreaCondition v, RowBounds rowBounds);

	public List getHdstbs(AreaCondition v);
	public List getHdstbs(AreaCondition v, RowBounds rowBounds);

	public List getVodBooks(AreaCondition v);
	public List getVodBooks(AreaCondition v, RowBounds rowBounds);
	public List getVodBooksNew(AreaCondition v);
	public List getVodBooksNew(AreaCondition v, RowBounds rowBounds);

	public List getBbBooks(AreaCondition v);
	public List getBbBooks(AreaCondition v, RowBounds rowBounds);
	public List getBbBooksNew(AreaCondition v);
	public List getBbBooksNew(AreaCondition v, RowBounds rowBounds);

	public List getDvbBooks(AreaCondition v);
	public List getDvbBooks(AreaCondition v, RowBounds rowBounds);
	public List getDvbBooksNew(AreaCondition v);
	public List getDvbBooksNew(AreaCondition v, RowBounds rowBounds);

	public List getHostStateChange(AreaCondition v);
	public List getHostStateChange(AreaCondition v, RowBounds rowBounds);
	
	public List getHostState(AreaCondition v);
	public List getHostState(AreaCondition v, RowBounds rowBounds);
	
	public List getHostUnpay1(AreaCondition v);
	public List getHostUnpay1(AreaCondition v, RowBounds rowBounds);

	public List getHostUnpay2(AreaCondition v);
	public List getHostUnpay2(AreaCondition v, RowBounds rowBounds);
	
	public List getOnlineBookedUsers(AreaCondition v);
	public List getOnlineBookedUsers(AreaCondition v, RowBounds rowBounds);

	public List getOnlineUnbookUsers(AreaCondition v);
	public List getOnlineUnbookUsers(AreaCondition v, RowBounds rowBounds);
	
	public List getActiveUsers(AreaCondition v);
	public List getActiveUsers(AreaCondition v, RowBounds rowBounds);
	
	
}
