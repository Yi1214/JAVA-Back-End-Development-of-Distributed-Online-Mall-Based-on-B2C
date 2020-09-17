package com.ego.manage.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.impl.TbItemParamServiceImpl;
import com.ego.pojo.TbItemParam;

@Controller
public class TbItemParamController {
	@Resource
	private TbItemParamServiceImpl tbItemParamServiceImpl;
	
	/**
	 * 分页查询规格参数
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("item/param/list")
	@ResponseBody
	public EasyUIDataGrid showPage(int page, int rows) {
		return tbItemParamServiceImpl.showPage(page, rows);
	}
	
	@RequestMapping("item/param/delete")
	@ResponseBody
	public EgoResult delete(String ids){
		EgoResult er = new EgoResult();
		try {
			int index = tbItemParamServiceImpl.delete(ids);
			if(index==1){
				er.setStatus(200);
			}
		} catch (Exception e) {
			er.setData(e.getMessage());
		}
		return er;
	}
	
	/**
	 * 点击商品类目按钮，显示添加分组按钮
	 * 判断类目是否已经添加模板
	 * @param catId
	 * @return
	 */
	@RequestMapping("item/param/query/itemcatid/{catId}")
	@ResponseBody
	public EgoResult show(@PathVariable long catId) {
		return tbItemParamServiceImpl.showParam(catId);
	}
	
	/**
	 * 商品类目新增
	 * @param param
	 * @param catId
	 * @return
	 */
	@RequestMapping("item/param/save/{catId}")
	@ResponseBody
	public EgoResult save(TbItemParam param, @PathVariable long catId) {
		param.setItemCatId(catId);
		return tbItemParamServiceImpl.save(param);
	}

}
