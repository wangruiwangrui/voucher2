package voucher;

import java.util.HashMap;
import java.util.Map;

import com.voucher.manage.tools.TransMapToString;

public class maptest {

	public static void  main(String[] args) {
		Map<String, String> map=new HashMap<>();
		map.put("aaa ", "b");

		String[] a=TransMapToString.get(map);
		for(String b :a){
			System.out.println(b);
		}
	}
	
}
