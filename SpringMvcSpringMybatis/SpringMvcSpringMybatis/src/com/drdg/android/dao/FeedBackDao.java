package com.drdg.android.dao;

import java.util.List;

import com.drdg.android.bean.FeedBackBean;

public interface FeedBackDao {

   public List<FeedBackBean> GetList(FeedBackBean feedBackBean);
   
   public List<FeedBackBean> GetListByEmail(FeedBackBean feedBackBean);
   
   public int Insert(FeedBackBean feedBackBean);
   
   public int Delete(FeedBackBean feedBackBean);
   
   public int Update(FeedBackBean feedBackBean);
   
   public FeedBackBean GetListById(FeedBackBean feedBackBean);
    // all the interface connect wirh collectBean class and use the medhod
   
}
