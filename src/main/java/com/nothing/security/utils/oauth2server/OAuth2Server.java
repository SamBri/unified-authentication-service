package com.nothing.security.utils.oauth2server;

import java.security.SecureRandom;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nothing.security.db.User;

public class OAuth2Server {

	public static OAuth2ServerResponse createJwt(User userDetails) {

		return null;
	}

	public static void main(String[] args) {
		
		try {
			
			
			// Create an HMAC-protected JWS object with a string payload
			JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256),
			                                    new Payload("Hello, world!"));

			// We need a 256-bit key for HS256 which must be pre-shared
			byte[] sharedKey = new byte[32];
			//new SecureRandom().nextBytes(sharedKey);

			// Apply the HMAC to the JWS object
			jwsObject.sign(new MACSigner(sharedKey));

			// Output in URL-safe format
			System.out.println(jwsObject.serialize());
			
		}catch(Exception e) {
			
			System.err.println("exception :: " + e);
			
		}
		
		
		
		}

}
