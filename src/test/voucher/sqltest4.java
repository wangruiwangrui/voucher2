package voucher;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.voucher.manage.daoModel.RoomChangeHireLog;
import com.voucher.manage.daoModel.RoomChartLog;
import com.voucher.manage.daoModel.RoomInfo;
import com.voucher.manage.daoModelJoin.RoomChangeHireLog_RoomChartLog;
import com.voucher.manage.daoSQL.DeleteExe;
import com.voucher.manage.daoSQL.InsertExe;
import com.voucher.manage.daoSQL.InsertSQL;
import com.voucher.manage.daoSQL.SelectExe;
import com.voucher.manage.daoSQL.SelectJoinExe;
import com.voucher.manage.daoSQL.UpdateExe;
import com.voucher.manage.tools.MyTestUtil;


public class sqltest4 {
	public static void main(String[] args) {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.springframework.jdbc.core.JdbcTemplate");
		dataSource.setUrl("jdbc:jtds:sqlserver://127.0.0.1:1433/TTT");
		dataSource.setUsername("sa");
		dataSource.setPassword("123");
		
		JdbcTemplate getJdbcTemplate= new JdbcTemplate();
		
		getJdbcTemplate.setDataSource(dataSource);
		
		RoomInfo roomInfo=new RoomInfo();
		/*
		roomInfo.setLimit(10);
		roomInfo.setOffset(0);
		roomInfo.setNotIn("[GUID]");
		
		roomInfo.setGUID("bbbb");
		roomInfo.setAddress("aaaaaa");
        roomInfo.setBuildArea((float) 123.8);
       roomInfo.setInDate(new Date());
       
		*/
		// String[] where={"Address like","%江阳区%"};
	     //  roomInfo.setWhere(where);
	   
	   //    int a=DeleteExe.get(getJdbcTemplate, roomInfo);
      //  UpdateExe.get(getJdbcTemplate, roomInfo);
	  // int a=InsertExe.get(getJdbcTemplate, roomInfo);
	       
	    //   System.out.println("a="+a);
	    /*   
		List list=SelectExe.get(getJdbcTemplate, roomInfo);
		Map map=SelectExe.getCount(getJdbcTemplate, roomInfo);
		MyTestUtil.print(map);
		*/
	//	roomInfo=new RoomInfo();
		/*RoomChangeHireLog roomChangeHireLog=new RoomChangeHireLog();
		 String[] where={ROOMDATABASE+".[dbo].[RoomInfo].Address like ","%江阳区%"};
	     roomInfo.setWhere(where);
		RoomChartLog roomChartLog=new RoomChartLog();
		roomChartLog.setLimit(10);
		roomChartLog.setOffset(0);
		roomChartLog.setNotIn("[GUID]");
		RoomChangeHireLog_RoomChartLog roomChangeHireLog_RoomChartLog=new RoomChangeHireLog_RoomChartLog();
	
		Object[] objects={roomChangeHireLog,roomChartLog};
		
		List list2=SelectJoinExe.get(getJdbcTemplate, objects, roomChangeHireLog_RoomChartLog, "[GUID]");
		MyTestUtil.print(list2);
		
		Map map=SelectJoinExe.getCount(getJdbcTemplate, objects, "[Charter]");
		MyTestUtil.print(map);
		*/
		
		String sql="SELECT top 10 id,HiddenLevel,ChangeSpeed,HiddenInstance,"+
    "doubletest,floattest,longtest,time"+
    " FROM  [Hidden]";
		
	}
}
