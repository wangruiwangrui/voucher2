package com.voucher.manage.mytask;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.voucher.manage.dao.FinanceDAO;
import com.voucher.manage.daoModelJoin.Finance.HireList_ChartInfo_Join;
import com.voucher.manage.model.WeiXin;
import com.voucher.manage.service.WeiXinService;
import com.voucher.sqlserver.context.Connect;
import com.voucher.weixin.controller.WechatSendMessageController;

import common.HttpClient;

@Component("taskJob")
public class TaskJob {

	@Autowired
	private WeiXinService weixinService;

	ApplicationContext applicationContext = new Connect().get();

	FinanceDAO financeDAO = (FinanceDAO) applicationContext.getBean("financeDao");

	private static final String requestUrl = "https://www.sojson.com/open/api/weather/json.shtml";

	public static HttpClient httpClient = new HttpClient();

	public void job1() {

		List<BasicNameValuePair> reqParam = new ArrayList<BasicNameValuePair>();
		reqParam.add(new BasicNameValuePair("city", "泸州"));

		String json = httpClient.doGet(requestUrl, reqParam);
		// JSONObject json=CommonUtil.httpsRequest(requestUrl+"?city=北京", "GET", null);
		JSONObject jsonObject = JSONObject.parseObject(json);

		System.out.println("json=" + json);

	}

	public void sendMatureHire() {

		Integer campusId = 1;

		WeiXin weixin = weixinService.getWeiXinByCampusId(campusId);

		Map map = financeDAO.findMatureHire(15, 2, 0, "GUID", "", null);
		List list = (List) map.get("rows");

		Iterator<HireList_ChartInfo_Join> iterator = list.iterator();

		String key2 = "";

		while (iterator.hasNext()) {
			HireList_ChartInfo_Join hireList_ChartInfo_Join = iterator.next();
			key2 = key2 + "," + hireList_ChartInfo_Join.getRoomAddress();
		}

		Integer place = 4;
		String title = "收租提醒";
		String Send_Type = "租金到期提醒";
		String first_data = " ";

		String allHire = (String) map.get("allHire");
		Integer count = (Integer) map.get("count");

		String keyword1_data = "共" + allHire + "," + count + "户";

		key2 = key2 + "......";

		String keyword2_data = key2;

		String matureTime = (String) map.get("matureTime");

		String keyword3_data = matureTime;
		String keyword4_data = null;
		String url = weixin.getUrl() + "/voucher/mobile/assetAdmin/assetFinance/15matureChartInfo.html";
		String remark_data = "查看详情";

		try {
			new WechatSendMessageController().sendMessage(place, title, Send_Type, url, first_data, keyword1_data,
					keyword2_data, keyword3_data, keyword4_data, "", remark_data);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public void sendMatureHire2() {

		Integer campusId = 1;

		WeiXin weixin = weixinService.getWeiXinByCampusId(campusId);

		Map map = financeDAO.findMatureHire(15, 2, 0, "GUID", "", null);
		List list = (List) map.get("rows");

		Iterator<HireList_ChartInfo_Join> iterator = list.iterator();

		String key2 = "";

		while (iterator.hasNext()) {
			HireList_ChartInfo_Join hireList_ChartInfo_Join = iterator.next();
			key2 = key2 + "," + hireList_ChartInfo_Join.getRoomAddress();
		}

		Integer place = 3;
		String title = "收租提醒";
		String Send_Type = "租金到期提醒";
		String first_data = " ";

		String allHire = (String) map.get("allHire");
		Integer count = (Integer) map.get("count");

		String keyword1_data = "共" + allHire + "," + count + "户";

		key2 = key2 + "......";

		String keyword2_data = key2;

		String matureTime = (String) map.get("matureTime");

		String keyword3_data = matureTime;
		String keyword4_data = null;
		String url = weixin.getUrl() + "/voucher/mobile/assetAdmin/assetFinance/15matureChartInfo.html";
		String remark_data = "查看详情";

		try {
			new WechatSendMessageController().sendMessage(place, title, Send_Type, url, first_data, keyword1_data,
					keyword2_data, keyword3_data, keyword4_data, "", remark_data);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

}
