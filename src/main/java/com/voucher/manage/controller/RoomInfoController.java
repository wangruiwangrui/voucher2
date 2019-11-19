package com.voucher.manage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.voucher.manage.dao.RoomInfoDao;
import com.voucher.manage.daoModel.RoomInfo;
import com.voucher.manage.service.WeiXinService;
import com.voucher.sqlserver.context.Connect;

@Controller
@RequestMapping("/roominfo")
public class RoomInfoController {

	ApplicationContext applicationContext=new Connect().get();
	RoomInfoDao roomInfoDao = (RoomInfoDao) applicationContext.getBean("roomInfodao");
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
	public @ResponseBody Map<String, Object> getAllMessageList(@RequestParam Integer campusId,Integer limit,Integer offset,String sort,String order,
			String search,HttpServletRequest request){
		
		
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
		
		if(search!=null&&!search.equals("")){
			search="%"+search+"%";
		}
		
		List list=weiXinService.getAllMessageList(campusId, limit, offset, sort, order, search);
		
		int count=weiXinService.getAllMessageCount(campusId, search);
		
		Map map=new HashMap<>();
		
		map.put("rows", list);
		map.put("total", count);
		
		return map;
	}
	
	
	
	@RequestMapping("/getBlueBill")
	@ResponseBody
	public  Map getBlueBill(@RequestParam Integer campusId,@RequestParam String State, Integer limit, Integer offset, String sort,String order,
			String search,HttpServletRequest request) {
		
		if(order!=null&&order.equals("asc")){
			order="asc";
		}
		
		Map where=new HashMap<>();
		
		if(search!=null&&!search.trim().equals("")){
			search="%"+search+"%";  
			where.put("gmf_mc like ", search);
			where.put("State=", State);
			where.put("campusId= ", campusId.toString());
		}else {
			where.put("State= ", State);
			where.put("campusId= ", campusId.toString());
		}
		
		if(sort!=null&&sort.equals("kprq")){
			sort="kprq";
		}
		/*
		 * 前端的user表与其它表不一样，必须指定查询参数，否则抛出sql异常
		 * 默认按id降序排列
		 */
		if(sort==null){
			sort="BillId";
			order="desc";
		}
		List list = roomInfoDao.getAllBill(limit,order,sort,where);
		int count = roomInfoDao.getAllBillTotal(limit,order,sort,where);
		
		Map map=new HashMap<>();
		
		map.put("rows", list);
		map.put("total", count);	
		return map;
	}
	
	@RequestMapping("/getRedBill")
	@ResponseBody
	public  Map getRedBill(@RequestParam Integer campusId, @RequestParam String state, Integer limit, Integer offset, String sort,String order,
			 String search,HttpServletRequest request) {
		if(order!=null&&order.equals("asc")){
			order="asc";
		}
		
		Map where=new HashMap<>();
		
		if(search!=null&&!search.trim().equals("")){
			search="%"+search+"%";  
			where.put("gmf_mc like ", search);
			where.put("state=", state);
			where.put("campusId=", String.valueOf(campusId));
		}else {
			where.put("state =", state);
			where.put("campusId=", String.valueOf(campusId));
		}
		
		if(sort!=null&&sort.equals("kprq")){
			sort="kprq";
		}
		/*
		 * 前端的user表与其它表不一样，必须指定查询参数，否则抛出sql异常
		 * 默认按id降序排列
		 */
		if(sort==null){
			sort="RedBillId";
			order="desc";
		}
		List list = roomInfoDao.getAllRedBill(limit,order,sort,where,campusId);
		int count = roomInfoDao.getAllRedBillTotal(limit,order,sort,where,campusId);
		
		Map map=new HashMap<>();
		
		map.put("rows", list);
		map.put("total", count);	
		return map;
	}
}
