package com.bmat.digitalcharts.admin.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Country extends AbstractEntity {
	
	private static final long serialVersionUID = -5598664929828959588L;

	private String name;
	
	@Column(name="code2")
	private String code;
	
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
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
}

