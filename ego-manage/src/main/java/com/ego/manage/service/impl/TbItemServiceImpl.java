package com.ego.manage.service.impl;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.utils.IDUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.manage.service.TbItemService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;

@Service
public class TbItemServiceImpl implements TbItemService{
	// @Reference是duubo专用的注解，注入的是分布式中的远程服务对象
	// @Resource和@Autowired注入的是本地spring容器中的对象。
	// 只不过@Autowired是byType自动注入，而@Resource默认byName自动注入。
	@Reference   
	private TbItemDubboService tbItemDubboServiceImpl;
	
	// 分页查询商品
	@Override
	public EasyUIDataGrid show(int page, int rows) {
		// TODO Auto-generated method stub
		return tbItemDubboServiceImpl.show(page, rows);
	}
	
	// 更新商品的状态
	@Override
	public int update(String ids, byte status) {
		int index = 0;
		
		String[] idsStr = ids.split(",");   //用逗号分开
		
		TbItem tbItem = new TbItem();
		
		for (String id : idsStr) {
			tbItem.setId(Long.parseLong(id));
			tbItem.setStatus(status);
			index += tbItemDubboServiceImpl.updItemStatus(tbItem);
		}
		if (index==idsStr.length){
			return 1;
		}
		return 0;
	}
	
	// 商品新增
	@Override
	public int save(TbItem item, String desc, String itemParams) throws Exception {
		// 产生item的id
		long id = IDUtils.genItemId();
		// 产生创建和修改时间
		Date date = new Date();
		
		item.setId(id);
		item.setCreated(date);
		item.setUpdated(date);
		item.setStatus((byte)1);
		
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		itemDesc.setItemId(id);
		itemDesc.setItemDesc(desc);
		
		TbItemParamItem paramItem = new TbItemParamItem();
		paramItem.setCreated(date);
		paramItem.setUpdated(date);
		paramItem.setId(id);
		paramItem.setParamData(itemParams);
		
		
		int index = 0;
		index = tbItemDubboServiceImpl.insTbItemDesc(item, itemDesc, paramItem);
		System.out.println("index:" + index);
		
		return index;
	}
	

	
}
