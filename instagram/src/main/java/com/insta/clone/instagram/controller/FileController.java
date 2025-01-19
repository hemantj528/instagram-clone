package com.insta.clone.instagram.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.insta.clone.instagram.constant.APIConstant;
import com.insta.clone.instagram.service.FileService;

@RestController
@RequestMapping("/file")
public class FileController {

	@Autowired
	private FileService fileService;
	
	@Value("${project.post}")
	private String path;
	
	@Value("${project.profile}")
	private String profilePath;
	@PostMapping(APIConstant.FILE_UPLOAD)
	public ResponseEntity<String> uploadFileHandler(@RequestPart MultipartFile file) throws IOException{
		String uploadedFileName = fileService.uploadFile(path, file,"hemantjj");
		return new ResponseEntity<String>(uploadedFileName, HttpStatus.OK);
	}
	
	@GetMapping(APIConstant.FILE_SERVE)
	public void serveFileHandler(@PathVariable String fileName, HttpServletResponse response) throws IOException {
		InputStream resourceFile = fileService.getResourceFile(path, fileName);
		response.setContentType(MediaType.IMAGE_PNG_VALUE);
		StreamUtils.copy(resourceFile, response.getOutputStream());
	}
	
	@GetMapping(APIConstant.FILE_SERVE_PROFILE)
	public void serveFileHandlerForProfile(@PathVariable String fileName, HttpServletResponse response) throws IOException {
		InputStream resourceFile = fileService.getResourceFile(profilePath, fileName);
		response.setContentType(MediaType.IMAGE_PNG_VALUE);
		StreamUtils.copy(resourceFile, response.getOutputStream());
	}
}
