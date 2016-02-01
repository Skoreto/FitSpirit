package cz.uhk.fim.fitspirit;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple JavaBean domain object representing a list of rooms. Mostly here to be used for the 'rooms'
 *
 * @author Tomas Skorepa
 */
public class Rooms {

	private List<Room> rooms;
	
	public List<Room> getRoomList() {
		if (rooms == null) {
			rooms = new ArrayList<Room>();
		}
		return rooms;
	}
}
