package com.nothing.security.db;

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
@Table(name="resources")
@Getter
@ToString
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Resource {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	protected String resourceType; // mandatory

	protected String resourceName; // mandatory

	protected String resourceCode; // mandatory
	
	protected String resourceId; // mandatory


}
