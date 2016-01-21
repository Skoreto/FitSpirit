package org.springframework.samples.petclinic.web;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.Clinic;
import org.springframework.samples.petclinic.Room;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("room")
public class EditRoomForm {

	private final Clinic clinic;
	
	private static final Logger logger = LoggerFactory.getLogger(EditRoomForm.class);
	private final String myProjectPath = "C:\\Users\\Tomas\\Documents\\workspace-sts-3.7.2.RELEASE\\petclinic\\src\\main\\webapp\\static\\uploads";
	
	@Autowired
	public EditRoomForm(Clinic clinic) {
		this.clinic = clinic;
	}

	/**
	 * Metoda pro smazání místnosti dle zadaného id.
	 * Nejprve odstraní fotografii místnosti ze složky roomImages, poté vymaže záznam z databáze.
	 */
	@RequestMapping(value="/rooms/{roomId}/delete")
	public String deleteRoom(@PathVariable int roomId) {
		Room room = this.clinic.loadRoom(roomId);
		String illustrationImageName = room.getIllustrationImageName();	
		String illustrationImagePath = myProjectPath + File.separator + "roomImages" + File.separator + illustrationImageName;
		File illustrationImage = new File(illustrationImagePath);
		
		// Smaže soubor a zároveò vrací bool, jestli byl soubor úspìšnì smazán.
		if (illustrationImage.delete()) {
			logger.info("Smazán obrázek: " + illustrationImageName);
		} else {
			logger.info("Nezdaøilo se smazat obrázek: " + illustrationImageName + " z umístìní " + illustrationImagePath);
		}
				
		this.clinic.deleteRoom(roomId);
		return "redirect:/rooms/index";	
	}
		
}
