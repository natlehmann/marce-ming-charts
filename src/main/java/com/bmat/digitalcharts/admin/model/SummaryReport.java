package com.bmat.digitalcharts.admin.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
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
	
	@ManyToOne
	private Country country;
}
