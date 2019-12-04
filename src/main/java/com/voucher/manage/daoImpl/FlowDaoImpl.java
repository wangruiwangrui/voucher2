package com.voucher.manage.daoImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSONObject;
import com.rmi.server.Server;
import com.voucher.manage.dao.FlowDao;
import com.voucher.manage.daoModel.RoomInfo;
import com.voucher.manage.daoModel.Assets.User_AccessTime;
import com.voucher.manage.daoModel.TTT.DataDictionary;
import com.voucher.manage.daoSQL.InsertExe;
import com.voucher.manage.daoSQL.SelectExe;
import com.voucher.manage.daoSQL.UpdateExe;

public class FlowDaoImpl extends JdbcDaoSupport implements FlowDao{

	@Override
	public Map addProcessInstance(Server server,String processDefinitionKey, String userId, String variableData, String className,List imageDataList) {
		// TODO Auto-generated method stub
		
		String guid="";
		
		try {

			org.json.JSONObject jsonObject= new org.json.JSONObject(variableData);
			
			guid=jsonObject.getString("guid");

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		RoomInfo roomInfo=new RoomInfo();
		
		roomInfo.setNeaten_flow(2);
		
		String[] where={"[GUID]=",guid};
		
		roomInfo.setWhere(where);
		
		int i=0;
		
		if(guid!=null&&!guid.equals("")){
			
			i=UpdateExe.get(this.getJdbcTemplate(), roomInfo);	
		}

		Map map = new HashMap<>();
		
		String imageDataString=null;
		
		if(imageDataList.size()>0) {
			JSONObject jObject =  new JSONObject();
			imageDataString =  jObject.toJSONString(imageDataList);
		}
		
		if(i>0){
			
			try{
				System.out.println("processDefinitionKey==========="+processDefinitionKey+"userId++++++++"+userId+"variableData==============="+variableData+"imageDataList================="+imageDataList+"className========="+className);
				map=server.startProcessInstance(processDefinitionKey, userId, variableData, imageDataString,className);
				
				if(map.get("state").equals("流程启动失败"))
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
								
				map.put("state", "流程启动失败");
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
			
		}else{
			
			map.put("state", "流程启动失败");
						
		}
		
		return map;
		
	}

	@Override
	public Integer upRoomNeatenFlowById(String guid) {
		// TODO Auto-generated method stub
				
		RoomInfo roomInfo=new RoomInfo();
		
		roomInfo.setNeaten_flow(0);
		
		String[] where={"[GUID]=",guid};
		
		roomInfo.setWhere(where);
		
		int i=0;
		
		i=UpdateExe.get(this.getJdbcTemplate(), roomInfo);	

		return i;
	}

	@Override
	public List<User_AccessTime> selectUserAccessTime(String openId) {
		// TODO Auto-generated method stub
		User_AccessTime user_AccessTime=new User_AccessTime();
		user_AccessTime.setLimit(1);
		user_AccessTime.setOffset(0);
		user_AccessTime.setNotIn("open_id");
		
		String[] where={"open_id=",openId};
		
		user_AccessTime.setWhere(where);
		
		List<User_AccessTime> list=SelectExe.get(this.getJdbcTemplate(), user_AccessTime);
		
		return list;
	}
	

	@Override
	public List getVersion(Integer limit, Integer offset, String sort, String order, String item) {
		
		DataDictionary dataDictionary = new DataDictionary();
		
		dataDictionary.setLimit(1);
		dataDictionary.setOffset(0);
		
		String[] where = {"item=", item};
		dataDictionary.setWhere(where);
		
		List<DataDictionary> list = SelectExe.get(getJdbcTemplate(), dataDictionary);
		return list;
	}
	
	
	@Override
	public Integer insertUserAccessTime(User_AccessTime user_AccessTime) {
		// TODO Auto-generated method stub
		return InsertExe.get(this.getJdbcTemplate(), user_AccessTime);
	}
	
	@Override
	public Integer upUserAccessTime(User_AccessTime user_AccessTime) {
		// TODO Auto-generated method stub
		return UpdateExe.get(this.getJdbcTemplate(), user_AccessTime);
	}

	
}
