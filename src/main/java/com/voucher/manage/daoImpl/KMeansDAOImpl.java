package com.voucher.manage.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
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
import com.voucher.manage.daoModel.Assets.Hidden_Check;
import com.voucher.manage.daoModel.Assets.Position;
import com.voucher.manage.daoModel.Assets.RoomInfo_Hidden_Item;
import com.voucher.manage.daoModel.TTT.ChartInfo;
import com.voucher.manage.daoModelJoin.RoomInfo_Position;
import com.voucher.manage.daoRowMapper.RowMappers;
import com.voucher.manage.daoSQL.SelectExe;
import com.voucher.manage.daoSQL.SelectJoinExe;
import com.voucher.manage.daoSQL.SelectSqlJoinExe;
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
		
		if(max<page*limit)
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
		System.out.println("start="+(page-1)*limit+"   end="+end+"   size="+points.size());
		System.out.println("sql0="+sql0);
		
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
		
		map.put("total", points.size());
		
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
		
		//房屋类型
		String sql1 = "SELECT [RoomProperty] as co  FROM "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] group by [RoomProperty]";
		
		List<String> list1 = this.getJdbcTemplate().query(sql1, new co());
		list1.removeAll(Collections.singleton(null));
		list1.removeAll(Collections.singleton(""));
		
		//
		String sql2 = "SELECT [Structure] as co FROM "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] group by [Structure]";
		
		List<String> list2 = this.getJdbcTemplate().query(sql2, new co());
		list2.removeAll(Collections.singleton(null));
		list2.removeAll(Collections.singleton(""));
		
		String sql3 = "SELECT [Region] as co FROM "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] group by [Region]";
		
		List<String> list3 = this.getJdbcTemplate().query(sql3, new co());
		list3.removeAll(Collections.singleton(null));
		list3.removeAll(Collections.singleton(""));
		
		String sql4 = "SELECT [DangerClassification] as co FROM "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] group by [DangerClassification]";
		
		List<String> list4 = this.getJdbcTemplate().query(sql4, new co());
		list4.removeAll(Collections.singleton(null));
		list4.removeAll(Collections.singleton(""));
		
		String sql5 = "SELECT [Floor] as co FROM "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] group by [Floor]";
		
		List<String> list5 = this.getJdbcTemplate().query(sql5, new co());	
		list5.removeAll(Collections.singleton(null));
		list5.removeAll(Collections.singleton(""));

		//出租情况
		String sql6 = "SELECT [State] as co FROM "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] group by [State]";
		
		List<String> list6 = this.getJdbcTemplate().query(sql6, new co());
		list6.removeAll(Collections.singleton(null));
		list6.removeAll(Collections.singleton(""));
		
		String sql7 = "SELECT [Useful] as co FROM "+Singleton.ROOMDATABASE+" .[dbo].[RoomInfo] WHERE State='已出租' group by [Useful]";
		
		//已出租的资产性质
		List<String> list7 = this.getJdbcTemplate().query(sql7, new co());
		list7.removeAll(Collections.singleton(null));
		list7.removeAll(Collections.singleton(""));
		
		//产权来源
		String sql8 = "SELECT [BeFrom] as co FROM "+Singleton.ROOMDATABASE+" .[dbo].[RoomInfo] group by [BeFrom]";
		
		List<String> list8 = this.getJdbcTemplate().query(sql8, new co());
		list8.removeAll(Collections.singleton(null));
		list8.removeAll(Collections.singleton(""));

		String sql9 = "SELECT [FareItem] as co FROM "+Singleton.ROOMDATABASE+" .[dbo].[ChartInfo] group by [FareItem]";
		
		List<String> list9 = this.getJdbcTemplate().query(sql9, new co());
		list9.removeAll(Collections.singleton(null));
		list9.removeAll(Collections.singleton(""));
		
		String sql10 = "SELECT [SecurityRegion] as co FROM "+Singleton.ROOMDATABASE+" .[dbo].[RoomInfo] group by [SecurityRegion]";
		
		List<String> list10 = this.getJdbcTemplate().query(sql10, new co());
		list10.removeAll(Collections.singleton(null));
		list10.removeAll(Collections.singleton(""));
		
		Map map1 = new HashMap();
		Map map3 = new HashMap();
		
		map1.put("RoomProperty",list1);
		map1.put("Structure", list2);
		map1.put("Region", list3);
		map1.put("DangerClassification", list4);
		map1.put("Floor", list5);
		map1.put("State", list6);
		map1.put("LeasedAssets", list7);
		map1.put("BeFrom", list8);
		map3.put("FareItem", list9);
		map1.put("SecurityRegion", list10);
		
		Map map2 = new HashMap();
		map2.put("RoomInfo", map1);
		map2.put("ChartInfo", map3);
		return map2;
	}

	@Override
	public Map getAssetByCondition(List list) {
		
		
		
		return null;
	}
	
	@Override
	public Map getAccessOperatingConditions(List list) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map getAccessByBeFrom(List list) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Map queryAssetByHidden(Map search,String term) {

		String sql0="SELECT "+
				"[Position].province,"+
				"[Position].city,"+
				"[Position].district,"+
				"[Position].street,"+
				"[Position].street_number,"+
				"[Position].lng,"+
				"[Position].lat,"+
				"[Position].wgs84_lng,"+
				"[Position].wgs84_lat,"+
				"[Position].date,"+
				Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].Num,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].OriginalNum,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].Address,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].OriginalAddress,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].Region,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].Segment,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].ManageRegion,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].RoomProperty,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].Useful,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].Floor,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].State,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].Structure,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].BuildArea,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].RoomType,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].IsCity,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].Manager,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].ManagerPhone,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].IsStreet,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].FitMent,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].BeFrom,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].InDate,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].PropertyRightNo,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].PropertyRightArea,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].DesignUseful,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].BuildYear,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].PropertyRightUnit,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].RealPropertyRightUnit,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].PropertyCardUnit ,"+	
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].DangerClassification ,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].ChartGUID "+
				"FROM "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] left join  [Position]"+
				"on "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID = [Position].GUID "+
				"left join "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo_Hidden_Item] "+
				"on "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID = "
				+Singleton.ROOMDATABASE+".[dbo].[RoomInfo_Hidden_Item].GUID "+
				"WHERE "+
				"([RoomInfo].State = '已出租' or [RoomInfo].State = '不可出租' or [RoomInfo].State = '空置' ) "+
				" AND  ([Position].lng is not null AND [Position].lat is not null)";
				
	    String sql01="AND "+
				Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID not in( select top 0 "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID from "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] left join  [Position]"+
				"on "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID = [Position].GUID "+
				"WHERE "+ 
				"([RoomInfo].State = '已出租' or [RoomInfo].State = '不可出租' or [RoomInfo].State = '空置' ) "+
				" AND  ([Position].lng is not null AND [Position].lat is not null)";
	
		String sql1="ORDER BY  "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].Num ";
	
		String sql;
	
		String sql2="SELECT count(*) "+				   
				"FROM "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] left join  [Position]"+
				"on "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID = [Position].GUID "+
				"left join "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo_Hidden_Item] "+
				"on "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID = "+
				Singleton.ROOMDATABASE+".[dbo].[RoomInfo_Hidden_Item].GUID "+
				"WHERE "+
				"([RoomInfo].State = '已出租' or [RoomInfo].State = '不可出租' or [RoomInfo].State = '空置' ) "+
				" AND  ([Position].lng is not null AND [Position].lat is not null)";
		
		System.out.println("search="+search);
		
		System.out.println("trem="+term);
		
		if(search.equals("")||search.isEmpty()){
			sql=sql0+sql01+sql1+")"+sql1;
		}else{
			
			StringBuilder sb = new StringBuilder();
			
			String[] where=TransMapToString.get(search);
			
			int i=0;
			for(String str : where){
			    
			    if(i%2==0){
			    	sb.append(str);
			    }else{
			    	sb.append(""+str+"");
			    	sb.append(" "+term+" ");
			    }
			    i++;
			}
			String s = sb.toString();
			
			String serach=s.substring(0,s.length()-4);
			
			System.out.println("serach="+serach);
			
			sql=sql0+" AND ("+serach+")"+sql01+" AND ("+serach+" )"+sql1+")"
					+sql1;
			sql2=sql2+" AND ("+serach+")";
		}
	
		System.out.println("sql="+sql);
	
		RoomInfo_Position roomInfo_Position=new RoomInfo_Position();
	
		Position position=new Position();		
	
		RoomInfo roomInfo=new RoomInfo();
	
		RoomInfo_Hidden_Item roomInfo_Hidden_Item=new RoomInfo_Hidden_Item();
		
		Object[] objects={roomInfo,roomInfo_Hidden_Item,position};
	
		Map map=new HashMap<>();
	
		try{
			List list=SelectSqlJoinExe.get(this.getJdbcTemplate(), sql, objects,roomInfo_Position);
			int total=(int) SelectSqlJoinExe.getCount(this.getJdbcTemplate(), sql2, objects).get("");
			map.put("rows", list);
			map.put("total", total);
			//MyTestUtil.print(list);
		}catch (Exception e) {
		// TODO: handle exception
		}

	return map;
	}

	@Override
	public Map queryAssetByHiddenGuid(Map search, String term) {
		String sql0="SELECT "+
				"[Position].province,"+
				"[Position].city,"+
				"[Position].district,"+
				"[Position].street,"+
				"[Position].street_number,"+
				"[Position].lng,"+
				"[Position].lat,"+
				"[Position].wgs84_lng,"+
				"[Position].wgs84_lat,"+
				"[Position].date,"+
				Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].Num,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].OriginalNum,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].Address,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].OriginalAddress,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].Region,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].Segment,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].ManageRegion,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].RoomProperty,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].Useful,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].Floor,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].State,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].Structure,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].BuildArea,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].RoomType,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].IsCity,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].Manager,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].ManagerPhone,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].IsStreet,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].FitMent,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].BeFrom,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].InDate,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].PropertyRightNo,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].PropertyRightArea,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].DesignUseful,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].BuildYear,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].PropertyRightUnit,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].RealPropertyRightUnit,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].PropertyCardUnit ,"+	
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].DangerClassification ,"+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].ChartGUID "+
				"FROM "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] left join  [Position]"+
				"on "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID = [Position].GUID "+
				"left join "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo_Hidden_Item] "+
				"on "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID = "
				+Singleton.ROOMDATABASE+".[dbo].[RoomInfo_Hidden_Item].GUID "+
				"WHERE "+
				"([RoomInfo].State = '已出租' or [RoomInfo].State = '不可出租' or [RoomInfo].State = '空置' ) "+
				" AND  ([Position].lng is not null AND [Position].lat is not null)";
				
	    String sql01="AND "+
				Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID not in( select top 0 "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID from "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] left join  [Position]"+
				"on "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID = [Position].GUID "+
				"WHERE "+ 
				"([RoomInfo].State = '已出租' or [RoomInfo].State = '不可出租' or [RoomInfo].State = '空置' ) "+
				" AND  ([Position].lng is not null AND [Position].lat is not null)";
	
		String sql1="ORDER BY  "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].Num ";
	
		String sql;
	
		String sql2="SELECT count(*) "+				   
				"FROM "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] left join  [Position]"+
				"on "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID = [Position].GUID "+
				"left join "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo_Hidden_Item] "+
				"on "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID = "+
				Singleton.ROOMDATABASE+".[dbo].[RoomInfo_Hidden_Item].GUID "+
				"WHERE "+
				"([RoomInfo].State = '已出租' or [RoomInfo].State = '不可出租' or [RoomInfo].State = '空置' ) "+
				" AND  ([Position].lng is not null AND [Position].lat is not null)";
		
		System.out.println("search="+search);
		
		System.out.println("trem="+term);
		
		if(search.equals("")||search.isEmpty()){
			sql=sql0+sql01+sql1+")"+sql1;
		}else{
			
			StringBuilder sb = new StringBuilder();
			
			String[] where=TransMapToString.get(search);
			
			int i=0;
			for(String str : where){
			    
			    if(i%2==0){
			    	sb.append(str);
			    }else{
			    	sb.append(""+str+"");
			    	sb.append(" "+term+" ");
			    }
			    i++;
			}
			String s = sb.toString();
			
			String serach=s.substring(0,s.length()-4);
			
			System.out.println("serach="+serach);
			
			sql=sql0+" AND ("+serach+")"+sql01+" AND ("+serach+" )"+sql1+")"
					+sql1;
			sql2=sql2+" AND ("+serach+")";
		}
	
		System.out.println("sql="+sql);
	
		RoomInfo_Position roomInfo_Position=new RoomInfo_Position();
	
		Position position=new Position();		
	
		RoomInfo roomInfo=new RoomInfo();
	
		RoomInfo_Hidden_Item roomInfo_Hidden_Item=new RoomInfo_Hidden_Item();
		
		Object[] objects={roomInfo,roomInfo_Hidden_Item,position};
	
		Map map=new HashMap<>();
	
		try{
			List list=SelectSqlJoinExe.get(this.getJdbcTemplate(), sql, objects,roomInfo_Position);
			int total=(int) SelectSqlJoinExe.getCount(this.getJdbcTemplate(), sql2, objects).get("");
			map.put("rows", list);
			map.put("total", total);
			//MyTestUtil.print(list);
		}catch (Exception e) {
		// TODO: handle exception
		}

	return map;
	}

	@Override
	public Hidden_Check selectHiddenStateByGuid(String guid) {
		Hidden_Check hCheck = new Hidden_Check();
		hCheck.setGUID(guid);
		hCheck.setLimit(10);
		hCheck.setOffset(0);
		hCheck.setNotIn("id");
		hCheck.setSort("id asc");
		String[] where = {"GUID = " ,guid};
		hCheck.setWhere(where);
		List list=SelectExe.get(this.getJdbcTemplate(), hCheck);
		hCheck = (Hidden_Check) list.get(0);
		return hCheck;
	}

	@Override
	public Map findPosition2() {
		// TODO Auto-generated method stub
		CopyOnWriteArrayList<ArrayList<Double>> cList = new CopyOnWriteArrayList<ArrayList<Double>>();
		
		double latmax = 0;
		double latmin = 1000000;
		double lngmax = 0;
		double lngmin = 1000000;
		
		String sql="select lng,lat from Position where is_roomInfo=1";
		try {
			Statement sta =this.getConnection().createStatement();
			ResultSet rs = sta.executeQuery(sql.toString());
			while(rs.next()){  
                ArrayList<Double> a=new ArrayList<>();
                double lng=rs.getDouble("lng");
                double lat=rs.getDouble("lat");
                a.add(lng);  
                a.add(lat);
                cList.add(a);
                if (lng > lngmax)
					lngmax = lng;
				if (lng < lngmin)
					lngmin = lng;
				if (lat > latmax)
					latmax = lat;
				if (lat < latmin)
					latmin = lat;
            }  
		} catch (CannotGetJdbcConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Map map = new HashMap<>();

		map.put("names", cList);
		map.put("latmax", latmax);
		map.put("latmin", latmin);
		map.put("lngmax", lngmax);
		map.put("lngmin", lngmin);
		
		return map;
		
	}

	
}
