package com.voucher.manage.daoModel.invoice;

import java.io.Serializable;

/**
 * 
 * @author admin
 *	发票查询参数
 */
public class QueryDataBase implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public String fileKey;

	public String getFileKey() {
		return fileKey;
	}

	public void setFileKey(String fileKey) {
		this.fileKey = fileKey;
	}
	
	
	 
}
