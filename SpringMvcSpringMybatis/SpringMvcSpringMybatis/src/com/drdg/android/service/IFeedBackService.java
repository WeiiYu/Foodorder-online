package com.drdg.android.service;

import java.util.List;

import com.drdg.android.bean.FeedBackBean;


public interface IFeedBackService {

	public abstract List<FeedBackBean> GetList(FeedBackBean feedBackBean);
	
	public abstract List<FeedBackBean> GetListByEmail(FeedBackBean feedBackBean);

	public abstract FeedBackBean GetListById(FeedBackBean feedBackBean);

	public abstract int Insert(FeedBackBean feedBackBean);

	public abstract int Delete(FeedBackBean feedBackBean);

	public abstract int Update(FeedBackBean feedBackBean);
	// these all method connect with bean class 
}