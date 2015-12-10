package com.bmat.digitalcharts.admin.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Song extends AbstractEntity {
	
	private static final long serialVersionUID = 5419845727163340242L;

	private String composers;
	
	@NotNull @NotBlank
	private String name;
	
	@ManyToOne
	@JoinColumn(name="performerId")
	private Performer performer;

	public String getComposers() {
		return composers;
	}

	public void setComposers(String composers) {
		this.composers = composers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Performer getPerformer() {
		return performer;
	}

	public void setPerformer(Performer performer) {
		this.performer = performer;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	

}
