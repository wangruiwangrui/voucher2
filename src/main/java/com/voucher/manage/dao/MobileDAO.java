package com.voucher.manage.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.voucher.manage.daoModel.RoomInfo;
import com.voucher.manage.daoModel.Assets.WeiXin_User;
import com.voucher.manage.daoModelJoin.Assets.Hidden_Check_Join;
import com.voucher.manage.daoModelJoin.Assets.Hidden_Join;
import com.voucher.manage.daoModelJoin.Assets.Hidden_Neaten_Join;

public interface MobileDAO {
	
	public Map<String, Object> hiddenImageQuery(HttpServletRequest request,List guidLits);
	
	public Map<String, Object> positionHiddenImageQuery(HttpServletRequest request,List guidLits);
	
	public List allHiddenImageByGUID(HttpServletRequest request,Hidden_Join hidden_Join);

	public Map<String, Object> checkImageQuery(HttpServletRequest request,List guidLits);
	
	public Map<String, Object> assetCheckImageQuery(HttpServletRequest request,List guidLits);
	
	public List allCheckImageByGUID(HttpServletRequest request,Hidden_Check_Join hidden_Check_Join);
	
	public List allAssetCheckImageByGUID(HttpServletRequest request,Hidden_Check_Join hidden_Check_Join);
	
	public Map<String, Object> neatenImageQuery(HttpServletRequest request,List guidLits);
	
	public List allNeatenImageByGUID(HttpServletRequest request,Hidden_Neaten_Join hidden_Neaten_Join);
	
	public Map<String, Object> roomInfoImageQuery(HttpServletRequest request,List guidLits);
	
	public Map<String, Object> roomInfo_PositionImageQuery(HttpServletRequest request,List guidLits);
		
	public List allRoomInfoImageByGUID(HttpServletRequest request, RoomInfo roomInfo);
	
	public Integer insertWeiXinUser(WeiXin_User weiXin_User);
	
	public Integer deleteWeiXinUser(WeiXin_User weiXin_User);
	
	public Integer updateWeiXinUser(WeiXin_User weiXin_User);
	
	public Integer selectCountWeiXinUser(WeiXin_User weiXin_User);
}
