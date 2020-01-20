package com.voucher.manage.daoModel.TTT;

import java.util.Date;

import java.io.Serializable;

import com.voucher.manage.daoSQL.annotations.*;

@DBTable(name="[RedBill]")
public class RedBill implements Serializable{

    private static final long serialVersionUID = 1L;

    @SQLInteger(name = "RedBillId")
	private Integer RedBillId;

	@SQLString(name="msg")
	private String msg;

    @SQLString(name="fhr")
	private String fhr;

    @SQLString(name="xsf_mc")
	private String xsf_mc;

    @SQLString(name="yfp_hm")
	private String yfp_hm;

    @SQLString(name="bz")
	private String bz;

    @SQLString(name="xsf_yhzh")
	private String xsf_yhzh;

    @SQLString(name="gmf_mc")
	private String gmf_mc;

    @SQLString(name="hjse")
	private String hjse;

    @SQLString(name="fp_dm")
	private String fp_dm;

    @SQLString(name="kce")
	private String kce;

    @SQLString(name="yfp_dm")
	private String yfp_dm;

    @SQLString(name="fp_hm")
	private String fp_hm;

    @SQLString(name="gmf_nsrsbh")
	private String gmf_nsrsbh;

    @SQLString(name="itype")
	private String itype;

    @SQLString(name="jym")
	private String jym;

    @SQLString(name="kplx")
	private String kplx;

    @SQLString(name="pdf_item_key")
	private String pdf_item_key;

    @SQLString(name="order_num")
	private String order_num;

    @SQLString(name="zsfs")
	private String zsfs;

    @SQLString(name="xsf_dzdh")
	private String xsf_dzdh;

    @SQLString(name="jqbh")
	private String jqbh;

    @SQLString(name="hjje")
	private String hjje;

    @SQLString(name="gmf_dzdh")
	private String gmf_dzdh;

    @SQLString(name="fpqqlsh")
	private String fpqqlsh;

    @SQLString(name="skr")
	private String skr;

    @SQLString(name="gmf_yhzh")
	private String gmf_yhzh;

    @SQLString(name="kpr")
	private String kpr;

    @SQLString(name="xsf_nsrsbh")
	private String xsf_nsrsbh;

    @SQLString(name="fp_mw")
	private String fp_mw;

    @SQLString(name="jshj")
	private String jshj;

    @SQLString(name="pdf_key")
	private String pdf_key;

    @SQLDateTime(name="kprq")
	private Date kprq;

    @SQLInteger(name="state")
	private Integer state;

    @SQLInteger(name="campusId")
	private Integer campusId;

    public void setRedBillId(Integer redBillId) {
		RedBillId = redBillId;
	}

	public Integer getRedBillId(){
		return RedBillId;
	}

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setFhr(String fhr){
		this.fhr = fhr;
	}

	public String getFhr(){
		return fhr;
	}

	public void setXsf_mc(String xsf_mc){
		this.xsf_mc = xsf_mc;
	}

	public String getXsf_mc(){
		return xsf_mc;
	}

	public void setYfp_hm(String yfp_hm){
		this.yfp_hm = yfp_hm;
	}

	public String getYfp_hm(){
		return yfp_hm;
	}

	public void setBz(String bz){
		this.bz = bz;
	}

	public String getBz(){
		return bz;
	}

	public void setXsf_yhzh(String xsf_yhzh){
		this.xsf_yhzh = xsf_yhzh;
	}

	public String getXsf_yhzh(){
		return xsf_yhzh;
	}

	public void setGmf_mc(String gmf_mc){
		this.gmf_mc = gmf_mc;
	}

	public String getGmf_mc(){
		return gmf_mc;
	}

	public void setHjse(String hjse){
		this.hjse = hjse;
	}

	public String getHjse(){
		return hjse;
	}

	public void setFp_dm(String fp_dm){
		this.fp_dm = fp_dm;
	}

	public String getFp_dm(){
		return fp_dm;
	}

	public void setKce(String kce){
		this.kce = kce;
	}

	public String getKce(){
		return kce;
	}

	public void setYfp_dm(String yfp_dm){
		this.yfp_dm = yfp_dm;
	}

	public String getYfp_dm(){
		return yfp_dm;
	}

	public void setFp_hm(String fp_hm){
		this.fp_hm = fp_hm;
	}

	public String getFp_hm(){
		return fp_hm;
	}

	public void setGmf_nsrsbh(String gmf_nsrsbh){
		this.gmf_nsrsbh = gmf_nsrsbh;
	}

	public String getGmf_nsrsbh(){
		return gmf_nsrsbh;
	}

	public void setItype(String itype){
		this.itype = itype;
	}

	public String getItype(){
		return itype;
	}

	public void setJym(String jym){
		this.jym = jym;
	}

	public String getJym(){
		return jym;
	}

	public void setKplx(String kplx){
		this.kplx = kplx;
	}

	public String getKplx(){
		return kplx;
	}

	public void setPdf_item_key(String pdf_item_key){
		this.pdf_item_key = pdf_item_key;
	}

	public String getPdf_item_key(){
		return pdf_item_key;
	}

	public void setOrder_num(String order_num){
		this.order_num = order_num;
	}

	public String getOrder_num(){
		return order_num;
	}

	public void setZsfs(String zsfs){
		this.zsfs = zsfs;
	}

	public String getZsfs(){
		return zsfs;
	}

	public void setXsf_dzdh(String xsf_dzdh){
		this.xsf_dzdh = xsf_dzdh;
	}

	public String getXsf_dzdh(){
		return xsf_dzdh;
	}

	public void setJqbh(String jqbh){
		this.jqbh = jqbh;
	}

	public String getJqbh(){
		return jqbh;
	}

	public void setHjje(String hjje){
		this.hjje = hjje;
	}

	public String getHjje(){
		return hjje;
	}

	public void setGmf_dzdh(String gmf_dzdh){
		this.gmf_dzdh = gmf_dzdh;
	}

	public String getGmf_dzdh(){
		return gmf_dzdh;
	}

	public void setFpqqlsh(String fpqqlsh){
		this.fpqqlsh = fpqqlsh;
	}

	public String getFpqqlsh(){
		return fpqqlsh;
	}

	public void setSkr(String skr){
		this.skr = skr;
	}

	public String getSkr(){
		return skr;
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

	public void setXsf_nsrsbh(String xsf_nsrsbh){
		this.xsf_nsrsbh = xsf_nsrsbh;
	}

	public String getXsf_nsrsbh(){
		return xsf_nsrsbh;
	}

	public void setFp_mw(String fp_mw){
		this.fp_mw = fp_mw;
	}

	public String getFp_mw(){
		return fp_mw;
	}

	public void setJshj(String jshj){
		this.jshj = jshj;
	}

	public String getJshj(){
		return jshj;
	}

	public void setPdf_key(String pdf_key){
		this.pdf_key = pdf_key;
	}

	public String getPdf_key(){
		return pdf_key;
	}

	public void setKprq(Date kprq){
		this.kprq = kprq;
	}

	public Date getKprq(){
		return kprq;
	}

	public void setState(Integer state){
		this.state = state;
	}

	public Integer getState(){
		return state;
	}

	public void setCampusId(Integer campusId){
		this.campusId = campusId;
	}

	public Integer getCampusId(){
		return campusId;
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

