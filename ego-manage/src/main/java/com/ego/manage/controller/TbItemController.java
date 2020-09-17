package com.ego.manage.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbItemService;
import com.ego.pojo.TbItem;

@Controller
public class TbItemController {
	@Resource
	private TbItemService tbItemServiceImpl;  // @Resource自动注入bean，不需要new ServiceImpl
	
	/**
	 * 分页显示商品
	 */
	@RequestMapping("item/list")
	@ResponseBody
	public EasyUIDataGrid show(int page, int rows) {
		return tbItemServiceImpl.show(page, rows);
	} 
	
	// 商品修改
	@RequestMapping("rest/page/item-edit")
	public String showItemEdit() {
		return "item-edit";
	}
	
	// 商品删除
	@RequestMapping("rest/item/delete")
	@ResponseBody
	public EgoResult delete(String ids) {
		EgoResult er= new EgoResult();
		int index = tbItemServiceImpl.update(ids, (byte)3);
		if (index==1) {
			er.setStatus(200);
		}
		return er;
	}
	
	// 商品下架
	@RequestMapping("rest/item/instock")
	@ResponseBody
	public EgoResult instock(String ids) {
		EgoResult er= new EgoResult();
		int index = tbItemServiceImpl.update(ids, (byte)2);
		if (index==1) {
			er.setStatus(200);
		}
		return er;
	}
	
	// 商品上架
	@RequestMapping("rest/item/reshelf")
	@ResponseBody
	public EgoResult reshelf(String ids) {
		EgoResult er= new EgoResult();
		int index = tbItemServiceImpl.update(ids, (byte)1);
		if (index==1) {
			er.setStatus(200);
		}
		return er;
	}
	
	// 商品新增
	@RequestMapping("item/save")
	@ResponseBody
	public EgoResult insert(TbItem item, String desc, String itemParams) {
		EgoResult er = new EgoResult();
		int index;
		// 通过SpringMVC，提交的变量会自动复制给item对象中的同名属性
		try {
			index = tbItemServiceImpl.save(item, desc, itemParams);
			System.out.println("Controller index:" + index);
			if (index == 1) {
				er.setStatus(200);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			er.setData(e.getMessage());
		}
		
		return er;
	}
	
	
	
}
