package com.ego.manage.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ego.manage.service.PicService;

@Controller
public class PicController {
	@Resource
	PicService picServiceImpl;    // @Resource自动注入bean，不需要new ServiceImpl
	
	@RequestMapping("pic/upload")
	@ResponseBody
	public Map<String, Object> upload(MultipartFile uploadFile) {
		Map<String, Object> map = new HashMap<>();
		try {
			map = picServiceImpl.upload(uploadFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
}
