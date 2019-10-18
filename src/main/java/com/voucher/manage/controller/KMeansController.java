package com.voucher.manage.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

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
import com.voucher.manage.daoModelJoin.RoomInfo_Position;
import com.voucher.manage.mapper.UsersMapper;
import com.voucher.manage.redis.JedisUtil1;
import com.voucher.manage.tools.MyTestUtil;
import com.voucher.manage.tools.KMeans.BisectingKMeans2;
import com.voucher.sqlserver.context.Connect;

@CrossOrigin
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
	
	@RequestMapping("/getAll")
	public @ResponseBody Map getAll(String id,HttpServletResponse resp) {		

		resp.setContentType("text/json; charset=utf-8");
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Max-Age", "3600");
		resp.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept");
		resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		
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
				
		MyTestUtil.print(map);
		
		return map;
	}
	
	@CrossOrigin
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
			System.out.print("resultMap==========================================");
			MyTestUtil.print(resultMap);
			
			roomInfo_Positions=(List) resultMap.get("rows");
			
			Map<String, Object> fileBytes = mobileDao.roomInfo_PositionImageQuery(request, roomInfo_Positions);
			
			System.out.print("fileBytes==========================================");
			MyTestUtil.print(fileBytes);
			
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
						map2.put("Address", roomInfo_Position.getAddress());
						map2.put("Num", roomInfo_Position.getNum());
						map2.put("Region", roomInfo_Position.getRegion());
						map2.put("RoomProperty", roomInfo_Position.getRoomProperty());
						map2.put("State", roomInfo_Position.getState());
						map2.put("BuildArea", roomInfo_Position.getBuildArea());
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
	
	//通过分类资产查询
	@CrossOrigin("*")
	@RequestMapping(value="/getAssetByCondition",method = RequestMethod.POST)
	public @ResponseBody Map getAssetByCondition(@RequestBody Map<String, Object> jMap,HttpServletResponse resp){

		Map map = new HashMap();
		List list = new ArrayList();
		
		for(String key : jMap.keySet()){
			   String value = (String) jMap.get(key);
			   String where = key + " = " +value;
			   map.put(key, value);
			   
			   list.add(where);
		}
		
		Map reMap = kMeansDao.getAssetByCondition(list);
		
		return reMap;
	}
	
	//通过隐患资产查询
	@CrossOrigin("*")
	@RequestMapping(value="/queryAssetByHidden",method = RequestMethod.POST)
	public @ResponseBody Map queryAssetByHidden(@RequestBody Map<String, Object> jMap,HttpServletResponse resp){

		Map map = new HashMap();
		List list = new ArrayList();
		
		System.out.println("===============");
		System.out.println(jMap);
		//{hiddenType=房屋结构隐患}
		for(String key : jMap.keySet()){
		   String value = (String) jMap.get(key);
		   if (value.equals("火灾隐患")) {
			   String hidden[] = {"line_aging","wire_chaos","valve_aging","high_power","fire_extinguisher","fire_aging","blow","other_fire_hazards"};
			   list.add(hidden);
			}
		   if (value.equals("房屋结构隐患")) {
			   String hidden[] = {};
		   }
		}
		
		Map reMap = kMeansDao.queryAssetByHidden(list);
		
		return reMap;
	}

}
