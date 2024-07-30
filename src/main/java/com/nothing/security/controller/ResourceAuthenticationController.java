package com.nothing.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nothing.security.db.User;
import com.nothing.security.dto.ResourceAuthRequestDto;
import com.nothing.security.dto.UserDto;
import com.nothing.security.exceptions.IncorrectUserPasswordException;
import com.nothing.security.exceptions.UserCreationException;
import com.nothing.security.exceptions.UserNotFoundException;
import com.nothing.security.response.ResourceAuthResponse;
import com.nothing.security.response.RootResponse;
import com.nothing.security.service.ResourceAuthenticationService;
import com.nothing.security.utils.oauth2server.OAuth2ServerResponse;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/resource-authentication")
@RestController
@Slf4j
public class ResourceAuthenticationController {

	@Autowired
	ResourceAuthenticationService resourceAuthenticationService;

	@PostMapping("/users")
	ResponseEntity<RootResponse<User>> createUser(@RequestBody UserDto requestDto) throws UserCreationException {

		User theCreatedUser = resourceAuthenticationService.createUser(requestDto);

		RootResponse<User> apiResponse = new RootResponse<>();

		apiResponse.setCode(201);
		apiResponse.setMessage("user was successfully created!");
		apiResponse.setStatus("success");
		apiResponse.setResponse(theCreatedUser);
		return new ResponseEntity<RootResponse<User>>(apiResponse, HttpStatus.CREATED);
	}

	@PostMapping("/user-token")
	public ResponseEntity<RootResponse<ResourceAuthResponse>> getAccessToken(
			@RequestBody ResourceAuthRequestDto requestDto)
			throws UserNotFoundException, IncorrectUserPasswordException {

		User theUser = null;

		RootResponse<ResourceAuthResponse> apiResponse = null; 

		// userId && password authentication block
		if ((requestDto.getUserId() != null && requestDto.getUserId().isEmpty())
				&& (requestDto.getPassword() != null && requestDto.getPassword().isEmpty())) {
			String userId = requestDto.getUserId();
			String password = requestDto.getPassword();

			// confirm if user exists in the database.
			theUser = resourceAuthenticationService.findUserByUserId(userId);

			// confirm the user password
			if (!theUser.getPassword().equals(password)) {

				throw new IncorrectUserPasswordException("incorrect user password entered");
			} else {

				OAuth2ServerResponse oauth2ServerResponse = resourceAuthenticationService.createOauth2JwtToken(theUser);

				apiResponse=new RootResponse<>();
				ResourceAuthResponse resourceAuthResponse = new ResourceAuthResponse();
				resourceAuthResponse.setAccessToken(oauth2ServerResponse.getAccessToken());
				resourceAuthResponse.setExpiresAt(oauth2ServerResponse.getExpiresAt().toString());
				apiResponse.setCode(200);
				apiResponse.setMessage("user access_token was successfully generated!");
				apiResponse.setStatus("success");
				apiResponse.setResponse(resourceAuthResponse);
			}

		} else {
			throw new UnsupportedOperationException("generation of user access token fails for authentication operation");
		}
		

		
		return new ResponseEntity<RootResponse<ResourceAuthResponse>>(apiResponse, HttpStatus.OK);

	}

}
