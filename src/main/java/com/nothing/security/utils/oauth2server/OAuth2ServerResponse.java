package com.nothing.security.utils.oauth2server;

import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OAuth2ServerResponse {

	private String accessToken;
	
	private OffsetDateTime expiresAt;

}
