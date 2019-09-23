package com.voucher.weixin.util;

import java.net.URLEncoder;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.voucher.manage.dao.BillDAO;
import com.voucher.manage.daoModel.TTT.BillServerInfo;
import com.voucher.manage.service.WeiXinService;
import com.voucher.manage.tools.MyTestUtil;
import com.voucher.sqlserver.context.Connect;
import com.voucher.weixin.base.AutoAccessToken;
public class BillAccessTokenUtil {

	ApplicationContext applicationContext = new Connect().get(); 
	BillDAO billDAO= (BillDAO) applicationContext.getBean("billDAO");
	
	private WeiXinService weixinService;
	
	@Autowired
	public void setweixinService(WeiXinService weiXinService) {
		this.weixinService=weiXinService;
	}
	
	HttpUtils ut = new HttpUtils();
	
	//此方法用于获取发票基础access_token
	public String getAccessToken(){
		
		String TokenStr; 
		Date currenTime;
		long timeDifference;
		BillServerInfo billServerinfo = billDAO.getBillServerInfo();
	   
		MyTestUtil.print(billServerinfo);
       
		currenTime=new Date();
  
		timeDifference=currenTime.getTime()-billServerinfo.getTokenCreateDate().getTime();
	   
		 if(timeDifference<7100*1000){
	    	   System.out.println("------------------===========");
	    	   return billServerinfo.getTokenStr();
	       }else{
	    	   	String url = billServerinfo.getAPI_TOKEN_URL();
	   		
	   			JSONObject param = new JSONObject();
	   			param.put("openid", billServerinfo.getOpenID());
	   			param.put("app_secret",billServerinfo.getAppSecret());
	   			TokenStr = ut.doPost2(url, param.toString());
	   			
	   			billServerinfo.setTokenStr(TokenStr);
	   			billServerinfo.setTokenCreateDate(currenTime);
	   			String[] where={"ID=","1"};
	   			billServerinfo.setWhere(where);
	   			System.out.println("-------------------");
	   			MyTestUtil.print(billServerinfo);
	   			Integer i = billDAO.updateBillServerInfo(billServerinfo);
	   			return TokenStr;
	       }
	}
	
	
	//此方法用于获取发票临时票据ticket
	public String getTicket(){
		BillServerInfo billServerInfo = billDAO.getBillServerInfo();
		Date currenTime=new Date();
		  
		Long timeDifference=currenTime.getTime()-billServerInfo.getTicket_Time().getTime();
		
		if(timeDifference<7100*1000){
	    	   System.out.println("------------------===========");
	    	   return billServerInfo.getTicket();
	       }else{
	    	   	String url = billServerInfo.getTicket_URL();
	    	   	
	    	   	
	    	   	Integer campusId=1;
	    	   	
	    	   	String accessToken = AutoAccessToken.get(weixinService, campusId);
	    	   	
	    	   	String params = "access_token="+accessToken+"&type=wx_card";
	    	   	
	   			String param = ut.doGet(url, params);
	   			
	   			JSONObject parseObject = JSONObject.parseObject(param);
	   			String ticket = parseObject.get("ticket").toString();
	   			
	   			billServerInfo.setTicket(ticket);
	   			billServerInfo.setTicket_Time(currenTime);
	   			String[] where={"ID=","1"};
	   			billServerInfo.setWhere(where);
	   			Integer i = billDAO.updateBillServerInfo(billServerInfo);
	   			return ticket;
	       }
		
	}
	
	//此方法用于获取s_pappid
	//提前获取开票平台标识s_pappid，因为同一个开票平台的s_pappid都相同，所以获取s_pappid的操作只需要进行一次。
	public String getS_pappid() {
		BillServerInfo billServerInfo = billDAO.getBillServerInfo();
		String s_pappid = billServerInfo.getS_pappid();
		if(s_pappid==""||s_pappid==null) {
			String access_token = AutoAccessToken.get(weixinService, 1);
			String url = "https://api.weixin.qq.com/card/invoice/seturl?access_token=%7b"+access_token+"%7d";
			
			JSONObject param = new JSONObject();
			String params=ut.doPost2(url, param.toString());
			
			//JSONObject parseObject = JSONObject.parseObject(params);
   			//String invoice_url = parseObject.get("invoice_url").toString();
   			
   			System.out.println("----------++++++++");
   			MyTestUtil.print(params);
		
		}
		return "";
	}
	
}
