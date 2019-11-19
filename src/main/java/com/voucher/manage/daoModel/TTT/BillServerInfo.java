package com.voucher.manage.daoModel.TTT;

import java.io.Serializable;
import java.util.Date;

import com.voucher.manage.daoSQL.annotations.DBTable;
import com.voucher.manage.daoSQL.annotations.QualifiLimit;
import com.voucher.manage.daoSQL.annotations.QualifiNotIn;
import com.voucher.manage.daoSQL.annotations.QualifiOffset;
import com.voucher.manage.daoSQL.annotations.QualifiOrder;
import com.voucher.manage.daoSQL.annotations.QualifiSort;
import com.voucher.manage.daoSQL.annotations.QualifiWhere;
import com.voucher.manage.daoSQL.annotations.QualifiWhereTerm;
import com.voucher.manage.daoSQL.annotations.SQLDateTime;
import com.voucher.manage.daoSQL.annotations.SQLFloat;
import com.voucher.manage.daoSQL.annotations.SQLInteger;
import com.voucher.manage.daoSQL.annotations.SQLString;

/**
 * 
 * @author admin
 *	发票获取Access_token参数
 *	发票应用接口参数
 */
@DBTable(name="[BillServerInfo]")
public class BillServerInfo implements Serializable{

	private static final long serialVersionUID = 1L;

	@SQLInteger(name="ID")
	private Integer ID;
	
	@SQLString(name="OpenID")
	private String OpenID;
	
	@SQLString(name="AppSecret")
	private String AppSecret;
	
	@SQLString(name="API_TOKEN_URL")
	private String API_TOKEN_URL;
	
	@SQLString(name="API_BUSS_URL")
	private String API_BUSS_URL;
	
	@SQLString(name="TokenStr")
	private String TokenStr;
	
	@SQLInteger(name="TokenExpireTime")
	private Integer TokenExpireTime;
	
	@SQLDateTime(name="TokenCreateDate")
	private Date TokenCreateDate;
	
	@SQLString(name="Data_Resources")
	private String Data_Resources;
	
	@SQLString(name="Bmb_BBH")
	private String Bmb_BBH;
	
	@SQLString(name="Zsfs")
	private String Zsfs;
	
	@SQLString(name="Fphxz")
	private String Fphxz;
	
	@SQLString(name="Spbm")
	private String Spbm;
	
	@SQLString(name="Xmmc")
	private String Xmmc;
	
	@SQLString(name="Ggxh")
	private String Ggxh;
	
	@SQLString(name="Dw")
	private String Dw;
	
	@SQLFloat(name="Sl")
	private Float Sl;
	
	@SQLString(name="Ticket_URL")
	private String Ticket_URL;
	
	@SQLString(name="Ticket")
	private String Ticket;
	
	@SQLDateTime(name="Ticket_Time")
	private Date Ticket_Time;

	@SQLString(name="s_pappid")
	private String s_pappid;
	
	@SQLInteger(name = "campusId")
	private Integer campusId;

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public String getOpenID() {
		return OpenID;
	}

	public void setOpenID(String openID) {
		OpenID = openID;
	}

	public String getAppSecret() {
		return AppSecret;
	}

	public void setAppSecret(String appSecret) {
		AppSecret = appSecret;
	}

	public String getAPI_TOKEN_URL() {
		return API_TOKEN_URL;
	}

	public void setAPI_TOKEN_URL(String aPI_TOKEN_URL) {
		API_TOKEN_URL = aPI_TOKEN_URL;
	}

	public String getAPI_BUSS_URL() {
		return API_BUSS_URL;
	}

	public void setAPI_BUSS_URL(String aPI_BUSS_URL) {
		API_BUSS_URL = aPI_BUSS_URL;
	}

	public String getTokenStr() {
		return TokenStr;
	}

	public void setTokenStr(String tokenStr) {
		TokenStr = tokenStr;
	}

	public Integer getTokenExpireTime() {
		return TokenExpireTime;
	}

	public void setTokenExpireTime(Integer tokenExpireTime) {
		TokenExpireTime = tokenExpireTime;
	}

	public Date getTokenCreateDate() {
		return TokenCreateDate;
	}

	public void setTokenCreateDate(Date tokenCreateDate) {
		TokenCreateDate = tokenCreateDate;
	}

	public String getData_Resources() {
		return Data_Resources;
	}

	public void setData_Resources(String data_Resources) {
		Data_Resources = data_Resources;
	}

	public String getBmb_BBH() {
		return Bmb_BBH;
	}

	public void setBmb_BBH(String bmb_BBH) {
		Bmb_BBH = bmb_BBH;
	}

	public String getZsfs() {
		return Zsfs;
	}

	public void setZsfs(String zsfs) {
		Zsfs = zsfs;
	}

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

	public String getDw() {
		return Dw;
	}

	public void setDw(String dw) {
		Dw = dw;
	}

	public Float getSl() {
		return Sl;
	}

	public void setSl(Float sl) {
		Sl = sl;
	}

	public String getTicket_URL() {
		return Ticket_URL;
	}

	public void setTicket_URL(String ticket_URL) {
		Ticket_URL = ticket_URL;
	}

	public String getTicket() {
		return Ticket;
	}

	public void setTicket(String ticket) {
		Ticket = ticket;
	}

	public Date getTicket_Time() {
		return Ticket_Time;
	}

	public void setTicket_Time(Date ticket_Time) {
		Ticket_Time = ticket_Time;
	}
	
	public String getS_pappid() {
		return s_pappid;
	}

	public void setS_pappid(String s_pappid) {
		this.s_pappid = s_pappid;
	}
	
	public Integer getCampusId() {
		return campusId;
	}

	public void setCampusId(Integer campusId) {
		this.campusId = campusId;
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
