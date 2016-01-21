package org.springframework.samples.petclinic.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.FitnessCentre;
import org.springframework.samples.petclinic.Owner;
import org.springframework.samples.petclinic.Room;
import org.springframework.samples.petclinic.validation.OwnerValidator;
import org.springframework.samples.petclinic.validation.RoomValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

/**
 * JavaBean form controller pro pøidání nové místnosti do systému.
 * @author Tomas Skorepa
 */
@Controller
@RequestMapping("/rooms/create")
public class CreateRoomForm {

	private final FitnessCentre fitnessCentre;
	
	private static final Logger logger = LoggerFactory.getLogger(CreateRoomForm.class);
	// Nutné mìnit absolutní cestu ke složce "uploads" v projektu.
	private final String myProjectPath = "C:\\Users\\Tomas\\Documents\\workspace-sts-3.7.2.RELEASE\\petclinic\\src\\main\\webapp\\static\\uploads";
	
	@Autowired
	public CreateRoomForm(FitnessCentre fitnessCentre) {
		this.fitnessCentre = fitnessCentre;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String setupForm(Model model) {
		Room room = new Room();
		model.addAttribute(room);
		return "rooms/createForm";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(@ModelAttribute Room room, BindingResult result, SessionStatus status, @RequestParam("file") MultipartFile file) {
		new RoomValidator().validate(room, result);
		if (result.hasErrors()) {
			return "rooms/createForm";
		}
		else {			
			if (!file.isEmpty()) {
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
	            return "Nepodarilo se uploadnout " + file.getOriginalFilename() + " protoze soubor je prazdny.";
	        }						
		}
	}
	
	
}
