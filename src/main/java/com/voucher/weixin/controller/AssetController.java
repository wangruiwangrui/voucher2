package com.voucher.weixin.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.sql.ast.statement.SQLIfStatement.Else;
import com.alibaba.fastjson.JSONObject;
import com.rmi.server.entity.ImageData;
import com.voucher.manage.dao.AssetsDAO;
import com.voucher.manage.dao.MobileDAO;
import com.voucher.manage.dao.RoomInfoDao;
import com.voucher.manage.daoModel.RoomInfo;
import com.voucher.manage.daoModel.Assets.Patrol_Cycle;
import com.voucher.manage.daoModel.Assets.Position;
import com.voucher.manage.daoModel.TTT.ChartInfo;
import com.voucher.manage.daoModel.TTT.PreMessage;
import com.voucher.manage.daoModel.TTT.User_AccessTime;
import com.voucher.manage.daoModelJoin.RoomInfo_Position;
import com.voucher.manage.model.Access;
import com.voucher.manage.model.Campus;
import com.voucher.manage.model.Users;
import com.voucher.manage.model.WeiXin;
import com.voucher.manage.service.CampusService;
import com.voucher.manage.service.PhotoService;
import com.voucher.manage.service.UserService;
import com.voucher.manage.service.WeiXinService;
import com.voucher.manage.singleton.Singleton;
import com.voucher.manage.tools.MyTestUtil;
import com.voucher.sqlserver.context.Connect;
import com.voucher.weixin.insweptcontroller.FileUploadController;

import common.HttpClient;

@Controller
@RequestMapping("/mobile/asset")
public class AssetController {

	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	
	private CampusService campusService;
	  
	@Autowired 
	public void setCampusService(CampusService campusService) {
	this.campusService = campusService; }
	 

	private WeiXinService weixinService;

	private PhotoService photoService;

	@Autowired
	public void setweixinService(WeiXinService weiXinService) {
		this.weixinService = weiXinService;
	}

	@Autowired
	public void setPhotoService(PhotoService photoService) {
		this.photoService = photoService;
	}

	ApplicationContext applicationContext = new Connect().get();

	RoomInfoDao roomInfoDao = (RoomInfoDao) applicationContext.getBean("roomInfodao");

	AssetsDAO assetsDAO = (AssetsDAO) applicationContext.getBean("assetsdao");

	MobileDAO mobileDao = (MobileDAO) applicationContext.getBean("mobileDao");

	@RequestMapping("/getAll")
	public @ResponseBody Map<String, Object> RoomInfo(@RequestParam Integer limit, @RequestParam Integer offset,
			String sort, String order, String search, Integer search2, Integer search3, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();

		String term = "OR";

		if (sort != null && sort.equals("buildArea")) {
			sort = "BuildArea";
		} else if (sort != null && sort.equals("address")) {
			sort = "Address";
		} else {
			sort = "Num";
		}

		if (order != null && order.equals("asc")) {
			order = "asc";
		} else if (order != null && order.equals("desc")) {
			order = "desc";
		} else {
			order = "asc";
		}

		Map where = new HashMap<>();

		if (search != null && !search.trim().equals("")) {
			search = "%" + search + "%";
			where.put("Address like ", search);
			where.put("Num like ", search);
		}

		// 设置有搜索条件并且时间为空时的查询条件
		String searchTerm = "";

		if (search2 != null) {

			term = "and";

			Calendar cal = Calendar.getInstance();
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			String startTime = null;

			startTime = sdf.format(cal.getTime());

			where = new HashMap<>();

			if (search != null && !search.trim().equals("")) {
				where.put("Address like ", search);
				searchTerm = "Address like '" + search + "'";
			}

			if (search2 == 0) {

				where.put(
						"convert(varchar(11)," + Singleton.ROOMDATABASE + ".[dbo].[RoomInfo].hidden_check_date ,120 )<",
						startTime);

				// 用于查询datatime等于空值
				Map roomInfo_Positions = assetsDAO.findAllRoomInfoCheckDateNULL(limit, offset, sort, order, term,
						searchTerm, where, 1);

				List roominfos = (List) roomInfo_Positions.get("rows");
				int total = (int) roomInfo_Positions.get("total");

				map.put("rows", roominfos);
				map.put("total", total);

				Map fileBytes = mobileDao.roomInfo_PositionImageQuery(request, roominfos);
				map.put("fileBytes", fileBytes);

				return map;

			} else if (search2 == 1) {
				where.put(Singleton.ROOMDATABASE + ".[dbo].[RoomInfo].hidden_check_date >", startTime);
			}
		}

		if (search3 != null) {
			Calendar cal = Calendar.getInstance();
			int m = cal.get(Calendar.MONTH) % 2;
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
			if (m != 0) {
				cal.add(Calendar.MONTH, -1);
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			String startTime = null;

			startTime = sdf.format(cal.getTime());

			where = new HashMap<>();

			if (search != null && !search.trim().equals("")) {
				where.put("Address like ", search);
				searchTerm = "Address like '" + search + "'";
			}

			if (search3 == 0) {

				where.put(
						"convert(varchar(11)," + Singleton.ROOMDATABASE + ".[dbo].[RoomInfo].asset_check_date ,120 )<",
						startTime);

				// 用于查询datatime等于空值
				Map roomInfo_Positions = assetsDAO.findAllRoomInfoCheckDateNULL(limit, offset, sort, order, term,
						searchTerm, where, 2);

				List roominfos = (List) roomInfo_Positions.get("rows");
				int total = (int) roomInfo_Positions.get("total");

				map.put("rows", roominfos);
				map.put("total", total);

				Map fileBytes = mobileDao.roomInfo_PositionImageQuery(request, roominfos);
				map.put("fileBytes", fileBytes);

				return map;

			} else if (search3 == 1) {
				where.put(Singleton.ROOMDATABASE + ".[dbo].[RoomInfo].asset_check_date >", startTime);
			}
		}

		Map roomInfo_Positions = assetsDAO.findAllRoomInfo_Position(limit, offset, sort, order, term, where);

		List roominfos = (List) roomInfo_Positions.get("rows");
		int total = (int) roomInfo_Positions.get("total");

		map.put("rows", roominfos);
		map.put("total", total);

		Map fileBytes = mobileDao.roomInfo_PositionImageQuery(request, roominfos);
		map.put("fileBytes", fileBytes);

		MyTestUtil.print(fileBytes);

		return map;
	}
	
	@RequestMapping("/upUserAccessTime")
	public void upUserAccessTime(@RequestParam String parameter,HttpServletRequest request,
			HttpServletResponse response){
		
		String openId=( String ) request.getSession().getAttribute("openId");
		
		Date date=new Date();
		
		User_AccessTime user_AccessTime=new User_AccessTime();
		user_AccessTime.setOpen_id(openId);
		String[] where = {"open_id=",openId};
		user_AccessTime.setWhere(where);
		
		int count;
		
		User_AccessTime user_AccessTime2=null;
		
		try{
			List list=assetsDAO.selectUserAccessTime(openId);	
			user_AccessTime2=(User_AccessTime) list.get(0);
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		if(parameter.equals("first")){
			user_AccessTime.setFirst(date);
			if(user_AccessTime2!=null){
				count=user_AccessTime2.getFirst_count()+1;
			}else{
				count=1;
			}
			user_AccessTime.setFirst_count(count);
		}else if(parameter.equals("finance")){
			user_AccessTime.setFinance(date);
			if(user_AccessTime2!=null){
				count=user_AccessTime2.getFinance_count()+1;
			}else{
				count=1;
			}
			user_AccessTime.setFinance_count(count);
		}else if(parameter.equals("matureChartInfo")){
			user_AccessTime.setMatureChartInfo(date);
			if(user_AccessTime2!=null){
				count=user_AccessTime2.getMatureCharInfo_count()+1;
			}else{
				count=1;
			}
			user_AccessTime.setMatureCharInfo_count(count);
		}else if(parameter.equals("overdueChartInfo")){
			user_AccessTime.setOverdueChartInfo(date);
			if(user_AccessTime2!=null){
				count=user_AccessTime2.getOverdueChartInfo_count()+1;
			}else{
				count=1;
			}
			user_AccessTime.setOverdueChartInfo_count(count);
		}
		
		int i=assetsDAO.upUserAccessTime(user_AccessTime);
		
		if(i==0){
			assetsDAO.insertUserAccessTime(user_AccessTime);
			assetsDAO.upUserAccessTime(user_AccessTime);
		}
		
	}

	@RequestMapping("/getAll2")
	public @ResponseBody Map<String, Object> RoomInfo2(@RequestParam Integer limit, @RequestParam Integer offset,
			String sort, String order, String search, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();

		String term = "OR";

		if (sort != null && sort.equals("buildArea")) {
			sort = "BuildArea";
		}

		if (sort != null && sort.equals("address")) {
			sort = "Address";
		}
		if (order != null && order.equals("asc")) {
			order = "asc";
		}

		if (order != null && order.equals("desc")) {
			order = "desc";
		}

		Map where = new HashMap<>();

		where.put("[RoomInfo].State !=", "已划拨");
		where.put("[RoomInfo].State= ", "空置");

		if (search != null && !search.trim().equals("")) {
			search = "%" + search + "%";
			where.put("Address like ", search);
		}

		// Map roomInfo_Positions=assetsDAO.findAllRoomInfo_Position(limit, offset,
		// sort, order,term, where);

		// List roominfos=(List) roomInfo_Positions.get("rows");

		List roominfos = roomInfoDao.findAllRoomInfo_Position(limit, offset, sort, order, where);

		map.put("rows", roominfos);

		Map fileBytes = mobileDao.roomInfo_PositionImageQuery(request, roominfos);
		map.put("fileBytes", fileBytes);

		MyTestUtil.print(fileBytes);

		return map;
	}

	@RequestMapping("/getAll3")
	public @ResponseBody Map<String, Object> RoomInfo3(@RequestParam Integer limit, @RequestParam Integer offset,
			String sort, String order, String search, String search2, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();

		String term = "OR";

		if (sort != null && sort.equals("buildArea")) {
			sort = "BuildArea";
		}

		if (sort != null && sort.equals("address")) {
			sort = "Address";
		}
		if (order != null && order.equals("asc")) {
			order = "asc";
		}

		if (order != null && order.equals("desc")) {
			order = "desc";
		}

		Map where = new HashMap<>();

		where.put("[RoomInfo].State !=", "已划拨");
		where.put("[RoomInfo].IsHidden > ", "0");

		if (search2 != null && !search2.equals("")) {
			where.put("[RoomInfo].neaten_flow > ", "0");
		}

		if (search != null && !search.trim().equals("")) {
			search = "%" + search + "%";
			where.put("Address like ", search);
		}

		// Map roomInfo_Positions=assetsDAO.findAllRoomInfo_Position(limit, offset,
		// sort, order,term, where);

		// List roominfos=(List) roomInfo_Positions.get("rows");

		List roominfos = roomInfoDao.findAllRoomInfo_Position(limit, offset, sort, order, where);
		int total = roomInfoDao.getRoomInfoCount(where);
		map.put("rows", roominfos);
		map.put("total", total);

		Map fileBytes = mobileDao.roomInfo_PositionImageQuery(request, roominfos);
		map.put("fileBytes", fileBytes);

		MyTestUtil.print(fileBytes);

		return map;
	}

	@RequestMapping("/getNotPlace")
	public @ResponseBody Map<String, Object> notPlaceRoomInfo(@RequestParam Integer limit, @RequestParam Integer offset,
			String sort, String order, String search, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (sort != null && sort.equals("buildArea")) {
			sort = "BuildArea";
		}

		if (sort != null && sort.equals("address")) {
			sort = "Address";
		}
		if (order != null && order.equals("asc")) {
			order = "asc";
		}

		if (order != null && order.equals("desc")) {
			order = "desc";
		}

		Map where = new HashMap<>();

		where.put("[RoomInfo].State !=", "已划拨");

		if (search != null && !search.trim().equals("")) {
			search = "%" + search + "%";
			where.put("Address like ", search);
		}

		/*
		 * List<RoomInfo> roomInfos=roomInfoDao.findAllRoomInfo(limit,offset,sort,
		 * order,where);
		 * System.out.println("roominfocontroller sort="+sort+"   order="+order);
		 */

		Map roomInfo_Positions = roomInfoDao.notPlaceRoomInfo(limit, offset, search);

		List roominfos = (List) roomInfo_Positions.get("rows");

		map.put("rows", roominfos);

		Map fileBytes = mobileDao.roomInfo_PositionImageQuery(request, roominfos);
		map.put("fileBytes", fileBytes);

		int total = (int) roomInfo_Positions.get("total");
		map.put("total", total);

		// MyTestUtil.print(fileBytes);

		return map;
	}

	@RequestMapping("/getRoomInfoByGUID")
	public @ResponseBody Map<String, Object> getRoomInfoByGUID(@RequestParam String guid, HttpServletRequest request) {
		Map searchMap = new HashMap<>();
		searchMap.put("[RoomInfo].GUID = ", guid);

		String term = "AND";

		Map map = new HashMap<>();

		try {
			List<RoomInfo_Position> roomInfo_Positions = (List<RoomInfo_Position>) assetsDAO
					.findAllRoomInfo_Position(1, 0, null, null, term, searchMap).get("rows");

			RoomInfo_Position roomInfo_Position = roomInfo_Positions.get(0);

			map.put("roomInfo", roomInfo_Position);

			RoomInfo roomInfo = new RoomInfo();

			roomInfo.setGUID(guid);

			List fileBytes = mobileDao.allRoomInfoImageByGUID(request, roomInfo);

			map.put("fileBytes", fileBytes);

			List hiddenCheckFileBytes = mobileDao.allRoomHiddenCheckImageByGUID(request, guid);

			map.put("hiddenCheckFileBytes", hiddenCheckFileBytes);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return map;

	}

	@RequestMapping("/getRoomInfoForGUID")
	public @ResponseBody Map<String, Object> getRoomInfoForGUID(@RequestParam String guid, HttpServletRequest request) {
		Map searchMap = new HashMap<>();
		searchMap.put("[RoomInfo].GUID = ", guid);

		List<RoomInfo> roomInfos = roomInfoDao.findAllRoomInfo(2, 0, null, null, searchMap);

		RoomInfo roomInfo = roomInfos.get(0);

		Map map = new HashMap<>();

		map.put("roomInfo", roomInfo);

		return map;

	}

	@RequestMapping("/getChartInfoByGUID")
	public @ResponseBody Map getChartInfoByGUID(@RequestParam Integer limit, @RequestParam Integer offset, String sort,
			String order, String search, HttpServletRequest request) {

		String openId = (String) request.getSession().getAttribute("openId");

		Users users = userService.getUserByOnlyOpenId(openId);

		String Charter = users.getCharter();
		String phone = users.getHirePhone();

		Map searchMap = new HashMap<>();

		searchMap.put("ChartInfo.Phone = ", phone.trim());
		searchMap.put("ChartInfo.Charter like ", "%" + Charter.trim() + "%");

		Map map = roomInfoDao.getChartInfoByGUID(limit, offset, sort, order, searchMap);

		return map;

	}
	
	@RequestMapping("/getChartByGUID")
	public @ResponseBody Map<String,String> getChartByGUID(HttpServletRequest request) {
		
		String openId = (String) request.getSession().getAttribute("openId");

		Users users = userService.getUserByOnlyOpenId(openId);

		String Charter = users.getCharter();
		String phone = users.getHirePhone();
		
		Map<String,String> map = new HashMap<String,String>();
			
		map.put("Charter", Charter);
		
		map.put("phone", phone);
		return map;

	}

	@RequestMapping("/getHireListByGUID")
	public @ResponseBody Map getHireListByGUID(@RequestParam Integer limit, @RequestParam Integer offset,
			@RequestParam String hireGUID, String sort, String order, String search, HttpServletRequest request) {
		if (sort == "" || sort.equals("")) {
			sort = "HireDate";
		}

		if (order == "" || order.equals("")) {
			order = "asc";
		}
		sort = sort.replaceAll("^,*|,*$", "");
		order = order.replaceAll("^,*|,*$", "");
		System.out.println("sort ="+sort+"       order="+order);
		
		Map searchMap = new HashMap<>();

		searchMap.put("[HireList].HireGUID=", hireGUID);

		Map map = roomInfoDao.getHireListByGUID(limit, offset, sort, order, searchMap);

		return map;

	}

	@RequestMapping("/getAllHire")
	public @ResponseBody Map getAllHire() {

		Double totalHire = roomInfoDao.getAllTotalHire();

		Double alreadyHire = roomInfoDao.getAlreadyHire();

		Double notHire = roomInfoDao.getNotHire();

		Map map = new HashMap<>();

		DecimalFormat df = new DecimalFormat("0.00");

		map.put("totalHire", df.format(totalHire / 10000));

		map.put("alreadyHire", df.format(alreadyHire / 10000));

		map.put("notHire", df.format(notHire / 10000));

		return map;

	}

	@RequestMapping("/getAssetByState")
	public @ResponseBody Map getAssetByState() {

		Map search = new HashMap<>();

		search.put(Singleton.ROOMDATABASE + ".[dbo].[RoomInfo].State=", "已出租");

		int alreadyHire = roomInfoDao.getRoomInfoCount(search);

		search = new HashMap<>();

		search.put(Singleton.ROOMDATABASE + ".[dbo].[RoomInfo].State=", "空置");

		int notHire = roomInfoDao.getRoomInfoCount(search);

		search = new HashMap<>();

		search.put(Singleton.ROOMDATABASE + ".[dbo].[RoomInfo].State=", "不可出租");

		int catnotHire = roomInfoDao.getRoomInfoCount(search);

		int total = alreadyHire + notHire + catnotHire;

		Map map = new HashMap<>();

		map.put("alreadyHire", alreadyHire);

		map.put("notHire", notHire);

		map.put("catnotHire", catnotHire);

		map.put("total", total);

		return map;

	}

	@RequestMapping("/getAssetByDangerClass")
	public @ResponseBody Map getAssetByDangerClass() {

		Map search = new HashMap<>();

		search.put(Singleton.ROOMDATABASE + ".[dbo].[RoomInfo].State !=", "已划拨");

		search.put(Singleton.ROOMDATABASE + ".[dbo].[RoomInfo].DangerClassification !=", "D级");

		int normal = roomInfoDao.getRoomInfoCount(search);

		search = new HashMap<>();

		search.put(Singleton.ROOMDATABASE + ".[dbo].[RoomInfo].State !=", "已划拨");

		search.put(Singleton.ROOMDATABASE + ".[dbo].[RoomInfo].DangerClassification=", "D级");

		int danger = roomInfoDao.getRoomInfoCount(search);

		int total = normal + danger;

		Map map = new HashMap<>();

		map.put("normal", normal);

		map.put("danger", danger);

		map.put("total", total);

		return map;
	}

	@RequestMapping("/findChartInfoByYear")
	public @ResponseBody List findChartInfoByYear() {

		return roomInfoDao.findChartInfoByYear();

	}

	@RequestMapping("/findHireListByYear")
	public @ResponseBody List findHireListByYear() {

		return roomInfoDao.findHireListByYear();

	}

	@RequestMapping("/getChartInfoByMonthOfYear")
	public @ResponseBody List getChartInfoByMonthOfYear(@RequestParam String year) {

		List list = roomInfoDao.findChartInfoByMonthOfYear(year);

		Iterator iterator = list.iterator();

		List chartByMonth = new ArrayList<>();

		DecimalFormat df = new DecimalFormat("0.00");

		while (iterator.hasNext()) {
			String month = (String) iterator.next();

			Double monthHire = roomInfoDao.getTotalHireByMonth(month);

			Map map = new HashMap<>();

			map.put("month", month);

			map.put("monthHire", df.format(monthHire / 10000));

			chartByMonth.add(map);
		}

		return chartByMonth;
	}

	@RequestMapping("/getHireListByMonthOfYear")
	public @ResponseBody List getHireListByMonthOfYear(@RequestParam String year) {
		System.out.println("year=" + year);
		List list = roomInfoDao.findHireListByMonthOfYear(year);

		Iterator iterator = list.iterator();

		List hireByMonth = new ArrayList<>();

		DecimalFormat df = new DecimalFormat("0.00");

		while (iterator.hasNext()) {
			String month = (String) iterator.next();

			Double nothireMonth = roomInfoDao.getNotHireByMonth(month);

			Double alreadyhireMonth = roomInfoDao.getAlreadyHireByMonth(month);

			Map map = new HashMap<>();

			map.put("month", month);

			map.put("nothireMonth", df.format(nothireMonth / 10000));

			map.put("alreadyhireMonth", df.format(alreadyhireMonth / 10000));

			hireByMonth.add(map);
		}

		System.out.println("hireByMonth");
		MyTestUtil.print(hireByMonth);

		return hireByMonth;
	}

	@RequestMapping("/getAssetByHidden")
	public @ResponseBody Map getAssetByHidden(@RequestParam Integer limit, @RequestParam Integer offset, String sort,
			String order, @RequestParam String hiddenGuid, HttpServletRequest request,HttpServletResponse resp) {

		resp.setContentType("text/json; charset=utf-8");
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Max-Age", "3600");
		resp.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept");
		resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		
		Map searchMap = new HashMap<>();

		searchMap.put("[RoomInfo].GUID=", hiddenGuid);

		Map map = new HashMap<>();

		Map hidden_Assets_JoinMap = assetsDAO.findAssetByHideen(limit, offset, sort, order, searchMap);

		List hidden_Assets_Joins = (List) hidden_Assets_JoinMap.get("rows");

		map.put("rows", hidden_Assets_Joins);

		String detail = new HiddenController().getRoomInfoHiddenItemDataByGUID(hiddenGuid);

		map.put("detail", detail);

		Iterator<RoomInfo_Position> iterator = hidden_Assets_Joins.iterator();

		List roominfos = new ArrayList<>();

		while (iterator.hasNext()) {

			roominfos.add(iterator.next());
		}

		Map fileBytes = mobileDao.roomInfoImageQuery(request, roominfos);
		map.put("fileBytes", fileBytes);

		return map;

	}

	@RequestMapping("/getAllAssetByHidden_GUID")
	public @ResponseBody Integer getAllAssetByHidden_GUID(@RequestParam String guid) {

		int count = assetsDAO.getAllAssetByHidden_GUID(guid);

		return count;

	}

	@RequestMapping("/getChartInfoByChartGUID")
	public @ResponseBody Map<String, Object> getChartInfoByChartGUID(@RequestParam String chartGUID,
			HttpServletRequest request) {

		Map map = new HashMap<>();

		ChartInfo chartInfo = assetsDAO.getChartInfoByChartGUID(chartGUID);

		map.put("chartInfo", chartInfo);

		return map;

	}

	@RequestMapping("/selectManageRegion")
	public @ResponseBody List selectHiddenLevel() {
		return assetsDAO.selectManageRegion();
	}

	@RequestMapping("/getWetchatAllUsers")
	public @ResponseBody List getWetchatAllUsers(HttpServletResponse resp) {
		resp.setContentType("text/json; charset=utf-8");
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Max-Age", "3600");
		resp.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept");
		resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		return userService.getWetchatAllUsers(1, 1, null, null, null, null);
	}

	@RequestMapping("/updatePositionByRoomInfo")
	public @ResponseBody Integer updatePositionByRoomInfo(HttpServletRequest request, ServletResponse response,
			@RequestParam String imagename, @RequestParam String serverId, @RequestParam Integer campusId,
			@RequestParam String id, @RequestParam String classType, @RequestParam String GUID,
			@RequestParam Double lng, @RequestParam Double lat, @RequestParam Double wgs84_lng,
			@RequestParam Double wgs84_lat, @RequestParam String addComp) {

		int upload = 0;

		/*
		 * String
		 * url="http://"+request.getServerName()+"/voucher/mobile/file/upload.do";
		 * 
		 * System.out.println("url="+url);
		 * 
		 * List<BasicNameValuePair> reqParam = new ArrayList<BasicNameValuePair>();
		 * 
		 * reqParam.add(new BasicNameValuePair("imagename", imagename));
		 * reqParam.add(new BasicNameValuePair("serverId", serverId)); reqParam.add(new
		 * BasicNameValuePair("campusId", String.valueOf(campusId))); reqParam.add(new
		 * BasicNameValuePair("id", id)); reqParam.add(new
		 * BasicNameValuePair("classType", classType));
		 * 
		 * HttpClient httpClient = new HttpClient();
		 * 
		 * upload=Integer.parseInt(httpClient.doGet(url, reqParam));
		 */

		try {
			upload = FileUploadController.fildUpload2(request, response, imagename, serverId, campusId, GUID, classType,
					weixinService, photoService);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (upload == 1) {

			boolean isUpdate = false; // 如果有位置就不更新

			Position position = new Position();
			position.setGUID(GUID);
			position.setLat(lat);
			position.setLng(lng);
			position.setWgs84_lat(wgs84_lat);
			position.setWgs84_lng(wgs84_lng);

			try {

				JSONObject jsonObject = JSONObject.parseObject(addComp);

				String province = jsonObject.getString("province");
				String city = jsonObject.getString("city");
				String district = jsonObject.getString("district");
				String street = jsonObject.getString("street");
				String streetNumber = jsonObject.getString("streetNumber");
				position.setProvince(province);
				position.setCity(city);
				position.setDistrict(district);
				position.setStreet(streetNumber);
				position.setStreet_number(streetNumber);

			} catch (Exception e) {
				// TODO: handle exception
			}

			Date date = new Date();

			position.setDate(date);

			assetsDAO.updatePositionByRoomInfo(position, isUpdate);
		}

		return upload;

	}

	@RequestMapping("/insertAccess")
	public @ResponseBody Integer insertAccess(@RequestParam String page, @RequestParam Integer campusId, String guid,
			HttpServletRequest hrequest) {
		Access access = new Access();

		String openId = (String) hrequest.getSession().getAttribute("openId");

		access.setCampusId(campusId);
		access.setOpenId(openId);
		access.setPage(page);

		if (guid != null && !guid.equals("")) {
			access.setGuid(guid);
		}

		Date date = new Date();

		access.setAccessTime(date);

		return userService.insertAccess(access);
	}

	@RequestMapping("/selectAllAccess")
	public @ResponseBody Map selectAllAccess(@RequestParam Integer campusId,@RequestParam Integer limit, @RequestParam Integer offset, String sort,
			String order, String search, String page, HttpServletRequest request) {

		return userService.selectAllAccess(campusId, limit, offset, sort, order, search, page);

	}

	@RequestMapping("/getCountCheckByGUID")
	public @ResponseBody Map getCountCheckByGUID(@RequestParam String guid) {
		return assetsDAO.getCountCheckByGUID(guid);
	}

	@RequestMapping("/getRoomRepairLog")
	public @ResponseBody Map getRoomRepairLog(@RequestParam Integer limit, @RequestParam Integer offset, String sort,
			String order, String search) {

		if (sort != null && !sort.equals("")) {
		} else {
			sort = "date";
		}

		if (order != null && order.equals("asc")) {
			order = "asc";
		} else if (order != null && order.equals("desc")) {
			order = "desc";
		} else {
			order = "asc";
		}

		Map where = new HashMap<>();

		if (search != null && !search.equals("")) {
			where.put("Address like ", "%" + search + "%");
		}

		return roomInfoDao.findAllRoomRepairLog(limit, offset, sort, order, where);
	}

	@RequestMapping("/getMessageNumber")
	public @ResponseBody Integer getMessageNumber() {

		WechatSendMessageController wechatSendMessageController = new WechatSendMessageController();

		return wechatSendMessageController.getPhoneMessageNumber();

	}

	@RequestMapping("/getCompanyName")
	public @ResponseBody String getCompanyName(@RequestParam Integer campusId) {
		String companyName = weixinService.getCompanyName(campusId);
		return companyName;
	}

	@RequestMapping("/getPreMessage")
	public @ResponseBody Map getPreMessage(@RequestParam Integer limit, @RequestParam Integer offset,
			@RequestParam Integer time, String sort, String order, String search) {

		if (sort != null && !sort.equals("")) {
		} else {
			sort = "OptDate";
		}

		System.out.println("order=" + order);

		if (order != null && order.equals("asc")) {
			order = "asc";
		} else if (order != null && order.equals("desc")) {
			order = "desc";
		} else {
			order = "desc";
		}

		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		if (time == 2) {
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		}

		String startTime = null;

		startTime = sdf.format(cal.getTime());

		cal = Calendar.getInstance();

		String endTime = null;

		endTime = sdf.format(cal.getTime());

		System.out.println("startTime=" + startTime + "   endTime=" + endTime);

		Map where = new HashMap<>();

		if (time == 1) {
			where.put("convert(varchar(11)," + Singleton.ROOMDATABASE + ".[dbo].[PreMessage].OptDate ,120 ) = ",
					endTime);
		} else if (time > 1) {
			where.put("convert(varchar(11)," + Singleton.ROOMDATABASE + ".[dbo].[PreMessage].OptDate ,120 ) >= ",
					startTime);
			where.put("convert(varchar(11)," + Singleton.ROOMDATABASE + ".[dbo].[PreMessage].OptDate ,120 ) <= ",
					endTime);
		}

		if (search != null && !search.equals("")) {
			where.put(Singleton.ROOMDATABASE + ".[dbo].[PreMessage].PhoneWho like ", "%" + search + "%");
		}

		return roomInfoDao.findAllPreMessage(limit, offset, sort, order, where);
	}

	@RequestMapping("/sendMessage")
	public @ResponseBody String sendMessage(@RequestParam String Message, @RequestParam String numbers,
			HttpServletRequest request) {

		String openId = (String) request.getSession().getAttribute("openId");

		WechatSendMessageController wechatSendMessageController = new WechatSendMessageController();

		String arr[] = numbers.split("\\s+");// 使用正则表达式将字符串分割 “\\s+”表示多个空格
		int sum = 0;
		String result = "";
		for (String phone : arr)// 遍历所有的字符串并转换成整数求和
		{
			int i = wechatSendMessageController.sendPhoneMessage(phone, Message, null, openId);
			if (i > 0) {
				result = result + " " + phone + "发送成功";
			} else {
				result = result + " " + phone + "发送失败";
			}
		}

		return result;

	}

	@RequestMapping("/sendAllMessage")
	public @ResponseBody String sendAllMessage(@RequestParam String Message, HttpServletRequest request) {

		String openId = (String) request.getSession().getAttribute("openId");

		Users users = userService.getUserByOnlyOpenId(openId);

		List list = roomInfoDao.getAllChartInfo();

		Iterator<ChartInfo> iterator = list.iterator();

		try {

			Runnable r = new Runnable() {

				@Override
				public void run() {

					WechatSendMessageController wechatSendMessageController = new WechatSendMessageController();

					while (iterator.hasNext()) {
						ChartInfo chartInfo = iterator.next();
						if (chartInfo.getPhone() != null || !chartInfo.getPhone().equals("")) {
							// TODO Auto-generated method stub

							int i = wechatSendMessageController.sendPhoneMessage(chartInfo.getPhone(), Message,
									chartInfo.getCharter(), openId);

						}
					}

				}
			};

			Thread t = new Thread(r);
			t.start();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "发送失败";
		}

		return "已启动发送程序,请稍后在短信列表查看";

	}

	@RequestMapping(value = "selectPlace")
	public @ResponseBody Integer selectPlace(HttpServletRequest request, HttpServletResponse response) {

		String openId = (String) request.getSession().getAttribute("openId");

		Users users = userService.getUserByOnlyOpenId(openId);

		int place = users.getPlace();

		return place;
	}

	@RequestMapping("getPatrolCycle")
	public @ResponseBody Patrol_Cycle getPatrolCycle(HttpServletRequest request){
		
		return assetsDAO.selectPatrolCycle();
		
	}
	
	@RequestMapping("getPatrolCycleH")
	public @ResponseBody Map getPatrolCycleH(HttpServletRequest request){
		
		Patrol_Cycle patrol_Cycle=assetsDAO.selectPatrolCycle();
		
		int cycle=1;
		
		if(patrol_Cycle!=null)
			cycle=patrol_Cycle.getHidden_cycle();
		
		Calendar cal = Calendar.getInstance();  
		int start=cal.get(Calendar.MONTH)+1;
		
		int m=cal.get(Calendar.MONTH)%cycle;
		
        Map map=new HashMap<>();
        
        if(cycle!=1){
        	if(cycle==2){
        		if(m!=0){
					map.put("start", start-1);
					map.put("end", start);
        		}else{
        			map.put("start", start);
					map.put("end", start+1);
        		}
        	}else{
        		int i=1;
        		int r=start-cycle;
        		while(r>0&&r>cycle){
        			r=r-cycle;
        			i++;
        		}

        		System.out.println("start===="+start+"  r="+r+"  cycle="+cycle+"  i="+i);
        		if(cycle==12) {
        			
        			map.put("start", 1);
            		map.put("end",  cycle);
        		}else {
	        		map.put("start", i*cycle+1);
	        		map.put("end", (i+1)*cycle);
        		}
        	}
        }else{
        	map.put("start", start);
        	map.put("end", start);
        }
        
        return map;
        
	}
	
	@RequestMapping("getPatrolCycleA")
	public @ResponseBody Map getPatrolCycleA(HttpServletRequest request){
		
		Patrol_Cycle patrol_Cycle=assetsDAO.selectPatrolCycle();
		
		int cycle=1;
		
		if(patrol_Cycle!=null)
			cycle=patrol_Cycle.getAsset_cycle();
		
		Calendar cal = Calendar.getInstance();  
		int start=cal.get(Calendar.MONTH)+1;
		
		System.out.print("start="+start);
		
		int m=cal.get(Calendar.MONTH)%cycle;
		
        Map map=new HashMap<>();
        
        
        
        if(cycle!=1){
        	if(cycle==2){
        		if(m!=0){
					map.put("start", start-1);
					map.put("end", start);
        		}else{
        			map.put("start", start);
					map.put("end", start+1);
        		}
        	}else{
        		int i=1;
        		int r=start-cycle;
        		while(r>0&&r>cycle){
        			r=r-cycle;
        			i++;
        		}
        		if(cycle==12) {
        			map.put("start", 1);
            		map.put("end",  cycle);
        		}else {
	        		map.put("start", i*cycle+1);
	        		map.put("end", (i+1)*cycle);
        		}
        	}
        }else{
        	map.put("start", start);
        	map.put("end", start);
        }
        
        return map;
		
	}
	
	@RequestMapping(value="/findimagedata")
	public @ResponseBody List findImageData(HttpServletRequest request,@RequestParam String id){
		
		LinkedHashMap<String, List<ImageData>> imageDataMap=Singleton.getInstance().getImageDataMap();
    	List<ImageData> imageDataList=new ArrayList<>();
    	try{
    		imageDataList=imageDataMap.get(id);
    	}catch (Exception e) {
			// TODO: handle exception
    		e.printStackTrace();
    		
		}
    	
    	return mobileDao.flowImageData(request, imageDataList);

	}
	
}
