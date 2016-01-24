package org.springframework.samples.petclinic;

import java.util.ArrayList;
import java.util.List;

/**
 * Jednoduch� JavaBean objekt reprezentuj�c� seznam instruktor�. 
 * Vyu�it p�edev��m pro ��ely odk�z�n� na "instructors" ve view.
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
