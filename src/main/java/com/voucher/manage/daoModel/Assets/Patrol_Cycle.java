package com.voucher.manage.daoModel.Assets;

import java.util.Date;

import java.io.Serializable;

import com.voucher.manage.daoSQL.annotations.*;

@DBTable(name="[Patrol_Cycle]")
public class Patrol_Cycle implements Serializable{

    private static final long serialVersionUID = 1L;

    @SQLInteger(name="id")
	private Integer id;

    @SQLInteger(name="hidden_cycle")
	private Integer hidden_cycle;

    @SQLInteger(name="asset_cycle")
	private Integer asset_cycle;

    @SQLDateTime(name="hidden_date")
	private Date hidden_date;

    @SQLDateTime(name="asset_date")
	private Date asset_date;

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return id;
	}

	public void setHidden_cycle(Integer hidden_cycle){
		this.hidden_cycle = hidden_cycle;
	}

	public Integer getHidden_cycle(){
		return hidden_cycle;
	}

	public void setAsset_cycle(Integer asset_cycle){
		this.asset_cycle = asset_cycle;
	}

	public Integer getAsset_cycle(){
		return asset_cycle;
	}

	public void setHidden_date(Date hidden_date){
		this.hidden_date = hidden_date;
	}

	public Date getHidden_date(){
		return hidden_date;
	}

	public void setAsset_date(Date asset_date){
		this.asset_date = asset_date;
	}

	public Date getAsset_date(){
		return asset_date;
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

