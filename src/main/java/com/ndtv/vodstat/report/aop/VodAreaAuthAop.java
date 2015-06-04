package com.ndtv.vodstat.report.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

public interface VodAreaAuthAop {

    public Object doAround(ProceedingJoinPoint pjp) throws Throwable;

	public void doBefore(JoinPoint jp);
   
	public void doAfterReturning(JoinPoint jp, Object result);
    
    public void doAfterThrowing(JoinPoint jp, Throwable e);

}
