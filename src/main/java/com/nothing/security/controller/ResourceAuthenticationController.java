package com.nothing.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nothing.security.db.User;
import com.nothing.security.dto.ResourceAuthRequestDto;
import com.nothing.security.dto.UserDto;
import com.nothing.security.response.ResourceAuthResponse;
import com.nothing.security.response.RootResponse;
import com.nothing.security.service.ResourceAuthenticationServiceImpl;

@RequestMapping("/resource-authentication")
@RestController
public class ResourceAuthenticationController {

	@Autowired
	ResourceAuthenticationServiceImpl resourceAuthenticationService;

	@PostMapping("/users")
	ResponseEntity<RootResponse<User>> createUser(@RequestBody UserDto requestDto) {

		User theCreatedUser = resourceAuthenticationService.createUser(requestDto);

		return null;

	}

	@PostMapping("/user-token")
	public ResponseEntity<RootResponse<ResourceAuthResponse>> getAccessToken(
			@RequestBody ResourceAuthRequestDto requestDto) {

		return null;

	}

}
