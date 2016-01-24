package org.springframework.samples.petclinic;

import java.util.ArrayList;
import java.util.List;

/**
 * Jednoduchý JavaBean objekt reprezentující seznam instruktorù. 
 * Využit pøedevším pro úèely odkázání na "instructors" ve view.
 * @author Tomas Skorepa
 */
public class Instructors {
	
	private List<User> instructors;
	
	public List<User> getInstructorList() {
		if (instructors == null) {
			instructors = new ArrayList<User>();
		}
		return instructors;
	}
}
