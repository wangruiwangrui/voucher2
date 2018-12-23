package com.voucher.manage.dao;

import java.util.Map;

import com.rmi.server.Server;

public interface FlowDao {

	public Map addProcessInstance(Server server,String processDefinitionKey,String userId,String variableData,String className)throws Exception;
	
	public Integer upRoomNeatenFlowById( String neatenId);
}
