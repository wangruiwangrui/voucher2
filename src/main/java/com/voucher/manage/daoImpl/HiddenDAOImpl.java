package com.voucher.manage.daoImpl;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.voucher.manage.dao.HiddenDAO;
import com.voucher.manage.daoModel.RoomInfo;
import com.voucher.manage.daoModel.Assets.Hidden_Check;
import com.voucher.manage.daoModel.Assets.Hidden_Check_Date;
import com.voucher.manage.daoModel.Assets.Hidden_Check_Item;
import com.voucher.manage.daoModel.Assets.Hidden_Neaten;
import com.voucher.manage.daoModel.Assets.Hidden_Neaten_Date;
import com.voucher.manage.daoModel.Assets.Hidden_User;
import com.voucher.manage.daoModel.Assets.Neaten_Check;
import com.voucher.manage.daoModel.Assets.Position;
import com.voucher.manage.daoModel.Assets.RoomInfo_Hidden_Item;
import com.voucher.manage.daoModel.Assets.WeiXin_User;
import com.voucher.manage.daoModelJoin.Assets.Hidden_Check_Join;
import com.voucher.manage.daoModelJoin.Assets.Hidden_Neaten_Join;
import com.voucher.manage.daoModelJoin.Assets.Position_Check_Join;
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

public class HiddenDAOImpl extends JdbcDaoSupport implements HiddenDAO{

	@Override
	public Integer InsertIntoHiddenData(String GUID,String NAME,String TYPE,String uri) {
		// TODO Auto-generated method stub
		Hidden_Check_Date hidden_Data=new Hidden_Check_Date();
		Date date=new Date();
		
		hidden_Data.setCheck_id(GUID);
		hidden_Data.setNAME(NAME);
		hidden_Data.setTYPE(TYPE);
		hidden_Data.setURI(uri);
        hidden_Data.setDate(date);
        hidden_Data.setFileBelong("隐患图片");
        
        String[] where={"[GUID]=",GUID,
				"[FileBelong]=","隐患图片"};
		
        hidden_Data.setWhere(where);
        
        int count=(int) SelectExe.getCount(this.getJdbcTemplate(), hidden_Data).get("");
		
		hidden_Data.setFileIndex(count+1);
        
		return InsertExe.get(this.getJdbcTemplate(), hidden_Data);
	}
	
	
	@Override
	public Integer InsertIntoHiddenCheckData(String Check_id, String NAME, String TYPE, String uri) {
		// TODO Auto-generated method stub
		Hidden_Check_Date hidden_Check_Date=new Hidden_Check_Date();
		Date date=new Date();
		
		hidden_Check_Date.setCheck_id(Check_id);
		hidden_Check_Date.setNAME(NAME);
		hidden_Check_Date.setTYPE(TYPE);
		hidden_Check_Date.setURI(uri);
		hidden_Check_Date.setDate(date);
		
		hidden_Check_Date.setFileBelong("检查图片");
        
        String[] where={"[check_id]=",Check_id,
				"[FileBelong]=","检查图片"};
		
        hidden_Check_Date.setWhere(where);
        
        int count=(int) SelectExe.getCount(this.getJdbcTemplate(), hidden_Check_Date).get("");
		
		hidden_Check_Date.setFileIndex(count+1);
		
		return InsertExe.get(this.getJdbcTemplate(), hidden_Check_Date);
	}


	@Override
	public Integer InsertIntoHiddenNeatenData(String Neaten_id, String NAME, String TYPE, String uri) {
		// TODO Auto-generated method stub
		Hidden_Neaten_Date hidden_Neaten_Date=new Hidden_Neaten_Date();
		Date date=new Date();
		
		hidden_Neaten_Date.setNeaten_id(Neaten_id);
		hidden_Neaten_Date.setNAME(NAME);
		hidden_Neaten_Date.setTYPE(TYPE);
		hidden_Neaten_Date.setURI(uri);
		hidden_Neaten_Date.setDate(date);
		
		hidden_Neaten_Date.setFileBelong("整改图片");
        
        String[] where={"[neaten_id]=",Neaten_id,
				"[FileBelong]=","整改图片"};
		
        hidden_Neaten_Date.setWhere(where);
        
        int count=(int) SelectExe.getCount(this.getJdbcTemplate(), hidden_Neaten_Date).get("");
		
        hidden_Neaten_Date.setFileIndex(count+1);
		
		return InsertExe.get(this.getJdbcTemplate(), hidden_Neaten_Date);
	}
	
	@Override
	public Map<String, Object> selectAllHiddenDate(String GUID) {
		
		String pathRoot = System.getProperty("user.home");
		
		String filePath=pathRoot+Singleton.filePath;
		
		List<String> names=new ArrayList<String>();
		List<String> types=new ArrayList<String>();
		List<byte[]> fileBytes=new ArrayList<byte[]>();
		
		// TODO Auto-generated method stub
		Hidden_Check_Date hidden_Data=new Hidden_Check_Date();
		hidden_Data.setLimit(1000);
		hidden_Data.setOffset(0);
		hidden_Data.setNotIn("GUID");
		String[] where={"GUID =",GUID};
		
		hidden_Data.setWhere(where);
		
		List<Hidden_Check_Date> hidden_Datas=SelectExe.get(this.getJdbcTemplate(), hidden_Data);
		
		Iterator<Hidden_Check_Date> iterator=hidden_Datas.iterator();
		
		while(iterator.hasNext()){
			
			Hidden_Check_Date hidden_Data2=iterator.next();
			
			File file=new File(filePath+"\\"+hidden_Data2.getURI());
			
			byte[] fileByte=FileConvect.fileToByte(file);
			
			names.add(hidden_Data2.getNAME());
			types.add(hidden_Data2.getTYPE());
			fileBytes.add(fileByte);
			
		}
		
		Map<String, Object> map=new HashMap<>();
		
		map.put("names", names);
		map.put("types", types);
		map.put("fileBytes", fileBytes);
		
		return map;
	}
	
	
	@Override
	public Map<String, Object> selectAllHiddenCheckDate(String check_id) {
		// TODO Auto-generated method stub
        String pathRoot = System.getProperty("user.home");
		
		String filePath=pathRoot+Singleton.filePath;
		
		List<String> names=new ArrayList<String>();
		List<String> types=new ArrayList<String>();
		List<byte[]> fileBytes=new ArrayList<byte[]>();
		
		// TODO Auto-generated method stub
		Hidden_Check_Date hidden_Check_Date=new Hidden_Check_Date();
		hidden_Check_Date.setLimit(1000);
		hidden_Check_Date.setOffset(0);
		hidden_Check_Date.setNotIn("id");
		String[] where={"check_id =",check_id};
		
		hidden_Check_Date.setWhere(where);
		
		List<Hidden_Check_Date> hidden_Check_Dates=SelectExe.get(this.getJdbcTemplate(), hidden_Check_Date);
		
		Iterator<Hidden_Check_Date> iterator=hidden_Check_Dates.iterator();
		
		while(iterator.hasNext()){
			
			Hidden_Check_Date hidden_Check_Date2=iterator.next();
			
			File file=new File(filePath+"\\"+hidden_Check_Date2.getURI());
			
			byte[] fileByte=FileConvect.fileToByte(file);
			
			names.add(hidden_Check_Date2.getNAME());
			types.add(hidden_Check_Date2.getTYPE());
			fileBytes.add(fileByte);
			
		}
		
		Map<String, Object> map=new HashMap<>();
		
		map.put("names", names);
		map.put("types", types);
		map.put("fileBytes", fileBytes);
		
		return map;
	}


	@Override
	public Map<String, Object> selectAllHiddenNeatenDate(String neaten_id) {
		// TODO Auto-generated method stub
        String pathRoot = System.getProperty("user.home");
		
		String filePath=pathRoot+Singleton.filePath;
		
		List<String> names=new ArrayList<String>();
		List<String> types=new ArrayList<String>();
		List<byte[]> fileBytes=new ArrayList<byte[]>();
		
		// TODO Auto-generated method stub
		Hidden_Neaten_Date hidden_Neaten_Date=new Hidden_Neaten_Date();
		hidden_Neaten_Date.setLimit(1000);
		hidden_Neaten_Date.setOffset(0);
		hidden_Neaten_Date.setNotIn("id");
		String[] where={"neaten_id =",neaten_id};
		
		hidden_Neaten_Date.setWhere(where);
		
		List<Hidden_Neaten_Date> hidden_Neaten_Dates=SelectExe.get(this.getJdbcTemplate(), hidden_Neaten_Date);
		
		Iterator<Hidden_Neaten_Date> iterator=hidden_Neaten_Dates.iterator();
		
		while(iterator.hasNext()){
			
			Hidden_Neaten_Date hidden_Neaten_Date2=iterator.next();
			
			File file=new File(filePath+"\\"+hidden_Neaten_Date2.getURI());
			
			byte[] fileByte=FileConvect.fileToByte(file);
			
			names.add(hidden_Neaten_Date2.getNAME());
			types.add(hidden_Neaten_Date2.getTYPE());
			fileBytes.add(fileByte);
			
		}
		
		Map<String, Object> map=new HashMap<>();
		
		map.put("names", names);
		map.put("types", types);
		map.put("fileBytes", fileBytes);
		
		return map;
	}
	
	@Override
	public Map<String, Object> selectAllHidden(Integer limit, Integer offset, String sort, String order, Map<String, String> search) {
		// TODO Auto-generated method stub
        Hidden_Check_Date hidden=new Hidden_Check_Date();
		
		hidden.setLimit(limit);
		hidden.setOffset(offset);
		hidden.setSort(sort);
		hidden.setOrder(order);
		hidden.setNotIn("id");
		
		if(!search.isEmpty()){
			search.put("[Hidden_Check].exist =", "1");
		    String[] where=TransMapToString.get(search);
		    hidden.setWhere(where);
		}else{
			String[] where={"[Hidden_Check].exist =", "1"};
			hidden.setWhere(where);
		}
		
		Map map=new HashMap<String, Object>();
		
		List list=SelectExe.get(this.getJdbcTemplate(), hidden);
		MyTestUtil.print(list);
		map.put("rows", list);
		
		Map countMap=SelectExe.getCount(this.getJdbcTemplate(), hidden);
		
		map.put("total", countMap.get(""));
		
		return map;
	}


	@Override
	public Map<String, Object> selectHiddenUser(String campusAdmin) {
		// TODO Auto-generated method stub
		Hidden_User hidden_User=new Hidden_User();
		hidden_User.setOffset(0);
		hidden_User.setLimit(10);
		hidden_User.setNotIn("id");
		
		String[] where={"[Hidden_User].campusAdmin =",campusAdmin};
		hidden_User.setWhere(where);
		
		List list=SelectExe.get(this.getJdbcTemplate(), hidden_User);
		
		Map map=new HashMap<>();
		
		try{
		  Hidden_User hidden_User2=(Hidden_User) list.get(0);
		  map.put("state", 1);
		  map.put("row", hidden_User2);
		  return map;
		}catch (Exception e) {
			// TODO: handle exception
		  e.printStackTrace();	
		  map.put("state", 0);
		  return map;
		}
	}
	
	@Override
	public Map<String, Object> selectAllHiddenUser(Integer limit, Integer offset, String sort,
			String order,Map<String, String> search) {
		// TODO Auto-generated method stub
		Hidden_User hidden_User=new Hidden_User();
		hidden_User.setOffset(offset);
		hidden_User.setLimit(limit);
		hidden_User.setNotIn("id");
		
		try{
			if(!search.isEmpty()){
				search.put("[Hidden_User].purview !=", "0");
				String[] where=TransMapToString.get(search);
				hidden_User.setWhere(where);
			}else{
				String[] where={"[Hidden_User].purview !=", "0"};
				hidden_User.setWhere(where);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		Map<String, Object> map=new HashMap<>();
		
		List list=SelectExe.get(this.getJdbcTemplate(), hidden_User);
		
		MyTestUtil.print(list);
		
		map.put("rows", list);
		
		Map countMap=SelectExe.getCount(this.getJdbcTemplate(), hidden_User);
		
		map.put("total", countMap.get(""));
		
		return map;
		
	}

	
	@Override
	public Integer updateUserPassword(Hidden_User hidden_User,String OldPw) {
		// TODO Auto-generated method stub
		hidden_User.setNotIn("id");
		hidden_User.setLimit(10);
		hidden_User.setOffset(0);
		
		String[] where={"[Hidden_User].campusAdmin=",hidden_User.getCampusAdmin()};
		hidden_User.setWhere(where);
		List list=SelectExe.get(this.getJdbcTemplate(), hidden_User);
		
		try{
			Hidden_User hidden_User2=(Hidden_User) list.get(0);
			if(!OldPw.equals(hidden_User2.getPassword())){
				System.out.println("npw="+hidden_User.getPassword());
				System.out.println("opw="+hidden_User2.getPassword());
				return 3;
			}else{
				return UpdateExe.get(this.getJdbcTemplate(), hidden_User);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 2;
		}
	}
	
	@Override
	public Map<String, Object> selectAllHiddenCheck(Integer limit, Integer offset, String sort,
			String order,String address,Map<String, String> search) {
		// TODO Auto-generated method stub
		
		Map map=new HashMap<String, Object>();
		List<Hidden_Check_Join> list;
		Map countMap;
		
		Hidden_Check hidden_Check=new Hidden_Check();
		search.put("[Hidden_Check].exist =", "1");
		
		hidden_Check.setOffset(offset);
		hidden_Check.setLimit(limit);
		hidden_Check.setSort(sort);
		hidden_Check.setOrder(order);
		hidden_Check.setNotIn("id");
		
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
		/*
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

					search.put("[Hidden_Check].[GUID] = ", roomInfo3.getGUID());
				}
				
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return null;
			}
			
		}
		*/
		
		if(!search.isEmpty()){
		    String[] where=TransMapToString.get(search);
		    hidden_Check.setWhere(where);
		    roomInfo.setWhere(where);
		    position.setWhere(where);
		    weiXin_User.setWhere(where);
		}

		Object[] objects={hidden_Check,roomInfo,position,weiXin_User};
		
		String[] join={"guid","check_id","campusAdmin"};
		
		Hidden_Check_Join hidden_Check_Join=new Hidden_Check_Join();
		
		list=SelectJoinExe.get(this.getJdbcTemplate(), objects, hidden_Check_Join, join);
		countMap=SelectJoinExe.getCount(this.getJdbcTemplate(), objects, join);
		/*
		if(offset>0){
			
			Object[] objects={hidden_Check,weiXin_User};
			
			String[] join={"campusAdmin"};
		
			Hidden_Check_Join hidden_Check_Join=new Hidden_Check_Join();
		
			list=SelectJoinExe.get(this.getJdbcTemplate(), objects, hidden_Check_Join, join);
		
			Iterator<Hidden_Check_Join> iterator=list.iterator();
			
			int i=0;
			
			while (iterator.hasNext()) {
				
				Hidden_Check_Join hidden_Check_Join2=iterator.next();
				
				String guid=hidden_Check_Join2.getGUID();
				
				RoomInfo roomInfo4=new RoomInfo();
				
				String[] where4={Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID = ",guid};
				
				roomInfo4.setWhere(where4);
				roomInfo4.setLimit(1);
				roomInfo4.setOffset(0);			
				roomInfo4.setNotIn("GUID");
				
				System.out.println("guid="+guid);
				
				List<RoomInfo> list2=SelectExe.get(this.getJdbcTemplate(), roomInfo4);
				
				try{
					
					RoomInfo roomInfo5=list2.get(0);
								
					//MyTestUtil.print(roomInfo5);
					
					hidden_Check_Join2.setAddress(roomInfo5.getAddress());
					hidden_Check_Join2.setManageRegion(roomInfo5.getManageRegion());
					hidden_Check_Join2.setState(roomInfo5.getState());
					
					
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
				Position position2=new Position();
				
				String[] where5={"[Position].[check_id]=",hidden_Check_Join2.getCheck_id()};
				
				position2.setWhere(where5);
				position2.setLimit(2);
				position2.setOffset(0);
				position2.setNotIn("id");
				
				List<Position> list3=SelectExe.get(this.getJdbcTemplate(), position2);
				
				try{
					if(!list3.isEmpty()){
					
						Position position3=list3.get(0);
					
						hidden_Check_Join2.setLat(position3.getLat());
						hidden_Check_Join2.setLng(position3.getLng());
						hidden_Check_Join2.setCity(position3.getCity());
						hidden_Check_Join2.setDistrict(position3.getDistrict());
						hidden_Check_Join2.setStreet(position3.getStreet());
					
					}
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
				list.set(i, hidden_Check_Join2);
				
				i++;
			}
			
			countMap=SelectJoinExe.getCount(this.getJdbcTemplate(), objects, join);
			
		}else{

			
			Object[] objects={hidden_Check,position,weiXin_User};
			
			String[] join={"check_id","campusAdmin"};
		
			Hidden_Check_Join hidden_Check_Join=new Hidden_Check_Join();
		
			list=SelectJoinExe.get(this.getJdbcTemplate(), objects, hidden_Check_Join, join);
		
			Iterator<Hidden_Check_Join> iterator=list.iterator();
			
			int i=0;
			
			while (iterator.hasNext()) {
				
				Hidden_Check_Join hidden_Check_Join2=iterator.next();
				
				String guid=hidden_Check_Join2.getGUID();
				
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
					
					hidden_Check_Join2.setAddress(roomInfo5.getAddress());
					hidden_Check_Join2.setManageRegion(roomInfo5.getManageRegion());
					hidden_Check_Join2.setState(roomInfo5.getState());
					hidden_Check_Join2.setNum(roomInfo5.getNum());
					
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
							
				
				list.set(i, hidden_Check_Join2);
				
				i++;
			}
			
			countMap=SelectJoinExe.getCount(this.getJdbcTemplate(), objects, join);
		}
		*/
		
		
		map.put("rows", list);
		System.out.println("checkjoinlist=");
		//MyTestUtil.print(list);
		

		map.put("total", countMap.get(""));
		
		return map;
	}

	//安全巡查位置
	@Override
	public Map<String, Object> selectAllHiddenCheckPosition(Integer limit, Integer offset, String sort,
			String order,Map<String, String> search){
		Map map=new HashMap<String, Object>();
		List<Hidden_Check_Join> list;
		Map countMap;
		
		Hidden_Check hidden_Check=new Hidden_Check();
		search.put("[Hidden_Check].exist =", "1");
		
		hidden_Check.setOffset(offset);
		hidden_Check.setLimit(limit);
		hidden_Check.setSort(sort);
		hidden_Check.setOrder(order);
		hidden_Check.setNotIn("id");
		
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
		    hidden_Check.setWhere(where);
		    position.setWhere(where);
		    weiXin_User.setWhere(where);
		}
		
		Object[] objects={hidden_Check,position,weiXin_User};
		
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
	

	@Override
	public Integer insertHiddenCheck(Hidden_Check hidden_Check) {
		// TODO Auto-generated method stub
		return InsertExe.get(this.getJdbcTemplate(), hidden_Check);
	}


	@Override
	public Integer deleteHiddenCheck(Hidden_Check hidden_Check) {
		// TODO Auto-generated method stub
		Hidden_Check hidden_Check2=new Hidden_Check();
		hidden_Check2.setExist(0);
		hidden_Check2.setWhere(hidden_Check.getWhere());
		//return DeleteExe.get(this.getJdbcTemplate(), hidden_Check);
		
		int i=UpdateExe.get(this.getJdbcTemplate(), hidden_Check2);
		
		if(i==1){
			String[] where2={"check_id=",hidden_Check.getCheck_id()};
			Hidden_Check_Date hidden_Check_Date=new Hidden_Check_Date();
			hidden_Check_Date.setWhere(where2);
			DeleteExe.get(this.getJdbcTemplate(), hidden_Check_Date);
		}
		
		return i;
	}


	@Override
	public Map<String, Object> selectAllHiddenNeaten(Integer limit, Integer offset, String sort,
			String order,Map<String, String> search) {
		// TODO Auto-generated method stub
		Hidden_Neaten hidden_Neaten=new Hidden_Neaten();
		search.put("[Hidden_Neaten].exist =", "1");
		
		hidden_Neaten.setOffset(offset);
		hidden_Neaten.setLimit(limit);
		hidden_Neaten.setSort(sort);
		hidden_Neaten.setOrder(order);
		hidden_Neaten.setNotIn("id");
		
		
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
		    hidden_Neaten.setWhere(where);
		    position.setWhere(where);
		    weiXin_User.setWhere(where);
		}
		
		Map map=new HashMap<String, Object>();
		
		Object[] objects={hidden_Neaten,position,weiXin_User};
		
		Hidden_Neaten_Join hidden_Neaten_Join=new Hidden_Neaten_Join();
		
		String[] join={"neaten_id","campusAdmin"};
		
		List<Hidden_Neaten> list=SelectJoinExe.get(this.getJdbcTemplate(), objects, hidden_Neaten_Join, join);
		
		map.put("rows", list);
		
		Map countMap=SelectJoinExe.getCount(this.getJdbcTemplate(), objects, join);
			
		map.put("total", countMap.get(""));
			
		return map;
	}


	@Override
	public Integer insertHiddenNeaten(Hidden_Neaten hidden_Neaten) {
		// TODO Auto-generated method stub
		// 写入整改记录
		int i = InsertExe.get(this.getJdbcTemplate(), hidden_Neaten);

		if (i > 0) {
			Hidden_Check hidden_check = new Hidden_Check();

			hidden_check.setState(hidden_Neaten.getProgress());

			hidden_check.setUpdate_time(hidden_Neaten.getDate());

			String[] where = { "[Hidden_Check].GUID=", hidden_Neaten.getGUID(), "[Hidden_Check].state !=", "整改完成",
								"[Hidden_Check].check_name = ","异常"};

			hidden_check.setWhere(where);

			SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			
			String time=sdf.format(hidden_Neaten.getDate());
			
			String sql="UPDATE [Hidden_Check] SET update_time= '"+time+"' , "+
						"state= '"+hidden_Neaten.getProgress()+"' "+
						"WHERE [Hidden_Check].GUID = '"+hidden_Neaten.getGUID()+"' "+
						"AND [Hidden_Check].check_name = '异常' "+
						"AND ([Hidden_Check].state != '整改完成' "+
						"OR [Hidden_Check].state is null) ";
			
			// 更新安全巡查记录
			i = this.getJdbcTemplate().update(sql);

			if (i > 0) {
				RoomInfo_Hidden_Item roomInfo_Hidden_Item = new RoomInfo_Hidden_Item();

				String[] where2 = { "[RoomInfo_Hidden_Item].guid=", hidden_Neaten.getGUID() };

				roomInfo_Hidden_Item.setWhere(where2);

				if (i > 0) {
					
					hidden_check.setLimit(1000);
					hidden_check.setOffset(0);
					hidden_check.setNotIn("id");
					
					List<Hidden_Check> list=SelectExe.get(this.getJdbcTemplate(), hidden_check);
					
					Iterator<Hidden_Check> iterator=list.iterator();
					
					while (iterator.hasNext()) {
						
						Hidden_Check hidden_Check2=iterator.next();
						
						Neaten_Check neaten_Check=new Neaten_Check();
						
						neaten_Check.setGuid(hidden_Neaten.getGUID());
						neaten_Check.setNeaten_id(hidden_Neaten.getNeaten_id());
						neaten_Check.setCheck_id(hidden_Check2.getCheck_id());
						neaten_Check.setPrincipal(hidden_Neaten.getPrincipal());
						neaten_Check.setProgress(hidden_Neaten.getProgress());
						neaten_Check.setUserName(hidden_Neaten.getUserName());
						neaten_Check.setCampusAdmin(hidden_Neaten.getCampusAdmin());
						neaten_Check.setDate(hidden_Neaten.getDate());
						
						i=InsertExe.get(this.getJdbcTemplate(), neaten_Check);
						
						if(i<1){
							TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						}
					}
					
					if (i > 0) {

						if (hidden_Neaten.getProgress().equals("整改完成")) {

							RoomInfo roomInfo = new RoomInfo();

							roomInfo.setIsHidden(0);

							String[] where3 = {"[RoomInfo].guid=", hidden_Neaten.getGUID() };

							roomInfo.setWhere(where3);

							// 更新资产隐患字段
							i = UpdateExe.get(this.getJdbcTemplate(), roomInfo);

							if (i > 0) {

								// 删除资产对应隐患表记录
								DeleteExe.get(this.getJdbcTemplate(), roomInfo_Hidden_Item);

							} else {
								TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
							}
						}

					} else {
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					}

				} else {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				}
			}else{
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
		} else {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return i;
	}


	@Override
	public Integer deleteHiddenNeaten(Hidden_Neaten hidden_Neaten) {
		// TODO Auto-generated method stub
		Hidden_Neaten hidden_Neaten2=new Hidden_Neaten();
		hidden_Neaten2.setExist(0);
		hidden_Neaten2.setWhere(hidden_Neaten.getWhere());
		//return DeleteExe.get(this.getJdbcTemplate(), hidden_Neaten);
		
		int i=UpdateExe.get(this.getJdbcTemplate(), hidden_Neaten2);
		
		if(i==1){
			String[] where2={"[Hidden_Neaten_Date].neaten_id=",hidden_Neaten.getNeaten_id()};
			Hidden_Neaten_Date hidden_Neaten_Date=new Hidden_Neaten_Date();
			hidden_Neaten_Date.setWhere(where2);
			DeleteExe.get(this.getJdbcTemplate(), hidden_Neaten_Date);		
		}
		
		return i;
	}


	@Override
	public Integer updateHiddenCheck(Hidden_Check hidden_Check) {
		// TODO Auto-generated method stub
		return UpdateExe.get(this.getJdbcTemplate(), hidden_Check);
	}


	@Override
	public Integer updateHiddenNeaten(Hidden_Neaten hidden_Neaten) {
		// TODO Auto-generated method stub
		return UpdateExe.get(this.getJdbcTemplate(), hidden_Neaten);
	}


	@Override
	public List<Hidden_Check_Join> selectHiddenOfMap(Map<String, String> search) {
		// TODO Auto-generated method stub
		 Hidden_Check hidden=new Hidden_Check();
		 search.put("[Hidden_Check].exist =", "1");
		 
		hidden.setLimit(10);
		hidden.setOffset(0);
		hidden.setSort(null);
		hidden.setOrder(null);
		hidden.setNotIn("check_id");

		Position position = new Position();
		position.setLimit(10);
		position.setOffset(0);
		position.setSort(null);
		position.setOrder(null);
		position.setNotIn("check_id");

		WeiXin_User weiXin_User = new WeiXin_User();
		weiXin_User.setLimit(10);
		weiXin_User.setOffset(0);
		weiXin_User.setSort(null);
		weiXin_User.setOrder(null);
		weiXin_User.setNotIn("check_id");
		
		 search.put("[Hidden_Check].check_name =", "异常");
		 search.put("[Hidden_Check].state !=", "整改完成");
		 String[] where=TransMapToString.get(search);
		 hidden.setWhere(where);
		 position.setWhere(where);
		 weiXin_User.setWhere(where);

			 
		Map map=new HashMap<String, Object>();
				
		Object[] objects={hidden,position,weiXin_User};
				
		String[] join={"check_id","campusAdmin"};
			
		Hidden_Check_Join hidden_Join=new Hidden_Check_Join();
		
		List hidden_joins=SelectJoinExe.get(this.getJdbcTemplate(), objects, hidden_Join, join);		
			 
		return hidden_joins;
	}


	@Override
	public Integer getAllAssetByHidden_GUID(String guid) {
		// TODO Auto-generated method stub
		
		RoomInfo roomInfo=new RoomInfo();
		
		String[] where={"[RoomInfo].IsHidden>","0"};

		roomInfo.setWhere(where);
		
		int count=(int) SelectExe.getCount(this.getJdbcTemplate(), roomInfo).get("");

		return count;
	}


	@Override
	public Integer insertIntoHidden_Check_Item(Hidden_Check_Item hidden_Check_Item) {
		// TODO Auto-generated method stub
		return InsertExe.get(this.getJdbcTemplate(), hidden_Check_Item);
	}


	@Override
	public Hidden_Check_Item selectHidden_Check_ItemById(Hidden_Check_Item hidden_Check_Item) {
		// TODO Auto-generated method stub
		Hidden_Check_Item hidden_Check_Item2=new Hidden_Check_Item();
		try{
			hidden_Check_Item2=(Hidden_Check_Item) SelectExe.get(this.getJdbcTemplate(), hidden_Check_Item).get(0);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return hidden_Check_Item2;
	}


	@Override
	public Map selectAllHidden_Point(Integer limit, Integer offset, String sort, String order,
			Map<String, String> search) {
		// TODO Auto-generated method stub
		Hidden_Check hidden=new Hidden_Check();
		hidden.setLimit(limit);
		hidden.setOffset(offset);
		hidden.setSort(sort);
		hidden.setOrder(order);
		hidden.setNotIn("check_id");
		
		Position position=new Position();
		position.setLimit(limit);
		position.setOffset(offset);
		position.setSort(sort);
		position.setOrder(order);
		position.setNotIn("check_id");
		
		if(search!=null&&!search.isEmpty()){
			search.put("check_name=", "异常");
		    String[] where=TransMapToString.get(search);
		    hidden.setWhere(where);
		    position.setWhere(where);
		}else{
			String[] where={"check_name=", "异常"};
			hidden.setWhere(where);
		    position.setWhere(where);
		}
		
		Position_Check_Join position_Hidden_Join=new Position_Check_Join();
		
		Object[] objects={hidden,position};
		
		String[] join={"check_id"};
		
		List list=SelectJoinExe.get(this.getJdbcTemplate(), objects, position_Hidden_Join, join);
		
		int total=(int) SelectJoinExe.getCount(this.getJdbcTemplate(), objects, join).get("");
		
		Map map=new HashMap<>();
		
		map.put("row", list);
		
		map.put("total", total);
		
		return map;
	}


	@Override
	public Integer updateRoomInfo_Hidden_Item(RoomInfo_Hidden_Item roomInfo_Hidden_Item) {
		// TODO Auto-generated method stub
		String guid=roomInfo_Hidden_Item.getGuid();
		
		String[] where={"[RoomInfo_Hidden_Item].guid=",guid};
		
		roomInfo_Hidden_Item.setWhere(where);
		
		int i;
		
		int count=(int) SelectExe.getCount(this.getJdbcTemplate(), roomInfo_Hidden_Item).get("");
		
		if(count>0){
			i=UpdateExe.get(this.getJdbcTemplate(), roomInfo_Hidden_Item);
		}else{
			i=InsertExe.get(this.getJdbcTemplate(), roomInfo_Hidden_Item);
		}
		
		return i;
	}


	@Override
	public List<RoomInfo_Hidden_Item> selectRoomInfo_Hidden_Item(Integer limit, Integer offset, String sort,
			String order, Map<String, String> search) {
		// TODO Auto-generated method stub
		RoomInfo_Hidden_Item roomInfo_Hidden_Item=new RoomInfo_Hidden_Item();
		roomInfo_Hidden_Item.setLimit(limit);
		roomInfo_Hidden_Item.setOffset(offset);
		roomInfo_Hidden_Item.setNotIn("guid");
		
		if(!search.isEmpty()){
		    String[] where=TransMapToString.get(search);
		    roomInfo_Hidden_Item.setWhere(where);
		}
		
		return SelectExe.get(this.getJdbcTemplate(), roomInfo_Hidden_Item);
	}


	@Override
	public Integer insertIntoNeaten_Check(Neaten_Check neaten_Check) {
		// TODO Auto-generated method stub
		return InsertExe.get(this.getJdbcTemplate(), neaten_Check);
	}



}
