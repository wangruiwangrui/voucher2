package com.voucher.weixin.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.voucher.manage.dao.BillDAO;
import com.voucher.manage.daoModel.TTT.BillServerInfo;
import com.voucher.manage.daoModel.TTT.CompanyMsg;
import com.voucher.manage.daoModel.TTT.Payment_Info;
import com.voucher.manage.daoModel.invoice.BusinessData;
import com.voucher.manage.daoModel.invoice.BusinessResult;
import com.voucher.manage.daoModel.invoice.Common_Fpkj_Xmxx;
import com.voucher.manage.model.WeiXin;
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
	public String getAccessToken(String out_trade_no){
		
		String TokenStr; 
		Date currenTime;
		long timeDifference;
		BillServerInfo billServerinfo = billDAO.getBillInfo(out_trade_no);

		currenTime=new Date();
  
		timeDifference=currenTime.getTime()-billServerinfo.getTokenCreateDate().getTime();
	   
		 if(timeDifference<5100*1000){
	    	   return billServerinfo.getTokenStr();
	       }else{
	    	   	String url = billServerinfo.getAPI_TOKEN_URL();
	   		
	   			JSONObject param = new JSONObject();
	   			param.put("openid", billServerinfo.getOpenID());
	   			param.put("app_secret",billServerinfo.getAppSecret());
	   			TokenStr = ut.doPost2(url, param.toString());
	   			
	   			JSONObject parseObject = JSONObject.parseObject(TokenStr);
	   			JSONObject row = (JSONObject) parseObject.get("rows");
	   			String access_token = (String) row.get("access_token");
	   			
	   			billServerinfo.setTokenStr(access_token);
	   			billServerinfo.setTokenCreateDate(currenTime);
	   			String[] where={"ID=","1"};
	   			billServerinfo.setWhere(where);
	   			Integer i = billDAO.updateBillServerInfo(billServerinfo);
	   			return TokenStr;
	       }
	}
	
	
	//此方法用于获取发票临时票据ticket
	public String getTicket(String out_trade_no){
		BillServerInfo billServerInfo = billDAO.getBillInfo(out_trade_no);
		Date currenTime=new Date();
		  
		Long timeDifference=currenTime.getTime()-billServerInfo.getTicket_Time().getTime();
		
		if(timeDifference<7100*1000){
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
	public String getS_pappid(String out_trade_no) {
		BillServerInfo billServerInfo = billDAO.getBillInfo(out_trade_no);
		String s_pappid = billServerInfo.getS_pappid();
		if(s_pappid==""||s_pappid==null) {
			String access_token = AutoAccessToken.get(weixinService, 1);
			System.out.println(access_token);
			String url = "https://api.weixin.qq.com/card/invoice/seturl?access_token="+access_token;

			JSONObject param = new JSONObject();
			String params=ut.doPost2(url, param.toString());
			JSONObject parseObject = JSONObject.parseObject(params);
   			String invoice_url = parseObject.get("invoice_url").toString();
   			
   			s_pappid = invoice_url.substring(invoice_url.indexOf("s_pappid="), invoice_url.length());
   			s_pappid = s_pappid.substring(s_pappid.indexOf("=")+1, s_pappid.length());
   			billServerInfo.setS_pappid(s_pappid);
   			String[] where={"ID=","1"};
   			billServerInfo.setWhere(where);
   			Integer b = billDAO.updateBillServerInfo(billServerInfo);
   			return s_pappid;
		}
		return s_pappid;
	}
	
	//查询商户联系方式
	public Map<String, Object> queryContact() {
		Map map = new HashMap<String,Object>();
		String access_token = AutoAccessToken.get(weixinService, 1);
		String url = "https://api.weixin.qq.com/card/invoice/setbizattr?action=get_contact&access_token="+access_token;
		
		JSONObject param = new JSONObject();
		String params=ut.doPost2(url, param.toString());
		JSONObject parseObject = JSONObject.parseObject(params);
		String errmsg = (String) parseObject.get("errmsg");
		if(errmsg.equals("ok")) {
			JSONObject contact = (JSONObject) parseObject.get("contact");
			map.put("contact", contact);
		}
		map.put("errmsg", errmsg);
		return map;
	}
	
	//商户获取授权链接之前，需要先设置商户的联系方式
	public Map setContact(){
		String access_token = AutoAccessToken.get(weixinService, 1);
		String url = "https://api.weixin.qq.com/card/invoice/setbizattr?action=set_contact&access_token="+access_token;
		
		JSONObject param = new JSONObject();

		Map contact = new HashMap();
		contact.put("phone", "13688215543");
		contact.put("time_out", "1234");
		param.put("contact",contact);
		String params=ut.doPost2(url, param.toString());
		JSONObject parseObject = JSONObject.parseObject(params);
		
		Map map2 = new HashMap();
		Integer errcode = parseObject.getInteger("errcode");
		String errmsg = parseObject.getString("errmsg");
		map2.put("errcode", errcode);
		map2.put("errmsg", errmsg);
		
		return map2;
	}

	//获取授权页链接
	public Map<String, String> getAuthorizationLink(String out_trade_no) {
		
		WeiXin weixin = weixinService.getWeiXinByCampusId(1);
		String accessToken = weixin.getAccessToken();
		//获取授权页链接请求URL
		String url = "https://api.weixin.qq.com/card/invoice/getauthurl?access_token="+accessToken;
		
		//订单id，在商户内单笔开票请求的唯一识别号，
		String order_id = out_trade_no;
		
		//开票平台在微信的标识号，商户需要找开票平台提供
		String s_pappid = getS_pappid(out_trade_no);
		
		String ticket = getTicket(out_trade_no);
		
		Payment_Info pInfo = billDAO.selectPayment_Info(out_trade_no);
		
		//订单金额，以分为单位
		Integer money = pInfo.getTotal_fee().intValue();
		
		//时间戳
		Integer timestamp = (int) new Date().getTime();
		
		//开票来源，app：app开票，web：微信h5开票，wxa：小程序开发票，wap：普通网页开票
		String source = "web";
		
		//授权成功后跳转页面。本字段只有在source为H5的时候需要填写，引导用户在微信中进行下一步流程
		String redirect_url = weixin.getUrl()+"/mobile/asset/hire/seccess.html";
		
		//0：开票授权，1：填写字段开票授权，2：领票授权
		Integer type = 1;
		
		JSONObject param = new JSONObject();
		param.put("s_pappid", s_pappid);
		param.put("order_id", order_id);
		param.put("money", money);
		param.put("timestamp", timestamp);
		param.put("source", source);
		param.put("redirect_url", redirect_url);
		param.put("ticket", ticket);
		param.put("type", type);
		
		String params=ut.doPost2(url, param.toString());
		
		JSONObject parseObject = JSONObject.parseObject(params);
		Integer errcode = parseObject.getInteger("errcode");
		String errmsg = parseObject.getString("errmsg");
		
		Map<String, String> map = new HashMap<String, String>();
		if (errcode==0) {
			map.put("errmsg", errmsg);
			String auth_url = parseObject.getString("auth_url");
			map.put("auth_url", auth_url);
			return map;
		}
		map.put("errmsg", errmsg);
		return map;
	}
	
	
	/**
	 * 开蓝字发票链接
	 * @return
	 */
	public BusinessResult getBillTicket(String out_trade_no,String purchaser,String ein) {
		
		//out_trade_no = "20191015164611992";
		Payment_Info pInfo = billDAO.selectPayment_Info(out_trade_no);    // 通过交易单号查询交易详情
		BillServerInfo billServerinfo = billDAO.getBillInfo(out_trade_no);
		CompanyMsg cMsg = billDAO.queryCompanyMsg(out_trade_no);    //获取销售方纳税人信息
		
		Common_Fpkj_Xmxx common_fpkj_xmxx = new Common_Fpkj_Xmxx();
		common_fpkj_xmxx.setFphxz(billServerinfo.getFphxz());
		common_fpkj_xmxx.setSpbm(billServerinfo.getSpbm());
		common_fpkj_xmxx.setXmmc(billServerinfo.getXmmc());
		common_fpkj_xmxx.setXmdj(pInfo.getTotal_fee().toString());
		common_fpkj_xmxx.setXmsl("1.000000");
		common_fpkj_xmxx.setXmje(pInfo.getTotal_fee().toString());
		common_fpkj_xmxx.setSl(billServerinfo.getSl().toString());

		// 不含税单价=含税单价/1+税率; 税额=不含税单价* 数量 * 税率
		Float dj = (float) Float.parseFloat(common_fpkj_xmxx.getXmdj()) / (1 + Float.parseFloat(common_fpkj_xmxx.getSl()));
		Float se = dj * 1 * Float.parseFloat(common_fpkj_xmxx.getSl());
		
		BusinessData bData = new BusinessData();
		bData.setData_resources(billServerinfo.getData_Resources());
		bData.setNsrsbh(cMsg.getNsrsbh()); 
		bData.setOrder_num(pInfo.getOut_trade_no());
		bData.setBmb_bbh(billServerinfo.getBmb_BBH());
		bData.setZsfs(billServerinfo.getZsfs());
		bData.setXsf_nsrsbh(cMsg.getNsrsbh());
		bData.setXsf_mc(cMsg.getCompanyName());
		bData.setXsf_dzdh(cMsg.getDzdh());
		bData.setXsf_yhzh(cMsg.getYhzh());
		bData.setGmf_mc(pInfo.getName());
		bData.setKpr(cMsg.getOperator());
		
		DecimalFormat   fnum   =   new   DecimalFormat("##0.00");  
		
		bData.setHjje(fnum.format(dj));
		bData.setHjse(fnum.format(se));
		bData.setJshj(fnum.format(dj+se));
				
		String dd=fnum.format(se);
		common_fpkj_xmxx.setSe(dd.toString());
		
		List common_fpkj_xmxx1 = new ArrayList();
		common_fpkj_xmxx1.add(common_fpkj_xmxx);
		bData.setCommon_fpkj_xmxx(common_fpkj_xmxx1);
		
		JSONObject param = new  JSONObject();
		param.put("access_token", billServerinfo.getTokenStr());
		param.put("serviceKey", "ebi_InvoiceHandle_newBlueInvoice");
		param.put("data", bData);

		String url = billServerinfo.getAPI_BUSS_URL();
		String returnString = ut.doPost2(url, param.toString());
		
		JSONObject jsonObject = JSONObject.parseObject(returnString);
		BusinessResult result = JSON.toJavaObject(jsonObject, BusinessResult.class);
		
		if (result.getResult().equals("SUCCESS")) {
			
			Integer re = billDAO.InsertBill(result, null, null);
		}
		
		return result;
	}
	
}
