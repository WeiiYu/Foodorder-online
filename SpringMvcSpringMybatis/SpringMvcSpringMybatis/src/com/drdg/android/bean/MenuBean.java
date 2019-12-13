package com.drdg.android.bean;

import java.sql.Date;

public class MenuBean {

	String id;
	String foodname;
	String foodprice;
	String foodpicture;
	String foodcalorie;
	String foodtype;
	String resaunt;
	String useremail;
	Date uptime;
	// menu string and date variables 
	
	public Date getUptime() {
		return uptime;
	}
	// set method 
	public void setUptime(Date uptime) {
		this.uptime = uptime;
	}
	// set method
	public String getUseremail() {
		return useremail;
	}
	// set method
	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}
	// set method
	public String getResaunt() {
		return resaunt;
	}
	// set method
	public void setResaunt(String resaunt) {
		this.resaunt = resaunt;
	}
	// set method
	public String getId() {
		return id;
	}
	//get method
	public void setId(String id) {
		this.id = id;
	}
	//get method
	public String getFoodname() {
		return foodname;
	}
	//get method
	public void setFoodname(String foodname) {
		this.foodname = foodname;
	}
	//get method
	public String getFoodprice() {
		return foodprice;
	}
	//get method
	public void setFoodprice(String foodprice) {
		this.foodprice = foodprice;
	}
	//get method
	public String getFoodpicture() {
		return foodpicture;
	}
	//get method
	public void setFoodpicture(String foodpicture) {
		this.foodpicture = foodpicture;
	}
	// set method 
	public String getFoodcalorie() {
		return foodcalorie;
	}
	//get method
	public void setFoodcalorie(String foodcalorie) {
		this.foodcalorie = foodcalorie;
	}
	//get method
	public String getfoodtype() {
		return foodtype;
	}
	//get method
	public void setfoodtype(String foodtype) {
		this.foodtype = foodtype;
	}
	//get method

	
	
    
}
