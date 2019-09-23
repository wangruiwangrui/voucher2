package com.voucher.manage.daoModel.invoice;

import java.io.Serializable;

/**
 * 
 * @author admin
 *	红票申请参数
 */
public class RedBusinessBase implements Serializable{

	private static final long serialVersionUID = 1L;

	public String access_token ;
	 
     public String serviceKey ;
     
     public RedBusinessData data ;

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getServiceKey() {
		return serviceKey;
	}

	public void setServiceKey(String serviceKey) {
		this.serviceKey = serviceKey;
	}

	public RedBusinessData getData() {
		return data;
	}

	public void setData(RedBusinessData data) {
		this.data = data;
	}

     
}
