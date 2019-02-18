package com.voucher.manage.dao;

import java.util.List;
import java.util.Map;

import com.rmi.server.Server;
import com.voucher.manage.daoModel.Assets.User_AccessTime;

public interface FlowDao {

	public Map addProcessInstance(Server server,String processDefinitionKey,String userId,String variableData,String className,List imageDataList)throws Exception;
	
	public Integer upRoomNeatenFlowById( String neatenId);
	
	public List<User_AccessTime> selectUserAccessTime(String openId);
	
	public Integer insertUserAccessTime(User_AccessTime user_AccessTime);
	
	public Integer upUserAccessTime(User_AccessTime user_AccessTime);
}
