package com.voucher.manage.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import javax.servlet.Filter;
public class IsLoginFilter implements Filter{
	 public FilterConfig config;
	    
	    public void destroy() {
	        this.config = null;
	    }
	    
	    public static boolean isContains(String container, String[] regx) {
	        boolean result = false;

	        for (int i = 0; i < regx.length; i++) {
	            if (container.indexOf(regx[i]) != -1) {
	                return true;
	            }
	        }
	        return result;
	    }

	    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	        HttpServletRequest hrequest = (HttpServletRequest)request;
	        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
	        HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper(httpServletResponse);
	        
	        String loginStrings = config.getInitParameter("loginStrings");        
	        String includeStrings = config.getInitParameter("includeStrings");    
	        String redirectPath = hrequest.getContextPath() + config.getInitParameter("redirectPath");
	        String disabletestfilter = config.getInitParameter("disabletestfilter");
	        

	        String[] loginList = loginStrings.split(";");
	        String[] includeList = includeStrings.split(";");
	        if (hrequest.getMethod().equals("OPTIONS")) {
	        	httpServletResponse.setContentType("application/json;charset=utf-8");
	            httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");

	            //httpServletResponse.setHeader("Access-Control-Allow-Headers", "Content-Type,content-type,Content-Length, Authorization, Accept,X-Requested-With,X-Token");
	            httpServletResponse.setHeader("Access-Control-Allow-Headers", "*");
	            //
	            httpServletResponse.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");

	            httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
	        	httpServletResponse.setStatus(200);
	            return;
	        }
	        if (!IsLoginFilter.isContains(hrequest.getRequestURI(), includeList)) {
	            chain.doFilter(request, response);
	            return;
	        }
	        
	        if (IsLoginFilter.isContains(hrequest.getRequestURI(), loginList)) {//
	            chain.doFilter(request, response);
	            return;
	        }
	        
	        String user = ( String ) hrequest.getSession().getAttribute("campusAdmin");
	        if (user == null) {
	            wrapper.sendRedirect(redirectPath);
	            return;
	        }else {
	            chain.doFilter(request, response);
	            return;
	        }
	    }

	    public void init(FilterConfig filterConfig) throws ServletException {
	        config = filterConfig;
	    }

}

