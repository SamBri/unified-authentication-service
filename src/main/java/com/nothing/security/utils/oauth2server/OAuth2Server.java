package com.nothing.security.utils.oauth2server;

import java.time.OffsetDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.HashMap;
import java.util.Map;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nothing.security.db.User;

public class OAuth2Server {

	public static OAuth2ServerResponse createJwt(User userDetails) {

		    OAuth2ServerResponse oAuth2ServerResponse = null;
		try {
			// Create an HMAC-protected JWS object with a string payload
			
			Map<String,Object> claimsSet = new HashMap<>();

			OffsetDateTime now = OffsetDateTime.now();
			OffsetDateTime issuedAt = now;
			OffsetDateTime expiresAt = now.plusSeconds(60);
			claimsSet.put("sub", "resource_access".toUpperCase());
			claimsSet.put("iss", "UNIFIED_AUTHENTICATION_SERVICE_OAUTH2SERVER");
			claimsSet.put("exp",  expiresAt.toEpochSecond());
			claimsSet.put("iat", issuedAt.toEpochSecond());
			claimsSet.put("userId", userDetails.getUserId());
			JWSObject jwsObject = 
					new JWSObject(
					new JWSHeader(JWSAlgorithm.HS256), 
					new Payload(claimsSet)
					);

			// We need a 256-bit key for HS256 which must be pre-shared
			byte[] sharedKey = new byte[32];

			// Apply the HMAC to the JWS object
			jwsObject.sign(new MACSigner(sharedKey));

			// Output in URL-safe format
			System.out.println(jwsObject.serialize());
			
			oAuth2ServerResponse = new OAuth2ServerResponse();
			oAuth2ServerResponse.setAccessToken(jwsObject.serialize());
			oAuth2ServerResponse.setExpiresAt(expiresAt);


		} catch (Exception e) {

			System.err.println("exception :: " + e);

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
