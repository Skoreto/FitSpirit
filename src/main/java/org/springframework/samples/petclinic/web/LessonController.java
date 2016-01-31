package org.springframework.samples.petclinic.web;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.ActivityType;
import org.springframework.samples.petclinic.FitnessCentre;
import org.springframework.samples.petclinic.Lesson;
import org.springframework.samples.petclinic.Lessons;
import org.springframework.samples.petclinic.Reservation;
import org.springframework.samples.petclinic.Room;
import org.springframework.samples.petclinic.User;
import org.springframework.samples.petclinic.Users;
import org.springframework.samples.petclinic.util.ProjectUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller pro handlovani Lekci v systemu.
 * @author Tomas Skorepa
 */
@Controller
@SessionAttributes("lesson")
public class LessonController {

	private final FitnessCentre fitnessCentre;
	
	@Autowired
	public LessonController(FitnessCentre fitnessCentre) {
		this.fitnessCentre = fitnessCentre;
	}
	
	// ModelAttribute pro select box
	@ModelAttribute("activityTypes")
	public Collection<ActivityType> populateActivityTypes() {
		return this.fitnessCentre.getActivityTypes();
	}
	
	// ModelAttribute pro select box
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
		new ProjectUtils(fitnessCentre).setExpiredLessons();
		Lessons lessons = new Lessons();
		lessons.getLessonList().addAll(this.fitnessCentre.getLessons());
		
		// Predani titulku stranky do view
		String pageTitle = "Lekce";
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
				model.addAttribute("lessons", lessons);
				return "lessons/indexStaff";
			}	
			if (loggedInUserRoleIdent.equals("instruktor")) {
				model.addAttribute("lessons", lessons);
				return "lessons/indexInstructor";
			}
			if (loggedInUserRoleIdent.equals("klient")) {								
				List<Reservation> clientReservations = new ArrayList<Reservation>();
				List<Reservation> allReservations = new ArrayList<Reservation>();
				allReservations.addAll(this.fitnessCentre.getReservations());
				
				// Ziskani seznamu rezervaci prihlaseneho klienta.
				for (Reservation reservation : allReservations) {
					if (reservation.getClient().getId().equals(loggedInUser.getId())) {
						clientReservations.add(reservation);
					}			
				}
				
				// Vyznaceni rezervovanych lekci pro prihlaseneho klienta.
				for (Lesson lesson : lessons.getLessonList()) {
					lesson.setReserved(false);	// Pro jistotu vyplneni vlastnosti.
					
					for (Reservation reservation : clientReservations) {
						if (reservation.getLesson().getId().equals(lesson.getId())) {
							lesson.setReserved(true);
						}					
					}
				}
				
				model.addAttribute("lessons", lessons);			
				return "lessons/indexClient";
			}
		}
		
		model.addAttribute("lessons", lessons);
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
		User loggedInUser = (User)request.getSession().getAttribute("logUser");
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
		User loggedInUser = (User)request.getSession().getAttribute("logUser");	
		lesson.setInstructor(loggedInUser);

		lesson.setDescription(description);
        lesson.setActive(true);
        lesson.setReserved(false);
        
        this.fitnessCentre.storeLesson(lesson);
		status.setComplete();
		return "redirect:/lessons/index";						
	}	
	
	/**
	 * Handler pro zobrazeni detailu Lekce.
	 */
	@RequestMapping("/lessons/{lessonId}")
	public ModelAndView instructorHandler(@PathVariable("lessonId") int lessonId, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("lessons/detail");
		Lesson lesson = this.fitnessCentre.loadLesson(lessonId);
		mav.addObject(lesson);
		
		// Predani klientu rezervovanych na vybranou lekci do JSP
		List<User> reservedClients = new ArrayList<User>();		
		List<Reservation> allReservations = new ArrayList<Reservation>();
		allReservations.addAll(this.fitnessCentre.getReservations());
		
		// Projde vsechny rezervace. Pokud nalezne rezervaci na vybranou lekci, prida
		// klienta, ktery vlastni tuto rezervaci do seznamu klientu rezervovanych na vybranou lekci.
		for (Reservation reservation : allReservations) {
			if (reservation.getLesson().getId().equals(lesson.getId())) {
				reservedClients.add(this.fitnessCentre.loadUser(reservation.getClient().getId()));
			}	
		}
		
		Users reservedClientsList = new Users();
		reservedClientsList.getUserList().addAll(reservedClients);		
		mav.addObject("users", reservedClientsList);
		
		// Predani titulku stranky do view
		String pageTitle = "Detail lekce " + lesson.getActivityType().getName();
		mav.addObject("pageTitle", pageTitle);
		
		// Predani seznamu lekci pro widget
		Lessons lessons = new Lessons();
		lessons.getLessonList().addAll(this.fitnessCentre.getLessons());
		mav.addObject("lessonsForWidget", lessons);
		
		// Pristup k session prihlaseneho uzivatele
		User loggedInUser = (User)request.getSession().getAttribute("logUser");
		if (null != loggedInUser) {
			mav.addObject("loggedInUser", loggedInUser);
			String loggedInUserRoleIdent = loggedInUser.getUserRole().getIdentificator();
			
			if (loggedInUserRoleIdent.equals("obsluha")) {
				mav.setViewName("lessons/detailStaff");
			}		
		}
			
		return mav;
	}
	
		
}