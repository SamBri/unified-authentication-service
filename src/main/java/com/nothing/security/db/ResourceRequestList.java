package com.nothing.security.db;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="resource_request_list")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResourceRequestList {
	
	private Long id;
	
	private UUID listNo;
	
	private UUID userId;
	
	private String resourceType;
	
	private String resourceName;
	
	private String resourceCode;
	
	private String userAccessToken;
	
	
	
    


}
