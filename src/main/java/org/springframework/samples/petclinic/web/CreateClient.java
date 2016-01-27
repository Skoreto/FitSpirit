package org.springframework.samples.petclinic.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.FitnessCentre;
import org.springframework.samples.petclinic.User;
import org.springframework.samples.petclinic.UserRole;
import org.springframework.samples.petclinic.util.ProjectUtils;
import org.springframework.samples.petclinic.validation.UserValidator;
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
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * JavaBean form controller pro registraci noveho klienta do systemu.
 * @author Tomas Skorepa
 */
@Controller
public class CreateClient {
	
	private final FitnessCentre fitnessCentre;
	
	private static final Logger logger = LoggerFactory.getLogger(CreateClient.class);

	private final String myProjectPath = ProjectUtils.getMyProjectPath();
	
	@Autowired
	public CreateClient(FitnessCentre fitnessCentre) {
		this.fitnessCentre = fitnessCentre;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@RequestMapping(value="/registration/create", method = RequestMethod.GET)
	public String setupForm(Model model) {
		User user = new User();
		model.addAttribute(user);
		return "registration/createForm";
	}	
	
	@RequestMapping(value="/registration/create", method = RequestMethod.POST)
	public String processSubmit(@ModelAttribute User client, BindingResult result, SessionStatus status, @RequestParam("file") MultipartFile file) {
		new UserValidator().validate(client, result);
		if (result.hasErrors()) {
			return "registration/createForm";
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
	                
	                client.setProfilePhotoName(originalFileName);
	                
	                UserRole clientRole = this.fitnessCentre.loadUserRole(3);	// id role klienta = 3
	                client.setUserRole(clientRole);
	                client.setActive(false);
	                
	                this.fitnessCentre.storeUser(client);
	    			status.setComplete();
	    			return "redirect:/registration/" + client.getId().toString() + "/success";	               	                
	            } catch (Exception e) {
	                return "Nepodarilo se uploadnout " + file.getOriginalFilename() + " CHYBA => " + e.getMessage();
	            }
	        } else {
	        	logger.info("Nepodarilo se uploadnout " + file.getOriginalFilename() + " protoze soubor je prazdny.");
	        	return "registration/createForm";
	        }						
		}
	}
	
	/**
	 * Handler pro zobrazeni oznameni o uspesne registraci klienta.
	 */
	@RequestMapping("/registration/{clientId}/success")
	public ModelAndView instructorHandler(@PathVariable("clientId") int clientId) {
		ModelAndView mav = new ModelAndView("registration/success");
		mav.addObject(this.fitnessCentre.loadUser(clientId));
		return mav;
	}
	
}
