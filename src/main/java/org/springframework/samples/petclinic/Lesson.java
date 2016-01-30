package org.springframework.samples.petclinic;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="LESSONS")
public class Lesson extends BaseEntity {

	private Timestamp startTime;
	
	private Timestamp endTime;
	
	private ActivityType activityType;
	
	private Room room;
	
	// P�vodn� kapacita vypsan� lekce.
	private int originalCapacity;
	
	// originalCapacity - po�et zarezervovan�ch m�st
	private int actualCapacity;
	
	private User instructor;
	
	// Pozn�mka k dan� lekci.
	private String description;
	
	// Pro rozli�en� prob�hl�ch (�i jinak neaktiv�ch) / aktivn�ch lekc�.
	private boolean isActive;
	
	// Pomocn� vlastnost pro zamezen� klientovi ve v�cen�sobn� rezervaci tyt� lekce. Nen� zaznamen�na v datab�zi.
	private boolean isReserved = false;
	
	@Column(name="START_TIME")
	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	@Column(name="END_TIME")
	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	@ManyToOne(fetch=FetchType.EAGER)
	public ActivityType getActivityType() {
		return activityType;
	}

	public void setActivityType(ActivityType activityType) {
		this.activityType = activityType;
	}

	@ManyToOne(fetch=FetchType.EAGER)
	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	@Column(name="ORIGINAL_CAPACITY")
	public int getOriginalCapacity() {
		return originalCapacity;
	}

	public void setOriginalCapacity(int originalCapacity) {
		this.originalCapacity = originalCapacity;
	}

	@Column(name="ACTUAL_CAPACITY")
	public int getActualCapacity() {
		return actualCapacity;
	}

	public void setActualCapacity(int actualCapacity) {
		this.actualCapacity = actualCapacity;
	}

	@ManyToOne(fetch=FetchType.EAGER)
	public User getInstructor() {
		return instructor;
	}

	public void setInstructor(User instructor) {
		this.instructor = instructor;
	}

	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="IS_ACTIVE")
	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	// Atribut by nebylo potreba vest v databazi, ale dochazi ke kolizi s Hibernate.
	@Column(name="IS_RESERVED")
	public boolean isReserved() {
		return isReserved;
	}

	public void setReserved(boolean isReserved) {
		this.isReserved = isReserved;
	}
	
}
