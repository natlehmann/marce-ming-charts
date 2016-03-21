package com.bmat.digitalcharts.admin.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class EmailAddress extends AbstractEntity {
	
	private static final long serialVersionUID = -5305599429253405977L;

	@NotNull @NotBlank
	private String email;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="restSourceId")
	private RestSource restSource;
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public RestSource getRestSource() {
		return restSource;
	}
	
	public void setRestSource(RestSource restSource) {
		this.restSource = restSource;
	}

	@Transient
	public static String getOrderingField(int indiceColumna) {
		
		switch(indiceColumna) {
		
		case 0:
			return "email";
		case 1:
			return "restSource.name";

		default:
			return null;
		}
	}
	
	@Override
	public List<String> getCamposAsList() {
		
		List<String> result = new LinkedList<>();
		result.add(this.email);
		result.add(this.restSource.getName());
		result.add(getLinksModificarEliminar());
		
		return result;
	}

}
