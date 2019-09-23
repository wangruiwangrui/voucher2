package com.voucher.manage.daoImpl;

import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.voucher.manage.dao.BillDAO;
import com.voucher.manage.daoModel.TTT.BillServerInfo;
import com.voucher.manage.daoSQL.InsertExe;
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

}
