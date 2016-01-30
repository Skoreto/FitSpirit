package org.springframework.samples.petclinic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="ACTIVITY_TYPES")
public class ActivityType extends BaseEntity {
	private String name;
	
	// Cena za jednu lekci dane aktivity.
	// TODO Mel by byt BigDecimal.
	private int price;
	
	private String illustrationImageName;
	
//	private String illustrationThumbImageName;
	
	private String shortDescription;
	
	private String description;
	
	@Column(name="NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="PRICE")
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Column(name="ILLUSTRATION_IMAGE_NAME")
	public String getIllustrationImageName() {
		return illustrationImageName;
	}

	public void setIllustrationImageName(String illustrationImageName) {
		this.illustrationImageName = illustrationImageName;
	}

	@Column(name="SHORT_DESCRIPTION")
	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
		
}
