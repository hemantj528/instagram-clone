package com.insta.clone.instagram.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.insta.clone.instagram.entity.Comment;
import com.insta.clone.instagram.entity.Post;
import com.insta.clone.instagram.entity.User;
import com.insta.clone.instagram.exception.CommentNotFoundException;
import com.insta.clone.instagram.exception.PostNotFoundException;
import com.insta.clone.instagram.exception.ResourceNotFoundException;
import com.insta.clone.instagram.exception.UserNotFoundException;
import com.insta.clone.instagram.payload.AllPostDto;
import com.insta.clone.instagram.payload.CommentDto;
import com.insta.clone.instagram.payload.LikeDto;
import com.insta.clone.instagram.payload.UserPostDto;
import com.insta.clone.instagram.repository.CommentRepository;
import com.insta.clone.instagram.repository.PostRepository;
import com.insta.clone.instagram.repository.UserRepository;
import com.insta.clone.instagram.service.FileService;
import com.insta.clone.instagram.service.PostService;

@Service
public class PostServiceImpl implements PostService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private PostRepository postRepository;
	
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Value("${base.url}")
	private String baseUrl;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public String imageUpload(String path, MultipartFile file, String username, String caption) throws IOException {
		User user = userRepository.findByUserName(username);
		 if(user==null) {
			 throw new UserNotFoundException("user with username "+ username +" not found");
		 }
		 String fileName = fileService.uploadFile(path, file, username);
		 Post post = new Post();
		 post.setCreatedAt(new Date());
		 post.setCaption(caption);
		 post.setImageName(fileName);
		 post.setUser(user);
		 Post save = postRepository.save(post);
		 System.out.println(save);
		 return fileName;
	}

	

	@Override
	public List<AllPostDto> getAllPost(String username) {
		// TODO Auto-generated method stub
		List<Post> posts = postRepository.findAll();
		User user = userRepository.findByUserName(username);
		return posts.stream().map(post -> {
			
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
			 
			 boolean isliked = post.getLikes().contains(user);
			 postdto.setLiked(isliked);
			 postdto.setFollowed(user.getFollowing().contains(post.getUser()));
			 return postdto;
			 
		 }).collect(Collectors.toList());
	}

	@Override
	public ResponseEntity<?> likePost(String username, Long postid) {
		// TODO Auto-generated method stub
		Post post = postRepository.findById(postid).orElseThrow(()-> new PostNotFoundException("Post not found with given id " + postid));
		
		User user = userRepository.findByUserName(username);
		if(user == null) {
			throw new UserNotFoundException("User not found with given username "+ username);
		}
		if(post.getLikes().contains(user)) {
			post.getLikes().remove(user);
		}
		else {
			post.getLikes().add(user);
		}
		postRepository.save(post);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	

	@Override
	public ResponseEntity<?> commentPost(String username, Long postid, String comment) {
		// TODO Auto-generated method stub
Post post = postRepository.findById(postid).orElseThrow(()-> new PostNotFoundException("Post not found with given id " + postid));
		
		User user = userRepository.findByUserName(username);
		if(user == null) {
			throw new UserNotFoundException("User not found with given username "+ username);
		}
		Comment comm = new Comment();
		comm.setPost(post);
		comm.setUser(user);
		comm.setText(comment);
		commentRepository.save(comm);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> deleteComment(Long commentid) {
		// TODO Auto-generated method stub
		Comment comment = commentRepository.findById(commentid).orElseThrow(()-> new CommentNotFoundException("comment not found"));
		commentRepository.delete(comment);
		return new ResponseEntity<>(HttpStatus.OK);
	}



	@Override
	public List<CommentDto> getAllComment(Long postId) {
		Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post not found with given post id "+ postId));
		List<Comment> comments = post.getComment();
		List<CommentDto> collect = comments.stream().map(
				(comment)->{
					CommentDto dto = new CommentDto();
					dto.setId(comment.getId());
					dto.setText(comment.getText());
					dto.setUsername(comment.getUser().getUserName());
					dto.setImageName(baseUrl+"profile/"+comment.getUser().getImageName());
					return dto;
				})
				.collect(Collectors.toList());
		return collect;
		
	}
}
