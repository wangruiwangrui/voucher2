package com.voucher.manage.daoModel.TTT;

import java.io.Serializable;

import com.voucher.manage.daoSQL.annotations.*;

@DBTable(name="[CompanyMsg]")
public class CompanyMsg implements Serializable{

    private static final long serialVersionUID = 1L;

    @SQLInteger(name = "ID")
	private Integer ID;

    @SQLString(name="CompanyName")
	private String CompanyName;

    @SQLString(name="Nsrsbh")
	private String Nsrsbh;

    @SQLString(name="Dzdh")
	private String Dzdh;

    @SQLString(name="Yhzh")
	private String Yhzh;

    @SQLString(name="Operator")
	private String Operator;

    @SQLInteger(name = "campusId")
    private Integer campusId; 
    
	public void setID(Integer ID){
		this.ID = ID;
	}

	public Integer getID(){
		return ID;
	}

	public void setCompanyName(String CompanyName){
		this.CompanyName = CompanyName;
	}

	public String getCompanyName(){
		return CompanyName;
	}

	public void setNsrsbh(String Nsrsbh){
		this.Nsrsbh = Nsrsbh;
	}

	public String getNsrsbh(){
		return Nsrsbh;
	}

	public void setDzdh(String Dzdh){
		this.Dzdh = Dzdh;
	}

	public String getDzdh(){
		return Dzdh;
	}

	public void setYhzh(String Yhzh){
		this.Yhzh = Yhzh;
	}

	public String getYhzh(){
		return Yhzh;
	}

	public void setOperator(String Operator){
		this.Operator = Operator;
	}

	public String getOperator(){
		return Operator;
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

