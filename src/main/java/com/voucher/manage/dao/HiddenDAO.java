package com.voucher.manage.dao;

import java.util.List;
import java.util.Map;

import com.voucher.manage.daoModel.Assets.Hidden_Check;
import com.voucher.manage.daoModel.Assets.Hidden_Check_Item;
import com.voucher.manage.daoModel.Assets.Hidden_Neaten;
import com.voucher.manage.daoModel.Assets.Hidden_User;
import com.voucher.manage.daoModel.Assets.Neaten_Check;
import com.voucher.manage.daoModel.Assets.RoomInfo_Hidden_Item;
import com.voucher.manage.daoModelJoin.Assets.Hidden_Check_Join;

public interface HiddenDAO {

	public Integer InsertIntoHiddenData(String GUID,String NAME,String TYPE,String uri);
	
	public Integer InsertIntoHiddenCheckData(String Check_id,String NAME,String TYPE,String uri);
	
	public Integer InsertIntoHiddenNeatenData(String Neaten_id,String NAME,String TYPE,String uri);
	
	public Map<String,Object> selectAllHiddenDate(String GUID);
	
	public Map<String,Object> selectAllHiddenCheckDate(String check_id);
	
	public Map<String,Object> selectAllHiddenNeatenDate(String neaten_id);
	
	public Map<String, Object> selectAllHidden(Integer limit, Integer offset, String sort,
			String order,Map<String, String> search);

	public Map<String , Object> selectHiddenUser(String campusAdmin);
	
	public Map<String, Object> selectAllHiddenUser(Integer limit, Integer offset, String sort,
			String order,Map<String, String> search);
	
	public Integer updateUserPassword(Hidden_User hidden_User,String OldPw);
	
	public Map<String, Object> selectAllHiddenCheck(Integer limit, Integer offset, String sort,
			String order,String address,Map<String, String> search);
	
	public Map selectNotHiddenCheckAssetByDate(int limit, int offset, 
			String search, String startTime,String endTime);

	public Map<String, Object> selectAllHiddenCheckPosition(Integer limit, Integer offset, String sort,
			String order,Map<String, String> search);
	
	public Integer insertHiddenCheck(Hidden_Check hidden_Check);
	
	public Integer updateHiddenCheck(Hidden_Check hidden_Check);
	
	public Integer deleteHiddenCheck(Hidden_Check hidden_Check);
	
	public Map<String, Object> selectAllHiddenNeaten(Integer limit, Integer offset, String sort,
			String order,Map<String, String> search);
	
	public Integer insertHiddenNeaten(Hidden_Neaten hidden_Neaten,RoomInfo_Hidden_Item roomInfo_Hidden_Item,
			Hidden_Check_Item hidden_Check_Item,Integer item);
	
	public Integer updateHiddenNeaten(Hidden_Neaten hidden_Neaten);
	
	public Integer deleteHiddenNeaten(Hidden_Neaten hidden_Neaten);
	
	public List<Hidden_Check_Join> selectHiddenOfMap(Map<String, String> search);
	
	public Integer getAllAssetByHidden_GUID(String guid);
	
	public Integer insertIntoHidden_Check_Item(Hidden_Check_Item hidden_Check_Item);

	public Hidden_Check_Item selectHidden_Check_ItemById(Hidden_Check_Item hidden_Check_Item);
	
	public Map selectAllHidden_Point(Integer limit, Integer offset, String sort,
			String order,Map<String, String> search);
	
	public List<RoomInfo_Hidden_Item> selectRoomInfo_Hidden_Item(Integer limit, Integer offset, String sort,
			String order,Map<String, String> search);
	
	public Integer updateRoomInfo_Hidden_Item(RoomInfo_Hidden_Item roomInfo_Hidden_Item);
	
	public Integer insertIntoNeaten_Check(Neaten_Check neaten_Check);
}
