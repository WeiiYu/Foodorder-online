package com.drdg.android.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drdg.android.bean.FeedBackBean;
import com.drdg.android.dao.FeedBackDao;
import com.drdg.android.service.IFeedBackService;


@Service("FeedBackService")
public class FeedBackService implements IFeedBackService
{
	@Autowired
	private FeedBackDao feedBackDao;

	public List<FeedBackBean> GetList(FeedBackBean feedBackBean) {
		// TODO Auto-generated method stub
		return feedBackDao.GetList(feedBackBean);
	}

	public FeedBackBean GetListById(FeedBackBean feedBackBean) {
		// TODO Auto-generated method stub
		return feedBackDao.GetListById(feedBackBean);
	}

	public int Insert(FeedBackBean feedBackBean) {
		// TODO Auto-generated method stub
		return feedBackDao.Insert(feedBackBean);
	}

	public int Delete(FeedBackBean feedBackBean) {
		// TODO Auto-generated method stub
		return feedBackDao.Delete(feedBackBean);
	}

	public int Update(FeedBackBean feedBackBean) {
		// TODO Auto-generated method stub
		return feedBackDao.Update(feedBackBean);
	}

	public List<FeedBackBean> GetListByEmail(FeedBackBean feedBackBean) {
		// TODO Auto-generated method stub
		return feedBackDao.GetListByEmail(feedBackBean);
	}



}
