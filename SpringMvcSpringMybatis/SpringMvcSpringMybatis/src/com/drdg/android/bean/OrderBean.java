package com.drdg.android.bean;

import java.sql.Date;

public class OrderBean {

	String id;
	String foodname;
	String menuid;
	String foodtype;
	String useremail;
	Date orderTime;
	String orderquantity;
	String orderpayment;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUseremail() {
		return useremail;
	}
	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}
	public String getMenuid() {
		return menuid;
	}
	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}
	public String getFoodname() {
		return foodname;
	}
	public void setFoodname(String foodname) {
		this.foodname = foodname;
	}
	public String getFoodtype() {
		return foodtype;
	}
	public void setFoodtype(String foodtype) {
		this.foodtype = foodtype;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public String getOrderquantity() {
		return orderquantity;
	}
	public void setOrderquantity(String orderquantity) {
		this.orderquantity = orderquantity;
	}
	public String getOrderpayment() {
		return orderpayment;
	}
	public void setOrderpayment(String orderpayment) {
		this.orderpayment = orderpayment;
	}
	
	
	
}
