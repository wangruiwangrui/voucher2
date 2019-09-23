package com.voucher.manage.daoModel.invoice;

import java.io.Serializable;

/**
 * 
 * @author admin
 *	发票查询结果
 */

public class QueryResult implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public String msg ;
	
    public String result ;
    
    public QueryResultBase rows ;
    
    public String total ;

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

	public QueryResultBase getRows() {
		return rows;
	}

	public void setRows(QueryResultBase rows) {
		this.rows = rows;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}
    
}
