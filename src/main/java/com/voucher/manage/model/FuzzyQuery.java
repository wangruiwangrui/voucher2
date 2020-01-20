package com.voucher.manage.model;

public class FuzzyQuery {
	/**
	  * GUID
	  */
	 private String GUID;
	/**
	 * A表
	 * 编号
	 */
	private String Num;
	/**
	 * 地址
	 */
	private String Address;
	/**
	 * 建筑面积
	 */
	private String BuildArea;
	/**
	 * 房屋状况
	 */
	private String State;
	/**
	 * 房屋结构
	 */
	private String Structure;
	/**
	 * 备注
	 */
	private String sMemo;
	/**
	 * 房屋性质
	 */
	private String RoomProperty;
	/**
	 * B表
	 * 承租人
	 */
	private String Charter;
	/**
	 * 租金
	 */
	private Double TotalHire;
	/**
	 * 电话号码
	 */
	private String Phone;
	/**
	 * 合同编码
	 */
	private String ContractNo;
	
	public String getGUID() {
		return GUID;
	}
	public void setGUID(String gUID) {
		GUID = gUID;
	}
	public String getNum() {
		return Num;
	}
	public void setNum(String num) {
		Num = num;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getBuildArea() {
		return BuildArea;
	}
	public void setBuildArea(String buildArea) {
		BuildArea = buildArea;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	public String getStructure() {
		return Structure;
	}
	public void setStructure(String structure) {
		Structure = structure;
	}
	public String getsMemo() {
		return sMemo;
	}
	public void setsMemo(String sMemo) {
		this.sMemo = sMemo;
	}
	public String getRoomProperty() {
		return RoomProperty;
	}
	public void setRoomProperty(String roomProperty) {
		RoomProperty = roomProperty;
	}
	public String getCharter() {
		return Charter;
	}
	public void setCharter(String charter) {
		Charter = charter;
	}
	public Double getTotalHire() {
		return TotalHire;
	}
	public void setTotalHire(Double totalHire) {
		TotalHire = totalHire;
	}
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	public String getContractNo() {
		return ContractNo;
	}
	public void setContractNo(String contractNo) {
		ContractNo = contractNo;
	}
}