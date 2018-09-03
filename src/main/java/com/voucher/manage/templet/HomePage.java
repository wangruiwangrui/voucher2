package com.voucher.manage.templet;

public class HomePage {
	 /*
     * 公众号ID
     */
    private String campusId;
    /*
     * 公众号名称
     */
    private String campusName="";
    /*
     * oauth回调地址
     */
    private String redirectUrl="";
   

	public String getCampusId() {
		return campusId;
	}

	public void setCampusId(String campusId) {
		this.campusId = campusId;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getCampusName() {
		return campusName;
	}

	public void setCampusName(String campusName) {
		this.campusName = campusName;
	}
}
