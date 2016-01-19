package org.springframework.samples.petclinic.validation;

import org.springframework.samples.petclinic.Room;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

/**
 * Valid�tor pro formul�� m�stnosti.
 * @author Tomas Skorepa
 *
 */
public class RoomValidator {

	public void validate(Room room, Errors errors) {
		if (!StringUtils.hasLength(room.getName())) {
			errors.rejectValue("name", "required", "required");
		}
	}
	
}
