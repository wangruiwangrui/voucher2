package com.voucher.manage.daoImpl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.voucher.manage.dao.AssetCheckDAO;
import com.voucher.manage.daoModel.RoomInfo;
import com.voucher.manage.daoModel.Assets.Assets_Check;
import com.voucher.manage.daoModel.Assets.Assets_Check;
import com.voucher.manage.daoModel.Assets.Assets_Check_Date;
import com.voucher.manage.daoModel.Assets.Hidden_Check;
import com.voucher.manage.daoModel.Assets.Position;
import com.voucher.manage.daoModel.Assets.WeiXin_User;
import com.voucher.manage.daoModelJoin.RoomInfo_Position;
import com.voucher.manage.daoModelJoin.Assets.Hidden_Check_Join;
import com.voucher.manage.daoSQL.InsertExe;
import com.voucher.manage.daoSQL.SelectExe;
import com.voucher.manage.daoSQL.SelectJoinExe;
import com.voucher.manage.daoSQL.SelectSqlJoinExe;
import com.voucher.manage.daoSQL.UpdateExe;
import com.voucher.manage.singleton.Singleton;
import com.voucher.manage.tools.FileConvect;
import com.voucher.manage.tools.MyTestUtil;
import com.voucher.manage.tools.TransMapToString;

public class AssetCheckDAOImpl extends JdbcDaoSupport implements AssetCheckDAO{
	private static final Logger logger = LoggerFactory.getLogger("AffairDAO");
	
	@Override
	public Map findAssetByDistanceDate(int limit,int offset,Double lng, Double lat,String search,String search3,Integer type){
		// TODO Auto-generated method stub
		
		String checkName = null;
		
		if(type==1){
			checkName="hidden_check_date";
		}else{
			checkName="asset_check_date";
		}
		
				String sql0="SELECT TOP "+limit+" "+
							"[Position].GUID,"+
							"[Position].province,"+
							"[Position].city,"+
							"[Position].district,"+
							"[Position].street,"+
							"[Position].street_number,"+
							"[Position].lng,"+
							"[Position].lat,"+
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
						    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].PropertyCardUnit "+				   
							"FROM "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] left join  [Position]"+
							"on "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID = [Position].GUID "+
							"WHERE ([Position].lng is not null AND [Position].lat is not null "+
							"AND ([RoomInfo].State = '已出租' or [RoomInfo].State = '不可出租' or [RoomInfo].State = '空置' ) "+
							"AND ";
							

				String sql01=Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID not in( select top "+offset+" "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID from "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] left join  [Position]"+
						"on "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID = [Position].GUID "+
						"WHERE [Position].lng is not null AND [Position].lat is not null "+ 
						"AND ([RoomInfo].State = '已出租' or [RoomInfo].State = '不可出租' or [RoomInfo].State = '空置' ) "+
						"AND ";
				
				String sql02 = null;
				
				if(search3!=null&&search3.equals("0")){
					
					Calendar cal = Calendar.getInstance();  
					int m=cal.get(Calendar.MONTH)%2;
			        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);  
			        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
			        if(m!=0){
			        	cal.add(Calendar.MONTH, -1);
			        }
			        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
					
					String startTime = null;
					
					startTime=sdf.format(cal.getTime());
					
					sql02=" convert(varchar(11),"+Singleton.ROOMDATABASE+
							".[dbo].[RoomInfo]."+checkName+" ,120 )< '"+startTime+"' "+
							" OR ([Position].lng is not null AND ([RoomInfo].State = '已出租' or [RoomInfo].State = '不可出租' or [RoomInfo].State = '空置' )"
							+ " AND [RoomInfo]."+checkName+" is null )";
					
					sql01=sql01+sql02;
					
					sql0=sql0+sql02+") AND "+sql01;
					
				}else if(search3!=null&&search3.equals("1")){
					
					Calendar cal = Calendar.getInstance();  
					int m=cal.get(Calendar.MONTH)%2;
			        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);  
			        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
			        if(m!=0){
			        	cal.add(Calendar.MONTH, -1);
			        }
			        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
					
					String startTime = null;
					
					startTime=sdf.format(cal.getTime());
					
					sql02=" convert(varchar(11),"+Singleton.ROOMDATABASE+
							".[dbo].[RoomInfo]."+checkName+" ,120 )> '"+startTime+"' ";
					
					sql01=sql01+sql02;
					
					sql0=sql0+sql02+") AND "+sql01;
					
				}
				
				
				
				String sql1="ORDER BY   "+
							"SQRT(("+lng+"-lng)*("+lng+"-lng)+("+lat+"-lat)*("+lat+"-lat))  ";
				
				String sql;
				
				String sql2="SELECT count(*) "+				   
						"FROM "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] left join  [Position]"+
						"on "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID = [Position].GUID "+
						"WHERE ([Position].lng is not null AND [Position].lat is not null "+
						"AND ([RoomInfo].State = '已出租' or [RoomInfo].State = '不可出租' or [RoomInfo].State = '空置' ) "+
						"AND "+sql02;
				
				if(search.equals("")){
					sql=sql0+sql1+")"+sql1;
					sql2=sql2+")";
				}else{
					sql=sql0+" AND ("+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].Address like '%"+search+"%' "
							+" OR "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].Num like '%"+search+"%' )"+
							sql1+")"+
							" AND ("+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].Address like '%"+search+"%' "
							+" OR "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].Num like '%"+search+"%' )"+sql1;
					sql2=sql2+") AND ("+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].Address like '%"+search+"%' "
							+" OR "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].Num like '%"+search+"%' )";
				}
				
				System.out.println("sql="+sql);
				
				RoomInfo_Position roomInfo_Position=new RoomInfo_Position();
				
				Position position=new Position();		
				
				RoomInfo roomInfo=new RoomInfo();
				
				Object[] objects={roomInfo,position};
				
				Map map=new HashMap<>();
				
				try{
					List list=SelectSqlJoinExe.get(this.getJdbcTemplate(), sql, objects,roomInfo_Position);
					int total=(int) SelectSqlJoinExe.getCount(this.getJdbcTemplate(), sql2, objects).get("");
					map.put("rows", list);
					map.put("total", total);
				}catch (Exception e) {
					// TODO: handle exception
				}

				return map;
	}
	
	@Override
	public Integer insertAssetCheck(Assets_Check assets_Check) {
		// TODO Auto-generated method stub
		return InsertExe.get(this.getJdbcTemplate(), assets_Check);
	}

	@Override
	public Map<String, Object> selectAllAssetCheckDate(String check_id) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
        String pathRoot = System.getProperty("user.home");
		
		String filePath=pathRoot+Singleton.filePath;
		
		List<String> names=new ArrayList<String>();
		List<String> types=new ArrayList<String>();
		List<byte[]> fileBytes=new ArrayList<byte[]>();
		
		// TODO Auto-generated method stub
		Assets_Check_Date assets_Check_Date=new Assets_Check_Date();
		assets_Check_Date.setLimit(1000);
		assets_Check_Date.setOffset(0);
		assets_Check_Date.setNotIn("id");
		String[] where={"check_id =",check_id};
		
		assets_Check_Date.setWhere(where);
		
		List<Assets_Check_Date> assets_Check_Dates=SelectExe.get(this.getJdbcTemplate(), assets_Check_Date);
		
		Iterator<Assets_Check_Date> iterator=assets_Check_Dates.iterator();
		
		while(iterator.hasNext()){
			
			Assets_Check_Date assets_Check_Date2=iterator.next();
			
			File file=new File(filePath+"\\"+assets_Check_Date2.getURI());
			
			byte[] fileByte=FileConvect.fileToByte(file);
			
			names.add(assets_Check_Date2.getNAME());
			types.add(assets_Check_Date2.getTYPE());
			fileBytes.add(fileByte);
			
		}
		
		Map<String, Object> map=new HashMap<>();
		
		map.put("names", names);
		map.put("types", types);
		map.put("fileBytes", fileBytes);
		
		return map;
	}

	@Override
	public Integer updateAssetCheck(Assets_Check assets_Check) {
		// TODO Auto-generated method stub
		return UpdateExe.get(this.getJdbcTemplate(), assets_Check);
	}

	@Override
	public Map<String, Object> selectAllAssetCheck(Integer limit, Integer offset, String sort, String order,
			String address, Map<String, String> search) {
		// TODO Auto-generated method stub
		Map map=new HashMap<String, Object>();
		List<Hidden_Check_Join> list;
		Map countMap;
		
		Assets_Check assets_Check=new Assets_Check();
		search.put("[Assets_Check].exist =", "1");
		
		assets_Check.setOffset(offset);
		assets_Check.setLimit(limit);
		assets_Check.setSort(sort);
		assets_Check.setOrder(order);
		assets_Check.setNotIn("id");
		
		RoomInfo roomInfo=new RoomInfo();
		
		roomInfo.setOffset(offset);
		roomInfo.setLimit(limit);
		roomInfo.setSort(sort);
		roomInfo.setOrder(order);
		roomInfo.setNotIn("id");
		
		Position position=new Position();
		
		position.setLimit(limit);
		position.setOffset(offset);
		position.setSort(sort);
		position.setOrder(order);
		position.setNotIn("id");
		
		WeiXin_User weiXin_User=new WeiXin_User();
		
		weiXin_User.setLimit(limit);
		weiXin_User.setOffset(offset);
		weiXin_User.setSort(sort);
		weiXin_User.setOrder(order);
		weiXin_User.setNotIn("id");
		
		if(address!=null&&!address.equals("")){
			
			RoomInfo roomInfo2=new RoomInfo();
			
			String[] where2={Singleton.ROOMDATABASE+".[dbo].[RoomInfo].[Address] like ","%"+address+"%"};
			
			roomInfo2.setWhere(where2);
			roomInfo2.setLimit(2);
			roomInfo2.setOffset(0);			
			roomInfo2.setNotIn("GUID");
			
			List<RoomInfo> list3=SelectExe.get(this.getJdbcTemplate(), roomInfo2);
			
			try{
				
				RoomInfo roomInfo3=list3.get(0);
							
				//MyTestUtil.print(roomInfo3);
				
				if(roomInfo3!=null){

					search.put("[Assets_Check].[GUID] = ", roomInfo3.getGUID());
				}
				
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return null;
			}
			
		}
		
		if(!search.isEmpty()){
		    String[] where=TransMapToString.get(search);
		    assets_Check.setWhere(where);
		    roomInfo.setWhere(where);
		    position.setWhere(where);
		    weiXin_User.setWhere(where);
		}

		if(offset>0){
			
			Object[] objects={assets_Check,weiXin_User};
			
			String[] join={"campusAdmin"};
		
			Hidden_Check_Join assets_Check_Join=new Hidden_Check_Join();
		
			list=SelectJoinExe.get(this.getJdbcTemplate(), objects, assets_Check_Join, join);
		
			Iterator<Hidden_Check_Join> iterator=list.iterator();
			
			int i=0;
			
			while (iterator.hasNext()) {
				
				Hidden_Check_Join assets_Check_Join2=iterator.next();
				
				String guid=assets_Check_Join2.getGUID();
				
				RoomInfo roomInfo4=new RoomInfo();
				
				String[] where4={Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID = ",guid};
				
				roomInfo4.setWhere(where4);
				roomInfo4.setLimit(2);
				roomInfo4.setOffset(0);			
				roomInfo4.setNotIn("GUID");
				
				System.out.println("guid="+guid);
				
				List<RoomInfo> list2=SelectExe.get(this.getJdbcTemplate(), roomInfo4);
				
				try{
					
					RoomInfo roomInfo5=list2.get(0);
								
					//MyTestUtil.print(roomInfo5);
					
					assets_Check_Join2.setAddress(roomInfo5.getAddress());
					assets_Check_Join2.setManageRegion(roomInfo5.getManageRegion());
					assets_Check_Join2.setState(roomInfo5.getState());
					
					
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
				Position position2=new Position();
				
				String[] where5={"[Position].[check_id]=",assets_Check_Join2.getCheck_id()};
				
				position2.setWhere(where5);
				position2.setLimit(2);
				position2.setOffset(0);
				position2.setNotIn("id");
				
				List<Position> list3=SelectExe.get(this.getJdbcTemplate(), position2);
				
				try{
					if(!list3.isEmpty()){
					
						Position position3=list3.get(0);
					
						assets_Check_Join2.setLat(position3.getLat());
						assets_Check_Join2.setLng(position3.getLng());
						assets_Check_Join2.setCity(position3.getCity());
						assets_Check_Join2.setDistrict(position3.getDistrict());
						assets_Check_Join2.setStreet(position3.getStreet());
					
					}
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
				list.set(i, assets_Check_Join2);
				
				i++;
			}
			
			countMap=SelectJoinExe.getCount(this.getJdbcTemplate(), objects, join);
			
		}else{

			
			Object[] objects={assets_Check,position,weiXin_User};
			
			String[] join={"check_id","campusAdmin"};
		
			Hidden_Check_Join assets_Check_Join=new Hidden_Check_Join();
		
			list=SelectJoinExe.get(this.getJdbcTemplate(), objects, assets_Check_Join, join);
		
			Iterator<Hidden_Check_Join> iterator=list.iterator();
			
			int i=0;
			
			while (iterator.hasNext()) {
				
				Hidden_Check_Join assets_Check_Join2=iterator.next();
				
				String guid=assets_Check_Join2.getGUID();
				
				RoomInfo roomInfo4=new RoomInfo();
				
				String[] where4={Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID = ",guid};
				
				roomInfo4.setWhere(where4);
				roomInfo4.setLimit(2);
				roomInfo4.setOffset(0);			
				roomInfo4.setNotIn("GUID");
				
				System.out.println("guid="+guid);
				
				List<RoomInfo> list2=SelectExe.get(this.getJdbcTemplate(), roomInfo4);
				
				try{
					
					RoomInfo roomInfo5=list2.get(0);
								
					//MyTestUtil.print(roomInfo5);
					
					assets_Check_Join2.setAddress(roomInfo5.getAddress());
					assets_Check_Join2.setManageRegion(roomInfo5.getManageRegion());
					assets_Check_Join2.setState(roomInfo5.getState());
					
					
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
							
				
				list.set(i, assets_Check_Join2);
				
				i++;
			}
			
			countMap=SelectJoinExe.getCount(this.getJdbcTemplate(), objects, join);
		}
		
		
		
		map.put("rows", list);
		System.out.println("checkjoinlist=");
		MyTestUtil.print(list);
		

		map.put("total", countMap.get(""));
		
		return map;
	}

	@Override
	public Integer InsertIntoAssetsCheckDate(String check_id, String NAME, String TYPE, String uri) {
		// TODO Auto-generated method stub
		Assets_Check_Date assets_Check_Date=new Assets_Check_Date();
		Date date=new Date();
		
		assets_Check_Date.setCheck_id(check_id);
		assets_Check_Date.setNAME(NAME);
		assets_Check_Date.setTYPE(TYPE);
		assets_Check_Date.setURI(uri);
		assets_Check_Date.setDate(date);
		
		assets_Check_Date.setFileBelong("检查图片");
        
        String[] where={"[check_id]=",check_id,
				"[FileBelong]=","检查图片"};
		
        assets_Check_Date.setWhere(where);
        
        int count=(int) SelectExe.getCount(this.getJdbcTemplate(), assets_Check_Date).get("");
		
        assets_Check_Date.setFileIndex(count+1);
		
		return InsertExe.get(this.getJdbcTemplate(), assets_Check_Date);
	}

	@Override
	public Map<String, Object> selectAllAssetCheckPosition(Integer limit, Integer offset, String sort, String order,
			Map<String, String> search) {
		// TODO Auto-generated method stub
		Map map=new HashMap<String, Object>();
		List<Hidden_Check_Join> list;
		Map countMap;
		
		Assets_Check assets_Check=new Assets_Check();
		search.put("[Assets_Check].exist =", "1");
		
		assets_Check.setOffset(offset);
		assets_Check.setLimit(limit);
		assets_Check.setSort(sort);
		assets_Check.setOrder(order);
		assets_Check.setNotIn("id");
		
		Position position=new Position();
		
		position.setLimit(limit);
		position.setOffset(offset);
		position.setSort(sort);
		position.setOrder(order);
		position.setNotIn("id");
		
		WeiXin_User weiXin_User=new WeiXin_User();
		
		weiXin_User.setLimit(limit);
		weiXin_User.setOffset(offset);
		weiXin_User.setSort(sort);
		weiXin_User.setOrder(order);
		weiXin_User.setNotIn("id");
		
		if(!search.isEmpty()){
		    String[] where=TransMapToString.get(search);
		    assets_Check.setWhere(where);
		    position.setWhere(where);
		    weiXin_User.setWhere(where);
		}
		
		Object[] objects={assets_Check,position,weiXin_User};
		
		String[] join={"check_id","campusAdmin"};
	
		Hidden_Check_Join hidden_Check_Join=new Hidden_Check_Join();
	
		list=SelectJoinExe.get(this.getJdbcTemplate(), objects, position, join);
	
		countMap=SelectJoinExe.getCount(this.getJdbcTemplate(), objects, join);
		
		map.put("rows", list);
		System.out.println("checkjoinlist=");
		//MyTestUtil.print(list);
		

		map.put("total", countMap.get(""));
		
		return map;
	}

}
