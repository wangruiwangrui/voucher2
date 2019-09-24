import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class testphone {
	 public static boolean isPhone(String str) { 
	        Pattern p1 = null,p2 = null;
	        Matcher m = null;
	        boolean b = false;  
	        p1 = Pattern.compile("^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$");

	         m = p1.matcher(str);
	         b = m.matches();  
	        return b;
	     }
	 
	 public static void main(String[] args) {
 
		 String fileName = "C:\\Users\\admin\\Desktop\\newtest.txt";
		 
		 System.out.println(readFileContent(fileName));
	 }
	 
	 public static String readFileContent(String fileName) {
		    File file = new File(fileName);
		    BufferedReader reader = null;
		    StringBuffer sbf = new StringBuffer();
		    try {
		        reader = new BufferedReader(new FileReader(file));
		        String tempStr;
		        while ((tempStr = reader.readLine()) != null) {
		            sbf.append(tempStr);
		        }
		        reader.close();
		        return sbf.toString();
		    } catch (IOException e) {
		        e.printStackTrace();
		    } finally {
		        if (reader != null) {
		            try {
		                reader.close();
		            } catch (IOException e1) {
		                e1.printStackTrace();
		            }
		        }
		    }
		    return sbf.toString();
		}

}
