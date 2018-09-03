
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.voucher.manage.service.UserService;

public class MysqlBatisTest {

	
	public static void main(String[] args) {
		
		ClassPathXmlApplicationContext applicationContext=new ClassPathXmlApplicationContext("spring-mybatis.xml");
		
		UserService userService=(UserService) applicationContext.getBean("userService");
		
		
		userService.getUserByOnlyOpenId("aaa");
	}
	
}
