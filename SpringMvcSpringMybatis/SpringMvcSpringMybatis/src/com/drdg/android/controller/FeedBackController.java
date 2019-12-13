package com.drdg.android.controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.drdg.android.bean.CollectBean;
import com.drdg.android.bean.FeedBackBean;
import com.drdg.android.service.IFeedBackService;

@Controller
@RequestMapping("/feedback/")
public class FeedBackController {
// feedback controller 
	
	@Autowired
	private IFeedBackService iFeedBackService;

	
	@RequestMapping("getById")
	public void getById(FeedBackBean feedBackBean,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			FeedBackBean feedBack= iFeedBackService.GetListById(feedBackBean);
			JSONObject  jsonObject= new JSONObject();
			jsonObject.put("id", feedBack.getId());
			jsonObject.put("foodname", feedBack.getFoodname());
			jsonObject.put("foodid", feedBack.getFoodid());
			jsonObject.put("username", feedBack.getUsername());
			jsonObject.put("useremail",feedBack.getUseremail());
			jsonObject.put("suggestion",feedBack.getSuggestion());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			jsonObject.put("feedbackDate",sdf.format(feedBack.getFeedbackDate()));
			out.print(jsonObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// getById method the method connect with android and database method 
	
	@RequestMapping("insertFeedBack")
	public void insertFine(FeedBackBean feedBackBean,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			int i=iFeedBackService.Insert(feedBackBean);
			out.print(i);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//insertFeedback method the method connect with android and database method 

	@RequestMapping("delFeedBack")
	public void delFeedBack(FeedBackBean feedBackBean,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
				int i=iFeedBackService.Delete(feedBackBean);
				if(i==0){
					out.print("delFeedFail");
				}else{
					out.print("delFeedSuccess");
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("null")
	@RequestMapping("feedBackList")
	public void fineList(FeedBackBean feedBackBean,
			HttpServletRequest request, HttpServletResponse response) {

		 List<FeedBackBean> list = iFeedBackService.GetList(feedBackBean);
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject =null;
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
				for (int i = 0; i < list.size(); i++) {
					FeedBackBean feedBack= (FeedBackBean) list.get(i);
					jsonObject=new JSONObject();
					jsonObject.put("id", feedBack.getId());
					jsonObject.put("foodname", feedBack.getFoodname());
					jsonObject.put("foodid", feedBack.getFoodid());
					jsonObject.put("username", feedBack.getUsername());
					jsonObject.put("useremail",feedBack.getUseremail());
					jsonObject.put("suggestion",feedBack.getSuggestion());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					jsonObject.put("feedbackDate",sdf.format(feedBack.getFeedbackDate()));
					jsonArray.add(jsonObject);
				}
				out.print(jsonArray.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("null")
	@RequestMapping("GetListByEmail")
	public void GetListByEmail(FeedBackBean feedBackBean,
			HttpServletRequest request, HttpServletResponse response) {

		 List<FeedBackBean> list = iFeedBackService.GetListByEmail(feedBackBean);
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject =null;
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
				for (int i = 0; i < list.size(); i++) {
					FeedBackBean feedBack= (FeedBackBean) list.get(i);
					jsonObject=new JSONObject();
					jsonObject.put("id", feedBack.getId());
					jsonObject.put("foodname", feedBack.getFoodname());
					jsonObject.put("foodid", feedBack.getFoodid());
					jsonObject.put("username", feedBack.getUsername());
					jsonObject.put("useremail",feedBack.getUseremail());
					jsonObject.put("suggestion",feedBack.getSuggestion());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					jsonObject.put("feedbackDate",sdf.format(feedBack.getFeedbackDate()));
					jsonArray.add(jsonObject);
				}
				out.print(jsonArray.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// these all method the method connect with android and database method 
}
