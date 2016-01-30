package org.springframework.samples.petclinic.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.ActivityType;
import org.springframework.samples.petclinic.FitnessCentre;
import org.springframework.stereotype.Component;

@Component
public class ActivityTypeEditor {

	private @Autowired FitnessCentre fitnessCentre;
	
//	public void setAsText(String text) {
//		ActivityType activityType = this.fitnessCentre.loadActivityType(Long.valueOf(text));
//	}
	
}
