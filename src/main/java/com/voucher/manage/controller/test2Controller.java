package com.voucher.manage.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
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
import com.voucher.manage.dao.AffairDAO;
import com.voucher.manage.tools.MyTestUtil;
import com.voucher.sqlserver.context.ConnectRMI;

import voucher.Mybatis;

@Controller
@RequestMapping("/test2")
public class test2Controller {

	ApplicationContext applicationContext=new ClassPathXmlApplicationContext("spring-sqlservers.xml");
	
	AffairDAO affairDAO=(AffairDAO) applicationContext.getBean("affairdao");
	
	
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
			@RequestParam String userId,@RequestParam String variableData,@RequestParam String className){
		
		return server.startProcessInstance(processDefinitionKey, userId, variableData, className);
		
	}
	
	@RequestMapping(value = "/findMyTask")
	public @ResponseBody List findMyPersonalTask(@RequestParam String assignee){
		
		return server.findMyPersonalTask(assignee);
		
	}
	
	@RequestMapping(value = "/toRoute")
	public String toRoute(@RequestParam String taskId,@RequestParam String userId,@RequestParam String className){
		
		return server.toRoute(taskId, userId, className);
		
	}
	
	@RequestMapping(value = "/findMyTaskById")
	public @ResponseBody Object findMyPersonalTaskById(@RequestParam String id){
		
		return server.findMyPersonalTaskById(id);
	
		
	}
	
	@RequestMapping(value = "/personalTask")
	public @ResponseBody Map completeMyPersonalTask(@RequestParam String taskId,@RequestParam Integer input,@RequestParam String variableData,
			@RequestParam String className){
		
		return server.completeMyPersonalTask(taskId, input, variableData, className);
		
	}
	
	@RequestMapping(value = "/findHistoryById")
	public @ResponseBody List findHistoryById(@RequestParam String id,HttpServletResponse response){
		
		return server.findHistoryById(id, response);
		
	}
	
	@RequestMapping(value="/selectAttachMent")    
	public @ResponseBody JSONObject selectAttachMent(@RequestParam Integer limit,@RequestParam Integer offset){
		
		JSONObject jsonObject=server.selectAttachMent(limit, offset);
		
		System.out.println("jsonObject="+jsonObject);
		
		MyTestUtil.print(jsonObject);
		
		return jsonObject;
		
		
	}
	
	 @RequestMapping(value = "/process/trace/auto")
	 public void readResource(@RequestParam String executionId, HttpServletResponse response) throws Exception{
		
		response.setHeader("Cache-Control", "no-store"); // 禁止浏览器缓存
		response.setHeader("Pragrma", "no-cache"); // 禁止浏览器缓存
		response.setDateHeader("Expires", 0); // 禁止浏览器缓存
		response.setCharacterEncoding("UTF-8");
		
		byte[] byt=server.readResource(executionId);

	    ByteArrayInputStream in = new ByteArrayInputStream(byt);    //将b作为输入流；
 		
		// 输出资源内容到相应对象
		byte[] b = new byte[1024];
		int len;
		while ((len = in.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}

	 }
	
}
