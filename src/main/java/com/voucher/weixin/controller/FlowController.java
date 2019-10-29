package com.voucher.weixin.controller;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.rmi.server.Server;
import com.rmi.server.entity.FlowData;
import com.rmi.server.entity.ImageData;
import com.voucher.manage.dao.AssetsDAO;
import com.voucher.manage.dao.FlowDao;
import com.voucher.manage.dao.MobileDAO;
import com.voucher.manage.daoModel.Assets.User_AccessTime;
import com.voucher.manage.daoModel.TTT.DataDictionary;
import com.voucher.manage.service.WeiXinService;
import com.voucher.manage.singleton.Singleton;
import com.voucher.manage.tools.MyTestUtil;
import com.voucher.sqlserver.context.Connect;
import com.voucher.sqlserver.context.ConnectRMI;

@Controller
@RequestMapping("/mobile/flow")
public class FlowController {

	@Value("${Version}")
	public String version;
	
	public void setVersion(String version) {
		this.version = version;
	}


	ApplicationContext applicationContext=new Connect().get();
	
	FlowDao flowDao=(FlowDao) applicationContext.getBean("flowDao");
	
	AssetsDAO assetsDAO=(AssetsDAO) applicationContext.getBean("assetsdao");
	
	MobileDAO mobileDao=(MobileDAO) applicationContext.getBean("mobileDao");
	
	Server server=new ConnectRMI().get();
	
	@RequestMapping(value = "/start")
	public @ResponseBody Map startProcessInstance(@RequestParam String processDefinitionKey,
			@RequestParam String variableData,@RequestParam String className,
			@RequestParam String id,HttpServletRequest request)throws Exception{
		
		String openId=( String ) request.getSession().getAttribute("openId");
		
		LinkedHashMap<String, List<ImageData>> imageDataMap=Singleton.getInstance().getImageDataMap();
    	List<ImageData> imageDataList=new ArrayList<>();
    	try{
    		imageDataList=imageDataMap.get(id);
    	}catch (Exception e) {
			// TODO: handle exception
    		e.printStackTrace();
    		
		}
		
    	if(imageDataList==null){
    		imageDataList=new ArrayList<>();
    	}
    	
		Map map=flowDao.addProcessInstance(server,processDefinitionKey, openId, variableData, className,imageDataList);
		
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
	public @ResponseBody Object findMyPersonalTaskById(@RequestParam String id,HttpServletRequest request){
		
		Object object=server.findMyPersonalTaskById(id);
	
		try {
			Map map = (Map) object;

			FlowData flowData = (FlowData) map.get("flowData");

			List<ImageData> imageDataList = flowData.getImageDataList();

			if (imageDataList != null) {
				mobileDao.flowImageData(request, imageDataList);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return object;
		
	}
	
	@RequestMapping(value = "/personalTask")
	public @ResponseBody Map completeMyPersonalTask(@RequestParam String taskId,@RequestParam Integer input,@RequestParam String variableData,
			@RequestParam String className,String id) throws Exception{
		
		LinkedHashMap<String, List<ImageData>> imageDataMap=Singleton.getInstance().getImageDataMap();
    	List<ImageData> imageDataList=new ArrayList<>();
    	try{
    		imageDataList=imageDataMap.get(id);
    	}catch (Exception e) {
			// TODO: handle exception
    		e.printStackTrace();
    		
		}
		
    	if(imageDataList==null){
    		imageDataList=new ArrayList<>();
    	}
    	
		return server.completeMyPersonalTask(taskId, input, variableData, className,imageDataList);
		
	}
	
	@RequestMapping(value = "/findHistoryById")
	public @ResponseBody Object findHistoryById(@RequestParam String id,HttpServletRequest request){
		
		Object object=server.findHistoryById(id);
		
		try {
			Map map = (Map) object;

			FlowData flowData = (FlowData) map.get("flowData");

			List<ImageData> imageDataList = flowData.getImageDataList();

			if (imageDataList != null) {
				mobileDao.flowImageData(request, imageDataList);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return object;
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
		
		System.out.println("=========================");
		System.out.println(openId);
		
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

	 
	@RequestMapping("/getAccessCount")
	public @ResponseBody Map getAccessCount(HttpServletRequest request,
			HttpServletResponse response) {
		
		String openId = (String) request.getSession().getAttribute("openId");

		User_AccessTime user_AccessTime = new User_AccessTime();
		user_AccessTime.setOpen_id(openId);
		String[] where = { "open_id=", openId };
		user_AccessTime.setWhere(where);

		Date taskDate = null;
		
		Date passDate = null;
		
		long taskCount = 0;

		long passCount = 0;
		
		Map map=new HashMap<>();
		
		User_AccessTime user_AccessTime2 = null;

		try {
			List list = flowDao.selectUserAccessTime(openId);
			
			user_AccessTime2 = (User_AccessTime) list.get(0);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			map.put("taskCount", taskCount);
			map.put("passCount", passCount);
			return map;
		}
		
		taskDate = user_AccessTime2.getMy_task();
		taskCount = server.findMyPersonalTaskCount(openId, taskDate);

		System.out.println("================--------------");
		MyTestUtil.print(taskDate);
		
		passDate = user_AccessTime2.getPass();
		System.out.println("passDate="+passDate);
		MyTestUtil.print(user_AccessTime2);
		passCount = server.selectCountAfter(openId, passDate);

		map.put("taskCount", taskCount);
		map.put("passCount", passCount);
		
		return map;
		
	}
	 
	 
	@RequestMapping("/upUserAccessTime")
	public void upUserAccessTime(@RequestParam String parameter, HttpServletRequest request,
			HttpServletResponse response) {

		String openId = (String) request.getSession().getAttribute("openId");

		Date date = new Date();

		User_AccessTime user_AccessTime = new User_AccessTime();
		user_AccessTime.setOpen_id(openId);
		String[] where = { "open_id=", openId };
		user_AccessTime.setWhere(where);

		int count;

		User_AccessTime user_AccessTime2 = null;

		try {
			List list = flowDao.selectUserAccessTime(openId);
			user_AccessTime2 = (User_AccessTime) list.get(0);
		} catch (Exception e) {
			// TODO: handle exception
		}

		if (parameter.equals("first")) {
			user_AccessTime.setFirst(date);
			if (user_AccessTime2 != null) {
				count = user_AccessTime2.getFirst_count() + 1;
			} else {
				count = 1;
			}
			user_AccessTime.setFirst_count(count);
		} else if (parameter.equals("myTask")) {
			user_AccessTime.setMy_task(date);
			if (user_AccessTime2 != null) {
				count = user_AccessTime2.getMy_task_count() + 1;
			} else {
				count = 1;
			}
			user_AccessTime.setMy_task_count(count);
		} else if (parameter.equals("pass")) {
			user_AccessTime.setPass(date);
			if (user_AccessTime2 != null) {
				count = user_AccessTime2.getPass_count() + 1;
			} else {
				count = 1;
			}
			user_AccessTime.setPass_count(count);
		}

		int i = flowDao.upUserAccessTime(user_AccessTime);

		if (i == 0) {
			flowDao.insertUserAccessTime(user_AccessTime);
		}

	}
	 
	@RequestMapping(value="/findimagedata")
	public @ResponseBody List findImageData(HttpServletRequest request,@RequestParam String id){
		
		LinkedHashMap<String, List<ImageData>> imageDataMap=Singleton.getInstance().getImageDataMap();
    	List<ImageData> imageDataList=new ArrayList<>();
    	try{
    		imageDataList=imageDataMap.get(id);
    	}catch (Exception e) {
			// TODO: handle exception
    		e.printStackTrace();
    		
		}
    	
    	return mobileDao.flowImageData(request, imageDataList);

	}
	
	
	//查询版本号
	@RequestMapping("/getVersion")
	@ResponseBody
	public Map<String,String> getVersion() {
		
		Map<String,String> map = new HashMap<String, String>();
		
		map.put("version",version);
		
		return map;
	}

	
}
