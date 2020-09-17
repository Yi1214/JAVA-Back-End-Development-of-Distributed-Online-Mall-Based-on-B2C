package com.ego.manage.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ego.commons.utils.FtpUtil;
import com.ego.commons.utils.IDUtils;
import com.ego.manage.service.PicService;

@Service
public class PicServiceImpl implements PicService{
	@Value("${ftpclient.host}")
	private String host;
	@Value("${ftpclient.port}")
	private int port;
	@Value("${ftpclient.usrname}")
	private String usrname;
	@Value("${ftpclient.password}")
	private String password;
	@Value("${ftpclient.basepath}")
	private String basepath;
	@Value("${ftpclient.filepath}")
	private String filepath;
	
	//文件上传
	@Override
	public Map<String, Object> upload(MultipartFile file) throws IOException {
		String oldName = file.getOriginalFilename();
		String genImageName = IDUtils.genImageName()+
								oldName.substring(oldName.lastIndexOf("."));
		boolean result = FtpUtil.uploadFile(host, port, usrname, password, 
							basepath, filepath, genImageName, file.getInputStream());
		
		Map<String, Object> map = new HashMap<>();
		if (result) {
			map.put("error", 0);
			map.put("url", "http://" + host + "/" + genImageName);
		}else{
			map.put("error", 1);
			map.put("message", "图片上传失败");
		}
		return map;
	}
}
