package com.bmat.digitalcharts.admin.model;

import javax.persistence.Entity;

@Entity
public class Country extends AbstractEntity {
	
	private static final long serialVersionUID = -5598664929828959588L;

	private String name;
	
	public Country(){}
	
	public Country(Long id){
		this.setId(id);
	}
	
	public Country(Long id, String name) {
		this(id);
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}

