package com.cronberry.service;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cronberry.model.PasswordChangeRequest;
import com.cronberry.model.Users;

public interface UserService  {
	
	
	//User create
	
	Boolean createuser(Users user) ;
	
//	show users
	
	List<Users> showallusers();
	
	
//	find byid user
	
	Users showByiduser(Long userid);
	
//	delete user by id
	
	void deleteuserbyid(Long userid);

//	user update
	Users updateuserbyid(Users user, Long userid);
	
//	Users fetchUserDetailByEmail
	
	Users fetchUserDetailByEmail(String email);
	
	
//	 change passwordchange 
	Boolean changepassword(PasswordChangeRequest passwordChangeRequest,Principal principal,HttpServletRequest request,HttpServletResponse response);
	
	


}
