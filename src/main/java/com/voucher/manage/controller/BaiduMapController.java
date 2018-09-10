package com.voucher.manage.controller;

import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.voucher.manage.dao.AssetCheckDAO;
import com.voucher.manage.dao.AssetsDAO;
import com.voucher.manage.dao.HiddenDAO;
import com.voucher.manage.dao.MobileDAO;
import com.voucher.manage.dao.RoomInfoDao;
import com.voucher.manage.daoModel.RoomInfo;
import com.voucher.manage.daoModel.Assets.Position;
import com.voucher.manage.daoModel.TTT.ChartInfo;
import com.voucher.manage.daoModelJoin.RoomInfo_Position;
import com.voucher.manage.daoModelJoin.Assets.Hidden_Check_Join;
import com.voucher.manage.mapper.WeiXinMapper;
import com.voucher.manage.model.Users;
import com.voucher.manage.service.UserService;
import com.voucher.manage.singleton.Singleton;
import com.voucher.manage.tools.MyTestUtil;
import com.voucher.manage.tools.TestDistance;
import com.voucher.sqlserver.context.Connect;

import voucher.Mybatis;

@Controller
@RequestMapping("/baiduMap")
public class BaiduMapController {

	ApplicationContext applicationContext=new Connect().get();
	
	HiddenDAO hiddenDAO=(HiddenDAO) applicationContext.getBean("hiddenDao");
	
	AssetsDAO assetsDAO=(AssetsDAO) applicationContext.getBean("assetsdao");
	
	MobileDAO mobileDao=(MobileDAO) applicationContext.getBean("mobileDao");
	
	RoomInfoDao roomInfoDao=(RoomInfoDao) applicationContext.getBean("roomInfodao");
	
	AssetCheckDAO assetCheckDAO=(AssetCheckDAO) applicationContext.getBean("assetCheckdao");
	
	private UserService userService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping("/get")
	public @ResponseBody List test(String manageRegion) {		
		
		Map map;
		if(manageRegion!=null){
		   map=assetsDAO.findAllPosition(manageRegion);
		}else{
		   map=assetsDAO.findAllPosition("");
		}
		
		//MyTestUtil.print(map);
		
		List list=(List) map.get("row");
		
		return list;
	}
	
	@RequestMapping("/getPosition")
	public @ResponseBody JSONObject getPosition() {
		JSONObject jsonObject=new JSONObject();
		Position position=new Position();
		position.setLimit(10);
		position.setOffset(0);		
		position.setSort("date");
		position.setOrder("desc");
		position.setNotIn("id");
        		
		try{
			position=(Position) assetsDAO.findPosition(position).get(0);
			jsonObject.put("lat", position.getLat());
			jsonObject.put("lng", position.getLng());
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return jsonObject;
		
	}
	
	@RequestMapping("/location")
	public @ResponseBody JSONObject baiduSwitch(HttpServletRequest request){
		JSONObject jsonObject=null;
		String requestUrl = "http://api.map.baidu.com/location/ip?ak=pQFgFpS0VnMXwCRN6cTc1jDOcBVi3XoD&coor=bd09ll";
		
		HttpGet g = new HttpGet(requestUrl);
    	RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
    	CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
    	CloseableHttpResponse r;
    	String content = null;
		try {
			r = httpClient.execute(g);
			content = EntityUtils.toString(r.getEntity());
	        r.close();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		jsonObject=JSONObject.parseObject(content);
  		
		return jsonObject;
		
	}
	
	@RequestMapping("/getByDistance")
	public @ResponseBody List getByDistance(Integer limit,Integer offset,Double lng,Double lat,
			Double distance,HttpServletRequest request){
		
		Map map=assetsDAO.findHiddenByDistance(limit, offset, lng, lat, "");
		
		MyTestUtil.print(map);
		
		List list=(List) map.get("row");
		
		return list;
	}
	
	@RequestMapping("/getByPoint")
	public @ResponseBody List getByPoint(@RequestParam Integer limit,@RequestParam Integer offset,Double lng,Double lat,
			Double distance,HttpServletRequest request){
		
		Map map=hiddenDAO.selectAllHidden_Point(limit, offset, null, null, null);
		
		MyTestUtil.print(map);
		
		List list=(List) map.get("row");
		
		return list;
	}
	
	
	@RequestMapping("/getAssetsByDistance")
	public @ResponseBody Map getAssetsByDistance(Integer limit,Integer offset,Double lng,Double lat,
			Double distance,HttpServletRequest request){

		Map map=assetsDAO.findAssetByDistance(limit, offset, lng, lat,"");
	
		List list=(List) map.get("row");

		Map result=new HashMap<>();
		
		result.put("rows", list);
		
		return result;
	}
	
	@RequestMapping("/getAssetsByPoint")
	public @ResponseBody Map getAssetsByPoint(Integer limit,Integer offset,Double lng,Double lat,
			Double distance,HttpServletRequest request){

		Map map=assetsDAO.findAssetByPoint(limit,offset,lng, lat, distance, "");
	
		List list=(List) map.get("rows");

		Map result=new HashMap<>();
		
		result.put("rows", list);
		
		return result;
	}
	
	@RequestMapping("/getAssetsByDistanceImg")
	public @ResponseBody Map getAssetsByDistanceImg(Integer limit,Integer offset,Double lng,Double lat,
			Double distance,String search,String search2,HttpServletRequest request){
		System.out.println("search="+search);

		Map map;
		
		if(search==null)
			search="";
		
		System.out.println("search2="+search2);
		
		if(search!=null&&!search.equals("")){
			double d=TestDistance.get(search);
			
			System.out.println("d="+d);
			
			if(d>0&&search2==null){
				map=assetsDAO.findAssetByPoint(limit,offset,lng, lat, d, null);
			}else{
				if(search2!=null&&!search2.equals("")){
					map=assetsDAO.findAssetByDistanceDate(limit, offset, lng, lat, search,search2,1);
				}else{
					map=assetsDAO.findAssetByDistance(limit, offset, lng, lat, search);
				}
			}
			
		}else{
			if(search2!=null&&!search2.equals("")){
				map=assetsDAO.findAssetByDistanceDate(limit, offset, lng, lat, search,search2,1);
			}else{
				map=assetsDAO.findAssetByDistance(limit, offset, lng, lat, search);
			}
		}
		
		MyTestUtil.print(map);
		
		List list=(List) map.get("rows");
		int total=(int) map.get("total");
		
		Map fileBytes=mobileDao.roomInfo_PositionImageQuery(request, list);
		
		Map result=new HashMap<>();
		
		result.put("rows", list);
		result.put("total", total);
		result.put("fileBytes", fileBytes);
		
		return result;
	}
	
	@RequestMapping("/getHiddenAssetsByDistanceImg")
	public @ResponseBody Map getHiddenAssetsByDistanceImg(Integer limit,Integer offset,Double lng,Double lat,
			Double distance,String search,HttpServletRequest request){
		System.out.println("search="+search);

		Map map;
		
		if(search==null)
			search="";		
	
		map = assetsDAO.findHiddenAssetByDistance(limit, offset, lng, lat, search);
		
		MyTestUtil.print(map);
		
		List list=(List) map.get("rows");
		int total=(int) map.get("total");
		
		Map fileBytes=mobileDao.roomInfo_PositionImageQuery(request, list);
		
		Map result=new HashMap<>();
		
		result.put("rows", list);
		result.put("total", total);
		result.put("fileBytes", fileBytes);
		
		return result;
	}
	
	@RequestMapping("/getAssetsHiddenByDistanceImg")
	public @ResponseBody Map getAssetsHiddenByDistanceImg(Integer limit,Integer offset,Double lng,Double lat,
			Double distance,String search,HttpServletRequest request){
		Map map;
		
		map=assetsDAO.findAssetHiddenByDistance(limit, offset, lng, lat, search);
		
		List list=(List) map.get("rows");
		int total=(int) map.get("total");
		
		Map fileBytes=mobileDao.roomInfo_PositionImageQuery(request, list);
		
		Map result=new HashMap<>();
		
		result.put("rows", list);
		result.put("total", total);
		result.put("fileBytes", fileBytes);
		
		return result;
		
	}
	
	@RequestMapping("getManageRegion")
	public @ResponseBody List getManageRegion(){
		
		return assetsDAO.selectManageRegion();
		
	}
	
	@RequestMapping("getRoomProperty")
	public @ResponseBody List getRoomProperty(){
		
		return assetsDAO.selectRoomProperty();
		
	}
	
	@RequestMapping("getFareItem")
	public @ResponseBody List getFareItem(){
		
		return assetsDAO.selectFareItem();
		
	}
	
	@RequestMapping("getDangerClassification")
	public @ResponseBody List getDangerClassification(){
		
		return assetsDAO.selectDangerClassification();
		
	}
	
	@RequestMapping("getGUIDByPosition")
	public @ResponseBody Map getGUIDByPosition(@RequestParam Double lng,
			@RequestParam Double lat){
		  
		Map searchMap=new HashMap<>();
        
		System.out.println("lng="+lng+"   lat="+lat);
		
        searchMap.put("[Position].lng=", String.valueOf(lng));
        searchMap.put("[Position].lat=", String.valueOf(lat));
        
        List list=hiddenDAO.selectHiddenOfMap(searchMap);
        
        Hidden_Check_Join hidden_Join=(Hidden_Check_Join) list.get(0);
		
        String GUID=hidden_Join.getGUID();
        
        Map map=new HashMap<>();
        
        map.put("guid", GUID);
        
        map.put("hidden", hidden_Join);
        
        return map;
        
	}
	
	@RequestMapping("getAssetGUIDByPosition")
	public @ResponseBody Map getAssetGUIDByPosition(@RequestParam Double lng,
			@RequestParam Double lat ,HttpServletRequest request){
		  
		Map searchMap=new HashMap<>();

		String term="AND";
		
        searchMap.put("[Position].lng=", String.valueOf(lng));
        searchMap.put("[Position].lat=", String.valueOf(lat));

        Map map=roomInfoDao.findRoomInfoPositionByLatLng(lat, lng);
        
        List list=(List) map.get("rows"); 
        
        int total=(int) map.get("total");
        
        RoomInfo_Position roomInfo_Position=(RoomInfo_Position) list.get(0);
        
        Map fileBytes=mobileDao.roomInfo_PositionImageQuery(request, list);
        
        String url=(String) fileBytes.get(roomInfo_Position.getGUID());
        
        Map map2=new HashMap<>();
        
        map2.put("roomInfo", roomInfo_Position);
        
        map2.put("total", total);
        
        map2.put("list", list);
        
        map2.put("url", url);
        
        return map2;
        
	}
	
	@RequestMapping("getCheckByPosition")
	public @ResponseBody Hidden_Check_Join getCheckByPosition(@RequestParam Double lng,
			@RequestParam Double lat ,@RequestParam String openId,HttpServletRequest request){
		
		Map searchMap=new HashMap<>();
        
        searchMap.put("[Position].lng=", String.valueOf(lng));
        searchMap.put("[Position].lat=", String.valueOf(lat));
        
        Users users=userService.getUserByOnlyOpenId(openId);
        
        Map map;
        
        if(users.getPlace()==3){
        	map=assetCheckDAO.selectAllAssetCheck(1, 0, null, null,null, searchMap);
        }else{
        	map=hiddenDAO.selectAllHiddenCheck(1, 0, null, null,null, searchMap);
        }
        
        Hidden_Check_Join hidden_Check_Join = null;
        
        try{
        	
        	List list=(List) map.get("rows");
        	
        	hidden_Check_Join=(Hidden_Check_Join) list.get(0);
        	
        }catch (Exception e) {
			// TODO: handle exception
        	e.printStackTrace();
		}
        
        return hidden_Check_Join;
        
	}
	
	@RequestMapping("/getAllAsset")
	public @ResponseBody Map getAllAsset(String manageRegion,String roomProperty,
			String state,Integer hire,String dangerClassification,
			String fareItem){
	
		Map where = new HashMap<>();
		
		String term="AND";
		
		try {
			if(manageRegion!=null&&!manageRegion.equals(""))
				manageRegion = URLDecoder.decode(manageRegion,"utf-8");
			if(roomProperty!=null&&!roomProperty.equals(""))
				roomProperty = URLDecoder.decode(roomProperty,"utf-8");
			if(state!=null&&!state.equals(""))
				state = URLDecoder.decode(state,"utf-8");
			if(dangerClassification!=null&&!dangerClassification.equals(""))
				dangerClassification = URLDecoder.decode(dangerClassification,"utf-8");
			if(fareItem!=null&&!fareItem.equals(""))
				fareItem = URLDecoder.decode(fareItem,"utf-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		if(manageRegion!=null&&!manageRegion.equals("")){
			where.put("[Position].GUID !=","");
			where.put(Singleton.ROOMDATABASE+".[dbo].[RoomInfo].ManageRegion = ", manageRegion);
		}else{
			where.put("[Position].GUID !=","");
		}
		
		if(roomProperty!=null&&!roomProperty.equals("")){
			where.put(Singleton.ROOMDATABASE+".[dbo].[RoomInfo].RoomProperty = ", roomProperty);
		}
		
		if(state!=null&&!state.equals("")){
			where.put(Singleton.ROOMDATABASE+".[dbo].[RoomInfo].State = ", state);
		}
		
		if(hire!=null){
			where.put(Singleton.ROOMDATABASE+".[dbo].[ChartInfo].IsHistory = ", "0");
			if(hire==0){
				where.put(Singleton.ROOMDATABASE+".[dbo].[ChartInfo].Hire >= ", "0");
				where.put(Singleton.ROOMDATABASE+".[dbo].[ChartInfo].Hire < ", "100");
			}else if(hire==1){
				where.put(Singleton.ROOMDATABASE+".[dbo].[ChartInfo].Hire >= ", "100");
				where.put(Singleton.ROOMDATABASE+".[dbo].[ChartInfo].Hire < ", "500");
			}else if(hire==2){
				where.put(Singleton.ROOMDATABASE+".[dbo].[ChartInfo].Hire >= ", "500");
				where.put(Singleton.ROOMDATABASE+".[dbo].[ChartInfo].Hire < ", "1000");
			}else if(hire==3){
				where.put(Singleton.ROOMDATABASE+".[dbo].[ChartInfo].Hire >= ", "1000");
				where.put(Singleton.ROOMDATABASE+".[dbo].[ChartInfo].Hire < ", "3000");
			}else if(hire==4){
				where.put(Singleton.ROOMDATABASE+".[dbo].[ChartInfo].Hire >= ", "3000");
				where.put(Singleton.ROOMDATABASE+".[dbo].[ChartInfo].Hire < ", "5000");
			}else if(hire==5){
				where.put(Singleton.ROOMDATABASE+".[dbo].[ChartInfo].Hire >= ", "5000");
				where.put(Singleton.ROOMDATABASE+".[dbo].[ChartInfo].Hire < ", "10000");
			}else if(hire==6){
				where.put(Singleton.ROOMDATABASE+".[dbo].[ChartInfo].Hire > ", "10000");
			}
		}
		
		if(dangerClassification!=null&&!dangerClassification.equals("")){
			where.put(Singleton.ROOMDATABASE+".[dbo].[RoomInfo].DangerClassification = ", dangerClassification);
		}
		
		if(fareItem!=null&&!fareItem.equals("")){
			where.put(Singleton.ROOMDATABASE+".[dbo].[ChartInfo].fareItem = ", fareItem);
		}
		
		Map map=assetsDAO.findAllRoomInfo_Position(100000, 0, null, null,term,where);
		
		Map map2=assetsDAO.findAllHire(term, where);
		
		String allHire=(String) map2.get("allHire");
		
		map.put("allHire", allHire);
		
		return map;
		
	}
	
	
	@RequestMapping("/getAllHiddenAsset")
	public @ResponseBody Map getAllHiddenAsset(){
		
		return roomInfoDao.getAllHiddenAsset();
		
	}
	
	@RequestMapping("/getAllAssetPosition")
	public @ResponseBody Map getAllAssetPosition(){
		
		Map map=roomInfoDao.getAllRoomInfoPosition();
		
		return map;
		
	}
	
	@RequestMapping("/getAllCheckByOpenId")
	public @ResponseBody Map getAllCheckByOpenId(@RequestParam String openId,
			String datepicker, String datepicker2){
				
		Calendar calendar; 
		
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
		
		String startTime = null;
		String endTime = null;
		
		Date sTime = null;
		Date eTime = null;
		
		try {
			if(datepicker!=null&&!datepicker.equals("")){
				sTime=sdf.parse(datepicker);
				startTime=sdf.format(sTime);
				System.out.println("startTime="+startTime);
			}
			if(datepicker2!=null&&!datepicker2.equals("")){
				eTime=sdf.parse(datepicker2);	
				calendar=Calendar.getInstance();
				calendar.setTime(eTime);
				calendar.add(Calendar.DATE, 1);
				endTime=sdf.format(calendar.getTime());	
				System.out.println("endTime="+endTime);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(startTime==null||startTime.equals("")){
			
			calendar=Calendar.getInstance();
			
			calendar.set(Calendar.HOUR_OF_DAY, 0);  
		    calendar.set(Calendar.SECOND, 0);  
		    calendar.set(Calendar.MINUTE, 0);  
		    calendar.set(Calendar.MILLISECOND, 0); 
						
			Date start=calendar.getTime();
			
			startTime=sdf.format(start);			
		}
		
		
		if(endTime==null||endTime.equals("")){
			
			calendar=Calendar.getInstance();
			
			calendar.set(Calendar.HOUR_OF_DAY, 0);  
		    calendar.set(Calendar.SECOND, 0);  
		    calendar.set(Calendar.MINUTE, 0);  
		    calendar.set(Calendar.MILLISECOND, 0); 
			
		    calendar.add(Calendar.DATE, 1);
		    
			Date end=calendar.getTime();
			
			endTime=sdf.format(end);	
			
		}
		
		Map searchMap=new HashMap<>();
		
		searchMap.put("[Hidden_Check].campusAdmin=", openId);	
		searchMap.put("[Position].check_id !=", "");
		searchMap.put("[Hidden_Check].date>",startTime);
		searchMap.put("[Hidden_Check].date<",endTime);
		
		Users users=userService.getUserByOnlyOpenId(openId);
		
		Map map;
		
		if(users.getPlace()==3){
			Map searchMap2=new HashMap<>();
			
			searchMap2.put("[Assets_Check].campusAdmin=", openId);	
			searchMap2.put("[Position].check_id !=", "");
			searchMap2.put("[Assets_Check].date>",startTime);
			searchMap2.put("[Assets_Check].date<",endTime);
			
			map=assetCheckDAO.selectAllAssetCheckPosition(1000, 0, "date", "asc", searchMap2);
		}else{		
			map=hiddenDAO.selectAllHiddenCheckPosition(1000, 0, "date", "asc", searchMap);
		}
		/*
		System.out.println("datepicker="+datepicker+" "+datepicker2);
		
		System.out.println("openid="+openId);
		System.out.println("time="+startTime+"   "+endTime);
		
		MyTestUtil.print(map);
		*/
		
		return map;
		
	}
	
}
