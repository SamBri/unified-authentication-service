package com.nothing.security.db;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="users")
@Getter
@ToString
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
