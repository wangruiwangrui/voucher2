package com.voucher.manage.mapper;

import java.util.List;
import java.util.Map;

import com.voucher.manage.model.WeiXin;

public interface WeiXinMapper {

	WeiXin getAccessToken(Integer campusId);
	
	List<WeiXin> getAllCampus(Integer cityId);
	
	WeiXin getCampus(Integer campusId);
	
	int insertCampus(Map<String, Object> paramMap);
	
	int updateCampus(Map<String, Object> paramMap);
	
	WeiXin getByCampusId(Integer campusId);
	
	int updateHomePageByCampusId(Map<String, Object> paramMap);
	
	Integer getCampusIdByUserName(String userName);

	String getCompanyName(Integer campusId);  //通过campusId查询公众号信息

	WeiXin getWeiXinByCampusId(Integer campusId);    //通过campusId查询当前公众号所有信息

}