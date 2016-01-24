package org.springframework.samples.petclinic.validation;

import org.springframework.samples.petclinic.User;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

public class FitnessCentreUserValidator {

	public void validate(User fitnessCentreUser, Errors errors) {
		if (!StringUtils.hasLength(fitnessCentreUser.getFirstName())) {
			errors.rejectValue("firstName", "required", "required");
		}
		if (!StringUtils.hasLength(fitnessCentreUser.getLastName())) {
			errors.rejectValue("lastName", "required", "required");
		}
		if (!StringUtils.hasLength(fitnessCentreUser.getMail())) {
			errors.rejectValue("mail", "required", "required");
		}
		if (!StringUtils.hasLength(fitnessCentreUser.getPassword())) {
			errors.rejectValue("password", "required", "required");
		}
		
	}
	
}
