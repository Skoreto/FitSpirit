package org.springframework.samples.petclinic.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.FitnessCentre;
import org.springframework.samples.petclinic.User;
import org.springframework.samples.petclinic.UserRole;
import org.springframework.samples.petclinic.Users;
import org.springframework.samples.petclinic.util.ProjectUtils;
import org.springframework.samples.petclinic.validation.UserValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
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
import org.springframework.web.servlet.ModelAndView;

/**
 * JavaBean form controller pro registraci noveho klienta do systemu.
 * @author Tomas Skorepa
 */
@Controller
@SessionAttributes("user")
public class ClientController {
	
	private final FitnessCentre fitnessCentre;
	
	private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

	private final String myProjectPath = ProjectUtils.getMyProjectPath();
	
	@Autowired
	public ClientController(FitnessCentre fitnessCentre) {
		this.fitnessCentre = fitnessCentre;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	/**
	 * Handler pro zobrazeni formulare pro registraci noveho uzivatele.
	 */
	@RequestMapping(value="/registration/create", method = RequestMethod.GET)
	public String setupForm(Model model) {
		User user = new User();
		model.addAttribute(user);
		return "registration/createForm";
	}	
	
	/**
	 * Handler pro registraci noveho uzivatele.
	 */
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
	
	/**
	 * Handler pro zobrazeni klientu.
	 * Nejprve ziska seznam vsech uzivatelu. Z nich vybere ty, s id klienta, a naplni
	 * je do pomocneho seznamu clientUsers. Teprve pomocny seznam clientUsers
	 * preleje do seznamu clients tridy Users, ktery slouzi pro ucely odkazani ve view.
	 * 
	 * @return ModelMap s atributy modelu pro dané view
	 */
	@RequestMapping("/admin/clients/indexStaff")
	public ModelMap clientsHandler() {
		List<User> clientUsers = new ArrayList<User>();
		List<User> allUsers = new ArrayList<User>();
		allUsers.addAll(this.fitnessCentre.getUsers());
		
		// TODO Rychlejsi by byl dotaz primo na databazi, nez prochazet vsechny usery.
		for (User user : allUsers) {
			if (user.getUserRole().getId() == 3) {
				clientUsers.add(user);
			}
		}
		
		Users clients = new Users();
		clients.getUserList().addAll(clientUsers);
		return new ModelMap(clients);
	}
		
	/**
	 * Handler pro zobrazeni detailu klienta.
	 */
	@RequestMapping("/admin/clients/{clientId}")
	public ModelAndView clientHandler(@PathVariable("clientId") int clientId) {
		ModelAndView mav = new ModelAndView("admin/clients/detail");
		mav.addObject(this.fitnessCentre.loadUser(clientId));
		return mav;
	}
	
	/**
	 * Handler pro zobrazeni formulare pro vytvoreni klienta a naplneni
	 * kolonek stavajicimi hodnotami = formular upravy klienta.
	 * Ve view se pak odkazuji na promennou "user"!
	 */
	@RequestMapping(value="/admin/clients/{clientId}/edit", method = RequestMethod.GET)
	public String setupEditForm(@PathVariable("clientId") int clientId, Model model) {
		User client = this.fitnessCentre.loadUser(clientId);
		model.addAttribute("user", client);
		return "admin/clients/editForm";
	}
	
	/**
	 * Handler pro editaci stavajiciho klienta.
	 * Nejprve overi, zda bylo vyplneno jmeno, prijmeni, mail, heslo. 
	 * - Pokud ne, vrati uzivatele na formular s upozornenim na povinnost vyplnit problemova pole.
	 * Pote overi, zda byla zvolena fotografie.
	 * - Pokud ne, vypise do konzole zpravu o nevyplneni pole fotografie a vrati uzivatele na formular.
	 * - Pokud ano, pokusi se smazat puvodni obrazek z uloziste, nahrat novy obrazek a vlozit zaznam do databaze.
	 */
	@RequestMapping(value="/admin/clients/{clientId}/edit", method = {RequestMethod.PUT, RequestMethod.POST})
	public String processEditSubmit(@ModelAttribute User client, BindingResult result, SessionStatus status, @RequestParam("file") MultipartFile file) {	
		new UserValidator().validate(client, result);
		if (result.hasErrors()) {
			return "admin/clients/editForm";
		}
		else {			
			if (!file.isEmpty()) {				
				// Smazani puvodni fotografie z uloziste.
				String profilePhotoName = client.getProfilePhotoName();	
				String profilePhotoPath = myProjectPath + File.separator + "userImages" + File.separator + profilePhotoName;
				File profilePhoto = new File(profilePhotoPath);
				
				// Smaze soubor a zaroven vraci bool, jestli byl soubor uspesne smazan.
				if (profilePhoto.delete()) {
					logger.info("Smazan obrazek: " + profilePhotoName);
				} else {
					logger.info("Nezdarilo se smazat obrazek: " + profilePhotoName + " z umisteni " + profilePhotoPath);
				}
							
				// Nahrani nove fotografie.
	            try {
	                byte[] bytes = file.getBytes();
	 
	                // Creating the directory to store file                                       
	                File directory = new File(myProjectPath + File.separator + "userImages");
	                
	                // TODO Lepe generovat GUID.
	                String originalFileName = file.getOriginalFilename();
	                
	                // Create the file on server
	                File serverFile = new File(directory.getAbsolutePath() + File.separator + originalFileName);
	                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
	                stream.write(bytes);
	                stream.close();

	                logger.info("Uspesne umisteni souboru na serveru = " + serverFile.getAbsolutePath());
	                
	                client.setProfilePhotoName(originalFileName);
	                
	                this.fitnessCentre.storeUser(client);
	    			status.setComplete();
	    			return "redirect:/admin/clients/indexStaff";	               	                
	            } catch (Exception e) {
	                return "Nepodarilo se uploadnout " + file.getOriginalFilename() + " => " + e.getMessage();
	            }
	        } else {
	        	// TODO Informovat uzivatele o povinnosti nahrat fotografii.
	        	logger.info("Nepodarilo se uploadnout " + file.getOriginalFilename() + " protoze soubor je prazdny.");
	        	return "admin/clients/editForm";
	        }						
		}
	}
	
	/**
	 * Handler pro smazani Klienta dle zadaneho id.
	 * Nejprve odstrani fotografii Klienta ze slozky userImages, pote vymaze zaznam z databaze.
	 */
	@RequestMapping(value="/admin/clients/{clientId}/delete")
	public String deleteInstructor(@PathVariable int clientId) {
		User client = this.fitnessCentre.loadUser(clientId);
		String profilePhotoName = client.getProfilePhotoName();	
		String profilePhotoPath = myProjectPath + File.separator + "userImages" + File.separator + profilePhotoName;
		File profilePhoto = new File(profilePhotoPath);
		
		// Smaže soubor a zároveò vrací bool, jestli byl soubor úspìšnì smazán.
		if (profilePhoto.delete()) {
			logger.info("Smazan obrazek: " + profilePhotoName);
		} else {
			logger.info("Nezdarilo se smazat obrazek: " + profilePhotoName + " z umisteni " + profilePhotoPath);
		}
				
		this.fitnessCentre.deleteUser(clientId);
		return "redirect:/admin/clients/indexStaff";	
	}
	
}
