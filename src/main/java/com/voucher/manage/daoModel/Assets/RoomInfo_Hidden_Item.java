package com.voucher.manage.daoModel.Assets;

import java.sql.Clob;

import java.io.Serializable;

import com.voucher.manage.daoSQL.annotations.*;

@DBTable(name="[RoomInfo_Hidden_Item]")
public class RoomInfo_Hidden_Item implements Serializable{

    private static final long serialVersionUID = 1L;

    @SQLString(name="guid")
	private String guid;

    @SQLInteger(name="fire_extinguisher")
	private Integer fire_extinguisher;

    @SQLInteger(name="fire_aging")
	private Integer fire_aging;

    @SQLInteger(name="high_power")
	private Integer high_power;

    @SQLInteger(name="blow")
	private Integer blow;

    @SQLInteger(name="line_aging")
	private Integer line_aging;

    @SQLInteger(name="wire_chaos")
	private Integer wire_chaos;

    @SQLInteger(name="valve_aging")
	private Integer valve_aging;

    @SQLInteger(name="other_fire_hazards")
	private Integer other_fire_hazards;

    @SQLInteger(name="incline")
	private Integer incline;

    @SQLInteger(name="split")
	private Integer split;

    @SQLInteger(name="break_off")
	private Integer break_off;

    @SQLInteger(name="destroy")
	private Integer destroy;

    @SQLInteger(name="invalidation")
	private Integer invalidation;

    @SQLInteger(name="collapse")
	private Integer collapse;

    @SQLInteger(name="flaw")
	private Integer flaw;

    @SQLInteger(name="cesspool")
	private Integer cesspool;

    @SQLInteger(name="wall_up")
	private Integer wall_up;

    @SQLInteger(name="secure_channel")
	private Integer secure_channel;

    @SQLInteger(name="warning_missing")
	private Integer warning_missing;

    @SQLInteger(name="handrail_destroy")
	private Integer handrail_destroy;

    @SQLInteger(name="other_supporting")
	private Integer other_supporting;

    @SQLInteger(name="flooding")
	private Integer flooding;

    @SQLInteger(name="coast")
	private Integer coast;

    @SQLInteger(name="earthquake")
	private Integer earthquake;

    @SQLInteger(name="down")
	private Integer down;

    @SQLInteger(name="snow")
	private Integer snow;

    @SQLInteger(name="other_natural")
	private Integer other_natural;

    @SQLInteger(name="illegal_building")
	private Integer illegal_building;

    @SQLInteger(name="structural_failure")
	private Integer structural_failure;

    @SQLInteger(name="other_illegal")
	private Integer other_illegal;

    @SQLInteger(name="is_other")
	private Integer is_other;

    @SQLClob(name="other")
	private String other;

	public void setGuid(String guid){
		this.guid = guid;
	}

	public String getGuid(){
		return guid;
	}

	public void setFire_extinguisher(Integer fire_extinguisher){
		this.fire_extinguisher = fire_extinguisher;
	}

	public Integer getFire_extinguisher(){
		return fire_extinguisher;
	}

	public void setFire_aging(Integer fire_aging){
		this.fire_aging = fire_aging;
	}

	public Integer getFire_aging(){
		return fire_aging;
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

	public void setWire_chaos(Integer wire_chaos){
		this.wire_chaos = wire_chaos;
	}

	public Integer getWire_chaos(){
		return wire_chaos;
	}

	public void setValve_aging(Integer valve_aging){
		this.valve_aging = valve_aging;
	}

	public Integer getValve_aging(){
		return valve_aging;
	}

	public void setOther_fire_hazards(Integer other_fire_hazards){
		this.other_fire_hazards = other_fire_hazards;
	}

	public Integer getOther_fire_hazards(){
		return other_fire_hazards;
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

	public void setCollapse(Integer collapse){
		this.collapse = collapse;
	}

	public Integer getCollapse(){
		return collapse;
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

	public void setWall_up(Integer wall_up){
		this.wall_up = wall_up;
	}

	public Integer getWall_up(){
		return wall_up;
	}

	public void setSecure_channel(Integer secure_channel){
		this.secure_channel = secure_channel;
	}

	public Integer getSecure_channel(){
		return secure_channel;
	}

	public void setWarning_missing(Integer warning_missing){
		this.warning_missing = warning_missing;
	}

	public Integer getWarning_missing(){
		return warning_missing;
	}

	public void setHandrail_destroy(Integer handrail_destroy){
		this.handrail_destroy = handrail_destroy;
	}

	public Integer getHandrail_destroy(){
		return handrail_destroy;
	}

	public void setOther_supporting(Integer other_supporting){
		this.other_supporting = other_supporting;
	}

	public Integer getOther_supporting(){
		return other_supporting;
	}

	public void setFlooding(Integer flooding){
		this.flooding = flooding;
	}

	public Integer getFlooding(){
		return flooding;
	}

	public void setCoast(Integer coast){
		this.coast = coast;
	}

	public Integer getCoast(){
		return coast;
	}

	public void setEarthquake(Integer earthquake){
		this.earthquake = earthquake;
	}

	public Integer getEarthquake(){
		return earthquake;
	}

	public void setDown(Integer down){
		this.down = down;
	}

	public Integer getDown(){
		return down;
	}

	public void setSnow(Integer snow){
		this.snow = snow;
	}

	public Integer getSnow(){
		return snow;
	}

	public void setOther_natural(Integer other_natural){
		this.other_natural = other_natural;
	}

	public Integer getOther_natural(){
		return other_natural;
	}

	public void setIllegal_building(Integer illegal_building){
		this.illegal_building = illegal_building;
	}

	public Integer getIllegal_building(){
		return illegal_building;
	}

	public void setStructural_failure(Integer structural_failure){
		this.structural_failure = structural_failure;
	}

	public Integer getStructural_failure(){
		return structural_failure;
	}

	public void setOther_illegal(Integer other_illegal){
		this.other_illegal = other_illegal;
	}

	public Integer getOther_illegal(){
		return other_illegal;
	}

	public void setIs_other(Integer is_other){
		this.is_other = is_other;
	}

	public Integer getIs_other(){
		return is_other;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
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

