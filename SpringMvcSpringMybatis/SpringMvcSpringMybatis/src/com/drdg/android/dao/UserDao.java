package com.drdg.android.dao;

import java.util.List;

import com.drdg.android.bean.UserBean;

public interface UserDao {

   public List<UserBean> GetList();
   
   public int Insert(UserBean userBean);
   
   public int Delete(UserBean userBean);
   
   public int Update(UserBean userBean);
   
   public UserBean GetListByAccount(UserBean userBean);
   
   public UserBean GetListById(UserBean userBean);
    // all the interface connect wirh collectBean class and use the medhod
   
}
