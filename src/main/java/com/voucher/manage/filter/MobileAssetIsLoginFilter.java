package com.voucher.manage.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.voucher.manage.dao.AssetsDAO;
import com.voucher.manage.dao.HiddenDAO;
import com.voucher.manage.daoModel.TTT.ChartInfo;
import com.voucher.manage.mapper.UsersMapper;
import com.voucher.manage.mapper.WeiXinMapper;
import com.voucher.manage.model.Users;
import com.voucher.manage.model.WeiXin;
import com.voucher.manage.service.UserService;
import com.voucher.manage.service.WeiXinService;
import com.voucher.manage.tools.MyTestUtil;
import com.voucher.sqlserver.context.Connect;
import com.voucher.weixin.util.SpringUtil;

public class MobileAssetIsLoginFilter implements Filter{
	
	ApplicationContext applicationContext=new Connect().get();
	
	AssetsDAO assetsDAO=(AssetsDAO) applicationContext.getBean("assetsdao");
	
	private UsersMapper usersMapper;
	private WeiXinService weixinService = (WeiXinService)SpringUtil.getBean("weixinService");
	
	 public FilterConfig configAsset=null;
	    @Override  
	    public void destroy() {   
	  
	    }   
	  
	    @Override  
	    public void doFilter(ServletRequest request, ServletResponse response,   
	            FilterChain chain) throws IOException, ServletException {   
	    	System.out.println("dofilter");
	    	  HttpServletRequest hrequest = (HttpServletRequest)request;
		        HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper((HttpServletResponse) response);
		        
		        String loginStrings = configAsset.getInitParameter("loginStrings");        
		        String includeStrings = configAsset.getInitParameter("includeStrings");    
		        String redirectPath = hrequest.getContextPath() + configAsset.getInitParameter("redirectPath");
		        String mobileLoginPath = hrequest.getContextPath() + configAsset.getInitParameter("mobileLoginPath");
		        String settingPath = hrequest.getContextPath() + configAsset.getInitParameter("settingPath");
		        String disabletestfilter = configAsset.getInitParameter("disabletestfilter");
		        

		        String[] loginList = loginStrings.split(";");
		        String[] includeList = includeStrings.split(";");
		        
		        if (!IsLoginFilter.isContains(hrequest.getRequestURI(), includeList)) {
		            chain.doFilter(request, response);
		            return;
		        }
		        
		        if (IsLoginFilter.isContains(hrequest.getRequestURI(), loginList)) {//
		            chain.doFilter(request, response);
		            return;
		        }
		        
		        String openId=( String ) hrequest.getSession().getAttribute("openId");
		        
		        HttpServletRequest httpRequest = (HttpServletRequest) request;
				WebApplicationContext wac = WebApplicationContextUtils    //controller浠ュ鐨勫寘鍒濆鍖栫被
						.getRequiredWebApplicationContext(httpRequest.getSession()
								.getServletContext());
		        
				usersMapper=wac.getBean(UsersMapper.class);
				
		        if (openId==null) {       
		        	MyTestUtil.print(hrequest.getSession());
		        	System.out.println("MobileAssetIsLoginFilter openid= null");
		        	wrapper.sendRedirect(mobileLoginPath);
		            return;
		        }else {
		        	Users users=usersMapper.getUserByOnlyOpenId(openId);
		        	System.out.println("MobileAssetIsLoginFilter openId ="+openId);
		        	
		        	String Charter=users.getCharter();
		        	String phone=users.getHirePhone();
		        	
		        	Integer campusId = users.getCampusId();
		        	
		        	if(phone==null||phone.equals("")||Charter.equals("")) {
		        		wrapper.sendRedirect(settingPath+"?campusId="+campusId);
			            return;
		        	}else {
		        		
		        		try {
		        			Map searchMap=new HashMap<>();
		        			searchMap.put("ChartInfo.Phone = ", phone.trim());
		        			searchMap.put("ChartInfo.Charter like ","%"+Charter.trim()+"%");
		        			
		        			Map map=assetsDAO.getAllChartInfo(5, 0, null, null, searchMap);
		        			
		        			List list=(List) map.get("rows");
		        			
		        			ChartInfo chartInfo=(ChartInfo) list.get(0);
		        			
		        			/**
		        			 * 通过所关注微信公众号判断是否是当前合同公司对应公众号
		        			 		       			
		        			WeiXin campus = weixinService.getWeiXinByCampusId(campusId);
		        			String campusName = campus.getCampusName();
		        			String manageItem = "";
		        			Iterator<ChartInfo> iterator=list.iterator();
		        			boolean ischar=false;
		        			while (iterator.hasNext()) {
		        				chartInfo=iterator.next();
			        			String manageRegion = chartInfo.getManageRegion();
			        			if(campusName.equals("泸州市工业投资集团有限公司")) {
			        				searchMap.put("ChartInfo.ManageRegion like ", "%工投委托%");
			        				map=assetsDAO.getAllChartInfo(1, 0, null, null, searchMap);
			        				if(map!=null) {
			        					ischar=true;
			        					break;
			        				}
			        			}else if(campusName.equals("泸州国有资产经营有限公司")) {
			        				searchMap.put("ChartInfo.ManageRegion like ", "%国资委托%");
			        				map=assetsDAO.getAllChartInfo(1, 0, null, null, searchMap);
			        				if(map!=null) {
			        					ischar=true;
			        					break;
			        				}
			        				searchMap.put("ChartInfo.ManageRegion like ", "%火炬资产%");
			        				map=assetsDAO.getAllChartInfo(1, 0, null, null, searchMap);
			        				if(map!=null) {
			        					ischar=true;
			        					break;
			        				}
			        				searchMap.put("ChartInfo.ManageRegion like ", "%国资财委%");
			        				map=assetsDAO.getAllChartInfo(1, 0, null, null, searchMap);
			        				if(map!=null) {
			        					ischar=true;
			        					break;
			        				}
		
			        			}else if (campusName.equals("泸州市国华资产经营有限公司")) {
			        				searchMap.put("ChartInfo.ManageRegion like ", "%国华自有%");
			        				map=assetsDAO.getAllChartInfo(1, 0, null, null, searchMap);
			        				if(map!=null) {
			        					ischar=true;
			        					break;
			        				}
			        			}								
							}
		        			if(!ischar) {
		        				if(campusId==1) {
		        					
		        				}else if(campusId==2){
		        					redirectPath=hrequest.getContextPath()+"/mobile/asset/restrict_user2.html?campusId="+campusId;
		        					
								}else if(campusId==3){
		        					redirectPath=hrequest.getContextPath()+"/mobile/asset/restrict_user3.html?campusId="+campusId;
		        					
								}
		        				wrapper.sendRedirect(redirectPath);
		        				return;
		        			}
		        			/**
		        			 * 
		        			 */
			        		
		        			final String REGEX = Charter.trim();

		        			Pattern pattern = Pattern.compile(REGEX);

		        			Matcher matcher = pattern.matcher(chartInfo.getCharter().trim());
		        			
		        			boolean isCharter=matcher.find();
		        			
			        		System.out.println("Charter="+Charter+"   chartInfo.getCharter()="+
			        				chartInfo.getCharter()+"   "+ isCharter);
			        		System.out.println("phone="+phone+"   chartInfo.getPhone="+
			        				chartInfo.getPhone()+"   "+phone.equals(chartInfo.getPhone().trim()));
			        					        		
		        			if(isCharter&&phone.trim().equals(chartInfo.getPhone().trim())){
		        				chain.doFilter(request, response);
		        			}else {
		        				wrapper.sendRedirect(redirectPath);
		        				return;
		        			}
		        		}catch (Exception e) {
							// TODO: handle exception
		        			e.printStackTrace();
		        			wrapper.sendRedirect(redirectPath);
				            return;
						}
					}
	
		            return;
		        }   
	    }   
	  
	    @Override  
	    public void init(FilterConfig filterConfig) throws ServletException {   
	    	configAsset = filterConfig;
	    }
}
