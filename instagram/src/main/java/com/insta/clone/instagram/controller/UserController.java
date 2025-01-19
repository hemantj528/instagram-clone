package com.insta.clone.instagram.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.insta.clone.instagram.constant.APIConstant;
import com.insta.clone.instagram.entity.User;
import com.insta.clone.instagram.exception.AuthorizationException;
import com.insta.clone.instagram.model.LoginDetails;
import com.insta.clone.instagram.model.LoginResponse;
import com.insta.clone.instagram.model.SignUpDetails;
import com.insta.clone.instagram.model.UserDto;
import com.insta.clone.instagram.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Value("${project.profile}")
	private String profilePath;

	@PostMapping(APIConstant.REGISTER)
	public ResponseEntity<?> userSignUp(@RequestBody SignUpDetails singUpDetails) {
		return userService.userSignUp(singUpDetails);
	}

	@PostMapping(APIConstant.LOGIN)
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginDetails loginDetails) throws AuthorizationException {

        return new ResponseEntity<>(userService.verify(loginDetails),HttpStatus.OK);
    }
	
	@PutMapping(APIConstant.PROFILE_UPDATE)
	public ResponseEntity<UserDto> updateProfile(@PathVariable String oldusername, @RequestPart("imageFile") MultipartFile file, @RequestPart String profileData) throws IOException{
		ObjectMapper mapper = new ObjectMapper();
		UserDto userDto = mapper.readValue(profileData, UserDto.class);
		UserDto profileUpdate = userService.profileUpdate(profilePath, file , userDto, oldusername);
		return new ResponseEntity<UserDto>(profileUpdate,HttpStatus.CREATED);
		
	}

	@GetMapping(APIConstant.GET_PROFILE)
	public ResponseEntity<UserDto> getProfileData(@PathVariable String username){
		UserDto profileData = userService.getProfileData(username, profilePath);
		System.out.println(profileData);
		return new ResponseEntity<UserDto>(profileData,HttpStatus.OK);
	}
	@GetMapping(APIConstant.HEALTH_CHECK)
	public String healthCheck() {
		//Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return "hello";
	}
	
	@PostMapping("/follow/{currentuser}/{targetuser}")
	public ResponseEntity<?> followUser(@PathVariable String currentuser, @PathVariable String targetuser){
		return userService.followUser(currentuser, targetuser);
	}
	
	
	
}
