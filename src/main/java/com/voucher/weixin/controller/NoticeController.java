package com.voucher.weixin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.voucher.manage.mapper.NoticeMapper;
import com.voucher.manage.model.Notice;
import com.voucher.manage.model.WeiXin;
import com.voucher.manage.service.WeiXinService;

@Controller
@RequestMapping("/noticeset")
public class NoticeController {

	@Value("${DOMAIN}")
	public String DOMAIN;
	
	private NoticeMapper noticeMapper;

	private WeiXinService weixinService;
	
	@Autowired
	public void setNoticeMapper(NoticeMapper noticeMapper) {
		this.noticeMapper = noticeMapper;
	}
	
	@Autowired
	public void setweixinService(WeiXinService weiXinService) {
		this.weixinService=weiXinService;
	}

	// 查询消息列表
	@RequestMapping("/getNoticeList")
	public @ResponseBody List<Notice> getNoticeList(HttpServletRequest request) {

		Integer campusId = Integer.parseInt(request.getParameter("campusId"));
		
		List<Notice> notices = new ArrayList<Notice>();

		HttpSession session = request.getSession(); // 取得session的type变量，判断是否为公众号管理员
		// 服务器端的相对地址
		String homeUrl = request.getHeader("Host") + request.getContextPath();

		notices = noticeMapper.getNoticeList(campusId);

		return notices;
	}

	// 修改消息
	@RequestMapping("/updateNotice")
	public @ResponseBody Integer updateNotice(HttpServletRequest request, @RequestParam Integer id,
			@RequestParam Integer campusId,@RequestParam String title, @RequestParam String templateId) {

		Notice notice = new Notice();

		
		notice.setId(id);
		notice.setCampusId(campusId);
		notice.setTitle(title);
		notice.setTemplateId(templateId);

		Integer result = noticeMapper.updateNotice(notice);

		return result;

	}

	@RequestMapping("/insertNotice")
	public @ResponseBody Integer insertNotice(HttpServletRequest request,@RequestParam Integer campusId,@RequestParam String title,
			@RequestParam String templateId) {

		Notice notice = new Notice();
		
		notice.setCampusId(campusId);
		notice.setTitle(title);
		notice.setTemplateId(templateId);
		 
		Integer result = noticeMapper.insertNotice(notice);
		
		return result;

	}
	
	@RequestMapping("/deleteNotice")
	public @ResponseBody Integer deleteNotice(HttpServletRequest request,@RequestParam Integer id,@RequestParam Integer campusId) {

		Notice notice = new Notice();
		notice.setId(id);
		notice.setCampusId(campusId);
		Integer result = noticeMapper.deleteNotice(notice);
		return result;

	}

	/**
	 * 通过campusId获取weixin类
	 * @param request
	 * @param campusId
	 * @return
	 */
	@RequestMapping("/mobile/getWeiXin")
	public @ResponseBody Map getWeiXin(HttpServletRequest request,@RequestParam Integer campusId){
		Map map = new HashMap();
		WeiXin weiXin=weixinService.getCampusById(campusId);
		String appId = weiXin.getAppId();
		map.put("appId", appId);
		map.put("DOMAIN", DOMAIN);
        return map;
	}
}
