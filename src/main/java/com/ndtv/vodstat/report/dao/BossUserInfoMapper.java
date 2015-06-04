package com.ndtv.vodstat.report.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.ndtv.vodstat.report.model.BossUserInfo;
import com.ndtv.vodstat.report.model.VodAreaCondition;

public interface BossUserInfoMapper extends MybatisSuperMapper {
	

	public BossUserInfo getUser(@Param("userId")Long userId);
	
	//实时用户清单
	public List findVodAreaDetail(VodAreaCondition v);
	//实时用户清单的总数
	public Integer findVodAreaDetailCount(VodAreaCondition v);
	//按条件查询客户
	public List findConditionCustomer(VodAreaCondition v);
	public List findConditionCustomer(VodAreaCondition v, RowBounds rowBounds);
	//查询活跃次数统计的用户清单
	public List getActives(VodAreaCondition v);
	public List getActives(VodAreaCondition v, RowBounds rowBounds);

	
	public List getCustomers(VodAreaCondition v);
	public List getCustomers(VodAreaCondition v, RowBounds rowBounds);
	
	public List getDvbUsers(VodAreaCondition v);
	public List getDvbUsers(VodAreaCondition v, RowBounds rowBounds);

	public List getVodUsers(VodAreaCondition v);
	public List getVodUsers(VodAreaCondition v, RowBounds rowBounds);

	public List getBbUsers(VodAreaCondition v);
	public List getBbUsers(VodAreaCondition v, RowBounds rowBounds);

	public List getHdstbs(VodAreaCondition v);
	public List getHdstbs(VodAreaCondition v, RowBounds rowBounds);

	public List getBookUsers(VodAreaCondition v);
	public List getBookUsers(VodAreaCondition v, RowBounds rowBounds);

	public List getActiveUsers(VodAreaCondition v);
	public List getActiveUsers(VodAreaCondition v, RowBounds rowBounds);

	public List getOnlineBookedUsers(VodAreaCondition v);
	public List getOnlineBookedUsers(VodAreaCondition v, RowBounds rowBounds);

	public List getOnlineUnbookUsers(VodAreaCondition v);
	public List getOnlineUnbookUsers(VodAreaCondition v, RowBounds rowBounds);

	public List getOfflineBookedUsers(VodAreaCondition v);
	public List getOfflineBookedUsers(VodAreaCondition v, RowBounds rowBounds);

	public List getOfflineUnbookUsers(VodAreaCondition v);
	public List getOfflineUnbookUsers(VodAreaCondition v, RowBounds rowBounds);

	public List getInactiveOnlineUsers(VodAreaCondition v);
	public List getInactiveOnlineUsers(VodAreaCondition v, RowBounds rowBounds);

	public List getInactiveOfflineUsers(VodAreaCondition v);
	public List getInactiveOfflineUsers(VodAreaCondition v, RowBounds rowBounds);
	
	
	//客户用户情况-实时 清单
	public List getVodOpenNowUsers(VodAreaCondition v);
	public List getVodOpenNowUsers(VodAreaCondition v, RowBounds rowBounds);
	
	public List getVodOpenDelayUsers(VodAreaCondition v);
	public List getVodOpenDelayUsers(VodAreaCondition v, RowBounds rowBounds);
	
	public List getVodNotOpenUsers(VodAreaCondition v);
	public List getVodNotOpenUsers(VodAreaCondition v, RowBounds rowBounds);
	
	public List getBbOpenNowUsers(VodAreaCondition v);
	public List getBbOpenNowUsers(VodAreaCondition v, RowBounds rowBounds);
	
	public List getBbOpenDelayUsers(VodAreaCondition v);
	public List getBbOpenDelayUsers(VodAreaCondition v, RowBounds rowBounds);
	
	public List getBbNotOpenUsers(VodAreaCondition v);
	public List getBbNotOpenUsers(VodAreaCondition v, RowBounds rowBounds);
	
	
	//客户用户情况-月报季报年报 清单(只查在线离线清单)
	public List getOnlineVodUsers(VodAreaCondition v);
	public List getOnlineVodUsers(VodAreaCondition v, RowBounds rowBounds);
	
	public List getOnlineBbUsers(VodAreaCondition v);
	public List getOnlineBbUsers(VodAreaCondition v, RowBounds rowBounds);
	
	public List getOfflineVodUsers(VodAreaCondition v);
	public List getOfflineVodUsers(VodAreaCondition v, RowBounds rowBounds);
	
	public List getOfflineBbUsers(VodAreaCondition v);
	public List getOfflineBbUsers(VodAreaCondition v, RowBounds rowBounds);
	
	//到期续费实时统计
	public List getRefeeExpiredUsers(VodAreaCondition v);
	public List getRefeeExpiredUsers(VodAreaCondition v, RowBounds rowBounds);
	
	public List getRefeeBookUsers(VodAreaCondition v);
	public List getRefeeBookUsers(VodAreaCondition v, RowBounds rowBounds);
	
	
	
	public List getVodExpiringBook(VodAreaCondition v);
	public List getVodExpiringBook(VodAreaCondition v,RowBounds rowBounds);
	
	public List getVodExpiredBook(VodAreaCondition v);
	public List getVodExpiredBook(VodAreaCondition v,RowBounds rowBounds);
	
	public List getVodPreBook(VodAreaCondition v);
	public List getVodPreBook(VodAreaCondition v,RowBounds rowBounds);
	
	public List getBbExpiringBook(VodAreaCondition v);
	public List getBbExpiringBook(VodAreaCondition v,RowBounds rowBounds);
	
	public List getBbExpiredBook(VodAreaCondition v);
	public List getBbExpiredBook(VodAreaCondition v,RowBounds rowBounds);

	public List getBbPreBook(VodAreaCondition v);
	public List getBbPreBook(VodAreaCondition v,RowBounds rowBounds);
}

