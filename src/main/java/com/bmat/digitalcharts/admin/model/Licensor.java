package com.bmat.digitalcharts.admin.model;

import javax.persistence.Entity;

@Entity
public class Licensor extends AbstractEntity {

	private static final long serialVersionUID = 7947907325736930571L;
	
	private String name;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
