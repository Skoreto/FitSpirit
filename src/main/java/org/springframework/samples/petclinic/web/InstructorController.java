package org.springframework.samples.petclinic.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
 * Controller pro handlovani Instruktora v systemu.
 * @author Tomas Skorepa
 */
@Controller
@SessionAttributes("user")
public class InstructorController {
	
	private final FitnessCentre fitnessCentre;
	
	private static final Logger logger = LoggerFactory.getLogger(InstructorController.class);

	private final String myProjectPath = ProjectUtils.getMyProjectPath();
	
	@Autowired
	public InstructorController(FitnessCentre fitnessCentre) {
		this.fitnessCentre = fitnessCentre;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	/**
	 * Handler pro zobrazeni seznamu instruktoru.
	 * Nejprve ziska seznam vsech uzivatelu. Z nich vybere ty, s id instruktora, a naplni
	 * je do pomocneho seznamu instructorUsers. Teprve pomocny seznam instructorUsers
	 * preleje do seznamu instructors tridy Users, ktery slouzi pro ucely odkazani ve view.
	 */
	@RequestMapping("/instructors/index")
	public String instructorsHandler(Model model, HttpServletRequest request) {	
		List<User> instructorUsers = new ArrayList<User>();
		List<User> allUsers = new ArrayList<User>();
		allUsers.addAll(this.fitnessCentre.getUsers());
		
		// TODO Rychlejsi by byl dotaz primo na databazi, nez prochazet vsechny usery.
		for (User user : allUsers) {
			if (user.getUserRole().getId() == 2) {
				instructorUsers.add(user);
			}
		}
		
		Users instructors = new Users();
		instructors.getUserList().addAll(instructorUsers);
		
		model.addAttribute("users", instructors);
		
		// Predani titulku stranky do view
		String pageTitle = "Instruktoøi";
		model.addAttribute("pageTitle", pageTitle);
		
		// Pristup k session prihlaseneho uzivatele
		User loggedInUser = (User)request.getSession().getAttribute("user");
		if (null != loggedInUser) {
			model.addAttribute("loggedInUser", loggedInUser);
			String loggedInUserRoleIdent = loggedInUser.getUserRole().getIdentificator();
			
			if (loggedInUserRoleIdent.equals("obsluha")) {
				return "instructors/indexStaff";
			}			
		}
			
		return "instructors/index";
	}
	
	/**
	 * Handler pro zobrazeni formulare pro vytvoreni noveho Instruktora.
	 */
	@RequestMapping(value="/instructors/create", method = RequestMethod.GET)
	public String setupForm(Model model, HttpServletRequest request) {
		User user = new User();
		model.addAttribute(user);
		
		// Predani titulku stranky do view
		String pageTitle = "Nový instruktor";
		model.addAttribute("pageTitle", pageTitle);
		
		// Pristup k session prihlaseneho uzivatele
		User loggedInUser = (User)request.getSession().getAttribute("user");
		if (null != loggedInUser) {
			model.addAttribute("loggedInUser", loggedInUser);
		}
		
		return "instructors/createForm";
	}
	
	/**
	 * Handler pro vytvoreni noveho Instruktora.
	 */
	@RequestMapping(value="/instructors/create", method = RequestMethod.POST)
	public String processSubmit(@ModelAttribute User instructor, BindingResult result, SessionStatus status, @RequestParam("file") MultipartFile file) {
		new UserValidator().validate(instructor, result);
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
	    			return "redirect:/instructors/indexStaff";	               	                
	            } catch (Exception e) {
	                return "Nepodarilo se uploadnout " + file.getOriginalFilename() + " CHYBA => " + e.getMessage();
	            }
	        } else {
	        	logger.info("Nepodarilo se uploadnout " + file.getOriginalFilename() + " protoze soubor je prazdny.");
	        	return "instructors/createForm";
	        }						
		}
	}
	
	/**
	 * Handler pro zobrazeni detailu instruktora.
	 */
	@RequestMapping("/instructors/{instructorId}")
	public ModelAndView instructorHandler(@PathVariable("instructorId") int instructorId, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("instructors/detail");
		User instructor = this.fitnessCentre.loadUser(instructorId);
		mav.addObject(instructor);
		
		// Predani titulku stranky do view
		String pageTitle = "Detail instruktora " + instructor.getFirstName() + " " + instructor.getLastName();
		mav.addObject("pageTitle", pageTitle);
			
		// Pristup k session prihlaseneho uzivatele
		User loggedInUser = (User)request.getSession().getAttribute("user");
		if (null != loggedInUser) {
			mav.addObject("loggedInUser", loggedInUser);
			String loggedInUserRoleIdent = loggedInUser.getUserRole().getIdentificator();
			
			if (loggedInUserRoleIdent.equals("obsluha")) {
				mav.setViewName("instructors/detailStaff");
			}		
		}
			
		return mav;
	}
	
	/**
	 * Handler pro zobrazeni formulare pro vytvoreni Instruktora a naplneni
	 * kolonek stavajicimi hodnotami = formular upravy Instruktora.
	 * Ve view se pak odkazuji na promennou "user"!
	 */
	@RequestMapping(value="/instructors/{instructorId}/edit", method = RequestMethod.GET)
	public String setupEditForm(@PathVariable("instructorId") int instructorId, Model model, HttpServletRequest request) {
		User instructor = this.fitnessCentre.loadUser(instructorId);
		model.addAttribute("user", instructor);
		
		// Predani titulku stranky do view
		String pageTitle = "Úprava instruktora " + instructor.getFirstName() + " " + instructor.getLastName();
		model.addAttribute("pageTitle", pageTitle);
		
		// Pristup k session prihlaseneho uzivatele
		User loggedInUser = (User)request.getSession().getAttribute("user");
		if (null != loggedInUser) {
			model.addAttribute("loggedInUser", loggedInUser);
		}
		
		return "instructors/createForm";
	}
	
	/**
	 * Handler pro editaci stavajiciho instruktora.
	 * Nejprve overi, zda bylo vyplneno jmeno, prijmeni, mail, heslo. 
	 * - Pokud ne, vrati uzivatele na formular s upozornenim na povinnost vyplnit problemova pole.
	 * Pote overi, zda byla zvolena fotografie.
	 * - Pokud ne, vypise do konzole zpravu o nevyplneni pole fotografie a vrati uzivatele na formular.
	 * - Pokud ano, pokusi se smazat puvodni obrazek z uloziste, nahrat novy obrazek a vlozit zaznam do databaze.
	 */
	@RequestMapping(value="/instructors/{instructorId}/edit", method = {RequestMethod.PUT, RequestMethod.POST})
	public String processEditSubmit(@ModelAttribute User instructor, BindingResult result, SessionStatus status, @RequestParam("file") MultipartFile file) {	
		new UserValidator().validate(instructor, result);
		if (result.hasErrors()) {
			return "instructors/createForm";
		}
		else {			
			if (!file.isEmpty()) {				
				// Smazani puvodni fotografie z uloziste.
				String profilePhotoName = instructor.getProfilePhotoName();	
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
	                
	                instructor.setProfilePhotoName(originalFileName);
	                
	                this.fitnessCentre.storeUser(instructor);
	    			status.setComplete();
	    			return "redirect:/instructors/indexStaff";	               	                
	            } catch (Exception e) {
	                return "Nepodarilo se uploadnout " + file.getOriginalFilename() + " => " + e.getMessage();
	            }
	        } else {
	        	// TODO Informovat uzivatele o povinnosti nahrat fotografii.
	        	logger.info("Nepodarilo se uploadnout " + file.getOriginalFilename() + " protoze soubor je prazdny.");
	        	return "instructors/createForm";
	        }						
		}
	}		
	
	/**
	 * Metoda pro smazani Instruktora dle zadaneho id.
	 * Nejprve odstrani fotografii Instruktora ze slozky userImages, pote vymaze zaznam z databaze.
	 */
	@RequestMapping(value="/instructors/{instructorId}/delete")
	public String deleteInstructor(@PathVariable int instructorId) {
		User instructor = this.fitnessCentre.loadUser(instructorId);
		String profilePhotoName = instructor.getProfilePhotoName();	
		String profilePhotoPath = myProjectPath + File.separator + "userImages" + File.separator + profilePhotoName;
		File profilePhoto = new File(profilePhotoPath);
		
		// Smaže soubor a zároveò vrací bool, jestli byl soubor úspìšnì smazán.
		if (profilePhoto.delete()) {
			logger.info("Smazan obrazek: " + profilePhotoName);
		} else {
			logger.info("Nezdarilo se smazat obrazek: " + profilePhotoName + " z umisteni " + profilePhotoPath);
		}
				
		this.fitnessCentre.deleteUser(instructorId);
		return "redirect:/instructors/indexStaff";	
	}
	
}
