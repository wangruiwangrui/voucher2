package com.voucher.manage.daoImpl;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.voucher.manage.dao.AssetsDAO;
import com.voucher.manage.daoImpl.RoomInfoDaoImpl.allHire;
import com.voucher.manage.daoModel.HiddenAssetByMonthAmount;
import com.voucher.manage.daoModel.HiddenByMonthAmount;
import com.voucher.manage.daoModel.RoomInfo;
import com.voucher.manage.daoModel.Users;
import com.voucher.manage.daoModel.Assets.Assets_Check;
import com.voucher.manage.daoModel.Assets.Hidden;
import com.voucher.manage.daoModel.Assets.Hidden_Assets;
import com.voucher.manage.daoModel.Assets.Hidden_Check;
import com.voucher.manage.daoModel.Assets.Hidden_Check_Date;
import com.voucher.manage.daoModel.Assets.Hidden_Data;
import com.voucher.manage.daoModel.Assets.Hidden_Level;
import com.voucher.manage.daoModel.Assets.Hidden_Neaten;
import com.voucher.manage.daoModel.Assets.Hidden_Type;
import com.voucher.manage.daoModel.Assets.Hidden_User;
import com.voucher.manage.daoModel.Assets.Position;
import com.voucher.manage.daoModel.TTT.ChartInfo;
import com.voucher.manage.daoModelJoin.RoomInfo_Position;
import com.voucher.manage.daoModelJoin.Assets.Hidden_Assets_Join;
import com.voucher.manage.daoModelJoin.Assets.Hidden_Check_Join;
import com.voucher.manage.daoModelJoin.Assets.Hidden_Data_Join;
import com.voucher.manage.daoModelJoin.Assets.Hidden_Join;
import com.voucher.manage.daoModelJoin.Assets.Position_Check_Join;
import com.voucher.manage.daoModelJoin.Assets.Position_Hidden_Join;
import com.voucher.manage.daoModelJoin.Assets.Position_Neaten_Join;
import com.voucher.manage.daoRowMapper.RowMappersJoin;
import com.voucher.manage.daoSQL.DeleteExe;
import com.voucher.manage.daoSQL.InsertExe;
import com.voucher.manage.daoSQL.SelectExe;
import com.voucher.manage.daoSQL.SelectJoinExe;
import com.voucher.manage.daoSQL.SelectSqlJoinExe;
import com.voucher.manage.daoSQL.UpdateExe;
import com.voucher.manage.file.AbstractFileUpload;
import com.voucher.manage.singleton.Singleton;
import com.voucher.manage.tools.FileConvect;
import com.voucher.manage.tools.MyTestUtil;
import com.voucher.manage.tools.TransMapToString;
import com.voucher.weixin.controller.WechatSendMessageController;

public class AssetsDAOImpl extends JdbcDaoSupport implements AssetsDAO{

	@Override
	public Map findAllHidden(Integer limit, Integer offset, String sort, String order, Map<String, String> search) {
		// TODO Auto-generated method stub
		
		Hidden hidden=new Hidden();
		
		hidden.setLimit(limit);
		hidden.setOffset(offset);
		hidden.setSort(sort);
		hidden.setOrder(order);
		
		if(!search.isEmpty()){
		    String[] where=TransMapToString.get(search);
		    hidden.setWhere(where);
		}
		
		
		return null;
	}

	//通过坐标查找GUID
	@Override
	public String getGUIDByPosition(String lng,String lat) {
		// TODO Auto-generated method stub		
		Position position=new Position();
		
		position.setLimit(2);
		position.setOffset(0);
		position.setNotIn("id");
		
		String[] where={"[Position].lng=",lng,"[Position].lat=",lat};
		
		position.setWhere(where);
		
		List list=SelectExe.get(this.getJdbcTemplate(), position);
		
		String GUID = null;
		
		try{
			Position position2=(Position) list.get(0);
			GUID=position2.getGUID();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
				
		return GUID;
	}
	
	@Override
	public Integer updatePosition(Position position) {
		// TODO Auto-generated method stub
		int i;
		String[] where={"[Position].GUID=",position.getGUID()};
		position.setWhere(where);
		int count=(int) SelectExe.getCount(this.getJdbcTemplate(), position).get("");
		if(count==0){
			i=InsertExe.get(this.getJdbcTemplate(), position);			
		}else{
			i=UpdateExe.get(this.getJdbcTemplate(), position);
		}
		return i;
	}

	@Override
	public Integer updatePositionByRoomInfo(Position position , boolean isUpdate) {
		// TODO Auto-generated method stub
		int i = 0;
		String[] where={"[Position].GUID=",position.getGUID()};
		position.setWhere(where);
		int count=(int) SelectExe.getCount(this.getJdbcTemplate(), position).get("");
		if(count==0){
			position.setIs_roomInfo(1);
			i=InsertExe.get(this.getJdbcTemplate(), position);			
		}else if(isUpdate){
			i=UpdateExe.get(this.getJdbcTemplate(), position);
		}
		
		return i;
	}
	
	@Override
	public Integer updatePositionByCheck(Position position) {
		// TODO Auto-generated method stub
		int i;
		String[] where={"[Position].check_id=",position.getCheck_id()};
		position.setWhere(where);
		int count=(int) SelectExe.getCount(this.getJdbcTemplate(), position).get("");
		if(count==0){
			i=InsertExe.get(this.getJdbcTemplate(), position);			
		}else{
			i=UpdateExe.get(this.getJdbcTemplate(), position);
		}
		
		return i;
	}

	@Override
	public Integer updatePositionByNeaten(Position position) {
		// TODO Auto-generated method stub
		int i;
		String[] where={"[Position].neaten_id=",position.getNeaten_id()};
		position.setWhere(where);
		int count=(int) SelectExe.getCount(this.getJdbcTemplate(), position).get("");
		if(count==0){
			i=InsertExe.get(this.getJdbcTemplate(), position);			
		}else{
			i=UpdateExe.get(this.getJdbcTemplate(), position);
		}
		return i;
	}
	
	public Integer countPositionByGUID(Position position){
		String[] where={"[Position].GUID=",position.getGUID()};
		position.setWhere(where);
		return (int) SelectExe.getCount(this.getJdbcTemplate(), position).get("");
	}
	
	@Override
	public Integer updateCheckPosition(Position position) {
		// TODO Auto-generated method stub
		int i;
		String[] where={"[Position].check_id=",position.getCheck_id()};
		position.setWhere(where);
		int count=(int) SelectExe.getCount(this.getJdbcTemplate(), position).get("");
		if(count==1){
			i=UpdateExe.get(this.getJdbcTemplate(), position);
		}else{
			i=InsertExe.get(this.getJdbcTemplate(), position);
		}
		return i;
	}
	
	@Override
	public Integer updateNeatenPosition(Position position) {
		// TODO Auto-generated method stub
		int i;
		String[] where={"[Position].neaten_id=",position.getNeaten_id()};
		position.setWhere(where);
		int count=(int) SelectExe.getCount(this.getJdbcTemplate(), position).get("");
		if(count==1){
			i=UpdateExe.get(this.getJdbcTemplate(), position);
		}else{
			i=InsertExe.get(this.getJdbcTemplate(), position);
		}
		return i;
	}
	
	
	public Integer deletePosition(Position position){
		return DeleteExe.get(this.getJdbcTemplate(), position);
	}
	
	@Override
	public Map findAllPosition(String manageRegion) {
		// TODO Auto-generated method stub
		int limit=1000;
		int offset=0;
		
		Position_Hidden_Join position_Hidden_Join=new Position_Hidden_Join();
		
		Position position=new Position();		
		position.setOffset(offset);
		position.setLimit(limit);
		position.setNotIn("id");
		
		Hidden hidden=new Hidden();
		hidden.setOffset(offset);
		hidden.setLimit(limit);
		hidden.setNotIn("id");
		
		if(!manageRegion.equals("")){
			String[] where = {"[Position].GUID !=","''",
					"Hidden.ManageRegion = ", manageRegion};
			position.setWhere(where);
			hidden.setWhere(where);
		}else{
			String[] where = {"[Position].GUID !=","''"};
			position.setWhere(where);
			hidden.setWhere(where);
		}
		
		Object[] objects={hidden,position};
		
		String[] join={"GUID"};
		
		List list=SelectJoinExe.get(this.getJdbcTemplate(), objects,position_Hidden_Join,join);
		
		Map map=new HashMap<>();
		
		map.put("row", list);
		
		return map;
	}

	@Override
	public Map findAllCheckPosition() {
		// TODO Auto-generated method stub
		int limit=1000;
		int offset=0;
		
		Position_Check_Join position_Check_Join=new Position_Check_Join();
		
		Position position=new Position();		
		position.setOffset(offset);
		position.setLimit(limit);
		position.setNotIn("id");
		
		Hidden_Check hidden_Check=new Hidden_Check();
		hidden_Check.setOffset(offset);
		hidden_Check.setLimit(limit);
		hidden_Check.setNotIn("id");
		
		String[] where = {"[Position].check_id !=","''"};
		position.setWhere(where);
		hidden_Check.setWhere(where);
		
		Object[] objects={position,hidden_Check};
		
		String[] join={"check_id"};
		
		List list=SelectJoinExe.get(this.getJdbcTemplate(), objects,position_Check_Join,join);
		
		Map map=new HashMap<>();
		
		map.put("row", list);
		
		return map;
	}
	
	@Override
	public Map findAllNeatenPosition() {
		// TODO Auto-generated method stub
		int limit=1000;
		int offset=0;
		
		Position_Neaten_Join position_Neaten_Join=new Position_Neaten_Join();
		
		Position position=new Position();		
		position.setOffset(offset);
		position.setLimit(limit);
		position.setNotIn("id");
		
		Hidden_Neaten hidden_Neaten=new Hidden_Neaten();
		hidden_Neaten.setOffset(offset);
		hidden_Neaten.setLimit(limit);
		hidden_Neaten.setNotIn("id");
		
		String[] where = {"[Position].neaten_id !=","''"};
		position.setWhere(where);
		hidden_Neaten.setWhere(where);
		
		
		Object[] objects={position,hidden_Neaten};
		
		String[] join={"neaten_id"};
		
		List list=SelectJoinExe.get(this.getJdbcTemplate(), objects,position_Neaten_Join,join);
		
		Map map=new HashMap<>();
		
		map.put("row", list);
		
		return map;
	}
	
	@Override
	public List findPosition(Position position) {
		// TODO Auto-generated method stub
		return SelectExe.get(this.getJdbcTemplate(), position);
	}
	
	@Override
	public Map findHiddenByDistance(int limit,int offset,Double lng, Double lat,String search) {
		// TODO Auto-generated method stub
		
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
					"[Hidden].name,"+
					"[Hidden].hidden_level,"+
					"[Hidden].detail,"+
					"[Hidden].progress,"+
					"[Hidden].happen_time,"+
					"[Hidden].principal,"+
					"[Hidden].type,"+
					"[Hidden].state,"+
					"[Hidden].remark,"+
					"[Hidden].update_time,"+
					"[Hidden].date "+
					"FROM [Hidden] left join  [Position]"+
					"on [Hidden].GUID = [Position].GUID "+
					"WHERE [Position].lng is not null AND [Position].lat is not null "+ 
					"AND [Hidden].exist=1"+
					"AND "+
					"[Hidden].id not in( select top "+offset+" [Hidden].id from [Hidden] left join  [Position]"+
					"on [Hidden].GUID = [Position].GUID "+
					"WHERE [Position].lng is not null AND [Position].lat is not null "+ 
					"ORDER BY   SQRT(("+lng+"-lng)*("+lng+"-lng)+("+lat+"-lat)*("+lat+"-lat))) ";
					
		String sql1="ORDER BY   "+
					"SQRT(("+lng+"-lng)*("+lng+"-lng)+("+lat+"-lat)*("+lat+"-lat))  ";
		
		String sql;
		
		if(search.equals("")){
			sql=sql0+sql1;
		}else{
			sql=sql0+"AND [Hidden].name like '%"+search+"%' "+sql1;
		}
		
		Position_Hidden_Join position_Hidden_Join=new Position_Hidden_Join();
		
		Position position=new Position();		
		
		Hidden hidden=new Hidden();
		
		Object[] objects={hidden,position};
		
		List list=SelectSqlJoinExe.get(this.getJdbcTemplate(), sql, objects,position_Hidden_Join);
		
		Map map=new HashMap<>();
		
		map.put("row", list);
		
		return map;
	}
	
	
	@Override
	public Map findAssetByDistance(int limit,int offset,Double lng, Double lat,String search) {
		// TODO Auto-generated method stub
		
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
					"WHERE [Position].lng is not null AND [Position].lat is not null "+
					"AND ([RoomInfo].State = '已出租' or [RoomInfo].State = '不可出租' or [RoomInfo].State = '空置' ) "+
					"AND "+
					Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID not in( select top "+offset+" "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID from "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] left join  [Position]"+
					"on "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID = [Position].GUID "+
					"WHERE [Position].lng is not null AND [Position].lat is not null "+ 
					"AND ([RoomInfo].State = '已出租' or [RoomInfo].State = '不可出租' or [RoomInfo].State = '空置' ) ";
		
		
		String sql1="ORDER BY   "+
					"SQRT(("+lng+"-lng)*("+lng+"-lng)+("+lat+"-lat)*("+lat+"-lat))  ";
		
		String sql;
		
		String sql2="SELECT count(*) "+				   
				"FROM "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] left join  [Position]"+
				"on "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID = [Position].GUID "+
				"WHERE [Position].lng is not null AND [Position].lat is not null "+
				"AND ([RoomInfo].State = '已出租' or [RoomInfo].State = '不可出租' or [RoomInfo].State = '空置' ) ";
		
		if(search.equals("")){
			sql=sql0+sql1+")"+sql1;
		}else{
			sql=sql0+" AND ("+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].Address like '%"+search+"%' "
					+" OR "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].Num like '%"+search+"%' )"+
					sql1+")"+
					" AND ("+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].Address like '%"+search+"%' "
					+" OR "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].Num like '%"+search+"%' )"+sql1;
			sql2=sql2+" AND ("+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].Address like '%"+search+"%' "
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
	public Map findAssetByDistanceDate(int limit,int offset,Double lng, Double lat,String search,String search2,Integer type){
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
				
				if(search2!=null&&search2.equals("0")){
					
					Calendar cal = Calendar.getInstance();  
			        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);  
			        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
			        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
					
					String startTime = null;
					
					startTime=sdf.format(cal.getTime());
					
					sql02=" convert(varchar(11),"+Singleton.ROOMDATABASE+
							".[dbo].[RoomInfo].hidden_check_date ,120 )< '"+startTime+"' "+
							" OR ([Position].lng is not null AND ([RoomInfo].State = '已出租' or [RoomInfo].State = '不可出租' or [RoomInfo].State = '空置' )"
							+ " AND [RoomInfo]."+checkName+" is null )";
					
					sql01=sql01+sql02;
					
					sql0=sql0+sql02+") AND "+sql01;
					
				}else if(search2!=null&&search2.equals("1")){
					
					Calendar cal = Calendar.getInstance();  
			        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);  
			        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
			        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
					
					String startTime = null;
					
					startTime=sdf.format(cal.getTime());
					
					sql02=" convert(varchar(11),"+Singleton.ROOMDATABASE+
							".[dbo].[RoomInfo].hidden_check_date ,120 )> '"+startTime+"' ";
					
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
	public Map findHiddenByPoint(Double lng, Double lat,Double distance,String search) {
		// TODO Auto-generated method stub
		
		String sql0="SELECT "+
		            "[Position].GUID,"+
					"[Position].province,"+
					"[Position].city,"+
					"[Position].district,"+
					"[Position].street,"+
					"[Position].street_number,"+
					"[Position].lng,"+
					"[Position].lat,"+
					"[Position].date,"+
					"[Hidden].name,"+
					"[Hidden].hidden_level,"+
					"[Hidden].detail,"+
					"[Hidden].progress,"+
					"[Hidden].happen_time,"+
					"[Hidden].principal,"+
					"[Hidden].type,"+
					"[Hidden].state,"+
					"[Hidden].remark,"+
					"[Hidden].update_time,"+
					"[Hidden].date "+
					"FROM [Hidden] left join  [Position]"+
					"on [Hidden].GUID = [Position].GUID "+
					"WHERE ";
					
		String sql1="geography::STGeomFromText('POINT(' + cast([lng] as varchar(20)) + ' '"+  
					"+ cast([lat] as varchar(20)) +')', 4326).STDistance(  "+
					"geography::STGeomFromText('POINT("+lng+"  "+lat+")', 4326))<"+distance+" "+
					"ORDER BY   "+
					"SQRT(("+lng+"-lng)*("+lng+"-lng)+("+lat+"-lat)*("+lat+"-lat))  ";
		
		String sql;
		
		if(search.equals("")){
			sql=sql0+sql1;
		}else{
			sql=sql0+"AND [Hidden].name like '%"+search+"%' "+sql1;
		}
		
		Position_Hidden_Join position_Hidden_Join=new Position_Hidden_Join();
		
		Position position=new Position();		
		
		Hidden hidden=new Hidden();
		
		Object[] objects={hidden,position};
		
		List list=SelectSqlJoinExe.get(this.getJdbcTemplate(), sql, objects,position_Hidden_Join);
		
		Map map=new HashMap<>();
		
		map.put("row", list);
		
		return map;
	}
	
	
	@Override
	public Map findAssetByPoint(int limit,int offset,Double lng, Double lat,Double distance,String search) {
		// TODO Auto-generated method stub
		
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
					"WHERE ";

		String sql1="geography::STGeomFromText('POINT(' + cast([lng] as varchar(20)) + ' '"+  
					"+ cast([lat] as varchar(20)) +')', 4326).STDistance(  "+
					"geography::STGeomFromText('POINT("+lng+"  "+lat+")', 4326))<"+distance+" ";
		
		String sql10=" ORDER BY   "+
					"SQRT(("+lng+"-lng)*("+lng+"-lng)+("+lat+"-lat)*("+lat+"-lat))  ";		
		
		String sql01="AND "+
				Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID not in( select top "+offset+" "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID from "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] left join  [Position]"+
				"on "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID = [Position].GUID WHERE " ;
		
		String sql02="geography::STGeomFromText('POINT(' + cast([lng] as varchar(20)) + ' '"+  
				"+ cast([lat] as varchar(20)) +')', 4326).STDistance(  "+
				"geography::STGeomFromText('POINT("+lng+"  "+lat+")', 4326))<"+distance+" ";
		
		String sql;
		
		String sql2="SELECT COUNT(*) "+
				"FROM "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] left join  [Position]"+
				"on "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID = [Position].GUID "+
				"WHERE ";
		
		if(search==null||search.equals("")){
			sql=sql0+sql1+sql01+sql1+sql10+")"+sql10;
			sql2=sql2+sql02;
		}else{
			sql=sql0+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].Address like '%"+search+"%' "+" AND "+sql1;
			sql2=sql2+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].Address like '%"+search+"%' "+" AND "+sql02;
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
	public Integer insertIntoHidden_Assets(Hidden_Assets hidden_Assets) {
		// TODO Auto-generated method stub
		
		String asset_GUID=hidden_Assets.getAsset_GUID();
		String hidden_GUID=hidden_Assets.getHidden_GUID();
		
		String[] where={"asset_GUID=",asset_GUID,"hidden_GUID=",hidden_GUID};
		
		Hidden_Assets hidden_Assets2=new Hidden_Assets();
		
		hidden_Assets2.setWhere(where);
		
		int result;
		
		int count=(int) SelectExe.getCount(this.getJdbcTemplate(), hidden_Assets2).get("");
		
		if(count==0){
			result=InsertExe.get(this.getJdbcTemplate(), hidden_Assets);
			
			WechatSendMessageController wechatSendMessageController=new WechatSendMessageController();
			
			wechatSendMessageController.send(asset_GUID, hidden_GUID);
			
		}else{
			result=2;
		}

		return result;
	}

	@Override
	public Map findAssetByHideen(Integer limit, Integer offset, String sort, String order, Map<String, String> search) {
		// TODO Auto-generated method stub
		
		Hidden_Assets hidden_Assets=new Hidden_Assets();
		
		hidden_Assets.setLimit(limit);
		hidden_Assets.setOffset(offset);
		hidden_Assets.setSort(sort);
		hidden_Assets.setOffset(offset);
		hidden_Assets.setNotIn("[GUID]");
		
		RoomInfo roomInfo=new RoomInfo();
		
		roomInfo.setLimit(limit);
		roomInfo.setOffset(offset);
		roomInfo.setSort(sort);
		roomInfo.setOrder(order);
		roomInfo.setNotIn("[GUID]");
			
		Object[] objects={hidden_Assets,roomInfo};
		
		Map map=new HashMap<String, Object>();
		
		Hidden_Assets_Join hidden_Assets_Join=new Hidden_Assets_Join();
		

  String sql="SELECT top "+limit+
    "[Hidden_Assets].id,"+
    "[Hidden_Assets].asset_GUID,"+
    "[Hidden_Assets].hidden_GUID,"+
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
    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].PropertyCardUnit,"+
    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GlebeCardUnit,"+
    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].TransferUnit,"+
    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GlebeCardNo,"+
    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GlebeUseType,"+
    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GlebeEndDate,"+
    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GlebeTypeUseful,"+
    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].PlanUseful,"+
    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].BefromFile,"+
    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].NoPRNResion,"+
    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].NoGCResion,"+
    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].RealEstateNo,"+
    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].PropertyMemo,"+
    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].OriginalAmount,"+
    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].AllDepreciation,"+
    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].DepreciationYear,"+
    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].NetWorth,"+
    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].EvaluationPrice,"+
    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].EvaluationSinglePrice,"+
    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].EvaluationPlace,"+
    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].BefromAmount,"+
    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].MarketHire,"+
    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].EvaluationUnit,"+
    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].EvaluationNo,"+
    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].IsPawn,"+
    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].PawnUnit,"+
    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].OriginalUnit,"+
    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].FinanceMemo,"+
    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].Utility,"+
    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].ChartGUID,"+
    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].AddressCode,"+
    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].OriginalAddressCode,"+
    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].SecurityClassification,"+
    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].DangerClassification,"+
    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].HiddenDanger,"+
    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].ResponsiblePerson,"+
    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].sMemo,"+
    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].BelongUnit,"+
    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].FileIndex,"+
    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].SecurityRegion,"+
    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].RoomCount,"+
    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].LandArea,"+
    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].UseYears "+
    "FROM "+
    "[Hidden_Assets] left join "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] on [Hidden_Assets].[asset_GUID]="+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].[GUID]"+
    ""; 
  
  //  String sqlWhere="AND [Hidden_Assets].[asset_GUID] not in( select top "+offset+" [Hidden_Assets].[asset_GUID] FROM [Hidden_Assets] left join Singleton.ROOMDATABASE"+".[dbo].[RoomInfo] on [Hidden_Assets].[asset_GUID]=Singleton.ROOMDATABASE"+".[dbo].[RoomInfo].[GUID] ORDER BY [Hidden_Assets].[asset_GUID]) ORDER BY [Hidden_Assets].[asset_GUID]";
	
  		StringBuilder whereCommand = new StringBuilder(); 
       
  		Class<?>[] classeNames=new Class<?>[2];
  
  		classeNames[0]=Hidden_Assets.class;
  		classeNames[1]=RoomInfo.class;
  		
    	List params=new ArrayList<>();
        List paramsCount=new ArrayList<>();
    	
    	if(!search.isEmpty()){
			String[] where=TransMapToString.get(search);	
			
			List wheres=new ArrayList<String[]>();
			
			String[] columnWhere;
			
			wheres.add(where);
             			        
	          Iterator<String[]> iterator=wheres.iterator();
	          System.out.println("wheres="+wheres);
	          
	          while (iterator.hasNext()) {
	        	  columnWhere=iterator.next();
	        	  int k=1;
	        	  for(String whereterm:columnWhere){
	            	  if(k%2==0){
	            		//  System.out.println("鍋舵暟");
	            		//  whereCommand.append(whereterm+"\n  AND ");
	            	   }else{
	            		//  System.out.println("濂囨暟");
	            		//  whereCommand.append("\n   "+whereterm);
	            		   whereCommand.append(whereterm+"? \n  AND ");
	            	   }
	               k++;
	               System.out.println("whereCommand="+whereCommand);
	              }
	            }
	          
	          iterator=wheres.iterator();
	          while (iterator.hasNext()) {
	        	  columnWhere=iterator.next();
	        	  int k=1;
	        	  for(String whereterm:columnWhere){
	            	  if(k%2==0){
	            		//  System.out.println("鍋舵暟");
	            		  params.add(whereterm);
	            	   }else{
	            		//  System.out.println("濂囨暟");
	            		//  whereCommand.append("\n   "+whereterm);
	            	   }
	               k++;
	              }
			  }
	          
	          iterator=wheres.iterator();
		       while (iterator.hasNext()) {
		        	  columnWhere=iterator.next();
		        	  int k=1;
		        	  for(String whereterm:columnWhere){
		            	  if(k%2==0){
		            		//  System.out.println("鍋舵暟");
		            		  params.add(whereterm);
		            		  paramsCount.add(whereterm);
		            	   }else{
		            		//  System.out.println("濂囨暟");
		            		//  whereCommand.append("\n   "+whereterm);
		            	   }
		               k++;
		              }
				  }
	          
		}

    	System.out.println("params="+params);
    	
    	if(!search.isEmpty()){
    		   sql=sql+   //sqlserver鍒嗛〉闇�瑕佸湪top涔熷姞涓妛here鏉′欢
    	         		 "\n  where "+whereCommand+
    	                  " [Hidden_Assets].asset_GUID not in("+
    	                  " select top "+offset+" [Hidden_Assets].asset_GUID  FROM [Hidden_Assets] where "+
    	                   whereCommand.substring(0,whereCommand.length()-7)+")";
    	}
    	
    	List list=this.getJdbcTemplate().query(sql,params.toArray(), new RowMappersJoin(classeNames,hidden_Assets_Join.getClass()));
       
    	map.put("rows", list);
		MyTestUtil.print(list);
		
		String countSql="select count(*) FROM "+
			    "[Hidden_Assets] left join "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] on [Hidden_Assets].[asset_GUID]="+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].[GUID]";
		
		if(!search.isEmpty()){
			countSql=countSql+   //sqlserver鍒嗛〉闇�瑕佸湪top涔熷姞涓妛here鏉′欢
	          		 "\n  where "+
	                    whereCommand.substring(0,whereCommand.length()-7);
    	}
		
    	Map amount=this.getJdbcTemplate().queryForMap(countSql,paramsCount.toArray());
    	
		map.put("total", amount.get(""));
		
		return map;

	}
	
	@Override
	public Map findHideenByAsset(Integer limit, Integer offset, String sort, String order, Map<String, String> search) {
		// TODO Auto-generated method stub
		
		Hidden_Assets hidden_Assets=new Hidden_Assets();
		
		hidden_Assets.setLimit(limit);
		hidden_Assets.setOffset(offset);
		hidden_Assets.setSort(sort);
		hidden_Assets.setOffset(offset);
		hidden_Assets.setNotIn("id");
		
		Hidden hidden=new Hidden();
		
		hidden.setLimit(limit);
		hidden.setOffset(offset);
		hidden.setSort(sort);
		hidden.setOffset(offset);
		hidden.setNotIn("id");
			
		Object[] objects={hidden_Assets,hidden};
		
		Map map=new HashMap<String, Object>();
		
		Hidden_Join hidden_Join=new Hidden_Join();
		

  String sql="SELECT top "+limit+
    "[Hidden_Assets].id,"+
    "[Hidden_Assets].asset_GUID,"+
    "[Hidden_Assets].hidden_GUID,"+
    "[Hidden].[GUID],"+
    "[Hidden].[exist],"+
    "[Hidden].[name],"+
    "[Hidden].[hidden_level],"+
    "[Hidden].[detail],"+
    "[Hidden].[happen_time],"+
    "[Hidden].[progress],"+
    "[Hidden].[principal],"+
    "[Hidden].[type],"+
    "[Hidden].[state],"+
    "[Hidden].[remark],"+
    "[Hidden].[update_time],"+
    "[Hidden].[date],"+
    "[Hidden].[campusAdmin],"+
    "[Hidden].[terminal],"+
    "[ManageRegion] "+
    "FROM "+
    "[Hidden_Assets] left join [Hidden] on [Hidden_Assets].[hidden_GUID]=[Hidden].[GUID]"+
    ""; 
  
  //  String sqlWhere="AND [Hidden_Assets].[asset_GUID] not in( select top "+offset+" [Hidden_Assets].[asset_GUID] FROM [Hidden_Assets] left join Singleton.ROOMDATABASE"+".[dbo].[RoomInfo] on [Hidden_Assets].[asset_GUID]=Singleton.ROOMDATABASE"+".[dbo].[RoomInfo].[GUID] ORDER BY [Hidden_Assets].[asset_GUID]) ORDER BY [Hidden_Assets].[asset_GUID]";
	
  		StringBuilder whereCommand = new StringBuilder(); 
       
  		Class<?>[] classeNames=new Class<?>[2];
  
  		classeNames[0]=Hidden_Assets.class;
  		classeNames[1]=Hidden.class;
  		
    	List params=new ArrayList<>();
        List paramsCount=new ArrayList<>();
    	
    	if(!search.isEmpty()){
			String[] where=TransMapToString.get(search);	
			
			List wheres=new ArrayList<String[]>();
			
			String[] columnWhere;
			
			wheres.add(where);
             			        
	          Iterator<String[]> iterator=wheres.iterator();
	          System.out.println("wheres="+wheres);
	          
	          while (iterator.hasNext()) {
	        	  columnWhere=iterator.next();
	        	  int k=1;
	        	  for(String whereterm:columnWhere){
	            	  if(k%2==0){
	            		//  System.out.println("鍋舵暟");
	            		//  whereCommand.append(whereterm+"\n  AND ");
	            	   }else{
	            		//  System.out.println("濂囨暟");
	            		//  whereCommand.append("\n   "+whereterm);
	            		   whereCommand.append(whereterm+"? \n  AND ");
	            	   }
	               k++;
	               System.out.println("whereCommand="+whereCommand);
	              }
	            }
	          
	          iterator=wheres.iterator();
	          while (iterator.hasNext()) {
	        	  columnWhere=iterator.next();
	        	  int k=1;
	        	  for(String whereterm:columnWhere){
	            	  if(k%2==0){
	            		//  System.out.println("鍋舵暟");
	            		  params.add(whereterm);
	            	   }else{
	            		//  System.out.println("濂囨暟");
	            		//  whereCommand.append("\n   "+whereterm);
	            	   }
	               k++;
	              }
			  }
	          
	          iterator=wheres.iterator();
		       while (iterator.hasNext()) {
		        	  columnWhere=iterator.next();
		        	  int k=1;
		        	  for(String whereterm:columnWhere){
		            	  if(k%2==0){
		            		//  System.out.println("鍋舵暟");
		            		  params.add(whereterm);
		            		  paramsCount.add(whereterm);
		            	   }else{
		            		//  System.out.println("濂囨暟");
		            		//  whereCommand.append("\n   "+whereterm);
		            	   }
		               k++;
		              }
				  }
	          
		}

    	System.out.println("params="+params);
    	
    	if(!search.isEmpty()){
    		   sql=sql+   //sqlserver鍒嗛〉闇�瑕佸湪top涔熷姞涓妛here鏉′欢
    	         		 "\n  where "+whereCommand+
    	                  " [Hidden_Assets].hidden_GUID not in("+
    	                  " select top "+offset+" [Hidden_Assets].hidden_GUID  FROM [Hidden_Assets] where "+
    	                   whereCommand.substring(0,whereCommand.length()-7)+")";
    	}
    	
    	List list=this.getJdbcTemplate().query(sql,params.toArray(), new RowMappersJoin(classeNames,hidden_Join.getClass()));
       
    	map.put("rows", list);
		MyTestUtil.print(list);
		
		String countSql="select count(*) FROM "+
			    "[Hidden_Assets] left join [Hidden] on [Hidden_Assets].[hidden_GUID]=[Hidden].[GUID]";
		
		if(!search.isEmpty()){
			countSql=countSql+   //sqlserver鍒嗛〉闇�瑕佸湪top涔熷姞涓妛here鏉′欢
	          		 "\n  where "+
	                    whereCommand.substring(0,whereCommand.length()-7);
    	}
		
    	Map amount=this.getJdbcTemplate().queryForMap(countSql,paramsCount.toArray());
    	
		map.put("total", amount.get(""));
		
		return map;

	}

	@Override
	public Integer deleteHidden_Assets(Hidden_Assets hidden_Assets) {
		// TODO Auto-generated method stub
		return DeleteExe.get(this.getJdbcTemplate(), hidden_Assets);
	}

	@Override
	public Integer findNotHidden() {
		// TODO Auto-generated method stub
		Hidden hidden=new Hidden();
		
		String[] where={"[Hidden].exist !="," 0 ","[Hidden].progress ="," 0 "};
		hidden.setWhere(where);
		
		int i=(int) SelectExe.getCount(this.getJdbcTemplate(), hidden).get("");
		
		return i;
	}

	@Override
	public Integer findInHidden() {
		// TODO Auto-generated method stub
		Hidden hidden=new Hidden();
		
		String[] where={"[Hidden].exist !="," 0 ","[Hidden].progress >","0"
				," [Hidden].progress <","1"};
		hidden.setWhere(where);
		
		int i=(int) SelectExe.getCount(this.getJdbcTemplate(), hidden).get("");
		
		return i;
	}

	@Override
	public Integer findAllAssets() {
		// TODO Auto-generated method stub
		RoomInfo roomInfo=new RoomInfo();
		
		int i=(int) SelectExe.getCount(this.getJdbcTemplate(), roomInfo).get("");
		
		String[] where={Singleton.ROOMDATABASE+".[dbo].[RoomInfo].State !=","已划拨"};
		
		roomInfo.setWhere(where);
		
		String sql="select asset_GUID "+
				"FROM [Hidden_Assets]  group by asset_GUID "	;
		
		List list=this.getJdbcTemplate().query(sql,new hidden_AssetsRowMapper());
		
		int count=list.size();
		
		int ii=i-count;
		
		return ii;
	}

	@Override
	public Integer findAllAssetsHidden() {
		// TODO Auto-generated method stub
		
		String sql="select asset_GUID "+
				"FROM [Hidden_Assets]  group by asset_GUID "	;
		
		List list=this.getJdbcTemplate().query(sql,new hidden_AssetsRowMapper());
		
		int count=list.size();
		
		return count;
	}
	
	class hidden_AssetsRowMapper implements RowMapper<Hidden_Assets> {

		@Override
		public Hidden_Assets mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			
			Hidden_Assets hidden_Assets=new Hidden_Assets();
			
			hidden_Assets.setAsset_GUID(rs.getString("asset_GUID"));

			return hidden_Assets;
		}
		
	}
	
	@Override
	public Integer findSuccessHidden() {
		// TODO Auto-generated method stub
		Hidden hidden=new Hidden();
		
		String[] where={"[Hidden].exist !="," 0 ","[Hidden].progress >","0"
				," [Hidden].progress >= ","1"};
		hidden.setWhere(where);
		
		int i=(int) SelectExe.getCount(this.getJdbcTemplate(), hidden).get("");
		
		return i;
	}
	
	@Override
	public String findLastHidden() {
		// TODO Auto-generated method stub
		
		Hidden hidden=new Hidden();
		
		String sql="SELECT MAX([date]) FROM [Hidden_Check] where [Hidden_Check].exist !=0";
		
		List list=this.getJdbcTemplate().query(sql,new hiddenRowMapper());
		
		hidden=(Hidden) list.get(0);
		
		String s=String.valueOf(hidden.getHappen_time());
		
		return s;
	}

	class hiddenRowMapper implements RowMapper<Hidden> {
        //rs涓鸿繑鍥炵粨鏋滈泦锛屼互姣忚涓哄崟浣嶅皝瑁呯潃
        public Hidden mapRow(ResultSet rs, int rowNum) throws SQLException {    
            Hidden hidden=new Hidden();
        	Date date=rs.getDate("");
        	hidden.setHappen_time(date);
            return hidden;
        }
    }
	
	@Override
	public String findIgnoreHidden() {
		// TODO Auto-generated method stub
		Hidden hidden=new Hidden();
		
		String sql="SELECT MAX([happen_time]) FROM [Hidden] where [Hidden].exist !=0";
		
		List list=this.getJdbcTemplate().query(sql,new hiddenRowMapper());
		
		hidden=(Hidden) list.get(0);
		
		String s=String.valueOf(hidden.getHappen_time());
		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		
		Date begin=new Date();
		
		int between=0;
		
		try {
			Date endDate=df.parse(s);
			between=(int) ((begin.getTime()-endDate.getTime())/(1000*3600*24));
			System.out.println("time=  "+begin.getTime()+"          "+endDate.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(between<0){
			between=0;
		}
		
		return String.valueOf(between);
	}

	@Override
	public Map<String, Object> hiddenQuery(Integer hiddenLevel) {
		// TODO Auto-generated method stub
		
		/*
		String sql="SELECT top 5 "+    
					"[Hidden_Data].GUID, "+
				    "[Hidden_Data].URI, "+
					"[Hidden_Data].date, "+
				    "[Hidden].name, "+
					"[Hidden].detail, "+
				    "[Hidden].hidden_level, "+
					"[Hidden].progress "+
					"FROM "+
					"[Hidden_Data] left join [Hidden] on [Hidden_Data].GUID=[Hidden].GUID "+  
					"where  [Hidden].hidden_level = "+hiddenLevel+" "+
					"AND ([Hidden_Data].TYPE ='png ' "+
					"OR [Hidden_Data].TYPE ='jpg ' "+
					"OR [Hidden_Data].TYPE ='jpeg ' "+
					"OR [Hidden_Data].TYPE ='gif ' )"+
					"group by [Hidden_Data].GUID,[Hidden_Data].URI,[Hidden_Data].date, "+
					"[Hidden].name,[Hidden].detail,[Hidden].hidden_level,[Hidden].progress "+
					"order by [Hidden_Data].date desc ";
		*/
		
		String sql1="SELECT top 5 [Hidden_Data].GUID "+
				 	"FROM [Hidden_Data] left join [Hidden] on [Hidden_Data].GUID=[Hidden].GUID "+
				 	"where  [Hidden].hidden_level = "+hiddenLevel+" "+
				 	"AND [Hidden].exist!=0 "+
				 	"AND ([Hidden_Data].TYPE ='png ' OR [Hidden_Data].TYPE ='jpg ' OR [Hidden_Data].TYPE ='jpeg ' OR [Hidden_Data].TYPE ='gif ' ) "+
				 	"group by [Hidden_Data].GUID ";
		
		List hidden_Data_Joins=this.getJdbcTemplate().query(sql1,new hiddenQueryRowMapper1());
		
        String pathRoot = System.getProperty("user.home");
		
		String filePath=pathRoot+Singleton.filePath;
		
		List<String> GUIDs=new ArrayList<String>();
		List<String> names=new ArrayList<String>();
		List<String> details=new ArrayList<String>();
		List<Double> progress=new ArrayList<Double>();
		List<Date> dates=new ArrayList<Date>();
		List<byte[]> fileBytes=new ArrayList<byte[]>();
				
		Iterator<Hidden_Data_Join> iterator=hidden_Data_Joins.iterator();
		
		while(iterator.hasNext()){			
			
			Hidden_Data_Join hidden_Data_Join1=iterator.next();
			
			String GUID=hidden_Data_Join1.getGUID();
			
			String sql2="SELECT top 1 "+    
					"[Hidden_Data].GUID, "+
				    "[Hidden_Data].URI, "+
					"[Hidden_Data].date, "+
				    "[Hidden].name, "+
					"[Hidden].detail, "+
				    "[Hidden].hidden_level, "+
					"[Hidden].progress "+
					"FROM "+
					"[Hidden_Data] left join [Hidden] on [Hidden_Data].GUID=[Hidden].GUID "+  
					"where [Hidden_Data].GUID='"+GUID+"'  "+
					"AND ([Hidden_Data].TYPE ='png ' OR [Hidden_Data].TYPE ='jpg ' OR [Hidden_Data].TYPE ='jpeg ' OR [Hidden_Data].TYPE ='gif ' ) "+
					"order by [Hidden_Data].date desc ";
			
			List hidden_Data_Joins2=this.getJdbcTemplate().query(sql2,new hiddenQueryRowMapper2());
			
			try{
			Hidden_Data_Join hidden_Data_Join2=(Hidden_Data_Join) hidden_Data_Joins2.get(0);
			
			File file=new File(filePath+"\\"+hidden_Data_Join2.getURI());
			
			byte[] fileByte=FileConvect.fileToByte(file);
			
			GUIDs.add(hidden_Data_Join2.getGUID());
			names.add(hidden_Data_Join2.getName());
			details.add(hidden_Data_Join2.getDetail());
			dates.add(hidden_Data_Join2.getDate());
			progress.add(hidden_Data_Join2.getProgress());
			fileBytes.add(fileByte);
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
		Map<String, Object> map=new HashMap<>();
		
		map.put("GUIDs", GUIDs);
		map.put("names", names);
		map.put("details", details);
		map.put("dates", dates);
		map.put("progress", progress);
		map.put("fileBytes", fileBytes);
		MyTestUtil.print(map);
		return map;
	}

	class hiddenQueryRowMapper1 implements RowMapper<Hidden_Data_Join> {
        //rs涓鸿繑鍥炵粨鏋滈泦锛屼互姣忚涓哄崟浣嶅皝瑁呯潃
        public Hidden_Data_Join mapRow(ResultSet rs, int rowNum) throws SQLException {    
            Hidden_Data_Join hidden_Data_Join=new Hidden_Data_Join();
        	hidden_Data_Join.setGUID(rs.getString("GUID"));
            return hidden_Data_Join;
        }
    }
	
	class hiddenQueryRowMapper2 implements RowMapper<Hidden_Data_Join> {
        //rs涓鸿繑鍥炵粨鏋滈泦锛屼互姣忚涓哄崟浣嶅皝瑁呯潃
        public Hidden_Data_Join mapRow(ResultSet rs, int rowNum) throws SQLException {    
            Hidden_Data_Join hidden_Data_Join=new Hidden_Data_Join();
        	hidden_Data_Join.setGUID(rs.getString("GUID"));
        	hidden_Data_Join.setURI(rs.getString("URI"));
        	hidden_Data_Join.setName(rs.getString("name"));
        	hidden_Data_Join.setDetail(rs.getString("detail"));
        	hidden_Data_Join.setHidden_level(rs.getInt("hidden_level"));
        	hidden_Data_Join.setProgress(rs.getDouble("progress"));
        	hidden_Data_Join.setDate(rs.getDate("date"));
            return hidden_Data_Join;
        }
    }

	@Override
	public List selectManageRegion() {
		// TODO Auto-generated method stub
		
		String sql="SELECT [ManageRegion] "+
                    "FROM "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] group by [ManageRegion]";
		
		List list=this.getJdbcTemplate().query(sql, new roomInfoRowMapper());
		
		return list;
	}

	@Override
	public List selectRoomProperty(){
		// TODO Auto-generated method stub
		
		String sql="SELECT [RoomProperty] "+
		                    "FROM "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] group by [RoomProperty]";
				
		List list=this.getJdbcTemplate().query(sql, new roomInfoRowMapper());
				
		return list;
	}
	
	@Override
	public List selectFareItem() {
		// TODO Auto-generated method stub
		String sql="SELECT FareItem "+
                "FROM "+Singleton.ROOMDATABASE+".[dbo].[ChartInfo] left join "+
                Singleton.ROOMDATABASE+".[dbo].[RoomInfo] on "+Singleton.ROOMDATABASE+".[dbo].[ChartInfo].GUID="+
                Singleton.ROOMDATABASE+".[dbo].[RoomInfo].ChartGUID left join "+
                "[Position] on "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID=[Position].GUID "+
                "where IsHistory =0 and FareItem!='' and Position.is_roomInfo=1 "+
                "group by FareItem ";
	
		List list=this.getJdbcTemplate().query(sql, new chartInfoRowMapper());
	
		return list;
	}
	
	class chartInfoRowMapper implements RowMapper<ChartInfo> {

		@Override
		public ChartInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			ChartInfo chartInfo=new ChartInfo();
			chartInfo.setFareItem(rs.getString("FareItem"));
			return chartInfo;
		}
		
	}
	
	@Override
	public List selectDangerClassification() {
		// TODO Auto-generated method stub
		String sql="SELECT [DangerClassification] "+
                "FROM "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] group by [DangerClassification]";
	
		List list=this.getJdbcTemplate().query(sql, new roomInfoRowMapper());
	
		return list;
	}
	
	class roomInfoRowMapper implements RowMapper<RoomInfo> {
        //rs涓鸿繑鍥炵粨鏋滈泦锛屼互姣忚涓哄崟浣嶅皝瑁呯潃
        public RoomInfo mapRow(ResultSet rs, int rowNum) {    
        	RoomInfo roomInfo=new RoomInfo();
        	try{
        		roomInfo.setManageRegion(rs.getString("ManageRegion"));               		
        	}catch (SQLException e) {
				// TODO: handle exception
			}
        	try{
        		roomInfo.setRoomProperty(rs.getString("RoomProperty"));
        	}catch (Exception e) {
				// TODO: handle exception
			}
        	try{
        		roomInfo.setDangerClassification(rs.getString("DangerClassification"));
        	}catch (Exception e) {
				// TODO: handle exception
			}
        	return roomInfo;
        }
    }

	@Override
	public ChartInfo getChartInfoByIDNo(String IDNo) {
		// TODO Auto-generated method stub
		System.out.println("IDNo="+IDNo);
		ChartInfo chartInfo=new ChartInfo();
		chartInfo.setLimit(10);
		chartInfo.setOffset(0);
		chartInfo.setNotIn("GUID");
		String[] where = {Singleton.ROOMDATABASE+".[dbo].[ChartInfo].IDNo=",IDNo};
		chartInfo.setWhere(where);
		
		List list=SelectExe.get(this.getJdbcTemplate(), chartInfo);
		
		MyTestUtil.print(list);
		
		ChartInfo chartInfo2 = null;
		
		try {
		 chartInfo2=(ChartInfo) list.get(0);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return chartInfo2;
	}

	@Override
	public ChartInfo getChartInfoByChartGUID(String chartGUID) {
		// TODO Auto-generated method stub
		ChartInfo chartInfo=new ChartInfo();
		chartInfo.setLimit(10);
		chartInfo.setOffset(0);
		chartInfo.setNotIn("GUID");
		String[] where = {Singleton.ROOMDATABASE+".[dbo].[ChartInfo].GUID=",chartGUID};
		chartInfo.setWhere(where);
		
		List list=SelectExe.get(this.getJdbcTemplate(), chartInfo);
		
		MyTestUtil.print(list);
		
		ChartInfo chartInfo2 = null;
		
		try {
		 chartInfo2=(ChartInfo) list.get(0);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return chartInfo2;
	}
	
	@Override
	public Map findChartInfo_HireListByIDNo(String IDNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map findHireListByGUID(String GUID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List findHiddenByYear() {
		// TODO Auto-generated method stub
		String sql="SELECT convert(varchar(4),date,120) as year"+
					" FROM [Hidden] where date is not null "+
					" group by convert(varchar(4),date,120) order by year desc";
		
		List years=this.getJdbcTemplate().query(sql, new HiddenByYear());
		
		return years;
	}
	
	@Override
	public List findAssetByYear() {
		// TODO Auto-generated method stub
		String sql="SELECT convert(varchar(4),InDate,120) as year"+
				" FROM "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] where InDate is not null "+
				" group by convert(varchar(4),InDate,120) order by year desc";
	
		List years=this.getJdbcTemplate().query(sql, new HiddenByYear());
	
		MyTestUtil.print(years);
	
		return years;
	}
	
	
	class HiddenByYear implements RowMapper<String> {

		@Override
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			
			String year=rs.getString("year");
			
			return year;
		}
		
	}

	@Override
	public List findHiddenByMonthOfYear(String year) {
		// TODO Auto-generated method stub
		String sql="SELECT convert(varchar(7),date,120) as year ,COUNT(*) as amount "+
					"FROM [Hidden] where date is not null "+ 
					"and convert(varchar(4),date,120)= "+year+" "+ 
					"group by convert(varchar(7),date,120)";
		
		List list=this.getJdbcTemplate().query(sql,new HiddenByMonth());
		
		return list;
	}
	
	class HiddenByMonth implements RowMapper<HiddenByMonthAmount> {

		@Override
		public HiddenByMonthAmount mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			HiddenByMonthAmount hiddenByMonthAmount=new HiddenByMonthAmount();
			
			hiddenByMonthAmount.setYear(rs.getString("year"));
			hiddenByMonthAmount.setAmount(rs.getInt("amount"));
			
			return hiddenByMonthAmount;
		}
		
	}

	@Override
	public List findHiddenAssetsByMonthOfYear(String year) {
		// TODO Auto-generated method stub
		
		String sql="SELECT convert(varchar(7),InDate,120) as year ,COUNT(*) as amount "+
					"FROM "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] where InDate is not null "+
					"and convert(varchar(4),InDate,120) = "+year+" group by convert(varchar(7),InDate,120)";
		
		List list=this.getJdbcTemplate().query(sql,new HiddenAssetByMonth());
		
		String sql2="SELECT convert(varchar(7),InDate,120) as year ,COUNT(*) as amount "+
					"FROM "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] left join [Hidden_Assets] on "+
					Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID=[Hidden_Assets].asset_GUID "+
					"where InDate is not null and [Hidden_Assets].asset_GUID is not null "+
					"and convert(varchar(4),InDate,120) = "+year+" group by convert(varchar(7),InDate,120)";
		
		List list2=this.getJdbcTemplate().query(sql2,new HiddenAssetByMonth());
		
		Iterator<HiddenAssetByMonthAmount> iterator=list.iterator();
		
		int i=0;
		
		while (iterator.hasNext()) {
			
			HiddenAssetByMonthAmount hiddenAssetByMonthAmount=iterator.next();
			
			String y=hiddenAssetByMonthAmount.getYear();
			
			Iterator<HiddenAssetByMonthAmount> iterator2=list2.iterator();
			
			while(iterator2.hasNext()){
				HiddenAssetByMonthAmount hiddenAssetByMonthAmount2=iterator2.next();
				
				if(y.equals(hiddenAssetByMonthAmount2.getYear())){
					hiddenAssetByMonthAmount.setHiddenAmount(hiddenAssetByMonthAmount2.getAmount());
					list.set(i, hiddenAssetByMonthAmount);
				}
			}
			
			i++;
			
		}
		
		return list;
	}
	
	class HiddenAssetByMonth implements RowMapper<HiddenAssetByMonthAmount>{

		@Override
		public HiddenAssetByMonthAmount mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			
			HiddenAssetByMonthAmount hiddenAssetByMonthAmount=
					new HiddenAssetByMonthAmount();
			
			hiddenAssetByMonthAmount.setYear(rs.getString("year"));
			hiddenAssetByMonthAmount.setAmount(rs.getInt("amount"));
			
			return hiddenAssetByMonthAmount;
		}
		
	}

	@Override
	public Integer getAllAssetByHidden_GUID(String guid) {
		// TODO Auto-generated method stub
		Hidden_Assets hidden_Assets=new Hidden_Assets();
		
		String[] where={"asset_GUID=",guid};
		
		hidden_Assets.setWhere(where);
		
		int count=(int) SelectExe.getCount(this.getJdbcTemplate(), hidden_Assets).get("");
		
		return count;
	}

	@Override
	public Map getTestOr(Map search) {
		// TODO Auto-generated method stub

		Hidden_Check hidden_Check=new Hidden_Check();
	
		hidden_Check.setOffset(0);
		hidden_Check.setLimit(1000);
		hidden_Check.setNotIn("GUID");
		hidden_Check.setWhereTerm("OR");
		
		RoomInfo roomInfo=new RoomInfo();
		
		roomInfo.setOffset(0);
		roomInfo.setLimit(1000);
		roomInfo.setNotIn("GUID");
		roomInfo.setWhereTerm("OR");
		
		if(!search.isEmpty()){
		    String[] where=TransMapToString.get(search);
		    hidden_Check.setWhere(where);
		    roomInfo.setWhere(where);
		}
		
		Hidden_Check_Join hidden_Check_Join=new Hidden_Check_Join();
		
		Object[] objects={roomInfo,hidden_Check};
		
		String[] join={"GUID"};
		
		List list=SelectJoinExe.get(this.getJdbcTemplate(), objects, hidden_Check_Join, join);
		
		int count=(int) SelectJoinExe.getCount(this.getJdbcTemplate(), objects,join).get("");
		
		Map map=new HashMap<>();
		
		map.put("rows", list);
		
		map.put("total", count);
		
		return map;
	}

	@Override
	public Map<String, Object> findAllRoomInfo_Position(Integer limit, Integer offset, String sort, String order,
			String term,Map search) {
		// TODO Auto-generated method stub
				String sql0="SELECT TOP "+limit+" "+
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
					    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].PropertyCardUnit ,"+	
					    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].DangerClassification ,"+
					    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].ChartGUID ,"+
					    Singleton.ROOMDATABASE+".[dbo].[ChartInfo].Hire ,"+
					    Singleton.ROOMDATABASE+".[dbo].[ChartInfo].FareItem ,"+
					    Singleton.ROOMDATABASE+".[dbo].[ChartInfo].Charter ,"+
					    Singleton.ROOMDATABASE+".[dbo].[ChartInfo].Phone "+
						"FROM "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] left join  [Position]"+
						"on "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID = [Position].GUID "+
						"left join "+Singleton.ROOMDATABASE+".[dbo].[ChartInfo] "+
						"on "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].ChartGUID = "
						+Singleton.ROOMDATABASE+".[dbo].[ChartInfo].GUID "+
						"WHERE "+
						"([RoomInfo].State = '已出租' or [RoomInfo].State = '不可出租' or [RoomInfo].State = '空置' ) ";
						
			    String sql01="AND "+
						Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID not in( select top "+offset+" "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID from "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] left join  [Position]"+
						"on "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID = [Position].GUID "+
						"WHERE "+ 
						"([RoomInfo].State = '已出租' or [RoomInfo].State = '不可出租' or [RoomInfo].State = '空置' ) ";
			
				String sql1="ORDER BY  "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].Num ";
			
				String sql;
			
				String sql2="SELECT count(*) "+				   
						"FROM "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] left join  [Position]"+
						"on "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID = [Position].GUID "+
						"left join "+Singleton.ROOMDATABASE+".[dbo].[ChartInfo] "+
						"on "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].ChartGUID = "+
						Singleton.ROOMDATABASE+".[dbo].[ChartInfo].GUID "+
						"WHERE "+
						"([RoomInfo].State = '已出租' or [RoomInfo].State = '不可出租' or [RoomInfo].State = '空置' ) ";
				
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
					    	sb.append("'"+str+"'");
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
			
				ChartInfo chartInfo=new ChartInfo();
				
				Object[] objects={roomInfo,chartInfo,position};
			
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
	public Map<String, Object> findAllRoomInfoCheckDateNULL(Integer limit, Integer offset, String sort, String order,
			String term,String searchTerm,Map search,Integer type) {
		
		String checkName = null;
		
		if(type==1){
			checkName="hidden_check_date";
		}else{
			checkName="asset_check_date";
		}
		
		// TODO Auto-generated method stub
		String sql0="SELECT TOP "+limit+" "+
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
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].PropertyCardUnit ,"+	
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].ChartGUID ,"+
			    Singleton.ROOMDATABASE+".[dbo].[ChartInfo].Charter ,"+
			    Singleton.ROOMDATABASE+".[dbo].[ChartInfo].Phone "+
				"FROM "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] left join  [Position]"+
				"on "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID = [Position].GUID "+
				"left join "+Singleton.ROOMDATABASE+".[dbo].[ChartInfo] "+
				"on "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].ChartGUID = "
				+Singleton.ROOMDATABASE+".[dbo].[ChartInfo].GUID "+
				"WHERE "+
				"(([RoomInfo].State = '已出租' or [RoomInfo].State = '不可出租' or [RoomInfo].State = '空置' ) ";
				
	    String sql01="AND "+
				Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID not in( select top "+offset+" "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID from "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] left join  [Position]"+
				"on "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID = [Position].GUID "+
				"WHERE "+ 
				"(([RoomInfo].State = '已出租' or [RoomInfo].State = '不可出租' or [RoomInfo].State = '空置' )  ";
	
		String sql1="ORDER BY  "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].Num ";
	
		String sql;
	
		String sql2="SELECT count(*) "+				   
				"FROM "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] left join  [Position]"+
				"on "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID = [Position].GUID "+
				"WHERE "+
				"(([RoomInfo].State = '已出租' or [RoomInfo].State = '不可出租' or [RoomInfo].State = '空置' ) ";
		
		System.out.println("search="+search);
		
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
			    	sb.append("'"+str+"'");
			    	sb.append(" "+term+" ");
			    }
			    i++;
			}
			String s = sb.toString();
			
			String serach=s.substring(0,s.length()-4);
			
			System.out.println("serach="+serach);
			
			if(searchTerm!=null&&!searchTerm.equals("")){
				sql=sql0+" AND "+serach+") OR ( ([RoomInfo].State = '已出租' or [RoomInfo].State = '不可出租' or [RoomInfo].State = '空置' ) AND [RoomInfo]."+checkName+" is null AND "+searchTerm+")"+sql01+" AND "+serach+" )"
						+ "OR ( ([RoomInfo].State = '已出租' or [RoomInfo].State = '不可出租' or [RoomInfo].State = '空置' ) AND [RoomInfo]."+checkName+" is null AND "+searchTerm+") "+sql1+")"+sql1;
				sql2=sql2+" AND "+serach+") OR ( ([RoomInfo].State = '已出租' or [RoomInfo].State = '不可出租' or [RoomInfo].State = '空置' ) AND [RoomInfo]."+checkName+" is null AND "+searchTerm+")";
			}else{
				sql=sql0+" AND "+serach+") OR ( ([RoomInfo].State = '已出租' or [RoomInfo].State = '不可出租' or [RoomInfo].State = '空置' ) AND [RoomInfo]."+checkName+" is null )"+sql01+" AND "+serach+" )"
					+ "OR ( ([RoomInfo].State = '已出租' or [RoomInfo].State = '不可出租' or [RoomInfo].State = '空置' ) AND [RoomInfo]."+checkName+" is null ) "+sql1+")"+sql1;
				sql2=sql2+" AND "+serach+") OR ( ([RoomInfo].State = '已出租' or [RoomInfo].State = '不可出租' or [RoomInfo].State = '空置' ) AND [RoomInfo]."+checkName+" is null )";
			}
		}
	
		System.out.println("sql="+sql);
	
		RoomInfo_Position roomInfo_Position=new RoomInfo_Position();
	
		Position position=new Position();		
	
		RoomInfo roomInfo=new RoomInfo();
	
		ChartInfo chartInfo=new ChartInfo();
		
		Object[] objects={roomInfo,chartInfo,position};
	
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
	public Map<String, Object> findAllHire(String term,Map search) {
		// TODO Auto-generated method stub
	
		String sql2="SELECT sum([TotalHire]) as allHire "+				   
				"FROM "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] left join  [Position]"+
				"on "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID = [Position].GUID "+
				"left join "+Singleton.ROOMDATABASE+".[dbo].[ChartInfo] "+
				"on "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].ChartGUID = "+
				Singleton.ROOMDATABASE+".[dbo].[ChartInfo].GUID "+
				"WHERE "+
				"([RoomInfo].State = '已出租' or [RoomInfo].State = '不可出租' or [RoomInfo].State = '空置' ) "+
				"AND "+Singleton.ROOMDATABASE+".[dbo].[ChartInfo].IsHistory=0 ";
		
		if(search.equals("")||search.isEmpty()){

		}else{
			
			StringBuilder sb = new StringBuilder();
			
			String[] where=TransMapToString.get(search);
			
			int i=0;
			for(String str : where){
			    
			    if(i%2==0){
			    	sb.append(str);
			    }else{
			    	sb.append("'"+str+"'");
			    	sb.append(" "+term+" ");
			    }
			    i++;
			}
			String s = sb.toString();
			
			String serach=s.substring(0,s.length()-4);
			
			System.out.println("serach="+serach);
			
			sql2=sql2+" AND ("+serach+")";
		}
	
		RoomInfo_Position roomInfo_Position=new RoomInfo_Position();
	
		Position position=new Position();		
	
		RoomInfo roomInfo=new RoomInfo();
	
		ChartInfo chartInfo=new ChartInfo();
		
		Object[] objects={roomInfo,chartInfo,position};
	
		Map map=new HashMap<>();
	
		try{
			List list2=this.getJdbcTemplate().query(sql2,new allHire());
			Double allHire=(Double) list2.get(0)/10000;
			java.text.DecimalFormat   df=new   java.text.DecimalFormat("######0.00");   			
			map.put("allHire", df.format(allHire)+"万元");
			//MyTestUtil.print(list);
		}catch (Exception e) {
			// TODO: handle exception
		}

		return map;
	}

	
	class allHire implements RowMapper<Double> {

		@Override
		public Double mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			
			Double allHire=rs.getDouble("allHire");
			
			return allHire;
		}
		
	}

	@Override
	public Map<String, Object> getCountCheckByGUID(String guid) {
		// TODO Auto-generated method stub
		Map map=new HashMap<>();
		
		String[] where={"guid=",guid};
		
		Hidden_Check hidden_Check=new Hidden_Check();
		
		hidden_Check.setWhere(where);
		
		int hiddenCheck=(int) SelectExe.getCount(this.getJdbcTemplate(), hidden_Check).get("");
		
		Assets_Check assets_Check=new Assets_Check();
		
		assets_Check.setWhere(where);
		
		int assetCheck=(int) SelectExe.getCount(this.getJdbcTemplate(), assets_Check).get("");
		
		map.put("hiddenCheck", hiddenCheck);
		map.put("assetCheck", assetCheck);
		
		return map;
	}
	
}
