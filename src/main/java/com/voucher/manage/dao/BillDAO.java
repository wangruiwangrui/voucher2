package com.voucher.manage.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.voucher.manage.daoModel.TTT.Bill;
import com.voucher.manage.daoModel.TTT.BillServerInfo;
import com.voucher.manage.daoModel.TTT.ChartInfo;
import com.voucher.manage.daoModel.TTT.CompanyMsg;
import com.voucher.manage.daoModel.TTT.Payment_Info;
import com.voucher.manage.daoModel.invoice.BusinessResult;
import com.voucher.manage.daoModel.invoice.RedBusinessResult;

public interface BillDAO {

	BillServerInfo getBillInfo(String out_trade_no);
	
	Integer updateBillServerInfo(BillServerInfo billServerinfo);

	Payment_Info selectPayment_Info(String out_trade_no);

	List qureyAll(String out_trade_no);

	CompanyMsg queryCompanyMsg(String out_trade_no);

	Integer InsertBill(BusinessResult result,Integer campusId, String out_trade_no);

	Bill queryBillByOrderNum(Bill bill);

	Integer InsertRedBill(RedBusinessResult result,Integer campusId);

	Integer updateBillImage(String nameStr,String out_trade_no);

	ChartInfo queryChartInfo(String guid);

	Integer updateBill(String order_num);

	Integer insertBillImage(String orderNum ,String imgUrl);
	
	String queryBillImgByOrderNum(String orderNum,HttpServletRequest request);

	Integer InsertBillFirst(BusinessResult result, Integer campusId);

	Integer updateRedBill(String out_trade_noRed);
}
