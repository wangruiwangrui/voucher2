package com.voucher.manage.dao;

import java.util.Map;

import com.voucher.manage.daoModel.TTT.BillServerInfo;

public interface BillDAO {

	BillServerInfo getBillServerInfo();

	Integer updateBillServerInfo(BillServerInfo billServerinfo);

}
