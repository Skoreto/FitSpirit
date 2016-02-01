package cz.uhk.fim.fitspirit;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple JavaBean domain object representing a list of reservations. Mostly here to be used for the 'reservations'
 * @author Tomas Skorepa
 */
public class Reservations {

	private List<Reservation> reservations;
	
	public List<Reservation> getReservationList() {
		if (reservations == null) {
			reservations = new ArrayList<Reservation>();
		}
		return reservations;
	}
}
