package com.bmat.digitalcharts.admin.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Track extends AbstractEntity {
	
	private static final long serialVersionUID = 3299196766182031047L;

	@ManyToOne
	@JoinColumn(name="performerId")
	private Performer performer;
	
	@ManyToOne
	@JoinColumn(name="songId")
	private Song song;
	
	@ManyToOne
	@JoinColumn(name="releaseId")
	private Release release;
	
	private String isrc;
	
	private String name;

	public Performer getPerformer() {
		return performer;
	}

	public void setPerformer(Performer performer) {
		this.performer = performer;
	}

	public Song getSong() {
		return song;
	}

	public void setSong(Song song) {
		this.song = song;
	}

	public Release getRelease() {
		return release;
	}

	public void setRelease(Release release) {
		this.release = release;
	}
	
	public String getIsrc() {
		return isrc;
	}
	
	public void setIsrc(String isrc) {
		this.isrc = isrc;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Transient
	public static String getOrderingField(int indiceColumna) {
		
		switch(indiceColumna) {
		
		case 0:
			return "name";
		case 1:
			return "isrc";
		case 2:
			return "song.id";
		case 3:
			return "song.name";
		case 4:
			return "performer.name";
		case 5:
			return "release.labelCompany.name";
		default:
			return null;
		}
	}
	
	@Override
	public List<String> getCamposAsList() {
		
		List<String> resultado = new LinkedList<>();
		resultado.add("<span title='ISRC = " + this.isrc + "'>" + this.name + "</span>");
		resultado.add(this.performer != null ? this.performer.getName() : "");
		resultado.add(release != null && release.getLicensor() != null ? 
				release.getLicensor().getName() : ""); 
		resultado.add(release != null && release.getLabelCompany() != null ? 
				release.getLabelCompany().getName() : ""); 
		resultado.add(getLinkModificar() + super.getLinkMerge("Asociar a otra canción"));
		
		return resultado;
	}
}
