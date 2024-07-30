package com.nothing.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nothing.security.db.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	
	User findUserByUserId(String userId);

}
