package com.learningtech.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.learningtech.model.Users;

public interface UsersRepository extends JpaRepository<Users, String> {

//	@Query(name = "Select * from user where user_name=:1", nativeQuery = true)
	Optional<Users> findByUserName(String username);
	
	
}
