package com.voucher.manage.daoModel.invoice;

import java.io.Serializable;
import java.util.List;

public class BusinessResult implements Serializable{

	private static final long serialVersionUID = 1L;
	
    
    /// 返回信息
    private String msg ;
    
    /// 返回状态
    private String result ;
    
    /// 
    private List<BusinessRows> rows ;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<BusinessRows> getRows() {
		return rows;
	}

	public void setRows(List<BusinessRows> rows) {
		this.rows = rows;
	}
}
