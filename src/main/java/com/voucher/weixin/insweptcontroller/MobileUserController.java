package com.voucher.weixin.insweptcontroller;

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
@RequestMapping("/mobile/user")
public class MobileUserController {
	
	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping(value="/getUserByPhone")
	public @ResponseBody
	Map<String, Object> getUserByphone(@RequestParam Integer limit,@RequestParam Integer offset,
			String sort,String order,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Users> userlist;
		String type,campusAdmin;
		Integer campusId=0;		
		
		/*
		 * 前端的user表与其它表不一样，必须指定查询参数，否则抛出sql异常
		 * 默认按id降序排列
		 */
		if(sort==null){
			sort="id";
			order="desc";
		}
        
		String search;
		
        userlist=userService.getUserByPhone(limit, offset, sort, order);

		JSONArray  json=JSONArray.parseArray(JSON.toJSONStringWithDateFormat(userlist,"yyyy-MM-dd"));		
		map.put("rows", json);
		return map;
	}
	
}
