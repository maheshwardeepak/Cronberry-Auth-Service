package com.cronberry.service;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cronberry.exception_handling.CronberryException;
import com.cronberry.jwtutil.JwtHelpar;
import com.cronberry.model.PasswordChangeRequest;
import com.cronberry.model.Users;
import com.cronberry.repository.UserRepository;
import com.cronberry.service.constants.CronberryEnums;

@Service
public class UserServiceimpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtHelpar jwtHelpar;

	@Override
	public Boolean createuser(Users user) throws CronberryException {

		try {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			userRepository.save(user);

			return true;
		} catch (Exception e) {
			throw new CronberryException("EMAIL ALREADY REGISTER");
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Users> showallusers() {
		try {
			return userRepository.findAll();
		} catch (Exception e) {
			return (List<Users>) new CronberryException("Haven't Any DATA");
		}
	}

	@Override
	public Users showByiduser(Long userid) {
		return userRepository.findById(userid).orElseThrow(() -> new CronberryException("user doesn't exist "));

	}

	@Override
	public void deleteuserbyid(Long userid) throws CronberryException {
		try {

			userRepository.deleteById(userid);

		} catch (Exception e) {
			e.printStackTrace();
			new CronberryException("USER ALREADY DOESn't EXIST");
		}

	}

	@Override
	public Users updateuserbyid(Users user, Long userid) {
		// TODO Auto-generated method stub
		return userRepository.findById(userid).map(users -> {
			users.setName(user.getName());
			users.setEmail(user.getEmail());
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			users.setPhonnumber(user.getPhonnumber());
			users.setDob(user.getDob());
			users.setCountryCodes(user.getCountryCodes());
			users.setUpdated(user.getUpdated());
			users.setRoles(user.getRoles());
			return userRepository.save(users);

		}).orElseGet(() -> {
			user.setUserid(userid);
			return userRepository.save(user);
		});

	}

	@Override
	public Users fetchUserDetailByEmail(String email) {

		Users user = userRepository.findByEmail(email);

		if (user != null && user.getStatus().equals(CronberryEnums.Status.INACTIVE)) {
			return user;
		} else if (user != null && user.getStatus().equals(CronberryEnums.Status.ACTIVE)) {
			return user;
		} else {
			throw new CronberryException("Account IS BLOCKED/DELETED");
		}
	}

	@Override
	public Boolean changepassword(PasswordChangeRequest pchangerequest, Principal principal, HttpServletRequest request,
			HttpServletResponse response) {
		String token = null;

		String confirmpassword = pchangerequest.getConfirmpassword();
		String newpassword = pchangerequest.getNewpassword();
		String oldpassword = pchangerequest.getOldpassword();

		token = request.getHeader("Authorization").substring(7);
		String username = jwtHelpar.extractUsername(token);

		Users currentuser = this.fetchUserDetailByEmail(username);

		if (newpassword.equals(confirmpassword)) {

			if (username != null && passwordEncoder.matches(oldpassword, currentuser.getPassword())) {

				currentuser.setPassword(passwordEncoder.encode(confirmpassword));

				token = jwtHelpar.generateToken(username);
				if (token != null) {
					currentuser.setToken(token);

					userRepository.save(currentuser);
					response.setHeader("token", token);

				} else {

					throw new CronberryException("PASSWORD  NOT CHANGE");
				}

			} else {

				throw new CronberryException("OLD-PASSWORD NOT MATCHED");
			}

		} else {

			throw new CronberryException("CONFIRM PASSWORD NOT MATCHED");
		}

		return true;

	}

}
