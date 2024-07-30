package com.nothing.security.controller;

import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.hash.Hashing;
import com.nothing.security.db.Resource;
import com.nothing.security.db.User;
import com.nothing.security.dto.ResourceAuthRequestDto;
import com.nothing.security.dto.UserDto;
import com.nothing.security.exceptions.IncorrectUserPasswordException;
import com.nothing.security.exceptions.OAuth2TokenCreationException;
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

	@GetMapping("/ping")
	String ping() {
		return OffsetDateTime.now().toString();
	}

	// confirm crsf test
	@PostMapping("/ping")
	String ping(@RequestBody String in) {
		String out = in;

		return "out:".concat(in).concat(",").concat("timestamp:").concat(OffsetDateTime.now().toString());
	}

	@PostMapping("/users")
	ResponseEntity<RootResponse<User>> createUser(@RequestBody UserDto requestDto) throws UserCreationException {

		User theCreatedUser = resourceAuthenticationService.createUser(requestDto);

		RootResponse<User> apiResponse = new RootResponse<>();

		apiResponse.setCode(201);
		apiResponse.setMessage("user was successfully created!");
		apiResponse.setStatus("success");
		apiResponse.setResponse(theCreatedUser);
		apiResponse.setTimeStamp(OffsetDateTime.now());
		return new ResponseEntity<RootResponse<User>>(apiResponse, HttpStatus.CREATED);
	}

	@PostMapping("/user-token")
	public ResponseEntity<RootResponse<ResourceAuthResponse>> getAccessToken(
			@RequestBody ResourceAuthRequestDto requestDto)
			throws UserNotFoundException, IncorrectUserPasswordException, OAuth2TokenCreationException {

		User theUser = null;

		RootResponse<ResourceAuthResponse> apiResponse = null;

		// userId && password authentication block
		if ((requestDto.getUserId() != null && !requestDto.getUserId().isEmpty())
				&& (requestDto.getPassword() != null && !requestDto.getPassword().isEmpty())) {
			String userId = requestDto.getUserId();
			String theEnteredPassword = requestDto.getPassword();

			// confirm if user exists in the database.
			theUser = resourceAuthenticationService.findUserByUserId(userId);

			String theHashedPassword = Hashing.sha256().hashString(theEnteredPassword, StandardCharsets.UTF_8)
					.toString();

			// confirm the user password
			if (!theUser.getPassword().equals(theHashedPassword)) {

				throw new IncorrectUserPasswordException("incorrect user password entered");
			} else {

				// generate jwt for user with a resource request
				if (requestDto.getResource() != null) {
					Resource theResource = requestDto.getResource();
					OAuth2ServerResponse oauth2ServerResponse = resourceAuthenticationService
							.createOauth2JwtToken(theUser, theResource);

					apiResponse = new RootResponse<>();
					ResourceAuthResponse resourceAuthResponse = new ResourceAuthResponse();
					resourceAuthResponse.setAccessToken(oauth2ServerResponse.getAccessToken());
					resourceAuthResponse.setExpiresAt(oauth2ServerResponse.getExpiresAt()
							.format(DateTimeFormatter.ofPattern("EEEE, dd MMM yyyy hh:mm:ss OOOO")));
					apiResponse.setCode(200);
					apiResponse.setMessage("user access_token was successfully generated!");
					apiResponse.setStatus("success");
					apiResponse.setResponse(resourceAuthResponse);
					apiResponse.setTimeStamp(OffsetDateTime.now());
				} else {

					OAuth2ServerResponse oauth2ServerResponse = resourceAuthenticationService
							.createOauth2JwtToken(theUser);

					apiResponse = new RootResponse<>();
					ResourceAuthResponse resourceAuthResponse = new ResourceAuthResponse();
					resourceAuthResponse.setAccessToken(oauth2ServerResponse.getAccessToken());
					resourceAuthResponse.setExpiresAt(oauth2ServerResponse.getExpiresAt()
							.format(DateTimeFormatter.ofPattern("EEEE, dd MMM yyyy hh:mm:ss OOOO")));
					apiResponse.setCode(200);
					apiResponse.setMessage("user access_token was successfully generated!");
					apiResponse.setStatus("success");
					apiResponse.setResponse(resourceAuthResponse);
					apiResponse.setTimeStamp(OffsetDateTime.now());

				}

			}

		} else {
			throw new UnsupportedOperationException(
					"generation of user access token fails for authentication operation");
		}

		return new ResponseEntity<RootResponse<ResourceAuthResponse>>(apiResponse, HttpStatus.OK);

	}

}
