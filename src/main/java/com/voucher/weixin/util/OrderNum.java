package com.voucher.weixin.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 
 * @author wr
 *	方法类
 */
public class OrderNum {

	public static String getOrderNum() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		String newDate=sdf.format(new Date());
		String result="";
		Random random=new Random();
		for(int i=0;i<3;i++){
		result+=random.nextInt(10);
		}
		 return newDate+result;
	}
}
