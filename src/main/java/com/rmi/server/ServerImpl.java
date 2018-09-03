package com.rmi.server;

import com.rmi.server.entity.Person;

//继承该类， UnicastRemoteObject，暴露远程服务  
public class ServerImpl implements Server {  


  @Override  
  public String helloWorld(String name) {  
      // TODO Auto-generated method stub  
      return name + ",aaaaaaaaaaaaaaaa";  
  }  

  @Override  
  public Person getPerson(String name, int age){  
      // TODO Auto-generated method stub  
      return new Person(name, age);  
  }  

}  