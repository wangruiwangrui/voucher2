package com.voucher.manage.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.voucher.manage.daoModel.TTT.HirePayInfo;
import com.voucher.manage.daoModel.TTT.Payment_Info;
import com.voucher.manage.model.Users;

public interface FinanceDAO {

	public Map findMatureHire(Integer days,Integer limit, Integer offset, String sort, String order,
			Map<String, String> search);
	
	public Integer findMatureHireClew(String openId,Integer days);
	
	public Map findGeneralChartInfo(Integer limit, Integer offset, String sort, String order,Integer isHistory,Map<String, String> search);
		
	public Map findOverdueHire(Integer limit, Integer offset, String sort, String order,Map<String, String> search);
	
	public Map findOverdueChartInfo(Integer limit, Integer offset, String sort, String order,Integer isHistory,Map<String, String> search);
	
	public Integer findOverdueChartInfoClew(String openId);
		
	public Map findAllChartInfo(Integer limit, Integer offset, String sort, String order,Map<String, String> search);
	
	public Map<String, Object> getHireListByGUID(Integer limit, Integer offset, String sort, String order, Map search);
	
	public Integer updateHireSetHireList(Users users,List files);
	
	public Integer updateHireSetHireListWinXinPay(Map<String,String> map,List files);

	public Integer insertPaymentInfo(Payment_Info payment);

	public int insertHirePayInfo(HirePayInfo hirePayInfo);

	public List queryHireDate(ArrayList<String> list);

}
