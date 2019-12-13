package com.drdg.android.dao;

import java.util.List;

import com.drdg.android.bean.MenuBean;

public interface MenuDao {

   public List<MenuBean> GetList(MenuBean menuBean);
   
   public int Insert(MenuBean menuBean);
   
   public int Delete(MenuBean menuBean);
   
   public int Update(MenuBean menuBean);
   
   public MenuBean GetListById(MenuBean menuBean);
   
   public List<MenuBean> GetListByName(MenuBean menuBean);
   
   public List<MenuBean> GetListByUser(MenuBean menuBean);
    // all the interface connect wirh collectBean class and use the medhod
}
