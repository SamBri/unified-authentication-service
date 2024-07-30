package com.nothing.security.dto;

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
public class UserDto {

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
