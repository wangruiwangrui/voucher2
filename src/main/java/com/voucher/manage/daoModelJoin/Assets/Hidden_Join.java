package com.voucher.manage.daoModelJoin.Assets;

import java.io.Serializable;
import java.util.Date;

import com.voucher.manage.daoSQL.annotations.SQLDateTime;
import com.voucher.manage.daoSQL.annotations.SQLDouble;
import com.voucher.manage.daoSQL.annotations.SQLInteger;
import com.voucher.manage.daoSQL.annotations.SQLString;

public class Hidden_Join implements Serializable{

	private static final long serialVersionUID = 1L;

	@SQLInteger(name="id")
	private Integer id;

    @SQLString(name="GUID")
	private String GUID;

    @SQLInteger(name="exist")
	private Integer exist;
    
    @SQLString(name="check_id")
	private String check_id;

    @SQLString(name="check_circs")
	private String check_circs;

    @SQLDateTime(name="happen_time")
	private Date happen_time;
    
    @SQLDateTime(name="update_time")
   	private Date update_time;
    
    @SQLDateTime(name="date")
	private Date date;

    @SQLString(name="remark")
	private String remark;

    @SQLString(name="check_name")
   	private String check_name;

    @SQLString(name="principal")
   	private String principal;
    
    @SQLString(name="campusAdmin")
   	private String campusAdmin;

    @SQLString(name="terminal")
   	private String terminal;
    
    /*
     * Hidden_User
     */
    @SQLString(name="principal_name")
   	private String principal_name;

    @SQLString(name="business")
   	private String business;
    
    /*
     * Position
     */   
    @SQLDouble(name="lng")
	private Double lng;

    @SQLDouble(name="lat")
	private Double lat;
    
    /*
     * WeiXin_User
     */
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
	
	public void setCheck_id(String check_id){
		this.check_id = check_id;
	}

	public String getCheck_id(){
		return check_id;
	}

	public void setCheck_circs(String check_circs){
		this.check_circs = check_circs;
	}

	public String getCheck_circs(){
		return check_circs;
	}

	
	public void setHappen_time(Date happen_time){
		this.happen_time = happen_time;
	}

	public Date getHappen_time(){
		return happen_time;
	}

	public void setPrincipal(String principal){
		this.principal = principal;
	}

	public String getPrincipal(){
		return principal;
	}

	public void setRemark(String remark){
		this.remark = remark;
	}

	public String getRemark(){
		return remark;
	}

	public void setUpdate_time(Date update_time){
		this.update_time = update_time;
	}

	public Date getUpdate_time(){
		return update_time;
	}
	
	public void setDate(Date date){
		this.date = date;
	}

	public Date getDate(){
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
	
	public void setLng(Double lng){
		this.lng = lng;
	}

	public Double getLng(){
		return lng;
	}

	public void setLat(Double lat){
		this.lat = lat;
	}

	public Double getLat(){
		return lat;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
	
}
