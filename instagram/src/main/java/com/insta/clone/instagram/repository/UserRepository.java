package com.insta.clone.instagram.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.insta.clone.instagram.entity.User;
import com.insta.clone.instagram.payload.AllPostDto;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{

	User findByUserName(String userName);

	User findByUserNameAndPassword(String userName, String password);

	User findByEmail(String email);

	

	
}
