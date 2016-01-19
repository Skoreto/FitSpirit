package org.springframework.samples.petclinic;

public class FitnessCentreUser extends BaseEntity {

	private String firstName;
	
	private String lastName;
	
	private String street;
	
	private String city;
	
	private String postCode;
	
	private String mail;
	
	private String telephone;
	
	// TODO mìl by být BigDecimal
	private double credit;
	
	// Pro úèely informací pro klienty o instruktorovi.
	private String description;
	
//	private String profilePhotoName;
	
	private String login;
	
	private String password;
	
	private FitnessCentreRole role;
	
	// Rozeznání nepotvrzeného nebo deaktivovaného úètu.
	private boolean isActive;
	
}
