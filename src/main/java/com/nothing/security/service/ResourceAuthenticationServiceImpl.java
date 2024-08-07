package com.nothing.security.service;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.hash.Hashing;
import com.nothing.security.db.Resource;
import com.nothing.security.db.User;
import com.nothing.security.dto.UserDto;
import com.nothing.security.exceptions.OAuth2TokenCreationException;
import com.nothing.security.exceptions.UserCreationException;
import com.nothing.security.exceptions.UserNotFoundException;
import com.nothing.security.repository.ResourceRequestListRepository;
import com.nothing.security.repository.UserRepository;
import com.nothing.security.utils.oauth2server.OAuth2Server;
import com.nothing.security.utils.oauth2server.OAuth2ServerResponse;

@Service
public class ResourceAuthenticationServiceImpl implements ResourceAuthenticationService {

	@Autowired
	ResourceRequestListRepository requestListRepository;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	OAuth2Server oauth2Server;

	@Override
	public User createUser(UserDto requestDto) throws UserCreationException {

		User theCreatedUser = new User();
		theCreatedUser.setEmailAddress(requestDto.getEmailAddress());
		theCreatedUser.setFirstName(requestDto.getFirstName());
		theCreatedUser.setLastName(requestDto.getLastName());
		theCreatedUser.setOtherNames(requestDto.getOtherNames());
		theCreatedUser.setFullName((requestDto.getOtherNames() != null && !requestDto.getOtherNames().isEmpty())
				? requestDto.getFirstName().concat(" ").concat(requestDto.getOtherNames()).concat(" ")
						.concat(requestDto.getLastName())
				: requestDto.getFirstName().concat(" ").concat(requestDto.getLastName()));
		theCreatedUser.setMobileNumber(requestDto.getMobileNumber());
		theCreatedUser.setNationalId(requestDto.getNationalId());
		theCreatedUser.setUserId(requestDto.getFirstName().concat(".").concat(requestDto.getLastName()).toLowerCase());
		theCreatedUser.setUserName(requestDto.getUserName());
		theCreatedUser.setUserRefNo(UUID.randomUUID().toString());
		theCreatedUser
				.setPassword(Hashing.sha256().hashString(requestDto.getPassword(), StandardCharsets.UTF_8).toString());

		try {
			theCreatedUser = userRepository.save(theCreatedUser);
		} catch (Exception e) {

			throw new UserCreationException("user could not be created");
		}

		return theCreatedUser;
	}

	
	@Override
	public OAuth2ServerResponse createOauth2JwtToken(User userDetails) throws OAuth2TokenCreationException {

		OAuth2ServerResponse oauth2ServerResponse = oauth2Server.createJwt(userDetails);

		return oauth2ServerResponse;
	}

	
	
	@Override
	public OAuth2ServerResponse createOauth2JwtToken(User userDetails, Resource resourceDetails) throws OAuth2TokenCreationException {

		OAuth2ServerResponse oauth2ServerResponse = oauth2Server.createJwt(userDetails,resourceDetails);

		return oauth2ServerResponse;
	}
	
	
	@Override
	public User findUserByUserId(String userId) throws UserNotFoundException {

		User tempUser = userRepository.findUserByUserId(userId);

		if (tempUser == null) {
			throw new UserNotFoundException("user does not exist in the database.");
		}

		return tempUser;
	}

}
