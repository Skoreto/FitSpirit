package org.springframework.samples.petclinic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="USER_ROLES")
public class UserRole extends BaseEntity {
	
	// Popisek role pouze pro zobrazení infa v aplikaci
	private String roleDescription;
	
	private String identificator;

	@Column(name="ROLE_DESCRIPTION")
	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	@Column(name="IDENTIFICATOR")
	public String getIdentificator() {
		return identificator;
	}

	public void setIdentificator(String identificator) {
		this.identificator = identificator;
	}
	
}
