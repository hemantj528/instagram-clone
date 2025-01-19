package com.insta.clone.instagram.model;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

public class SignUpDetails {

	
	@NotBlank(message = "username is mandatory")
	private String userName;
	@NotBlank(message = "email is mandatory")
	private String email;
	@NotBlank(message = "password is mandatory")
	@Length(min = 8,message = "password should have minimum 8 character")
	private String password;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
