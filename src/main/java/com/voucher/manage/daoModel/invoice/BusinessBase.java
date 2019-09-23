package com.voucher.manage.daoModel.invoice;

import java.io.Serializable;

/**
 * 
 * @author admin
 *	蓝色发票申请参数
 */
public class BusinessBase implements Serializable{

	private static final long serialVersionUID = 1L;

	public String access_token ;
    
    public String serviceKey ;
    
    public BusinessData data ;
    
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
	public BusinessData getData() {
		return data;
	}
	public void setData(BusinessData data) {
		this.data = data;
	}
    
    

}
