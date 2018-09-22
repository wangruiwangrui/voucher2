package com.voucher.manage.dao;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;
import com.voucher.manage.daoModel.Assets.Position;
import com.voucher.manage.daoModel.TTT.ChartInfo;

public interface AssetsDAO {

	public Map findAllHidden(Integer limit, Integer offset, String sort, String order,
			Map<String, String> search);
	
	public String getGUIDByPosition(String lng,String lat);
	
	public Integer updatePosition(Position position);
	
	public Integer updatePositionByRoomInfo(Position position , boolean isUpdate);
	
	public Integer updatePositionByCheck(Position position);
	
	public Integer updatePositionByNeaten(Position position);
	
	public Integer countPositionByGUID(Position position);
	
	public Integer updateCheckPosition(Position position); 
	
	public Integer updateNeatenPosition(Position position);
	
	public Integer deletePosition(Position position);
	
	public Map findAllPosition(String manageRegion);
	
	public Map findAllCheckPosition();
	
	public Map findAllNeatenPosition();
	
	public List findPosition(Position position);
	
	public Map findHiddenByDistance(int limit,int offset,Double lng, Double lat,String search);
	
	public Map findAssetByDistance(int limit,int offset,Double lng, Double lat,String search);
	
	public Map findHiddenAssetByDistance(int limit,int offset,Double lng, Double lat,String search);
	
	public Map findAssetHiddenByDistance(int limit,int offset,Double lng, Double lat,String search);
	
	public Map findAssetByDistanceDate(int limit,int offset,Double lng, Double lat,String search,String search2, Integer type);
	
	public Map findHiddenByPoint(Double lng, Double lat,Double distance,String search);
	
	public Map findAssetByPoint(int limit,int offset,Double lng, Double lat,Double distance,String search);
	
	public Map findAssetByHideen(Integer limit, Integer offset, String sort, String order,
			Map<String, String> search);
	
	public Map findHideenByAsset(Integer limit, Integer offset, String sort, String order,
			Map<String, String> search);
	
	public Integer findNotHidden();
	
	public Integer findInHidden();
	
	public Integer findSuccessHidden();
	
	public Integer findAllAssets();
	
	public Integer findAllAssetsHidden();
	
	public String findLastHidden();
	
	public String  findIgnoreHidden();
		
	public List selectManageRegion();
	
	public List selectRoomProperty();
	
	public List selectFareItem();
	
	public List selectDangerClassification();
	
	public ChartInfo getChartInfoByIDNo(String IDNo);           //根据身份证查找承租户
	
	public ChartInfo getChartInfoByChartGUID(String chartGUID);           //根据GUID查找承租户
	
	public Map findChartInfo_HireListByIDNo(String IDNo);
	
	public Map findHireListByGUID(String GUID);
	
	public List findHiddenByYear();
	
	public List findAssetByYear();
	
	public List findHiddenByMonthOfYear(String year);
	
	public List findHiddenAssetsByMonthOfYear(String year);
	
	public Integer getAllAssetByHidden_GUID(String guid);
	
	public Map getTestOr(Map search);
	
	public Map<String, Object> findAllRoomInfo_Position(Integer limit, Integer offset, String sort,
			 String order,String term,Map search);
	
	public Map<String, Object> findAllRoomInfoCheckDateNULL(Integer limit, Integer offset, String sort,
			String order,String term,String searchTerm,Map search,Integer type);
	
	public Map<String, Object> findAllHire(String term,Map search);
	
	public Map<String, Object> getCountCheckByGUID(@RequestParam String guid);
	
	public Map<String, Object> getAllChartInfo(Integer limit, Integer offset, String sort,
			String order,Map search);
}
