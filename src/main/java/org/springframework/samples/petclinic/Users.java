package org.springframework.samples.petclinic;

import java.util.ArrayList;
import java.util.List;

/**
 * Jednoduchý JavaBean objekt reprezentující seznam uživatelù. 
 * Využit pøedevším pro úèely odkázání na "users" ve view.
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
