package com.drdg.android.service;

import java.util.List;

import com.drdg.android.bean.UserBean;

public interface IUserService {

	public abstract List<UserBean> GetList();  

	public abstract UserBean GetListById(UserBean userBean);
	
	public abstract UserBean GetListByAccount(UserBean userBean);

	public abstract int Insert(UserBean userBean);

	public abstract int Delete(UserBean userBean);

	public abstract int Update(UserBean userBean);
	// these all method connect with bean class 
}