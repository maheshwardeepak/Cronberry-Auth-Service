package com.cronberry.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cronberry.exception_handling.CronberryException;
import com.cronberry.jwtutil.JwtHelpar;
import com.cronberry.model.AuthRequest;
import com.cronberry.model.PasswordChangeRequest;
import com.cronberry.model.Users;
import com.cronberry.repository.UserRepository;
import com.cronberry.service.UserServiceimpl;
import com.cronberry.service.constants.BaseManagerImpl;
import com.cronberry.service.constants.UIConstants;

@RestController
public class UsersController {

	@Autowired
	private UserServiceimpl UsersService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtHelpar jwtHelpar;

	@PostMapping("cronberry/sign-up")
	public ResponseEntity<Map<String, Object>> createUsers(@Valid @RequestBody Users user) throws CronberryException {

		UsersService.createuser(user);

		return BaseManagerImpl.sendSuccessResponse("REGISTER SUCCESS " + user.getName());

	}

	@PostMapping("cronberry/sign-in")
	public ResponseEntity<Map<String, Object>> signin(@RequestBody AuthRequest authRequest,
			HttpServletResponse response) throws Exception {

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		} catch (Exception ex) {
			ex.printStackTrace();
throw new CronberryException("INVALID USERNAME/PASSWORD");
		}
		String token = jwtHelpar.generateToken(authRequest.getUsername());
		String username = jwtHelpar.extractUsername(token);

		Users user = UsersService.fetchUserDetailByEmail(username);

		user.setToken(token);
		userRepository.save(user);
		response.setHeader("token", token);
		return BaseManagerImpl.sendSuccessResponse(UIConstants.DATA);

	}

	@GetMapping("cronberry/users-list")
	public ResponseEntity<List<Users>> showAllUsersList() {
		return new ResponseEntity<List<Users>>(UsersService.showallusers(), HttpStatus.OK);

	}

	@GetMapping("cronberry/user/{Usersid}")
	public ResponseEntity<Users> showUsersById(@PathVariable Long Usersid) {
		return new ResponseEntity<Users>(UsersService.showByiduser(Usersid), HttpStatus.OK);
	}

	@DeleteMapping("cronberry/user/{Usersid}")
	public ResponseEntity<String> deleteUsersById(@PathVariable Long Usersid) {
		UsersService.deleteuserbyid(Usersid);
		return new ResponseEntity<String>("delete successfully Users : " + Usersid, HttpStatus.OK);
	}

	@PutMapping("cronberry/user/{Usersid}")
	public ResponseEntity<Users> updateUsersById(@Valid @RequestBody Users Users, @PathVariable Long Usersid) {
		return new ResponseEntity<Users>(UsersService.updateuserbyid(Users, Usersid), HttpStatus.OK);
	}

	@GetMapping("cronberry/user-detail/{email}")
	public ResponseEntity<Users> userDetailByEmail(@PathVariable String email) {
		return new ResponseEntity<Users>(UsersService.fetchUserDetailByEmail(email), HttpStatus.OK);
	}

	@PatchMapping("cronberry/user/change-password")
	public ResponseEntity<Map<String, Object>> changepassword(@RequestBody PasswordChangeRequest passwordChangeRequest,
			Principal principal, HttpServletRequest request, HttpServletResponse response) {
//		return new ResponseEntity<Map<String,Object>>(UsersService.changepassword(passwordChangeRequest,principal,request,response));

		UsersService.changepassword(passwordChangeRequest, principal, request, response);
		return BaseManagerImpl.sendSuccessResponse("PASSWORD CHANGE SUCCESS");

	}

}
