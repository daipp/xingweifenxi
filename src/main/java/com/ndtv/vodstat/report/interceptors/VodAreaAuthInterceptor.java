package com.ndtv.vodstat.report.interceptors;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 权限拦截器
 */
public class VodAreaAuthInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = Logger.getLogger(VodAreaAuthInterceptor.class);

	/**
	 * 在调用controller具体方法前拦截
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String url = requestUri.substring(contextPath.length());
		if(url.indexOf("/reportvodDo")<0){
			return true;
		}
		logger.info(requestUri);
		logger.info(contextPath);
		logger.info(url);
		
		
		HandlerMethod hm = (HandlerMethod)handler;
		logger.info(hm.getBeanType());
		logger.info(hm.getMethod().getName());
		
		Set<String> names = request.getParameterMap().keySet();
		for(String n : names){
			logger.info(n+":"+request.getParameter(n));
		}
		
		logger.info(handler.getClass().getName());
		
		for (MethodParameter meter : hm.getMethodParameters()) {
			logger.info(meter.getParameterType().getSimpleName()+" "+meter.getParameterName()+","); 
	        //怎么获取参数值？获取username 和password  
	    } 

		return true;
	}
}
