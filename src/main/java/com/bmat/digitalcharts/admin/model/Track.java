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
			return "id";
		case 1:
			return "name";
		case 2:
			return "isrc";
		case 3:
			return "song.id";
		case 4:
			return "song.name";
		case 5:
			return "performer.name";
		default:
			return null;
		}
	}
	
	@Override
	public List<String> getCamposAsList() {
		
		List<String> resultado = new LinkedList<>();
		resultado.add(String.valueOf(this.getId()));
		resultado.add(this.name);
		resultado.add(this.isrc);
		resultado.add(String.valueOf(this.song.getId()));
		resultado.add(this.song.getName());
		resultado.add(this.performer != null ? this.performer.getName() : "");
		resultado.add(super.getLinkModificar());
		
		return resultado;
	}
}
