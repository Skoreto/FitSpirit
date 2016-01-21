package org.springframework.samples.petclinic.web;

import java.beans.PropertyEditorSupport;

import org.springframework.samples.petclinic.FitnessCentre;
import org.springframework.samples.petclinic.PetType;

/**
 * @author Mark Fisher
 * @author Juergen Hoeller
 */
public class PetTypeEditor extends PropertyEditorSupport {

	private final FitnessCentre fitnessCentre;


	public PetTypeEditor(FitnessCentre fitnessCentre) {
		this.fitnessCentre = fitnessCentre;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		for (PetType type : this.fitnessCentre.getPetTypes()) {
			if (type.getName().equals(text)) {
				setValue(type);
			}
		}
	}

}
