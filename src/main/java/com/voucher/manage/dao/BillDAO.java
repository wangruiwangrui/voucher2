package com.voucher.manage.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.voucher.manage.daoModel.TTT.Bill;
import com.voucher.manage.daoModel.TTT.BillServerInfo;
import com.voucher.manage.daoModel.TTT.ChartInfo;
import com.voucher.manage.daoModel.TTT.CompanyMsg;
import com.voucher.manage.daoModel.TTT.Payment_Info;
import com.voucher.manage.daoModel.TTT.PreBill;
import com.voucher.manage.daoModel.invoice.BusinessData;
import com.voucher.manage.daoModel.invoice.BusinessResult;
import com.voucher.manage.daoModel.invoice.RedBusinessResult;

public interface BillDAO {

	BillServerInfo getBillInfo(String out_trade_no,String sl);
	
	Integer updateBillServerInfo(BillServerInfo billServerinfo);

	Payment_Info selectPayment_Info(String out_trade_no);

	List qureyAll(String out_trade_no);

	CompanyMsg queryCompanyMsg(String out_trade_no);

	Bill queryBillByOrderNum(Bill bill);

	Integer updateBillImage(String nameStr,String out_trade_no);

	ChartInfo queryChartInfo(String guid);

	Integer updateBill(String order_num);

	Integer insertBillImage(String orderNum ,String imgUrl);
	
	String queryBillImgByOrderNum(String orderNum,HttpServletRequest request);

	Integer updateRedBill(String out_trade_noRed);

	Integer InserIntoPreBill(BusinessData bData, Integer campusId);

	Integer updateErrMsg(String out_trade_no ,String result);

	PreBill selectPreBill(String out_trade_no);

	Integer InserIntoBill(BusinessData bData, Integer campusId);

	Integer updatePreBill(String out_trade_no, BusinessResult result);

	Integer updatePreBillMsg(String out_trade_no, BusinessResult result);

	Bill selectBillByOrderNum(String out_trade_no);

	Integer updateToRed(RedBusinessResult result, String out_trade_no);

	Integer updateRedBillMsg(String out_trade_no, RedBusinessResult result);

	Integer updateToBlue(BusinessResult result, String out_trade_noRed);

	Integer updateBillMsg(String out_trade_noRed, BusinessResult result);

	Payment_Info getPaymenInfo(String out_trade_no_new);

}
