package org.springframework.samples.petclinic.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.ActivityType;
import org.springframework.samples.petclinic.FitnessCentre;
import org.springframework.samples.petclinic.User;
import org.springframework.samples.petclinic.UserRole;
import org.springframework.samples.petclinic.validation.ActivityTypeValidator;
import org.springframework.samples.petclinic.validation.FitnessCentreUserValidator;
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
 * JavaBean form controller pro pridani noveho instruktora do systemu.
 * @author Tomas Skorepa
 */
@Controller
@RequestMapping("/instructors/create")
public class CreateInstructor {
private final FitnessCentre fitnessCentre;
	
	private static final Logger logger = LoggerFactory.getLogger(CreateRoomForm.class);
	// Nutné mìnit absolutní cestu ke složce "uploads" v projektu.
	private final String myProjectPath = "C:\\Users\\Tomas\\Documents\\workspace-sts-3.7.2.RELEASE\\petclinic\\src\\main\\webapp\\static\\uploads";
	
	@Autowired
	public CreateInstructor(FitnessCentre fitnessCentre) {
		this.fitnessCentre = fitnessCentre;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String setupForm(Model model) {
		User user = new User();
		model.addAttribute(user);
		return "instructors/createForm";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(@ModelAttribute User instructor, BindingResult result, SessionStatus status, @RequestParam("file") MultipartFile file) {
		new FitnessCentreUserValidator().validate(instructor, result);
		if (result.hasErrors()) {
			return "instructors/createForm";
		}
		else {			
			if (!file.isEmpty()) {
	            try {
	                byte[] bytes = file.getBytes();
	 
	                // Creating the directory to store file                                       
	                File directory = new File(myProjectPath + File.separator + "userImages");
	                
	                // TODO Lepe zmenit na GUID.
	                String originalFileName = file.getOriginalFilename();
	                
	                // Create the file on server
	                File serverFile = new File(directory.getAbsolutePath() + File.separator + originalFileName);
	                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
	                stream.write(bytes);
	                stream.close();

	                logger.info("Uspesne umisteni souboru na serveru = " + serverFile.getAbsolutePath());
	                
	                instructor.setProfilePhotoName(originalFileName);
	                
	                UserRole instructorRole = this.fitnessCentre.loadUserRole(2);	// id role instruktora = 2
	                instructor.setUserRole(instructorRole);
	                instructor.setActive(true);
	                
	                this.fitnessCentre.storeUser(instructor);
	    			status.setComplete();
	    			return "redirect:/instructors/index";	               	                
	            } catch (Exception e) {
	                return "Nepodarilo se uploadnout " + file.getOriginalFilename() + " CHYBA => " + e.getMessage();
	            }
	        } else {
	        	logger.info("Nepodarilo se uploadnout " + file.getOriginalFilename() + " protoze soubor je prazdny.");
	        	return "instructors/createForm";
	        }						
		}
	}
	
	
}
