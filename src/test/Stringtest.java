import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.voucher.manage.singleton.Singleton;
import com.voucher.weixin.util.OrderNum;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/*.xml"})
public class Stringtest {
	
	public static void main(String[] args){
		Map map = new HashMap();
		map.put("a", 1);
		map.put("b", 1);
		
		System.out.println(map.get("a"));
	}
	
}
