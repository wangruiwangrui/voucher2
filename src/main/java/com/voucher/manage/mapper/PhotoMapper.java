package com.voucher.manage.mapper;

import java.util.List;

import com.voucher.manage.model.Photo;

public interface PhotoMapper {

	int insertPhtoByOpenId(Photo photo);
	
	List<Photo> getAllPhoto();
}
