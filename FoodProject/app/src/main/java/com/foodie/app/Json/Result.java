package com.foodie.app.Json;

import java.io.Serializable;

public class Result implements Serializable{
	private static final long serialVersionUID = 6288374846131788743L;

	public static final String SUCCESS = "success";
	//success string
	public static final String FAILED = "failed";
	//failed string
	
	private String status = SUCCESS;
	private String tipCode = "";
	private String tipMsg = "";
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		if(status == null  ||  (!status.equals(SUCCESS) &&  !status.equals(FAILED))){
			throw new IllegalArgumentException("status：" + SUCCESS + "、" + FAILED);
		}
		this.status = status;
	}
	public String getTipCode() {
		return tipCode;
	}
	public void setTipCode(String tipCode) {
		this.tipCode = tipCode;
	}
	public String getTipMsg() {
		return tipMsg;
	}
	public void setTipMsg(String tipMsg) {
		this.tipMsg = tipMsg;
	}
}
