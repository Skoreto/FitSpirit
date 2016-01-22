package org.springframework.samples.petclinic.validation;

import org.springframework.samples.petclinic.ActivityType;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

public class ActivityTypeValidator {

	public void validate(ActivityType activityType, Errors errors) {
		if (!StringUtils.hasLength(activityType.getName())) {
			errors.rejectValue("name", "required", "required");
		}
		
	}
	
	
	
	
	
}
