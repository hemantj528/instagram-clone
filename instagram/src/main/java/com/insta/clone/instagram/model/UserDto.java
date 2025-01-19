package com.insta.clone.instagram.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.insta.clone.instagram.entity.Post;
import com.insta.clone.instagram.payload.AllPostDto;

public class UserDto implements Serializable{

	private Long id;
	private String userName;
	private String email;
	private String imageName;
	private String bio;
	
	private int noOfPosts;
	private int noOfFollowers;
	private int noOfFollowings;
	
	private List<AllPostDto> posts = new ArrayList<>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public List<AllPostDto> getPosts() {
		return posts;
	}
	public void setPosts(List<AllPostDto> posts) {
		this.posts = posts;
	}
	public int getNoOfPosts() {
		return noOfPosts;
	}
	public void setNoOfPosts(int noOfPosts) {
		this.noOfPosts = noOfPosts;
	}
	public int getNoOfFollowers() {
		return noOfFollowers;
	}
	public void setNoOfFollowers(int noOfFollowers) {
		this.noOfFollowers = noOfFollowers;
	}
	public int getNoOfFollowings() {
		return noOfFollowings;
	}
	public void setNoOfFollowings(int noOfFollowings) {
		this.noOfFollowings = noOfFollowings;
	}
	
	
	
}
