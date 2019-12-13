package com.drdg.android.controller;

import java.io.IOException;
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
import com.drdg.android.bean.UserBean;
import com.drdg.android.service.IUserService;
import com.mysql.jdbc.StringUtils;

@Controller
@RequestMapping("/user/")
// connect with user mapper 
public class UserController {

	private IUserService iUserService;

	public IUserService getUserService() {
		return iUserService;
	}
//these all method connect with android and database method 
	@Autowired
	public void setUserService(IUserService iUserService) {
		this.iUserService = iUserService;
	}

	@RequestMapping("userlogin")
	public void login(UserBean userBean, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out=null;
		try {
			out = response.getWriter();
			response.setCharacterEncoding("utf-8");
			request.setCharacterEncoding("utf-8");
			UserBean us = iUserService.GetListByAccount(userBean);
		 
			if (!us.getPassword().equals(userBean.getPassword())) {
				out.print(6);
			} else if(!us.getType().equals(userBean.getType())){
				out.print(5);
			}else if(!us.getAddress().equals(userBean.getAddress()))
			{
				out.print(5);
			}else{
				out.print(us.getUsername());
			}
		} catch (Exception e) {
			out.print(5);
			// TODO: handle exception
		}

	}

	@RequestMapping("addUser")
	public void addUser(UserBean userBean, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");

			PrintWriter out = response.getWriter();
			try {
				// java.sql.Date currentDate = new
				// java.sql.Date(System.currentTimeMillis());
				UserBean us = iUserService.GetListByAccount(userBean);
				if(us==null){
					out.print(insertDo(userBean));
				}else{
					out.print(4);
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				out.print("");
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String  insertDo(UserBean userBean){
		String b = "insertFail";
		int i=0;
		i = iUserService.Insert(userBean);
		if(i==0){
			b = "insertFail";
		}else{
			b = "insertSuccess";
		}
		return b;
	}

	@RequestMapping("updateUser")
	public void updateUser(UserBean userBean, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");

			String account = request.getParameter("account");
			String password = request.getParameter("password");
			String flag = request.getParameter("flag");
			String id = request.getParameter("id");

			PrintWriter out = response.getWriter();

			if (!StringUtils.isNullOrEmpty(id)) {
				try {
					java.sql.Date currentDate = new java.sql.Date(
							System.currentTimeMillis());

					iUserService.Update(userBean);
					out.print("1");
				} catch (Exception e) {
					// TODO: handle exception
					out.print("0");
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("dealUser")
	public void dealUser(UserBean userBean, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");

			String id = request.getParameter("id");
			PrintWriter out = response.getWriter();
			if (!StringUtils.isNullOrEmpty(id)) {
				try {
					iUserService.Delete(userBean);
					out.print("1");
				} catch (Exception e) {
					// TODO: handle exception
					out.print("0");
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("userList")
	public void userList(UserBean userBean, HttpServletRequest request,
			HttpServletResponse response) {
		List<UserBean> list = iUserService.GetList();
		JSONObject jsonObject = null;
		JSONArray jsonArray = new JSONArray();
		try {
			// ���ñ���
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");

			PrintWriter out = response.getWriter();
			SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd");
			for (int i = 0; i < list.size(); i++) {
				userBean = (UserBean) list.get(i);
				jsonObject = new JSONObject();
				jsonObject.put("password", userBean.getPassword());
				jsonArray.add(jsonObject);
			}
			out.print(jsonArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("login")
	public void loginList(UserBean userBean, HttpServletRequest request,
			HttpServletResponse response) {

		JSONObject jsonObject = new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");

			PrintWriter out = response.getWriter();
			String account = request.getParameter("account");
			String password = request.getParameter("password");

			UserBean uBean = iUserService.GetListByAccount(userBean);

			if (uBean == null) {
				jsonObject.put("result", "2");
			} else {
				if (password.equals(uBean.getPassword())) {
					jsonObject.put("result", "1");
				} else {
					jsonObject.put("result", "0");
				}
			}
			out.print(jsonObject.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
