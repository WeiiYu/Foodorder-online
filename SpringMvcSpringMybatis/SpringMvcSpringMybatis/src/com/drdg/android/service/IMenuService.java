package com.drdg.android.service;

import java.util.List;

import com.drdg.android.bean.MenuBean;


public interface IMenuService {

	public abstract List<MenuBean> GetList(MenuBean menuBean);
	
	public abstract List<MenuBean> GetListByName(MenuBean menuBean);
	
	public abstract List<MenuBean> GetListByUser(MenuBean menuBean);

	public abstract MenuBean GetListById(MenuBean menuBean);

	public abstract int Insert(MenuBean menuBean);

	public abstract int Delete(MenuBean menuBean);

	public abstract int Update(MenuBean menuBean);
	// these all method connect with bean class 
}
