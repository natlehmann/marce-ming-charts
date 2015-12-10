package com.bmat.digitalcharts.admin.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="BMATDigitalChartsDB.Right")
public class Right extends AbstractEntity {
	
	private static final long serialVersionUID = -7284096948210244375L;
	
	@NotBlank @NotNull
	private String name;
	
	public Right(){}
	
	public Right(Long id, String name) {
		super();
		this.setId(id);
		this.name = name;
	}



	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.name + "(" + this.getId() + ")";
	}

}
