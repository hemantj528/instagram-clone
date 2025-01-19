package com.insta.clone.instagram.service;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.insta.clone.instagram.exception.AuthorizationException;
import com.insta.clone.instagram.model.LoginDetails;
import com.insta.clone.instagram.model.LoginResponse;
import com.insta.clone.instagram.model.SignUpDetails;
import com.insta.clone.instagram.model.UserDto;

public interface UserService {

	public ResponseEntity<?> userSignUp(SignUpDetails singUpDetails);
	
	public LoginResponse verify(LoginDetails loginDetails) throws AuthorizationException;
	
	public UserDto profileUpdate(String path, MultipartFile file, UserDto userDto, String oldusername) throws IOException;
	
	public UserDto getProfileData(String username, String path);
	
	public ResponseEntity<?> followUser(String currentuser, String targetuser);



}
