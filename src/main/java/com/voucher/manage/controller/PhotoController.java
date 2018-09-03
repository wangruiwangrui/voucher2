package com.voucher.manage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.voucher.manage.service.PhotoService;

@Controller
@RequestMapping("/photo")
public class PhotoController {

	private PhotoService photoService;
	
	@Autowired
	public void setPhotoService(PhotoService photoService) {
		this.photoService = photoService;
	}
	
	@RequestMapping("/getAll")
	public @ResponseBody Map<String, Object> getAllPhotos(){
		Map<String, Object> map =new HashMap<>();
		List list=photoService.getAllPhoto();
		map.put("rows",list);
		return map;
	}
	
}
