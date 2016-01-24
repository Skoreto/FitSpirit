package org.springframework.samples.petclinic;

import java.util.ArrayList;
import java.util.List;

/**
 * Jednoduch� JavaBean objekt reprezentuj�c� seznam u�ivatel�. 
 * Vyu�it p�edev��m pro ��ely odk�z�n� na "users" ve view.
 * @author Tomas Skorepa
 */
public class Users {

	private List<User> users;
	
	public List<User> getUserList() {
		if (users == null) {
			users = new ArrayList<User>();
		}
		return users;
	}
}
