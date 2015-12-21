package com.bmat.digitalcharts.admin.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Entity
public class WeeklyReportItem extends SummaryReportItem {

	private static final long serialVersionUID = 8314280309209739860L;
	
	private static Log log = LogFactory.getLog(WeeklyReportItem.class);
	
	@ManyToOne(optional=false)
	private WeeklyReport weeklyReport;
	
	
	
	public WeeklyReportItem() {
		super();
	}

	public WeeklyReportItem(Long songId, String songName, Long performerId,
			String performerName, Long currentAmount, Long labelCompanyId,
			String labelCompanyName) {
		super(songId, songName, performerId, performerName, currentAmount,
				labelCompanyId, labelCompanyName);
	}

	public WeeklyReport getWeeklyReport() {
		return weeklyReport;
	}
	
	public void setWeeklyReport(WeeklyReport weeklyReport) {
		this.weeklyReport = weeklyReport;
	}

	@Override
	public void setReport(SummaryReport report) {
		if (report instanceof WeeklyReport) {
			setWeeklyReport((WeeklyReport)report);
		
		} else {
			log.error("Tipo incorrecto de reporte - no es reporte semanal " + report);
			setWeeklyReport(null);
		}
	}

}
