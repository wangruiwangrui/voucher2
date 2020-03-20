package com.voucher.manage.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.voucher.manage.model.Users;
import com.voucher.manage.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	@RequestMapping(value="/getAllUser")
	public @ResponseBody
	Map<String, Object> getAllUser(Integer limit,Integer offset,String sort,String order,
			String search,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Users> userlist;
		String type,campusAdmin;
		Integer campusId=0;

		if(sort!=null&&sort.equals("subscribeTime")){
			sort="subscribe_time";
		}
		
		if(sort!=null&&sort.equals("totalAmount")){
			sort="total_amount";
		}
		
		if(sort!=null&&sort.equals("defaultAddress")){
			sort="default_address";
		}
		
		/*
		 * 前端的user表与其它表不一样，必须指定查询参数，否则抛出sql异常
		 * 默认按id降序排列
		 */
		if(sort==null){
			sort="id";
			order="desc";
		}
		
		if(search!=null&&!search.trim().equals("")){
			search="%"+search+"%";
		}		
		
		Cookie[] cookies = request.getCookies();  
		if(cookies!=null){
			for(Cookie i:cookies){
				if(i.getName().equalsIgnoreCase("campusId"))
					campusId=Integer.parseInt(i.getValue());
			}
		}
			
		HttpSession session=request.getSession();  //取得session的type变量，判断是否为公众号管理员
		type=session.getAttribute("type").toString();
		campusAdmin=session.getAttribute("campusAdmin").toString();
        if(type.equals("0")){
        	userlist=userService.getAllFullUser(campusId,limit,offset,sort,order,search);
        	map.put("total", userService.getUserFullCount(campusId,search));
        }else{
		    return map;
        }
		JSONArray  json=JSONArray.parseArray(JSON.toJSONStringWithDateFormat(userlist,"yyyy-MM-dd"));		
		map.put("rows", json);
		return map;
	}

	@RequestMapping(value="/getAllChartUser")
	public @ResponseBody
	Map<String, Object> getAllChartUser(Integer limit,Integer offset,String sort,String order,
			String search,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Users> userlist;
		String type,campusAdmin;
		Integer campusId=0;

		if(sort!=null&&sort.equals("subscribeTime")){
			sort="subscribe_time";
		}
		
		if(sort!=null&&sort.equals("totalAmount")){
			sort="total_amount";
		}
		
		if(sort!=null&&sort.equals("defaultAddress")){
			sort="default_address";
		}
		
		/*
		 * 前端的user表与其它表不一样，必须指定查询参数，否则抛出sql异常
		 * 默认按id降序排列
		 */
		if(sort==null){
			sort="id";
			order="desc";
		}
		
		if(search!=null&&!search.trim().equals("")){
			search="%"+search+"%";
		}		
		
		Cookie[] cookies = request.getCookies();  
		if(cookies!=null){
			for(Cookie i:cookies){
				if(i.getName().equalsIgnoreCase("campusId"))
					campusId=Integer.parseInt(i.getValue());
			}
		}
			
		HttpSession session=request.getSession();  //取得session的type变量，判断是否为公众号管理员
		type=session.getAttribute("type").toString();
		campusAdmin=session.getAttribute("campusAdmin").toString();
        if(type.equals("0")){
        	userlist=userService.getAllChartUser(campusId,limit,offset,sort,order,search);
        	map.put("total", userService.getAllChartCount(campusId, search));
        }else{
		    return map;
        }
		JSONArray  json=JSONArray.parseArray(JSON.toJSONStringWithDateFormat(userlist,"yyyy-MM-dd"));		
		map.put("rows", json);
		return map;
	}
	
	@RequestMapping(value="/upAtionFormatter")
	public @ResponseBody Integer upAtionFormatter(HttpServletRequest request,@RequestParam String openId,
			@RequestParam Integer campusId,@RequestParam Integer place){
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("openId", openId);
		map.put("place", place);
		map.put("campusId", campusId);
		
		return userService.upAtionFormatter(map);
	}
	
	@RequestMapping(value="/deleteByOpenId")
	public @ResponseBody Integer deleteByOpenId(HttpServletRequest request,@RequestParam String openId,
			@RequestParam Integer campusId){
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("openId", openId);
		map.put("campusId", campusId);
		
		return userService.deleteByOpenId(map);
	}
	
	/**
	 * 閫�鍑虹櫥褰�
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/logout")
	public  String logout(HttpServletRequest request){	
		request.getSession().removeAttribute("campusAdmin");

		return "redirect:/index.html";
	}
	
	/**
	 * 鑾峰彇鐢ㄦ埛绫诲瀷
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getUserType")
	public @ResponseBody Short getUserType(HttpServletRequest request){
		return (Short) request.getSession().getAttribute("type");
	}
	

}
