package com.voucher.manage.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.voucher.manage.dao.AffairDAO;

@Controller
@RequestMapping("/test2")
public class test2Controller {

	ApplicationContext applicationContext=new ClassPathXmlApplicationContext("spring-sqlservers.xml");
	
	AffairDAO affairDAO=(AffairDAO) applicationContext.getBean("affairdao");
	
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
}
