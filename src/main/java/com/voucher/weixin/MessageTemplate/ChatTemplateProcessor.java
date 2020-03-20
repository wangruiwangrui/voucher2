package com.voucher.weixin.MessageTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.voucher.manage.mapper.WeiXinMapper;
import com.voucher.manage.model.WeiXin;
import com.voucher.manage.service.WeiXinService;
import com.voucher.manage.tools.MyTestUtil;
import com.voucher.weixin.base.AdvancedUtil;
import com.voucher.weixin.base.CommonUtil;

public class ChatTemplateProcessor {
	private final String SEND_TEMPLAYE_MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";

	private ClassPathXmlApplicationContext applicationContext=new ClassPathXmlApplicationContext("spring-mybatis2.xml");
	
	private DefaultSqlSessionFactory defaultSqlSessionFactory= (DefaultSqlSessionFactory) applicationContext.getBean("sqlSessionFactory");
		
	SqlSession sqlSession=defaultSqlSessionFactory.openSession();
	
	WeiXinMapper weiXinMapper=sqlSession.getMapper(WeiXinMapper.class);
	
	public String sendTemplateMessage(String accessToken, WxTemplate wxTemplate) {	
		int campusId=1;
		String jsonString = new Gson().toJson(wxTemplate).toString();	
		System.out.println("templatemessage="+jsonString);
		String requestUrl = SEND_TEMPLAYE_MESSAGE_URL.replace("ACCESS_TOKEN", accessToken);	
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", jsonString);
	
		if (null != jsonObject) {
			int errorCode = jsonObject.getInteger("errcode");		
			if (0 == errorCode) {
				System.out.println("消息发送成功");
				return "消息发送成功";
			} else  if(errorCode==40001||errorCode==42001){
				String appId, appSecret;
		        WeiXin weiXin;
				weiXin=weiXinMapper.getCampus(campusId);
		        
		        appId=weiXin.getAppId();
		        appSecret=weiXin.getAppSecret();
		        
       		    accessToken=AdvancedUtil.getAccessToken(appId, appSecret);
       		    Map<String, Object> paramMap=new HashMap<>();  
       		    paramMap.put("accessToken", accessToken);
      		    paramMap.put("campusId", campusId);
      		    Date date=new Date();
      		    paramMap.put("createTime", date);
      		    
      		    weiXinMapper.updateCampus(paramMap);
      		    requestUrl = SEND_TEMPLAYE_MESSAGE_URL.replace("ACCESS_TOKEN", accessToken);
      		    jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", jsonString);
      			
      			errorCode = jsonObject.getInteger("errcode");		
    			if (0 == errorCode) {
    				
    				return "消息发送成功";
    			}else{
    				String errorMsg = jsonObject.getString("errmsg");
    				
    			    return "消息发送失败,错误是 "+errorCode+",错误信息是"+ errorMsg;
    			}
       		  }else{
				String errorMsg = jsonObject.getString("errmsg");
				
			    return "消息发送失败,错误是 "+errorCode+",错误信息是"+ errorMsg;
			}
		}else{
		   return "消息发送失败 null json";
		}
	}
	
	
	public String sendTemplateMessage2(Integer campusId,String accessToken, WxTemplate wxTemplate) {	
		String jsonString = new Gson().toJson(wxTemplate).toString();	
		System.out.println("templatemessage="+jsonString);
		String requestUrl = SEND_TEMPLAYE_MESSAGE_URL.replace("ACCESS_TOKEN", accessToken);	
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", jsonString);
	
		if (null != jsonObject) {
			int errorCode = jsonObject.getInteger("errcode");		
			if (0 == errorCode) {
				System.out.println("消息发送成功");
				return "消息发送成功";
			} else  if(errorCode==40001||errorCode==42001){
				String appId, appSecret;
		        WeiXin weiXin;
				weiXin=weiXinMapper.getCampus(campusId);
		        
		        appId=weiXin.getAppId();
		        appSecret=weiXin.getAppSecret();
		        
       		    accessToken=AdvancedUtil.getAccessToken(appId, appSecret);
       		    Map<String, Object> paramMap=new HashMap<>();  
       		    paramMap.put("accessToken", accessToken);
      		    paramMap.put("campusId", campusId);
      		    Date date=new Date();
      		    paramMap.put("createTime", date);
      		    
      		    weiXinMapper.updateCampus(paramMap);
      		    requestUrl = SEND_TEMPLAYE_MESSAGE_URL.replace("ACCESS_TOKEN", accessToken);
      		    jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", jsonString);
      			
      			errorCode = jsonObject.getInteger("errcode");		
    			if (0 == errorCode) {
    				
    				return "消息发送成功";
    			}else{
    				String errorMsg = jsonObject.getString("errmsg");
    				
    			    return "消息发送失败,错误是 "+errorCode+",错误信息是"+ errorMsg;
    			}
       		  }else{
				String errorMsg = jsonObject.getString("errmsg");
				
			    return "消息发送失败,错误是 "+errorCode+",错误信息是"+ errorMsg;
			}
		}else{
		   return "消息发送失败 null json";
		}
	}
}
