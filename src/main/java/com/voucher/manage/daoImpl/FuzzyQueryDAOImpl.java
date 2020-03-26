package com.voucher.manage.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.voucher.manage.dao.FuzzyQueryDao;
import com.voucher.manage.model.FuzzyQuery2;


public class FuzzyQueryDAOImpl extends JdbcDaoSupport implements FuzzyQueryDao {

    public FuzzyQuery2 fuzzyQuery(String Address,String Num,String RoomProperty,String Charter,String Phone,String ContractNo,int pageSize,int page) {
    	// 复杂查询返回List集合
        
        String sql = "SELECT TOP "+pageSize+" * FROM (" +
        		"SELECT row_number ( ) OVER ( ORDER BY a.Num ASC ) AS rownumber," + 
        		"a.GUID," + 
        		"a.Num," + 
        		"a.Address," + 
        		"a.BuildArea," + 
        		"a.State," + 
        		"a.Structure," + 
        		"a.sMemo," + 
        		"a.RoomProperty," +  
        		"b.Charter," + 
        		"b.TotalHire," + 
        		"b.Phone " + 
        		"FROM  RoomInfo a " + 
        		"INNER JOIN ChartInfo b ON a.ChartGUID = b.GUID WHERE " + 
        		"a.Address LIKE '%"+Address+"%'" + 
        		" OR a.Num LIKE '%"+Num+"%'" +  
        		" OR a.RoomProperty LIKE '%"+RoomProperty+"%'" + 
        		" OR b.Charter LIKE '%"+Charter+"%'" + 
        		" OR b.Phone LIKE '%"+Phone+"%'" + 
        		" OR b.ContractNo LIKE '%"+ContractNo+"%'"+") temp_row WHERE rownumber > ("+page+"-1)*10";
        
        String sql2 ="SELECT " + 
        		"count(0) " + 
        		"FROM RoomInfo a " + 
        		"INNER JOIN ChartInfo b ON a.ChartGUID = b.GUID " + 
        		"WHERE " + 
        		"a.Address LIKE '%"+Address+"%'" + 
        		"OR a.Num LIKE '%"+Num+"%'" + 
        		"OR a.RoomProperty LIKE '%"+RoomProperty+"%'" + 
        		"OR b.Charter LIKE '%"+Charter+"%' " + 
        		"OR b.Phone LIKE '%"+Phone+"%'" + 
        		"OR b.ContractNo LIKE '%"+ContractNo+"%'";
        
        FuzzyQuery2 fq=new FuzzyQuery2();
        
        List list=this.getJdbcTemplate().query(sql,new fuzzyQueryRowMapper());
        
        int allCount=this.getJdbcTemplate().queryForInt(sql2);
        
        fq.setData(list);
        fq.setAllCount(allCount);
        
        return fq;
 
    }
	
	  class fuzzyQueryRowMapper implements RowMapper<Map> {
	  
	  @Override public Map mapRow(ResultSet rs, int rowNum) throws SQLException {
	  Map map = new HashMap<>();
	  map.put("GUID",rs.getString("GUID"));
	  map.put("Num",rs.getString("Num"));
	  map.put("Address",rs.getString("Address"));
	  map.put("BuildArea",rs.getString("BuildArea"));
	  map.put("State",rs.getString("State"));
	  map.put("Structure",rs.getString("Structure"));
	  map.put("sMemo",rs.getString("sMemo"));
	  map.put("RoomProperty",rs.getString("RoomProperty"));
	  map.put("Charter",rs.getString("Charter"));
	  map.put("TotalHire",rs.getString("TotalHire"));
	  map.put("Phone",rs.getString("Phone"));
	 
	  return map; 
	  }
	  
	}
    
}
