package com.voucher.manage.daoModel.TTT;

import java.util.Date;

import java.io.Serializable;

import com.voucher.manage.daoSQL.annotations.*;

@DBTable(name="[HirePayInfo]")
public class HirePayInfo implements Serializable{

    private static final long serialVersionUID = 1L;

	private Integer ID;

    @SQLString(name="ChartGUID")
	private String ChartGUID;

    @SQLString(name="HireListGUID")
	private String HireListGUID;

    @SQLInteger(name="HireCount")
	private Integer HireCount;

    @SQLInteger(name="GuidsCount")
	private Integer GuidsCount;
    
    @SQLFloat(name="Hire")
   	private Float Hire;

    @SQLString(name="openId")
   	private String openId;
    
    @SQLString(name="sl")
   	private String sl;
    
    @SQLString(name="out_trade_no")
   	private String out_trade_no;

	@SQLDateTime(name="Paytime")
	private Date Paytime;

	public void setID(Integer ID){
		this.ID = ID;
	}

	public Integer getID(){
		return ID;
	}

	public void setChartGUID(String ChartGUID){
		this.ChartGUID = ChartGUID;
	}

	public String getChartGUID(){
		return ChartGUID;
	}

	public void setHireListGUID(String HireListGUID){
		this.HireListGUID = HireListGUID;
	}

	public String getHireListGUID(){
		return HireListGUID;
	}

	public void setHireCount(Integer HireCount){
		this.HireCount = HireCount;
	}

	public Integer getHireCount(){
		return HireCount;
	}

	public void setGuidsCount(Integer GuidsCount){
		this.GuidsCount = GuidsCount;
	}

	public Integer getGuidsCount(){
		return GuidsCount;
	}

    public Float getHire() {
		return Hire;
	}

	public void setHire(Float hire) {
		Hire = hire;
	}
	
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	public String getSl() {
		return sl;
	}

	public void setSl(String sl) {
		this.sl = sl;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	
	public void setPaytime(Date Paytime){
		this.Paytime = Paytime;
	}

	public Date getPaytime(){
		return Paytime;
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

