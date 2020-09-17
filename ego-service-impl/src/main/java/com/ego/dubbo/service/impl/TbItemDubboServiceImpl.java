package com.ego.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.mapper.TbItemDescMapper;
import com.ego.mapper.TbItemMapper;
import com.ego.mapper.TbItemParamItemMapper;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemExample;
import com.ego.pojo.TbItemParamItem;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class TbItemDubboServiceImpl implements TbItemDubboService{
	@Resource
	private TbItemMapper tbItemMapper;
	@Resource
	private TbItemDescMapper tbItemDescMapper;
	@Resource
	private TbItemParamItemMapper tbItemParamItemMapper;
	
	// 商品分页查询
	@Override
	public EasyUIDataGrid show(int page, int rows) {
		// 设置分页条件
		PageHelper.startPage(page, rows);
		
		// 查询全部
		List<TbItem> list = tbItemMapper.selectByExample(new TbItemExample());
		
		// 分页代码
		PageInfo<TbItem> pi = new PageInfo<>(list);
		
		//放入到实体类
		EasyUIDataGrid dataGrid = new EasyUIDataGrid();
		dataGrid.setRows(pi.getList());
		dataGrid.setTotal(pi.getTotal());
		
		return dataGrid;
	}
	
	// 修改商品的状态
	@Override
	public int updItemStatus(TbItem tbItem) {
		// TODO Auto-generated method stub
		return tbItemMapper.updateByPrimaryKeySelective(tbItem);
	}
	
	// 新增，添加到商品表和商品描述表中
	@Override
	public int insTbItemDesc(TbItem tbItem, TbItemDesc desc, TbItemParamItem paramItem) throws Exception {
		// duubo-service-impl的作用：调用mapper，获取/修改数据库的内容
		int index = 0;
		try {
			index = tbItemMapper.insertSelective(tbItem);    // Selective：有空缺时不会自动填充
			index += tbItemDescMapper.insertSelective(desc);   //Selective：有空缺时不会自动填充
			index += tbItemParamItemMapper.insertSelective(paramItem); //Selective：有空缺时不会自动填充
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (index==3) {
			return 1;
		}else{
			throw new Exception("新增失败，数据还原！");
		}

	}
}
