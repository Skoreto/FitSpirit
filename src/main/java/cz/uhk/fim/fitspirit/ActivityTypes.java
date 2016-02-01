package cz.uhk.fim.fitspirit;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple JavaBean domain object representing a list of activity types. Mostly here to be used for the 'activityTypes'
 * @author Tomas Skorepa
 */
public class ActivityTypes {

	private List<ActivityType> activityTypes;
	
	public List<ActivityType> getActivityTypeList() {
		if (activityTypes == null) {
			activityTypes = new ArrayList<ActivityType>();
		}
		return activityTypes;
	}
}
