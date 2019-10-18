package com.voucher.manage.daoImpl;

import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.voucher.manage.dao.BillDAO;
import com.voucher.manage.daoModel.TTT.BillServerInfo;
import com.voucher.manage.daoModel.TTT.CompanyMsg;
import com.voucher.manage.daoModel.TTT.Payment_Info;
import com.voucher.manage.daoSQL.SelectExe;
import com.voucher.manage.daoSQL.UpdateExe;

public class BillDAOImpl extends JdbcDaoSupport implements BillDAO {

	@Override
	public BillServerInfo getBillServerInfo() {
		
		BillServerInfo bill = new BillServerInfo();
		bill.setLimit(10);
		bill.setOffset(0);
		bill.setNotIn("ID");
		bill.setSort("ID asc");
		List list=SelectExe.get(this.getJdbcTemplate(), bill);
		bill=(BillServerInfo) list.get(0);
		return bill;
	}

	@Override
	public Integer updateBillServerInfo(BillServerInfo billServerinfo) {
		
		return UpdateExe.get(this.getJdbcTemplate(), billServerinfo);
	}

	@Override
	public Payment_Info selectPayment_Info(String out_trade_no) {

		Payment_Info pInfo = new Payment_Info();
		String[] where = {"out_trade_no = ",out_trade_no};
		pInfo.setWhere(where);
		pInfo.setLimit(10);
		pInfo.setOffset(0);
		pInfo.setNotIn("PaymentId");
		pInfo.setSort("PaymentId asc");
		List list = SelectExe.get(this.getJdbcTemplate(), pInfo);
		pInfo = (Payment_Info) list.get(0);
		return pInfo;
	}

	@Override
	public List qureyAll(String out_trade_no) {
		Payment_Info pInfo = new Payment_Info();
		
		return null;
	}

	@Override
	public CompanyMsg queryCompanyMsg() {
		
		CompanyMsg cMsg = new CompanyMsg();
		
		cMsg.setLimit(10);
		cMsg.setOffset(0);
		cMsg.setNotIn("ID");
		cMsg.setSort("ID asc");
		List list = SelectExe.get(getJdbcTemplate(), cMsg);
		cMsg = (CompanyMsg) list.get(0);
		return cMsg;
	}

}
