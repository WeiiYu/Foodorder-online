package com.drdg.android.service;

import java.util.List;

import com.drdg.android.bean.OrderBean;


public interface IOrderService {

	public abstract List<OrderBean> GetList(OrderBean orderBean);

	public abstract OrderBean GetListById(OrderBean orderBean);

	public abstract int Insert(OrderBean orderBean);

	public abstract int Delete(OrderBean orderBean);

	public abstract int Update(OrderBean orderBean);
	// these all method connect with bean class 
}