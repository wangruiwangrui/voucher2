package com.voucher.manage.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.voucher.manage.mapper.NoticeMapper;
import com.voucher.manage.model.Notice;
import com.voucher.manage.service.NoticeService;

@Service
public class NoticeServiceImpl implements NoticeService {

	private NoticeMapper noticeMapper;
	
	@Autowired
	public void setNoticeMapper(NoticeMapper noticeMapper) {
		this.noticeMapper = noticeMapper;
	}

	@Override
	public Notice getTemplateIdByTitle(Notice notice) {

		Notice notice1 = noticeMapper.selectTemplate(notice);
		
		return notice1;
	}

}
