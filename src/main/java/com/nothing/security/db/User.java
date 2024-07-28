package com.nothing.security.db;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	
	private Long id;
	
	private UUID userId;
	
	private String userName;
	
	private String emailAddress;
	
	private String mobileNumber;
	
	private String password;
	
	
	

}
