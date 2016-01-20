package org.springframework.samples.petclinic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="ROOMS")
public class Room extends BaseEntity {
	
	private String name;
	
	private String illustrationImageName;
	
//	private String illustrationThumbImageName;

	@Column(name="NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="ILLUSTRATION_IMAGE_NAME")
	public String getIllustrationImageName() {
		return illustrationImageName;
	}

	public void setIllustrationImageName(String illustrationImageName) {
		this.illustrationImageName = illustrationImageName;
	}
	
	
		
}
