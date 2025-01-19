package com.insta.clone.instagram.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

	
	InputStream getResourceFile(String path, String fileName) throws FileNotFoundException;

	String uploadFile(String path, MultipartFile file, String username) throws IOException;
	
}
