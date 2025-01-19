package com.insta.clone.instagram.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.insta.clone.instagram.constant.APIConstant;
import com.insta.clone.instagram.entity.Post;
import com.insta.clone.instagram.payload.AllPostDto;
import com.insta.clone.instagram.payload.CommentDto;
import com.insta.clone.instagram.service.PostService;

@RestController
@CrossOrigin
@RequestMapping("/post")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Value("${base.url}")
	private String baseUrl;
	
	@Value("${project.post}")
	private String postPath;
	
	@PostMapping(APIConstant.IMAGE_UPLOAD)
	public ResponseEntity<String> imageUploadHandler(@RequestPart("imageFile") MultipartFile file, @PathVariable String username, @PathVariable String caption) throws IOException{
		String filename= postService.imageUpload(postPath, file, username, caption);
		return new ResponseEntity<String>(filename, HttpStatus.OK);
	}
	
	@GetMapping(APIConstant.GET_ALL_POSTS)
	public ResponseEntity<List<AllPostDto>> getAllPost(@PathVariable String username){
		List<AllPostDto> allPost = postService.getAllPost(username);
		return new ResponseEntity<>(allPost, HttpStatus.OK);
		
	}
	
	@PostMapping("/like/{username}/{postid}")
	public ResponseEntity<?> likePost(@PathVariable String username, @PathVariable Long postid){
		return postService.likePost(username, postid);
	}
	
	
	@PostMapping("/comment/{username}/{postid}/{comment}")
	public ResponseEntity<?> commentPost(@PathVariable String username, @PathVariable Long postid, @PathVariable String comment){
		return postService.commentPost(username, postid, comment);
	}
	@GetMapping("/comment/get/{postid}")
	public ResponseEntity<?> getComments(@PathVariable Long postid){
		List<CommentDto> allComment = postService.getAllComment(postid);
		return new ResponseEntity<>(allComment, HttpStatus.OK);
	}
	@DeleteMapping("/delete/comment/{commentid}")
	public ResponseEntity<?> commentPost(@PathVariable Long commentid){
		return postService.deleteComment(commentid);
	}
	
	
}
