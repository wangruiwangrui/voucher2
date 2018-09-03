package voucher;

import java.sql.SQLClientInfoException;
import java.sql.SQLSyntaxErrorException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.voucher.manage.daoModel.RoomChangeHireLog;
import com.voucher.manage.daoModel.RoomChartLog;
import com.voucher.manage.daoModel.RoomInfo;
import com.voucher.manage.daoSQL.DeleteSQL;
import com.voucher.manage.daoSQL.InsertSQL;
import com.voucher.manage.daoSQL.UpdateSQL;
import com.voucher.manage.tools.MyTestUtil;

public class sqltest2 {

	public static void main(String[] args) throws ClassNotFoundException {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.springframework.jdbc.core.JdbcTemplate");
		dataSource.setUrl("jdbc:jtds:sqlserver://127.0.0.1:1433/TTT");
		dataSource.setUsername("sa");
		dataSource.setPassword("123");
		
		JdbcTemplate getJdbcTemplate= new JdbcTemplate();
		
		getJdbcTemplate.setDataSource(dataSource);
		
		String a="ddddddddddddddd";
		String tableCreate;
		
      //  tableCreate = CreateSQL.get(User.class);
     //   System.out.println(tableCreate);                        //打印出来
        
		RoomInfo roomInfo=new RoomInfo();

		RoomChangeHireLog roomChangeHireLog=new RoomChangeHireLog();
	
		RoomChartLog roomChartLog=new RoomChartLog();

		roomInfo.setGUID("hhhhhhhhh");
		roomInfo.setAddress("aaaaaa");
        roomInfo.setBuildArea((float) 123.8);
       roomInfo.setInDate(new Date());
       String[] where={"Address=","aaaaaa"};
       roomInfo.setWhere(where);
		
	//	Map map=InsertSQL.get(roomInfo);
    //    Map map=UpdateSQL.get(roomInfo);
       Map map=DeleteSQL.get(roomInfo);
		MyTestUtil.print(map);
		List params=(List) map.get("pamars");
		String sql=(String) map.get("sql");
		int i=getJdbcTemplate.update(sql,params.toArray());
		System.out.println(i);
	}
}
