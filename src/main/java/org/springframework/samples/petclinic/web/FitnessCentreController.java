package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.FitnessCentre2;
import org.springframework.samples.petclinic.Rooms;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FitnessCentreController {

//	private final FitnessCentre2 fitnessCentre;
//	
//	@Autowired
//	public FitnessCentreController(FitnessCentre2 fitnessCentre) {
//		this.fitnessCentre = fitnessCentre;
//	}
	
	
//	/**
//	 * Vlastn� handler pro zobrazen� m�stnost�.
//	 * 
//	 * @return ModelMap s atributy modelu pro dan� view
//	 */
//	@RequestMapping("/rooms")
//	public ModelMap roomsHandler() {
//		Rooms rooms = new Rooms();
//		rooms.getRoomList().addAll(this.fitnessCentre.getRooms());
//		return new ModelMap(rooms);
//	}
	
}
