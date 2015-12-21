package com.bmat.digitalcharts.admin.services;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bmat.digitalcharts.admin.dao.CountryDao;
import com.bmat.digitalcharts.admin.dao.MonthlyReportDao;
import com.bmat.digitalcharts.admin.dao.MonthlyReportItemDao;
import com.bmat.digitalcharts.admin.dao.RightDao;
import com.bmat.digitalcharts.admin.dao.WeeklyReportDao;
import com.bmat.digitalcharts.admin.dao.WeeklyReportItemDao;
import com.bmat.digitalcharts.admin.model.MonthlyReport;
import com.bmat.digitalcharts.admin.model.SummaryReport;
import com.bmat.digitalcharts.admin.model.SummaryReportItem;
import com.bmat.digitalcharts.admin.model.WeeklyReport;

@Service
public class SummaryReportService {
	
	@Autowired
	private CountryDao countryDao;
	
	@Autowired
	private RightDao rightDao;
	
	@Autowired
	private WeeklyReportItemDao weeklyReportItemDao;
	
	@Autowired
	private MonthlyReportItemDao monthlyReportItemDao;
	
	@Autowired
	private WeeklyReportDao weeklyReportDao;
	
	@Autowired
	private MonthlyReportDao monthlyReportDao;

	public SummaryReport getSummaryReport(Long countryId, Integer year, 
			Integer weekFrom, Integer weekTo, Integer month, Long rightId) {
		
		if (weekTo == null) {
			weekTo = weekFrom;
		}
				
		SummaryReport report = buildSummaryReport(weekFrom, weekTo, month, year);		
		
		report.setRight(rightDao.search(rightId));		
		report.setCountry(countryDao.search(countryId));
		
		// TODO: ESTO NO ES VALIDO PARA REPORTE MENSUAL
		report.setPreviousDateFrom(getDateFrom(year, weekFrom - 1));
		report.setPreviousDateTo(getDateTo(year, weekTo - 1));
		
		
		List<SummaryReportItem> items = buildItems(countryId, rightId, report);
		
		report.setItems(items);

		return report;
	}


	private List<SummaryReportItem> buildItems(Long countryId, Long rightId,
			SummaryReport report) {
		
		List<SummaryReportItem> items = new LinkedList<>();
		
		if (report.isMonthly()) {
			items = monthlyReportItemDao.getItems(
					countryId, rightId, report.getDateFrom(), report.getDateTo());
			
		} else {
			items = weeklyReportItemDao.getItems(
					countryId, rightId, report.getDateFrom(), report.getDateTo());
		}
		
		int currentPosition = 1;
		for (SummaryReportItem item : items) {
			item.setCurrentPosition(currentPosition++);
			item.setReport(report);
		}
		
		return items;
	}

	
	private SummaryReport buildSummaryReport(Integer weekFrom, Integer weekTo,
			Integer month, Integer year) {
		
		SummaryReport report = new WeeklyReport();
		if (month != null) {
			report = new MonthlyReport();
			((MonthlyReport)report).setMonth(month);
		}
		
		report.setDateFrom(getDateFrom(year, weekFrom));
		report.setDateTo(getDateTo(year, weekTo));
		report.setWeekFrom(weekFrom);
		report.setWeekTo(weekTo);
		report.setYear(year);
		
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
		
		SummaryReport existingReport = null;
		if (report.isMonthly()) {
			
			existingReport = monthlyReportDao.getReport(
					report.getYear(), report.getWeekFrom(), report.getMonth());
			
		} else {
			existingReport = weeklyReportDao.getReport(
					report.getYear(), report.getWeekFrom(), report.getMonth());
		}
				
		
		if (existingReport != null) {
			existingReport.setEnabled(false);
			oldId = existingReport.getId();
			save(existingReport);
		}
		
		save(report);
		
		String msg = null;
		if (oldId != null) {
			msg = "El reporte anterior correspondiente al mismo período (ID: " + oldId 
					+ ") fue reemplazado por el nuevo (ID: " + report.getId() + ").";
			
		} else {
			msg = "El reporte fue guardado con éxito.";
		}
		
		return msg;
	}


	@Transactional
	public void save(SummaryReport report) {
		
		if (report.isMonthly()) {
			monthlyReportDao.save(report);
			
		} else {
			weeklyReportDao.save(report);
		}		
	}

}
