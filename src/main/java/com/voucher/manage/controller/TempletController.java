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
	 * 生成用户个人页面
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
		
		//设置编码格式与MIME类型
        response.setContentType("text/html; charset=UTF-8");
        
      //文件夹列表路径
      	directory=request.getSession().getServletContext().getRealPath("/mobile/home");
        
        //服务器端的相对地址
        String homeUrl=request.getHeader("Host")+request.getContextPath()+
        		       "/mobile/home/"+campusId+".html";
        
        System.out.println("dirads="+directory);
		
		File folder=new File(directory);
		
		//如果文件夹不存在则创建
		if(!folder.exists()&&!folder.isDirectory()){
			folder.mkdir();
			System.out.println("folder not exists");
		}
		
        
        //首页新闻列表路径
        String indexPath=request.getSession().getServletContext().getRealPath("/mobile/home/"+campusId+".html");
        
        //文件是否存在
        File file=new File(indexPath);
		
        String templatePath=request.getSession().getServletContext().getRealPath("/")
        		+"WEB-INF/templates";
        //设置模板目录
        
        //创建一个freemarker.template.Configuration实例，它是存储 FreeMarker 应用级设置的核心部分
        //指定版本号
        Configuration cfg=new Configuration(Configuration.VERSION_2_3_22);
        //获得模板文件路径
        cfg.setEncoding(Locale.CHINA, "UTF-8");
      //设置默认编码格式
        
        cfg.setDirectoryForTemplateLoading(new File(templatePath));
        
        //数据
        Map<String, Object> HomePageData = new HashMap<>();
        
        weiXin=weixinService.getCampusById(campusId);		        
        appId=weiXin.getAppId();
        campusName=weiXin.getCampusName();
        
        try{
            redirectUrl=redirectUrl.replace("APPID", appId);
           }catch (Exception e) {
				// TODO: handle exception
           	map.put(Constants.STATUS, Constants.FAILURE);
				map.put(Constants.MESSAGE, "更新失败,未设置APPID");
				
				return map;
			}
           redirectUrl=redirectUrl.replace("REDIRECT_URI", "http://"+homeUrl);
          
         HomePage homePage=new HomePage();
         
         homePage.setCampusId(String.valueOf(campusId));
         homePage.setRedirectUrl(redirectUrl);
         homePage.setCampusName(campusName);
         
         System.out.println("redireurl="+redirectUrl);
         
         HomePageData.put("homePages", homePage);


         //从设置的目录中获得模板
         Template template = cfg.getTemplate("homePageTemplet.ftl");
         
         //合并模板和数据模型
         try {
             //将数据与模板渲染的结果写入文件中
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
	 map.put(Constants.MESSAGE, "更新成功");
		
	 return map;
           
	}
	
}
