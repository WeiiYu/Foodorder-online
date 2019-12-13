package com.drdg.android.bean;

import java.sql.Date;

public class FeedBackBean {

	String id;
	String username;
	String useremail;
	String foodname;
	String foodid;
	String suggestion;
	Date feedbackDate;
	// string and data variable 
	public String getId() {
		return id;
	}
	//set method 
	public void setId(String id) {
		this.id = id;
	}
	//set method 
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	//set method 
	public String getUseremail() {
		return useremail;
	}
	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}
	//set method 
	public String getFoodname() {
		return foodname;
	}
	//set method 
	public void setFoodname(String foodname) {
		this.foodname = foodname;
	}
	//set method 
	public String getFoodid() {
		return foodid;
	}
	// get method
	public void setFoodid(String foodid) {
		this.foodid = foodid;
	}
	// get method
	public String getSuggestion() {
		return suggestion;
	}
	// get method
	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}
	// get method
	public Date getFeedbackDate() {
		return feedbackDate;
	}
	// get method
	public void setFeedbackDate(Date feedbackDate) {
		this.feedbackDate = feedbackDate;
	}
	// get method
	
    
}
