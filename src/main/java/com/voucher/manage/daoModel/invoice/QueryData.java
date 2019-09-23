package com.voucher.manage.daoModel.invoice;

import java.io.Serializable;

/**
 * 
 * @author admin
 *	发票查询参数
 */
public class QueryData implements Serializable{

	private static final long serialVersionUID = 1L;

	 public String access_token;
	 
     public String serviceKey;
     
     public QueryDataBase data;
}
