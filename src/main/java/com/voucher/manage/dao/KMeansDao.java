package com.voucher.manage.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import com.alibaba.fastjson.JSONArray;
import com.voucher.manage.daoModel.Assets.Hidden_Check;


public interface KMeansDao {

	public CopyOnWriteArrayList<ArrayList<Double>> findPosition();
	
	public Map findAssetByDistance(int limit,int offset,Double lng, Double lat,String search);
	
	public Map findAssetByLngLat(CopyOnWriteArrayList<ArrayList<Double>> points,int page,Integer limit,Map searchMap);

	public Map getHouseTypes();

	public Map getAssetByCondition(List list);

	public Map queryAssetByHidden(Map search,String term);

	public Map getAccessOperatingConditions(List list);

	public Map getAccessByBeFrom(List list);

	public Map queryAssetByHiddenGuid(Map where, String term);

	public Hidden_Check selectHiddenStateByGuid(String guid);

	public Map findPosition2();
}
