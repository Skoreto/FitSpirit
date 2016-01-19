package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.Clinic;
import org.springframework.samples.petclinic.Owner;
import org.springframework.samples.petclinic.Room;
import org.springframework.samples.petclinic.validation.OwnerValidator;
import org.springframework.samples.petclinic.validation.RoomValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

/**
 * JavaBean form controller pro pøidání nové místnosti do systému.
 * @author Tomas Skorepa
 */
@Controller
@RequestMapping("/rooms/create")
public class CreateRoomForm {

	private final Clinic clinic;
	
	@Autowired
	public CreateRoomForm(Clinic clinic) {
		this.clinic = clinic;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String setupForm(Model model) {
		Room room = new Room();
		model.addAttribute(room);
		return "rooms/createForm";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(@ModelAttribute Room room, BindingResult result, SessionStatus status) {
		new RoomValidator().validate(room, result);
		if (result.hasErrors()) {
			return "rooms/createForm";
		}
		else {
			this.clinic.storeRoom(room);
			status.setComplete();
//			return "redirect:/rooms/" + room.getId();
			return "redirect:/rooms/index";
		}
	}
	
	
}
