package com.nothing.security.service;

import com.nothing.security.db.User;
import com.nothing.security.dto.UserDto;
import com.nothing.security.utils.oauth2server.OAuth2ServerResponse;

public interface ResourceAuthenticationService {
	
	User createUser(UserDto requestDto);
	
	OAuth2ServerResponse createOauth2JwtToken(User userDetails);
}
