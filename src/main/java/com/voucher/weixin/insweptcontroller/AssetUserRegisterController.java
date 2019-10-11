package com.voucher.weixin.insweptcontroller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.activemq.filter.function.makeListFunction;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.voucher.manage.dao.AssetsDAO;
import com.voucher.manage.dao.RoomInfoDao;
import com.voucher.manage.daoModel.TTT.ChartInfo;
import com.voucher.manage.daoModel.TTT.PreMessage;
import com.voucher.manage.model.Sellers;
import com.voucher.manage.model.User_Asset;
import com.voucher.manage.model.Users;

import com.voucher.manage.service.SellerService;
import com.voucher.manage.service.UserService;
import com.voucher.manage.singleton.Singleton;
import com.voucher.manage.tools.Constants;
import com.voucher.manage.tools.IdcardUtil;
import com.voucher.manage.tools.Md5;
import com.voucher.manage.tools.MyTestUtil;
import com.voucher.manage.tools.verifycode.Captcha;
import com.voucher.manage.tools.verifycode.SpecCaptcha;
import com.voucher.sqlserver.context.Connect;
import com.voucher.weixin.controller.WechatSendMessageController;

import common.HttpClient;

@Controller
@RequestMapping("/mobile/assetRegister")
public class AssetUserRegisterController {

	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	ApplicationContext applicationContext = new Connect().get();

	AssetsDAO assetsDAO = (AssetsDAO) applicationContext.getBean("assetsdao");

	RoomInfoDao roomInfoDao = (RoomInfoDao) applicationContext.getBean("roomInfodao");

	/*
	 * 生成验证码类
	 */
	@RequestMapping(value = "getYzm", method = RequestMethod.GET)
	public void getYzm(HttpServletResponse response, HttpServletRequest request) {
		String verifyCode;
		try {
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("image/jpeg");

			// 生成随机字串
			Captcha captcha = new SpecCaptcha(120, 25, 4);

			// 生成图片
			captcha.out(response.getOutputStream());
			verifyCode = captcha.text().toLowerCase();
			HttpSession session = request.getSession();
			session.setAttribute("verifyCode", verifyCode);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * @param wr
	 * 验证姓名
	 * @return
	 */
	@RequestMapping("testName")
	public @ResponseBody Map<String, Object> testName(@RequestParam String name) {
		Map<String, Object> map = new HashMap<>();

		int repeat = userService.selectRepeatUser(name);

		if (name.equals("")) {
			map.put("data", "用户名不能空");
			return map;
		}

		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(name);

		if (m.find()) {
			map.put("data", "用户名含有非法字符");
			return map;
		}else {
			Integer count = assetsDAO.selectUserName(name);
			if(count>0) {
				map.put("data", "succeed");
			}else {
				map.put("data", "没有该用户");
			}
		}
		return map;
	}

	@RequestMapping("testIDNo")
	public @ResponseBody Map<String, Object> testIDNo(@RequestParam String IDNo) {
		Map<String, Object> map = new HashMap<>();

		if (IDNo.equals("")) {
			map.put("data", "身份证号码不能空");
			return map;
		}

		// if(IdcardUtil.isIdcard(IDNo)) {
		if (true) {
			map.put("data", "succeed");
			return map;
		} else {
			map.put("data", "false");
			return map;
		}

	}

	/**
	 * 电话号码验证
	 * 电话号码与姓名一起查询
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isPhone(String str) {
		Pattern p1 = null, p2 = null;
		Matcher m = null;
		if (str.length() != 11) {
			return false;
		}
		// return b;
		return true;
	}

	@RequestMapping("/testPhone")
	public @ResponseBody Map<String, Object> testPhone(@RequestParam String name, @RequestParam String telephone) {
		Map<String, Object> map = new HashMap<>();

		if (telephone.equals("")) {
			map.put("data", "手机号码不能空");
			return map;
		}

		if (!isPhone(telephone)) {
			map.put("data", "请输入正确的手机号码");
			return map;
		}else {
			Integer count = assetsDAO.selectUserPhone(name,telephone);
			if (count>0) {
				map.put("data", "succeed");
			}else {
				map.put("data", "请输入正确的手机号码");
			}
		}
		return map;
	}

	// 生成短信验证码
	@RequestMapping(value = "getValidate", method = RequestMethod.GET)
	public @ResponseBody Integer getValidate(@RequestParam String phone, @RequestParam String name,
			HttpServletResponse response, HttpServletRequest request) {

		PreMessage preMessage = new PreMessage();

		HttpSession session = request.getSession();

		String openId = session.getAttribute("openId").toString();

		HttpClient httpClient = new HttpClient();

		String requestUrl = "http://utf8.api.smschinese.cn";

		Map searchMap = new HashMap<>();

		searchMap.put("Charter like ", "%" + name.trim() + "%");

		Map useMap = assetsDAO.getAllChartInfo(1, 0, "", "", searchMap);

		try {
			List<ChartInfo> chartInfoList = (List<ChartInfo>) useMap.get("rows");
			ChartInfo chartInfo = chartInfoList.get(0);
			if (chartInfo == null) {
				return 2;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 2;
		}

		String vcode = "";
		for (int i = 0; i < 6; i++) {
			vcode = vcode + (int) (Math.random() * 9);
		}

		String Message = "您正在进行手机验证,验证码是 : " + vcode + " , 5分钟内有效";

		System.out.println("phone=" + phone);
		System.out.println("Message=" + Message);

		WechatSendMessageController wechatSendMessageController = new WechatSendMessageController();

		int i = wechatSendMessageController.sendPhoneMessage(phone, Message, name, openId);

		System.out.println("i=" + i);

		if (i > 0) {
			LinkedHashMap<String, Map<String, Object>> linkMap = Singleton.getInstance().getRegisterMap();
			Map<String, Object> map = new HashMap<>();
			map.put("vcode", vcode);
			map.put("startTime", new Date());
			linkMap.put(phone, map);
			preMessage.setState("发送成功");
		} else {
			preMessage.setState("发送失败");
		}

		return i;
	}

	@RequestMapping("insert")
	public @ResponseBody Integer insert(HttpServletRequest request, @RequestParam String name,
			@RequestParam String phone, @RequestParam String regtlx) {

		HttpSession session = request.getSession();
		String openId = null;

		try {
			openId = session.getAttribute("openId").toString();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		if (regtlx.equals("")) {
			return 2;
		}

		try {

			regtlx = regtlx.toLowerCase();
			LinkedHashMap<String, Map<String, Object>> linkMap = Singleton.getInstance().getRegisterMap();
			Map<String, Object> map = linkMap.get(phone);

			if (map == null || map.isEmpty()) {
				return 3;
			}

			String verifyCode = (String) map.get("vcode");

			System.out.println("regtlx=" + regtlx + "      verifyCode=" + verifyCode);

			if (!regtlx.equals(verifyCode)) {
				verifyCode = null;
				return 2;
			}

			Date upTime = new Date();

			User_Asset user_asset = new User_Asset();

			user_asset.setOpenId(openId);
			if (!name.equals(""))
				user_asset.setCharter(name);
			if (!phone.equals(""))
				user_asset.setHirePhone(phone);

			int testRepeat = userService.getCountUser_AssetByOpenId(openId);

			int type;

			if (testRepeat == 1) {
				type = userService.updateUser_AssetByOpenId(user_asset);
			} else {
				type = userService.insertIntoUser_AssetByOpenId(user_asset);
			}

			return type;

		} catch (Exception e) {
			e.printStackTrace();

			return 3;
		}
	}

	@RequestMapping("/userAssetByopenId")
	public @ResponseBody User_Asset userAssetByopenId(HttpServletRequest request, @RequestParam Integer campusId) {
		HttpSession session = request.getSession();
		String openId = null;
		try {
			openId = session.getAttribute("openId").toString();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return userService.selectUser_AssetByOpenId(openId);
	}
}
