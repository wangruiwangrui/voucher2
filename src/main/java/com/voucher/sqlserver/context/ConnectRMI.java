package com.voucher.sqlserver.context;

import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

import com.rmi.server.Server;


public class ConnectRMI {
	
	private Future<Server> future;
	
	public ConnectRMI() {
		// TODO Auto-generated constructor stub
		ExecutorService pool=Executors.newCachedThreadPool();
		GetConnect getConnect=new GetConnect();
		future=pool.submit(getConnect);
	}
	
	//创建数据库连接池
	class GetConnect implements Callable<Server>{
		@Override
		public Server call() throws Exception {
			// TODO Auto-generated method stub

			/*
			ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
					"applicationContext-beans.xml");
			 */
			Server server = null;
			
			try {
			//	server = (Server) applicationContext.getBean("flowSpringRMI");
				
				RmiProxyFactoryBean factory = new RmiProxyFactoryBean();

				factory.setServiceInterface(Server.class);

				factory.setServiceUrl("rmi://127.0.0.1:1199/flowSpringRMI");

				factory.setLookupStubOnStartup(false);

				factory.setRefreshStubOnConnectFailure(true);

				factory.afterPropertiesSet();

				server = (Server) factory.getObject();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				
				RmiProxyFactoryBean factory = new RmiProxyFactoryBean();

				factory.setServiceInterface(Server.class);

				factory.setServiceUrl("rmi://127.0.0.1:1199/flowSpringRMI");

				factory.setLookupStubOnStartup(false);

				factory.setRefreshStubOnConnectFailure(true);

				factory.afterPropertiesSet();

				server = (Server) factory.getObject();

			}
			
			return server;
		}
		
	}
	
	public Server getContext(){
		Server server=null;
		try {
			server = future.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("serverclass="+server.getClass());
		
		return server;
	}
	
	public Server get(){

		return this.getContext();
		
	}
	
	
}
