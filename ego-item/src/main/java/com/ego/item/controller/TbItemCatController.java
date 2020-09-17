package com.ego.item.controller;

import javax.annotation.Resource;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.item.service.TbItemCatService;

@Controller
public class TbItemCatController {
	@Resource
	private TbItemCatService tbItemCatServiceImpl;

	/**
	 * 返回Jsonp数据格式，里面包含所有菜单信息
	 * @param callback
	 * @return
	 */
	// 由ego-protal项目请求 ego-item的控制器:
	// http://127.0.0.1:8081/rest/itemcat/all?callback=category.getDataService
	@RequestMapping("rest/itemcat/all")
	@ResponseBody
	public MappingJacksonValue showMenu(String callback){
		MappingJacksonValue mjv = new MappingJacksonValue(tbItemCatServiceImpl.showCatMenu());
		mjv.setJsonpFunction(callback);   // callback=category.getDataService
		
		return mjv;
	}
}
