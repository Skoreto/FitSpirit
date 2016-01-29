package org.springframework.samples.petclinic.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.FitnessCentre;
import org.springframework.samples.petclinic.Room;
import org.springframework.samples.petclinic.Rooms;
import org.springframework.samples.petclinic.User;
import org.springframework.samples.petclinic.util.ProjectUtils;
import org.springframework.samples.petclinic.validation.RoomValidator;
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
 * Controller pro handlovani Mistnosti v systemu.
 * @author Tomas Skorepa
 */
@Controller
@SessionAttributes("room")
public class RoomController {

	private final FitnessCentre fitnessCentre;
	
	private static final Logger logger = LoggerFactory.getLogger(RoomController.class);
	
	private final String myProjectPath = ProjectUtils.getMyProjectPath();
	
	@Autowired
	public RoomController(FitnessCentre fitnessCentre) {
		this.fitnessCentre = fitnessCentre;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	/**
	 * Handler pro zobrazeni seznamu mistnosti.
	 */
	@RequestMapping("/rooms/index")
	public String roomsHandler(Model model, HttpServletRequest request) {
		Rooms rooms = new Rooms();
		rooms.getRoomList().addAll(this.fitnessCentre.getRooms());
				
		model.addAttribute("rooms", rooms);
		
		// Predani titulku stranky do view
		String pageTitle = "Místnosti";
		model.addAttribute("pageTitle", pageTitle);
		
		// Pristup k session prihlaseneho uzivatele
		User loggedInUser = (User)request.getSession().getAttribute("user");
		if (null != loggedInUser) {
			model.addAttribute("loggedInUser", loggedInUser);
			String loggedInUserRoleIdent = loggedInUser.getUserRole().getIdentificator();
			
			if (loggedInUserRoleIdent.equals("obsluha")) {
				return "rooms/indexStaff";
			}			
		}
		
		return "rooms/index";
	}
	
	/**
	 * Handler pro zobrazeni formulare pro vytvoreni nove Mistnosti.
	 */
	@RequestMapping(value="/rooms/create", method = RequestMethod.GET)
	public String setupForm(Model model, HttpServletRequest request) {
		Room room = new Room();
		model.addAttribute(room);
		
		// Predani titulku stranky do view
		String pageTitle = "Nová místnost";
		model.addAttribute("pageTitle", pageTitle);
		
		// Pristup k session prihlaseneho uzivatele
		User loggedInUser = (User)request.getSession().getAttribute("user");
		if (null != loggedInUser) {
			model.addAttribute("loggedInUser", loggedInUser);
		}
		
		return "rooms/createForm";
	}
	
	/**
	 * Handler pro vytvoreni nove Mistnosti.
	 */
	@RequestMapping(value="/rooms/create", method = RequestMethod.POST)
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
	
	/**
	 * Handler pro zobrazeni detailu o Mistnosti.
	 */
	@RequestMapping("/rooms/{roomId}")
	public ModelAndView roomHandler(@PathVariable("roomId") int roomId, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("rooms/detail");
		Room room = this.fitnessCentre.loadRoom(roomId);
		mav.addObject(room);
		
		// Pristup k session prihlaseneho uzivatele
		User loggedInUser = (User)request.getSession().getAttribute("user");
		if (null != loggedInUser) {
			mav.addObject("loggedInUser", loggedInUser);
		}
			
		return mav;
	}
		
	/**
	 * Handler pro zobrazeni formulare pro vytvoreni Mistnosti a naplneni
	 * kolonek stavajicimi hodnotami = formular upravy Mistnosti.
	 */
	@RequestMapping(value="/rooms/{roomId}/edit", method = RequestMethod.GET)
	public String setupForm(@PathVariable("roomId") int roomId, Model model, HttpServletRequest request) {
		Room room = this.fitnessCentre.loadRoom(roomId);
		model.addAttribute("room", room);
		
		// Predani titulku stranky do view
		String pageTitle = "Úprava místnosti " + room.getName();
		model.addAttribute("pageTitle", pageTitle);
		
		// Pristup k session prihlaseneho uzivatele
		User loggedInUser = (User)request.getSession().getAttribute("user");
		if (null != loggedInUser) {
			model.addAttribute("loggedInUser", loggedInUser);
		}
		
		return "rooms/createForm";
	}
	
	/**
	 * Handler pro editaci stavajici Mistnosti.
	 * Nejprve overi, zda byl vyplnen novy nazev Mistnosti. 
	 * - Pokud ne, vrati uzivatele na formular s upozornenim na povinnost vyplnit nazev Mistnosti.
	 * Pote overi, zda byla zvolena fotografie Mistnosti.
	 * - Pokud ne, vypise do konzole zpravu o nevyplneni pole fotografie a vrati uzivatele na formular.
	 * - Pokud ano, pokusi se smazat puvodni obrazek z uloziste, nahrat novy obrazek a vlozit zaznam do databaze.
	 */
	@RequestMapping(value="/rooms/{roomId}/edit", method = {RequestMethod.PUT, RequestMethod.POST})
	public String processEditSubmit(@ModelAttribute Room room, BindingResult result, SessionStatus status, @RequestParam("file") MultipartFile file) {	
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
	 * Handler pro smazani Mistnosti dle zadaneho id.
	 * Nejprve odstrani fotografii Mistnosti ze slozky roomImages, pote vymaze zaznam z databaze.
	 */
	@RequestMapping(value="/rooms/{roomId}/delete")
	public String deleteRoom(@PathVariable int roomId) {
		Room room = this.fitnessCentre.loadRoom(roomId);
		String illustrationImageName = room.getIllustrationImageName();	
		String illustrationImagePath = myProjectPath + File.separator + "roomImages" + File.separator + illustrationImageName;
		File illustrationImage = new File(illustrationImagePath);
		
		// Smaze soubor a zaroven vraci bool, jestli byl soubor uspesne smazan.
		if (illustrationImage.delete()) {
			logger.info("Smazan obrazek: " + illustrationImageName);
		} else {
			logger.info("Nezdarilo se smazat obrazek: " + illustrationImageName + " z umisteni " + illustrationImagePath);
		}
				
		this.fitnessCentre.deleteRoom(roomId);
		return "redirect:/rooms/index";	
	}
	
}
