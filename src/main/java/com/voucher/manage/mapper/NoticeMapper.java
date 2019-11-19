package com.voucher.manage.mapper;

import java.util.List;

import com.voucher.manage.model.Notice;

public interface NoticeMapper {
	
	List<Notice> getNoticeList(Integer campusId);
	
	Integer insertNotice(Notice notice);
	
	Integer updateNotice(Notice notice);

	Integer deleteNotice(Notice notice);
	
	Notice selectTemplate(Notice notice);

}
