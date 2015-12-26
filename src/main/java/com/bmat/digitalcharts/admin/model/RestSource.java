package com.bmat.digitalcharts.admin.model;

import javax.persistence.Entity;

@Entity
public class RestSource extends AbstractEntity {
	
	private static final long serialVersionUID = -529758520498857835L;
	
	private String name;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

}
