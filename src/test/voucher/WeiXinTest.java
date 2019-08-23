package voucher;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.voucher.manage.mapper.WeiXinMapper;
import com.voucher.manage.model.WeiXin;

public class WeiXinTest {

	
	public static void main(String[] args) {
		ClassPathXmlApplicationContext applicationContext=new ClassPathXmlApplicationContext("spring-mybatis2.xml");		
		DefaultSqlSessionFactory defaultSqlSessionFactory= (DefaultSqlSessionFactory) applicationContext.getBean("sqlSessionFactory");				
		SqlSession sqlSession=defaultSqlSessionFactory.openSession();
		WeiXinMapper weiXinMapper=sqlSession.getMapper(WeiXinMapper.class);
		
		WeiXin weixin=weiXinMapper.getWeiXinByCampusId(1);
		
		System.out.println(weixin.getUrl());

	}

}
