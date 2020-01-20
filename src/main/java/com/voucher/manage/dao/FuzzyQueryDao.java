package com.voucher.manage.dao;

import com.voucher.manage.model.FuzzyQuery2;

public interface FuzzyQueryDao {

	public FuzzyQuery2 fuzzyQuery(String Address,String Num,String RoomProperty,String Charter,String Phone,String ContractNo,int pageSize,int page);
}
