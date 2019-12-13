package com.drdg.android.dao;

import java.util.List;

import com.drdg.android.bean.CollectBean;
import com.drdg.android.bean.OrderBean;

public interface OrderDao {

   public List<OrderBean> GetList(OrderBean orderBean);
   
   public int Insert(OrderBean orderBean);
   
   public int Delete(OrderBean orderBean);
   
   public int Update(OrderBean orderBean);
   
   public OrderBean GetListById(OrderBean orderBean);
    // all the interface connect wirh collectBean class and use the medhod
   
}
