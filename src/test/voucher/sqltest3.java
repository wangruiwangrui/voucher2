package voucher;

import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.voucher.manage.dao.RoomInfoDao;
import com.voucher.manage.daoModel.RoomInfo;
import com.voucher.manage.tools.MyTestUtil;
import com.voucher.sqlserver.context.Connect;

public class sqltest3 {
	
	
	public static void main(String[] args) {
		ApplicationContext applicationContext=new Connect().get();
		
		RoomInfoDao roomInfoDao=(RoomInfoDao) applicationContext.getBean("roomInfodao");
		
		int limit=100;
		int offset=0;
		
		String sort=null;
		String order=null;
		String search=null;
		
		Map map=roomInfoDao.findAllFloor(limit, offset, sort, order, search);
		//List<RoomInfo> map=roomInfoDao.findAllRoomInfo(limit, offset, sort, order, search);
		MyTestUtil.print(map);

	}
}
