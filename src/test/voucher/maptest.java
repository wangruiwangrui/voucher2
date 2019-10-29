package voucher;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.support.PropertiesLoaderUtils;



public class maptest {


    public static void main(String[] args) throws IOException {
    	Properties properties2 = PropertiesLoaderUtils.loadAllProperties("test.properties");
    	String test = properties2.getProperty("Version");
    	System.out.println("properties.test:"+test);

    }
	
}
