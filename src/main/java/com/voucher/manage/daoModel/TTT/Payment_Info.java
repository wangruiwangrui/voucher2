package com.voucher.manage.daoModel.TTT;

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

@DBTable(name="[Payment_Info]")
public class Payment_Info {

	@SQLInteger(name="PaymentId")
	private Integer PaymentId;
	
	@SQLString(name="out_trade_no")
	private String out_trade_no;
	
	@SQLFloat(name="total_fee")
	private Float total_fee;
	
	@SQLDateTime(name="CreateTime")
	private Date CreateTime;
	
	@SQLString(name="Unit")
	private String Unit;
	
	@SQLString(name="name")
	private String name;
	
	@SQLString(name="ChartGUID")
	private String ChartGUID;
	
	@SQLString(name="open_id")
	private String openid;
	
	@SQLString(name="nonce_str")
	private String nonceStr;
	
	@SQLString(name="sign")
	private String Sign;
	
	@SQLString(name="trade_type")
	private String trade_type;		
	
	@SQLString(name="prepay_id")
	private String prepay_id;

	public Integer getPaymentId() {
		return PaymentId;
	}

	public void setPaymentId(Integer paymentId) {
		PaymentId = paymentId;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public Float getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(Float total_fee) {
		this.total_fee = total_fee;
	}

	public Date getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(Date createTime) {
		CreateTime = createTime;
	}

	public String getUnit() {
		return Unit;
	}

	public void setUnit(String unit) {
		Unit = unit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getChartGUID() {
		return ChartGUID;
	}

	public void setChartGUID(String chartGUID) {
		ChartGUID = chartGUID;
	}
	
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getSign() {
		return Sign;
	}

	public void setSign(String sign) {
		Sign = sign;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	public String getPrepay_id() {
		return prepay_id;
	}

	public void setPrepay_id(String prepay_id) {
		this.prepay_id = prepay_id;
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
