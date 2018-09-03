package com.rmi.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.voucher.manage.dao.AssetsDAO;
import com.voucher.manage.dao.HiddenDAO;
import com.voucher.manage.dao.MobileDAO;
import com.voucher.manage.dao.RoomInfoDao;
import com.voucher.manage.daoModel.RoomInfo;
import com.voucher.manage.daoModel.Assets.Hidden;
import com.voucher.manage.daoModel.Assets.Hidden_Assets;
import com.voucher.manage.daoModel.Assets.Hidden_Check;
import com.voucher.manage.daoModel.Assets.Hidden_Level;
import com.voucher.manage.daoModel.Assets.Hidden_Neaten;
import com.voucher.manage.daoModel.Assets.Hidden_Type;
import com.voucher.manage.daoModel.Assets.Hidden_User;
import com.voucher.manage.daoModel.Assets.Position;
import com.voucher.manage.daoModel.Assets.WeiXin_User;
import com.voucher.manage.daoModelJoin.Assets.Hidden_Data_Join;
import com.voucher.manage.daoModelJoin.Assets.Hidden_Join;
import com.voucher.manage.file.DocFileFactory;
import com.voucher.manage.file.ImageFileFactory;
import com.voucher.manage.file.PdfFileFactory;
import com.voucher.manage.file.XlsFileFactory;
import com.voucher.manage.model.Users;
import com.voucher.manage.service.UserService;
import com.voucher.manage.tools.MyTestUtil;
import com.voucher.sqlserver.context.Connect;

public class AssetsImpl implements Assets{
	
//	ApplicationContext applicationContext=new ClassPathXmlApplicationContext("spring-sqlservers.xml");
	
	/*
	 * 连接池
	 */
	ApplicationContext applicationContext=new Connect().get();
	
	HiddenDAO hiddenDAO=(HiddenDAO) applicationContext.getBean("hiddenDao");
	
	AssetsDAO assetsDAO=(AssetsDAO) applicationContext.getBean("assetsdao");
	
	MobileDAO mobileDao=(MobileDAO) applicationContext.getBean("mobileDao");
	
	RoomInfoDao roomInfoDao=(RoomInfoDao) applicationContext.getBean("roomInfodao");
	
	private UserService userService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Override
	public Map<String, Object> getRoomInfo(Integer limit,Integer offset,String sort,String order,
			Map search) {
		Map<String, Object> map = new HashMap<String, Object>();

		
	
		/*
		if(search!=null&&!search.trim().equals("")){
			search="%"+search+"%";
		}		
		*/


		RoomInfoDao roomInfoDao=(RoomInfoDao) applicationContext.getBean("roomInfodao");
		
		List<RoomInfo> roomInfos=roomInfoDao.findAllRoomInfo(limit,offset,sort,
				order,search);
		
		map.put("rows", roomInfos);
		
		Integer total=roomInfoDao.getRoomInfoCount(search);
		
        map.put("total", total);
		
		return map;
	}


	@Override
	public Integer uploadImageFile(Object object,String GUID,List<String> names, List<byte[]> files) {
	     return new ImageFileFactory().upload(object,GUID, names, files);
    }

	@Override
	public Integer uploadPdfFile(Object object,String GUID,List<String> names, List<byte[]> files) {
		// TODO Auto-generated method stub
		return new PdfFileFactory().upload(object,GUID, names, files);
	}

	@Override
	public Integer uploadDocFile(Object object,String GUID,List<String> names, List<byte[]> files) {
		// TODO Auto-generated method stub
		return new DocFileFactory().upload(object,GUID, names, files);
	}

	@Override
	public Integer uploadXlsFile(Object object,String GUID,List<String> names, List<byte[]> files) {
		// TODO Auto-generated method stub
		return new XlsFileFactory().upload(object,GUID, names, files);
	}

	@Override
	public Map<String, Object> selectAllHidden(Integer limit, Integer offset, String sort, String order, Map search) {
		// TODO Auto-generated method stub
		
		HiddenDAO hiddenDAO=(HiddenDAO) applicationContext.getBean("hiddenDao");
		
		Map map=hiddenDAO.selectAllHidden(limit, offset, sort, order, search);
		
		return map;
	}

	@Override
	public Integer insertIntoHidden(Hidden hidden) {
		// TODO Auto-generated method stub		
		return hiddenDAO.insertIntoHidden(hidden);
	}

	@Override
	public Integer updateHidden(Hidden hidden) {
		// TODO Auto-generated method stub
		
		return hiddenDAO.updateHidden(hidden);
	}

	@Override
	public Integer deleteHidden(Hidden hidden) {
		// TODO Auto-generated method stub
		Hidden_Assets hidden_Assets=new Hidden_Assets();
		String[] where0={"[Hidden_Assets].hidden_GUID =",hidden.getGUID()};
		hidden_Assets.setWhere(where0);
		assetsDAO.deleteHidden_Assets(hidden_Assets);
		
		Hidden_Check hidden_Check=new Hidden_Check();
		String[] where1={"[Hidden_Check].GUID =",hidden.getGUID()};
		hidden_Check.setWhere(where1);
		hiddenDAO.deleteHiddenCheck(hidden_Check);
		
		Hidden_Neaten hidden_Neaten=new Hidden_Neaten();
		String[] where2={"[Hidden_Neaten].GUID =",hidden.getGUID()};
		hidden_Neaten.setWhere(where2);
		hiddenDAO.deleteHiddenNeaten(hidden_Neaten);
		
		System.out.println("hidden guid="+hidden.getGUID());
		
		Position position=new Position();
		String[] where3={"[Position].GUID =",hidden.getGUID()};
		position.setWhere(where3);
		assetsDAO.deletePosition(position);
		
		return hiddenDAO.deleteHidden(hidden);
	}

	@Override
	public Integer updateRoomInfo(RoomInfo roomInfo) {
		// TODO Auto-generated method stub
		RoomInfoDao roomInfoDao=(RoomInfoDao) applicationContext.getBean("roomInfodao");
		return roomInfoDao.updateRoomInfo(roomInfo);
	}

	@Override
	public Integer deleteRoomInfo(RoomInfo roomInfo) {
		// TODO Auto-generated method stub
		RoomInfoDao roomInfoDao=(RoomInfoDao) applicationContext.getBean("roomInfodao");
		return roomInfoDao.deleteRoomInfo(roomInfo);
	}

	@Override
	public Map<String, Object> findAllRoomInfo_Position(Integer limit, Integer offset, String sort, String order,
			Map search) {
		// TODO Auto-generated method stub
		RoomInfoDao roomInfoDao=(RoomInfoDao) applicationContext.getBean("roomInfodao");
		
		String term="AND";
		
		Map map=assetsDAO.findAllRoomInfo_Position(limit, offset, sort, order,term , search);
		
		return map;
	}

	@Override
	public Integer updatePosition(Position position) {
		// TODO Auto-generated method stub
		AssetsDAO assetsDAO=(AssetsDAO) applicationContext.getBean("assetsdao");
		
		return assetsDAO.updatePosition(position);
	}


	@Override
	public List<Hidden_Level> setctAllHiddenLevel() {		
		// TODO Auto-generated method stub		
		return hiddenDAO.setctAllHiddenLevel();
	}


	@Override
	public Integer insertHiddenLevel(Hidden_Level hidden_level) {
		// TODO Auto-generated method stub
		
		return hiddenDAO.insertHiddenLevel(hidden_level);
	}


	@Override
	public Integer deleteHiddenLevel(Hidden_Level hidden_level) {
		// TODO Auto-generated method stub
		
		return hiddenDAO.deleteHiddenLevel(hidden_level);
	}


	@Override
	public Map<String, Object> selectAllHidden_Jion(Integer limit, Integer offset, String sort, String order,
			Map<String, String> search) {
		// TODO Auto-generated method stub
		
		return hiddenDAO.selectAllHidden_Jion(limit, offset, sort, order, search);
	}


	@Override
	public Map<String, Object> selectAllHiddenDate(String GUID) {
		// TODO Auto-generated method stub
		
		return hiddenDAO.selectAllHiddenDate(GUID);
	}


	@Override
	public Map<String, Object> selectAllHiddenCheckDate(String check_id) {
		// TODO Auto-generated method stub
		return hiddenDAO.selectAllHiddenCheckDate(check_id);
	}


	@Override
	public Map<String, Object> selectAllHiddenNeatenDate(String neaten_id) {
		// TODO Auto-generated method stub
		return hiddenDAO.selectAllHiddenNeatenDate(neaten_id);
	}
	
	@Override
	public List<Hidden_Type> selectAllHiddenType() {
		// TODO Auto-generated method stub
		
		return hiddenDAO.selectAllHiddenType();
	}


	@Override
	public Integer insertHiddenType(Hidden_Type hidden_Type) {
		// TODO Auto-generated method stub
		
		return hiddenDAO.insertHiddenType(hidden_Type);
	}


	@Override
	public Integer deleteHiddenType(Hidden_Type hidden_Type) {
		// TODO Auto-generated method stub
		return hiddenDAO.deleteHiddenType(hidden_Type);
	}


	@Override
	public Map<String, Object> selectAllHiddenUser(Integer limit, Integer offset, String sort,
			String order,Map<String, String> search){
		 return hiddenDAO.selectAllHiddenUser(limit, offset, sort, order, search);
	}


	@Override
	public Integer insertHiddenUser(Hidden_User hidden_User) {
		// TODO Auto-generated method stub
		return hiddenDAO.insertHiddenUser(hidden_User);
	}


	@Override
	public Integer deleteHiddenUser(Hidden_User hidden_User) {
		// TODO Auto-generated method stub
		return hiddenDAO.deleteHiddenUser(hidden_User);
	}
	
	
	@Override
	public Integer insertWeiXinUser(WeiXin_User weiXin_User) {
		// TODO Auto-generated method stub
		return mobileDao.insertWeiXinUser(weiXin_User);
	}

	@Override
	public Integer deleteWeiXinUser(WeiXin_User weiXin_User) {
		// TODO Auto-generated method stub
		return mobileDao.deleteWeiXinUser(weiXin_User);
	}

	@Override
	public Integer updateWeiXinUser(WeiXin_User weiXin_User) {
		// TODO Auto-generated method stub
		return mobileDao.updateWeiXinUser(weiXin_User);
	}


	@Override
	public Map<String, Object> selectAllHiddenCheck(Integer limit, Integer offset, String sort, String order,
			String address,Map<String, String> search) {
		// TODO Auto-generated method stub
		return hiddenDAO.selectAllHiddenCheck(limit, offset, sort, order,address,search);
	}

	@Override
	public Map<String, Object> selectAllHiddenCheckPosition(Integer limit, Integer offset, String sort, String order,
			Map<String, String> search) {
		// TODO Auto-generated method stub
		return hiddenDAO.selectAllHiddenCheckPosition(limit, offset, sort, order, search);
	}


	@Override
	public Integer insertHiddenCheck(Hidden_Check hidden_Check) {
		// TODO Auto-generated method stub
		return hiddenDAO.insertHiddenCheck(hidden_Check);
	}


	@Override
	public Integer deleteHiddenCheck(Hidden_Check hidden_Check) {
		// TODO Auto-generated method stub
		return hiddenDAO.deleteHiddenCheck(hidden_Check);
	}


	@Override
	public Map<String, Object> selectAllHiddenNeaten(Integer limit, Integer offset, String sort, String order,
			Map<String, String> search) {
		// TODO Auto-generated method stub
		return hiddenDAO.selectAllHiddenNeaten(limit, offset, sort, order, search);
	}


	@Override
	public Integer insertHiddenNeaten(Hidden_Neaten hidden_Neaten) {
		// TODO Auto-generated method stub
		return hiddenDAO.insertHiddenNeaten(hidden_Neaten);
	}


	@Override
	public Integer deleteHiddenNeaten(Hidden_Neaten hidden_Neaten) {
		// TODO Auto-generated method stub
		return hiddenDAO.deleteHiddenNeaten(hidden_Neaten);
	}


	@Override
	public Integer updateHiddenCheck(Hidden_Check hidden_Check) {
		// TODO Auto-generated method stub
		return hiddenDAO.updateHiddenCheck(hidden_Check);
	}


	@Override
	public Integer updateHiddenNeaten(Hidden_Neaten hidden_Neaten) {
		// TODO Auto-generated method stub
		return hiddenDAO.updateHiddenNeaten(hidden_Neaten);
	}


	@Override
	public List<Hidden_Join> selectHiddenOfMap(Map<String, String> search) {
		// TODO Auto-generated method stub
		return hiddenDAO.selectHiddenOfMap(search);
	}


	@Override
	public Integer insertIntoHidden_Assets(Hidden_Assets hidden_Assets) {
		// TODO Auto-generated method stub
		return  assetsDAO.insertIntoHidden_Assets(hidden_Assets);
	}


	@Override
	public Map findAssetByHideen(Integer limit, Integer offset, String sort, String order, Map<String, String> search) {
		// TODO Auto-generated method stub
		return assetsDAO.findAssetByHideen(limit, offset, sort, order, search);
	}


	@Override
	public Integer deleteHidden_Assets(Hidden_Assets hidden_Assets) {
		// TODO Auto-generated method stub
		return assetsDAO.deleteHidden_Assets(hidden_Assets);
	}


	@Override
	public Integer findNotHidden() {
		// TODO Auto-generated method stub
		return assetsDAO.findNotHidden();
	}


	@Override
	public Integer findInHidden() {
		// TODO Auto-generated method stub
		return assetsDAO.findInHidden();
	}


	@Override
	public Integer findSuccessHidden() {
		// TODO Auto-generated method stub
		return assetsDAO.findSuccessHidden();
	}
	
	@Override
	public Integer findAllAssets() {
		// TODO Auto-generated method stub
		return assetsDAO.findAllAssets();
	}

	@Override
	public Integer findAllAssetsHidden() {
		// TODO Auto-generated method stub
		return assetsDAO.findAllAssetsHidden();
	}
	
	@Override
	public String findLastHidden() {
		// TODO Auto-generated method stub
		return assetsDAO.findLastHidden();
	}


	@Override
	public String findIgnoreHidden() {
		// TODO Auto-generated method stub
		return assetsDAO.findIgnoreHidden();
	}


	@Override
	public Map<String, Object> hiddenQuery(Integer hiddenLevel) {
		// TODO Auto-generated method stub
		return assetsDAO.hiddenQuery(hiddenLevel);
	}


	@Override
	public Integer updateHiddenLevel(Hidden_Level hidden_Level) {
		// TODO Auto-generated method stub
		return hiddenDAO.updateHiddenLevel(hidden_Level);
	}


	@Override
	public Integer updateHiddenType(Hidden_Type hidden_Type) {
		// TODO Auto-generated method stub
		return hiddenDAO.updateHiddenType(hidden_Type);
	}


	@Override
	public Integer updateHiddenUser(Hidden_User hidden_User) {
		// TODO Auto-generated method stub
		return hiddenDAO.updateHiddenUser(hidden_User);
	}


	@Override
	public Map<String, Object> selectHiddenUser(String campusAdmin) {
		// TODO Auto-generated method stub
		return hiddenDAO.selectHiddenUser(campusAdmin);
	}


	@Override
	public Integer updateUserPassword(Hidden_User hidden_User,String OldPw) {
		// TODO Auto-generated method stub
		return hiddenDAO.updateUserPassword(hidden_User,OldPw);
	}


	@Override
	public List selectManageRegion() {
		// TODO Auto-generated method stub
		return assetsDAO.selectManageRegion();
	}

	@Override
	public List selectRoomProperty() {
		// TODO Auto-generated method stub
		return assetsDAO.selectRoomProperty();
	}


	@Override
	public Users getWetchatUsers(String openId) {
		// TODO Auto-generated method stub
		Users users= userService.getUserByOnlyOpenId(openId);
		
		MyTestUtil.print(users);
		
		return users;
	}

	@Override
	public List<Users> getWetchatAllUsers(Integer place) {
		// TODO Auto-generated method stub
		return userService.getWetchatAllUsers(1, place,null,null,null,null);
	}

	@Override
	public List findHiddenByYear() {
		// TODO Auto-generated method stub
		return assetsDAO.findHiddenByYear();
	}

	@Override
	public List findAssetByYear() {
		// TODO Auto-generated method stub
		return assetsDAO.findAssetByYear();
	}

	
	@Override
	public List findHiddenByMonthOfYear(String year) {
		// TODO Auto-generated method stub
		return assetsDAO.findHiddenByMonthOfYear(year);
	}

	@Override
	public List findHiddenAssetsByMonthOfYear(String year) {
		// TODO Auto-generated method stub
		return assetsDAO.findHiddenAssetsByMonthOfYear(year);
	}

	@Override
	public String getGUIDByPosition(String lng, String lat) {
		// TODO Auto-generated method stub
		return assetsDAO.getGUIDByPosition(lng, lat);
	}

	@Override
	public Integer getAllRoomInfoPosition() {
		// TODO Auto-generated method stub
		return (Integer) roomInfoDao.getAllRoomInfoPosition().get("total");
	}


	
}
