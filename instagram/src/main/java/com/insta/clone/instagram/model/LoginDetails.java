package com.insta.clone.instagram.model;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

public class LoginDetails {

	@NotBlank(message = "username is mandatory")
	private String userName;
	@NotBlank(message = "password is required")
	@Length(min = 8,message = "password should have minimum 8 character")
	private String password;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public LoginDetails(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}
	public LoginDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
