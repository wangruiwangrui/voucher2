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
import com.voucher.manage.daoModel.Assets.Hidden_Check;
import com.voucher.manage.daoModel.Assets.Hidden_Check_Date;
import com.voucher.manage.daoModel.Assets.Hidden_Neaten;
import com.voucher.manage.daoModel.Assets.Hidden_User;
import com.voucher.manage.daoModel.Assets.Patrol_Cycle;
import com.voucher.manage.daoModel.Assets.Position;
import com.voucher.manage.daoModel.TTT.ChartInfo;
import com.voucher.manage.daoModel.TTT.User_AccessTime;
import com.voucher.manage.daoModelJoin.RoomInfo_Position;
import com.voucher.manage.daoModelJoin.Assets.Hidden_Check_Join;
import com.voucher.manage.daoModelJoin.Assets.Position_Check_Join;
import com.voucher.manage.daoModelJoin.Assets.Position_Neaten_Join;
import com.voucher.manage.daoRowMapper.RowMappers;
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
		
		Position_Check_Join position_Hidden_Join=new Position_Check_Join();
		
		Position position=new Position();		
		position.setOffset(offset);
		position.setLimit(limit);
		position.setNotIn("id");
		
		Hidden_Check hidden=new Hidden_Check();
		hidden.setOffset(offset);
		hidden.setLimit(limit);
		hidden.setNotIn("id");
		
		String[] where = { "[Position].GUID !=", "''" };
		position.setWhere(where);
		hidden.setWhere(where);

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
					"[Hidden_Check].check_id,"+
					"[Hidden_Check].check_name,"+
					"[Hidden_Check].principal,"+
					"[Hidden_Check].check_circs,"+
					"[Hidden_Check].happen_time,"+
					"[Hidden_Check].principal,"+
					"[Hidden_Check].state,"+
					"[Hidden_Check].remark,"+
					"[Hidden_Check].update_time,"+
					"[Hidden_Check].date "+
					"FROM [Hidden_Check] left join  [Position]"+
					"on [Hidden_Check].GUID = [Position].GUID "+
					"WHERE [Position].lng is not null AND [Position].lat is not null "+ 
					"AND [Hidden_Check].exist=1"+
					"AND "+
					"[Hidden_Check].id not in( select top "+offset+" [Hidden_Check].id from [Hidden_Check] left join  [Position]"+
					"on [Hidden_Check].GUID = [Position].GUID "+
					"WHERE [Position].lng is not null AND [Position].lat is not null "+ 
					"ORDER BY   SQRT(("+lng+"-lng)*("+lng+"-lng)+("+lat+"-lat)*("+lat+"-lat))) ";
					
		String sql1="ORDER BY   "+
					"SQRT(("+lng+"-lng)*("+lng+"-lng)+("+lat+"-lat)*("+lat+"-lat))  ";
		
		String sql;
		
		if(search.equals("")){
			sql=sql0+sql1;
		}else{
			sql=sql0+"AND [Hidden_Check].check_circs like '%"+search+"%' "+sql1;
		}
		
		Position_Check_Join position_Hidden_Join=new Position_Check_Join();
		
		Position position=new Position();		
		
		Hidden_Check hidden=new Hidden_Check();
		
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
	public Map findHiddenAssetByDistance(int limit,int offset,Double lng, Double lat,String search) {
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
					"AND [RoomInfo].IsHidden > 0 "+
					"AND "+
					Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID not in( select top "+offset+" "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID from "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] left join  [Position]"+
					"on "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID = [Position].GUID "+
					"WHERE [Position].lng is not null AND [Position].lat is not null "+ 
					"AND ([RoomInfo].State = '已出租' or [RoomInfo].State = '不可出租' or [RoomInfo].State = '空置' ) "+
					"AND [RoomInfo].IsHidden > 0 ";
		
		
		String sql1="ORDER BY   "+
					"SQRT(("+lng+"-lng)*("+lng+"-lng)+("+lat+"-lat)*("+lat+"-lat))  ";
		
		String sql;
		
		String sql2="SELECT count(*) "+				   
				"FROM "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] left join  [Position]"+
				"on "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID = [Position].GUID "+
				"WHERE [Position].lng is not null AND [Position].lat is not null "+
				"AND ([RoomInfo].State = '已出租' or [RoomInfo].State = '不可出租' or [RoomInfo].State = '空置' ) "+
				"AND [RoomInfo].IsHidden > 0 ";
		
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
	public Map findAssetHiddenByDistance(int limit,int offset,Double lng, Double lat,String search) {
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
				    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].PropertyCardUnit,"+		
				    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].IsHidden "+
					"FROM "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] left join  [Position]"+
					"on "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID = [Position].GUID "+
					"WHERE [Position].lng is not null AND [Position].lat is not null "+
					"AND ([RoomInfo].State = '已出租' or [RoomInfo].State = '不可出租' or [RoomInfo].State = '空置' ) "+
					"AND IsHidden>0 "+
					"AND "+
					Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID not in( select top "+offset+" "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID from "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] left join  [Position]"+
					"on "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID = [Position].GUID "+
					"WHERE [Position].lng is not null AND [Position].lat is not null "+ 
					"AND ([RoomInfo].State = '已出租' or [RoomInfo].State = '不可出租' or [RoomInfo].State = '空置' ) "+
					"AND IsHidden>0 ";
		
		
		String sql1="ORDER BY   "+
					"SQRT(("+lng+"-lng)*("+lng+"-lng)+("+lat+"-lat)*("+lat+"-lat))  ";
		
		String sql;
		
		String sql2="SELECT count(*) "+				   
				"FROM "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] left join  [Position]"+
				"on "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID = [Position].GUID "+
				"WHERE [Position].lng is not null AND [Position].lat is not null "+
				"AND ([RoomInfo].State = '已出租' or [RoomInfo].State = '不可出租' or [RoomInfo].State = '空置' ) "+
				"AND IsHidden>0 ";
		
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
	public Map findAssetHiddenByDistanceNotFlow(int limit,int offset,Double lng, Double lat,String search) {
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
					"[Position].wgs84_lat,"+
					"[Position].wgs84_lng,"+
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
				    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].IsHidden "+
					"FROM "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] left join  [Position]"+
					"on "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID = [Position].GUID "+
					"WHERE [Position].lng is not null AND [Position].lat is not null "+
					"AND ([RoomInfo].State = '已出租' or [RoomInfo].State = '不可出租' or [RoomInfo].State = '空置' ) "+
					"AND IsHidden>0 "+
					" AND ("+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].neaten_flow is null  "+
					" OR "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].neaten_flow = 0 )"+
					"AND "+
					Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID not in( select top "+offset+" "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID from "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] left join  [Position]"+
					"on "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID = [Position].GUID "+
					"WHERE [Position].lng is not null AND [Position].lat is not null "+ 
					"AND ([RoomInfo].State = '已出租' or [RoomInfo].State = '不可出租' or [RoomInfo].State = '空置' ) "+
					"AND IsHidden>0 "+
					" AND ("+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].neaten_flow is null  "+
					" OR "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].neaten_flow = 0 )";
		
		
		String sql1="ORDER BY   "+
					"SQRT(("+lng+"-lng)*("+lng+"-lng)+("+lat+"-lat)*("+lat+"-lat))  ";
		
		String sql;
		
		String sql2="SELECT count(*) "+				   
				"FROM "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] left join  [Position]"+
				"on "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID = [Position].GUID "+
				"WHERE [Position].lng is not null AND [Position].lat is not null "+
				"AND ([RoomInfo].State = '已出租' or [RoomInfo].State = '不可出租' or [RoomInfo].State = '空置' ) "+
				"AND IsHidden>0 "+
				" AND ("+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].neaten_flow is null  "+
				" OR "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo].neaten_flow = 0 )";
		
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
		
		int cycle=1;
		
		if(type==1){
			checkName="hidden_check_date";
		}else{
			checkName="asset_check_date";
		}
		
		
		Patrol_Cycle patrol_Cycle=new Patrol_Cycle();
		
		patrol_Cycle.setLimit(1);
		patrol_Cycle.setOffset(0);
		patrol_Cycle.setNotIn("id");
		
		List list1=SelectExe.get(this.getJdbcTemplate(), patrol_Cycle);
		
		try{
			if(list1.size()>0){
				patrol_Cycle=(Patrol_Cycle) list1.get(0);
				if(type==1){
					cycle=patrol_Cycle.getHidden_cycle();
				}else{
					cycle=patrol_Cycle.getAsset_cycle();
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
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
			        		System.out.println("start====----"+start+"  r="+r+"  cycle="+cycle+"  i="+i);
			        		int year=cal.get(Calendar.YEAR);
			        		if(cycle==12)
			        			year=year-1;
			        		System.out.println("year======"+year);
			        		cal.set(year, i*cycle, cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
			        	}
			        }
			        
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
			        		System.out.println("start====----"+start+"  r="+r+"  cycle="+cycle+"  i="+i);
			        		int year=cal.get(Calendar.YEAR);
			        		if(cycle==12)
			        			year=year-1;
			        		System.out.println("year======"+year);
			        		cal.set(year, i*cycle, cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
			        	}
			        }
			        
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
					"[Hidden_Check].name,"+
					"[Hidden_Check].hidden_level,"+
					"[Hidden_Check].detail,"+
					"[Hidden_Check].progress,"+
					"[Hidden_Check].happen_time,"+
					"[Hidden_Check].principal,"+
					"[Hidden_Check].type,"+
					"[Hidden_Check].state,"+
					"[Hidden_Check].remark,"+
					"[Hidden_Check].update_time,"+
					"[Hidden_Check].date "+
					"FROM [Hidden_Check] left join  [Position]"+
					"on [Hidden_Check].GUID = [Position].GUID "+
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
			sql=sql0+"AND [Hidden_Check].check_circs like '%"+search+"%' "+sql1;
		}
		
		Position_Check_Join position_Hidden_Join=new Position_Check_Join();
		
		Position position=new Position();		
		
		Hidden_Check hidden=new Hidden_Check();
		
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
	public Map findAssetByHideen(Integer limit, Integer offset, String sort, String order, Map<String, String> search) {
		// TODO Auto-generated method stub
				
		RoomInfo roomInfo=new RoomInfo();
		
		roomInfo.setLimit(limit);
		roomInfo.setOffset(offset);
		roomInfo.setSort(sort);
		roomInfo.setOrder(order);
		roomInfo.setNotIn("[GUID]");

		Map map=new HashMap<String, Object>();
				

  String sql="SELECT top "+limit+
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
    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].UseYears, "+
    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].hidden_check_date, "+
    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].asset_check_date, "+
    Singleton.ROOMDATABASE+".[dbo].[RoomInfo].IsHidden "+
    "FROM "+
     Singleton.ROOMDATABASE+".[dbo].[RoomInfo] ";

  		StringBuilder whereCommand = new StringBuilder(); 
         		
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
    	                  " GUID not in("+
    	                  " select top "+offset+" GUID  FROM "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] where "+
    	                   whereCommand.substring(0,whereCommand.length()-7)+")";
    	}
    	
    	List list=this.getJdbcTemplate().query(sql,params.toArray(),new RowMappers(RoomInfo.class));
       
    	map.put("rows", list);
		MyTestUtil.print(list);
		
		String countSql="select count(*) FROM "+
			    Singleton.ROOMDATABASE+".[dbo].[RoomInfo]";
		
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
		
		Hidden_Check hidden=new Hidden_Check();
		
		hidden.setLimit(limit);
		hidden.setOffset(offset);
		hidden.setSort(sort);
		hidden.setOffset(offset);
		hidden.setNotIn("id");
			
		
		Map map=new HashMap<String, Object>();

  String sql="SELECT top "+limit+
    "[Hidden_Check].[GUID],"+
    "[Hidden_Check].[exist],"+
    "[Hidden_Check].[check_id],"+
    "[Hidden_Check].[check_name],"+
    "[Hidden_Check].[principal],"+
    "[Hidden_Check].[happen_time],"+
    "[Hidden_Check].[check_circs],"+
    "[Hidden_Check].[principal],"+
    "[Hidden_Check].[state],"+
    "[Hidden_Check].[remark],"+
    "[Hidden_Check].[update_time],"+
    "[Hidden_Check].[date],"+
    "[Hidden_Check].[campusAdmin],"+
    "[Hidden_Check].[terminal] "+
    "FROM "+
    "[Hidden_Check] "; 
  

  		StringBuilder whereCommand = new StringBuilder(); 
        		
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
    	                  " [Hidden_Check].GUID not in("+
    	                  " select top "+offset+" [Hidden_Check].GUID  FROM [Hidden_Check] where "+
    	                   whereCommand.substring(0,whereCommand.length()-7)+")";
    	}
    	
    	List list=this.getJdbcTemplate().query(sql,params.toArray(), new RowMappers(Hidden_Check.class));
       
    	map.put("rows", list);
		MyTestUtil.print(list);
		
		String countSql="select count(*) FROM "+
			    "[Hidden_Check] ";
		
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
	public Integer findNotHidden() {
		// TODO Auto-generated method stub
		Hidden_Check hidden=new Hidden_Check();
		
		String[] where={"[Hidden_Check].exist !="," 0 ","[Hidden_Check].check_name =","异常",
						"[Hidden_Check].state =", "未整改"};
		hidden.setWhere(where);
		
		int i=(int) SelectExe.getCount(this.getJdbcTemplate(), hidden).get("");
		
		return i;
	}

	@Override
	public Integer findInHidden() {
		// TODO Auto-generated method stub
		Hidden_Check hidden=new Hidden_Check();
		
		String[] where={"[Hidden_Check].exist !="," 0 ","[Hidden_Check].state =","整改中"};
		hidden.setWhere(where);
		
		int i=(int) SelectExe.getCount(this.getJdbcTemplate(), hidden).get("");
		
		return i;
	}

	@Override
	public Integer findAllAssets() {
		// TODO Auto-generated method stub
		RoomInfo roomInfo=new RoomInfo();
		
		String[] where={"[RoomInfo].IsHidden =","0"};
		
		roomInfo.setWhere(where);
		
		int count=(int) SelectExe.getCount(this.getJdbcTemplate(), roomInfo).get("");
		
		return count;
	}

	@Override
	public Integer findAllAssetsHidden() {
		// TODO Auto-generated method stub
		
		RoomInfo roomInfo=new RoomInfo();
		
		String[] where={"[RoomInfo].IsHidden>","0"};
		
		roomInfo.setWhere(where);
		
		int count=(int) SelectExe.getCount(this.getJdbcTemplate(), roomInfo).get("");
		
		return count;
	}
	
	
	@Override
	public Integer findSuccessHidden() {
		// TODO Auto-generated method stub
		Hidden_Check hidden=new Hidden_Check();
		
		String[] where={"[Hidden_Check].exist !="," 0 ","[Hidden_Check].state =","整改完成"};
		hidden.setWhere(where);
		
		int i=(int) SelectExe.getCount(this.getJdbcTemplate(), hidden).get("");
		
		return i;
	}
	
	@Override
	public String findLastHidden() {
		// TODO Auto-generated method stub
		
		Hidden_Check hidden=new Hidden_Check();
		
		String sql="SELECT MAX([date]) FROM [Hidden_Check] where [Hidden_Check].exist !=0";
		
		List list=this.getJdbcTemplate().query(sql,new hiddenRowMapper());
		
		hidden= (Hidden_Check) list.get(0);
		
		String s=String.valueOf(hidden.getHappen_time());
		
		return s;
	}

	class hiddenRowMapper implements RowMapper<Hidden_Check> {
        //rs涓鸿繑鍥炵粨鏋滈泦锛屼互姣忚涓哄崟浣嶅皝瑁呯潃
        public Hidden_Check mapRow(ResultSet rs, int rowNum) throws SQLException {    
            Hidden_Check hidden=new Hidden_Check();
        	Date date=rs.getDate("");
        	hidden.setHappen_time(date);
            return hidden;
        }
    }
	
	@Override
	public String findIgnoreHidden() {
		// TODO Auto-generated method stub
		Hidden_Check hidden=new Hidden_Check();
		
		String sql="SELECT MAX([happen_time]) FROM [Hidden_Check] where [Hidden_Check].exist !=0";
		
		List list=this.getJdbcTemplate().query(sql,new hiddenRowMapper());
		
		hidden=(Hidden_Check) list.get(0);
		
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
					" FROM [Hidden_Check] where date is not null "+
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
					"FROM [Hidden_Check] where date is not null "+ 
					"and convert(varchar(4),date,120)= "+year+" and check_name='异常'  "+ 
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
					"FROM "+Singleton.ROOMDATABASE+".[dbo].[RoomInfo] "+
					"where IsHidden>0 and InDate is not null "+
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
		RoomInfo roomInfo=new RoomInfo();
		
		String[] where={"[RoomInfo].GUID=",guid};
		
		roomInfo.setLimit(2);
		roomInfo.setOffset(0);
		roomInfo.setNotIn("GUID");
		roomInfo.setWhere(where);
		
		List list=SelectExe.get(this.getJdbcTemplate(), roomInfo);
		
		roomInfo=(RoomInfo) list.get(0);
		
		int count=roomInfo.getIsHidden();
		
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
	public List<User_AccessTime> selectUserAccessTime(String openId) {
		// TODO Auto-generated method stub
		User_AccessTime user_AccessTime=new User_AccessTime();
		user_AccessTime.setLimit(1);
		user_AccessTime.setOffset(0);
		user_AccessTime.setNotIn("open_id");
		
		String[] where={"open_id=",openId};
		
		user_AccessTime.setWhere(where);
		
		List<User_AccessTime> list=SelectExe.get(this.getJdbcTemplate(), user_AccessTime);
		
		return list;
	}
	
	@Override
	public Integer upUserAccessTime(User_AccessTime user_AccessTime) {
		// TODO Auto-generated method stub
		return UpdateExe.get(this.getJdbcTemplate(), user_AccessTime);
	}
	
	@Override
	public Integer insertUserAccessTime(User_AccessTime user_AccessTime) {
		// TODO Auto-generated method stub
		return InsertExe.get(this.getJdbcTemplate(), user_AccessTime);
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

	@Override
	public Map<String, Object> getAllChartInfo(Integer limit, Integer offset, String sort, String order, Map search) {
		// TODO Auto-generated method stub
		
		ChartInfo chartInfo=new ChartInfo();
		
		chartInfo.setLimit(limit);
		chartInfo.setOffset(offset);
		if(sort!=null&&!sort.equals("")){
			chartInfo.setSort(sort);
		}
		if(offset!=null&&!offset.equals("")){
			chartInfo.setOffset(offset);
		}
		
		chartInfo.setNotIn("GUID");
		
		if(!search.isEmpty()){
			String[] where=TransMapToString.get(search);
			chartInfo.setWhere(where);
		}
		
		Map<String, Object> map = new HashMap<>();

		List list = SelectExe.get(this.getJdbcTemplate(), chartInfo);
		map.put("rows", list);

		Map countMap = SelectExe.getCount(this.getJdbcTemplate(), chartInfo);

		map.put("total", countMap.get(""));
		
		return map;
	}

	@Override
	public int updatePatrolCycle(Patrol_Cycle patrol_Cycle) {
		// TODO Auto-generated method stub
		
		int count=(int) SelectExe.getCount(this.getJdbcTemplate(), patrol_Cycle).get("");
		
		int i;
		
		if(count>0){
			String[] where={"id=","1"};
			patrol_Cycle.setWhere(where);
			i=UpdateExe.get(this.getJdbcTemplate(), patrol_Cycle);
		}else{
			patrol_Cycle.setId(1);
			i=InsertExe.get(this.getJdbcTemplate(), patrol_Cycle);
		}
		
		return i;
	}

	@Override
	public Patrol_Cycle selectPatrolCycle() {
		// TODO Auto-generated method stub
		
		Patrol_Cycle patrol_Cycle=new Patrol_Cycle();
		
		patrol_Cycle.setLimit(1);
		patrol_Cycle.setOffset(0);
		patrol_Cycle.setNotIn("id");
		
		List list=SelectExe.get(this.getJdbcTemplate(), patrol_Cycle);
		
		try{
			if(list.size()>0)
				patrol_Cycle=(Patrol_Cycle) list.get(0);
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		return patrol_Cycle;
	}
	
}
