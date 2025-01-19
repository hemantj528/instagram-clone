package com.insta.clone.instagram.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.insta.clone.instagram.entity.Post;
import com.insta.clone.instagram.entity.User;
import com.insta.clone.instagram.exception.AuthorizationException;
import com.insta.clone.instagram.exception.UserNotFoundException;
import com.insta.clone.instagram.exception.UserWithGivenUserNameIsAlreadyRegistered;
import com.insta.clone.instagram.exception.WrongUserNameOrPassword;
import com.insta.clone.instagram.jwt.JWTService;
import com.insta.clone.instagram.model.LoginDetails;
import com.insta.clone.instagram.model.LoginResponse;
import com.insta.clone.instagram.model.SignUpDetails;
import com.insta.clone.instagram.model.UserDto;
import com.insta.clone.instagram.payload.AllPostDto;
import com.insta.clone.instagram.payload.UserPostDto;
import com.insta.clone.instagram.repository.PostRepository;
import com.insta.clone.instagram.repository.UserRepository;
import com.insta.clone.instagram.service.FileService;
import com.insta.clone.instagram.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;

	@Autowired
	private JWTService jwtService;

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private FileService fileService;

	@Autowired
	private ModelMapper modelMapper;

	@Value("${base.url}")
	private String baseUrl;
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

	public ResponseEntity<?> userSignUp(SignUpDetails singUpDetails) {
		User userData = userRepository.findByUserName(singUpDetails.getUserName());
		if (userData == null) {
			User user = modelMapper.map(singUpDetails, User.class);
			user.setPassword(encoder.encode(user.getPassword()));
			userRepository.save(user);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		throw new UserWithGivenUserNameIsAlreadyRegistered("User already present");
	}

	public LoginResponse verify(LoginDetails loginDetails) throws AuthorizationException {
		try {
			Authentication authentication = authManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginDetails.getUserName(), loginDetails.getPassword()));
			LoginResponse loginResponse = new LoginResponse();
			loginResponse.setUserName(loginDetails.getUserName());
			if (authentication.isAuthenticated()) {
				String token = jwtService.generateToken(loginDetails.getUserName());
				loginResponse.setJwtToken(token);

			}
			return loginResponse;
		} catch (BadCredentialsException e) {
			throw new AuthorizationException("INVALID CREDENTIALS");
		}
	}

	
	public UserDto profileUpdate(String path, MultipartFile file, UserDto userDto, String oldusername) throws IOException {	
		if(!oldusername.equals(userDto.getUserName())) {
			User userexist = userRepository.findByUserName(userDto.getUserName());
			if(userexist!=null) {
				throw new UserWithGivenUserNameIsAlreadyRegistered("User Name is already taken");
			}
		}
		User user = userRepository.findByUserName(oldusername);
		user.setUserName(userDto.getUserName());
		if(user==null) {
			throw new UserNotFoundException("User not found with given username");
		}
		String fileName = user.getImageName();
		if(file!=null) {
			
			fileName = fileService.uploadFile(path, file, userDto.getUserName());
			user.setImageName(fileName);
		}
		user.setBio(userDto.getBio());
		user.setEmail(userDto.getEmail());
		UserDto dto = modelMapper.map(userRepository.save(user), UserDto.class);
		dto.setImageName(baseUrl+"profile/"+fileName);
		return dto;
		
	}

	@Override
	public UserDto getProfileData(String username, String path) {
		User user = userRepository.findByUserName(username);
		if(user==null) {
			throw new UserNotFoundException("User not found with given username "+ username);
		}
		UserDto userDto = new UserDto();
		userDto.setUserName(user.getUserName());
		userDto.setId(user.getId());
		userDto.setBio(user.getBio());
		userDto.setEmail(user.getEmail());
		userDto.setImageName(baseUrl+"profile/"+user.getImageName());
		userDto.setNoOfPosts(user.getPosts().size());
		List<AllPostDto> allPostDto = user.getPosts().stream().map(post -> {
			
			 // create allpostdto
			 AllPostDto postdto = new AllPostDto();
			 postdto.setId(post.getId());
			 postdto.setCaption(post.getCaption());
			 postdto.setImageName(baseUrl + post.getImageName());
			 Long diff = post.getCreatedAt().getTime()- new Date().getTime();
			 
			 if(((diff/(1000 * 60)) % 60 )<60) {
				 postdto.setCreatedAt(post.getCreatedAt().getMinutes() + " minutes ");
			 }else if(((diff/(1000 * 60 * 60)) % 24)<25) {
				 postdto.setCreatedAt(post.getCreatedAt().getHours() + " hours ");
			 }else if(((diff/(1000 * 60 * 60 * 24)) % 365)<366) {
				 postdto.setCreatedAt(post.getCreatedAt().getDay() + " days ");
			 }else {
				 postdto.setCreatedAt((diff/(1000l * 60 * 60 * 24 * 365)) + " years ");
			 }
			 UserPostDto dto = new UserPostDto();
			 dto.setId(post.getUser().getId());
			 dto.setImageName(baseUrl+"profile/" +post.getUser().getImageName());
			 dto.setUsername(post.getUser().getUserName());
			 postdto.setUser(dto);
			 postdto.setNoOfComments(post.getComment().size());
			 postdto.setNoOfLikes(post.getLikes().size());
			 User user1 = userRepository.findByUserName(username);
			 boolean isliked = post.getLikes().contains(user1);
			 postdto.setLiked(isliked);
			 return postdto;
			 
		 }).collect(Collectors.toList());
		userDto.setNoOfFollowers(user.getFollowers().size());
		userDto.setNoOfFollowings(user.getFollowing().size());
		userDto.setPosts(allPostDto);
		
		return userDto;
		
	}

	@Override
	public ResponseEntity<?> followUser(String currentuser, String targetuser) {
		// TODO Auto-generated method stub
		User cuser = userRepository.findByUserName(currentuser);
		if(cuser==null) {
			throw new UserNotFoundException("Current User not found with given username " + currentuser);
		}User tuser = userRepository.findByUserName(targetuser);
		if(tuser==null) {
			throw new UserNotFoundException("Target User not found with given username "+targetuser);
		}
		if(cuser.getFollowing().contains(tuser)) {
			cuser.getFollowing().remove(tuser);
			tuser.getFollowers().remove(cuser);
		}else {
			cuser.getFollowing().add(tuser);
			tuser.getFollowers().add(cuser);
		}
		
		
		userRepository.save(cuser);
		userRepository.save(tuser);
		return new ResponseEntity<>(HttpStatus.OK);
	}


	
	

}
