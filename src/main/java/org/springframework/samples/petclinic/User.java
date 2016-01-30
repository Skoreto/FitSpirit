package org.springframework.samples.petclinic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="USERS")
public class User extends BaseEntity {

	private String firstName;
	
	private String lastName;
	
	private String street;
	
	private String city;
	
	private String postcode;
	
	private String mail;
	
	private String telephone;
	
	// TODO mìl by být BigDecimal
	private int credit;
	
	// Pro úèely informací pro klienty o instruktorovi.
	private String description;
	
	private String profilePhotoName;
	
	private int login;
	
	private String password;
	
	private UserRole userRole;
	
	// Rozeznání nepotvrzeného nebo deaktivovaného úètu.
	private boolean isActive;
	
	@Column(name="FIRST_NAME")
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name="LAST_NAME")
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name="STREET")
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@Column(name="CITY")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name="POSTCODE")
	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	
	@Column(name="MAIL")
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	@Column(name="TELEPHONE")
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Column(name="CREDIT")
	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name="PROFILE_PHOTO_NAME")
	public String getProfilePhotoName() {
		return profilePhotoName;
	}

	public void setProfilePhotoName(String profilePhotoName) {
		this.profilePhotoName = profilePhotoName;
	}

	@Column(name="LOGIN")
	public int getLogin() {
		return login;
	}

	public void setLogin(int login) {
		this.login = login;
	}

	@Column(name="PASSWORD")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@ManyToOne(fetch=FetchType.EAGER)
	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	@Column(name="IS_ACTIVE")
	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

}
