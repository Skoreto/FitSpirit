package org.springframework.samples.petclinic.validation;

import org.springframework.samples.petclinic.User;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

public class UserValidator {

	public void validate(User User, Errors errors) {
		if (!StringUtils.hasLength(User.getFirstName())) {
			errors.rejectValue("firstName", "required", "required");
		}
		if (!StringUtils.hasLength(User.getLastName())) {
			errors.rejectValue("lastName", "required", "required");
		}
		if (!StringUtils.hasLength(User.getMail())) {
			errors.rejectValue("mail", "required", "required");
		}
		if (!StringUtils.hasLength(User.getPassword())) {
			errors.rejectValue("password", "required", "required");
		}
		
	}
	
}
