package com.bmat.digitalcharts.admin.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bmat.digitalcharts.admin.dao.CountryDao;
import com.bmat.digitalcharts.admin.dao.RightDao;
import com.bmat.digitalcharts.admin.dao.SummaryReportDao;
import com.bmat.digitalcharts.admin.dao.SummaryReportItemDao;
import com.bmat.digitalcharts.admin.model.SummaryReport;
import com.bmat.digitalcharts.admin.model.SummaryReportItem;

@Service
public class SummaryReportService {
	
	@Autowired
	private CountryDao countryDao;
	
	@Autowired
	private RightDao rightDao;
	
	@Autowired
	private SummaryReportItemDao summaryReportItemDao;
	
	@Autowired
	private SummaryReportDao summaryReportDao;

	public SummaryReport getSummaryReport(
			Long countryId, Integer year, Integer weekFrom, Integer weekTo, Long rightId) {
		
		if (weekTo == null) {
			weekTo = weekFrom;
		}
		
		
		SummaryReport report = new SummaryReport();
		report.setDateFrom(getDateFrom(year, weekFrom));
		report.setDateTo(getDateTo(year, weekTo));
		report.setWeekFrom(weekFrom);
		report.setWeekTo(weekTo);
		report.setYear(year);
		
		report.setRight(rightDao.search(rightId));		
		report.setCountry(countryDao.search(countryId));
		
		// TODO: ESTO NO ES VALIDO PARA REPORTE MENSUAL
		report.setPreviousDateFrom(getDateFrom(year, weekFrom - 1));
		report.setPreviousDateTo(getDateTo(year, weekTo - 1));
		
		
		List<SummaryReportItem> items = summaryReportItemDao.getItems(countryId, rightId, report.getDateFrom(), report.getDateTo());
		
		int currentPosition = 1;
		for (SummaryReportItem item : items) {
			item.setCurrentPosition(currentPosition++);
			item.setSummaryReport(report);
		}
		
		report.setItems(items);

		return report;
	}

	
	Date getDateTo(Integer year, Integer weekTo) {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getDateFrom(year, weekTo));
		calendar.add(Calendar.DAY_OF_MONTH, 6);
		
		return calendar.getTime();
	}

	Date getDateFrom(Integer year, Integer weekFrom) {

		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.FRIDAY);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.WEEK_OF_YEAR, weekFrom);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		return calendar.getTime();
	}


	@Transactional(value="transactionManager")
	public String saveReport(SummaryReport report) {
		
		Long oldId = null;
		
		SummaryReport existingReport = summaryReportDao.getReport(
				report.getYear(), report.getWeekFrom(), report.getMonth());
		
		if (existingReport != null) {
			existingReport.setEnabled(false);
			oldId = existingReport.getId();
			summaryReportDao.save(existingReport);
		}
		
		summaryReportDao.save(report);
		
		String msg = null;
		if (oldId != null) {
			msg = "El reporte anterior correspondiente al mismo período (ID: " + oldId 
					+ ") fue reemplazado por el nuevo (ID: " + report.getId() + ").";
			
		} else {
			msg = "El reporte fue guardado con éxito.";
		}
		
		return msg;
	}

}
