package com.ego.manage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.dubbo.service.TbItemParamDubboService;
import com.ego.manage.pojo.TbItemParamChild;
import com.ego.manage.service.TbItemParamService;
import com.ego.pojo.TbItemParam;

import net.sf.jsqlparser.statement.create.table.Index;

@Service
public class TbItemParamServiceImpl implements TbItemParamService{
	@Reference
	private TbItemParamDubboService tbItemParamDubboServiceImpl;
	@Reference
	private TbItemCatDubboService tbItemCatDubboServiceImpl;

	@Override
	public EasyUIDataGrid showPage(int page, int rows) {
		// 分页查询
		EasyUIDataGrid datagrid = tbItemParamDubboServiceImpl.showPage(page, rows);
		List<TbItemParam> list = (List<TbItemParam>) datagrid.getRows();
		
		List<TbItemParamChild> listChild = new ArrayList<>();
		
		for(TbItemParam tbItemParam: list) {
			TbItemParamChild tbItemParamChild = new TbItemParamChild();
			tbItemParamChild.setCreated(tbItemParam.getCreated());
			tbItemParamChild.setId(tbItemParam.getId());
			tbItemParamChild.setItemCatId(tbItemParam.getItemCatId());
			tbItemParamChild.setParamData(tbItemParam.getParamData());
			tbItemParamChild.setUpdated(tbItemParam.getUpdated());
			tbItemParamChild.setItemCatName(tbItemCatDubboServiceImpl.selById(tbItemParamChild.getItemCatId()).getName());
			listChild.add(tbItemParamChild);
		}
		datagrid.setRows(listChild);
		return datagrid;
	}

	@Override
	public int delete(String ids) throws Exception {
		return tbItemParamDubboServiceImpl.delByIds(ids);
	}
	
	@Override
	public EgoResult showParam(long catId) {
		EgoResult er = new EgoResult();

		TbItemParam param = tbItemParamDubboServiceImpl.SelByCatid(catId);
		
		if (param!=null) {
			er.setStatus(200);
			er.setData(param);
		}
		return er;
	}
	
	@Override
	public EgoResult save(TbItemParam param) {
		Date date = new Date();
		param.setCreated(date);
		param.setUpdated(date);
		int index = tbItemParamDubboServiceImpl.insParam(param);
		EgoResult er = new EgoResult();
		if(index>0) {
			er.setStatus(200);
		}
		return er;
	}

}
