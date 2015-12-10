package com.bmat.digitalcharts.admin.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Performer extends AbstractEntity {

	private static final long serialVersionUID = 5577335973668899662L;
	
	@NotBlank @NotNull
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
	
	@Override
	public List<String> getCamposAsList() {
		
		List<String> resultado = new LinkedList<>();
		resultado.add(String.valueOf(this.getId()));
		resultado.add(this.name);
		resultado.add(super.getLinkModificar());
		
		return resultado;
	}

	@Transient
	public static String getOrderingField(int indiceColumna) {
		
		switch(indiceColumna) {
		
		case 0:
			return "id";
		case 1:
			return "name";
		default:
			return null;
		}
	}

}
