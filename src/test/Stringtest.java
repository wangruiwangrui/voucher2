import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Stringtest {

	public static void main(String[] args){
		/*String str = "我的名字（测试）";
		
		final String REGEX1 = "我的名字2";
		
		Pattern pattern=Pattern.compile(REGEX1);
		
		Matcher matcher=pattern.matcher(str);
		
		System.out.println(matcher.find());*/
		Calendar cal = Calendar.getInstance();  
		int m=cal.get(Calendar.MONTH);
		int i=1;
		int s=6;
		int e=3;
		int r=s-e;
		while(r>0&&r>e){
			r=r-e;
			i++;
		}
		System.out.println(e*i+"  "+e*(i+1));
	}
	
}
