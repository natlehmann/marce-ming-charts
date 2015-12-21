package com.bmat.digitalcharts.admin.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Entity
public class MonthlyReportItem extends SummaryReportItem {

	private static final long serialVersionUID = 8930489162956283218L;
	
	private static Log log = LogFactory.getLog(MonthlyReportItem.class);
	
	@ManyToOne(optional=false)
	private MonthlyReport monthlyReport;
	

	public MonthlyReportItem() {
		super();
	}

	public MonthlyReportItem(Long songId, String songName, Long performerId,
			String performerName, Long currentAmount, Long labelCompanyId,
			String labelCompanyName) {
		super(songId, songName, performerId, performerName, currentAmount,
				labelCompanyId, labelCompanyName);
	}

	public MonthlyReport getMonthlyReport() {
		return monthlyReport;
	}
	
	public void setMonthlyReport(MonthlyReport monthlyReport) {
		this.monthlyReport = monthlyReport;
	}

	@Override
	public void setReport(SummaryReport report) {
		if (report instanceof MonthlyReport) {
			this.setMonthlyReport((MonthlyReport)report);
			
		} else {
			log.error("Error al setear reporte - no es reporte mensual. " + report);
			this.setMonthlyReport(null);
		}
		
	}
}
