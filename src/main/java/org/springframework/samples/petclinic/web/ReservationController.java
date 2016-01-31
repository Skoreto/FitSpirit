package org.springframework.samples.petclinic.web;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.FitnessCentre;
import org.springframework.samples.petclinic.Lesson;
import org.springframework.samples.petclinic.Lessons;
import org.springframework.samples.petclinic.Reservation;
import org.springframework.samples.petclinic.Reservations;
import org.springframework.samples.petclinic.User;
import org.springframework.samples.petclinic.util.ProjectUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
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
		new ProjectUtils(fitnessCentre).setExpiredLessons();
		
		// Predani titulku stranky do view
		String pageTitle = "Rezervace";
		model.addAttribute("pageTitle", pageTitle);
				
		// Predani seznamu lekci pro widget
		Lessons lessons = new Lessons();
		lessons.getLessonList().addAll(this.fitnessCentre.getLessons());
		model.addAttribute("lessonsForWidget", lessons);
		
		// Pristup k session prihlaseneho uzivatele
		User loggedInUser = (User)request.getSession().getAttribute("logUser");
		if (null != loggedInUser) {
			model.addAttribute("loggedInUser", loggedInUser);
			String loggedInUserRoleIdent = loggedInUser.getUserRole().getIdentificator();
			
			if (loggedInUserRoleIdent.equals("klient")) {
				// Filtrace rezervaci pouze pro prihlaseneho klienta.
				Reservations clientReservations = new Reservations();
				List<Reservation> allReservations = new ArrayList<Reservation>();
				allReservations.addAll(this.fitnessCentre.getReservations());
							
				for (Reservation reservation : allReservations) {
					if (reservation.getClient().getId().equals(loggedInUser.getId())) {
						// Protoze rezervaci lekce lze zrusit nejdele do 6 hodin pred 
						// zahajenim lekce, je zde nastaveno, zda rezervaci lze jeste zrusit.
						Timestamp actualTime = new Timestamp(new Date().getTime());						
						Timestamp sixHoursBeforeStart = new Timestamp(reservation.getLesson().getStartTime().getTime() - 21600000);	
						
						// Porovnani compareTo vraci hodnoty -1, 0, 1. 
						if (actualTime.compareTo(sixHoursBeforeStart) > 0) {
							reservation.setCancellable(false);
						} else {
							reservation.setCancellable(true);
						}
						
						// Pridani rezervace do seznamu rezervaci prihlaseneho klienta.
						clientReservations.getReservationList().add(reservation);
					}
				}		
				
				model.addAttribute("reservations", clientReservations);							
				return "reservations/index";
			}
		}
		
		return "index";	
	}
	
	/**
	 * Handler pro rezervaci vybrane Lekce.
	 */
	@RequestMapping("/reservations/{lessonId}/reserve")
	public String lessonReservationHandler(@PathVariable("lessonId") int lessonId, HttpServletRequest request) {
		
		Lesson lesson = this.fitnessCentre.loadLesson(lessonId);
		
		// Pristup k session prihlaseneho uzivatele
		User loggedInUser = (User)request.getSession().getAttribute("logUser");
		
		// Overeni, zda lekce je aktivni (z duvodu casu ci aktivnosti instruktora).
		if (lesson.isActive()) {
			// Overeni, zda na lekci je volne misto pro registraci.
			if (lesson.getActualCapacity() > 0) {
				// Overeni, zda ma klient dostatecny kredit pro rezervaci aktivity.
				if (loggedInUser.getCredit() >= lesson.getActivityType().getPrice()) {
					// Nastaveni parametru rezervace.
					Reservation reservation = new Reservation();
					reservation.setLesson(lesson);
					reservation.setClient(loggedInUser);
					
					Timestamp actualTime = new Timestamp(new Date().getTime());
					reservation.setReservationTime(actualTime);
					
					Timestamp sixHoursBeforeStart = new Timestamp(lesson.getStartTime().getTime() - 21600000);		
					// Porovnani compareTo vraci hodnoty -1, 0, 1. 
					// Pokud aktualni cas je v rozmezi 6 hodin pred zahajenim lekce a vice, pak jiz rezervaci nelze zrusit.
					if (actualTime.compareTo(sixHoursBeforeStart) > 0) {
						reservation.setCancellable(false);
					} else {
						reservation.setCancellable(true);
					}
								
					// Vlozeni rezervace do databaze.
					this.fitnessCentre.storeReservation(reservation);
					
					// Odecteni ceny aktivity z kreditu klienta a odecteni 1 volneho mista z kapacity lekce. 
					// Update hodnot v databazi.
					loggedInUser.setCredit(loggedInUser.getCredit() - lesson.getActivityType().getPrice());
					this.fitnessCentre.storeUser(loggedInUser);
					lesson.setActualCapacity(lesson.getActualCapacity() - 1);
					this.fitnessCentre.storeLesson(lesson);
					return "redirect:/lessons/index";
				}
							
			}
					
		}
		// TODO Oznameni o chybach v ruznych pripadech.		
		return "redirect:/lessons/index";
	}
	
		
}
