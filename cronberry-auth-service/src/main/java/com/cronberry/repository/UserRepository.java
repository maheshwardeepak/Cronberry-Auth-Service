package com.cronberry.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cronberry.model.Users;

public interface UserRepository extends JpaRepository<Users, Long> {


	Users findByEmail(String Email);
	
	

}
