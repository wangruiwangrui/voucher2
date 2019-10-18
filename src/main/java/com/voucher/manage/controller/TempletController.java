package com.voucher.manage.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.voucher.manage.model.WeiXin;
import com.voucher.manage.service.WeiXinService;
import com.voucher.manage.templet.HomePage;
import com.voucher.manage.tools.Constants;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Controller
@RequestMapping("templet")
public class TempletController{
	private static final Logger logger = LoggerFactory.getLogger("templet");
		
    private WeiXinService weixinService;
	
    
	@Autowired
	public void setweixinService(WeiXinService weiXinService) {
		this.weixinService=weiXinService;
	}

	/*
	 * �����û�����ҳ��
	 */
	@RequestMapping("/getHomePageTemplet")	
	public @ResponseBody Map<String, Object>
	getHomePageTemplet(HttpServletRequest request, HttpServletResponse response,
    		@RequestParam Integer campusId) throws IOException, ServletException{
		String redirectUrl="https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
		String directory;
		Map<String, Object> map = new HashMap<String, Object>();
	    String appId, appSecret;
	    String campusName;
	    WeiXin weiXin;
		
		//���ñ����ʽ��MIME����
        response.setContentType("text/html; charset=UTF-8");
        
      //�ļ����б�·��
      	directory=request.getSession().getServletContext().getRealPath("/mobile/home");
        
        //�������˵���Ե�ַ
        String homeUrl=request.getHeader("Host")+request.getContextPath()+
        		       "/mobile/home/"+campusId+".html";
        
        System.out.println("dirads="+directory);
		
		File folder=new File(directory);
		
		//����ļ��в������򴴽�
		if(!folder.exists()&&!folder.isDirectory()){
			folder.mkdir();
			System.out.println("folder not exists");
		}
		
        
        //��ҳ�����б�·��
        String indexPath=request.getSession().getServletContext().getRealPath("/mobile/home/"+campusId+".html");
        
        //�ļ��Ƿ����
        File file=new File(indexPath);
		
        String templatePath=request.getSession().getServletContext().getRealPath("/")
        		+"WEB-INF/templates";
        //����ģ��Ŀ¼
        
        //����һ��freemarker.template.Configurationʵ�������Ǵ洢 FreeMarker Ӧ�ü����õĺ��Ĳ���
        //ָ���汾��
        Configuration cfg=new Configuration(Configuration.VERSION_2_3_22);
        //���ģ���ļ�·��
        cfg.setEncoding(Locale.CHINA, "UTF-8");
      //����Ĭ�ϱ����ʽ
        
        cfg.setDirectoryForTemplateLoading(new File(templatePath));
        
        //����
        Map<String, Object> HomePageData = new HashMap<>();
        
        weiXin=weixinService.getCampusById(campusId);		        
        appId=weiXin.getAppId();
        campusName=weiXin.getCampusName();
        
        try{
            redirectUrl=redirectUrl.replace("APPID", appId);
           }catch (Exception e) {
				// TODO: handle exception
           	map.put(Constants.STATUS, Constants.FAILURE);
				map.put(Constants.MESSAGE, "����ʧ��,δ����APPID");
				
				return map;
			}
           redirectUrl=redirectUrl.replace("REDIRECT_URI", "http://"+homeUrl);
          
         HomePage homePage=new HomePage();
         
         homePage.setCampusId(String.valueOf(campusId));
         homePage.setRedirectUrl(redirectUrl);
         homePage.setCampusName(campusName);
         
         System.out.println("redireurl="+redirectUrl);
         
         HomePageData.put("homePages", homePage);


         //�����õ�Ŀ¼�л��ģ��
         Template template = cfg.getTemplate("homePageTemplet.ftl");
         
         //�ϲ�ģ�������ģ��
         try {
             //��������ģ����Ⱦ�Ľ��д���ļ���
             Writer writer=new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
             template.process(HomePageData, writer);
             writer.flush();
             
             HomePageData.clear();

             writer.close();
         } catch (TemplateException e) {
             e.printStackTrace();
         }

     
     Map<String, Object> paramMap=new HashMap<>();

     paramMap.put("campusId", campusId);
     paramMap.put("homePage", homeUrl);

     weixinService.updateHomePageByCampusId(paramMap);
     
     map.put(Constants.STATUS, Constants.SUCCESS);
	 map.put(Constants.MESSAGE, "���³ɹ�");
		
	 return map;
           
	}
	
}
