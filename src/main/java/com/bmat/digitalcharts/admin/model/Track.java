package com.bmat.digitalcharts.admin.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
	
	

}
