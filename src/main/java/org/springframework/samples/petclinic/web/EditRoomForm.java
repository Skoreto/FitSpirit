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
	 * Metoda pro smaz�n� m�stnosti dle zadan�ho id.
	 * Nejprve odstran� fotografii m�stnosti ze slo�ky roomImages, pot� vyma�e z�znam z datab�ze.
	 */
	@RequestMapping(value="/rooms/{roomId}/delete")
	public String deleteRoom(@PathVariable int roomId) {
		Room room = this.clinic.loadRoom(roomId);
		String illustrationImageName = room.getIllustrationImageName();	
		String illustrationImagePath = myProjectPath + File.separator + "roomImages" + File.separator + illustrationImageName;
		File illustrationImage = new File(illustrationImagePath);
		
		// Sma�e soubor a z�rove� vrac� bool, jestli byl soubor �sp�n� smaz�n.
		if (illustrationImage.delete()) {
			logger.info("Smaz�n obr�zek: " + illustrationImageName);
		} else {
			logger.info("Nezda�ilo se smazat obr�zek: " + illustrationImageName + " z um�st�n� " + illustrationImagePath);
		}
				
		this.clinic.deleteRoom(roomId);
		return "redirect:/rooms/index";	
	}
		
}
