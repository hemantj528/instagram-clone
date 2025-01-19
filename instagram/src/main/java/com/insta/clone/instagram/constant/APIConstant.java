package com.insta.clone.instagram.constant;

public class APIConstant {

	//File controller
	
	public static final String FILE_UPLOAD ="/upload";

	public static final String FILE_SERVE ="/{fileName}";

	public static final String FILE_SERVE_PROFILE ="/profile/{fileName}";

	
	//User controller
	
	public static final String REGISTER = "/register";
	
	public static final String LOGIN ="/login";
	
	public static final String PROFILE_UPDATE ="/profile/update/{oldusername}";
	
	public static final String GET_PROFILE ="/profile/{username}";
	
	public static final String HEALTH_CHECK ="/health-check";


	//Post controller
	
	public static final String IMAGE_UPLOAD = "/image/upload/{username}/{caption}";

	public static final String GET_ALL_POSTS_BY_USERNAME ="/profile/all/posts/{username}";
	
	public static final String GET_ALL_POSTS ="/all/posts/{username}";


}
