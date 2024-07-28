package com.nothing.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nothing.security.db.User;
import com.nothing.security.dto.UserDto;
import com.nothing.security.repository.ResourceRequestListRepository;
import com.nothing.security.repository.UserRepository;
import com.nothing.security.utils.oauth2server.OAuth2Server;
import com.nothing.security.utils.oauth2server.OAuth2ServerResponse;

@Service
public class ResourceAuthenticationService implements ResourceAuthenticationServiceImpl {

	@Autowired
	ResourceRequestListRepository requestListRepository;

	@Autowired
	UserRepository userRepository;
	

	@Override
	public User createUser(UserDto requestDto) {

		User tempUser = new User();

		userRepository.save(tempUser);

		return null;
	}

	@Override
	public OAuth2ServerResponse createOauth2JwtToken(User userDetails) {

		
	  OAuth2ServerResponse oauth2ServerResponse =	OAuth2Server.createJwt(userDetails);
		
		return oauth2ServerResponse;
	}
	
	

}
