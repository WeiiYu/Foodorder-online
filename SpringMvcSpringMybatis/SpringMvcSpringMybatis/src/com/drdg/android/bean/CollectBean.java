package com.drdg.android.bean;

import java.sql.Date;

public class CollectBean {

	String id;
	String useremail;
	String menuid;
	String foodname;
	String foodtype;
	Date collectTime;
	// four string match with database and android
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	// set tmethod 
	public String getUseremail() {
		return useremail;
	}
		// set tmethod 
	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}
		// set tmethod 
	public String getMenuid() {
		return menuid;
	}
	// set tmethod 
	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}
	// set tmethod 
	public String getFoodname() {
		return foodname;
	}
	//get method 
	public void setFoodname(String foodname) {
		this.foodname = foodname;
	}
		//get method 
	public String getFoodtype() {
		return foodtype;
	}
		//get method 
	public void setFoodtype(String foodtype) {
		this.foodtype = foodtype;
	}
		//get method 
	public Date getCollectTime() {
		return collectTime;
	}
		//get method 
	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}
		//get method 
	
    
}
