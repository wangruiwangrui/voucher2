
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
public class JSONtoObj {

 
  @Test
  public void bbb() {
	
	 String text = "{'name':'?????', 'age':66}" ; 
	  
     
     /** ??JSON?????????JSON???? **/  
     JSONObject json = JSON.parseObject(text) ; 
     System.out.println("aaa"+json);


     String str = "{\"a\":\"b\", \"c\":\"d\"}";  
     JSONObject a = new JSONObject();
     a=JSONObject.parseObject(text);
     System.out.println(a); // {"c":"d","a":"b"}  
     System.out.println(a.get("name")); // d  

}

}

class Userd {  
    String name="g"; 
    int age  ;

    public String getname() {
		return name;
	}
    
}  