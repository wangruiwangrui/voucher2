package com.voucher.manage.daoModel.invoice;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author admin
 *	红票申请返回结果
 */
public class RedBusinessResult implements Serializable{

	private static final long serialVersionUID = 1L;

    //返回信息
    public String msg ;
    
    //返回状态
    public String result ;
    
    public List<RedBusinessRows> rows ;
    
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

	public List<RedBusinessRows> getRows() {
		return rows;
	}

	public void setRows(List<RedBusinessRows> rows) {
		this.rows = rows;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}
    
}
