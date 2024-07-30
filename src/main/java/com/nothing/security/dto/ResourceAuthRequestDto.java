package com.nothing.security.dto;

import java.util.UUID;

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
public class ResourceAuthRequestDto {

	private String userId;

	private String resourceType;

	private String resourceName;

	private String resourceCode;
	
	private String userName;
	
	private String emailAddress;
	
	private String mobileNumber;
	
	
	private String nationalId;
	
	private String password;
	
	private String otp;
	

}
