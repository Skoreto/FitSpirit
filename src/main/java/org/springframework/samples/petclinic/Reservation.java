package org.springframework.samples.petclinic;

import java.util.Date;

public class Reservation extends BaseEntity {

	// TODO Vybrat jeden z druh� DateTime.
	private Date reservationTime;
	
	private Lesson lesson;
	
	// Klient jako vlastn�k dan� rezervace.
	private FitnessCentreUser client;
	
	// Pomocn� vlastnost na ozna�en� rezervac�, kter� ji� nelze zru�it z d�vod� vnit�n� politiky fitness centra
	// Nap�. 6 hodin p�ed zah�jen�m lekce klient ji� nem��e zru�it rezervaci lekce.
	private boolean isCancellable;
}
