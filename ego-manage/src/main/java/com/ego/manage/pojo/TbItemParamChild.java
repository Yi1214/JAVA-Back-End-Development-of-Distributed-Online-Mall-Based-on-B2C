package com.ego.manage.pojo;

import com.ego.pojo.TbItemParam;

public class TbItemParamChild extends TbItemParam{
	// TbItemParam中没有商品类目名称 属性，查询规格参数时会无法显示这一栏，因此需要再建一个子类具有该属性
	private String itemCatName;

	public String getItemCatName() {
		return itemCatName;
	}

	public void setItemCatName(String itemCatName) {
		this.itemCatName = itemCatName;
	}   
	
}
