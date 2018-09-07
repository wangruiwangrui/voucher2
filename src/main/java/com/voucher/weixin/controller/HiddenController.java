package com.voucher.weixin.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.voucher.manage.dao.AssetsDAO;
import com.voucher.manage.dao.HiddenDAO;
import com.voucher.manage.dao.MobileDAO;
import com.voucher.manage.dao.RoomInfoDao;
import com.voucher.manage.daoModel.RoomInfo;
import com.voucher.manage.daoModel.Assets.Hidden_Assets;
import com.voucher.manage.daoModel.Assets.Hidden_Check;
import com.voucher.manage.daoModel.Assets.Hidden_Check_Item;
import com.voucher.manage.daoModel.Assets.Hidden_Neaten;
import com.voucher.manage.daoModel.Assets.Position;
import com.voucher.manage.daoModel.Assets.RoomInfo_Hidden_Item;
import com.voucher.manage.daoModelJoin.Assets.Hidden_Check_Join;
import com.voucher.manage.daoModelJoin.Assets.Hidden_Join;
import com.voucher.manage.daoModelJoin.Assets.Hidden_Neaten_Join;
import com.voucher.manage.model.Users;
import com.voucher.manage.service.UserService;
import com.voucher.manage.singleton.Singleton;
import com.voucher.manage.tools.MyTestUtil;
import com.voucher.manage.tools.TestDistance;
import com.voucher.sqlserver.context.Connect;

@Controller
@RequestMapping("/mobile/hidden")
public class HiddenController {

	ApplicationContext applicationContext=new Connect().get();
	
	HiddenDAO hiddenDAO=(HiddenDAO) applicationContext.getBean("hiddenDao");
	
	AssetsDAO assetsDAO=(AssetsDAO) applicationContext.getBean("assetsdao");
	
	MobileDAO mobileDao=(MobileDAO) applicationContext.getBean("mobileDao");
	
	RoomInfoDao roomInfoDao=(RoomInfoDao) applicationContext.getBean("roomInfodao");
	
	private UserService userService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping("/selectAllHidden")
	public @ResponseBody Map selectAllHidden(@RequestParam Integer limit, @RequestParam Integer offset, 
			String sort, String order,
			@RequestParam String search,HttpServletRequest request) {
		Map searchMap=new HashMap<>();
		
		if(!search.equals("")){
			searchMap.put("Hidden.name like", "%"+search+"%");
		}
		
		Map map=hiddenDAO.selectAllHidden_Jion(limit, offset, sort, order, searchMap);
		
		List list=(List) map.get("rows");
		
		Map fileBytes=mobileDao.hiddenImageQuery(request,list);
		
		Map result=new HashMap<>();
		
		result.put("hidden", list);
		result.put("fileBytes", fileBytes);
		
		return result;
	}
	
	@RequestMapping("/selectHiddenByGuid")
	public @ResponseBody Map selectHiddenByGuid(@RequestParam String guid,HttpServletRequest request){
        Map searchMap=new HashMap<>();
		System.out.println("guid="+guid);
	    searchMap.put("[Hidden_Check].check_id = ", guid);
		
		Map map=hiddenDAO.selectAllHidden_Jion(10, 0, null, null, searchMap);
		
		List list=(List) map.get("rows");
		
		Map result=new HashMap<>();
		
		Hidden_Join hidden_Join=(Hidden_Join) list.get(0);
		
		List fileBytes=mobileDao.allHiddenImageByGUID(request, hidden_Join);
		
		result.put("hidden", hidden_Join);
		result.put("fileBytes", fileBytes);
		MyTestUtil.print(fileBytes);
		
		return result;
	}
	
	@RequestMapping("/selectAllCheck")
	public @ResponseBody Map selectAllCheck(@RequestParam Integer limit, @RequestParam Integer offset, 
			String sort, String order,
			@RequestParam String search,String search2,String search3,String search4,
			HttpServletRequest request) {
		Map searchMap=new HashMap<>();
		
		Map map;
		
		/*
		if(!search.equals("")){
			searchMap.put("check_name like", "%"+search+"%");
		}
		*/
		
		if(search!=null&&!search.equals("")){
			
			int d=(int) TestDistance.get(search);
			
			System.out.println("d="+d);
			
			if(d>0){
				
				Calendar cal = Calendar.getInstance();  
				cal.set(cal.get(Calendar.YEAR), d-1, 0, 0, 0, 0);  
		        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
				
				String startTime = null;
				
				String endTime=null;
				
				startTime=sdf.format(cal.getTime());
				
				cal.set(cal.get(Calendar.YEAR), d, 0, 0, 0, 0);
		        
		        endTime=sdf.format(cal.getTime());
				
		        System.out.println("searchMap="+searchMap);
		        
				searchMap.put("convert(varchar(11),[Hidden_Check].date,120 ) >", startTime);
				searchMap.put("convert(varchar(11),[Hidden_Check].date,120 ) <", endTime);
				
				System.out.println("cal.getActualMinimum(Calendar.DAY_OF_MONTH="+startTime);
				
				System.out.println("searchMap="+searchMap);
				
				map=hiddenDAO.selectAllHiddenCheck(limit, offset, sort, order,null, searchMap);

				List list=(List) map.get("rows");
				
				int total=(int) map.get("total");
				
				Map fileBytes=mobileDao.checkImageQuery(request,list);
				
				Map result=new HashMap<>();
				
				result.put("hidden_Check", list);
				result.put("total", total);
				result.put("fileBytes", fileBytes);
				
				return result;
				
			}else{
				searchMap.put(Singleton.ROOMDATABASE+".[dbo].[RoomInfo].[Address] like", "%"+search+"%");
			}
	
		}
		
		if(search2!=null&&!search2.equals("")){
			
			Calendar cal = Calendar.getInstance();  
	        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);  
	        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
	        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
			
			String startTime = null;
			
			startTime=sdf.format(cal.getTime());
			
			searchMap.put("convert(varchar(11),[Hidden_Check].date,120 ) >", startTime);
			
			System.out.println("startTime="+startTime);
		}
		
		
		if(search3!=null&&!search3.equals("")){
			searchMap.put("[Hidden_Check].guid = ", search3);
		}
		
		if(search4!=null&&!search4.equals("")){
			searchMap.put("[Hidden_Check].check_name = ", search4);
		}
		
		map=hiddenDAO.selectAllHiddenCheck(limit, offset, sort, order,search, searchMap);
		
		List list=(List) map.get("rows");
		
		int total=(int) map.get("total");
		
		Map fileBytes=mobileDao.checkImageQuery(request,list);
		
		Map result=new HashMap<>();
		
		result.put("hidden_Check", list);
		result.put("total", total);
		result.put("fileBytes", fileBytes);
		
		return result;
	}
	
	@RequestMapping("/selectCheckByCheckId")
	public @ResponseBody Map selectCheckByCheckId(@RequestParam String check_id,HttpServletRequest request){
        Map searchMap=new HashMap<>();
		
	    searchMap.put("[Hidden_Check].check_id = ", check_id);
		String[] where={"check_id = ", check_id};
	    
		Map map=hiddenDAO.selectAllHiddenCheck(1, 0, null, null,null, searchMap);
		
		Hidden_Check_Item hidden_Check_Item=new Hidden_Check_Item();
		hidden_Check_Item.setLimit(1);
		hidden_Check_Item.setOffset(0);
		hidden_Check_Item.setNotIn("check_id");
		hidden_Check_Item.setWhere(where);
		
		List list=(List) map.get("rows");
		
		Map result=new HashMap<>();
		
		Hidden_Check_Join hidden_Check_Join=(Hidden_Check_Join) list.get(0);
		
		hidden_Check_Item=hiddenDAO.selectHidden_Check_ItemById(hidden_Check_Item);
		
		List fileBytes=mobileDao.allCheckImageByGUID(request, hidden_Check_Join);
		
		result.put("hidden_Check", hidden_Check_Join);
		result.put("hidden_Check_Item", hidden_Check_Item);
		result.put("fileBytes", fileBytes);
		MyTestUtil.print(fileBytes);
		
		return result;
	}
	
	
	@RequestMapping("/selectAllNeaten")
	public @ResponseBody Map selectAllNeaten(@RequestParam Integer limit, @RequestParam Integer offset, 
			String sort, String order,
			@RequestParam String search,HttpServletRequest request) {
		Map searchMap=new HashMap<>();
		
		if(!search.equals("")){
			searchMap.put("[Hidden_Neaten].neaten_name like", "%"+search+"%");
		}
		
		Map map=hiddenDAO.selectAllHiddenNeaten(limit, offset, sort, order, searchMap);
		
		List list=(List) map.get("rows");
		
		Map fileBytes=mobileDao.neatenImageQuery(request,list);
		
		Map result=new HashMap<>();
		
		result.put("hidden_Neaten", list);
		result.put("fileBytes", fileBytes);
		
		return result;
	}
	
	@RequestMapping("/selectNeatenByNeatenId")
	public @ResponseBody Map selectNeatenByNeatenId(@RequestParam String neaten_id,HttpServletRequest request){
        Map searchMap=new HashMap<>();
		
	    searchMap.put("[Hidden_Neaten].neaten_id = ", neaten_id);
		
		Map map=hiddenDAO.selectAllHiddenNeaten(10, 0, null, null, searchMap);
		
		List list=(List) map.get("rows");
		
		Map result=new HashMap<>();
		
		Hidden_Neaten_Join hidden_Neaten_Join=(Hidden_Neaten_Join) list.get(0);
		
		List fileBytes=mobileDao.allNeatenImageByGUID(request, hidden_Neaten_Join);
		
		result.put("hidden_Neaten", hidden_Neaten_Join);
		result.put("fileBytes", fileBytes);
		MyTestUtil.print(fileBytes);
		
		return result;
	}
	
			
	@RequestMapping("/insertHiddenCheck")
	public @ResponseBody Map insertHiddenCheck(
			@RequestParam String guid,@RequestParam String checkItemDate,
			String name, //资产名称
			@RequestParam String happenTime,@RequestParam String check_name,
			@RequestParam String check_circs,
			@RequestParam String addComp,@RequestParam String remark,
			@RequestParam Double lng,@RequestParam Double lat,
			HttpServletRequest request){
		
		Hidden_Check hidden_Check=new Hidden_Check();

        UUID uuid=UUID.randomUUID();
        
        String openId=( String ) request.getSession().getAttribute("openId");
        
        JSONObject jsonObject1;
        
        Hidden_Check_Item hidden_Check_Item=new Hidden_Check_Item();
        
        RoomInfo_Hidden_Item roomInfo_Hidden_Item=new RoomInfo_Hidden_Item();
        
        String checkItem = null;
        
        boolean isNull = false;
        
        Integer item = null;
        
        try {
			jsonObject1= JSONObject.parseObject(checkItemDate);
			hidden_Check_Item.setFire_extinguisher(jsonObject1.getInteger("fire_extinguisher"));
			hidden_Check_Item.setHigh_power(jsonObject1.getInteger("high_power"));
			hidden_Check_Item.setBlow(jsonObject1.getInteger("blow"));
			hidden_Check_Item.setLine_aging(jsonObject1.getInteger("line_aging"));
			hidden_Check_Item.setIncline(jsonObject1.getInteger("incline"));
			hidden_Check_Item.setSplit(jsonObject1.getInteger("split"));
			hidden_Check_Item.setDown(jsonObject1.getInteger("down"));
			hidden_Check_Item.setBreak_off(jsonObject1.getInteger("break_off"));
			hidden_Check_Item.setDestroy(jsonObject1.getInteger("destroy"));
			hidden_Check_Item.setInvalidation(jsonObject1.getInteger("invalidation"));
			hidden_Check_Item.setFlaw(jsonObject1.getInteger("flaw"));
			hidden_Check_Item.setCesspool(jsonObject1.getInteger("cesspool"));
			hidden_Check_Item.setCoast(jsonObject1.getInteger("coast"));
			hidden_Check_Item.setWall_up(jsonObject1.getInteger("wall_up"));
			hidden_Check_Item.setOther(jsonObject1.getString("other"));
			
			roomInfo_Hidden_Item.setGuid(guid);
			roomInfo_Hidden_Item.setFire_extinguisher(jsonObject1.getInteger("fire_extinguisher"));
			roomInfo_Hidden_Item.setHigh_power(jsonObject1.getInteger("high_power"));
			roomInfo_Hidden_Item.setBlow(jsonObject1.getInteger("blow"));
			roomInfo_Hidden_Item.setLine_aging(jsonObject1.getInteger("line_aging"));
			roomInfo_Hidden_Item.setIncline(jsonObject1.getInteger("incline"));
			roomInfo_Hidden_Item.setSplit(jsonObject1.getInteger("split"));
			roomInfo_Hidden_Item.setDown(jsonObject1.getInteger("down"));
			roomInfo_Hidden_Item.setBreak_off(jsonObject1.getInteger("break_off"));
			roomInfo_Hidden_Item.setDestroy(jsonObject1.getInteger("destroy"));
			roomInfo_Hidden_Item.setInvalidation(jsonObject1.getInteger("invalidation"));
			roomInfo_Hidden_Item.setFlaw(jsonObject1.getInteger("flaw"));
			roomInfo_Hidden_Item.setCesspool(jsonObject1.getInteger("cesspool"));
			roomInfo_Hidden_Item.setCoast(jsonObject1.getInteger("coast"));
			roomInfo_Hidden_Item.setWall_up(jsonObject1.getInteger("wall_up"));
			roomInfo_Hidden_Item.setOther(jsonObject1.getString("other"));
			
			System.out.println("other="+jsonObject1.getString("other"));
			
			if(jsonObject1.getString("other")!=null&&!jsonObject1.getString("other").equals("")){
				hidden_Check_Item.setIs_other(1);
				roomInfo_Hidden_Item.setIs_other(1);
			}
			
			isNull=jsonObject1.getInteger("fire_extinguisher")==null&&jsonObject1.getInteger("high_power")==null&&jsonObject1.getInteger("blow")==null&&
					jsonObject1.getInteger("line_aging")==null&&jsonObject1.getInteger("incline")==null&&jsonObject1.getInteger("split")==null&&
					jsonObject1.getInteger("down")==null&&jsonObject1.getInteger("break_off")==null&&jsonObject1.getInteger("destroy")==null&&
					jsonObject1.getInteger("invalidation")==null&&jsonObject1.getInteger("flaw")==null&&jsonObject1.getInteger("cesspool")==null&&
					jsonObject1.getInteger("coast")==null&&jsonObject1.getInteger("wall_up")==null&&
					(jsonObject1.getString("other")==null||jsonObject1.getString("other").equals(""));
			
			item=getInt(jsonObject1.getInteger("fire_extinguisher"))+getInt(jsonObject1.getInteger("high_power"))+getInt(jsonObject1.getInteger("blow"))+
					getInt(jsonObject1.getInteger("line_aging"))+getInt(jsonObject1.getInteger("incline"))+getInt(jsonObject1.getInteger("split"))+
					getInt(jsonObject1.getInteger("down"))+getInt(jsonObject1.getInteger("break_off"))+getInt(jsonObject1.getInteger("destroy"))+
					getInt(jsonObject1.getInteger("invalidation"))+getInt(jsonObject1.getInteger("flaw"))+getInt(jsonObject1.getInteger("cesspool"))+
					getInt(jsonObject1.getInteger("coast"))+getInt(jsonObject1.getInteger("wall_up"));
			
			checkItem=getItem("灭火器", hidden_Check_Item.getFire_extinguisher())+getItem("大功率用电器", hidden_Check_Item.getHigh_power())+
					getItem("易燃易爆物品", hidden_Check_Item.getBlow())+getItem("线路老化", hidden_Check_Item.getLine_aging())+
					getItem("倾斜", hidden_Check_Item.getIncline())+getItem("开裂", hidden_Check_Item.getSplit())+
					getItem("地基下沉", hidden_Check_Item.getDown())+getItem("屋面脱落", hidden_Check_Item.getBreak_off())+
					getItem("结构破坏", hidden_Check_Item.getDestroy())+getItem("承重失效", hidden_Check_Item.getInvalidation())+
					getItem("漏雨", hidden_Check_Item.getFlaw())+getItem("化粪池问题", hidden_Check_Item.getCesspool())+
					getItem("山体滑坡", hidden_Check_Item.getCoast())+getItem("管道堵塞", hidden_Check_Item.getWall_up());
			
			if(checkItem.length()>2)
				checkItem=checkItem.substring(0, checkItem.length()-2);
			
			if(check_circs!=null&&!check_circs.equals("")&&!isNull){
				check_circs=check_circs+","+checkItem;
			}else{
				check_circs=checkItem;
			}
        }catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
                
        System.out.println("hidden_Check_Item="+hidden_Check_Item==null);
        
        System.out.println("check_circs="+check_circs);
        
        System.out.println("isNull="+isNull);

        Map map=new HashMap<>();
        
        if(check_name.equals("异常")&&isNull){
        	map.put("status", 2);
        	return map;
        }
        
        System.out.println("item="+item);
        
        hidden_Check.setCampusAdmin(openId);

        hidden_Check.setCheck_id(uuid.toString());
        
        hidden_Check.setGUID(guid);
		
        hidden_Check.setCheck_name(check_name);
        
        hidden_Check.setRemark(remark);
        
        hidden_Check.setCheck_circs(check_circs);

		if(happenTime!=null&&!happenTime.equals("")){
			try {
				DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
				Date date;		
				date = fmt.parse(happenTime);	
				hidden_Check.setHappen_time(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Date date=new Date();
		hidden_Check.setUpdate_time(date);
		hidden_Check.setDate(date);
		hidden_Check.setTerminal("Wechat");
		
		int i=hiddenDAO.insertHiddenCheck(hidden_Check);
		
		hidden_Check_Item.setCheck_id(uuid.toString());
		
		int state=hiddenDAO.insertIntoHidden_Check_Item(hidden_Check_Item);
		
		state=hiddenDAO.updateRoomInfo_Hidden_Item(roomInfo_Hidden_Item);
		
		if(i == 1){

			RoomInfo roomInfo = new RoomInfo();
			
			if (check_name != null && check_name.equals("异常")) {
				
				//插入资产隐患表
				Hidden_Assets hidden_Assets=new Hidden_Assets();
				
				hidden_Assets.setAsset_GUID(guid);
				hidden_Assets.setHidden_GUID(uuid.toString());
				hidden_Assets.setCampusAdmin(openId);
				hidden_Assets.setDate(date);
				
				assetsDAO.insertIntoHidden_Assets(hidden_Assets);
				
				// 更新资产隐患字段
				Map search = new HashMap<>();

				search.put("[RoomInfo_Hidden_Item].guid = ", guid);
				
				try{
					
					RoomInfo_Hidden_Item roomInfo_Hidden_Item2=(RoomInfo_Hidden_Item) hiddenDAO.selectRoomInfo_Hidden_Item(1, 0, "", "", search).get(0);
					int isHidden=getInt(roomInfo_Hidden_Item2.getFire_extinguisher())+
								getInt(roomInfo_Hidden_Item2.getHigh_power())+
								getInt(roomInfo_Hidden_Item2.getBlow())+
								getInt(roomInfo_Hidden_Item2.getLine_aging())+
								getInt(roomInfo_Hidden_Item2.getIncline())+
								getInt(roomInfo_Hidden_Item2.getSplit())+
								getInt(roomInfo_Hidden_Item2.getDown())+
								getInt(roomInfo_Hidden_Item2.getBreak_off())+
								getInt(roomInfo_Hidden_Item2.getDestroy())+
								getInt(roomInfo_Hidden_Item2.getInvalidation())+
								getInt(roomInfo_Hidden_Item2.getFlaw())+
								getInt(roomInfo_Hidden_Item2.getCesspool())+
								getInt(roomInfo_Hidden_Item2.getCoast())+
								getInt(roomInfo_Hidden_Item2.getWall_up())+
								getInt(roomInfo_Hidden_Item2.getIs_other());
					
					System.out.println("isHidden="+isHidden);
					
					roomInfo.setIsHidden(isHidden);
					
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
			}
			
			JSONObject jsonObject = JSONObject.parseObject(addComp);

			String province = jsonObject.getString("province");
			String city = jsonObject.getString("city");
			String district = jsonObject.getString("district");
			String street = jsonObject.getString("street");
			String streetNumber = jsonObject.getString("streetNumber");

			Position position = new Position();

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

			map.put("status", i);
			map.put("check_id", uuid.toString());

			position.setCheck_id(null);
			position.setGUID(guid);

			boolean isUpdate = false; // 如果有位置就不更新

			assetsDAO.updatePositionByRoomInfo(position, isUpdate); // 更新资产位置

			// 更新安全巡查时间
			
			roomInfo.setHidden_check_date(date);

			String[] where = { Singleton.ROOMDATABASE + ".[dbo].[RoomInfo].GUID = ", guid };

			roomInfo.setWhere(where);

			roomInfoDao.updateRoomInfo(roomInfo);
			
		}else{
			map.put("status", 0);
        	return map;
		}
		
		return map;
		
	}
	
	
	String getItem(String name,Integer value){
		if(value!=null){
			if(value==0){
				return name+"正常"+" , ";
			}else if(value==1){
				return name+"异常"+" , ";
			}else{
				return "";
			}
		}else{
			return "";
		}
	}
	
	int getInt(Integer value){
		if(value!=null){
			return value;
		}else{
			return 0;
		}
	}
	
	@RequestMapping("/updateHiddenCheck")
	public @ResponseBody Map updateHiddenCheck(
			@RequestParam String check_id,@RequestParam String check_name,
			@RequestParam String happenTime,@RequestParam String remark,
			@RequestParam String check_circs,@RequestParam String addComp,
			@RequestParam Double lng,@RequestParam Double lat,
			HttpServletRequest request){
		
		Hidden_Check hidden_Check=new Hidden_Check();
       		
        hidden_Check.setCheck_name(check_name);
        
        hidden_Check.setRemark(remark);
        
        hidden_Check.setCheck_circs(check_circs);

        String[] where={"[Hidden_Check].check_id=",check_id};
        
        hidden_Check.setWhere(where);
        
		if(happenTime!=null&&!happenTime.equals("")){
			try {
				DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
				Date date;		
				date = fmt.parse(happenTime);	
				hidden_Check.setHappen_time(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Date date=new Date();
		hidden_Check.setUpdate_time(date);
		hidden_Check.setDate(date);
		
		int i=hiddenDAO.updateHiddenCheck(hidden_Check);
		
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
	
	@RequestMapping("/selectNameBycampusAdmin")
	public @ResponseBody Map selectNameBycampusAdmin(@RequestParam String campusAdmin){
		
		Map map=new HashMap<>();
		
		Users users=userService.getUserByOnlyOpenId(campusAdmin);
		
		map.put("name",users.getName());
		
		return map;
		
	}
	
	@RequestMapping("/insertHiddenNeaten")
	public @ResponseBody Map insertHiddenNeaten(
			@RequestParam String guid,
			@RequestParam String neaten_name,@RequestParam String progress,
			@RequestParam String state,
			@RequestParam String happenTime,@RequestParam String principal,
			@RequestParam String remark,
			@RequestParam String neaten_instance,@RequestParam String addComp,
			@RequestParam Double lng,@RequestParam Double lat,
			HttpServletRequest request){
		
		Hidden_Neaten hidden_Neaten=new Hidden_Neaten();

        UUID uuid=UUID.randomUUID();
        
        String openId=( String ) request.getSession().getAttribute("openId");
        
        hidden_Neaten.setGUID(guid);
        
        hidden_Neaten.setNeaten_id(uuid.toString());
        
        hidden_Neaten.setNeaten_name(neaten_name);
        
        hidden_Neaten.setPrincipal(principal);
        
        hidden_Neaten.setRemark(remark);
        
        hidden_Neaten.setNeaten_instance(neaten_instance);

        hidden_Neaten.setCampusAdmin(openId);
        
        Users users=userService.getUserByOnlyOpenId(openId);
        
        String userName=users.getName();
        
        hidden_Neaten.setUserName(userName);
        
        hidden_Neaten.setProgress(progress);
        
		if(happenTime!=null&&!happenTime.equals("")){
			try {
				DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
				Date date;		
				date = fmt.parse(happenTime);	
				hidden_Neaten.setHappen_time(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Date date=new Date();
		hidden_Neaten.setUpdate_time(date);
		hidden_Neaten.setDate(date);
		hidden_Neaten.setTerminal("Wechat");
		
		int i=hiddenDAO.insertHiddenNeaten(hidden_Neaten);
		
		Hidden_Check hidden_check=new Hidden_Check();

		hidden_check.setState(state);
		
		hidden_check.setUpdate_time(date);
		
		String[] where={"[Hidden_Check].GUID=",guid};
		
		hidden_check.setWhere(where);
		
		i=hiddenDAO.updateHiddenCheck(hidden_check);
		
		JSONObject jsonObject=JSONObject.parseObject(addComp);
		
		String province=jsonObject.getString("province");		
		String city=jsonObject.getString("city");		
		String district=jsonObject.getString("district");		
		String street=jsonObject.getString("street");		
		String streetNumber=jsonObject.getString("streetNumber");	
		
		Position position=new Position();
		
		position.setNeaten_id(uuid.toString());
		position.setLat(lat);
		position.setLng(lng);
		
		position.setProvince(province);
		position.setCity(city);
		position.setDistrict(district);
		position.setStreet(streetNumber);
		position.setStreet_number(streetNumber);
		
		assetsDAO.updatePositionByNeaten(position);
		
		Map map=new HashMap<>();
		
		map.put("status", i);
		map.put("neaten_id", uuid.toString());
				
		return map;
		
	}
	
	
	@RequestMapping("/updateHiddenNeaten")
	public @ResponseBody Map updateHiddenNeaten(
			@RequestParam String guid,@RequestParam String neaten_id,
			@RequestParam String neaten_name,@RequestParam String progress,
			@RequestParam String happenTime,@RequestParam String principal,
			@RequestParam String remark,
			@RequestParam String neaten_instance,@RequestParam String addComp,
			@RequestParam Double lng,@RequestParam Double lat,
			HttpServletRequest request){
		
		Hidden_Neaten hidden_Neaten=new Hidden_Neaten();
      
        String openId=( String ) request.getSession().getAttribute("openId");
        
        hidden_Neaten.setNeaten_id(neaten_id);
        
        hidden_Neaten.setNeaten_name(neaten_name);
        
        hidden_Neaten.setPrincipal(principal);
        
        hidden_Neaten.setRemark(remark);
        
        hidden_Neaten.setNeaten_instance(neaten_instance);

        hidden_Neaten.setCampusAdmin(openId);
        
		if(happenTime!=null&&!happenTime.equals("")){
			try {
				DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
				Date date;		
				date = fmt.parse(happenTime);	
				hidden_Neaten.setHappen_time(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Date date=new Date();
		hidden_Neaten.setUpdate_time(date);
		hidden_Neaten.setDate(date);
		hidden_Neaten.setTerminal("Wechat");
		
		String[] where={"[Hidden_Neaten].neaten_id=",neaten_id};
		
		hidden_Neaten.setWhere(where);
		
		int i=hiddenDAO.updateHiddenNeaten(hidden_Neaten);
		
		Hidden_Check hidden_check=new Hidden_Check();
		
		hidden_check.setState(progress);
		
		String[] where2={"[Hidden_Check].GUID=",guid};
		
		hidden_check.setWhere(where2);
		
		i=hiddenDAO.updateHiddenCheck(hidden_check);
		
		JSONObject jsonObject=JSONObject.parseObject(addComp);
		
		String province=jsonObject.getString("province");		
		String city=jsonObject.getString("city");		
		String district=jsonObject.getString("district");		
		String street=jsonObject.getString("street");		
		String streetNumber=jsonObject.getString("streetNumber");	
		
		Position position=new Position();
		
		position.setNeaten_id(neaten_id);
		position.setLat(lat);
		position.setLng(lng);
		
		position.setProvince(province);
		position.setCity(city);
		position.setDistrict(district);
		position.setStreet(streetNumber);
		position.setStreet_number(streetNumber);
		
		assetsDAO.updatePositionByNeaten(position);
		
		Map map=new HashMap<>();
		
		map.put("status", i);
		map.put("neaten_id", neaten_id);
				
		return map;
		
	}
	
	@RequestMapping("/hiddenStat")
	public @ResponseBody Map hiddenStat(){
		
		int inHidden=assetsDAO.findInHidden();
		
		int notHidden=assetsDAO.findNotHidden();
		
		int successHidden=assetsDAO.findSuccessHidden();
		
		Map search=new HashMap<>();
		
		search.put(Singleton.ROOMDATABASE+".[dbo].[RoomInfo].State=","已出租");
		
		int alreadyHire=roomInfoDao.getRoomInfoCount(search);
		
		search=new HashMap<>();
		
		search.put(Singleton.ROOMDATABASE+".[dbo].[RoomInfo].State=","空置");
		
		int notHire=roomInfoDao.getRoomInfoCount(search);
		
		search=new HashMap<>();
		
		search.put(Singleton.ROOMDATABASE+".[dbo].[RoomInfo].State=","不可出租");
		
		int catnotHire=roomInfoDao.getRoomInfoCount(search);
		
		int allAsset=alreadyHire+notHire+catnotHire;
		
		int allAssetsHidden=assetsDAO.findAllAssetsHidden();
		
		Map map=new HashMap<>();
		
		map.put("inHidden", inHidden);
		
		map.put("notHidden", notHidden);
		
		map.put("successHidden", successHidden);
		
		map.put("allAsset", allAsset);
		
		map.put("allAssetsHidden", allAssetsHidden);
		
		return map;
		
	}
	
	@RequestMapping("/findHiddenByYear")
	public @ResponseBody List findHiddenByYear(){
		
		List list=assetsDAO.findHiddenByYear();
		
		return list;
		
	}
	
	@RequestMapping("/findHiddenByMonthOfYear")
	public @ResponseBody List findHiddenByMonthOfYear(@RequestParam String year){
		
		List list=assetsDAO.findHiddenByMonthOfYear(year);
		
		return list;
		
	}
	
	@RequestMapping("/getAllAssetByHidden_GUID")
	public @ResponseBody Integer getAllAssetByHidden_GUID(@RequestParam String guid){
		
		int count=hiddenDAO.getAllAssetByHidden_GUID(guid);
		
		return count;
		
	}
	
	@RequestMapping("/getRoomInfoHiddenItemByGUID")
	public @ResponseBody RoomInfo_Hidden_Item getRoomInfoHiddenItemByGUID(@RequestParam String guid){
		
		Map search = new HashMap<>();

		search.put("[RoomInfo_Hidden_Item].guid = ", guid);
		
		RoomInfo_Hidden_Item roomInfo_Hidden_Item = null;
		
		try{			
			roomInfo_Hidden_Item=(RoomInfo_Hidden_Item) hiddenDAO.selectRoomInfo_Hidden_Item(1, 0, "", "", search).get(0);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return roomInfo_Hidden_Item;
	}
	
	@RequestMapping("/getRoomInfoHiddenItemDataByGUID")
	public @ResponseBody String getRoomInfoHiddenItemDataByGUID(@RequestParam String guid){
		
		Map search = new HashMap<>();

		search.put("[RoomInfo_Hidden_Item].guid = ", guid);
   
		String hiddenCircs = "";
		
		try{			
			RoomInfo_Hidden_Item hidden_Check_Item=(RoomInfo_Hidden_Item) hiddenDAO.selectRoomInfo_Hidden_Item(1, 0, "", "", search).get(0);
			
			hiddenCircs=getItem2("灭火器", hidden_Check_Item.getFire_extinguisher())+getItem2("大功率用电器", hidden_Check_Item.getHigh_power())+
					getItem2("易燃易爆物品", hidden_Check_Item.getBlow())+getItem2("线路老化", hidden_Check_Item.getLine_aging())+
					getItem2("倾斜", hidden_Check_Item.getIncline())+getItem2("开裂", hidden_Check_Item.getSplit())+
					getItem2("地基下沉", hidden_Check_Item.getDown())+getItem2("屋面脱落", hidden_Check_Item.getBreak_off())+
					getItem2("结构破坏", hidden_Check_Item.getDestroy())+getItem2("承重失效", hidden_Check_Item.getInvalidation())+
					getItem2("漏雨", hidden_Check_Item.getFlaw())+getItem2("化粪池问题", hidden_Check_Item.getCesspool())+
					getItem2("山体滑坡", hidden_Check_Item.getCoast())+getItem2("管道堵塞", hidden_Check_Item.getWall_up());
			
			String other=hidden_Check_Item.getOther();
			
			if(other!=null&&!other.equals("")){
				hiddenCircs=other+","+hiddenCircs;
			}
			
			if(hiddenCircs.length()>2)
				hiddenCircs=hiddenCircs.substring(0, hiddenCircs.length()-2);
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return hiddenCircs;
	}
	
	String getItem2(String name,Integer value){
		if(value!=null){
			if(value==0){
				return "";
			}else if(value==1){
				return name+"异常"+" , ";
			}else{
				return "";
			}
		}else{
			return "";
		}
	}
	
}
