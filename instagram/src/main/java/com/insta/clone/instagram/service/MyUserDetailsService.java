package com.insta.clone.instagram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.insta.clone.instagram.entity.User;
import com.insta.clone.instagram.exception.WrongUserNameOrPassword;
import com.insta.clone.instagram.model.MyUserDetails;
import com.insta.clone.instagram.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//System.out.println("myuserDetailsservice");
		 User user = userRepository.findByUserName(username);
	        if (user == null) {
	            //System.out.println("User Not Found");
	            throw new WrongUserNameOrPassword("user not found");
	        }
	        return new MyUserDetails(user);
	}

}
