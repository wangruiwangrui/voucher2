package com.voucher.weixin.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.voucher.manage.dao.AssetsDAO;
import com.voucher.manage.dao.RoomInfoDao;
import com.voucher.manage.daoModel.Assets.Patrol_Cycle;
import com.voucher.manage.model.WeiXin;
import com.voucher.manage.service.WeiXinService;
import com.voucher.sqlserver.context.Connect;



@Controller
@RequestMapping("/weixinset")
public class WeinXinSetController {

    private WeiXinService weixinService;
	
	@Autowired
	public void setAccessTokenService(WeiXinService weiXinService) {
		this.weixinService=weiXinService;
	}
	
	ApplicationContext applicationContext = new Connect().get();

	AssetsDAO assetsDAO = (AssetsDAO) applicationContext.getBean("assetsdao");
	
	@RequestMapping("getCampusInfo")
	public @ResponseBody List<WeiXin>
	getCampusInfo(HttpServletRequest request){		
		String campusAdmin;
		Short type;
        Integer  campusId,cityId,currentId;
		List<WeiXin> weixins=new ArrayList<>();
		WeiXin weixin;
		
		HttpSession session=request.getSession();  //取得session的type变量，判断是否为公众号管理员
		type=(Short) session.getAttribute("type");
		cityId=(Integer) session.getAttribute("cityId");

		//服务器端的相对地址
		String homeUrl=request.getHeader("Host")+request.getContextPath();
		
		if(type==0){
		   weixins=weixinService.getAllCampusById(cityId);
		 }
		
		Iterator<WeiXin> iterator=weixins.iterator();
		
		while (iterator.hasNext()) {
			weixin=iterator.next();
			currentId=weixin.getCampusId();
			weixin.setUrl("http://"+homeUrl+"/wechat/security.do?campusId="+currentId);
		}
		
		return weixins;
		
	}
	
	@RequestMapping("insertIntoCampus")
	public @ResponseBody 
	Integer insetIntoCampus(HttpServletRequest request , @RequestParam Integer campusId,@RequestParam String campusName
			,@RequestParam String companyName,@RequestParam String customService,@RequestParam String appId 
			,@RequestParam String mchId,@RequestParam String api, @RequestParam String appSecret
			,@RequestParam String token, @RequestParam String uid, @RequestParam String uidKey
			,@RequestParam String userName) {
		Map<String, Object> paramMap=new HashMap<>();
		Integer  cityId , flag ;
		WeiXin be=null;
		
		HttpSession session=request.getSession();  //ȡ��session��type�������ж��Ƿ�Ϊ���ںŹ���Ա
		cityId=(Integer) session.getAttribute("cityId");
		
		Short type=(Short)session.getAttribute("type");
		if(type==1){
			return 0;
		}
		System.out.println("========"+companyName);
		
		paramMap.put("campusName", campusName);
		paramMap.put("companyName", companyName);
		paramMap.put("cityId", cityId);
		paramMap.put("customService", customService);
		paramMap.put("userName", userName);
		paramMap.put("appId", appId);
		paramMap.put("appSecret", appSecret);
		paramMap.put("mchId", mchId);
		paramMap.put("api", api);
		paramMap.put("token", token);
		paramMap.put("uid", uid);
		paramMap.put("uidKey", uidKey);
		paramMap.put("campusId", campusId);
		 Date date=new Date();
		 paramMap.put("createTime", date);
		if(campusId!=null){
		   be=weixinService.getByCampusIds(campusId);
		}
		System.out.println("be="+be+ "         campusid="+campusId);
		if(be==null){
		 flag=weixinService.insertIntoCampus(paramMap);
		}else{
		  paramMap.put("campusId", campusId);
		  flag=weixinService.updateCampusById(paramMap);
		}
		
		System.out.println("==========="+flag);
		return flag;
	}
	
	
	@RequestMapping("setPatrolCycleA")
	public @ResponseBody Integer setPatrolCycleA(@RequestParam Integer d,HttpServletRequest request){
		
		Patrol_Cycle patrol_Cycle=new Patrol_Cycle();
		
		patrol_Cycle.setAsset_cycle(d);
		patrol_Cycle.setAsset_date(new Date());
		
		return assetsDAO.updatePatrolCycle(patrol_Cycle);
		
	}
	
	@RequestMapping("setPatrolCycleH")
	public @ResponseBody Integer setPatrolCycleH(@RequestParam Integer d,HttpServletRequest request){
		
		Patrol_Cycle patrol_Cycle=new Patrol_Cycle();
		
		patrol_Cycle.setHidden_cycle(d);
		patrol_Cycle.setHidden_date(new Date());
		
		return assetsDAO.updatePatrolCycle(patrol_Cycle);
		
	}
	
	@RequestMapping("getPatrolCycle")
	public @ResponseBody Patrol_Cycle getPatrolCycle(HttpServletRequest request){
		
		return assetsDAO.selectPatrolCycle();
		
	}
		
	
}
