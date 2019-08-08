package com.voucher.manage.daoModel.TTT;

import java.io.Serializable;

import com.voucher.manage.daoSQL.annotations.*;

@DBTable(name="[Hidden_Join]")
public class Hidden_Join implements Serializable{

    private static final long serialVersionUID = 1L;

    @SQLInteger(name="id")
	private Integer id;

    @SQLString(name="GUID")
	private String GUID;

    @SQLInteger(name="exist")
	private Integer exist;

    @SQLString(name="name")
	private String name;

    @SQLString(name="hidden_level")
	private String hidden_level;

    @SQLString(name="detail")
	private String detail;

    @SQLString(name="progress")
	private String progress;

    @SQLString(name="happen_time")
	private String happen_time;

    @SQLString(name="principal")
	private String principal;

    @SQLString(name="type")
	private String type;

    @SQLString(name="state")
	private String state;

    @SQLString(name="remark")
	private String remark;

    @SQLString(name="update_time")
	private String update_time;

    @SQLString(name="date")
	private String date;

    @SQLString(name="campusAdmin")
	private String campusAdmin;

    @SQLString(name="terminal")
	private String terminal;

    @SQLString(name="ManageRegion")
	private String ManageRegion;

    @SQLString(name="level_text")
	private String level_text;

    @SQLString(name="hidden_type")
	private String hidden_type;

    @SQLString(name="principal_name")
	private String principal_name;

    @SQLString(name="business")
	private String business;

    @SQLFloat(name="lng")
	private Float lng;

    @SQLFloat(name="lat")
	private Float lat;

    @SQLString(name="phone")
	private String phone;

    @SQLString(name="user_name")
	private String user_name;

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return id;
	}

	public void setGUID(String GUID){
		this.GUID = GUID;
	}

	public String getGUID(){
		return GUID;
	}

	public void setExist(Integer exist){
		this.exist = exist;
	}

	public Integer getExist(){
		return exist;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setHidden_level(String hidden_level){
		this.hidden_level = hidden_level;
	}

	public String getHidden_level(){
		return hidden_level;
	}

	public void setDetail(String detail){
		this.detail = detail;
	}

	public String getDetail(){
		return detail;
	}

	public void setProgress(String progress){
		this.progress = progress;
	}

	public String getProgress(){
		return progress;
	}

	public void setHappen_time(String happen_time){
		this.happen_time = happen_time;
	}

	public String getHappen_time(){
		return happen_time;
	}

	public void setPrincipal(String principal){
		this.principal = principal;
	}

	public String getPrincipal(){
		return principal;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setState(String state){
		this.state = state;
	}

	public String getState(){
		return state;
	}

	public void setRemark(String remark){
		this.remark = remark;
	}

	public String getRemark(){
		return remark;
	}

	public void setUpdate_time(String update_time){
		this.update_time = update_time;
	}

	public String getUpdate_time(){
		return update_time;
	}

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setCampusAdmin(String campusAdmin){
		this.campusAdmin = campusAdmin;
	}

	public String getCampusAdmin(){
		return campusAdmin;
	}

	public void setTerminal(String terminal){
		this.terminal = terminal;
	}

	public String getTerminal(){
		return terminal;
	}

	public void setManageRegion(String ManageRegion){
		this.ManageRegion = ManageRegion;
	}

	public String getManageRegion(){
		return ManageRegion;
	}

	public void setLevel_text(String level_text){
		this.level_text = level_text;
	}

	public String getLevel_text(){
		return level_text;
	}

	public void setHidden_type(String hidden_type){
		this.hidden_type = hidden_type;
	}

	public String getHidden_type(){
		return hidden_type;
	}

	public void setPrincipal_name(String principal_name){
		this.principal_name = principal_name;
	}

	public String getPrincipal_name(){
		return principal_name;
	}

	public void setBusiness(String business){
		this.business = business;
	}

	public String getBusiness(){
		return business;
	}

	public void setLng(Float lng){
		this.lng = lng;
	}

	public Float getLng(){
		return lng;
	}

	public void setLat(Float lat){
		this.lat = lat;
	}

	public Float getLat(){
		return lat;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setUser_name(String user_name){
		this.user_name = user_name;
	}

	public String getUser_name(){
		return user_name;
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

