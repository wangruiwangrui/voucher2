package QRCode;

import java.io.File;
import java.util.Hashtable;

import org.junit.jupiter.api.Test;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;  
  
public class Main {  
	
	@Test
    public void aaa() throws Exception {  
        String text = "NiuYueYue I Love You��"; // ��ά������  
        int width = 430; // ��ά��ͼƬ���  
        int height = 430; // ��ά��ͼƬ�߶�  
        String format = "jpg";// ��ά���ͼƬ��ʽ          
        
        File outputFile = new File("d:" + File.separator + "new.jpg");  
        System.out.println(outputFile);
        
        if(!outputFile.exists()){
          Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();  
          hints.put(EncodeHintType.CHARACTER_SET, "utf-8");   // ������ʹ���ַ�������  
          // ���ɶ�ά��  
          BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);        
          MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
        }else{
        	System.out.println("outputFile is exists");
        }
    }  
}  