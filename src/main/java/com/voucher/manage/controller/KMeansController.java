package com.voucher.manage.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.voucher.manage.dao.AssetsDAO;
import com.voucher.manage.dao.KMeansDao;
import com.voucher.manage.dao.MobileDAO;
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
	
	@RequestMapping("/getAll")
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
				
		MyTestUtil.print(map);
		
		return map;
	}
	
	
	@RequestMapping("/getAllRoom")
	public @ResponseBody Map<String, Object> getAllRoom(@RequestParam Integer page,String id,
			HttpServletRequest request) {
		
		Map<String, Object> map=new HashMap<String, Object>();
		
		Map searchMap=new HashMap<>();
		
		Map resultMap=new HashMap<>();
		
		List<RoomInfo_Position> roomInfo_Positions=new ArrayList<>();
		
		CopyOnWriteArrayList<ArrayList<Double>> points=null;
		
		if(id!=null&&!id.equals("")&&JedisUtil1.exist(id)){
			
			map=JedisUtil1.getObject(id);
			
			points=(CopyOnWriteArrayList<ArrayList<Double>>) map.get("points");
						
			resultMap=kMeansDao.findAssetByLngLat(points, page,null, searchMap);
			MyTestUtil.print(resultMap);
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
			
		}else{
		
			int offset=(page-1)*10;
			
			int limit=page*10;
			
			resultMap=assetsDAO.findAllRoomInfo_Position(limit, offset, "Num", "asc", "or", searchMap);
		
			roomInfo_Positions=(List) resultMap.get("rows");
			
		}


		Map<String, Object> fileBytes = mobileDao.roomInfo_PositionImageQuery(request, roomInfo_Positions);
		
		List rows=(List) resultMap.get("rows");
		
		for (Map.Entry<String, Object> entry : fileBytes.entrySet()) {
		
			Iterator<Map> iterator=rows.iterator();
			int i=0;
			while(iterator.hasNext()) {
				Map m=iterator.next();
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
		resultMap.put("points", points);
		
		return resultMap;
	}
	
	//查询分类
	@RequestMapping("/getHouseAndAssetTypes")
	public @ResponseBody Map getHouseAndAssetTypes() {
		
		List AssetCheck = usersMapper.getWetchatAllUsers(1, 1, null, null, null, null);

		Map houseTypes = kMeansDao.getHouseTypes();
		
		houseTypes.put("AssetCheck", AssetCheck);
		
		return houseTypes;
	}
	
	//通过资产状况分类资产查询
	@RequestMapping("/getAssetByCondition")
	public @ResponseBody Map getAssetByCondition(@RequestBody JSONObject assetCondition){
		
		JSONArray roomPropertyArray = assetCondition.getJSONArray("RoomProperty");
		
		JSONArray structureArray = assetCondition.getJSONArray("Structure");
		
		JSONArray regionArray = assetCondition.getJSONArray("RegionArray");
		
		JSONArray dangerClassificationArray = assetCondition.getJSONArray("DangerClassificationArray");
		
		JSONArray floorArray = assetCondition.getJSONArray("floorArray");
		
		Map map = kMeansDao.getAssetByCondition(roomPropertyArray,structureArray,regionArray,dangerClassificationArray,floorArray);
		
		return null;
	}
	
	//通过经营状况分类查询
	@RequestMapping("/getAssetsByBusiness")
	public @ResponseBody Map getAssetsByBusiness(@RequestBody JSONObject assetBusiness) {
		return null;
	}
	
	//通过产权/财产情况分类查询
	@RequestMapping("/getAssetsByFinancial")
	public @ResponseBody Map getAssetsByFinancial(@RequestBody JSONObject assetFinancial) {
		return null;
	}
}
