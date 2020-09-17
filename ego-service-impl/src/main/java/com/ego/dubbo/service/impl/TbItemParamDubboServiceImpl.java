package com.ego.dubbo.service.impl;



import java.util.List;

import javax.annotation.Resource;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.dubbo.service.TbItemParamDubboService;
import com.ego.mapper.TbItemParamMapper;
import com.ego.pojo.TbItemParam;
import com.ego.pojo.TbItemParamExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class TbItemParamDubboServiceImpl implements TbItemParamDubboService{
	@Resource
	private TbItemParamMapper mapper;
	
	@Override
	public EasyUIDataGrid showPage(int page, int rows) {
		// 1. 先PageHelper.startPage()设置分页条件
		PageHelper.startPage(page, rows);
		// 2. 再写查询语句 
		// 若表中有text类型的数据需要查询，则使用withBlobs()方法
		List<TbItemParam> list = mapper.selectByExampleWithBLOBs(new TbItemParamExample());
		// 3. 封装到PageInfo
		PageInfo<TbItemParam> pi = new PageInfo<>(list);
		
		//4. 设置返回结果EasyUIgrid
		EasyUIDataGrid datagrid = new EasyUIDataGrid();
		datagrid.setTotal(pi.getTotal());
		datagrid.setRows(pi.getList());

		return datagrid;
	}
	
	@Override
	public int delByIds(String ids) throws Exception {
		String[] idStr = ids.split(",");
		int index = 0;
		
		for(String id: idStr) {
			index += mapper.deleteByPrimaryKey(Long.parseLong(id));
		}
		
		if(index==idStr.length) {
			return 1;
		}else {
			throw new Exception("删除失败. 可能原因：数据已经不存在！");
		}
	}
	
	@Override
	public TbItemParam SelByCatid(long catId) {
		TbItemParamExample example = new TbItemParamExample();
		// 先创建查询条件点，and后为条件
		example.createCriteria().andItemCatIdEqualTo(catId);
		List<TbItemParam> list = mapper.selectByExampleWithBLOBs(example);
		if (list!=null && list.size()>0) {
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public int insParam(TbItemParam param) {
		return mapper.insertSelective(param);
	}
}
