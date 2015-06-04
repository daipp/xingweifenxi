package com.ndtv.vodstat.report.aop.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Service;

import com.ndtv.vodstat.common.util.ConfigUtil;
import com.ndtv.vodstat.report.aop.VodAreaAuthAop;
import com.ndtv.vodstat.report.model.BossSysCode;
import com.ndtv.vodstat.report.model.VodAreaCondition;
import com.ndtv.vodstat.report.service.BossService;

@Service
public class VodAreaAuthAopImpl implements VodAreaAuthAop{

	private Log log = LogFactory.getLog(VodAreaAuthAopImpl.class);

	@Resource(name="bossServiceImpl")
	private BossService bossService;
	
	//@Resource
	//BossSysCodeMapper sysCodeMapper;
	
    
    /**
     * 环绕通知
     * @param pjp
     * @return
     * @throws Throwable
     */
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
    	log.info("===========enter around advice=========== ");
        // 调用目标方法之前执行的动作
    	log.info("around advice: before invoking...");

    	printInvoke(pjp);
        
    	String className = pjp.getTarget().getClass().getSimpleName();
    	String methodName = pjp.getSignature().getName();
    	
    	if(className.equals("ReportRefeeServiceImpl")
    	|| className.equals("ReportVodServiceImpl")
    	|| className.equals("BossCustomerUserServiceImpl")){
    		if(methodName.indexOf("find")==0 || methodName.indexOf("get")==0){
    			 // 调用方法的参数
    	        if(pjp.getArgs() != null && pjp.getArgs().length>0){
    	            for(Object obj : pjp.getArgs()){
    	            	if(obj != null){
    	            		if(obj.getClass().getSimpleName().equals("VodAreaCondition")){
    	            			injectAuthority((VodAreaCondition)obj);
    	            		}
    	            	}
    	            }
    	        }
    		}
    	}
    	
        // 执行完方法的返回值：调用proceed()方法，就会触发切入点方法执行
        Object result = pjp.proceed();

    	log.info("around advice: after invoking...");

    	log.info("===========exit around advice=========== ");
        return result;
    }
    
    long customerTypeId = ConfigUtil.getLong("BOSS_CODETYPE_CUSTOMERTYPE");
	long townId = ConfigUtil.getLong("BOSS_CODETYPE_TOWN");
	long communityId = ConfigUtil.getLong("BOSS_CODETYPE_COMMUNITY");
	long villageId = ConfigUtil.getLong("BOSS_CODETYPE_VILLAGE");
	
    private void injectAuthority(VodAreaCondition condition){
    	if(condition.getQueryUserId()<=0){
    		throw new RuntimeException("查询的系统操作员信息不足!");
    	}
    	
    	List<BossSysCode> customerTypes = bossService.getCodeListBySysUser(condition.getQueryUserId(), customerTypeId, 1);
		List<BossSysCode> towns = bossService.getCodeListBySysUser(condition.getQueryUserId(), townId, 1);
		List<BossSysCode> communitys = bossService.getCodeListBySysUser(condition.getQueryUserId(), communityId, 1);
		List<BossSysCode> villages = bossService.getCodeListBySysUser(condition.getQueryUserId(), villageId, 1);
		
		if(!customerTypes.isEmpty()){
			List tmpls = new ArrayList();
			long[] tmpa = new long[customerTypes.size()];
			for(int i =0;i<customerTypes.size();i++){
				BossSysCode b = customerTypes.get(i);
				tmpa[i] = b.getCodeId();
				tmpls.add(b.getCodeId());
			}
			
			if(condition.getCustomerTypeIds() == null || condition.getCustomerTypeIds().length == 0){
				condition.setCustomerTypeIds(tmpa);
			} else {
				List<Long> tmpIds = new ArrayList();
				for(long tmpid : condition.getCustomerTypeIds()) {
					if(tmpls.contains(tmpid)){
						tmpIds.add(tmpid);
					}
				}
				condition.setCustomerTypeIds(tmpIds.toArray(new Long[0]));
			}
		}

		if(!towns.isEmpty()){
			List tmpls = new ArrayList();
			long[] tmpa = new long[towns.size()];
			for(int i =0;i<towns.size();i++){
				BossSysCode b = towns.get(i);
				tmpa[i] = b.getCodeId();
				tmpls.add(b.getCodeId());
			}
			
			if(condition.getTownIds() == null || condition.getTownIds().length == 0){
				condition.setTownIds(tmpa);
			} else {
				List<Long> tmpIds = new ArrayList();
				for(long tmpid : condition.getTownIds()) {
					if(tmpls.contains(tmpid)){
						tmpIds.add(tmpid);
					}
				}
				condition.setTownIds(tmpIds.toArray(new Long[0]));
			}
		}

		if(!communitys.isEmpty()){
			List tmpls = new ArrayList();
			long[] tmpa = new long[communitys.size()];
			for(int i =0;i<communitys.size();i++){
				BossSysCode b = communitys.get(i);
				tmpa[i] = b.getCodeId();
				tmpls.add(b.getCodeId());
			}
			
			if(condition.getCommunityIds() == null || condition.getCommunityIds().length == 0){
				condition.setCommunityIds(tmpa);
			} else {
				List<Long> tmpIds = new ArrayList();
				for(long tmpid : condition.getCommunityIds()) {
					if(tmpls.contains(tmpid)){
						tmpIds.add(tmpid);
					}
				}
				condition.setCommunityIds(tmpIds.toArray(new Long[0]));
			}
		}

		if(!villages.isEmpty()){
			List tmpls = new ArrayList();
			long[] tmpa = new long[villages.size()];
			for(int i =0;i<villages.size();i++){
				BossSysCode b = villages.get(i);
				tmpa[i] = b.getCodeId();
				tmpls.add(b.getCodeId());
			}
			
			if(condition.getVillageIds() == null || condition.getVillageIds().length == 0){
				condition.setVillageIds(tmpa);
			} else {
				List<Long> tmpIds = new ArrayList();
				for(long tmpid : condition.getVillageIds()) {
					if(tmpls.contains(tmpid)){
						tmpIds.add(tmpid);
					}
				}
				condition.setVillageIds(tmpIds.toArray(new Long[0]));
			}
		}
		
		log.debug("区域性权限注入!");
		
    }
    
    private void printInvoke(JoinPoint jp){
    	// 获取目标对象
        String str = jp.getTarget().getClass().getSimpleName() +"";
        // 调用的方法名
        str += "." + jp.getSignature().getName() + "(";
        // 调用方法的参数
        if(jp.getArgs() != null && jp.getArgs().length>0){
            for(Object obj : jp.getArgs()){
            	if(obj != null){
            		str += obj.getClass().getSimpleName()+",";
            	}
            }
            str = str.substring(0, str.length()-1)+")";
        } else {
        	str += ")";
        }
        log.info(str);
    }
    
	 /**
    * 前置通知
    * @param jp
    */
   public void doBefore(JoinPoint jp) {
       log.info("===========enter before advice============");
       printInvoke(jp);
   }
   
    /**
     * 后置通知
     * @param jp 连接点
     * @param result 返回值
     */
    public void doAfterReturning(JoinPoint jp, Object result) {
    	log.info("==========enter after-returning advice===========");
    	printInvoke(jp);
    }
    
    /**
     * 异常通知
     * @param jp
     * @param e
     */
    public void doAfterThrowing(JoinPoint jp, Throwable e) {
    	log.info("===========enter after-throwing advice=========== ");
    	printInvoke(jp);
    	log.error(jp.getClass().getSimpleName(), e);
    }

}
