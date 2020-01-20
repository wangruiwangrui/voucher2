package com.voucher.manage.model;

import java.util.List;

public class FuzzyQuery2 {
	/**
	 * 
	 * 模糊查询类
	 */
	private List <FuzzyQuery> data;
	/**
	 * 总数量
	 */
	private int allCount;
	public List<FuzzyQuery> getData() {
		return data;
	}
	public void setData(List<FuzzyQuery> data) {
		this.data = data;
	}
	public int getAllCount() {
		return allCount;
	}
	public void setAllCount(int allCount) {
		this.allCount = allCount;
	}
	
}