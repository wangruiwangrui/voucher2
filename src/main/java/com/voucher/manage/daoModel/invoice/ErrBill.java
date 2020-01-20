package com.voucher.manage.daoModel.invoice;

import java.io.Serializable;

import com.voucher.manage.daoSQL.annotations.*;

@DBTable(name="[ErrBill]")
public class ErrBill implements Serializable{

    private static final long serialVersionUID = 1L;

    @SQLInteger(name="ErrID")
	private Integer ErrID;

    @SQLString(name="out_trade_no")
	private String out_trade_no;

    @SQLString(name="err")
	private String err;

    @SQLInteger(name = "campusId")
    private Integer campusId;
    
	public Integer getCampusId() {
		return campusId;
	}

	public void setCampusId(Integer campusId) {
		this.campusId = campusId;
	}

	public void setErrID(Integer ErrID){
		this.ErrID = ErrID;
	}

	public Integer getErrID(){
		return ErrID;
	}

	public void setOut_trade_no(String out_trade_no){
		this.out_trade_no = out_trade_no;
	}

	public String getOut_trade_no(){
		return out_trade_no;
	}

	public void setErr(String err){
		this.err = err;
	}

	public String getErr(){
		return err;
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

