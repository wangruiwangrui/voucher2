import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Stringtest {

	public static void main(String[] args){
		String str = "我的名字（测试）";
		
		final String REGEX1 = "我的名字2";
		
		Pattern pattern=Pattern.compile(REGEX1);
		
		Matcher matcher=pattern.matcher(str);
		
		System.out.println(matcher.find());
	}
	
}
