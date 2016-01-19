package org.springframework.samples.petclinic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.core.style.ToStringCreator;

/**
 * Simple JavaBean domain object representing an owner.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 */
@Entity
@Table(name="OWNERS")
public class Owner extends Person {

	private String address;

	private String city;
      
	private String telephone;

	private String email;
	
	private Set<Pet> pets;


	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	protected void setPetsInternal(Set<Pet> pets) {
		this.pets = pets;
	}

	@OneToMany(mappedBy="owner", fetch=FetchType.EAGER)
	protected Set<Pet> getPetsInternal() {
		if (this.pets == null) {
			this.pets = new HashSet<Pet>();
		}
		return this.pets;
	}

	@Transient
	public List<Pet> getPets() {
		List<Pet> sortedPets = new ArrayList<Pet>(getPetsInternal());
		PropertyComparator.sort(sortedPets, new MutableSortDefinition("name", true, true));
		return Collections.unmodifiableList(sortedPets);
	}

	public void addPet(Pet pet) {
		getPetsInternal().add(pet);
		pet.setOwner(this);
	}

	/**
	 * Return the Pet with the given name, or null if none found for this Owner.
	 *
	 * @param name to test
	 * @return true if pet name is already in use
	 */
	public Pet getPet(String name) {
		return getPet(name, false);
	}

	/**
	 * Return the Pet with the given name, or null if none found for this Owner.
	 *
	 * @param name to test
	 * @return true if pet name is already in use
	 */
	public Pet getPet(String name, boolean ignoreNew) {
		name = name.toLowerCase();
		for (Pet pet : getPetsInternal()) {
			if (!ignoreNew || !pet.isNew()) {
				String compName = pet.getName();
				compName = compName.toLowerCase();
				if (compName.equals(name)) {
					return pet;
				}
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return new ToStringCreator(this)

		.append("id", this.getId())

		.append("new", this.isNew())

		.append("lastName", this.getLastName())

		.append("firstName", this.getFirstName())

		.append("address", this.address)

		.append("city", this.city)

		.append("telephone", this.telephone)

		.append("email", this.email)
		
		.toString();
	}
}