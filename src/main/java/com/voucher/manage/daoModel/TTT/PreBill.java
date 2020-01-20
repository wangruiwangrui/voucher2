package com.voucher.manage.daoModel.TTT;

import java.io.Serializable;

import com.voucher.manage.daoSQL.annotations.*;

@DBTable(name="[PreBill]")
public class PreBill implements Serializable{

    private static final long serialVersionUID = 1L;

    @SQLInteger(name = "ID")
	private Integer ID;

    @SQLString(name="data_resources")
	private String data_resources;

    @SQLString(name="nsrsbh")
	private String nsrsbh;

    @SQLString(name="out_trade_no")
	private String out_trade_no;

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

    @SQLInteger(name="campusId")
	private Integer campusId;

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

	public void setID(Integer ID){
		this.ID = ID;
	}

	public Integer getID(){
		return ID;
	}

	public void setData_resources(String data_resources){
		this.data_resources = data_resources;
	}

	public String getData_resources(){
		return data_resources;
	}

	public void setNsrsbh(String nsrsbh){
		this.nsrsbh = nsrsbh;
	}

	public String getNsrsbh(){
		return nsrsbh;
	}

	public void setOut_trade_no(String out_trade_no){
		this.out_trade_no = out_trade_no;
	}

	public String getOut_trade_no(){
		return out_trade_no;
	}

	public void setBmb_bbh(String bmb_bbh){
		this.bmb_bbh = bmb_bbh;
	}

	public String getBmb_bbh(){
		return bmb_bbh;
	}

	public void setZsfs(String zsfs){
		this.zsfs = zsfs;
	}

	public String getZsfs(){
		return zsfs;
	}

	public void setTspz(String tspz){
		this.tspz = tspz;
	}

	public String getTspz(){
		return tspz;
	}

	public void setXsf_nsrsbh(String xsf_nsrsbh){
		this.xsf_nsrsbh = xsf_nsrsbh;
	}

	public String getXsf_nsrsbh(){
		return xsf_nsrsbh;
	}

	public void setXsf_mc(String xsf_mc){
		this.xsf_mc = xsf_mc;
	}

	public String getXsf_mc(){
		return xsf_mc;
	}

	public void setXsf_dzdh(String xsf_dzdh){
		this.xsf_dzdh = xsf_dzdh;
	}

	public String getXsf_dzdh(){
		return xsf_dzdh;
	}

	public void setXsf_yhzh(String xsf_yhzh){
		this.xsf_yhzh = xsf_yhzh;
	}

	public String getXsf_yhzh(){
		return xsf_yhzh;
	}

	public void setGmf_nsrsbh(String gmf_nsrsbh){
		this.gmf_nsrsbh = gmf_nsrsbh;
	}

	public String getGmf_nsrsbh(){
		return gmf_nsrsbh;
	}

	public void setGmf_mc(String gmf_mc){
		this.gmf_mc = gmf_mc;
	}

	public String getGmf_mc(){
		return gmf_mc;
	}

	public void setGmf_dzdh(String gmf_dzdh){
		this.gmf_dzdh = gmf_dzdh;
	}

	public String getGmf_dzdh(){
		return gmf_dzdh;
	}

	public void setGmf_yhzh(String gmf_yhzh){
		this.gmf_yhzh = gmf_yhzh;
	}

	public String getGmf_yhzh(){
		return gmf_yhzh;
	}

	public void setKpr(String kpr){
		this.kpr = kpr;
	}

	public String getKpr(){
		return kpr;
	}

	public void setSkr(String skr){
		this.skr = skr;
	}

	public String getSkr(){
		return skr;
	}

	public void setFhr(String fhr){
		this.fhr = fhr;
	}

	public String getFhr(){
		return fhr;
	}

	public void setYfp_dm(String yfp_dm){
		this.yfp_dm = yfp_dm;
	}

	public String getYfp_dm(){
		return yfp_dm;
	}

	public void setYfp_hm(String yfp_hm){
		this.yfp_hm = yfp_hm;
	}

	public String getYfp_hm(){
		return yfp_hm;
	}

	public void setJshj(String jshj){
		this.jshj = jshj;
	}

	public String getJshj(){
		return jshj;
	}

	public void setHjje(String hjje){
		this.hjje = hjje;
	}

	public String getHjje(){
		return hjje;
	}

	public void setHjse(String hjse){
		this.hjse = hjse;
	}

	public String getHjse(){
		return hjse;
	}

	public void setKce(String kce){
		this.kce = kce;
	}

	public String getKce(){
		return kce;
	}

	public void setBz(String bz){
		this.bz = bz;
	}

	public String getBz(){
		return bz;
	}

	public void setKpzdbs(String kpzdbs){
		this.kpzdbs = kpzdbs;
	}

	public String getKpzdbs(){
		return kpzdbs;
	}

	public void setState(Integer state){
		this.state = state;
	}

	public Integer getState(){
		return state;
	}

	public void setErr(String err){
		this.err = err;
	}

	public String getErr(){
		return err;
	}

	public void setCampusId(Integer campusId){
		this.campusId = campusId;
	}

	public Integer getCampusId(){
		return campusId;
	}

	public void setFphxz(String Fphxz){
		this.Fphxz = Fphxz;
	}

	public String getFphxz(){
		return Fphxz;
	}

	public void setSpbm(String Spbm){
		this.Spbm = Spbm;
	}

	public String getSpbm(){
		return Spbm;
	}

	public void setXmmc(String Xmmc){
		this.Xmmc = Xmmc;
	}

	public String getXmmc(){
		return Xmmc;
	}

	public void setGgxh(String Ggxh){
		this.Ggxh = Ggxh;
	}

	public String getGgxh(){
		return Ggxh;
	}

	public void setSl(String Sl){
		this.Sl = Sl;
	}

	public String getSl(){
		return Sl;
	}

	public void setDw(String Dw){
		this.Dw = Dw;
	}

	public String getDw(){
		return Dw;
	}




/*
*数据库查询参数
*/
    @QualifiLimit(name="limit")
    private Integer limit;
    @QualifiOffset(name="offset")
    private Integer offset;
    @QualifiNotIn(name="notIn")
    private String notIn;
    @QualifiSort(name="sort")
    private String sort;
    @QualifiOrder(name="order")
    private String order;
    @QualifiWhere(name="where")
    private String[] where;
    @QualifiWhereTerm(name="whereTerm")
    private String whereTerm;


	public void setLimit(Integer limit){
		this.limit = limit;
	}

	public Integer getLimit(){
		return limit;
	}

	public void setOffset(Integer offset){
		this.offset = offset;
	}

	public Integer getOffset(){
		return offset;
	}

	public void setNotIn(String notIn){
		this.notIn = notIn;
	}

	public String getNotIn(){
		return notIn;
	}

	public void setSort(String sort){
		this.sort = sort;
	}

	public String getSort(){
		return sort;
	}

	public void setOrder(String order){
		this.order = order;
	}

	public String getOrder(){
		return order;
	}

	public void setWhere(String[] where){
		this.where = where;
	}

	public String[] getWhere(){
		return where;
	}

	public void setWhereTerm(String whereTerm){
		this.whereTerm = whereTerm;
	}

	public String getWhereTerm(){
		return whereTerm;
	}

}

