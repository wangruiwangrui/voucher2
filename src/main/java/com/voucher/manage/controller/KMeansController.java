package com.voucher.manage.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.security.auth.kerberos.KerberosKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.voucher.manage.dao.AssetsDAO;
import com.voucher.manage.dao.KMeansDao;
import com.voucher.manage.dao.MobileDAO;
import com.voucher.manage.dao.RoomInfoDao;
import com.voucher.manage.daoModel.RoomInfo;
import com.voucher.manage.daoModel.Assets.Hidden_Check;
import com.voucher.manage.daoModelJoin.RoomInfo_Position;
import com.voucher.manage.daoModelJoin.Assets.Hidden_Check_Join;
import com.voucher.manage.mapper.UsersMapper;
import com.voucher.manage.redis.JedisUtil1;
import com.voucher.manage.singleton.Singleton;
import com.voucher.manage.tools.MyTestUtil;
import com.voucher.manage.tools.KMeans.BisectingKMeans2;
import com.voucher.manage.tools.KMeans.Grid;
import com.voucher.sqlserver.context.Connect;
import com.voucher.weixin.controller.HiddenController;

//@CrossOrigin
@Controller
@RequestMapping("/baiduMap/kMeans")
public class KMeansController {

	@Autowired
	private UsersMapper usersMapper; 
	
	ApplicationContext applicationContext=new Connect().get();
	
	KMeansDao kMeansDao=(KMeansDao) applicationContext.getBean("kMeansDao");
	
	AssetsDAO assetsDAO = (AssetsDAO) applicationContext.getBean("assetsdao");
	
	MobileDAO mobileDao = (MobileDAO) applicationContext.getBean("mobileDao");
	
	RoomInfoDao roomInfoDao=(RoomInfoDao) applicationContext.getBean("roomInfodao");
	
	//@RequestMapping("/getAll")
	public @ResponseBody Map getAll(String id) {		
		
		BisectingKMeans2 bisectingKMeans2=new BisectingKMeans2();
		
		Map<String, Object> map=new HashMap<String, Object>(); 
		/*
		 * Map mm=new HashMap(); mm.put("aaa","aaa"); JedisUtil1.setMap("1", mm); Map
		 * m=JedisUtil1.getMap("1"); MyTestUtil.print(m);
		 */
		if(id==null||id.equals("")){
			id = "assetMap";
			if (JedisUtil1.exist(id)) {
				map = JedisUtil1.getObject(id);
			} else {
				CopyOnWriteArrayList<ArrayList<Double>> cList = kMeansDao.findPosition();
				map = bisectingKMeans2.get(cList);		
				Map<String, Object> mapCopy=new HashMap<String, Object>(); 
				int i = 0;
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					Map<String, Object> map2 = (Map<String, Object>) entry.getValue();
					String lowerCentroid = id + "_" + i;
					map2.put("id", lowerCentroid);
					JedisUtil1.deleteData(lowerCentroid);
					JedisUtil1.setObject(lowerCentroid, map2);
					mapCopy.put(String.valueOf(entry.getKey()), map2);
					i++;
				}
				JedisUtil1.setObject(id, mapCopy);
			}
		}else{
			
			if(JedisUtil1.exist(id)){
				map=JedisUtil1.getObject(id);
			}else{
				if(JedisUtil1.exist("assetMap")){
					map=JedisUtil1.getObject("assetMap");
				}else{
					CopyOnWriteArrayList<ArrayList<Double>> cList=kMeansDao.findPosition();
					map=bisectingKMeans2.get(cList);
					JedisUtil1.setObject("assetMap", map);
				}
			}
			
			CopyOnWriteArrayList<ArrayList<Double>> cList=(CopyOnWriteArrayList<ArrayList<Double>>) map.get("points");
			map=bisectingKMeans2.get(cList);
			int i=0;
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				Map<String, Object> map2 = (Map<String, Object>) entry.getValue();				
				String lowerCentroid=id+"_"+i;
				map2.put("id", lowerCentroid);
				JedisUtil1.deleteData(lowerCentroid);
				JedisUtil1.setObject(lowerCentroid, map2);
				i++;				
			}
		}
				
		return map;
	}
	
	@RequestMapping("/getAll")
	public @ResponseBody List getAll2(String id) {		

		BisectingKMeans2 bisectingKMeans2=new BisectingKMeans2();
		
		List list=new ArrayList<>(); 
		
		if(id==null||id.equals("")){
			id = "assetMap";
			if (JedisUtil1.exist(id)) {
				list = JedisUtil1.getObject(id);
			} else {
				Map  map0= kMeansDao.findPosition2();
				Map map4=new Grid().get(map0);
				
				list=(List) map4.get("list");
				
				JedisUtil1.setObject(id, list);
				
				Map<Integer,List> map2=(Map) map4.get("map2");

				for (Map.Entry<Integer,List> entry : map2.entrySet()) {
					List clist= entry.getValue();
					String lowerCentroid = entry.getKey().toString();
					JedisUtil1.deleteData(lowerCentroid);
					JedisUtil1.setObject(lowerCentroid, clist);
	
				}

			}
		}else{
			
			if(JedisUtil1.exist(id)){
				list=JedisUtil1.getObject(id);
			}else{
				if(JedisUtil1.exist("assetMap")){
					list=JedisUtil1.getObject("assetMap");
				}else{
					Map  map0= kMeansDao.findPosition2();
					Map map4=new Grid().get(map0);
					
					list=(List) map4.get("list");
					
					JedisUtil1.setObject(id, list);
					
					Map<Integer,List> map2=(Map) map4.get("map2");

					for (Map.Entry<Integer,List> entry : map2.entrySet()) {
						List clist= entry.getValue();
						String lowerCentroid = entry.getKey().toString();
						JedisUtil1.deleteData(lowerCentroid);
						JedisUtil1.setObject(lowerCentroid, clist);
		
					}
				}
			}
			
		}
				
		//MyTestUtil.print(map);
		
		return list;
	}
	
//	@CrossOrigin
	@RequestMapping("/getAllRoom")
	public @ResponseBody Map<String, Object> getAllRoom(@RequestParam Integer page,String id,
			HttpServletRequest request,HttpServletResponse resp) {
		
		Map<String, Object> map=new HashMap<String, Object>();
		
		Map searchMap=new HashMap<>();
		
		Map resultMap=new HashMap<>();
		
		List<RoomInfo_Position> roomInfo_Positions=new ArrayList<>();
		
		CopyOnWriteArrayList<ArrayList<Double>> points=null;
		
		if(id!=null&&!id.equals("")&&JedisUtil1.exist(id)){
			
			map=JedisUtil1.getObject(id);
			
			points=(CopyOnWriteArrayList<ArrayList<Double>>) map.get("points");
						
			resultMap=kMeansDao.findAssetByLngLat(points, page,null, searchMap);

			List<Map> roominfos=null;
			
			if(resultMap!=null)
				roominfos= (List) resultMap.get("rows");
			else
				return null;
			
			Iterator<Map> iterator=roominfos.iterator();
			
			while (iterator.hasNext()) {
				RoomInfo_Position roomInfo_Position=new RoomInfo_Position();
				Map map2=(Map) iterator.next();
				String GUID=(String) map2.get("GUID");
				roomInfo_Position.setGUID(GUID);
				roomInfo_Positions.add(roomInfo_Position);
			}
			
			Map<String, Object> fileBytes = mobileDao.roomInfo_PositionImageQuery(request, roomInfo_Positions);
			
			List rows=(List) resultMap.get("rows");
			
			for (Map.Entry<String, Object> entry : fileBytes.entrySet()) {
			
				Iterator<Map> iterator2=rows.iterator();
				int i=0;
				while(iterator2.hasNext()) {
					Map m=iterator2.next();
					String guid=(String) m.get("GUID");
					if(guid.equals(entry.getKey())) {
						m.put("url", entry.getValue());
						rows.set(i, m);
						continue;
					}
					i++;
				}
		
			}
			
			resultMap.put("rows",rows);
			
		}else{
		
			int offset=(page-1)*10;
			
			int limit=10;
			
			resultMap=assetsDAO.findAllRoomInfo_Position(limit, offset, "Num", "asc", "or", searchMap);
			
			roomInfo_Positions=(List) resultMap.get("rows");
			
			Map<String, Object> fileBytes = mobileDao.roomInfo_PositionImageQuery(request, roomInfo_Positions);
			
			List rows=(List) resultMap.get("rows");
			
			List rows2=new ArrayList();
			
			for (Map.Entry<String, Object> entry : fileBytes.entrySet()) {
			
				Iterator<Map> iterator2=rows.iterator();
				int i=0;
				Map map2=new HashMap();
				while(iterator2.hasNext()) {
					RoomInfo_Position roomInfo_Position=(RoomInfo_Position) iterator2.next();
					String guid=(String) roomInfo_Position.getGUID();
					if(guid.equals(entry.getKey())) {
						map2.put("GUID", guid);
						map2.put("address", roomInfo_Position.getAddress());
						map2.put("num", roomInfo_Position.getNum());
						map2.put("region", roomInfo_Position.getRegion());
						map2.put("roomProperty", roomInfo_Position.getRoomProperty());
						map2.put("state", roomInfo_Position.getState());
						map2.put("buildArea", roomInfo_Position.getBuildArea());
						map2.put("lng", roomInfo_Position.getLng());
						map2.put("lat", roomInfo_Position.getLat());
						map2.put("url", entry.getValue());
						rows2.add(map2);
						continue;
					}
					i++;
				}
		
			}
			
			resultMap.put("rows",rows2);
		}

		
		resultMap.put("points", points);
		
		resp.setContentType("text/json; charset=utf-8");
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Max-Age", "3600");
		resp.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept");
		resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		
		return resultMap;
	}
	
	@RequestMapping("/getRoomByPoint")
	public @ResponseBody Map<String, Object> getRoomByPoint(Double lng,Double lat,
			HttpServletRequest request,HttpServletResponse resp) {
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
        
        resp.setContentType("text/json; charset=utf-8");
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Max-Age", "3600");
		resp.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept");
		resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        
        return map2;
		
	}
	
	//查询分类
	@RequestMapping("/getHouseAndAssetTypes")
	public @ResponseBody Map getHouseAndAssetTypes(HttpServletResponse resp) {
		
		List AssetCheck = usersMapper.getWetchatAllUsers(1, 1, null, null, null, null);

		Map houseTypes = kMeansDao.getHouseTypes();
		
		houseTypes.put("AssetCheck", AssetCheck);
		
		resp.setContentType("text/json; charset=utf-8;application/json");
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Max-Age", "3600");
		resp.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept");
		resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		
		return houseTypes;
	}
	
	//通过资产状况查询
//	@CrossOrigin("*")
	@RequestMapping(value="/getAssetByCondition",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody Map getAssetByCondition(@RequestBody Map<String, Object> jMap,HttpServletResponse response){

		
		String term="AND";
		Map where = new HashMap();
		
		for(String key : jMap.keySet()){
			if (key.equals("RoomInfo")) {
				Map rObject =  (Map) jMap.get("RoomInfo");
				for (Object object : rObject.keySet()) {
			
					if(object.equals("Registration")){
						String rString = (String) rObject.get(object);
						if(rString.equals("单土地证")) {
							where.put("["+key+"]."+"GlebeCardNo !=", " ");
						}
						if (rString.equals("单房产证")) {
							where.put("["+key+"]."+"PropertyRightNo !=", " ");
						}
						if (rString.equals("不动产证")) {
							where.put("["+key+"]."+"RealEstateNo !=", " ");
						}
						if (rString.equals("未办证")) {
							where.put("["+key+"]."+"GlebeCardNo =", " ");
							where.put("["+key+"]."+"PropertyRightNo =", " ");
							where.put("["+key+"]."+"RealEstateNo =", " ");
						}
						if (rString.equals("双证齐全")) {
							where.put("["+key+"]."+"GlebeCardNo !=", " ");
							where.put("["+key+"]."+"PropertyRightNo !=", " ");
						}
						
					}
					else if (object.equals("LeasedAssets")) {
						String rString = (String) rObject.get(object);
						where.put("["+key+"]."+"State =", "已出租");
						where.put("["+key+"]."+"Useful = ", rString);
					}
					else if(object.equals("Mortgage")) {
						String rString = (String) rObject.get(object);
						if (rString.equals("已抵押")) {
							where.put("["+key+"]."+"IsPawn =", "是");
							
						}else {
							where.put("["+key+"]."+"IsPawn =", "否");
						}
					}else if (object.equals("address")) {
						String rString = (String) rObject.get(object);
						rString = "%" + rString +"%";
						where.put("["+key+"]."+"Address like", rString);
					}else {
						where.put("["+key+"]."+object+"=", rObject.get(object));
					}
				}
			}
			
			if(key.equals("ChartInfo")) {
				Map rObject =  (Map) jMap.get("ChartInfo");
				for (Object object : rObject.keySet()) {

					if(object.equals("Rent")){
						String  hire = (String) rObject.get(object);
						
						if(hire != null) {
							if (hire.equals("0")) {
								where.put("[dbo].[ChartInfo].Hire >= ", "0");
							}else if(hire.equals("1")){
								where.put("[dbo].[ChartInfo].Hire >= ", "0");
								where.put("[dbo].[ChartInfo].Hire < ", "100");
							}else if(hire.equals("2")){
								where.put("[dbo].[ChartInfo].Hire >= ", "100");
								where.put("[dbo].[ChartInfo].Hire < ", "500");
							}else if(hire.equals("3")){
								where.put("[dbo].[ChartInfo].Hire >= ", "500");
								where.put("[dbo].[ChartInfo].Hire < ", "1000");
							}else if(hire.equals("4")){
								where.put("[dbo].[ChartInfo].Hire >= ", "1000");
								where.put("[dbo].[ChartInfo].Hire < ", "3000");
							}else if(hire.equals("5")){
								where.put("[dbo].[ChartInfo].Hire >= ", "3000");
								where.put("[dbo].[ChartInfo].Hire < ", "5000");
							}else if(hire.equals("6")){
								where.put("[dbo].[ChartInfo].Hire >= ", "5000");
								where.put("[dbo].[ChartInfo].Hire < ", "10000");
							}else if(hire.equals("7")){
								where.put("[dbo].[ChartInfo].Hire > ", "10000");
							}
						}
					}else {
						where.put("["+key+"]."+object+"=", rObject.get(object));
					}
				}
			}
			   
		}
		
		Map reMap = assetsDAO.findAllRoomInfo_Position(100000, 0, null, null,term,where);
		
		
		
		return reMap;
	}
	
	//分页查询侧边栏数据
//	@CrossOrigin("*")
	@RequestMapping(value="/getAccessPagingConditions",method = RequestMethod.POST)
	public @ResponseBody Map getAccessPagingConditions(@RequestBody Map<String, Object> jMap,HttpServletRequest request){

		String term="AND";
		Map where = new HashMap();
		
		Integer page = (Integer) jMap.get("page");
		int offset=(page-1)*10;
		Integer limit = 10;
		
		for(String key : jMap.keySet()){
			if (key.equals("RoomInfo")) {
				Map rObject =  (Map) jMap.get("RoomInfo");
				for (Object object : rObject.keySet()) {
			
					if(object.equals("Registration")){
						String rString = (String) rObject.get(object);
						if(rString.equals("单土地证")) {
							where.put("["+key+"]."+"GlebeCardNo !=", " ");
						}
						if (rString.equals("单房产证")) {
							where.put("["+key+"]."+"PropertyRightNo !=", " ");
						}
						if (rString.equals("不动产证")) {
							where.put("["+key+"]."+"RealEstateNo !=", " ");
						}
						if (rString.equals("未办证")) {
							where.put("["+key+"]."+"GlebeCardNo =", " ");
							where.put("["+key+"]."+"PropertyRightNo =", " ");
							where.put("["+key+"]."+"RealEstateNo =", " ");
						}
						if (rString.equals("双证齐全")) {
							where.put("["+key+"]."+"GlebeCardNo !=", " ");
							where.put("["+key+"]."+"PropertyRightNo !=", " ");
						}
						
					}else if (object.equals("LeasedAssets")) {
						String rString = (String) rObject.get(object);
						where.put("["+key+"]."+"State =", "已出租");
						where.put("["+key+"]."+"Useful = ", rString);
					}else if(object.equals("Mortgage")) {
						String rString = (String) rObject.get(object);
						if (rString.equals("已抵押")) {
							where.put("["+key+"]."+"IsPawn =", "是");
							
						}else {
							where.put("["+key+"]."+"IsPawn =", "否");
						}
					}else if (object.equals("address")) {
						String rString = (String) rObject.get(object);
						rString = "%" + rString +"%";
						where.put("["+key+"]."+"Address like", rString);
					}else {
						where.put("["+key+"]."+object+"=", rObject.get(object)); 
					}
				}
			}
			
			if(key.equals("ChartInfo")) {
				Map rObject =  (Map) jMap.get("ChartInfo");
				for (Object object : rObject.keySet()) {

					if(object.equals("Rent")){
						String  hire = (String) rObject.get(object);
						
						if(hire != null) {
							if (hire.equals("0")) {
								where.put("[dbo].[ChartInfo].Hire >= ", "0");
							}else if(hire.equals("1")){
								where.put("[dbo].[ChartInfo].Hire >= ", "0");
								where.put("[dbo].[ChartInfo].Hire < ", "100");
							}else if(hire.equals("2")){
								where.put("[dbo].[ChartInfo].Hire >= ", "100");
								where.put("[dbo].[ChartInfo].Hire < ", "500");
							}else if(hire.equals("3")){
								where.put("[dbo].[ChartInfo].Hire >= ", "500");
								where.put("[dbo].[ChartInfo].Hire < ", "1000");
							}else if(hire.equals("4")){
								where.put("[dbo].[ChartInfo].Hire >= ", "1000");
								where.put("[dbo].[ChartInfo].Hire < ", "3000");
							}else if(hire.equals("5")){
								where.put("[dbo].[ChartInfo].Hire >= ", "3000");
								where.put("[dbo].[ChartInfo].Hire < ", "5000");
							}else if(hire.equals("6")){
								where.put("[dbo].[ChartInfo].Hire >= ", "5000");
								where.put("[dbo].[ChartInfo].Hire < ", "10000");
							}else if(hire.equals("7")){
								where.put("[dbo].[ChartInfo].Hire > ", "10000");
							}
						}
					}else {
						where.put("["+key+"]."+object+"=", rObject.get(object));
					}
				}
			}
			   
		}
		
		Map reMap = assetsDAO.findAllRoomInfo_Position(limit, offset, null, null,term,where);
		
		List roomInfo_Positions=(List) reMap.get("rows");
		
		Map<String, Object> fileBytes = mobileDao.roomInfo_PositionImageQuery(request, roomInfo_Positions);
		
		List rows=(List) reMap.get("rows");
		
		List rows2=new ArrayList();
		
		for (Map.Entry<String, Object> entry : fileBytes.entrySet()) {
		
			Iterator<Map> iterator2=rows.iterator();
			int i=0;
			Map map2=new HashMap();
			while(iterator2.hasNext()) {
				RoomInfo_Position roomInfo_Position=(RoomInfo_Position) iterator2.next();
				String guid=(String) roomInfo_Position.getGUID();
				if(guid.equals(entry.getKey())) {
					map2.put("GUID", guid);
					map2.put("address", roomInfo_Position.getAddress());
					map2.put("num", roomInfo_Position.getNum());
					map2.put("region", roomInfo_Position.getRegion());
					map2.put("roomProperty", roomInfo_Position.getRoomProperty());
					map2.put("state", roomInfo_Position.getState());
					map2.put("buildArea", roomInfo_Position.getBuildArea());
					map2.put("lng", roomInfo_Position.getLng());
					map2.put("lat", roomInfo_Position.getLat());
					map2.put("url", entry.getValue());
					rows2.add(map2);
					continue;
				}
				i++;
			}
	
		}
		
		reMap.put("rows",rows2);

		return reMap;
	}
	
	//通过隐患资产查询
//	@CrossOrigin("*")
	@RequestMapping(value="/queryAssetByHidden",method = RequestMethod.POST)
	public @ResponseBody Map queryAssetByHidden(@RequestBody Map<String, Object> jMap,HttpServletRequest request,HttpServletResponse resp){

		String term="OR";
		Map where = new HashMap();
		//{hiddenType=房屋结构隐患}
		for(String key : jMap.keySet()){
		   String value = (String) jMap.get(key);
		   if (value.equals("火灾隐患")) {
			   where.put("[RoomInfo_Hidden_Item].line_aging >", "0");
			   where.put("[RoomInfo_Hidden_Item].wire_chaos >", "0");
			   where.put("[RoomInfo_Hidden_Item].valve_aging >", "0");
			   where.put("[RoomInfo_Hidden_Item].high_power >", "0");
			   where.put("[RoomInfo_Hidden_Item].fire_extinguisher >", "0");
			   where.put("[RoomInfo_Hidden_Item].fire_aging >", "0");
			   where.put("[RoomInfo_Hidden_Item].blow >", "0");
			   where.put("[RoomInfo_Hidden_Item].other_fire_hazards >", "0");
			}
		   if (value.equals("房屋结构隐患")) {
			   where.put("[RoomInfo_Hidden_Item].incline >", "0");
			   where.put("[RoomInfo_Hidden_Item].split >", "0");
			   where.put("[RoomInfo_Hidden_Item].collapse >", "0");
			   where.put("[RoomInfo_Hidden_Item].flaw >", "0");
			   where.put("[RoomInfo_Hidden_Item].invalidation >", "0");
			   where.put("[RoomInfo_Hidden_Item].break_off >", "0");
			   where.put("[RoomInfo_Hidden_Item].destroy >", "0");
		   }
		   if (value.equals("配套设施隐患")) {
			   where.put("[RoomInfo_Hidden_Item].wall_up >", "0");
			   where.put("[RoomInfo_Hidden_Item].cesspool >", "0");
			   where.put("[RoomInfo_Hidden_Item].warning_missing >", "0");
			   where.put("[RoomInfo_Hidden_Item].secure_channel >", "0");
			   where.put("[RoomInfo_Hidden_Item].handrail_destroy >", "0");
			   where.put("[RoomInfo_Hidden_Item].other_supporting >", "0");
		   }
		   if(value.equals("自然灾害隐患")) {
			   where.put("[RoomInfo_Hidden_Item].flooding >", "0");
			   where.put("[RoomInfo_Hidden_Item].coast >", "0");
			   where.put("[RoomInfo_Hidden_Item].earthquake >", "0");
			   where.put("[RoomInfo_Hidden_Item].down >", "0");
			   where.put("[RoomInfo_Hidden_Item].snow >", "0");
			   where.put("[RoomInfo_Hidden_Item].other_natural >", "0");
		   }
		   if(value.equals("违章搭建隐患")) {
			   where.put("[RoomInfo_Hidden_Item].illegal_building >", "0");
			   where.put("[RoomInfo_Hidden_Item].structural_failure >", "0");
			   where.put("[RoomInfo_Hidden_Item].other_illegal >", "0");
		   }
		   if(value.equals("其它隐患")) {
			   where.put("[RoomInfo_Hidden_Item].is_other > ", "0");
		   }
		   if (value.equals("全部隐患")) {
			   where.put("[RoomInfo_Hidden_Item].line_aging >", "0");
			   where.put("[RoomInfo_Hidden_Item].wire_chaos >", "0");
			   where.put("[RoomInfo_Hidden_Item].valve_aging >", "0");
			   where.put("[RoomInfo_Hidden_Item].high_power >", "0");
			   where.put("[RoomInfo_Hidden_Item].fire_extinguisher >", "0");
			   where.put("[RoomInfo_Hidden_Item].fire_aging >", "0");
			   where.put("[RoomInfo_Hidden_Item].blow >", "0");
			   where.put("[RoomInfo_Hidden_Item].other_fire_hazards >", "0");
			   where.put("[RoomInfo_Hidden_Item].incline >", "0");
			   where.put("[RoomInfo_Hidden_Item].split >", "0");
			   where.put("[RoomInfo_Hidden_Item].collapse >", "0");
			   where.put("[RoomInfo_Hidden_Item].flaw >", "0");
			   where.put("[RoomInfo_Hidden_Item].invalidation >", "0");
			   where.put("[RoomInfo_Hidden_Item].break_off >", "0");
			   where.put("[RoomInfo_Hidden_Item].destroy >", "0");
			   where.put("[RoomInfo_Hidden_Item].wall_up >", "0");
			   where.put("[RoomInfo_Hidden_Item].cesspool >", "0");
			   where.put("[RoomInfo_Hidden_Item].warning_missing >", "0");
			   where.put("[RoomInfo_Hidden_Item].secure_channel >", "0");
			   where.put("[RoomInfo_Hidden_Item].handrail_destroy >", "0");
			   where.put("[RoomInfo_Hidden_Item].other_supporting >", "0");
			   where.put("[RoomInfo_Hidden_Item].flooding >", "0");
			   where.put("[RoomInfo_Hidden_Item].coast >", "0");
			   where.put("[RoomInfo_Hidden_Item].earthquake >", "0");
			   where.put("[RoomInfo_Hidden_Item].down >", "0");
			   where.put("[RoomInfo_Hidden_Item].snow >", "0");
			   where.put("[RoomInfo_Hidden_Item].other_natural >", "0");
			   where.put("[RoomInfo_Hidden_Item].illegal_building >", "0");
			   where.put("[RoomInfo_Hidden_Item].structural_failure >", "0");
			   where.put("[RoomInfo_Hidden_Item].other_illegal >", "0");
			   where.put("[RoomInfo_Hidden_Item].is_other >", "0");
		   }
		}
		
		Map reMap = kMeansDao.queryAssetByHidden(where,term);
		System.out.println("--------------");
		MyTestUtil.print(reMap.get("rows"));
		return reMap;
	}
	
	//通过隐患资产查询
//	@CrossOrigin("*")
	@RequestMapping(value="/queryAssetPagingByHidden",method = RequestMethod.POST)
	public @ResponseBody Map queryAssetPagingByHidden(@RequestBody Map<String, Object> jMap,HttpServletRequest request,HttpServletResponse resp){

		String term="OR";
		Map where = new HashMap();
		//{hiddenType=房屋结构隐患}
		for(String key : jMap.keySet()){
		   String value = (String) jMap.get(key);
		   if (value.equals("火灾隐患")) {
			   where.put("[RoomInfo_Hidden_Item].line_aging >", "0");
			   where.put("[RoomInfo_Hidden_Item].wire_chaos >", "0");
			   where.put("[RoomInfo_Hidden_Item].valve_aging >", "0");
			   where.put("[RoomInfo_Hidden_Item].high_power >", "0");
			   where.put("[RoomInfo_Hidden_Item].fire_extinguisher >", "0");
			   where.put("[RoomInfo_Hidden_Item].fire_aging >", "0");
			   where.put("[RoomInfo_Hidden_Item].blow >", "0");
			   where.put("[RoomInfo_Hidden_Item].other_fire_hazards >", "0");
			}
		   if (value.equals("房屋结构隐患")) {
			   where.put("[RoomInfo_Hidden_Item].incline >", "0");
			   where.put("[RoomInfo_Hidden_Item].split >", "0");
			   where.put("[RoomInfo_Hidden_Item].collapse >", "0");
			   where.put("[RoomInfo_Hidden_Item].flaw >", "0");
			   where.put("[RoomInfo_Hidden_Item].invalidation >", "0");
			   where.put("[RoomInfo_Hidden_Item].break_off >", "0");
			   where.put("[RoomInfo_Hidden_Item].destroy >", "0");
		   }
		   if (value.equals("配套设施隐患")) {
			   where.put("[RoomInfo_Hidden_Item].wall_up >", "0");
			   where.put("[RoomInfo_Hidden_Item].cesspool >", "0");
			   where.put("[RoomInfo_Hidden_Item].warning_missing >", "0");
			   where.put("[RoomInfo_Hidden_Item].secure_channel >", "0");
			   where.put("[RoomInfo_Hidden_Item].handrail_destroy >", "0");
			   where.put("[RoomInfo_Hidden_Item].other_supporting >", "0");
		   }
		   if(value.equals("自然灾害隐患")) {
			   where.put("[RoomInfo_Hidden_Item].flooding >", "0");
			   where.put("[RoomInfo_Hidden_Item].coast >", "0");
			   where.put("[RoomInfo_Hidden_Item].earthquake >", "0");
			   where.put("[RoomInfo_Hidden_Item].down >", "0");
			   where.put("[RoomInfo_Hidden_Item].snow >", "0");
			   where.put("[RoomInfo_Hidden_Item].other_natural >", "0");
		   }
		   if(value.equals("违章搭建隐患")) {
			   where.put("[RoomInfo_Hidden_Item].illegal_building >", "0");
			   where.put("[RoomInfo_Hidden_Item].structural_failure >", "0");
			   where.put("[RoomInfo_Hidden_Item].other_illegal >", "0");
		   }
		   if(value.equals("其它隐患")) {
			   where.put("[RoomInfo_Hidden_Item].is_other > ", "0");
		   }
		   if (value.equals("全部隐患")) {
			   where.put("[RoomInfo_Hidden_Item].line_aging >", "0");
			   where.put("[RoomInfo_Hidden_Item].wire_chaos >", "0");
			   where.put("[RoomInfo_Hidden_Item].valve_aging >", "0");
			   where.put("[RoomInfo_Hidden_Item].high_power >", "0");
			   where.put("[RoomInfo_Hidden_Item].fire_extinguisher >", "0");
			   where.put("[RoomInfo_Hidden_Item].fire_aging >", "0");
			   where.put("[RoomInfo_Hidden_Item].blow >", "0");
			   where.put("[RoomInfo_Hidden_Item].other_fire_hazards >", "0");
			   where.put("[RoomInfo_Hidden_Item].incline >", "0");
			   where.put("[RoomInfo_Hidden_Item].split >", "0");
			   where.put("[RoomInfo_Hidden_Item].collapse >", "0");
			   where.put("[RoomInfo_Hidden_Item].flaw >", "0");
			   where.put("[RoomInfo_Hidden_Item].invalidation >", "0");
			   where.put("[RoomInfo_Hidden_Item].break_off >", "0");
			   where.put("[RoomInfo_Hidden_Item].destroy >", "0");
			   where.put("[RoomInfo_Hidden_Item].wall_up >", "0");
			   where.put("[RoomInfo_Hidden_Item].cesspool >", "0");
			   where.put("[RoomInfo_Hidden_Item].warning_missing >", "0");
			   where.put("[RoomInfo_Hidden_Item].secure_channel >", "0");
			   where.put("[RoomInfo_Hidden_Item].handrail_destroy >", "0");
			   where.put("[RoomInfo_Hidden_Item].other_supporting >", "0");
			   where.put("[RoomInfo_Hidden_Item].flooding >", "0");
			   where.put("[RoomInfo_Hidden_Item].coast >", "0");
			   where.put("[RoomInfo_Hidden_Item].earthquake >", "0");
			   where.put("[RoomInfo_Hidden_Item].down >", "0");
			   where.put("[RoomInfo_Hidden_Item].snow >", "0");
			   where.put("[RoomInfo_Hidden_Item].other_natural >", "0");
			   where.put("[RoomInfo_Hidden_Item].illegal_building >", "0");
			   where.put("[RoomInfo_Hidden_Item].structural_failure >", "0");
			   where.put("[RoomInfo_Hidden_Item].other_illegal >", "0");
			   where.put("[RoomInfo_Hidden_Item].is_other >", "0");
		   }
		}
		
		Map reMap = kMeansDao.queryAssetByHiddenGuid(where,term);
		List roomInfo_Positions=(List) reMap.get("rows");
		
		List rows=(List) reMap.get("rows");
		Iterator<Map> iterator2=rows.iterator();
		List row2 = new ArrayList();
		while(iterator2.hasNext()) {
			RoomInfo_Position roomInfo_Position=(RoomInfo_Position) iterator2.next();
			String guid=(String) roomInfo_Position.getGUID();
			Hidden_Check hCheck = kMeansDao.selectHiddenStateByGuid(guid); 
			String state = hCheck.getState();
			String address = roomInfo_Position.getAddress();
			Map search = new HashMap();
			String detail = new HiddenController().getRoomInfoHiddenItemDataByGUID(guid);
			Map map2=new HashMap();
			map2.put("GUID", guid);
			map2.put("detail", detail);
			List<Hidden_Check_Join> list=new ArrayList();
			Hidden_Check_Join hidden_Check_Join=new Hidden_Check_Join();
			hidden_Check_Join.setGUID(guid);
			list.add(hidden_Check_Join);
			Map fileBytes = mobileDao.checkImageQuery(request, list);
			map2.put("fileBytes", fileBytes);
			map2.put("state",state);
			map2.put("address", address);
			row2.add(map2);
		}
		reMap.put("rows",row2);
		return reMap;
	}
			
	//通过资产状况查询
//	@CrossOrigin("*")
	@RequestMapping(value="/getAccessByBeFrom",method = RequestMethod.POST)
	public @ResponseBody Map getAccessByBeFrom(@RequestBody Map<String, Object> jMap,HttpServletResponse resp){

		Map map = new HashMap();
		List list = new ArrayList();
		
		for(String key : jMap.keySet()){
			   String value = (String) jMap.get(key);
			   String where = key + " = " +value;
			   map.put(key, value);
			   
			   list.add(where);
		}
		
		Map reMap = kMeansDao.getAccessByBeFrom(list);
		
		return reMap;
	}

}
