package org.springframework.samples.petclinic.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.FitnessCentre;
import org.springframework.samples.petclinic.Room;
import org.springframework.samples.petclinic.util.ProjectUtils;
import org.springframework.samples.petclinic.validation.RoomValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

@Controller
@SessionAttributes("room")
public class EditRoomForm {

	private final FitnessCentre fitnessCentre;
	
	private static final Logger logger = LoggerFactory.getLogger(EditRoomForm.class);
	private final String myProjectPath = ProjectUtils.getMyProjectPath();
	
	@Autowired
	public EditRoomForm(FitnessCentre fitnessCentre) {
		this.fitnessCentre = fitnessCentre;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder databinder) {
		databinder.setDisallowedFields("id");
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String setupForm(@PathVariable("roomId") int roomId, Model model) {
		Room room = this.fitnessCentre.loadRoom(roomId);
		model.addAttribute("room", room);
		return "rooms/createForm";
	}
	
	/**
	 * Metoda pro editaci stavajici mistnosti.
	 * Nejprve overi, zda byl vyplnen novy nazev mistnosti. 
	 * - Pokud ne, vrati uzivatele na formular s upozornenim na povinnost vyplnit nazev mistnosti.
	 * Pote overi, zda byla zvolena fotografie mistnosti.
	 * - Pokud ne, vypise do konzole zpravu o nevyplneni pole fotografie a vrati uzivatele na formular.
	 * - Pokud ano, pokusi se smazat puvodni obrazek z uloziste, nahrat novy obrazek a vlozit zaznam do databaze.
	 */
	@RequestMapping(value="/rooms/{roomId}/edit", method = {RequestMethod.PUT, RequestMethod.POST})
	public String processSubmit(@ModelAttribute Room room, BindingResult result, SessionStatus status, @RequestParam("file") MultipartFile file) {	
		new RoomValidator().validate(room, result);
		if (result.hasErrors()) {
			return "rooms/createForm";
		}
		else {			
			if (!file.isEmpty()) {				
				// Smazani puvodního obrazku z uloziste.
				String illustrationImageName = room.getIllustrationImageName();	
				String illustrationImagePath = myProjectPath + File.separator + "roomImages" + File.separator + illustrationImageName;
				File illustrationImage = new File(illustrationImagePath);
				
				// Smaze soubor a zaroven vraci bool, jestli byl soubor uspesne smazan.
				if (illustrationImage.delete()) {
					logger.info("Smazan obrazek: " + illustrationImageName);
				} else {
					logger.info("Nezdarilo se smazat obrazek: " + illustrationImageName + " z umisteni " + illustrationImagePath);
				}
							
				// Nahrani noveho obrazku.
	            try {
	                byte[] bytes = file.getBytes();
	 
	                // Creating the directory to store file                                       
	                File directory = new File(myProjectPath + File.separator + "roomImages");
	                
	                String originalFileName = file.getOriginalFilename();
	                
	                // Create the file on server
	                File serverFile = new File(directory.getAbsolutePath() + File.separator + originalFileName);
	                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
	                stream.write(bytes);
	                stream.close();

	                logger.info("Uspesne umisteni souboru na Serveru = " + serverFile.getAbsolutePath());
	                
	                room.setIllustrationImageName(originalFileName);
	                
	                this.fitnessCentre.storeRoom(room);
	    			status.setComplete();
	    			return "redirect:/rooms/index";	               	                
	            } catch (Exception e) {
	                return "Nepodarilo se uploadnout " + file.getOriginalFilename() + " => " + e.getMessage();
	            }
	        } else {
	        	// TODO Informovat uzivatele o povinnosti nahrat fotografii.
	        	logger.info("Nepodarilo se uploadnout " + file.getOriginalFilename() + " protoze soubor je prazdny.");
	        	return "rooms/createForm";
	        }						
		}
	}
	
	
	/**
	 * Metoda pro smazání místnosti dle zadaného id.
	 * Nejprve odstraní fotografii místnosti ze složky roomImages, poté vymaže záznam z databáze.
	 */
	@RequestMapping(value="/rooms/{roomId}/delete")
	public String deleteRoom(@PathVariable int roomId) {
		Room room = this.fitnessCentre.loadRoom(roomId);
		String illustrationImageName = room.getIllustrationImageName();	
		String illustrationImagePath = myProjectPath + File.separator + "roomImages" + File.separator + illustrationImageName;
		File illustrationImage = new File(illustrationImagePath);
		
		// Smaže soubor a zároveò vrací bool, jestli byl soubor úspìšnì smazán.
		if (illustrationImage.delete()) {
			logger.info("Smazán obrázek: " + illustrationImageName);
		} else {
			logger.info("Nezdaøilo se smazat obrázek: " + illustrationImageName + " z umístìní " + illustrationImagePath);
		}
				
		this.fitnessCentre.deleteRoom(roomId);
		return "redirect:/rooms/index";	
	}
		
}
