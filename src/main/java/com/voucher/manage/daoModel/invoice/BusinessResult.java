package com.voucher.manage.daoModel.invoice;

import java.io.Serializable;
import java.util.List;

public class BusinessResult implements Serializable{

	private static final long serialVersionUID = 1L;
	
    
    /// 返回信息
    
    public String msg ;
    
    /// 返回状态
    
    public String result ;
    
    /// 
    
    public List<BusinessRows> rows ;
}
