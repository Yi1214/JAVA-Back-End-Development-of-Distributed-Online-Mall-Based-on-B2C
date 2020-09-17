package com.ego.dubbo.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbItemParam;

public interface TbItemParamDubboService {
	/**
	 * 分页显示数据
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDataGrid showPage(int page, int rows);
	
	/**
	 * 批量删除规格参数
	 * @param ids
	 * @return
	 */
	int delByIds(String ids) throws Exception;
	
	/**
	 * 根据item_cat_id查询参数模板（tb_item_param表）
	 * @param catId
	 * @return
	 */
	TbItemParam SelByCatid(long catId);
	
	/**
	 * 新增，支持主键自增
	 * @param param
	 * @return
	 */
	int insParam(TbItemParam param);
}
