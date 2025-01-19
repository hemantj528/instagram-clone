package com.insta.clone.instagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.insta.clone.instagram.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
