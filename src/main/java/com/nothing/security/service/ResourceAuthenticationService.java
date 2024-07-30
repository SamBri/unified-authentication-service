package com.nothing.security.service;

import com.nothing.security.db.Resource;
import com.nothing.security.db.ResourceRequestList;
import com.nothing.security.db.User;
import com.nothing.security.dto.UserDto;
import com.nothing.security.exceptions.OAuth2TokenCreationException;
import com.nothing.security.exceptions.UserCreationException;
import com.nothing.security.exceptions.UserNotFoundException;
import com.nothing.security.utils.oauth2server.OAuth2ServerResponse;

public interface ResourceAuthenticationService {
	
	User createUser(UserDto requestDto) throws UserCreationException;
	
	OAuth2ServerResponse createOauth2JwtToken(User userDetails,Resource resourceDetails) throws OAuth2TokenCreationException;
	
	OAuth2ServerResponse createOauth2JwtToken(User userDetails) throws OAuth2TokenCreationException;


	User findUserByUserId(String userId) throws UserNotFoundException;
}
