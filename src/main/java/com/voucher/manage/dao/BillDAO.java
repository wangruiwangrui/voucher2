package com.voucher.manage.dao;

import java.util.List;
import java.util.Map;

import com.voucher.manage.daoModel.TTT.BillServerInfo;
import com.voucher.manage.daoModel.TTT.CompanyMsg;
import com.voucher.manage.daoModel.TTT.Payment_Info;

public interface BillDAO {

	BillServerInfo getBillServerInfo();

	Integer updateBillServerInfo(BillServerInfo billServerinfo);

	Payment_Info selectPayment_Info(String out_trade_no);

	List qureyAll(String out_trade_no);

	CompanyMsg queryCompanyMsg();

}
