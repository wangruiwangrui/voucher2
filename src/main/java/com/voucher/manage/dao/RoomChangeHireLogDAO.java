package com.voucher.manage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.voucher.manage.daoModel.RoomChangeHireLog;


public interface RoomChangeHireLogDAO {

	public List<RoomChangeHireLog> findAllRoomInfo(Integer limit, Integer offset, String sort,
			String order,String search);
	
	Integer getRoomChangeHireLogCount(@Param(value="search")String search);
	
}
