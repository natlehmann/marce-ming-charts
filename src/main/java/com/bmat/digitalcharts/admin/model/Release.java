package com.bmat.digitalcharts.admin.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="BMATDigitalChartsDB.Release")
public class Release extends AbstractEntity {
	
	private static final long serialVersionUID = 2249538472115519938L;
	
	@ManyToOne
	@JoinColumn(name="labelCompanyId")
	private LabelCompany labelCompany;
	
	@ManyToOne
	@JoinColumn(name="licensorId")
	private Licensor licensor;
	
	public LabelCompany getLabelCompany() {
		return labelCompany;
	}
	
	public void setLabelCompany(LabelCompany labelCompany) {
		this.labelCompany = labelCompany;
	}
	
	public Licensor getLicensor() {
		return licensor;
	}
	
	public void setLicensor(Licensor licensor) {
		this.licensor = licensor;
	}

}
