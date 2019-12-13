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
import com.drdg.android.bean.OrderBean;
import com.drdg.android.service.IMenuService;
import com.drdg.android.service.IOrderService;

@Controller
@RequestMapping("/order/")
//request mapper class
public class OrderController {

	
	@Autowired
	private IOrderService iOrderService;

	@Autowired
	private IMenuService iMenuService;
// these all method the method connect with android and database method 


	@RequestMapping("getById")
	public void getById(OrderBean orderBean,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			OrderBean  or= iOrderService.GetListById(orderBean);
			if(or==null){
				out.print("11");
			}else{
				out.print("10");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("delorder")
	public void delCollect(OrderBean orderBean,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
				int i=iOrderService.Delete(orderBean);
				if(i==0){
					out.print("delOrderFail");
				}else{
					out.print("delOrderSuccess");
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("insertorder")
	public void insertorder(OrderBean orderBean,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			
			MenuBean menuBean =new MenuBean();
			menuBean.setId(orderBean.getMenuid());
			MenuBean menu= iMenuService.GetListById(menuBean);
			int mf=Integer.parseInt(menu.getFoodcalorie());
			int of=Integer.parseInt(orderBean.getOrderquantity());
			if(mf<of){
				 out.print("nonum");
			}else{
				int i=iOrderService.Insert(orderBean);
				menu.setFoodcalorie(Integer.toString(mf-of));
				iMenuService.Update(menu);
				if(i==0){
					out.print("saveOrderFail");
				}else{
					out.print("saveOrderSuccess");
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("null")
	@RequestMapping("orderList")
	public void orderList(OrderBean orderBean,
			HttpServletRequest request, HttpServletResponse response) {

		 List<OrderBean> list = iOrderService.GetList(orderBean);
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject =null;
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
				for (int i = 0; i < list.size(); i++) {
					OrderBean or= (OrderBean) list.get(i);
					jsonObject=new JSONObject();
					jsonObject.put("id", or.getId());
					jsonObject.put("foodname", or.getFoodname());
					jsonObject.put("useremail", or.getUseremail());
					jsonObject.put("menuid",or.getMenuid());
					jsonObject.put("foodtype",or.getFoodtype());
					jsonObject.put("orderquantity",or.getOrderquantity());
					jsonObject.put("orderpayment",or.getOrderpayment());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					jsonObject.put("orderTime",sdf.format(or.getOrderTime()));
					jsonArray.add(jsonObject);
				}
				out.print(jsonArray.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
