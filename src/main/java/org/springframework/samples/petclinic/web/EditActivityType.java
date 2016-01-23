package org.springframework.samples.petclinic.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.ActivityType;
import org.springframework.samples.petclinic.FitnessCentre;
import org.springframework.samples.petclinic.validation.ActivityTypeValidator;
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
@SessionAttributes("activityType")
public class EditActivityType {
	
	private final FitnessCentre fitnessCentre;
	
	private static final Logger logger = LoggerFactory.getLogger(EditRoomForm.class);
	private final String myProjectPath = "C:\\Users\\Tomas\\Documents\\workspace-sts-3.7.2.RELEASE\\petclinic\\src\\main\\webapp\\static\\uploads";
	
	@Autowired
	public EditActivityType(FitnessCentre fitnessCentre) {
		this.fitnessCentre = fitnessCentre;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder databinder) {
		databinder.setDisallowedFields("id");
	}
	
	/**
	 * Metoda pro zobrazeni formulare pro vytvoreni aktivity a naplneni
	 * kolonek stavajicimi hodnotami = formular upravy aktivity.
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String setupForm(@PathVariable("activityTypeId") int activityTypeId, Model model) {
		ActivityType activityType = this.fitnessCentre.loadActivityType(activityTypeId);
		model.addAttribute("activityType", activityType);
		return "activityTypes/createForm";
	}
	
	/**
	 * Metoda pro editaci stavajici aktivity.
	 * Nejprve overi, zda byl vyplnen novy nazev aktivity. 
	 * - Pokud ne, vrati uzivatele na formular s upozornenim na povinnost vyplnit nazev aktivity.
	 * Pote overi, zda byla zvolena fotografie aktivity.
	 * - Pokud ne, vypise do konzole zpravu o nevyplneni pole fotografie a vrati uzivatele na formular.
	 * - Pokud ano, pokusi se smazat puvodni obrazek z uloziste, nahrat novy obrazek a vlozit zaznam do databaze.
	 */
	@RequestMapping(value="/activityTypes/{activityTypeId}/edit", method = {RequestMethod.PUT, RequestMethod.POST})
	public String processSubmit(@ModelAttribute ActivityType actvityType, BindingResult result, SessionStatus status, @RequestParam("file") MultipartFile file) {	
		new ActivityTypeValidator().validate(actvityType, result);
		if (result.hasErrors()) {
			return "activityTypes/createForm";
		}
		else {			
			if (!file.isEmpty()) {				
				// Smazani puvodního obrazku z uloziste.
				String illustrationImageName = actvityType.getIllustrationImageName();	
				String illustrationImagePath = myProjectPath + File.separator + "activityTypeImages" + File.separator + illustrationImageName;
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
	                File directory = new File(myProjectPath + File.separator + "activityTypeImages");
	                
	                // TODO Lepe generovat GUID.
	                String originalFileName = file.getOriginalFilename();
	                
	                // Create the file on server
	                File serverFile = new File(directory.getAbsolutePath() + File.separator + originalFileName);
	                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
	                stream.write(bytes);
	                stream.close();

	                logger.info("Uspesne umisteni souboru na serveru = " + serverFile.getAbsolutePath());
	                
	                actvityType.setIllustrationImageName(originalFileName);
	                
	                this.fitnessCentre.storeActivityType(actvityType);
	    			status.setComplete();
	    			return "redirect:/activityTypes/index";	               	                
	            } catch (Exception e) {
	                return "Nepodarilo se uploadnout " + file.getOriginalFilename() + " => " + e.getMessage();
	            }
	        } else {
	        	// TODO Informovat uzivatele o povinnosti nahrat fotografii.
	        	logger.info("Nepodarilo se uploadnout " + file.getOriginalFilename() + " protoze soubor je prazdny.");
	        	return "activityTypes/createForm";
	        }						
		}
	}	
	
		
}
