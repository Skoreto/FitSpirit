package org.springframework.samples.petclinic;

import java.sql.Timestamp;

public class Reservation extends BaseEntity {

	// TODO Vybrat jeden z druhù DateTime.
	private Timestamp reservationTime;
	
	private Lesson lesson;
	
	// Klient jako vlastník dané rezervace.
	private User client;
	
	// Pomocná vlastnost na oznaèení rezervací, které již nelze zrušit z dùvodù vnitøní politiky fitness centra
	// Napø. 6 hodin pøed zahájením lekce klient již nemùže zrušit rezervaci lekce.
	private boolean isCancellable;
}
