package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.Room;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RoomsController {

//	private final Room room;
	
	@RequestMapping("/rooms")
	public String roomHandler() {
		return "rooms/create";
		
	}
	
}
