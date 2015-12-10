package com.bmat.digitalcharts.admin.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="BMATDigitalChartsDB.Usage")
public class Usage extends AbstractEntity {
	
	private static final long serialVersionUID = -429590376260309376L;

	@Temporal(TemporalType.TIMESTAMP)
	private Date chartDate;
	
	private Long units;
	
	@ManyToOne
	@JoinColumn(name="countryId")
	private Country country;
	
	@ManyToOne
	@JoinColumn(name="rightId")
	private Right right;
	
	@ManyToOne
	@JoinColumn(name="trackId")
	private Track track;

	public Date getChartDate() {
		return chartDate;
	}

	public void setChartDate(Date chartDate) {
		this.chartDate = chartDate;
	}

	public Long getUnits() {
		return units;
	}

	public void setUnits(Long units) {
		this.units = units;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Right getRight() {
		return right;
	}

	public void setRight(Right right) {
		this.right = right;
	}

	public Track getTrack() {
		return track;
	}

	public void setTrack(Track track) {
		this.track = track;
	}

	
}
