package com.voucher.manage.daoImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.voucher.manage.dao.RoomChangeHireLogDAO;
import com.voucher.manage.daoModel.RoomChangeHireLog;
import com.voucher.manage.daoRowMapper.RowMappers;
import com.voucher.manage.daoSQL.SelectSQL;


public class RoomChangeHireLogImpl extends JdbcDaoSupport implements RoomChangeHireLogDAO{

	@Override
	public List<RoomChangeHireLog> findAllRoomInfo(Integer limit, Integer offset, String sort, String order,
			String search) {
		// TODO Auto-generated method stub
		
		RoomChangeHireLog roomChangeHireLog=new RoomChangeHireLog();
		
		roomChangeHireLog.setLimit(limit);
		roomChangeHireLog.setOffset(offset);
		roomChangeHireLog.setNotIn("[GUID]");
		if(search!=null&&!search.trim().equals("")){
			String[] where={"RoomAddress like ",search,"Charter like ",search,
					  "OHire like ",search,"Area like ",search};
			  roomChangeHireLog.setWhereTerm("OR");
		      roomChangeHireLog.setWhere(where);
		}
		
		if(sort!=null)
			roomChangeHireLog.setSort(sort);
		if(order!=null)
			roomChangeHireLog.setOrder(order);
		
		 String sql="";
	     Map<String,Object> map=new HashMap<>();
		try {
			map = SelectSQL.get(roomChangeHireLog);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sql=(String) map.get("sql");
		List params=(List) map.get("params");
		
		return this.getJdbcTemplate().query(sql,params.toArray(),new RowMappers(RoomChangeHireLog.class));
	}

	@Override
	public Integer getRoomChangeHireLogCount(String search) {
		// TODO Auto-generated method stub
		
		Map<String,Object> map=new HashMap<>();
		
		RoomChangeHireLog roomChangeHireLog=new RoomChangeHireLog();
		
		if(search!=null&&!search.trim().equals("")){
			  String[] where={"RoomAddress like ",search,"Charter like ",search,
					  "OHire like ",search,"Area like ",search};
			  roomChangeHireLog.setWhereTerm("OR");
		      roomChangeHireLog.setWhere(where);
		}
		
		 String sql="";
	        Map<String,Object> map2=new HashMap<>();
		try {
			map2 = SelectSQL.getCount(roomChangeHireLog);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sql=(String) map2.get("sql");
		List params=(List) map2.get("params");
		map=this.getJdbcTemplate().queryForMap(sql,params.toArray());
		
		return (Integer) map.get("");
	}

}
