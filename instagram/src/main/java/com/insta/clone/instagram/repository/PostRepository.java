package com.insta.clone.instagram.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.insta.clone.instagram.entity.Post;
import com.insta.clone.instagram.payload.AllPostDto;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

	@Query(value = "select * from posts where user_name=?", nativeQuery = true)
	List<Post> getAllPost(String username);
}
