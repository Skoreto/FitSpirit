
package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.ActivityTypes;
import org.springframework.samples.petclinic.FitnessCentre;
import org.springframework.samples.petclinic.Rooms;
import org.springframework.samples.petclinic.User;
import org.springframework.samples.petclinic.Users;
import org.springframework.samples.petclinic.Vets;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Annotation-driven <em>MultiActionController</em> that handles all non-form
 * URL's.
 */
@Controller
public class FitnessCentreController {

	private final FitnessCentre fitnessCentre;

	@Autowired
	public FitnessCentreController(FitnessCentre clinic) {
		this.fitnessCentre = clinic;
	}

	/**
	 * Custom handler for the welcome view.
	 * <p>
	 * Note that this handler relies on the RequestToViewNameTranslator to
	 * determine the logical view name based on the request URL: "/welcome.do"
	 * -&gt; "welcome".
	 */
	@RequestMapping("/")
	public String welcomeHandler() {
		return "welcome";
	}
	
	/**
	 * Handler pro zobrazeni hlavni stranky fitness centra.
	 */
	@RequestMapping("/index")
	public String indexHandler() {
		return "index";
	}

	/**
	 * Custom handler for displaying vets.
	 *
	 * <p>Note that this handler returns a plain {@link ModelMap} object instead of
	 * a ModelAndView, thus leveraging convention-based model attribute names.
	 * It relies on the RequestToViewNameTranslator to determine the logical
	 * view name based on the request URL: "/vets.do" -&gt; "vets".
	 *
	 * @return a ModelMap with the model attributes for the view
	 */
	@RequestMapping("/vets")
	public ModelMap vetsHandler() {
		Vets vets = new Vets();
		vets.getVetList().addAll(this.fitnessCentre.getVets());
		return new ModelMap(vets);
	}
	
	
	/**
	 * Vlastn� handler pro zobrazen� m�stnost�.
	 * 
	 * @return ModelMap s atributy modelu pro dan� view
	 */
	@RequestMapping("/rooms/index")
	public ModelMap roomsHandler() {
		Rooms rooms = new Rooms();
		rooms.getRoomList().addAll(this.fitnessCentre.getRooms());
		return new ModelMap(rooms);
	}
	
	/**
	 * Vlastn� handler pro zobrazen� aktivit.
	 * 
	 * @return ModelMap s atributy modelu pro dan� view
	 */
	@RequestMapping("/activityTypes/index")
	public ModelMap activityTypesHandler() {
		ActivityTypes activityTypes = new ActivityTypes();
		activityTypes.getActivityTypeList().addAll(this.fitnessCentre.getActivityTypes());
		return new ModelMap(activityTypes);
	}
	
	/**
	 * Vlastni handler pro zobrazeni instruktoru.
	 * Nejprve ziska seznam vsech uzivatelu. Z nich vybere ty, s id instruktora, a naplni
	 * je do pomocneho seznamu instructorUsers. Teprve pomocny seznam instructorUsers
	 * preleje do seznamu instructors tridy Users, ktery slouzi pro ucely odkazani ve view.
	 * 
	 * @return ModelMap s atributy modelu pro dan� view
	 */
	@RequestMapping("/instructors/index")
	public ModelMap instructorsHandler() {
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
		return new ModelMap(instructors);
	}
		
	/**
	 * Custom handler for displaying an owner.
	 *
	 * @param ownerId the ID of the owner to display
	 * @return a ModelMap with the model attributes for the view
	 */
	@RequestMapping("/owners/{ownerId}")
	public ModelAndView ownerHandler(@PathVariable("ownerId") int ownerId) {
		ModelAndView mav = new ModelAndView("owners/show");
		mav.addObject(this.fitnessCentre.loadOwner(ownerId));
		return mav;
	}
	
	/**
	 * Vlastni handler pro zobrazeni detailu o mistnosti.
	 */
	@RequestMapping("/rooms/{roomId}")
	public ModelAndView roomHandler(@PathVariable("roomId") int roomId) {
		ModelAndView mav = new ModelAndView("rooms/detail");
		mav.addObject(this.fitnessCentre.loadRoom(roomId));
		return mav;
	}
	
	/**
	 * Vlastni handler pro zobrazeni detailu o aktivite.
	 */
	@RequestMapping("/activityTypes/{activityTypeId}")
	public ModelAndView activityTypeHandler(@PathVariable("activityTypeId") int activityTypeId) {
		ModelAndView mav = new ModelAndView("activityTypes/detail");
		mav.addObject(this.fitnessCentre.loadActivityType(activityTypeId));
		return mav;
	}
	
	/**
	 * Handler pro zobrazeni detailu instruktora.
	 */
	@RequestMapping("/instructors/{instructorId}")
	public ModelAndView instructorHandler(@PathVariable("instructorId") int instructorId) {
		ModelAndView mav = new ModelAndView("instructors/detail");
		mav.addObject(this.fitnessCentre.loadUser(instructorId));
		return mav;
	}
	
	/**
	 * Custom handler for displaying an list of visits.
	 *
	 * @param petId the ID of the pet whose visits to display
	 * @return a ModelMap with the model attributes for the view
	 */
	@RequestMapping(value="/owners/*/pets/{petId}/visits", method=RequestMethod.GET)
	public ModelAndView visitsHandler(@PathVariable int petId) {
		ModelAndView mav = new ModelAndView("visits");
		mav.addObject("visits", this.fitnessCentre.loadPet(petId).getVisits());
		return mav;
	}
	
	/**
	 * Handler pro JSP k nahr�v�n� obr�zku.
	 * @return
	 */
	@RequestMapping("/upload")
	public String uploadHandler() {
		return "upload";
	}
	
	/**
	 * Handler pro zobrazeni JSP Prihlasovaci obrazovky.
	 */
	@RequestMapping("/login")
	public String loginHandler() {
		return "login/login";
	}
	
}
