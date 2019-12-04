package com.voucher.weixin.controller;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.rmi.server.entity.ImageData;
import com.voucher.manage.dao.AssetCheckDAO;
import com.voucher.manage.dao.AssetsDAO;
import com.voucher.manage.dao.HiddenDAO;
import com.voucher.manage.dao.MobileDAO;
import com.voucher.manage.dao.RoomInfoDao;
import com.voucher.manage.daoModel.RoomInfo;
import com.voucher.manage.daoModel.Assets.Assets_Check;
import com.voucher.manage.daoModel.Assets.Assets_Check_Date;
import com.voucher.manage.daoModel.Assets.Patrol_Cycle;
import com.voucher.manage.daoModel.Assets.Position;
import com.voucher.manage.daoModelJoin.Assets.Hidden_Check_Join;
import com.voucher.manage.model.Users;
import com.voucher.manage.service.UserService;
import com.voucher.manage.singleton.Singleton;
import com.voucher.manage.tools.MyTestUtil;
import com.voucher.manage.tools.TestDistance;
import com.voucher.sqlserver.context.Connect;

@Controller
@RequestMapping("/mobile/assetCheck")
public class AssetCheckController {

	
	ApplicationContext applicationContext=new Connect().get();
	
	HiddenDAO hiddenDAO=(HiddenDAO) applicationContext.getBean("hiddenDao");
	
	AssetsDAO assetsDAO=(AssetsDAO) applicationContext.getBean("assetsdao");
	
	AssetCheckDAO assetCheckDAO=(AssetCheckDAO) applicationContext.getBean("assetCheckdao");
	
	MobileDAO mobileDao=(MobileDAO) applicationContext.getBean("mobileDao");
	
	RoomInfoDao roomInfoDao=(RoomInfoDao) applicationContext.getBean("roomInfodao");
	
	private UserService userService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping("/getAssetsByDistanceImg")
	public @ResponseBody Map getAssetsByDistanceImg(Integer limit,Integer offset,Double lng,Double lat,
			Double distance,String search,String search3,HttpServletRequest request){
		System.out.println("search="+search);

		Map map;
		
		if(search==null)
			search="";
		
		System.out.println("search3="+search3);
		
		if(search!=null&&!search.equals("")){
			double d=TestDistance.get(search);
			
			System.out.println("d="+d);
			
			if(d>0&&search3==null){
				map=assetsDAO.findAssetByPoint(limit,offset,lng, lat, d, null);
			}else{
				if(search3!=null&&!search3.equals("")){
					map=assetCheckDAO.findAssetByDistanceDate(limit, offset, lng, lat, search,search3,0);
				}else{
					map=assetsDAO.findAssetByDistance(limit, offset, lng, lat, search);
				}
			}
			
		}else{
			if(search3!=null&&!search3.equals("")){
				map=assetCheckDAO.findAssetByDistanceDate(limit, offset, lng, lat, search,search3,0);
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
	
	@RequestMapping("/insertAssetCheck")
	public @ResponseBody Map insertAssetCheck(
			@RequestParam String guid,@RequestParam String check_name,
			@RequestParam String happenTime,@RequestParam String remark,
			@RequestParam String check_circs,@RequestParam String addComp,
			@RequestParam Double lng,@RequestParam Double lat,
			@RequestParam String id,HttpServletRequest request){
		
		Assets_Check assets_Check=new Assets_Check();

        UUID uuid=UUID.randomUUID();
        
        String openId=( String ) request.getSession().getAttribute("openId");
        
        assets_Check.setCampusAdmin(openId);

        assets_Check.setCheck_id(uuid.toString());
        
        assets_Check.setGUID(guid);
		
        assets_Check.setCheck_name(check_name);
        
        assets_Check.setRemark(remark);
        
        assets_Check.setCheck_circs(check_circs);

		if(happenTime!=null&&!happenTime.equals("")){
			try {
				DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
				Date date;		
				date = fmt.parse(happenTime);	
				assets_Check.setHappen_time(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Date date=new Date();
		assets_Check.setUpdate_time(date);
		assets_Check.setDate(date);
		assets_Check.setTerminal("Wechat");
		
		LinkedHashMap<String, List<ImageData>> imageDataMap = Singleton.getInstance().getImageDataMap();
		List<ImageData> imageDataList = new ArrayList<>();
		try {
			imageDataList = imageDataMap.get(id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

		}

		if (imageDataList == null) {
			imageDataList = new ArrayList<>();
		}
		
		int i=assetCheckDAO.insertAssetCheck(assets_Check);
		
		if (i > 0) { // 执行插入成功的方法

			Iterator iterator = imageDataList.iterator();
			int n = 0;
			while (iterator.hasNext()) {
				ImageData imageData = (ImageData) iterator.next();
				
				Assets_Check_Date assets_Check_Date = new Assets_Check_Date();
				assets_Check_Date.setCheck_id(uuid.toString());
				assets_Check_Date.setNAME(imageData.getName());
				assets_Check_Date.setFileBelong("检查图片");
				assets_Check_Date.setURI(imageData.getURI());
				assets_Check_Date.setTYPE(imageData.getType());
				assets_Check_Date.setFileIndex(n+1);
				assets_Check_Date.setDate(imageData.getDate());
				Integer in = assetCheckDAO.insertAssets_Check_Date(assets_Check_Date);
				MyTestUtil.print(in);
				n++;
			}
			
		}
		
		JSONObject jsonObject=JSONObject.parseObject(addComp);
		
		String province=jsonObject.getString("province");		
		String city=jsonObject.getString("city");		
		String district=jsonObject.getString("district");		
		String street=jsonObject.getString("street");		
		String streetNumber=jsonObject.getString("streetNumber");	
		
		Position position=new Position();
		
		position.setCheck_id(uuid.toString());
		position.setLat(lat);
		position.setLng(lng);
		
		position.setProvince(province);
		position.setCity(city);
		position.setDistrict(district);
		position.setStreet(streetNumber);
		position.setStreet_number(streetNumber);
		position.setDate(date);
		
		assetsDAO.updatePosition(position);
		
		Map map=new HashMap<>();
		
		map.put("status", i);
		map.put("check_id", uuid.toString());
		
		position.setCheck_id(null);
		position.setGUID(guid);
		
		boolean isUpdate=false;   //如果有位置就不更新
		
		assetsDAO.updatePositionByRoomInfo(position,isUpdate); //更新资产位置
		
		//更新安全巡查时间
		RoomInfo roomInfo=new RoomInfo();
				
		roomInfo.setAsset_check_date(date);
				
		String[] where={Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID = ",guid};
				
		roomInfo.setWhere(where);
				
		roomInfoDao.updateRoomInfo(roomInfo);
		
		return map;
		
	}
	
	@RequestMapping("/selectCheckByCheckId")
	public @ResponseBody Map selectCheckByCheckId(@RequestParam String check_id,HttpServletRequest request){
        Map searchMap=new HashMap<>();
		
	    searchMap.put("[Assets_Check].check_id = ", check_id);
		
		Map map=assetCheckDAO.selectAllAssetCheck(10, 0, null, null,null, searchMap);
		
		List list=(List) map.get("rows");
		
		Map result=new HashMap<>();
		
		Hidden_Check_Join assets_Check_Join= (Hidden_Check_Join) list.get(0);
		
		List fileBytes=mobileDao.allAssetCheckImageByGUID(request, assets_Check_Join);
		
		result.put("assets_Check", assets_Check_Join);
		result.put("fileBytes", fileBytes);
		MyTestUtil.print(fileBytes);
		
		return result;
	}
	
	
	@RequestMapping("/updateAssetCheck")
	public @ResponseBody Map updateAssetCheck(
			@RequestParam String check_id,@RequestParam String check_name,
			@RequestParam String happenTime,@RequestParam String remark,
			@RequestParam String check_circs,@RequestParam String addComp,
			@RequestParam Double lng,@RequestParam Double lat,
			HttpServletRequest request){
		
		Assets_Check assets_Check=new Assets_Check();
       		
        assets_Check.setCheck_name(check_name);
        
        assets_Check.setRemark(remark);
        
        assets_Check.setCheck_circs(check_circs);

        String[] where={"[Assets_Check].check_id=",check_id};
        
        assets_Check.setWhere(where);
        
		if(happenTime!=null&&!happenTime.equals("")){
			try {
				DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
				Date date;		
				date = fmt.parse(happenTime);	
				assets_Check.setHappen_time(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Date date=new Date();
		assets_Check.setUpdate_time(date);
		assets_Check.setDate(date);
		
		int i=assetCheckDAO.updateAssetCheck(assets_Check);
		
		JSONObject jsonObject=JSONObject.parseObject(addComp);
		
		String province=jsonObject.getString("province");		
		String city=jsonObject.getString("city");		
		String district=jsonObject.getString("district");		
		String street=jsonObject.getString("street");		
		String streetNumber=jsonObject.getString("streetNumber");	
		
		Position position=new Position();
		
		position.setCheck_id(check_id);
		position.setLat(lat);
		position.setLng(lng);
		
		position.setProvince(province);
		position.setCity(city);
		position.setDistrict(district);
		position.setStreet(streetNumber);
		position.setStreet_number(streetNumber);
		
		//assetsDAO.updatePosition(position);
		
		Map map=new HashMap<>();
		
		map.put("status", i);
		map.put("check_id", check_id);
		
		position.setCheck_id(null);
		
		//assetsDAO.updatePositionByRoomInfo(position); //更新资产位置
		
		return map;
		
	}
	
	@RequestMapping("/selectAllCheck")
	public @ResponseBody Map selectAllCheck(@RequestParam Integer limit, @RequestParam Integer offset, 
			String sort, String order,
			@RequestParam String search,String search2,String search3,HttpServletRequest request) {
		Map searchMap=new HashMap<>();
		
		/*
		if(!search.equals("")){
			searchMap.put("check_name like", "%"+search+"%");
		}
		*/
		
		if(search2!=null&&!search2.equals("")){
			
			int cycle=1;
			
			Patrol_Cycle patrol_Cycle=assetsDAO.selectPatrolCycle();
			
			if(patrol_Cycle!=null)
				cycle=patrol_Cycle.getAsset_cycle();

			Calendar cal = Calendar.getInstance();  
			int start=cal.get(Calendar.MONTH)+1;
			int m=cal.get(Calendar.MONTH)%cycle;
	        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);  
	        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
	        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
			
	        if(cycle!=1){
	        	if(m!=0&&cycle==2){
	        		cal.add(Calendar.MONTH, -(cycle-1));
	        	}else{
	        		int i=1;
	        		int r=start-cycle;
	        		while(r>0&&r>cycle){
	        			r=r-cycle;
	        			i++;
	        		}
	        		System.out.println("start====++"+start+"  r="+r+"  cycle="+cycle+"  i="+i);
	        		int year=cal.get(Calendar.YEAR);
	        		if(cycle==12)
	        			year=year-1;
	        		System.out.println("year======"+year);
	        		cal.set(year, i*cycle, cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
	        	}
	        }
	        
			String startTime = null;
			
			startTime=sdf.format(cal.getTime());
			
			System.out.println("startTime="+startTime);
			
			searchMap.put("convert(varchar(11),[Assets_Check].date,120 ) >", startTime);
			
			System.out.println("startTime="+startTime);
		}
		
		System.out.println("search3="+search3);
		
		if(search3!=null&&!search3.equals("")){
			searchMap.put("[Assets_Check].guid = ", search3);
		}
		
		Map map=assetCheckDAO.selectAllAssetCheck(limit, offset, sort, order,search, searchMap);
		
		List list=(List) map.get("rows");
		
		int total=(int) map.get("total");
		
		Map fileBytes=mobileDao.assetCheckImageQuery(request,list);
		
		Map result=new HashMap<>();
		
		result.put("assets_Check", list);
		result.put("total", total);
		result.put("fileBytes", fileBytes);
		
		return result;
	}
	
	
	@RequestMapping("/selectNameBycampusAdmin")
	public @ResponseBody Map selectNameBycampusAdmin(@RequestParam String campusAdmin){
		
		Map map=new HashMap<>();
		
		Users users=userService.getUserByOnlyOpenId(campusAdmin);
		
		map.put("name",users.getName());
		
		return map;
		
	}
	
	@RequestMapping(value="selectPlace")
	public @ResponseBody Integer selectPlace(HttpServletRequest request,
			HttpServletResponse response){
		
		String openId=( String ) request.getSession().getAttribute("openId");
		
		Users users=userService.getUserByOnlyOpenId(openId);
		
		int place=users.getPlace();
	    
		return place;
	}
	
	//删除资产巡查记录图片
	@RequestMapping("/deleteImgById")
	public @ResponseBody Boolean deleteImgById(@RequestParam String imgName) {
		String pathRoot = System.getProperty("user.home");
		String pString = pathRoot + Singleton.filePath+File.separator+imgName;
		System.out.println("----------------");
		System.out.println(pString);
		Boolean result = false;
		if (imgName != null) {
			result = new File(pString).delete();
		}
		return result;
	}
}
