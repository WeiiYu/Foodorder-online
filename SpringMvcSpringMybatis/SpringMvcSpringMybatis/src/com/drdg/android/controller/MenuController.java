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
import com.drdg.android.bean.MenuBean;
import com.drdg.android.service.IMenuService;

@Controller
@RequestMapping("/menu/")
public class MenuController {

	
	@Autowired
	private IMenuService iMenuService;

	
	@RequestMapping("getById")
	public void getById(MenuBean menuBean,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			MenuBean menu= iMenuService.GetListById(menuBean);
			JSONObject  jsonObject= new JSONObject();
			jsonObject.put("id", menu.getId());
			jsonObject.put("foodname", menu.getFoodname());
			jsonObject.put("foodprice", menu.getFoodprice());
			jsonObject.put("foodpicture", menu.getFoodpicture());
			jsonObject.put("foodcalorie", menu.getFoodcalorie());
			jsonObject.put("foodtype", menu.getfoodtype());
			out.print(jsonObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//hetbyid the method connect with android and database method 
	@RequestMapping("insertMenu")
	public void insertFine(MenuBean menuBean,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			int i=iMenuService.Insert(menuBean);
			out.print(i);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("updateMenu")
	public void updateMenu(MenuBean menuBean,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			int i=iMenuService.Update(menuBean);
			out.print(i);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//update menu method the method connect with android and database method 
	
	@RequestMapping("delMenu")
	public void delMenu(MenuBean menuBean,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			int i=iMenuService.Delete(menuBean);
			out.print(i);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("menuList")
	public void fineList(MenuBean menuBean,
			HttpServletRequest request, HttpServletResponse response) {

		 List<MenuBean> list = iMenuService.GetList(menuBean);
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
				for (int i = 0; i < list.size(); i++) {
					MenuBean menu= (MenuBean) list.get(i);
					jsonObject = new JSONObject();
					jsonObject.put("id", menu.getId());
					jsonObject.put("foodname", menu.getFoodname());
					jsonObject.put("foodprice", menu.getFoodprice());
					jsonObject.put("foodpicture", menu.getFoodpicture());
					jsonObject.put("foodcalorie", menu.getFoodcalorie());
					jsonObject.put("foodtype", menu.getfoodtype());
					jsonArray.add(jsonObject);
				} 
				out.print(jsonArray.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping("GetListByName")
	public void GetListByName(MenuBean menuBean,
			HttpServletRequest request, HttpServletResponse response) {

		 List<MenuBean> list = iMenuService.GetListByName(menuBean);
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			if(list.size()==0){
				 out.print("nofood");
			 }else{
				 for (int i = 0; i < list.size(); i++) {
						MenuBean menu= (MenuBean) list.get(i);
						jsonObject = new JSONObject();
						jsonObject.put("id", menu.getId());
						jsonObject.put("foodname", menu.getFoodname());
						jsonObject.put("foodprice", menu.getFoodprice());
						jsonObject.put("foodpicture", menu.getFoodpicture());
						jsonObject.put("foodcalorie", menu.getFoodcalorie());
						jsonObject.put("foodtype", menu.getfoodtype());
						jsonArray.add(jsonObject);
					}
					out.print(jsonArray.toString()); 
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("GetListByUser")
	public void GetListByUser(MenuBean menuBean,
			HttpServletRequest request, HttpServletResponse response) {

		 List<MenuBean> list = iMenuService.GetListByUser(menuBean);
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
				 for (int i = 0; i < list.size(); i++) {
						MenuBean menu= (MenuBean) list.get(i);
						jsonObject = new JSONObject();
						jsonObject.put("id", menu.getId());
						jsonObject.put("foodname", menu.getFoodname());
						jsonObject.put("foodprice", menu.getFoodprice());
						jsonObject.put("foodpicture", menu.getFoodpicture());
						jsonObject.put("foodcalorie", menu.getFoodcalorie());
						jsonObject.put("foodtype", menu.getfoodtype());
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						jsonObject.put("uptime",sdf.format(menu.getUptime()));
						jsonArray.add(jsonObject);
					}
					out.print(jsonArray.toString()); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// these all method the method connect with android and database method 
}
