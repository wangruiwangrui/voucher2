package com.voucher.manage.daoModel.Assets;

import java.util.Date;

import java.io.Serializable;

import com.voucher.manage.daoSQL.annotations.*;

@DBTable(name="[User_AccessTime]")
public class User_AccessTime implements Serializable{

    private static final long serialVersionUID = 1L;

    @SQLString(name="open_id")
	private String open_id;

    @SQLDateTime(name="first")
	private Date first;

    @SQLInteger(name="first_count")
	private Integer first_count;

    @SQLDateTime(name="my_task")
	private Date my_task;

    @SQLInteger(name="my_task_count")
	private Integer my_task_count;

    @SQLDateTime(name="pass")
	private Date pass;

    @SQLInteger(name="pass_count")
	private Integer pass_count;

	public void setOpen_id(String open_id){
		this.open_id = open_id;
	}

	public String getOpen_id(){
		return open_id;
	}

	public void setFirst(Date first){
		this.first = first;
	}

	public Date getFirst(){
		return first;
	}

	public void setFirst_count(Integer first_count){
		this.first_count = first_count;
	}

	public Integer getFirst_count(){
		return first_count;
	}

	public void setMy_task(Date my_task){
		this.my_task = my_task;
	}

	public Date getMy_task(){
		return my_task;
	}

	public void setMy_task_count(Integer my_task_count){
		this.my_task_count = my_task_count;
	}

	public Integer getMy_task_count(){
		return my_task_count;
	}

	public void setPass(Date pass){
		this.pass = pass;
	}

	public Date getPass(){
		return pass;
	}

	public void setPass_count(Integer pass_count){
		this.pass_count = pass_count;
	}

	public Integer getPass_count(){
		return pass_count;
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

