package com.voucher.manage.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bouncycastle.jce.provider.JCEMac.MD5;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.voucher.manage.dao.IUserDAO;
import com.voucher.manage.daoModel.Users;
import com.voucher.manage.tools.Md5;

@Controller
@RequestMapping("/houses")
public class SqlServerController {

	ApplicationContext applicationContext=new ClassPathXmlApplicationContext("spring-sqlservers.xml");
	
	@RequestMapping("/get")
	public @ResponseBody Map<String, Object> demo6(){
		Map<String, Object> map = new HashMap<String, Object>();
		
    	IUserDAO dao=(IUserDAO) applicationContext.getBean("dao");
        List<Users> users=dao.findAll();
        
        map.put("rows", users);
        map.put("total", users.size());
              
        return map;
	}
	
	@RequestMapping("/set")
	public @ResponseBody Integer demo(){
		Map<String, Object> map = new HashMap<String, Object>();
		
    	IUserDAO dao=(IUserDAO) applicationContext.getBean("dao");
        Users users=new Users();
        
        int i=0;
        
        for(;i<100;i++){
        users.setId(i);
        String a=Md5.GetMD5Code(String.valueOf(Math.random()*1000));
        users.setPassword(a);
        String b=Md5.GetMD5Code(String.valueOf(Math.random()*1000));
        users.setUsername(b);
        dao.addUser(users);
        dao.addUser2(users);
        }
        
        return 1;

	}
}
