package com.voucher.manage.daoModel.TTT;

import java.sql.Clob;

import java.io.Serializable;

import com.voucher.manage.daoSQL.annotations.*;

@DBTable(name="[Hidden_Check_Item]")
public class Hidden_Check_Item implements Serializable{

    private static final long serialVersionUID = 1L;

    @SQLString(name="check_id")
	private String check_id;

    @SQLInteger(name="fire_extinguisher")
	private Integer fire_extinguisher;

    @SQLInteger(name="high_power")
	private Integer high_power;

    @SQLInteger(name="blow")
	private Integer blow;

    @SQLInteger(name="line_aging")
	private Integer line_aging;

    @SQLInteger(name="incline")
	private Integer incline;

    @SQLInteger(name="split")
	private Integer split;

    @SQLInteger(name="down")
	private Integer down;

    @SQLInteger(name="break_off")
	private Integer break_off;

    @SQLInteger(name="destroy")
	private Integer destroy;

    @SQLInteger(name="invalidation")
	private Integer invalidation;

    @SQLInteger(name="flaw")
	private Integer flaw;

    @SQLInteger(name="cesspool")
	private Integer cesspool;

    @SQLInteger(name="coast")
	private Integer coast;

    @SQLInteger(name="wall_up")
	private Integer wall_up;

    @SQLInteger(name="is_other")
	private Integer is_other;

    @SQLClob(name="other")
	private Clob other;

	public void setCheck_id(String check_id){
		this.check_id = check_id;
	}

	public String getCheck_id(){
		return check_id;
	}

	public void setFire_extinguisher(Integer fire_extinguisher){
		this.fire_extinguisher = fire_extinguisher;
	}

	public Integer getFire_extinguisher(){
		return fire_extinguisher;
	}

	public void setHigh_power(Integer high_power){
		this.high_power = high_power;
	}

	public Integer getHigh_power(){
		return high_power;
	}

	public void setBlow(Integer blow){
		this.blow = blow;
	}

	public Integer getBlow(){
		return blow;
	}

	public void setLine_aging(Integer line_aging){
		this.line_aging = line_aging;
	}

	public Integer getLine_aging(){
		return line_aging;
	}

	public void setIncline(Integer incline){
		this.incline = incline;
	}

	public Integer getIncline(){
		return incline;
	}

	public void setSplit(Integer split){
		this.split = split;
	}

	public Integer getSplit(){
		return split;
	}

	public void setDown(Integer down){
		this.down = down;
	}

	public Integer getDown(){
		return down;
	}

	public void setBreak_off(Integer break_off){
		this.break_off = break_off;
	}

	public Integer getBreak_off(){
		return break_off;
	}

	public void setDestroy(Integer destroy){
		this.destroy = destroy;
	}

	public Integer getDestroy(){
		return destroy;
	}

	public void setInvalidation(Integer invalidation){
		this.invalidation = invalidation;
	}

	public Integer getInvalidation(){
		return invalidation;
	}

	public void setFlaw(Integer flaw){
		this.flaw = flaw;
	}

	public Integer getFlaw(){
		return flaw;
	}

	public void setCesspool(Integer cesspool){
		this.cesspool = cesspool;
	}

	public Integer getCesspool(){
		return cesspool;
	}

	public void setCoast(Integer coast){
		this.coast = coast;
	}

	public Integer getCoast(){
		return coast;
	}

	public void setWall_up(Integer wall_up){
		this.wall_up = wall_up;
	}

	public Integer getWall_up(){
		return wall_up;
	}

	public void setIs_other(Integer is_other){
		this.is_other = is_other;
	}

	public Integer getIs_other(){
		return is_other;
	}

	public void setOther(Clob other){
		this.other = other;
	}

	public Clob getOther(){
		return other;
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

