package com.voucher.weixin.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.message.BasicNameValuePair;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.voucher.manage.dao.AssetsDAO;
import com.voucher.manage.dao.HiddenDAO;
import com.voucher.manage.dao.RoomInfoDao;
import com.voucher.manage.daoModel.RoomInfo;
import com.voucher.manage.daoModel.Assets.Hidden_Check;
import com.voucher.manage.daoModel.TTT.ChartInfo;
import com.voucher.manage.daoModel.TTT.PreMessage;
import com.voucher.manage.mapper.MessageListMapper;
import com.voucher.manage.mapper.UsersMapper;
import com.voucher.manage.mapper.WeiXinMapper;
import com.voucher.manage.model.MessageList;
import com.voucher.manage.model.Users;
import com.voucher.manage.model.WeiXin;
import com.voucher.manage.singleton.Singleton;
import com.voucher.manage.tools.MyTestUtil;
import com.voucher.sqlserver.context.Connect;
import com.voucher.weixin.MessageTemplate.ChatTemplateProcessor;
import com.voucher.weixin.MessageTemplate.TemplateData;
import com.voucher.weixin.MessageTemplate.WxTemplate;

import common.HttpClient;

@Controller
@RequestMapping("/mobile/WechatSendMessage")
public class WechatSendMessageController {
	Integer campusId=1;

	ApplicationContext applicationContext=new Connect().get();
	
	HiddenDAO hiddenDAO=(HiddenDAO) applicationContext.getBean("hiddenDao");
	
	AssetsDAO assetsDAO=(AssetsDAO) applicationContext.getBean("assetsdao");
	
	RoomInfoDao roomInfoDao=(RoomInfoDao) applicationContext.getBean("roomInfodao");
	
	ClassPathXmlApplicationContext mysqlApplicationContext = new ClassPathXmlApplicationContext(
			"spring-mybatis2.xml");
	DefaultSqlSessionFactory defaultSqlSessionFactory = (DefaultSqlSessionFactory) mysqlApplicationContext
			.getBean("sqlSessionFactory");
	SqlSession sqlSession = defaultSqlSessionFactory.openSession();
	
	private UsersMapper usersMapper = sqlSession.getMapper(UsersMapper.class);
	
	private WeiXinMapper weiXinMapper = sqlSession.getMapper(WeiXinMapper.class);
		
	private MessageListMapper messageListMapper = sqlSession.getMapper(MessageListMapper.class);
	
	@RequestMapping("/send")
	public @ResponseBody String template(HttpServletRequest request,HttpServletResponse response,
			@RequestParam String title,@RequestParam String reportUser,
			@RequestParam String reportContext,@RequestParam String place,@RequestParam String url) {
		String accessToken;
    	WeiXin weixin;
		
		weixin=weiXinMapper.getCampus(campusId);   	
		accessToken=weixin.getAccessToken();
    	
		List userlist=usersMapper.getUserByGuidance();
		
    	ChatTemplateProcessor wechatTemplate=new ChatTemplateProcessor();
    	
    	Iterator<Users> iterator=userlist.iterator();
    	
    	String message="";
    	
    	while (iterator.hasNext()) {
		
    	Users users=iterator.next();	
    	
    	String openid=users.getOpenId();
    	
    	WxTemplate templateData=new WxTemplate();
    	System.out.println("url="+url);
    	templateData.setUrl(url);
    	templateData.setTouser(openid);
    	templateData.setTopcolor("#000000");
    	templateData.setTemplate_id("TxvYImikyfvBejKmpBlwJ1-RB8nzQDI4YbljomErC_Q");
    	Map<String,TemplateData> m = new HashMap<String,TemplateData>();
    	TemplateData first = new TemplateData();
    	first.setColor("#000000");
    	first.setValue(title);
    	m.put("first", first);
    	TemplateData keyword1 = new TemplateData();
    	keyword1.setColor("#328392");
    	keyword1.setValue(reportUser);
    	m.put("keyword1", keyword1);
    	TemplateData keyword2 = new TemplateData();
    	keyword2.setColor("#328392");
    	keyword2.setValue(reportContext);
    	m.put("keyword2", keyword2);
    	TemplateData keyword3 = new TemplateData();
    	keyword3.setColor("#328392");
    	keyword3.setValue(place);
    	m.put("keyword3", keyword3);
    	SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
    	Date date=new Date();
		String time=sdf.format(date);
    	TemplateData keyword4 = new TemplateData();
    	keyword4.setColor("#328392");
    	keyword4.setValue(time);
    	m.put("keyword4", keyword4);
    	TemplateData remark = new TemplateData();
    	remark.setColor("#929232");
    	remark.setValue("点击查看事件位置");
    	m.put("remark", remark);
    	templateData.setData(m);
    	
    	String s=wechatTemplate.sendTemplateMessage(accessToken, templateData);
    	
    	message=message+"  "+users.getNickname()+"  "+s;
    	
    	MessageList messageList=new MessageList();
    	
    	messageList.setCampusId(campusId);
    	messageList.setOpenId(openid);
    	messageList.setContext(reportContext);
    	messageList.setType("紧急报告");
    	messageList.setSendTime(date);
    	if(s.equals("消息发送成功")){
    		messageList.setState(1);
    	}else{
    		messageList.setState(0);
    	}
    	
    	messageListMapper.insertMessageList(messageList);
    	
       }
    	
      return message;
    	
	}
	
	//向承租人发隐患通知
	public void send(@RequestParam String guid,@RequestParam String check_id,String userName,
			String openId,HttpServletRequest request){

		Integer campusId=1;
		
		String accessToken;
    	WeiXin weixin;

		Map search=new HashMap<>();
		
		search.put(Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID=", guid);
		
		List<RoomInfo> list=roomInfoDao.findAllRoomInfo(2, 0, null, null, search);
		
		RoomInfo roomInfo=list.get(0);
		
		String chartGUID=roomInfo.getChartGUID();
		
		Map search2=new HashMap<>();
		
		search2.put(Singleton.ROOMDATABASE+".[dbo].[ChartInfo].GUID=", chartGUID);
		search2.put(Singleton.ROOMDATABASE+".[dbo].[ChartInfo].IsHistory=", "0");
		
		List<ChartInfo> list2=(List<ChartInfo>) roomInfoDao.getChartInfoByGUID(1, 0, null, null, search2).get("rows");

		try {
			
			Map search3 = new HashMap<>();

			search3.put("check_id=", check_id);

			List<Hidden_Check> list3 = (List<Hidden_Check>) hiddenDAO.selectAllHidden(2, 0, null, null, search3)
					.get("rows");

			Hidden_Check hidden = list3.get(0);
			
			String Message="您承租的资产存在" + hidden.getCheck_circs() + "的隐患,请注意安全防范!";
			
			if (list2 != null) {
				
				ChartInfo chartInfo = list2.get(0);
				String charter = chartInfo.getCharter().trim();
				String phone = chartInfo.getPhone().trim();
				System.out.println("charter=" + charter + "     phone=" + phone);

				sendPhoneMessage(chartInfo.getPhone(), "尊敬的承租人" + charter + " , 您好 : "+Message, charter, openId);
								
				Users users = usersMapper.getUserByAssetCharter(charter, phone);

				// 向承租人发微信消息
				if (users != null) {
					
					weixin = weiXinMapper.getCampus(campusId);
					accessToken = weixin.getAccessToken();

					WxTemplate templateData = new WxTemplate();
					templateData.setTouser(openId);
					templateData.setTopcolor("#000000");
					templateData.setTemplate_id("nBV50MfKYjpDlWqXJQAgjPZrW-925l45CYoxNaiMSI0");
					Map<String, TemplateData> m = new HashMap<String, TemplateData>();
					TemplateData first = new TemplateData();
					first.setColor("#000000");
					first.setValue("尊敬的承租人" + charter + " , 您好 : ");
					m.put("first", first);

					TemplateData keyword1 = new TemplateData();
					keyword1.setColor("#328392");
					keyword1.setValue(userName);
					m.put("keyword1", keyword1);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date = new Date();
					String time = sdf.format(date);
					TemplateData keyword2 = new TemplateData();
					keyword2.setValue(time);
					m.put("keyword2", keyword2);
					TemplateData keyword3 = new TemplateData();
					keyword3.setValue("安全巡查");
					m.put("keyword3", keyword3);
					TemplateData keyword4 = new TemplateData();
					keyword4.setColor("#328392");
					keyword4.setValue("您承租的资产存在" + hidden.getCheck_circs() + "的隐患,请注意安全防范!");
					m.put("keyword4", keyword4);
					templateData.setData(m);

					ChatTemplateProcessor wechatTemplate = new ChatTemplateProcessor();

					String s = wechatTemplate.sendTemplateMessage(accessToken, templateData);

					MessageList messageList = new MessageList();

					messageList.setCampusId(campusId);
					messageList.setOpenId(openId);
					messageList.setContext(Message);
					messageList.setType("安全通知");
					messageList.setSendTime(date);
					if (s.equals("消息发送成功")) {
						messageList.setState(1);
					} else {
						messageList.setState(0);
					}

					MessageListMapper messageListMapper = sqlSession.getMapper(MessageListMapper.class);

					messageListMapper.insertMessageList(messageList);
				}

			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	
	public void sendMessage(@RequestParam Integer place,@RequestParam String Template_Id,
			@RequestParam String Send_Type,@RequestParam String url,
			@RequestParam String first_data,@RequestParam String keyword1_data,
			@RequestParam String keyword2_data,@RequestParam String keyword3_data,
			@RequestParam String keyword4_data,@RequestParam String keyword5_data,
			@RequestParam String remark_data, 
			@RequestParam String currentOpenId){

		Integer campusId=1;
		
		String accessToken;
    	WeiXin weixin;

	try{
		
		ClassPathXmlApplicationContext applicationContext=new ClassPathXmlApplicationContext("spring-mybatis2.xml");		
		DefaultSqlSessionFactory defaultSqlSessionFactory= (DefaultSqlSessionFactory) applicationContext.getBean("sqlSessionFactory");				
		SqlSession sqlSession=defaultSqlSessionFactory.openSession();
			
		UsersMapper usersMapper=sqlSession.getMapper(UsersMapper.class);
			
		MessageListMapper messageListMapper=sqlSession.getMapper(MessageListMapper.class);
			
		List<Users> list=usersMapper.getUserByGuidance();
			
	  if(list!=null){
		
		 Iterator<Users> iterator=list.iterator();
			
	    while (iterator.hasNext()) {
			
		    Users users=iterator.next();

			if(users==null)
				return ;
			
			String openId=users.getOpenId();
			
			if(currentOpenId!=null&&!currentOpenId.equals("")&&openId.equals(currentOpenId)){
			 //	continue; //跳过本人的微信号
			}
			
			WeiXinMapper weiXinMapper=sqlSession.getMapper(WeiXinMapper.class);
			
			weixin=weiXinMapper.getCampus(campusId);   	
			accessToken=weixin.getAccessToken();
		
			WxTemplate templateData=new WxTemplate();
			templateData.setUrl(url);
	    	templateData.setTouser(openId);
	    	templateData.setTopcolor("#000000");
	    	templateData.setTemplate_id(Template_Id);
	    	Map<String,TemplateData> m = new HashMap<String,TemplateData>();
	    	TemplateData first = new TemplateData();
	    	first.setColor("#000000");
	    	first.setValue(first_data);
	    	m.put("first", first);
	        
	    	TemplateData keyword1 = new TemplateData();
	    	keyword1.setColor("#328392");
	    	keyword1.setValue(keyword1_data);
	    	m.put("keyword1", keyword1);
			TemplateData keyword2 = new TemplateData();
			keyword2.setColor("#328392");
			keyword2.setValue(keyword2_data);
			m.put("keyword2", keyword2);
			TemplateData keyword3 = new TemplateData();
			keyword3.setColor("#328392");
			keyword3.setValue(keyword3_data);
			m.put("keyword3", keyword3);
			
			if(keyword4_data!=null&&!keyword4_data.equals("")){
				TemplateData keyword4 = new TemplateData();
				keyword4.setColor("#D2691E");
				keyword4.setValue(keyword4_data);
				m.put("keyword4", keyword4);
			}
			
			if(keyword5_data!=null&&!keyword5_data.equals("")){
				TemplateData keyword5 = new TemplateData();
				keyword5.setColor("#328392");
				keyword5.setValue(keyword5_data);
				m.put("keyword5", keyword5);
			}
			
			if(remark_data!=null&&remark_data.equals("")){
				TemplateData remark = new TemplateData();
				remark.setColor("#929232");
				remark.setValue(remark_data);
				m.put("remark", remark);
			}
	    	templateData.setData(m);
	    	
	    	ChatTemplateProcessor wechatTemplate=new ChatTemplateProcessor();
	    	
	    	String s=wechatTemplate.sendTemplateMessage(accessToken, templateData);
	    	
	    	MessageList messageList=new MessageList();
	    	
	    	messageList.setCampusId(campusId);
	    	messageList.setOpenId(openId);
	    	messageList.setContext(keyword1_data+","+keyword2_data+
	    			","+keyword3_data+","+keyword4_data);
	    	messageList.setType(Send_Type);
	    	messageList.setSendTime(new Date());
	    	if(s.equals("消息发送成功")){
	    		messageList.setState(1);
	    	}else{
	    		messageList.setState(0);
	    	}
	    	
	    	messageListMapper.insertMessageList(messageList);
		  }
		}
	   }catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	public Integer sendPhoneMessage(String phone,String Message,
			String charter,String openId){
		
		Users users = usersMapper.getUserByOnlyOpenId(openId);

		PreMessage preMessage = new PreMessage();

		preMessage.setOptAdd(users.getName());
		preMessage.setMessage(Message);

		HttpClient httpClient = new HttpClient();

		String requestUrl="http://utf8.api.smschinese.cn";

		List<BasicNameValuePair> reqParam = new ArrayList<BasicNameValuePair>();
		reqParam.add(new BasicNameValuePair("Uid", Singleton.UID));
		reqParam.add(new BasicNameValuePair("Key", Singleton.KEY));
		reqParam.add(new BasicNameValuePair("smsMob", phone));
		reqParam.add(new BasicNameValuePair("smsText", Message));
		String r = httpClient.doGet(requestUrl, reqParam);

		String GUID = UUID.randomUUID().toString();

		preMessage.setGUID(GUID);
		preMessage.setPhone(phone);
		preMessage.setOptDate(new Date());
		
		if(charter!=null&&!charter.equals(""))
			preMessage.setPhoneWho(charter);
		
		int i=Integer.parseInt(r);
		
		if(i>0){
			  preMessage.setState("发送成功");
		  }else{
			  preMessage.setState("发送失败");
		  }
		
		roomInfoDao.insertPreMessage(preMessage);

		return i;
	}
	
	
	public Integer getPhoneMessageNumber(){
		
		  HttpClient httpClient = new HttpClient();
		
		  String requestUrl="http://www.smschinese.cn/web_api/SMS";
		  
		  List<BasicNameValuePair> reqParam = new ArrayList<BasicNameValuePair>();
		  reqParam.add(new BasicNameValuePair("Action", "SMS_Num"));
		  reqParam.add(new BasicNameValuePair("Uid", Singleton.UID));
		  reqParam.add(new BasicNameValuePair("Key", Singleton.KEY));
		  String r=httpClient.doGet(requestUrl, reqParam);
		  
		  return Integer.parseInt(r);
		  
	}
	
}
