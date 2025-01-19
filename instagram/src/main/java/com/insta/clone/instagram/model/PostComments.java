package com.insta.clone.instagram.model;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.insta.clone.instagram.entity.Post;
import com.insta.clone.instagram.entity.User;
import com.insta.clone.instagram.payload.UserPostDto;

public class PostComments {

private Long id;
	
	private String text;
	private UserPostDto user;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public UserPostDto getUser() {
		return user;
	}
	public void setUser(UserPostDto user) {
		this.user = user;
	}
	
	
}
