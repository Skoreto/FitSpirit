
package org.springframework.samples.petclinic.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.FitnessCentre;
import org.springframework.samples.petclinic.Lessons;
import org.springframework.samples.petclinic.User;
import org.springframework.samples.petclinic.util.ProjectUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Annotation-driven <em>MultiActionController</em> that handles all non-form
 * URL's.
 */
@Controller
public class FitnessCentreController {

	private final FitnessCentre fitnessCentre;

	@Autowired
	public FitnessCentreController(FitnessCentre fitnessCentre) {
		this.fitnessCentre = fitnessCentre;
	}

	/**
	 * Handler pro prvotni presmerovani.
	 */
	@RequestMapping("/")
	public String welcomeHandler(Model model, HttpServletRequest request) {
		
		// Predani titulku stranky do view
		String pageTitle = "Hlavní strana";
		model.addAttribute("pageTitle", pageTitle);
		
		// Predani seznamu lekci pro widget
		new ProjectUtils(fitnessCentre).setExpiredLessons();
		Lessons activeLessons = new Lessons();
		activeLessons.getLessonList().addAll(this.fitnessCentre.getActiveLessons());
		model.addAttribute("lessonsForWidget", activeLessons);
		
		// Pristup k session prihlaseneho uzivatele
		User loggedInUser = (User)request.getSession().getAttribute("logUser");
		if (null != loggedInUser) {
			model.addAttribute("loggedInUser", loggedInUser);
		}
		
		return "index";
	}
	
	/**
	 * Handler pro zobrazeni Hlavni stranky fitness centra.
	 */
	@RequestMapping("/index")
	public String indexHandler(Model model, HttpServletRequest request) {
			
		// Predani titulku stranky do view
		String pageTitle = "Hlavní strana";
		model.addAttribute("pageTitle", pageTitle);
		
		// Predani seznamu lekci pro widget
		new ProjectUtils(fitnessCentre).setExpiredLessons();
		Lessons activeLessons = new Lessons();
		activeLessons.getLessonList().addAll(this.fitnessCentre.getActiveLessons());
		model.addAttribute("lessonsForWidget", activeLessons);
		
		// Kod, ktery musi byt v kazde GET mapovane URL, kde potrebujeme pristup k session (vsude)
		// Bylo by vhodne udelat nejaky BaseController jako rodice kazdeho controlleru, aby tento kod nemusel byt duplicitne...
		// Pristup k session prihlaseneho uzivatele
		User loggedInUser = (User)request.getSession().getAttribute("logUser");
		if (null != loggedInUser) {
			model.addAttribute("loggedInUser", loggedInUser);
		}
		
		return "index";
	}
	
	/**
	 * Handler pro zobrazeni JSP Prihlasovaci obrazovky.
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginHandler(Model model) {		
		return "login/login";
	}
	
	/**
	 * Handler pro obsluhu prihlaseni uzivatele do systemu.
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginUser(HttpServletRequest request, Model model) {
		
		// Univerzalni chybova hlaska jako obrana proti crackerum
		String errorString = "Neexistujicí uživatel nebo špatné heslo!";
		
		// Test, zda ma smysl pokracovat dal
		if ("".equals(request.getParameter("login")) // je login nevyplneny?
			|| "".equals(request.getParameter("password")) // je heslo nevyplnene?
			|| !request.getParameter("login").matches("^-?\\d+$")) { // je login neco jineho nez cislo?
			// Do view predame promennou errorString, ktera bude ve view pristupna jako ${message}
			model.addAttribute("message", errorString);
			return "login/login";
		}
		
		// Promenne z POST dat
		int login = new Integer(request.getParameter("login"));
		String password = request.getParameter("password");
		
		// Na zaklade POST dat najdeme uzivatele
		User user = this.fitnessCentre.loadUser(login);
		
		// Pokud zadny neexistuje, tak uzivateli vynadame
		if (null == user) {
			model.addAttribute("message", errorString);
			return "login/login";
		}
		
		// Pokud heslo neodpovida, uzivateli zase vynadame
		if (!user.getPassword().equals(password)) {
			model.addAttribute("message", errorString);
			return "login/login";
		}
		
		// Jinak je vse v poradku a uzivatele si ulozime do session
		request.getSession().setAttribute("logUser", user);

		// Takto pak muzeme kdekoliv k uzivateli pristoupit
		// User loggedInUser = (User)request.getSession().getAttribute("user");
		//
		// Chceme-li zjistit roli uzivatele, zavolame jen
		// loggedInUser.getUserRole().getIdentificator();
		//
		// Zbytek analogicky, je to prece User user jako kazdej jinej
		
		// Po prihlaseni presmerujeme na homepage
		return "redirect:/index";		
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutUser(HttpServletRequest request, Model model) {
		
		// Vycistime data prihlaseneho uzivatele ze session
		request.getSession().removeAttribute("logUser");
		// Uzivatele presmerujeme na homepage
		return "redirect:/index";		
	}

	public static boolean isInteger(String s, int radix) {
	    if(s.isEmpty()) return false;
	    for(int i = 0; i < s.length(); i++) {
	        if(i == 0 && s.charAt(i) == '-') {
	            if(s.length() == 1) return false;
	            else continue;
	        }
	        if(Character.digit(s.charAt(i),radix) < 0) return false;
	    }
	    return true;
	}	
	
}
