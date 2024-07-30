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
	
	private String userId;
	
	private String userRefNo;
	
	private String userName;
	
	private String firstName;
	
	private String lastName;
	
	private String otherNames;
	
	private String nationalId;
	
	private String fullName;
	
	private String emailAddress;
	
	private String mobileNumber;
	
	private String password;
	
	
	

}
