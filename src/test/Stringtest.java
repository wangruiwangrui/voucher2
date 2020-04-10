import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:/*.xml"})
public class Stringtest {
	
	public static void main(String[] args){
		String string1 = "20200302165732961803";
		String g = string1.substring(0, 17);
		System.out.println(g);
		String string="abcd123456中文_$,@";
		string.replaceAll("[^\\u4e00-\\u9fa5]", "");
		//System.out.println(string.replaceAll("[^\\u4e00-\\u9fa5]", ""));
		
		List<String> list = new ArrayList<String>();
		
		String s = "2294290";
        String str="13183606610/2294290";
        String strss="131836066102294290";
        list.add(str);
        list.add(strss);
        Boolean flag = false;
        for (String ss : list) {
			if (ss.contains("/")) {
				String[] strs = str.split("/");
	        	for (String string2 : strs) {
	    			if (s.equals(string2)) {
	    				flag=true;
						break;
					}
	    		}
			}else {
				if (ss.equals(s)) {
					flag=true;
					break;
				}
			}
		}
       
        System.out.println("111111111111111"+flag);
	}
	
}
