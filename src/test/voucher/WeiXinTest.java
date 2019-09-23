package voucher;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.voucher.manage.mapper.WeiXinMapper;
import com.voucher.manage.model.WeiXin;
import com.voucher.manage.singleton.Singleton;
import com.voucher.weixin.base.CommonUtil;
import com.voucher.weixin.util.OrderNum;

public class WeiXinTest {

	public static final String URL = "http://test.lzgtzh.com/voucher";
	private static final String requestUrl = URL+"/mobile/WechatSendMessage/send.do";
	
	public static void main(String[] args) {
		ClassPathXmlApplicationContext applicationContext=new ClassPathXmlApplicationContext("spring-mybatis2.xml");		
		DefaultSqlSessionFactory defaultSqlSessionFactory= (DefaultSqlSessionFactory) applicationContext.getBean("sqlSessionFactory");				
		SqlSession sqlSession=defaultSqlSessionFactory.openSession();
		WeiXinMapper weiXinMapper=sqlSession.getMapper(WeiXinMapper.class);
		
		WeiXin weixin=weiXinMapper.getWeiXinByCampusId(1);
		
		System.out.println(weixin.getUrl());
		
		Date date = new Date();
		long date2 = date.getTime()+7000*1000;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datt = formatter.format(date);
		System.out.println(datt);
		
		String sd = "";
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sd = sdf.format(new Date(date2)); // 时间戳转换日期
		System.out.println("sd="+sd);
		
		
		System.out.println(OrderNum.getOrderNum());
		System.out.println(requestUrl);
		
		
	}

}
