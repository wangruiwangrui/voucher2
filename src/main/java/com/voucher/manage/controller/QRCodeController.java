package com.voucher.manage.controller;

import java.io.File;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import com.voucher.manage.tools.MatrixToImageWriter;

@Controller
@RequestMapping("/QRCode")
public class QRCodeController {
	
	private Integer campusId=null;
	private String campusAdmin;
    private String directory;
    private int width = 430; // 二维码图片宽度  
    private int height = 430; // 二维码图片高度  
    private String format = "jpg";// 二维码的图片格式     
    
    // 清除掉所有特殊字符正则
    private final static String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";  
	
	@RequestMapping("getUrl")
    public @ResponseBody String 
    getUrl(HttpServletRequest request,@RequestParam String text) throws Exception {       
        
        Cookie[] cookies = request.getCookies();  
		if(cookies!=null){
			for(Cookie i:cookies){
				if(i.getName().equalsIgnoreCase("campusId"))
					campusId=Integer.parseInt(i.getValue());
			}
		}
        
		HttpSession session=request.getSession();  //取得session的type变量
		campusAdmin=session.getAttribute("campusAdmin").toString();
		
		
	    Pattern p=Pattern.compile(regEx);    
	    Matcher m=p.matcher(request.getHeader("Host")); 
	    
	    String addr=m.replaceAll("").trim();
	    
	    //文件夹列表路径
		directory=request.getSession().getServletContext().getRealPath("/mobile/"+addr);
		
		System.out.println("dirads="+directory);
		
		File folder=new File(directory);
		
		//如果文件夹不存在则创建
		if(!folder.exists()&&!folder.isDirectory()){
			folder.mkdir();
			System.out.println("folder not exists");
		}
		
		//服务器端的相对地址
       /* text="http://"+request.getHeader("Host")+request.getContextPath()+"/mobileConsume/beg.do?"
        		 + "campusAdmin="+campusAdmin+"&campusId="+campusId;
        		 */
		System.out.println(text);		
		String tag="mobile/"+addr+"/"+campusId+campusAdmin+".jpg";
		
		//生成文件的地址
		String QRCodeAddr=directory+File.separator+campusId+campusAdmin+".jpg";
		
        File outputFile = new File(QRCodeAddr);  
        System.out.println("outputfile="+outputFile);
        

          Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();  
          hints.put(EncodeHintType.CHARACTER_SET, "utf-8");   // 内容所使用字符集编码  
          // 生成二维码  
          BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);        
          MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
   
        
        return tag;
    }  
	
}

