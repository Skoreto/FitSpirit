package cz.uhk.fim.fitspirit.validation;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

import cz.uhk.fim.fitspirit.Room;

/**
 * Validátor pro formuláø místnosti.
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
