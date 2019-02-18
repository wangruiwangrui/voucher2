package com.voucher.manage.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.rmi.server.Server;
import com.rmi.server.entity.FlowImage;
import com.rmi.server.entity.ImageData;
import com.voucher.manage.dao.AffairDAO;
import com.voucher.manage.dao.AssetsDAO;
import com.voucher.manage.dao.FlowDao;
import com.voucher.manage.singleton.Singleton;
import com.voucher.manage.tools.MyTestUtil;
import com.voucher.sqlserver.context.ConnectRMI;

import voucher.Mybatis;

@Controller
@RequestMapping("/test2")
public class test2Controller {

	ApplicationContext applicationContext=new ClassPathXmlApplicationContext("spring-sqlservers.xml");
	
	AffairDAO affairDAO=(AffairDAO) applicationContext.getBean("affairdao");
	
	AssetsDAO assetsDAO=(AssetsDAO) applicationContext.getBean("assetsdao");
	
	FlowDao flowDao=(FlowDao) applicationContext.getBean("flowDao");
	
	Server server=new ConnectRMI().get();
	
	@RequestMapping("affair1")
	public @ResponseBody
	Integer affair1() throws Exception{
	
		
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", 1);
		paramMap.put("val", "dddddd");
		
		int i=0;		
		
		i=affairDAO.insert1(paramMap);
		
		return i;
	}

	@RequestMapping("affair2")
	public @ResponseBody
	Integer affair2() throws Exception{
	
		
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", 2);
		paramMap.put("val", "fffffffff");
		
		int i=0;
		
	    i=affairDAO.insert2(paramMap);
    
		return i;
	}
	
	@RequestMapping("affairAll")
	public @ResponseBody
	Integer affairAll() throws Exception{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", 2);
		paramMap.put("val", "aaaaaaa");
		
		int i=0;
		
		i=affairDAO.insertAll(paramMap);
		
		return i;
	}
	
	@RequestMapping(value = "/start")
	public @ResponseBody Map startProcessInstance(@RequestParam String processDefinitionKey,
			@RequestParam String variableData,@RequestParam String className,
			@RequestParam String id,HttpServletRequest request)throws Exception{
		
		String openId="ccc";
		
		LinkedHashMap<String, List<ImageData>> imageDataMap=Singleton.getInstance().getImageDataMap();
    	List<ImageData> imageDataList=new ArrayList<>();
    	try{
    		imageDataList=imageDataMap.get(id);
    	}catch (Exception e) {
			// TODO: handle exception
    		e.printStackTrace();
    		
		}
		
		Map map=flowDao.addProcessInstance(server,processDefinitionKey, openId, variableData, className,imageDataList);
		
		return map;
	
	}

	@RequestMapping(value = "/personalTask")
	public @ResponseBody Map completeMyPersonalTask(@RequestParam String taskId,@RequestParam Integer input,@RequestParam String variableData,
			@RequestParam String className,@RequestParam String id) throws Exception{
		
		LinkedHashMap<String, List<ImageData>> imageDataMap=Singleton.getInstance().getImageDataMap();
    	List<ImageData> imageDataList=new ArrayList<>();
    	try{
    		imageDataList=imageDataMap.get(id);
    	}catch (Exception e) {
			// TODO: handle exception
    		e.printStackTrace();
    		
		}
		
		return server.completeMyPersonalTask(taskId, input, variableData, className,imageDataList);
		
	}
	
	@RequestMapping(value = "/findMyTask")
	public @ResponseBody Map findMyTask(HttpServletRequest request,@RequestParam Integer limit,@RequestParam Integer offset){
		
		String openId="ccc";
		
		return server.findMyPersonalTask(openId,limit,offset);
		
	}
	
	@RequestMapping(value = "/findMyTaskById")
	public @ResponseBody Object findMyPersonalTaskById(@RequestParam String id){
		
		return server.findMyPersonalTaskById(id);
	
		
	}
	
	@RequestMapping(value = "/toRoute")
	public String toRoute(@RequestParam String taskId,@RequestParam String className){
		
		return server.toRoute(taskId,className);
		
	}
	
	@RequestMapping(value = "/findHistoryById")
	public @ResponseBody Map findHistoryById(@RequestParam String id,HttpServletResponse response){
		
		return server.findHistoryById(id);
		
	}
	
	@RequestMapping(value="/findMyAllHistory")
	public @ResponseBody Map findMyAllHistory(@RequestParam Integer limit,@RequestParam Integer offset,HttpServletRequest request){
		
		String openId="ccc";
		
		return server.findMyAllHistory(openId, limit, offset);
		
	}
}
