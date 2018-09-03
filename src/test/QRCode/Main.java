package QRCode;

import java.io.File;  
import java.util.Hashtable;

import org.junit.Test;

import com.google.zxing.BarcodeFormat;  
import com.google.zxing.EncodeHintType;  
import com.google.zxing.MultiFormatWriter;  
import com.google.zxing.common.BitMatrix;  
  
public class Main {  
	
	@Test
    public void aaa() throws Exception {  
        String text = "NiuYueYue I Love You！"; // 二维码内容  
        int width = 430; // 二维码图片宽度  
        int height = 430; // 二维码图片高度  
        String format = "jpg";// 二维码的图片格式          
        
        File outputFile = new File("d:" + File.separator + "new.jpg");  
        System.out.println(outputFile);
        
        if(!outputFile.exists()){
          Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();  
          hints.put(EncodeHintType.CHARACTER_SET, "utf-8");   // 内容所使用字符集编码  
          // 生成二维码  
          BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);        
          MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
        }else{
        	System.out.println("outputFile is exists");
        }
    }  
}  