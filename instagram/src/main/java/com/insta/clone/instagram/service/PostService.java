package com.insta.clone.instagram.service;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.insta.clone.instagram.entity.Comment;
import com.insta.clone.instagram.entity.Post;
import com.insta.clone.instagram.payload.AllPostDto;
import com.insta.clone.instagram.payload.CommentDto;

public interface PostService {

	public String imageUpload(String path, MultipartFile file, String username, String caption) throws IOException;
	
	public List<AllPostDto> getAllPost(String username);
	
	public ResponseEntity<?> likePost(String username, Long postid);
		
	public ResponseEntity<?> commentPost(String username, Long postid, String comment);
	
	public ResponseEntity<?> deleteComment(Long commentid);
	
	public List<CommentDto> getAllComment(Long postId);

}
