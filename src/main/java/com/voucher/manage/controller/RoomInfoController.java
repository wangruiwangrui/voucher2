package com.voucher.manage.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
	public  Map getBlueBill(@RequestParam Integer campusId,@RequestParam String State,@RequestParam String datepicker,@RequestParam String datepicker2, Integer limit, Integer offset, String sort,String order,
			String search,HttpServletRequest request) {
		Integer preState = 1;
		if(order!=null&&order.equals("asc")){
			order="asc";
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
		
		Calendar calendar; 
		
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
		
		String startTime = null;
		String endTime = null;
		
		Date sTime = null;
		Date eTime = null;
		
		try {
			if(datepicker!=null&&!datepicker.equals("")){
				sTime=sdf.parse(datepicker);
				startTime=sdf.format(sTime);
			}
			if(datepicker2!=null&&!datepicker2.equals("")){
				eTime=sdf.parse(datepicker2);	
				calendar=Calendar.getInstance();
				calendar.setTime(eTime);
				calendar.add(Calendar.DATE, 1);
				endTime=sdf.format(calendar.getTime());	
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map where=new HashMap<>();
		
		if(search!=null&&!search.trim().equals("")){
			search="%"+search+"%";  
			where.put("gmf_mc like ", search);
		}
		if (startTime!=null&&!startTime.equals("")) {
			where.put("kprq > ", startTime);
		}
		if (endTime!=null&&!endTime.equals("")) {
			where.put("kprq < ", endTime);
		}
		where.put("State=", State);
		where.put("preState=", preState.toString());
		where.put("campusId= ", campusId.toString());
		
		List list = roomInfoDao.getAllBill(limit,offset,order,sort,where);
		int count = roomInfoDao.getAllBillTotal(limit,order,sort,where);
		
		Map map=new HashMap<>();
		
		map.put("rows", list);
		map.put("total", count);	
		return map;
	}
	
	@RequestMapping("/getRedBill")
	@ResponseBody
	public  Map getRedBill(@RequestParam Integer campusId, @RequestParam String State,@RequestParam String datepicker,@RequestParam String datepicker2, Integer limit, Integer offset, String sort,String order,
			 String search,HttpServletRequest request) {
		if(order!=null&&order.equals("asc")){
			order="asc";
		}
		
		Calendar calendar; 
		
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
		
		String startTime = null;
		String endTime = null;
		
		Date sTime = null;
		Date eTime = null;
		
		try {
			if(datepicker!=null&&!datepicker.equals("")){
				sTime=sdf.parse(datepicker);
				startTime=sdf.format(sTime);
			}
			if(datepicker2!=null&&!datepicker2.equals("")){
				eTime=sdf.parse(datepicker2);	
				calendar=Calendar.getInstance();
				calendar.setTime(eTime);
				calendar.add(Calendar.DATE, 1);
				endTime=sdf.format(calendar.getTime());	
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Map where=new HashMap<>();
		
		if(search!=null&&!search.trim().equals("")){
			search="%"+search+"%";  
			where.put("gmf_mc like ", search);
		}
		if (startTime!=null&&!startTime.equals("")) {
			where.put("kprq > ", startTime);
		}
		if (endTime!=null&&!endTime.equals("")) {
			where.put("kprq < ", endTime);
		}
		Integer preState = 1;
		where.put("preState=", preState.toString());
		where.put("State=", State);
		where.put("campusId= ", campusId.toString());
		
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
		List list = roomInfoDao.getAllRedBill(limit,offset,order,sort,where,campusId);
		int count = roomInfoDao.getAllRedBillTotal(limit,order,sort,where,campusId);
		
		Map map=new HashMap<>();
		
		map.put("rows", list);
		map.put("total", count);	
		return map;
	}
	
	@RequestMapping("/payment")
	@ResponseBody
	public  Map payment(@RequestParam Integer campusId,@RequestParam String datepicker,@RequestParam String datepicker2, 
			Integer limit, Integer offset, String sort,String order,String search,HttpServletRequest request) {
		if(order!=null&&order.equals("asc")){
			order="BillId asc";
		}
		
		Calendar calendar; 
		
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
		
		String startTime = null;
		String endTime = null;
		
		Date sTime = null;
		Date eTime = null;
		
		try {
			if(datepicker!=null&&!datepicker.equals("")){
				sTime=sdf.parse(datepicker);
				startTime=sdf.format(sTime);
			}
			if(datepicker2!=null&&!datepicker2.equals("")){
				eTime=sdf.parse(datepicker2);	
				calendar=Calendar.getInstance();
				calendar.setTime(eTime);
				calendar.add(Calendar.DATE, 1);
				endTime=sdf.format(calendar.getTime());	
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Map where=new HashMap<>();
		
		if(search!=null&&!search.trim().equals("")){
			search="%"+search+"%";  
			where.put("gmf_mc like ", search);
		}
		if (startTime!=null&&!startTime.equals("")) {
			where.put("createTime > ", startTime);
		}
		if (endTime!=null&&!endTime.equals("")) {
			where.put("createTime < ", endTime);
		}

		where.put("campusId= ", campusId.toString());
		where.put("preState != ", "1");
		if(sort!=null&&sort.equals("createTime")){
			sort="createTime";
		}
		/*
		 * 前端的user表与其它表不一样，必须指定查询参数，否则抛出sql异常
		 * 默认按id降序排列
		 */
		if(sort==null){
			sort="BillId";
			order="desc";
		}
		Map map = roomInfoDao.getAllErrBill(limit,offset,order,sort,where,campusId);
		return map;
	}
}
