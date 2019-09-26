package com.voucher.manage.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.voucher.manage.dao.KMeansDao;
import com.voucher.manage.daoModel.RoomInfo;
import com.voucher.manage.daoModelJoin.RoomInfo_Position;
import com.voucher.manage.daoRowMapper.RowMappers;
import com.voucher.manage.mapper.UsersMapper;
import com.voucher.manage.singleton.Singleton;
import com.voucher.manage.tools.MyTestUtil;
import com.voucher.manage.tools.TransMapToString;

public class KMeansDAOImpl extends JdbcDaoSupport implements KMeansDao{

	@Override
	public CopyOnWriteArrayList<ArrayList<Double>> findPosition() {
		// TODO Auto-generated method stub
		
		CopyOnWriteArrayList<ArrayList<Double>> cList = null;
		
		String sql="select lng,lat from Position where is_roomInfo=1";
		try {
			Statement sta =this.getConnection().createStatement();
			ResultSet rs = sta.executeQuery(sql.toString());
			while(rs.next()){  
                if(cList == null){  
                	cList = new CopyOnWriteArrayList<ArrayList<Double>>();  
                }  
                ArrayList<Double> a=new ArrayList<>();
                a.add(rs.getDouble("lng"));  
                a.add(rs.getDouble("lat"));
                cList.add(a);
            }  
		} catch (CannotGetJdbcConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return cList;
	}

	@Override
	public Map findAssetByDistance(int limit, int offset, Double lng, Double lat, String search) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map findAssetByLngLat(CopyOnWriteArrayList<ArrayList<Double>> points, int page, Integer limit,Map searchMap) {
		// TODO Auto-generated method stub
		
		if(limit==null)
			limit=10;
		
		System.out.println("========="+limit);
		int end=page*limit;
		
		int max=points.size();
		
		if(max<(page-1)*limit)
			return null;
		
		if(max<end)
			end=max;
		
		String sql=" select RoomInfo.GUID,Address,Num,Region,RoomProperty,State,BuildArea,lng,lat from RoomInfo "
				+ " left join Position on RoomInfo.GUID=Position.GUID ";
		
		String sql2=" select count(*) from RoomInfo "
				+ " left join Position on RoomInfo.GUID=Position.GUID ";
		
		String sql0="";
		
		String sql1="";
		
		for(int start=(page-1)*limit;start<end;start++){
			ArrayList<Double> arrayList=points.get(start);
			double lng=arrayList.get(0);
			double lat=arrayList.get(1);
			sql0=sql0+lng+",";
			sql1=sql1+lat+",";
		}
		
		sql0=sql0.substring(0,sql0.length()-1);
		
		sql1=sql1.substring(0,sql1.length()-1);
		
		sql=sql+"where lng in ("+sql0+") and lat in ("+sql1+")";
		
		sql2=sql2+"where lng in ("+sql0+") and lat in ("+sql1+")";
		
		if(searchMap!=null&&!searchMap.isEmpty()){
			StringBuilder sb = new StringBuilder();
			
			String[] where=TransMapToString.get(searchMap);
			
			int i=0;
			for(String str : where){
			    
			    if(i%2==0){
			    	sb.append(str);
			    }else{
			    	sb.append("'"+str+"'");
			    	sb.append(" or ");
			    }
			    i++;
			}
			String s = sb.toString();
			
			String serach=s.substring(0,s.length()-4);
			
			System.out.println("serach="+serach);
			
			sql=sql+" AND ("+serach+")";
			
			sql2=sql2+" AND ("+serach+")";
		}
		
		List<Map> list=this.getJdbcTemplate().query(sql, new roomInfo());
		
		int total = (int) this.getJdbcTemplate().queryForMap(sql2).get("");
		
		Map map=new HashMap();
		
		map.put("rows", list);
		
		map.put("total", total);
		
		return map;
	}

	class roomInfo implements RowMapper<Map>{

		@Override
		public Map mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			
			Map map=new HashMap<>();

			map.put("GUID", rs.getString("GUID"));
			map.put("Address", rs.getString("Address"));
			map.put("Num", rs.getString("Num"));
			map.put("Region", rs.getString("Region"));
			map.put("RoomProperty", rs.getString("RoomProperty"));
			map.put("State", rs.getString("State"));
			map.put("BuildArea", rs.getFloat("BuildArea"));
			map.put("lng", rs.getDouble("lng"));
			map.put("lat", rs.getDouble("lat"));
			
			return map;
		}
		
	}
	
	class co implements RowMapper<String>{

		@Override
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			
			String s=rs.getString("co");
			
			return s;
		}
		
	}
	
	@Override
	public Map getHouseTypes() {
		
		String[] str = {"RoomProperty","Structure","Region","DangerClassification","Floor","State"};
		
		String sql1 = "SELECT [RoomProperty] as co  FROM "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] group by [RoomProperty]";
		
		List<String> list1 = this.getJdbcTemplate().query(sql1, new co());
		
		String sql2 = "SELECT [Structure] as co FROM "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] group by [Structure]";
		
		List<String> list2 = this.getJdbcTemplate().query(sql2, new co());
		
		String sql3 = "SELECT [Region] as co FROM "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] group by [Region]";
		
		List<String> list3 = this.getJdbcTemplate().query(sql3, new co());
		
		String sql4 = "SELECT [DangerClassification] as co FROM "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] group by [DangerClassification]";
		
		List<String> list4 = this.getJdbcTemplate().query(sql4, new co());	
		
		String sql5 = "SELECT [Floor] as co FROM "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] group by [Floor]";
		
		List<String> list5 = this.getJdbcTemplate().query(sql5, new co());	

		String sql6 = "SELECT [State] as co FROM "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] group by [State]";
		
		List<String> list6 = this.getJdbcTemplate().query(sql6, new co());
		
		String sql7 = "SELECT [Useful] as co FROM "+Singleton.ROOMDATABASE+" .[dbo].[RoomInfo] WHERE State='已出租' group by [Useful]";
		
		List<String> list7 = this.getJdbcTemplate().query(sql7, new co());
		
		List<String> list8 = new ArrayList<String>();
		list8.add("已抵押");
		list8.add("未抵押");
		
		String sql9 = "SELECT [BeFrom] as co FROM "+Singleton.ROOMDATABASE+" .[dbo].[RoomInfo] group by [BeFrom]";
		
		List<String> list9 = this.getJdbcTemplate().query(sql9, new co());

		List<String> list10 = new ArrayList<String>();
		list10.add("未办证");
		list10.add("单土地证");
		list10.add("单房产证");
		list10.add("双证齐全");
		list10.add("不动产权证");
		
		List<String> list11 = new ArrayList<String>();
		list11.add("全部");
		list11.add("0-100");
		list11.add("100-500");
		list11.add("500-1000");
		list11.add("1000-3000");
		list11.add("3000-5000");
		list11.add("5000-10000");
		list11.add(">10000");

		Map map = new HashMap();
		
		map.put("RoomProperty",list1);
		map.put("Structure", list2);
		map.put("Region", list3);
		map.put("DangerClassification", list4);
		map.put("Floor", list5);
		map.put("State", list6);
		map.put("LeasedAssets", list7);
		map.put("Ispawn", list8);
		map.put("BeFrom", list9);
		map.put("Certificate", list10);
		map.put("Hire",list11);
		return map;
	}

	@Override
	public Map getAssetByCondition(JSONArray roomPropertyArray, JSONArray structureArray, JSONArray regionArray, JSONArray dangerClassificationArray, JSONArray floorArray) {
		
		String where = "";
		
		String strRoomProperty = "[RoomInfo].roomProperty= ";
		String strStructure = "[RoomInfo].Structure= ";
		
		if(roomPropertyArray!=null) {
			for (int i = 0; i < roomPropertyArray.size(); i++) {
				
				if (i == (roomPropertyArray.size() - 1)) {
					where = where + strRoomProperty+roomPropertyArray.get(i) + " and";
				} else {
					where = where + strRoomProperty+roomPropertyArray.get(i)+" or";
				}
				
			}
		}else {
			
		}
		
		if(structureArray!=null) {
			for (int i = 0; i < structureArray.size(); i++) {
				
				if (i == (structureArray.size() - 1)) {
					where = where + strStructure+structureArray.get(i);
				} else {
					where = where + strStructure+structureArray.get(i)+" or";
				}
				
			}
		}
		
		return null;
	}
}