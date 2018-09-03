package com.voucher.manage.dao;

import java.util.Map;

import com.voucher.manage.daoModel.Assets.Assets_Check;

public interface AssetCheckDAO {

	public Map findAssetByDistanceDate(int limit,int offset,Double lng, Double lat,String search,String search2, Integer type);
	
	public Integer insertAssetCheck(Assets_Check assets_Check);
	
	public Map<String, Object> selectAllAssetCheckDate(String check_id);
	
	public Integer updateAssetCheck(Assets_Check assets_Check);
	
	public Map<String, Object> selectAllAssetCheck(Integer limit, Integer offset, String sort,
			String order,String address,Map<String, String> search);
	
	public Integer InsertIntoAssetsCheckDate(String check_id, String NAME, String TYPE, String uri);
	
	public Map<String, Object> selectAllAssetCheckPosition(Integer limit, Integer offset, String sort,
			String order,Map<String, String> search);
}
