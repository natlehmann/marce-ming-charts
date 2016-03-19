package com.bmat.digitalcharts.admin.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.bmat.digitalcharts.admin.controllers.Utils;

@Entity
public class Song extends AbstractEntity {
	
	private static final long serialVersionUID = 5419845727163340242L;

	private String composers;
	
	@NotNull @NotBlank
	private String name;
	
	@NotNull
	@ManyToOne(optional=false)
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
	
	@Transient
	public static String getOrderingField(int indiceColumna, String from) {
		
		switch(indiceColumna) {
		
		case 0:
			return "name";
		case 1:
			return "performer.name";
		case 2:
			if (from.equals(Utils.TRACK_LIST)) {
				return "id";
				
			} else {
				return "composers";
			}
		default:
			return null;
		}
	}
	
	@Override
	public List<String> getCamposAsList() {
		
		List<String> resultado = new LinkedList<>();
		resultado.add(this.name);
		resultado.add(this.performer != null ? this.performer.getName() : "");
		resultado.add(this.composers);
		resultado.add(super.getLinkModificar() + super.getLinkMerge());
		
		return resultado;
	}

	@Transient
	public List<String> getFieldsForUniqueSelection() {
		
		List<String> resultado = getCamposAsList();
		resultado.remove(resultado.size() - 1);
		
		resultado.add("<input type='radio' name='song.id' value='" + this.getId() + "'/><br>");
		return resultado;
	}
	
	
	@Override
	@Transient
	public List<String> getFieldsForTrackEdition() {
		
		List<String> resultado = new LinkedList<>();
		resultado.add(this.name);
		resultado.add(this.performer != null ? this.performer.getName() : "");
		resultado.add(String.valueOf(this.getId()));
		resultado.add("<a href='updateTracks?id=" + this.getId() 
				+ "' class='modificar-link' title='Modificar Tracks'></a> ");
		
		return resultado;
	}
	

}
