package cz.uhk.fim.fitspirit.validation;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

import cz.uhk.fim.fitspirit.ActivityType;

public class ActivityTypeValidator {

	public void validate(ActivityType activityType, Errors errors) {
		if (!StringUtils.hasLength(activityType.getName())) {
			errors.rejectValue("name", "required", "required");
		}
		
	}
	
	
	
	
	
}
