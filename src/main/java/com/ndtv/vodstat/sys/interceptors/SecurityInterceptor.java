package com.ndtv.vodstat.sys.interceptors;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ndtv.vodstat.common.pagemodel.SessionInfo;
import com.ndtv.vodstat.common.util.ConfigUtil;

/**
 * 权限拦截器
 */
public class SecurityInterceptor implements HandlerInterceptor {

	private static final Logger logger = Logger.getLogger(SecurityInterceptor.class);

	private List<String> excludeUrls;// 不需要拦截的资源

	public List<String> getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(List<String> excludeUrls) {
		this.excludeUrls = excludeUrls;
	}

	/**
	 * 完成页面的render后调用
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception) throws Exception {

	}

	/**
	 * 在调用controller具体方法后拦截
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView) throws Exception {

	}

	/**
	 * 在调用controller具体方法前拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String url = requestUri.substring(contextPath.length());
		logger.info(url);

		if (url.indexOf("/baseController/") > -1 
		|| url.indexOf("/CallableController/") > -1 
		|| excludeUrls.contains(url)) {// 如果要访问的资源是不需要验证的
			return true;
		}

		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ConfigUtil.getSessionInfoName());
		if (sessionInfo == null || sessionInfo.getId() == null || sessionInfo.getId() == 0) {// 如果没有登录或登录超时
			request.setAttribute("msg", "您还没有登录或登录已超时，请重新登录，然后再刷新本功能！");
			request.getRequestDispatcher("/error/noSession.jsp").forward(request, response);
			return false;
		}
		
		boolean legal = false;
		List<String> legalResources = sessionInfo.getResourceList();
		for(String str : legalResources){
			if(str.length()<1){
				legal = true;
				break;
			}
			if(str.equals(url)){
				legal = true;
				break;
			}
			//xxxxx/*这样的资源配置也可以
			if(str.substring(str.length()-1).equals("*")
			&& url.indexOf(str.substring(0, str.length()-1))==0){
				legal = true;
				break;
			}
		}

		if (!legal) {// 如果当前用户没有访问此资源的权限
			request.setAttribute("msg", "您没有访问此资源的权限！<br/>请联系超管赋予您<br/>[" + url + "]<br/>的资源访问权限！");
			request.getRequestDispatcher("/error/noauth.jsp").forward(request, response);
			return false;
		}

		return true;
	}
}
