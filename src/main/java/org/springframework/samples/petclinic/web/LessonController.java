package org.springframework.samples.petclinic.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.ActivityType;
import org.springframework.samples.petclinic.ActivityTypes;
import org.springframework.samples.petclinic.FitnessCentre;
import org.springframework.samples.petclinic.Lesson;
import org.springframework.samples.petclinic.Lessons;
import org.springframework.samples.petclinic.PetType;
import org.springframework.samples.petclinic.Room;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controller pro handlovani Lekci v systemu.
 * @author Tomas Skorepa
 */
@Controller
@SessionAttributes("lesson")
public class LessonController {

	private final FitnessCentre fitnessCentre;
	
	private static final Logger logger = LoggerFactory.getLogger(LessonController.class);
	
	private final String myProjectPath = ProjectUtils.getMyProjectPath();
	
	@Autowired
	public LessonController(FitnessCentre fitnessCentre) {
		this.fitnessCentre = fitnessCentre;
	}
	
	@ModelAttribute("activityTypes")
	public Collection<ActivityType> populateActivityTypes() {
		return this.fitnessCentre.getActivityTypes();
	}
	
	@ModelAttribute("rooms")
	public Collection<Room> populateRooms() {	
		return this.fitnessCentre.getRooms();
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	/**
	 * Handler pro zobrazení seznamu Lekci.
	 */
	@RequestMapping("/lessons/index")
	public String activityTypesHandler(Model model, HttpServletRequest request) {
		Lessons lessons = new Lessons();
		lessons.getLessonList().addAll(this.fitnessCentre.getLessons());
		
		model.addAttribute("lessons", lessons);
		
		// Predani titulku stranky do view
		String pageTitle = "Lekce";
		model.addAttribute("pageTitle", pageTitle);
		
		// Predani seznamu lekci pro widget
		model.addAttribute("lessonsForWidget", lessons);
		
		// Pristup k session prihlaseneho uzivatele
		User loggedInUser = (User)request.getSession().getAttribute("user");
		if (null != loggedInUser) {
			model.addAttribute("loggedInUser", loggedInUser);
			String loggedInUserRoleIdent = loggedInUser.getUserRole().getIdentificator();
			
			if (loggedInUserRoleIdent.equals("obsluha")) {
				return "lessons/indexStaff";
			}	
			if (loggedInUserRoleIdent.equals("instruktor")) {
				return "lessons/indexInstructor";
			}
			if (loggedInUserRoleIdent.equals("klient")) {
				return "lessons/indexClient";
			}
		}
		
		return "lessons/index";	
	}
	
	/**
	 * Handler pro zobrazeni formulare pro vytvoreni nove Lekce.
	 */
	@RequestMapping(value="/lessons/create", method = RequestMethod.GET)
	public String setupForm(Model model, HttpServletRequest request) {
		Lesson lesson = new Lesson();
		model.addAttribute(lesson);
			
		// Predani titulku stranky do view
		String pageTitle = "Nová lekce";
		model.addAttribute("pageTitle", pageTitle);
		
		// Predani seznamu lekci pro widget
		Lessons lessons = new Lessons();
		lessons.getLessonList().addAll(this.fitnessCentre.getLessons());
		model.addAttribute("lessonsForWidget", lessons);
		
		// Pristup k session prihlaseneho uzivatele
		User loggedInUser = (User)request.getSession().getAttribute("user");
		if (null != loggedInUser) {
			model.addAttribute("loggedInUser", loggedInUser);
		}
		
		return "lessons/createForm";
	}
	
	/**
	 * Handler pro vytvoreni nove Lekce.
	 * Vytvari pouze aktualne prihlaseny instruktor.
	 */
	@RequestMapping(value="/lessons/create", method = RequestMethod.POST)
	public String processSubmit(SessionStatus status, HttpServletRequest request, @RequestParam("startTime") String startTime, 
			@RequestParam("endTime") String endTime, @RequestParam("originalCapacity") int originalCapacity, 
			@RequestParam("activityType") int activityTypeId, @RequestParam("room") int roomId, @RequestParam("description") String description) {
		
		Lesson lesson = new Lesson();
		
		// Parsovani casu ze Stringu z datepickeru
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy H:mm");
		Date startTimeDate;
		Date endTimeDate;
		try {
			startTimeDate = df.parse(startTime);
			Timestamp startTimeTS = new Timestamp(startTimeDate.getTime());
			lesson.setStartTime(startTimeTS);
			
			endTimeDate = df.parse(endTime);
			Timestamp endTimeTS = new Timestamp(endTimeDate.getTime());
			lesson.setEndTime(endTimeTS);
		} catch (ParseException e) {
			System.out.println("Nepodarilo se naparsovat èas!!!!!");
			e.printStackTrace();
		}
		
		// Pri vytvoreni nove lekce aktualni kapacita = originalni kapacite
		int actualCapacity = originalCapacity;
		lesson.setOriginalCapacity(originalCapacity);
		lesson.setActualCapacity(actualCapacity);
				
		lesson.setActivityType(this.fitnessCentre.loadActivityType(activityTypeId));
		lesson.setRoom(this.fitnessCentre.loadRoom(roomId));
		
		// Predani intruktora jako prave prihlaseneho uzivatele
		User loggedInUser = (User)request.getSession().getAttribute("user");	
		lesson.setInstructor(loggedInUser);

		lesson.setDescription(description);
        lesson.setActive(true);
        lesson.setReserved(false);
        
        this.fitnessCentre.storeLesson(lesson);
		status.setComplete();
		return "redirect:/lessons/index";						
	}	
	
		
}