package com.voucher.manage.daoModelJoin.Assets;

import java.io.Serializable;
import java.util.Date;

import com.voucher.manage.daoSQL.annotations.SQLDateTime;
import com.voucher.manage.daoSQL.annotations.SQLInteger;
import com.voucher.manage.daoSQL.annotations.SQLString;

public class Hidden_Check_Item_Join implements Serializable{
	
	  @SQLInteger(name="id")
		private Integer id;

	    @SQLString(name="GUID")
		private String GUID;

	    @SQLInteger(name="exist")
		private Integer exist;
	    
	    @SQLString(name="check_id")
		private String check_id;

	    @SQLString(name="check_name")
		private String check_name;

	    @SQLString(name="principal")
		private String principal;

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

	    @SQLString(name="campusAdmin")
	   	private String campusAdmin;

	    @SQLString(name="terminal")
	   	private String terminal;
	    
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

		public void setCheck_name(String check_name){
			this.check_name = check_name;
		}

		public String getCheck_name(){
			return check_name;
		}

		public void setPrincipal(String principal){
			this.principal = principal;
		}

		public String getPrincipal(){
			return principal;
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

		public void setRemark(String remark){
			this.remark = remark;
		}

		public String getRemark(){
			return remark;
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
	    
	    @SQLString(name="other")
		private String other;

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
		
		public void setOther(String other){
			this.other = other;
		}

		public String getOther(){
			return other;
		}

		
}
