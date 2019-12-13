package com.drdg.android.service;

import java.util.List;

import com.drdg.android.bean.CollectBean;


public interface ICollectService {

	public abstract List<CollectBean> GetList(CollectBean collectBean);

	public abstract CollectBean GetListById(CollectBean collectBean);

	public abstract int Insert(CollectBean collectBean);

	public abstract int Delete(CollectBean collectBean);

	public abstract int Update(CollectBean collectBean);
	// these all method connect with bean class 
}