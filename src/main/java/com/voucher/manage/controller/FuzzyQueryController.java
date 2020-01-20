package com.voucher.manage.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.voucher.manage.dao.FuzzyQueryDao;
import com.voucher.manage.model.FuzzyQuery2;
import com.voucher.sqlserver.context.Connect;


@Controller
@RequestMapping("/baiduMap/FuzzyQuery")
public class FuzzyQueryController {
	
     ApplicationContext applicationContext=new Connect().get();
    
     FuzzyQueryDao fuzzyQueryDao=(FuzzyQueryDao) applicationContext.getBean("fuzzyQueryDao");
     
	@RequestMapping("fuzzyQuery")
	public @ResponseBody FuzzyQuery2 fuzzyQuery(String common,int pageSize,int page, HttpServletRequest request,
			HttpServletResponse response){
		String Address=common;
		String Num=common;
		String RoomProperty=common;
		String Charter=common;
		String Phone=common;
		String ContractNo=common;
		
		return fuzzyQueryDao.fuzzyQuery(Address, Num, RoomProperty, Charter, Phone, ContractNo, pageSize, page);
		
	}	
}
