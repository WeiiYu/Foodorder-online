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
import com.drdg.android.service.ICollectService;

@Controller
@RequestMapping("/collect/")
public class CollectController {

	
	@Autowired
	private ICollectService iCollectService;

	
	@RequestMapping("getById")
	public void getById(CollectBean collectBean,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			CollectBean  collect= iCollectService.GetListById(collectBean);
			if(collect==null){
				out.print("11");
			}else{
				out.print("10");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//getByid method , the method connect with android and database method 
	
	@RequestMapping("delCollect")
	public void delCollect(CollectBean collectBean,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
				int i=iCollectService.Delete(collectBean);
				if(i==0){
					out.print("delFail");
				}else{
					out.print("delSuccess");
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// decollect method,the method connect with android and database method 
	
	@RequestMapping("insertCollect")
	public void insertFine(CollectBean collectBean,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			
			CollectBean  collect= iCollectService.GetListById(collectBean);
			if(collect==null){
				int i=iCollectService.Insert(collectBean);
				if(i==0){
					out.print("saveFail");
				}else{
					out.print("saveSuccess");
				}
			}else{
				out.print("hasSaved");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// insert method the method connect with android and database method 
	
	@SuppressWarnings("null")
	@RequestMapping("collectList")
	public void fineList(CollectBean collectBean,
			HttpServletRequest request, HttpServletResponse response) {

		 List<CollectBean> list = iCollectService.GetList(collectBean);
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject =null;
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
				for (int i = 0; i < list.size(); i++) {
					CollectBean collect= (CollectBean) list.get(i);
					jsonObject=new JSONObject();
					jsonObject.put("id", collect.getId());
					jsonObject.put("foodname", collect.getFoodname());
					jsonObject.put("useremail", collect.getUseremail());
					jsonObject.put("menuid",collect.getMenuid());
					jsonObject.put("foodtype",collect.getFoodtype());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					jsonObject.put("collectTime",sdf.format(collect.getCollectTime()));
					jsonArray.add(jsonObject);
				}
				out.print(jsonArray.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//get the collectlist
}
