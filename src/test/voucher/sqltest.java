package voucher;

import java.sql.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.voucher.manage.daoModel.RoomChangeHireLog;
import com.voucher.manage.daoModel.RoomChartLog;
import com.voucher.manage.daoModel.RoomInfo;
import com.voucher.manage.daoModel.RoomInfoRowMapper;
import com.voucher.manage.daoRowMapper.RowMappers;
import com.voucher.manage.daoRowMapper.RowMappersJoin;
import com.voucher.manage.daoSQL.SelectSQL;
import com.voucher.manage.daoSQL.SelectSQLJoin;
import com.voucher.manage.daoSQL.annotations.DBTable;
import com.voucher.manage.daoSQL.annotations.QualifiLimit;
import com.voucher.manage.daoSQL.annotations.QualifiNotIn;
import com.voucher.manage.daoSQL.annotations.SQLDateTime;
import com.voucher.manage.daoSQL.annotations.SQLFloat;
import com.voucher.manage.daoSQL.annotations.SQLInteger;
import com.voucher.manage.daoSQL.annotations.SQLString;
import com.voucher.manage.tools.MyTestUtil;
import com.voucher.manage.daoSQL.annotations.QualifiWhere;
import com.voucher.manage.daoModelJoin.*;


public class sqltest {

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
		roomInfo.setLimit(30);
		roomInfo.setOffset(0);
		roomInfo.setNotIn("[GUID]");
	//	roomInfo.setSort("BuildArea");
	//	roomInfo.setOrder("desc");
	//	String[] where={ROOMDATABASE+".[dbo].[RoomInfo].Region=","江阳区"};
	//	roomInfo.setWhere(where);
 
		RoomChangeHireLog roomChangeHireLog=new RoomChangeHireLog();
		
		roomChangeHireLog.setLimit(10);
		roomChangeHireLog.setOffset(0);
		roomChangeHireLog.setNotIn("[GUID]");
		roomChangeHireLog.setSort("Area");
	//	roomChangeHireLog.setOrder("desc");
		
	//	String[] where3={"[RoomManage].[dbo].[RoomChangeHireLog].Charter=","林忠英"};
		String[] where3={"[RoomManage].[dbo].[RoomChangeHireLog].Region=","江阳区"};
		String[] where={"[RoomManage].[dbo].[RoomChangeHireLog].Region=","江阳区","[RoomManage].[dbo].[RoomChangeHireLog].Region=","江阳区"};
		roomChangeHireLog.setWhere(where3);
		
		RoomChartLog roomChartLog=new RoomChartLog();
		roomChartLog.setLimit(10);
		roomChartLog.setOffset(10);
		roomChartLog.setNotIn("[Charter]");
	/*	
		String[] where2={"[RoomManage].[dbo].[RoomChartLog].Charter=","廖常青"};
	//	roomChartLog.setWhere(where2);
		roomChartLog.setWhereTerm("OR");
		Object[] objects={roomChangeHireLog,roomChartLog,roomInfo};

	//	Map map=SelectSQLJoin.get(objects, "[GUID]");
	//	MyTestUtil.print(map);
	//    Map map2=new SelectSQLJoin().get(objects,"[GUID]");
		 Map map2=new SelectSQLJoin().getCount(objects,"[GUID]");
		 MyTestUtil.print(map2);
		//String sql=new SelectSQL().get(roomInfo);
		
	//	System.out.println(sql);
     
		
		
		
		Class<?>[] classeNames={RoomChangeHireLog.class,RoomInfo.class, RoomChartLog.class};
		String sql=(String) map2.get("sql");
		
		List params=(List) map2.get("params");
		
		Map map3=getJdbcTemplate.queryForMap(sql,params.toArray());
	       MyTestUtil.print(map3);
		
    //	List list=getJdbcTemplate.query(sql,new RowMappers(RoomChangeHireLog.class));

	//	List params=(List) map.get("params");
		
    //	List list=getJdbcTemplate.query(sql,params.toArray(), new RowMappersJoin(classeNames,RoomChangeHireLog_RoomChartLog.class));
		
    //	MyTestUtil.print(list);
		
		
		Iterator iterator=list.iterator();
		
		int j=0;
		while (iterator.hasNext()) {
   
				   MyTestUtil.print(iterator.next());

		   System.out.println("j="+j);
		   j++;
		}
		*/
	//	String sqlcount=SelectSQLJoin.getCount(objects, "[Charter]");
	//	System.out.println(sqlcount);
		Object[] objects={roomChangeHireLog,roomChartLog};
		Class<?>[] classs={roomChangeHireLog.getClass(),roomChangeHireLog.getClass()};
		String[] join={"[Charter]"};
		Map map2=new SelectSQLJoin().get(objects,join);
	//	Map map2=new SelectSQLJoin().getCount(objects,"[Charter]");
	//	Map map2=new SelectSQL().get(roomInfo);
        MyTestUtil.print(map2);
	//	String[] where={ROOMDATABASE+".[dbo].[RoomInfo].Region=","江阳区",ROOMDATABASE+".[dbo].[RoomInfo].Region=","江阳区"};
	//	String[] where={"Address like ","%新二村%"};
	//	roomInfo.setWhere(where);
	//	roomInfo.setWhereTerm("OR");
	//	Map map=SelectSQL.get(roomInfo);
		
	//	Map map=SelectSQLJoin.getCount(objects,"[GUID]");
	/*	Map map=SelectSQL.getCount(roomInfo);
		
		MyTestUtil.print(map);
	//	sql=(String) map.get("sql");
		List params=(List) map2.get("params");
		String sql=(String) map2.get("sql");
       System.out.println(sql);
       MyTestUtil.print(params);
       System.out.println("order="+roomChangeHireLog.getOrder());
    //   Object[] ss=new Object[]{1,"江阳区"};
    //	List list2=getJdbcTemplate.query(sql,params.toArray(),new RowMappersJoin(classs, RoomChangeHireLog_RoomChartLog.class));
    //  List list2=getJdbcTemplate.query(sql,params.toArray(),new RowMappers(RoomInfo.class));
	  Map map4=getJdbcTemplate.queryForMap(sql,params.toArray());
    //   MyTestUtil.print(list2);
	  MyTestUtil.print(map4);*/
	}
}

