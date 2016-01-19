package org.springframework.samples.petclinic;

public class FitnessCentreUser extends BaseEntity {

	private String firstName;
	
	private String lastName;
	
	private String street;
	
	private String city;
	
	private String postCode;
	
	private String mail;
	
	private String telephone;
	
	// TODO m�l by b�t BigDecimal
	private double credit;
	
	// Pro ��ely informac� pro klienty o instruktorovi.
	private String description;
	
//	private String profilePhotoName;
	
	private String login;
	
	private String password;
	
	private FitnessCentreRole role;
	
	// Rozezn�n� nepotvrzen�ho nebo deaktivovan�ho ��tu.
	private boolean isActive;
	
}
