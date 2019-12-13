package com.drdg.android.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drdg.android.bean.OrderBean;
import com.drdg.android.dao.OrderDao;
import com.drdg.android.service.IOrderService;


@Service("OrderService")
public class OrderService implements IOrderService
{
	@Autowired
	private OrderDao orderDao;

	public List<OrderBean> GetList(OrderBean orderBean) {
		// TODO Auto-generated method stub
		return orderDao.GetList(orderBean);
	}

	public OrderBean GetListById(OrderBean orderBean) {
		// TODO Auto-generated method stub
		return orderDao.GetListById(orderBean);
	}

	public int Insert(OrderBean orderBean) {
		// TODO Auto-generated method stub
		return orderDao.Insert(orderBean);
	}

	public int Delete(OrderBean orderBean) {
		// TODO Auto-generated method stub
		return orderDao.Delete(orderBean);
	}

	public int Update(OrderBean orderBean) {
		// TODO Auto-generated method stub
		return orderDao.Update(orderBean);
	}



}
