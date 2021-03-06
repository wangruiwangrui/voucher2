package voucher;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.voucher.manage.mapper.NoticeMapper;
import com.voucher.manage.mapper.WeiXinMapper;
import com.voucher.manage.model.Notice;
import com.voucher.manage.model.WeiXin;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	private static final String requestUrl = "http://127.0.0.1/voucher/mobile/WechatSendMessage/send.do";
	
	public static void main(String[] args) {


		/*
		 * ClassPathXmlApplicationContext applicationContext=new
		 * ClassPathXmlApplicationContext("spring-mybatis2.xml");
		 * DefaultSqlSessionFactory defaultSqlSessionFactory= (DefaultSqlSessionFactory)
		 * applicationContext.getBean("sqlSessionFactory"); SqlSession
		 * sqlSession=defaultSqlSessionFactory.openSession(); WeiXinMapper
		 * weiXinMapper=sqlSession.getMapper(WeiXinMapper.class);
		 * 
		 * WeiXin weixin=weiXinMapper.getWeiXinByCampusId(1);
		 * 
		 * NoticeMapper noticeMapper = sqlSession.getMapper(NoticeMapper.class);
		 * 
		 * Notice notice = new Notice(); notice.setCampusId(1); notice.setTitle("隐患通知");
		 * notice = noticeMapper.selectTemplate(notice);
		 */
		
		
		//System.out.println(weixin.getUrl());

		//WeiXin weixin=weiXinMapper.getWeiXinByCampusId(1);
		/*
		System.out.println(weixin.getUrl());
		
		Date date = new Date();
		long date2 = date.getTime()+7000*1000;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datt = formatter.format(date);
		System.out.println(datt);
		
		String sd = "";
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sd = sdf.format(new Date(date2)); // 鏃堕棿鎴宠浆鎹㈡棩鏈�
		System.out.println("sd="+sd);
		
		
		System.out.println(OrderNum.getOrderNum());
		System.out.println(requestUrl);
		*/

		LinkedHashMap<String, Map<String, Object>> registerMap = Singleton.getInstance().getRegisterMap();
	}

}
