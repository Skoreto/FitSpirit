package org.springframework.samples.petclinic.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.FitnessCentre;
import org.springframework.samples.petclinic.Lessons;
import org.springframework.samples.petclinic.Reservations;
import org.springframework.samples.petclinic.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * Controller pro handlovani Rezervaci v systemu.
 * @author Tomas Skorepa
 */
@Controller
@SessionAttributes("reservation")
public class ReservationController {

	private final FitnessCentre fitnessCentre;
	
	@Autowired
	public ReservationController(FitnessCentre fitnessCentre) {
		this.fitnessCentre = fitnessCentre;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	/**
	 * Handler pro zobrazení seznamu Lekci.
	 */
	@RequestMapping("/reservations/index")
	public String activityTypesHandler(Model model, HttpServletRequest request) {
		Reservations reservations = new Reservations();
		reservations.getReservationList().addAll(this.fitnessCentre.getReservations());
				
		model.addAttribute("reservations", reservations);
		
		// Predani titulku stranky do view
		String pageTitle = "Rezervace";
		model.addAttribute("pageTitle", pageTitle);
		
		
		// Predani seznamu lekci pro widget
		Lessons lessons = new Lessons();
		lessons.getLessonList().addAll(this.fitnessCentre.getLessons());
		model.addAttribute("lessonsForWidget", lessons);
		
		// Pristup k session prihlaseneho uzivatele
		User loggedInUser = (User)request.getSession().getAttribute("user");
		if (null != loggedInUser) {
			model.addAttribute("loggedInUser", loggedInUser);
			String loggedInUserRoleIdent = loggedInUser.getUserRole().getIdentificator();
			
			if (loggedInUserRoleIdent.equals("klient")) {
				return "reservations/index";
			}
		}
		
		return "index";	
	}
	
	
	
}
