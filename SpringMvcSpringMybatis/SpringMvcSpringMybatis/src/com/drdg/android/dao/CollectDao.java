package com.drdg.android.dao;

import java.util.List;

import com.drdg.android.bean.CollectBean;

public interface CollectDao {

   public List<CollectBean> GetList(CollectBean collectBean);
   
   public int Insert(CollectBean collectBean);
   
   public int Delete(CollectBean collectBean);
   
   public int Update(CollectBean collectBean);
   
   public CollectBean GetListById(CollectBean collectBean);
   // all the interface connect wirh collectBean class and use the medhod
   
   
}
