package com.voucher.manage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.voucher.manage.dao.RoomInfoDao;
import com.voucher.manage.daoModel.RoomInfo;
import com.voucher.manage.service.WeiXinService;
import com.voucher.sqlserver.context.Connect;

@Controller
@RequestMapping("/roominfo")
public class RoomInfoController {

	ApplicationContext applicationContext=new Connect().get();
	
	private WeiXinService weiXinService;
	
	@Autowired
	public void setWeiXinService(WeiXinService weiXinService) {
		this.weiXinService = weiXinService;
	}
	
	@RequestMapping("/getAll")
	public @ResponseBody Map<String, Object> RoomInfo(Integer limit,Integer offset,String sort,String order,
			String search,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();

		
			
		if(sort!=null&&sort.equals("buildArea")){
			sort="BuildArea";
		}
		
		if(sort!=null&&sort.equals("address")){
			sort="Address";
		}
		if(order!=null&&order.equals("asc")){
			order="asc";
		}
		
		if(order!=null&&order.equals("desc")){
			order="desc";
		}
	
		
		
		Map where=new HashMap<>();
		
		if(search!=null&&!search.trim().equals("")){
			search="%"+search+"%";  
			where.put("Address like ", search);
		}		
		System.out.println("roominfocontroller sort="+sort+"   order="+order);

		RoomInfoDao roomInfoDao=(RoomInfoDao) applicationContext.getBean("roomInfodao");
		
		
		
		
		
		List<RoomInfo> roomInfos=roomInfoDao.findAllRoomInfo(limit,offset,sort,
				order,where);
		System.out.println("roominfocontroller sort="+sort+"   order="+order);
		map.put("rows", roomInfos);
	
		
		
		
		Integer total=roomInfoDao.getRoomInfoCount(where);
		
        map.put("total", total);
		
		return map;
	}
	
	
	@RequestMapping("/findChange")
	public @ResponseBody Map<String, Object> findAllChangehire_CharLog(Integer limit,Integer offset,String sort,String order,
			String search,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		RoomInfoDao roomInfoDao=(RoomInfoDao) applicationContext.getBean("roomInfodao");
		
		if(sort!=null&&sort.equals("buildArea")){
			sort="BuildArea";
		}
		
		if(sort!=null&&sort.equals("address")){
			sort="Address";
		}
		if(order!=null&&order.equals("asc")){
			order="asc";
		}
		
		if(order!=null&&order.equals("desc")){
			order="desc";
		}

		

		if(search!=null&&!search.trim().equals("")){
			search="%"+search+"%";              
		}	
				
		Map map2=roomInfoDao.findAllChangehire_CharLog(limit, offset, sort, order, search);
		
		map.put("rows", map2.get("value"));
		map.put("total", map2.get("rows"));
		
		return map;
	}
	
	
	@RequestMapping("/getAllMessageList")
	public @ResponseBody Map<String, Object> getAllMessageList(Integer limit,Integer offset,String sort,String order,
			String search,HttpServletRequest request){
		
		Integer campusId=1;
		
		if(sort!=null&&sort.equals("sendTime")){
			sort="send_time";
		}else{
			sort="send_time";
		}
		
		if(order!=null&&order.equals("asc")){
			order="asc";
		}
		
		if(order!=null&&order.equals("desc")){
			order="desc";
		}
		
		List list=weiXinService.getAllMessageList(campusId, limit, offset, sort, order, search);
		
		int count=weiXinService.getAllMessageCount(campusId, search);
		
		Map map=new HashMap<>();
		
		map.put("rows", list);
		map.put("total", count);
		
		return map;
	}
	
}
