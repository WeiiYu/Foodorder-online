package com.drdg.android.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drdg.android.bean.UserBean;
import com.drdg.android.dao.UserDao;
import com.drdg.android.service.IUserService;

@Service("UserService")
public class UserService implements IUserService
{
	@Autowired
	private UserDao userDao;
	
	public UserDao getUserDao() {
		return userDao;
	}

	public List<UserBean> GetList() {
		// TODO Auto-generated method stub
		return userDao.GetList();
	}

	public UserBean GetListByAccount(UserBean userBean) {
		// TODO Auto-generated method stub
		return userDao.GetListByAccount(userBean);
	}

	public int Insert(UserBean userBean) {
		// TODO Auto-generated method stub
		return userDao.Insert(userBean);
	}

	public int Delete(UserBean userBean) {
		// TODO Auto-generated method stub
		return userDao.Delete(userBean);
	}

	public int Update(UserBean userBean) {
		// TODO Auto-generated method stub
		return userDao.Update(userBean);
	}

	public UserBean GetListById(UserBean userBean) {
		// TODO Auto-generated method stub
		return userDao.GetListById(userBean);
	}

}
