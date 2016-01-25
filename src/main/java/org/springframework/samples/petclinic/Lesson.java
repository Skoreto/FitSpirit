package org.springframework.samples.petclinic;

import java.util.Date;

public class Lesson extends BaseEntity {

	// TODO Vybrat jeden z typ� DateTime
	private Date startTime;
	
	private Date endTime;
	
	private ActivityType activityType;
	
	private Room room;
	
	// P�vodn� kapacita vypsan� lekce.
	private int originalCapacity;
	
	// originalCapacity - po�et zarezervovan�ch m�st
	private int actualCapacity;
	
	private User instructor;
	
	// Pozn�mka k dan� lekci.
	private String descriptionLesson;
	
	// Pro rozli�en� prob�hl�ch (�i jinak neaktiv�ch) / aktivn�ch lekc�.
	private boolean isActive;
	
}
