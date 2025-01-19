package com.insta.clone.instagram.model;

public class LoginResponse {

	private String userName;
	private String jwtToken;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getJwtToken() {
		return jwtToken;
	}
	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
	public LoginResponse(String userName, String jwtToken) {
		super();
		this.userName = userName;
		this.jwtToken = jwtToken;
	}
	public LoginResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
