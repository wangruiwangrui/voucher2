package com.voucher.manage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.voucher.manage.dao.RoomChangeHireLogDAO;
import com.voucher.manage.daoModel.RoomChangeHireLog;
import com.voucher.sqlserver.context.Connect;

@Controller
@RequestMapping("/roomChangeHireLog")
public class RoomChangeHireLogController {

	ApplicationContext applicationContext=new Connect().get();
	
	@RequestMapping("/getAll")
	public @ResponseBody Map<String, Object> RoomChangeHireLog(Integer limit,Integer offset,String sort,String order,
			String search,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();

		if(sort!=null&&sort.equals("Charter")){
			sort="Charter";
		}
		
		if(sort!=null&&sort.equals("OHire")){
			sort="OHire";
		}
		
		if(sort!=null&&sort.equals("Area")){
			sort="Area";
		}
		
	
		
		if(search!=null&&!search.trim().equals("")){
			search="%"+search+"%";                 //sqlserver 必须加''
		}		
		

		RoomChangeHireLogDAO roomChangeHireLogdao=(RoomChangeHireLogDAO) applicationContext.getBean("roomChangeHireLogdao");
		
		List<RoomChangeHireLog> roomChangeHireLog=roomChangeHireLogdao.findAllRoomInfo(limit,offset,sort,
				order,search);
		
		map.put("rows", roomChangeHireLog);
		
		Integer total=roomChangeHireLogdao.getRoomChangeHireLogCount(search);
		
        map.put("total", total);
		
        return map;
	}
}
