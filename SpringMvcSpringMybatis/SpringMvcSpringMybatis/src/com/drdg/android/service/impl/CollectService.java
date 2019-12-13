package com.drdg.android.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drdg.android.bean.CollectBean;
import com.drdg.android.dao.CollectDao;
import com.drdg.android.service.ICollectService;


@Service("CollectService")
public class CollectService implements ICollectService
{
	@Autowired
	private CollectDao collectDao;

	public List<CollectBean> GetList(CollectBean collectBean) {
		// TODO Auto-generated method stub
		return collectDao.GetList(collectBean);
	}

	public CollectBean GetListById(CollectBean collectBean) {
		// TODO Auto-generated method stub
		return collectDao.GetListById(collectBean);
	}

	public int Insert(CollectBean collectBean) {
		// TODO Auto-generated method stub
		return collectDao.Insert(collectBean);
	}

	public int Delete(CollectBean collectBean) {
		// TODO Auto-generated method stub
		return collectDao.Delete(collectBean);
	}

	public int Update(CollectBean collectBean) {
		// TODO Auto-generated method stub
		return collectDao.Update(collectBean);
	}



}
