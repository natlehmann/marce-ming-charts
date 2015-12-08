package com.bmat.digitalcharts.admin.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class TopChartDetail extends AbstractEntity {

	private static final long serialVersionUID = -183386314836391046L;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="start_date")
	private Date startDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="end_date")
	private Date endDate;
	
	private String label;
	
	private String licensor;
	
	@Column(name="total_streams")
	private Long totalStreams;
	
	private Long performerId;
	
	private Long songId;
	
	private Long chartHeaderId;

}
