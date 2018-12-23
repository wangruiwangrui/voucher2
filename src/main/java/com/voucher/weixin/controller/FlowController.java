package com.voucher.weixin.controller;

import java.io.ByteArrayInputStream;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.rmi.server.Server;
import com.voucher.manage.dao.AssetsDAO;
import com.voucher.manage.dao.FlowDao;
import com.voucher.manage.tools.MyTestUtil;
import com.voucher.sqlserver.context.Connect;
import com.voucher.sqlserver.context.ConnectRMI;

@Controller
@RequestMapping("/mobile/flow")
public class FlowController {

	ApplicationContext applicationContext=new Connect().get();
	
	FlowDao flowDao=(FlowDao) applicationContext.getBean("flowDao");
	
	AssetsDAO assetsDAO=(AssetsDAO) applicationContext.getBean("assetsdao");
	
	Server server=new ConnectRMI().get();
	
	@RequestMapping(value = "/start")
	public @ResponseBody Map startProcessInstance(@RequestParam String processDefinitionKey,
			@RequestParam String variableData,@RequestParam String className,HttpServletRequest request)throws Exception{
		
		String openId=( String ) request.getSession().getAttribute("openId");
		
		Map map=flowDao.addProcessInstance(server,processDefinitionKey, openId, variableData, className);
		
		return map;
	
	}
	
		
	@RequestMapping(value = "/toRoute")
	public String toRoute(@RequestParam String taskId,@RequestParam String className){
		
		return server.toRoute(taskId,className);
		
	}
	
	@RequestMapping(value = "/findMyTask")
	public @ResponseBody Map findMyTask(HttpServletRequest request,@RequestParam Integer limit,@RequestParam Integer offset){
		
		String openId=( String ) request.getSession().getAttribute("openId");
		
		return server.findMyPersonalTask(openId,limit,offset);
		
	}
	
	@RequestMapping(value = "/findMyTaskById")
	public @ResponseBody Object findMyPersonalTaskById(@RequestParam String id){
		
		return server.findMyPersonalTaskById(id);
	
		
	}
	
	@RequestMapping(value = "/personalTask")
	public @ResponseBody Map completeMyPersonalTask(@RequestParam String taskId,@RequestParam Integer input,@RequestParam String variableData,
			@RequestParam String className) throws Exception{
		
		return server.completeMyPersonalTask(taskId, input, variableData, className);
		
	}
	
	@RequestMapping(value = "/findHistoryById")
	public @ResponseBody Object findHistoryById(@RequestParam String id,HttpServletResponse response){
		
		return server.findHistoryById(id);
		
	}
	
	@RequestMapping(value="/selectAttachMent")    
	public @ResponseBody JSONObject selectAttachMent(@RequestParam Integer limit,@RequestParam Integer offset){
		
		JSONObject jsonObject=server.selectAttachMent(limit, offset);
		
		System.out.println("jsonObject="+jsonObject);
		
		MyTestUtil.print(jsonObject);
		
		return jsonObject;
		
		
	}
	
	@RequestMapping(value="/findMyAllHistory")
	public @ResponseBody Map findMyAllHistory(@RequestParam Integer limit,@RequestParam Integer offset,HttpServletRequest request){
		
		String openId=( String ) request.getSession().getAttribute("openId");
		
		return server.findMyAllHistory(openId, limit, offset);
		
	}
	
	@RequestMapping(value="/findAllHistory")
	public @ResponseBody Map findAllHistory(@RequestParam Integer limit,@RequestParam Integer offset){
		
		return server.findAllHistory(limit, offset);
		
	}
	
	@RequestMapping(value="/selectAll")
	public @ResponseBody Map selectAll(@RequestParam Integer result,@RequestParam int limit,@RequestParam int offset){

		return server.selectAll(result, limit, offset);

	}

	@RequestMapping(value="/selectByOpenId")
	public @ResponseBody Map selectByOpenId(@RequestParam Integer result, @RequestParam int limit,
			@RequestParam int offset,HttpServletRequest request){

		String openId=( String ) request.getSession().getAttribute("openId");
		
		return server.selectByOpenId(openId, result, limit, offset);

	} 
	
	@RequestMapping(value="/selectAllByState")
	public @ResponseBody Map selectAllByState(@RequestParam Integer state,@RequestParam int limit,@RequestParam int offset){
		
		return server.selectAllByState(state, limit, offset);
		
	}
	
	@RequestMapping(value="/selectByGuid")
	public @ResponseBody Map selectByGuid(String guid){
		
		return server.selectByGuid(guid);
		
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
