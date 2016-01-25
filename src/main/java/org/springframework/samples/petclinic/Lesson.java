package org.springframework.samples.petclinic;

import java.util.Date;

public class Lesson extends BaseEntity {

	// TODO Vybrat jeden z typù DateTime
	private Date startTime;
	
	private Date endTime;
	
	private ActivityType activityType;
	
	private Room room;
	
	// Pùvodní kapacita vypsané lekce.
	private int originalCapacity;
	
	// originalCapacity - poèet zarezervovaných míst
	private int actualCapacity;
	
	private User instructor;
	
	// Poznámka k dané lekci.
	private String descriptionLesson;
	
	// Pro rozlišení probìhlých (èi jinak neaktivých) / aktivních lekcí.
	private boolean isActive;
	
}
