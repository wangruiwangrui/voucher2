package com.voucher.manage.daoModel.invoice;

import java.io.Serializable;

public class RedBusinessData implements Serializable{

	private static final long serialVersionUID = 1L;

	//固定参数"API"(必填)
    
    public String data_resources ;
    
    //销售方纳税人识别号(必填)
    
    public String nsrsbh ;
    
    //业务单据号；必须是唯一的(必填)
    
    public String order_num ;
    
    //税收编码版本号，参数“13.0”，具体值请询问提供商(必填)
    
    public String yfp_dm ;
    
    //征税方式 0：普通征税 1: 减按计增 2：差额征税(必填)
    
    public String yfp_hm ;
    
    //特殊票种标识:“00”=正常票种,“01”=农产品销售,“02”=农产品收购(非必填)
    
    public String bz ;
    
    //销售方纳税人识别号(必填)
    
    public String kpr ;
    
    //销售方纳税人识别号(必填)
    
    public String skr ;
    
    //销售方纳税人识别号(必填)
    
    public String fhr ;
    
    //销售方纳税人识别号(必填)
    
    public String kpzdbs ;

	public String getData_resources() {
		return data_resources;
	}

	public void setData_resources(String data_resources) {
		this.data_resources = data_resources;
	}

	public String getNsrsbh() {
		return nsrsbh;
	}

	public void setNsrsbh(String nsrsbh) {
		this.nsrsbh = nsrsbh;
	}

	public String getOrder_num() {
		return order_num;
	}

	public void setOrder_num(String order_num) {
		this.order_num = order_num;
	}

	public String getYfp_dm() {
		return yfp_dm;
	}

	public void setYfp_dm(String yfp_dm) {
		this.yfp_dm = yfp_dm;
	}

	public String getYfp_hm() {
		return yfp_hm;
	}

	public void setYfp_hm(String yfp_hm) {
		this.yfp_hm = yfp_hm;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getKpr() {
		return kpr;
	}

	public void setKpr(String kpr) {
		this.kpr = kpr;
	}

	public String getSkr() {
		return skr;
	}

	public void setSkr(String skr) {
		this.skr = skr;
	}

	public String getFhr() {
		return fhr;
	}

	public void setFhr(String fhr) {
		this.fhr = fhr;
	}

	public String getKpzdbs() {
		return kpzdbs;
	}

	public void setKpzdbs(String kpzdbs) {
		this.kpzdbs = kpzdbs;
	}
    
}
