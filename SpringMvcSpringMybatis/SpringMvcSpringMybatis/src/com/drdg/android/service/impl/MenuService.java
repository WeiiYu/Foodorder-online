package com.drdg.android.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drdg.android.bean.MenuBean;
import com.drdg.android.dao.MenuDao;
import com.drdg.android.service.IMenuService;


@Service("MenuService")
public class MenuService implements IMenuService
{
	@Autowired
	private MenuDao menuDao;

	public List<MenuBean> GetList(MenuBean menuBean) {
		// TODO Auto-generated method stub
		return menuDao.GetList(menuBean);
	}

	public MenuBean GetListById(MenuBean menuBean) {
		// TODO Auto-generated method stub
		return menuDao.GetListById(menuBean);
	}

	public int Insert(MenuBean menuBean) {
		// TODO Auto-generated method stub
		return menuDao.Insert(menuBean);
	}

	public int Delete(MenuBean menuBean) {
		// TODO Auto-generated method stub
		return menuDao.Delete(menuBean);
	}

	public int Update(MenuBean menuBean) {
		// TODO Auto-generated method stub
		return menuDao.Update(menuBean);
	}

	public List<MenuBean> GetListByName(MenuBean menuBean) {
		// TODO Auto-generated method stub
		return menuDao.GetListByName(menuBean);
	}

	public List<MenuBean> GetListByUser(MenuBean menuBean) {
		// TODO Auto-generated method stub
		return menuDao.GetListByUser(menuBean);
	}



}
