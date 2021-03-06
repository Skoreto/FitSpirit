package cz.uhk.fim.fitspirit.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import cz.uhk.fim.fitspirit.ActivityType;
import cz.uhk.fim.fitspirit.ActivityTypes;
import cz.uhk.fim.fitspirit.FitnessCentre;
import cz.uhk.fim.fitspirit.Lessons;
import cz.uhk.fim.fitspirit.User;
import cz.uhk.fim.fitspirit.util.ProjectUtils;
import cz.uhk.fim.fitspirit.validation.ActivityTypeValidator;

/**
 * Controller pro handlovani Aktivit v systemu.
 * @author Tomas Skorepa
 */
@Controller
@SessionAttributes("activityType")
public class ActivityTypeController {

	private final FitnessCentre fitnessCentre;
	
	private static final Logger logger = LoggerFactory.getLogger(ActivityTypeController.class);
	
	private final String myProjectPath = ProjectUtils.getMyProjectPath();
	
	@Autowired
	public ActivityTypeController(FitnessCentre fitnessCentre) {
		this.fitnessCentre = fitnessCentre;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	/**
	 * Handler pro zobrazen� seznamu Aktivit.
	 */
	@RequestMapping("/activityTypes/index")
	public String activityTypesHandler(Model model, HttpServletRequest request) {
		ActivityTypes activityTypes = new ActivityTypes();
		activityTypes.getActivityTypeList().addAll(this.fitnessCentre.getActivityTypes());
		
		model.addAttribute("activityTypes", activityTypes);
		
		// Predani titulku stranky do view
		String pageTitle = "Aktivity";
		model.addAttribute("pageTitle", pageTitle);
		
		// Predani seznamu lekci pro widget
		Lessons activeLessons = new Lessons();
		activeLessons.getLessonList().addAll(this.fitnessCentre.getActiveLessons());
		model.addAttribute("lessonsForWidget", activeLessons);
		
		// Pristup k session prihlaseneho uzivatele
		User loggedInUser = (User)request.getSession().getAttribute("logUser");
		if (null != loggedInUser) {
			model.addAttribute("loggedInUser", loggedInUser);
			String loggedInUserRoleIdent = loggedInUser.getUserRole().getIdentificator();
			
			if (loggedInUserRoleIdent.equals("obsluha")) {
				return "activityTypes/indexStaff";
			}			
		}
		
		return "activityTypes/index";	
	}
	
	/**
	 * Handler pro zobrazeni formulare pro vytvoreni nove Aktivity.
	 */
	@RequestMapping(value="/activityTypes/create", method = RequestMethod.GET)
	public String setupForm(Model model, HttpServletRequest request) {
		ActivityType activityType = new ActivityType();
		model.addAttribute(activityType);
		
		// Predani titulku stranky do view
		String pageTitle = "Nov� aktivita";
		model.addAttribute("pageTitle", pageTitle);
		
		// Predani seznamu lekci pro widget
		Lessons activeLessons = new Lessons();
		activeLessons.getLessonList().addAll(this.fitnessCentre.getActiveLessons());
		model.addAttribute("lessonsForWidget", activeLessons);
		
		// Pristup k session prihlaseneho uzivatele
		User loggedInUser = (User)request.getSession().getAttribute("logUser");
		if (null != loggedInUser) {
			model.addAttribute("loggedInUser", loggedInUser);
		}
		
		return "activityTypes/createForm";
	}
	
	/**
	 * Handler pro vytvoreni nove Aktivity.
	 */
	@RequestMapping(value="/activityTypes/create", method = RequestMethod.POST)
	public String processSubmit(@ModelAttribute ActivityType activityType, BindingResult result, SessionStatus status, @RequestParam("file") MultipartFile file) {
		new ActivityTypeValidator().validate(activityType, result);
		if (result.hasErrors()) {
			return "activityTypes/createForm";
		}
		else {			
			if (!file.isEmpty()) {
	            try {
	                byte[] bytes = file.getBytes();
	 
	                // Creating the directory to store file                                       
	                File directory = new File(myProjectPath + File.separator + "activityTypeImages");
	                
	                // TODO Lepe zmenit na GUID.
	                String originalFileName = file.getOriginalFilename();
	                
	                // Create the file on server
	                File serverFile = new File(directory.getAbsolutePath() + File.separator + originalFileName);
	                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
	                stream.write(bytes);
	                stream.close();

	                logger.info("Uspesne umisteni souboru na serveru = " + serverFile.getAbsolutePath());
	                
	                activityType.setIllustrationImageName(originalFileName);
	                
	                this.fitnessCentre.storeActivityType(activityType);
	    			status.setComplete();
	    			return "redirect:/activityTypes/index";	               	                
	            } catch (Exception e) {
	                return "Nepodarilo se uploadnout " + file.getOriginalFilename() + " => " + e.getMessage();
	            }
	        } else {
	        	logger.info("Nepodarilo se uploadnout " + file.getOriginalFilename() + " protoze soubor je prazdny.");
	        	return "activityTypes/createForm";
	        }						
		}
	}
	
	/**
	 * Handler pro zobrazeni detailu o Aktivite.
	 */
	@RequestMapping("/activityTypes/{activityTypeId}")
	public ModelAndView activityTypeHandler(@PathVariable("activityTypeId") int activityTypeId, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("activityTypes/detail");
		ActivityType activityType = this.fitnessCentre.loadActivityType(activityTypeId);
		mav.addObject(activityType);
		
		// Predani titulku stranky do view
		String pageTitle = "Detail aktivity " + activityType.getName();
		mav.addObject("pageTitle", pageTitle);
		
		// Predani seznamu lekci pro widget
		Lessons activeLessons = new Lessons();
		activeLessons.getLessonList().addAll(this.fitnessCentre.getActiveLessons());
		mav.addObject("lessonsForWidget", activeLessons);
			
		// Pristup k session prihlaseneho uzivatele
		User loggedInUser = (User)request.getSession().getAttribute("logUser");
		if (null != loggedInUser) {
			mav.addObject("loggedInUser", loggedInUser);
			String loggedInUserRoleIdent = loggedInUser.getUserRole().getIdentificator();
			
			if (loggedInUserRoleIdent.equals("obsluha")) {
				mav.setViewName("activityTypes/detail");
			}	
		}
		
		return mav;
	}
	
	/**
	 * Handler pro zobrazeni formulare pro vytvoreni Aktivity a naplneni
	 * kolonek stavajicimi hodnotami = formular upravy Aktivity.
	 */
	@RequestMapping(value="/activityTypes/{activityTypeId}/edit", method = RequestMethod.GET)
	public String setupEditForm(@PathVariable("activityTypeId") int activityTypeId, Model model, HttpServletRequest request) {
		ActivityType activityType = this.fitnessCentre.loadActivityType(activityTypeId);
		model.addAttribute("activityType", activityType);
		
		// Predani titulku stranky do view
		String pageTitle = "�prava aktivity " + activityType.getName();
		model.addAttribute("pageTitle", pageTitle);
		
		// Predani seznamu lekci pro widget
		Lessons activeLessons = new Lessons();
		activeLessons.getLessonList().addAll(this.fitnessCentre.getActiveLessons());
		model.addAttribute("lessonsForWidget", activeLessons);
		
		// Pristup k session prihlaseneho uzivatele
		User loggedInUser = (User)request.getSession().getAttribute("logUser");
		if (null != loggedInUser) {
			model.addAttribute("loggedInUser", loggedInUser);
		}
		
		return "activityTypes/createForm";
	}
	
	/**
	 * Handler pro editaci stavajici Aktivity.
	 * Nejprve overi, zda byl vyplnen novy nazev aktivity. 
	 * - Pokud ne, vrati uzivatele na formular s upozornenim na povinnost vyplnit nazev aktivity.
	 * Pote overi, zda byla zvolena fotografie aktivity.
	 * - Pokud ne, vypise do konzole zpravu o nevyplneni pole fotografie a vrati uzivatele na formular.
	 * - Pokud ano, pokusi se smazat puvodni obrazek z uloziste, nahrat novy obrazek a vlozit zaznam do databaze.
	 */
	@RequestMapping(value="/activityTypes/{activityTypeId}/edit", method = {RequestMethod.PUT, RequestMethod.POST})
	public String processEditSubmit(@ModelAttribute ActivityType actvityType, BindingResult result, SessionStatus status, @RequestParam("file") MultipartFile file) {	
		new ActivityTypeValidator().validate(actvityType, result);
		if (result.hasErrors()) {
			return "activityTypes/createForm";
		}
		else {			
			if (!file.isEmpty()) {				
				// Smazani puvodn�ho obrazku z uloziste.
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
	
	/**
	 * Handler pro smazani Aktivity dle zadaneho id.
	 * Nejprve odstrani fotografii Aktivity ze slozky activityTypeImages, pote vymaze zaznam z databaze.
	 */
	@RequestMapping(value="/activityTypes/{activityTypeId}/delete")
	public String deleteActivityType(@PathVariable int activityTypeId) {
		ActivityType activityType = this.fitnessCentre.loadActivityType(activityTypeId);
		String illustrationImageName = activityType.getIllustrationImageName();	
		String illustrationImagePath = myProjectPath + File.separator + "activityTypeImages" + File.separator + illustrationImageName;
		File illustrationImage = new File(illustrationImagePath);
		
		// Sma�e soubor a z�rove� vrac� bool, jestli byl soubor �sp�n� smaz�n.
		if (illustrationImage.delete()) {
			logger.info("Smaz�n obr�zek: " + illustrationImageName);
		} else {
			logger.info("Nezda�ilo se smazat obr�zek: " + illustrationImageName + " z um�st�n� " + illustrationImagePath);
		}
				
		this.fitnessCentre.deleteActivityType(activityTypeId);
		return "redirect:/activityTypes/index";	
	}
	
}
