package com.capstone.mountain.auth;

import lombok.Data;

import javax.annotation.Generated;
@Data
public class NaverProfile {

	public String resultcode;
	public String message;
	public Response response;

	@Data
	public class Response {
		public String id;
		public String profile_image;
		public String email;
		public String name;
	}
}

