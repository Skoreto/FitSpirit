package cz.uhk.fim.fitspirit.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import cz.uhk.fim.fitspirit.FitnessCentre;
import cz.uhk.fim.fitspirit.Lessons;
import cz.uhk.fim.fitspirit.User;
import cz.uhk.fim.fitspirit.Users;
import cz.uhk.fim.fitspirit.util.ProjectUtils;

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
	

}
