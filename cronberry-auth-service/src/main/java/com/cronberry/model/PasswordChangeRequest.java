package com.cronberry.model;

import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PasswordChangeRequest {
	
	
	private String oldpassword;
	@Size(min = 6, message = "password must be at least 6 characters")
	private String newpassword;
	@Size(min = 6, message = "password must be at least 6 characters")
	private String confirmpassword;
	
		

}
