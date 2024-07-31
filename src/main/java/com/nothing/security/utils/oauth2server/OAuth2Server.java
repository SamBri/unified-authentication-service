package com.nothing.security.utils.oauth2server;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nothing.security.db.Resource;
import com.nothing.security.db.ResourceRequestList;
import com.nothing.security.db.User;
import com.nothing.security.exceptions.OAuth2TokenCreationException;
import com.nothing.security.repository.ResourceRequestListRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OAuth2Server {

	@Autowired
	private  ResourceRequestListRepository resourceRequestListRepository;

	public  OAuth2ServerResponse createJwt(User userDetails) throws OAuth2TokenCreationException {

		OAuth2ServerResponse oAuth2ServerResponse = null;
		try {
			// Create an HMAC-protected JWS object with a string payload

			Map<String, Object> claimsSet = new HashMap<>();

			OffsetDateTime now = OffsetDateTime.now();
			OffsetDateTime issuedAt = now;
			OffsetDateTime expiresAt = now.plusSeconds(360);
			log.info("expiresAt :"+ expiresAt.toString());
			claimsSet.put("sub", "user_jwt_request".toUpperCase());
			claimsSet.put("iss", "UNIFIED_AUTHENTICATION_SERVICE_OAUTH2SERVER");
			claimsSet.put("exp", expiresAt.toEpochSecond());
			claimsSet.put("iat", issuedAt.toEpochSecond());
			claimsSet.put("userId", userDetails.getUserId());
			JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload(claimsSet));

			// We need a 256-bit key for HS256 which must be pre-shared
			byte[] sharedKey = new byte[32];
			String secretKey = "abcdefghijklmnopqrstuvxwyz12345678";

			System.out.println("secret key in bytes length:" + secretKey.getBytes().length);
			System.out.println("shared key in bytes length:" + sharedKey.length);

			// Apply the HMAC to the JWS object
			jwsObject.sign(new MACSigner(secretKey));
			
			

			ResourceRequestList resourceRequest = new ResourceRequestList();

			resourceRequest.setUserId(userDetails.getUserId());
			resourceRequest.setListId(UUID.randomUUID());
			resourceRequest.setResourceId("");
			
			String accessToken = jwsObject.serialize();
			resourceRequest.setUserAccessToken(accessToken);

			resourceRequestListRepository.saveResourceRequest(resourceRequest);
		

			oAuth2ServerResponse = new OAuth2ServerResponse();
			oAuth2ServerResponse.setAccessToken(accessToken);
			oAuth2ServerResponse.setExpiresAt(expiresAt);

		} catch (Exception e) {
			
			log.error("exception ",e);

			throw new OAuth2TokenCreationException("access token could not be generated");

		}

		return oAuth2ServerResponse;
	}

	public  OAuth2ServerResponse createJwt(User userDetails, Resource resourceDetails)
			throws OAuth2TokenCreationException {

		OAuth2ServerResponse oAuth2ServerResponse = null;
		try {
			// Create an HMAC-protected JWS object with a string payload

			Map<String, Object> claimsSet = new HashMap<>();

			OffsetDateTime now = OffsetDateTime.now();
			OffsetDateTime issuedAt = now;
			OffsetDateTime expiresAt = now.plusSeconds(360);
			log.info("expiresAt :"+ expiresAt.toString());
			claimsSet.put("sub", "resource_access_jwt_request".toUpperCase());
			claimsSet.put("iss", "UNIFIED_AUTHENTICATION_SERVICE_OAUTH2SERVER");
			claimsSet.put("exp", expiresAt.toEpochSecond());
			claimsSet.put("iat", issuedAt.toEpochSecond());
			claimsSet.put("userId", userDetails.getUserId());
			claimsSet.put("resourceId", resourceDetails.getResourceId()); // resource request type is resourceId
			JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload(claimsSet));

			// We need a 256-bit key for HS256 which must be pre-shared
			byte[] sharedKey = new byte[32];
			String secretKey = "abcdefghijklmnopqrstuvxwyz12345678";

			System.out.println("secret key in bytes length:" + secretKey.getBytes().length);
			System.out.println("shared key in bytes length:" + sharedKey.length);

			ResourceRequestList resourceRequest = new ResourceRequestList();

			resourceRequest.setUserId(userDetails.getUserId());
			resourceRequest.setListId(UUID.randomUUID());
			resourceRequest.setResourceId(resourceDetails.getResourceId());

			
			jwsObject.sign(new MACSigner(secretKey));

			String accessToken = jwsObject.serialize();
			resourceRequest.setUserAccessToken(accessToken);

			resourceRequestListRepository.saveResourceRequest(resourceRequest);


			// Output in URL-safe format
//			System.out.println(jwsObject.serialize());

			
			oAuth2ServerResponse = new OAuth2ServerResponse();
			oAuth2ServerResponse.setAccessToken(accessToken);
			oAuth2ServerResponse.setExpiresAt(expiresAt);

		} catch (Exception e) {

			
			log.error("exception ",e);
			
			throw new OAuth2TokenCreationException("access token could not be generated");

		}
		return oAuth2ServerResponse;
	}

	public static void main(String[] args) {

		try {

			// Create an HMAC-protected JWS object with a string payload
			JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload("Hello, world!"));

			// We need a 256-bit key for HS256 which must be pre-shared
			byte[] sharedKey = new byte[32];
			// new SecureRandom().nextBytes(sharedKey);

			// Apply the HMAC to the JWS object
			jwsObject.sign(new MACSigner(sharedKey));

			// Output in URL-safe format
			System.out.println(jwsObject.serialize());

		} catch (Exception e) {

			System.err.println("exception :: " + e);

		}

	}

}
