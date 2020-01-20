package com.voucher.manage.daoModelJoin;

import java.util.Date;

import com.voucher.manage.daoSQL.annotations.SQLDateTime;
import com.voucher.manage.daoSQL.annotations.SQLFloat;
import com.voucher.manage.daoSQL.annotations.SQLInteger;
import com.voucher.manage.daoSQL.annotations.SQLString;

public class Payment_ErrBill_Join {
	@SQLInteger(name="PaymentId")
	private Integer PaymentId;
	
	@SQLString(name="out_trade_no")
	private String out_trade_no;
	
	@SQLFloat(name="total_fee")
	private Float total_fee;
	
	@SQLDateTime(name="CreateTime")
	private Date CreateTime;
	
	@SQLString(name="Unit")
	private String Unit;
	
	@SQLString(name="name")
	private String name;
	
	@SQLString(name="open_id")
	private String openid;
	
	@SQLString(name="nonce_str")
	private String nonceStr;
	
	@SQLString(name="sign")
	private String Sign;
	
	@SQLString(name="trade_type")
	private String trade_type;		
	
	@SQLString(name="prepay_id")
	private String prepay_id;
	
	@SQLInteger(name = "campusId")
	private Integer campusId;
	
	@SQLInteger(name = "billState")
	private Integer billState;

    @SQLInteger(name = "ID")
	private Integer ID;

    @SQLString(name="data_resources")
	private String data_resources;

    @SQLString(name="nsrsbh")
	private String nsrsbh;

    @SQLString(name="bmb_bbh")
	private String bmb_bbh;

    @SQLString(name="zsfs")
	private String zsfs;

    @SQLString(name="tspz")
	private String tspz;

    @SQLString(name="xsf_nsrsbh")
	private String xsf_nsrsbh;

    @SQLString(name="xsf_mc")
	private String xsf_mc;

    @SQLString(name="xsf_dzdh")
	private String xsf_dzdh;

    @SQLString(name="xsf_yhzh")
	private String xsf_yhzh;

    @SQLString(name="gmf_nsrsbh")
	private String gmf_nsrsbh;

    @SQLString(name="gmf_mc")
	private String gmf_mc;

    @SQLString(name="gmf_dzdh")
	private String gmf_dzdh;

    @SQLString(name="gmf_yhzh")
	private String gmf_yhzh;

    @SQLString(name="kpr")
	private String kpr;

    @SQLString(name="skr")
	private String skr;

    @SQLString(name="fhr")
	private String fhr;

    @SQLString(name="yfp_dm")
	private String yfp_dm;

    @SQLString(name="yfp_hm")
	private String yfp_hm;

    @SQLString(name="jshj")
	private String jshj;

    @SQLString(name="hjje")
	private String hjje;

    @SQLString(name="hjse")
	private String hjse;

    @SQLString(name="kce")
	private String kce;

    @SQLString(name="bz")
	private String bz;

    @SQLString(name="kpzdbs")
	private String kpzdbs;

    @SQLInteger(name="state")
	private Integer state;

    @SQLString(name="err")
	private String err;

    @SQLString(name="Fphxz")
	private String Fphxz;

    @SQLString(name="Spbm")
	private String Spbm;

    @SQLString(name="Xmmc")
	private String Xmmc;

    @SQLString(name="Ggxh")
	private String Ggxh;

    @SQLString(name="Sl")
	private String Sl;

    @SQLString(name="Dw")
	private String Dw;

	public String getFphxz() {
		return Fphxz;
	}

	public void setFphxz(String fphxz) {
		Fphxz = fphxz;
	}

	public String getSpbm() {
		return Spbm;
	}

	public void setSpbm(String spbm) {
		Spbm = spbm;
	}

	public String getXmmc() {
		return Xmmc;
	}

	public void setXmmc(String xmmc) {
		Xmmc = xmmc;
	}

	public String getGgxh() {
		return Ggxh;
	}

	public void setGgxh(String ggxh) {
		Ggxh = ggxh;
	}

	public String getSl() {
		return Sl;
	}

	public void setSl(String sl) {
		Sl = sl;
	}

	public String getDw() {
		return Dw;
	}

	public void setDw(String dw) {
		Dw = dw;
	}

	public Integer getPaymentId() {
		return PaymentId;
	}

	public void setPaymentId(Integer paymentId) {
		PaymentId = paymentId;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public Float getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(Float total_fee) {
		this.total_fee = total_fee;
	}

	public Date getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(Date createTime) {
		CreateTime = createTime;
	}

	public String getUnit() {
		return Unit;
	}

	public void setUnit(String unit) {
		Unit = unit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getSign() {
		return Sign;
	}

	public void setSign(String sign) {
		Sign = sign;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	public String getPrepay_id() {
		return prepay_id;
	}

	public void setPrepay_id(String prepay_id) {
		this.prepay_id = prepay_id;
	}

	public Integer getCampusId() {
		return campusId;
	}

	public void setCampusId(Integer campusId) {
		this.campusId = campusId;
	}

	public Integer getBillState() {
		return billState;
	}

	public void setBillState(Integer billState) {
		this.billState = billState;
	}

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

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

	public String getBmb_bbh() {
		return bmb_bbh;
	}

	public void setBmb_bbh(String bmb_bbh) {
		this.bmb_bbh = bmb_bbh;
	}

	public String getZsfs() {
		return zsfs;
	}

	public void setZsfs(String zsfs) {
		this.zsfs = zsfs;
	}

	public String getTspz() {
		return tspz;
	}

	public void setTspz(String tspz) {
		this.tspz = tspz;
	}

	public String getXsf_nsrsbh() {
		return xsf_nsrsbh;
	}

	public void setXsf_nsrsbh(String xsf_nsrsbh) {
		this.xsf_nsrsbh = xsf_nsrsbh;
	}

	public String getXsf_mc() {
		return xsf_mc;
	}

	public void setXsf_mc(String xsf_mc) {
		this.xsf_mc = xsf_mc;
	}

	public String getXsf_dzdh() {
		return xsf_dzdh;
	}

	public void setXsf_dzdh(String xsf_dzdh) {
		this.xsf_dzdh = xsf_dzdh;
	}

	public String getXsf_yhzh() {
		return xsf_yhzh;
	}

	public void setXsf_yhzh(String xsf_yhzh) {
		this.xsf_yhzh = xsf_yhzh;
	}

	public String getGmf_nsrsbh() {
		return gmf_nsrsbh;
	}

	public void setGmf_nsrsbh(String gmf_nsrsbh) {
		this.gmf_nsrsbh = gmf_nsrsbh;
	}

	public String getGmf_mc() {
		return gmf_mc;
	}

	public void setGmf_mc(String gmf_mc) {
		this.gmf_mc = gmf_mc;
	}

	public String getGmf_dzdh() {
		return gmf_dzdh;
	}

	public void setGmf_dzdh(String gmf_dzdh) {
		this.gmf_dzdh = gmf_dzdh;
	}

	public String getGmf_yhzh() {
		return gmf_yhzh;
	}

	public void setGmf_yhzh(String gmf_yhzh) {
		this.gmf_yhzh = gmf_yhzh;
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

	public String getJshj() {
		return jshj;
	}

	public void setJshj(String jshj) {
		this.jshj = jshj;
	}

	public String getHjje() {
		return hjje;
	}

	public void setHjje(String hjje) {
		this.hjje = hjje;
	}

	public String getHjse() {
		return hjse;
	}

	public void setHjse(String hjse) {
		this.hjse = hjse;
	}

	public String getKce() {
		return kce;
	}

	public void setKce(String kce) {
		this.kce = kce;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getKpzdbs() {
		return kpzdbs;
	}

	public void setKpzdbs(String kpzdbs) {
		this.kpzdbs = kpzdbs;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getErr() {
		return err;
	}

	public void setErr(String err) {
		this.err = err;
	}

}
