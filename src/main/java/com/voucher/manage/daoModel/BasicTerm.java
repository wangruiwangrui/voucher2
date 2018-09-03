package com.voucher.manage.daoModel;

import com.voucher.manage.daoSQL.annotations.QualifiLimit;
import com.voucher.manage.daoSQL.annotations.QualifiNotIn;
import com.voucher.manage.daoSQL.annotations.QualifiOffset;
import com.voucher.manage.daoSQL.annotations.QualifiOrder;
import com.voucher.manage.daoSQL.annotations.QualifiSort;
import com.voucher.manage.daoSQL.annotations.QualifiWhere;
import com.voucher.manage.daoSQL.annotations.QualifiWhereTerm;

/*
 * 每个Model类都需要的数据库where参数
 */
public class BasicTerm {

	  /*
	   * 数据库查询参数
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
	   /*
	    * 数据库查询where变量
	    */
	   @QualifiWhere(name="where")
	   private String[] where;
	   @QualifiWhereTerm(name="whereTerm") 
	   private String whereTerm;         //多个where的连接条件
	   
	   public Integer getOffset() {
			return offset;
		}

		public void setOffset(Integer offset) {
			this.offset = offset;
		}

		public String getSort() {
			return sort;
		}

		public void setSort(String sort) {
			this.sort = sort;
		}

		public String getOrder() {
			return order;
		}

		public void setOrder(String order) {
			this.order = order;
		}

		public Integer getLimit() {
			return limit;
		}

		public void setLimit(Integer limit) {
			this.limit = limit;
		}

		public String[] getWhere() {
			return where;
		}

		public void setWhere(String[] where) {
			this.where = where;
		}

		public String getNotIn() {
			return notIn;
		}

		public void setNotIn(String notIn) {
			this.notIn = notIn;
		}

		public String getWhereTerm() {
			return whereTerm;
		}

		public void setWhereTerm(String whereTerm) {
			this.whereTerm = whereTerm;
		}
	
}
