package com.bmat.digitalcharts.admin.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class SummaryReport extends AbstractEntity {

	private static final long serialVersionUID = -1175240899053172690L;

	@Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateFrom;
	
	@Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTo;
	
	@Column(nullable=false)
	private Integer weekFrom;
	
	@Column(nullable=false)
	private Integer weekTo;
	
	@Column(nullable=false)
	private Integer year;
	
	private Integer month;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date previousDateFrom;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date previousDateTo;
	
	@ManyToOne
	private Country country;
	
	@ManyToOne
	private Right right;
	
	@OneToMany(mappedBy="summaryReport", cascade=CascadeType.ALL)
	private List<SummaryReportItem> items;
	
	private boolean enabled = true;

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public Integer getWeekFrom() {
		return weekFrom;
	}

	public void setWeekFrom(Integer weekFrom) {
		this.weekFrom = weekFrom;
	}

	public Integer getWeekTo() {
		return weekTo;
	}

	public void setWeekTo(Integer weekTo) {
		this.weekTo = weekTo;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
	
	public List<SummaryReportItem> getItems() {
		return items;
	}
	
	public void setItems(List<SummaryReportItem> items) {
		this.items = items;
	}

	public Date getPreviousDateFrom() {
		return previousDateFrom;
	}

	public void setPreviousDateFrom(Date previousDateFrom) {
		this.previousDateFrom = previousDateFrom;
	}

	public Date getPreviousDateTo() {
		return previousDateTo;
	}

	public void setPreviousDateTo(Date previousDateTo) {
		this.previousDateTo = previousDateTo;
	}
	
	public Right getRight() {
		return right;
	}
	
	public void setRight(Right right) {
		this.right = right;
	}

	public Integer getYear() {
		return year;
	}
	
	public void setYear(Integer year) {
		this.year = year;
	}
	
	public Integer getMonth() {
		return month;
	}
	
	public void setMonth(Integer month) {
		this.month = month;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	
}
