package com.voucher.manage.tools;


import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.voucher.manage.singleton.Singleton;

 
/**
 * 
 * @Description: 带有效期map 简单实现 实现了基本的方法
 * @author: qd-ankang
 * @date: 2016-11-24 下午4:08:46
 * @param <K>
 * @param <V>
 */
public class ExpiryMap{
 
 
    /**
     * 
    	 * @Description: 是否过期 
         * @author: qd-ankang
         * @date: 2016-11-24 下午4:05:02
         * @param expiryTime true 过期
         * @param isRemoveSuper true super删除
         * @return
     */

    public static String createRandomVcode(){
        //验证码
        String vcode = "";
        for (int i = 0; i < 6; i++) {
            vcode = vcode + (int)(Math.random() * 9);
        }
        return vcode;
    }
    
    public static void main(String[] args) throws InterruptedException {

    	
       LinkedHashMap<String, Map<String, Object>> map=Singleton.getInstance().getRegisterMap();
    	
    /*
       for(int i=0;i<10;i++){
    	   Date date= new Date();
    	   System.out.println(i+"   :      "+date.getTime());
    	   Map<String, Object> map2=new HashMap<String, Object>();
        map2.put("startTime",date);       
    	map.put(createRandomVcode(), map2);
    	
    	//Thread.sleep(1000);
       }
*/
	/*	LinkedHashMap<String, Map<String, Object>> linkMap=Singleton.getInstance().getRegisterMap();
		Map<String, Object> mm=new HashMap<>();
		mm.put("vcode", 435435);
		mm.put("startTime", new Date());
		linkMap.put("ghghg", mm);
       */
    	System.out.println("1:");    	

    	System.out.println("size:" + map.size());
    	

    	for (Map.Entry<String, Map<String, Object>> m : map.entrySet()) {
           
    		map.containsKey(m.getKey());
			System.out.println("key:" + m.getKey());
			for (Map.Entry<String, Object> m2 : m.getValue().entrySet()){
				System.out.println("     currentSize : "+map.size()+"     key:  " + m2.getKey()+
					"     value:  " + m2.getValue());
			}
			
			
		}
    	


	}
}

