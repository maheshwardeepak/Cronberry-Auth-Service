package com.cronberry.securityconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cronberry.exception_handling.CronberryException;
import com.cronberry.model.Users;
import com.cronberry.service.UserServiceimpl;

@Service
public class CustomUserDetailService implements UserDetailsService {
	
	@Lazy
	@Autowired
	private UserServiceimpl userservice;
		


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		System.out.println("call loadUserByusername from UserDetail  &&&&&&&");


	Users user = userservice.fetchUserDetailByEmail(username);
	
	
	
		CustomUserDetail userdetail = null;

		if (user != null) {

			userdetail = new CustomUserDetail();
			userdetail.setUser(user);

		} else {
			throw new CronberryException("user does not exist : " + username);
		}

		return userdetail;
	}

}
