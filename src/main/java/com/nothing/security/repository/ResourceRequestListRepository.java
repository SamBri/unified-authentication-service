package com.nothing.security.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nothing.security.db.ResourceRequestList;
import com.nothing.security.exceptions.OAuth2TokenCreationException;

import lombok.extern.slf4j.Slf4j;

@Repository
public interface ResourceRequestListRepository extends JpaRepository<ResourceRequestList,Long>
{

	default ResourceRequestList saveResourceRequest(ResourceRequestList resourceRequest) throws OAuth2TokenCreationException {
      
		ResourceRequestList resourceRequestList = null;
		try {
			
			 resourceRequestList =	this.save(resourceRequest);		
			 

		} catch(Exception e) {
			
			throw new OAuth2TokenCreationException("access token could not be created.");
		}
		
		return null;
		
		
	};
	
	
}
