package com.ego.item.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.item.pojo.PortalMenu;
import com.ego.item.pojo.PortalMenuNode;
import com.ego.item.service.TbItemCatService;
import com.ego.pojo.TbItemCat;

@Service
public class TbItemCatServiceImpl implements TbItemCatService{
	@Reference
	private TbItemCatDubboService tbItemCatDubboServiceImpl;
	
	/**
	 * 将查询到的所有菜单装入PortalMenu.data中
	 */
	@Override
	public PortalMenu showCatMenu() {
		// 查出所有一级菜单
		List<TbItemCat> list = tbItemCatDubboServiceImpl.show(0);
		PortalMenu pm = new PortalMenu();
		pm.setData(selAllMenu(list));
		return pm;
	}
	
	/**
	 * 返回所有查询到的菜单结果
	 * @param list
	 * @return
	 */
	public List<Object> selAllMenu(List<TbItemCat> list) {
		List<Object> listNode = new ArrayList<>(); 
		for(TbItemCat tbItemCat: list) {
			// 父级菜单
			if (tbItemCat.getIsParent()) {
				PortalMenuNode pmd = new PortalMenuNode();
				pmd.setU("/products/"+ tbItemCat.getId() + ".html");
				pmd.setN("<a href='/products/" + tbItemCat.getId() +
							".html'>" + tbItemCat.getName() + "</a>");
				// 递归调用selAllMenu
				// 因为是父菜单，I中仍存在下一级U,N,I的数据
				pmd.setI(selAllMenu(tbItemCatDubboServiceImpl.show(tbItemCat.getId())));
				listNode.add(pmd);
			// 子菜单
			} else {
				listNode.add("/products/" + tbItemCat.getId() + ".html|" + tbItemCat.getName());
			}
		}
		return listNode;
	}
}
