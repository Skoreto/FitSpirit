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

import cz.uhk.fim.fitspirit.FitnessCentre;
import cz.uhk.fim.fitspirit.Lessons;
import cz.uhk.fim.fitspirit.User;
import cz.uhk.fim.fitspirit.UserRole;
import cz.uhk.fim.fitspirit.Users;
import cz.uhk.fim.fitspirit.util.ProjectUtils;
import cz.uhk.fim.fitspirit.validation.UserValidator;

/**
 * Controller pro handlovani Obsluhy v systemu.
 * @author Tomas Skorepa
 */
@Controller
@SessionAttributes("user")
public class StaffController {
	
	private final FitnessCentre fitnessCentre;
	
	private static final Logger logger = LoggerFactory.getLogger(StaffController.class);

	private final String myProjectPath = ProjectUtils.getMyProjectPath();
	
	@Autowired
	public StaffController(FitnessCentre fitnessCentre) {
		this.fitnessCentre = fitnessCentre;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	/**
	 * Handler pro zobrazeni seznamu obsluhy.
	 */
	@RequestMapping("/staffs/index")
	public String staffsHandler(Model model, HttpServletRequest request) {	
		// Predani seznamu obsluhy do view.
		Users staffs = new Users();
		staffs.getUserList().addAll(this.fitnessCentre.getStaffs());
		model.addAttribute("staffs", staffs);
		
		// Predani titulku stranky do view
		String pageTitle = "Obsluha";
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
				return "staffs/indexStaff";
			}			
		}
			
		return "index";
	}
	
	/**
	 * Handler pro zobrazeni detailu obsluhy.
	 * Na zacatku presmerovani na index jako ochrana proti neopravnenemu pristupu.
	 */
	@RequestMapping("/staffs/{staffId}")
	public ModelAndView clientHandler(@PathVariable("staffId") int staffId, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("index");
		User staff = this.fitnessCentre.loadUser(staffId);
		mav.addObject(staff);
		
		// Predani titulku stranky do view
		String pageTitle = "Detail obsluhy " + staff.getFirstName() + " " + staff.getLastName();
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
				mav.setViewName("staffs/detail");
				return mav;
			}		
		}
		
		return mav;
	}
	
	/**
	 * Handler pro zobrazeni formulare pro vytvoreni nove Obsluhy.
	 */
	@RequestMapping(value="/staffs/create", method = RequestMethod.GET)
	public String setupForm(Model model, HttpServletRequest request) {
		User user = new User();
		model.addAttribute(user);
		
		// Predani titulku stranky do view
		String pageTitle = "Nová obsluha";
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
		
		return "staffs/createForm";
	}
	
	/**
	 * Handler pro vytvoreni nove Obsluhy.
	 */
	@RequestMapping(value="/staffs/create", method = RequestMethod.POST)
	public String processSubmit(@ModelAttribute User staff, BindingResult result, SessionStatus status, @RequestParam("file") MultipartFile file) {
		new UserValidator().validate(staff, result);
		if (result.hasErrors()) {
			return "staffs/createForm";
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
	                
	                staff.setProfilePhotoName(originalFileName);
	                
	                UserRole staffRole = this.fitnessCentre.loadUserRole(1);	// id role obsluhy = 1
	                staff.setUserRole(staffRole);
	                staff.setActive(true);
	                
	                // Generovani loginu
	                int usersCount = this.fitnessCentre.getUsers().size();
	                int login = usersCount + 1;
	                staff.setLogin(login);
	                
	                this.fitnessCentre.storeUser(staff);
	    			status.setComplete();
	    			return "redirect:/staffs/index";	               	                
	            } catch (Exception e) {
	                return "Nepodarilo se uploadnout " + file.getOriginalFilename() + " CHYBA => " + e.getMessage();
	            }
	        } else {
	        	logger.info("Nepodarilo se uploadnout " + file.getOriginalFilename() + " protoze soubor je prazdny.");
	        	return "staffs/createForm";
	        }						
		}
	}
	

}
