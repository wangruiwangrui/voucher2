package com.voucher.manage.controller;


import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.activemq.filter.function.splitFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


import com.rmi.server.AssetsImpl;
import com.rmi.server.Server;
import com.voucher.manage.dao.HiddenDAO;
import com.voucher.manage.dao.RoomInfoDao;
import com.voucher.manage.daoImpl.HiddenDAOImpl;
import com.voucher.manage.redis.Orders;
import com.voucher.manage.redis.RedisDao;
import com.voucher.manage.service.AffairService;
import com.voucher.manage.singleton.Singleton;
import com.voucher.sqlserver.context.Connect;
import com.voucher.sqlserver.context.ConnectRMI;


@Controller
@RequestMapping("/test")
public class testController {
	
	ApplicationContext applicationContext=new Connect().get();
	
     private AffairService testService;
	
     private HiddenDAO hiddenDAO=(HiddenDAO) applicationContext.getBean("hiddenDao");
     
     RoomInfoDao roomInfoDao=(RoomInfoDao) applicationContext.getBean("roomInfodao");
     
	/*
     private RedisDao orderDao;
 	
 	@Autowired
 	public void setOrderDao(RedisDao orderDao) {
 		this.orderDao = orderDao;
 	}
 	*/
     
    com.rmi.server.Server server=new ConnectRMI().get();
     
	@Transactional(rollbackFor = { Exception.class })
	@Autowired
	public void setTestService(AffairService testService) {
		this.testService=testService;
	}
	
	
	@RequestMapping("/aaa")
	public @ResponseBody
	String aaa() {
		
		return "aaa";
		 
	}
	
	
	
	@RequestMapping("affair1")
	public @ResponseBody
	Integer affair1() throws Exception{
	
		
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", 1);
		paramMap.put("val", "dddddd");
		
		int i=0;
		/*
		i=testService.insertAll(paramMap);	 
       */
		
		System.out.println(testService);
		
		i=testService.insert1(paramMap);
		
		
		return i;
	}

	
	@RequestMapping("affair2")
	public @ResponseBody
	Integer affair2() throws Exception{
	
		
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", 2);
		paramMap.put("val", "fffffffff");
		
		int i=0;
		
	    i=testService.insert2(paramMap);
    
		return i;
	}


	/*
	@RequestMapping("save")
	public @ResponseBody String save(@RequestParam String id,@RequestParam 
			String name){
		Orders order=new Orders();
		order.setId(id);
		order.setName(name);
		
		orderDao.save(order);
		
		return "1";
		
	}
	
	
	@RequestMapping("read")
	public @ResponseBody String read(@RequestParam String id){
		Orders order= (Orders) orderDao.read(id);
		
		return order.getName();
	}
	
	@RequestMapping("getAll")
	public @ResponseBody Map getAll(){
		Map<String, Object> map=new HashMap();
		Set set= orderDao.getAll();
		Iterator<String> iterator=set.iterator();
		while (iterator.hasNext()) {
			String key=iterator.next();
			Orders order=(Orders) orderDao.read(key);
			map.put(key, order);			
		}
		return map;
	}
	
	@RequestMapping("del")
	public @ResponseBody Integer del(@RequestParam String id){
		orderDao.delete(id);
		return 1;
	}
	
	@RequestMapping("delAll")
	public @ResponseBody Set delAll(){
		Set set= orderDao.getAll();
		orderDao.deleteAll();
		return set;
	}
	*/
	
	@RequestMapping("getAllCheck")
	public @ResponseBody Map getAllCheck(@RequestParam Integer limit,@RequestParam
			Integer offset){
		
		return hiddenDAO.selectAllHiddenCheck(limit, offset, null, null, null, new HashMap<>());
		
	}
	
	@RequestMapping("getAllChartInfo")
	public @ResponseBody List getAllChartInfo(){
		
		return roomInfoDao.getAllChartInfo();
		
	}
	
	
	@RequestMapping("getAllVerify")
	public @ResponseBody String getAllVerify(){
		
		LinkedHashMap<String, Map<String, Object>> map = Singleton.getInstance().getRegisterMap();

		System.out.println("1:");

		System.out.println("size:" + map.size());

		String s = "";

		for (Map.Entry<String, Map<String, Object>> m : map.entrySet()) {

			map.containsKey(m.getKey());
			System.out.println("key:" + m.getKey());
			for (Map.Entry<String, Object> m2 : m.getValue().entrySet()) {
				System.out.println("     currentSize : " + map.size() + "     key:  " + m2.getKey() + "     value:  "
						+ m2.getValue());
				s = s + "     currentSize : " + map.size() + "     key:  " + m2.getKey() + "     value:  "
						+ m2.getValue();
			}

		}

		return s;
		
	}
}
