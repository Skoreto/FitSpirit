package org.springframework.samples.petclinic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

/**
 * Simple JavaBean business object representing a pet.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 */
@Entity
@Table(name="PETS")
public class Pet extends NamedEntity {

	private Date birthDate;

	private PetType type;

	private Owner owner;

	private Set<Visit> visits;


	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@Column(name="BIRTH_DATE")
	@Temporal(TemporalType.DATE)
	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setType(PetType type) {
		this.type = type;
	}

	@ManyToOne(fetch=FetchType.EAGER)
	public PetType getType() {
		return this.type;
	}

	protected void setOwner(Owner owner) {
		this.owner = owner;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	public Owner getOwner() {
		return this.owner;
	}

	protected void setVisitsInternal(Set<Visit> visits) {
		this.visits = visits;
	}

	@OneToMany(mappedBy="pet",cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	protected Set<Visit> getVisitsInternal() {
		if (this.visits == null) {
			this.visits = new HashSet<Visit>();
		}
		return this.visits;
	}

	@Transient
	public List<Visit> getVisits() {
		List<Visit> sortedVisits = new ArrayList<Visit>(getVisitsInternal());
		PropertyComparator.sort(sortedVisits, new MutableSortDefinition("date", false, false));
		return Collections.unmodifiableList(sortedVisits);
	}

	public void addVisit(Visit visit) {
		getVisitsInternal().add(visit);
		visit.setPet(this);
	}

}
