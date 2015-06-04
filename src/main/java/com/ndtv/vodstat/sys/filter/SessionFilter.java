package com.ndtv.vodstat.sys.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ndtv.vodstat.common.pagemodel.SessionInfo;
import com.ndtv.vodstat.common.util.ConfigUtil;


public class SessionFilter implements Filter {
	
	/**
	 * web.xml中配置放过的地址
	 */
	private String[] passUrls;
	
	/**
	 * web.xml中配置的登录页面
	 */
	private String loginPage;
	
	/**
	 * 没有权限的提示页面
	 */
	private String noAuthPage;	
	
	
//	文字编码
	private static final String ENCODING = "UTF-8";
	
	@Override
	public void init(FilterConfig config) throws ServletException {
		passUrls = config.getInitParameter("passUrls").split(",");
		loginPage = config.getInitParameter("loginPage");
		noAuthPage = config.getInitParameter("noAuthPage");
	}	

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

//		请求地址
		String refererURL = getReqURL(request, true);
		//得到当前访问路径
		String path = request.getServletPath();
		
		//放过配置的地址
		for (String p : passUrls) {
			if (path.matches(p)) {
				chain.doFilter(request, response);//放过访问地址
				return;
			}
		}
		
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ConfigUtil.getSessionInfoName());
		if (sessionInfo == null || sessionInfo.getId() == null || sessionInfo.getId() == 0) {// 如果没有登录或登录超时
			if(request.getMethod().toUpperCase().equals("GET")){
				request.setAttribute("refererURL", refererURL);
			}
			response.sendRedirect(request.getContextPath() +  loginPage);
			return;
		}
		chain.doFilter(request, response);

/*		if (!sessionInfo.getResourceList().contains(path)) {// 如果当前用户没有访问此资源的权限
			response.sendRedirect(request.getContextPath() + noAuthPage);
		} else {
			chain.doFilter(request, response);
		}
*/
		
	}
	
	@Override
	public void destroy() {
	}	
	

//	得到待过滤页面
	private String getReqURL(HttpServletRequest req, boolean encodes) {
		String reqURL = "";
		String param = "";
		
		reqURL = req.getRequestURI();
		
		Map paramMap = req.getParameterMap();
		
		Object[] params;
		for(Object paramName : paramMap.keySet()) {
			
			params = (Object[])paramMap.get(paramName);
			
			for(Object item : params) {
				if(!param.equals("")) {
					param += "&";
				}
				
				//需要编码
				if(encodes) {
					try {
						param += paramName.toString() + "=" + URLEncoder.encode(item.toString(), ENCODING);
					} catch (UnsupportedEncodingException e) {
						param += paramName.toString() + "=" + item.toString();
					}
				} else {
					param += paramName.toString() + "=" + item.toString();
				}
			}
		}
		
		if (!param.equals(""))
			reqURL += "?" + param;
		return reqURL;
	}

}
