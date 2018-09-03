package BaiduMap;

import com.alibaba.fastjson.JSONObject;

public class test {

	
	
	
	public static void main(String[] args) {
		SnTest snTest=new SnTest();
		try {
			JSONObject jsonObject=snTest.testGet();
			System.out.println("jsonobject="+jsonObject.getString("result"));
			JSONObject jsonObject2=JSONObject.parseObject(jsonObject.getString("result"));
			System.out.println("jsonobject2="+jsonObject2.getString("location"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
