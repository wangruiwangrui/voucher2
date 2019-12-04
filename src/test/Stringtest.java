import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/*.xml"})
public class Stringtest {
	
	public static void main(String[] args){
		int count = 0;
		for (int i = 0; i < 10; i++) {
			if (count ==4) {
				System.out.println("i="+i);
				continue;
			}
			count++;
		}
		System.out.println(count);
	}
	
}
