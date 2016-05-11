package com.bmat.digitalcharts.admin.dao;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bmat.digitalcharts.admin.model.SummaryReport;
import com.bmat.digitalcharts.admin.model.SummaryReportItem;

@Repository
public class SummaryReportDaoFacade {
	
	@Autowired
	private WeeklyReportItemDao weeklyReportItemDao;
	
	@Autowired
	private MonthlyReportItemDao monthlyReportItemDao;
	
	@Autowired
	private WeeklyReportDao weeklyReportDao;
	
	@Autowired
	private MonthlyReportDao monthlyReportDao;

	
	public List<SummaryReportItem> getAmountsBySource(SummaryReport report,
			List<Long> ids) {
		
		List<SummaryReportItem> amountsBySource = new LinkedList<>();
		
		if (report.isMonthly()) {
			amountsBySource = monthlyReportItemDao.getItems(report.getCountry().getId(), 
					report.getRight().getId(), report.getDateFrom(), report.getDateTo(), 
					report.getFilteredBySource(), ids);
			
		} else {
			amountsBySource = weeklyReportItemDao.getItems(report.getCountry().getId(), 
					report.getRight().getId(), report.getDateFrom(), report.getDateTo(), 
					report.getFilteredBySource(), ids);
		}
		
		return amountsBySource;
	}

	
	
	public SummaryReport getPreviousReport(SummaryReport report) {
		
		Integer year = report.getYear();
		SummaryReport previousReport = null;
		
		if (report.isMonthly()) {
			
			Integer month = report.getMonth() - 1;
			
			if (month == 0) {
				month = 12;
				year--;
			}
			
			previousReport = monthlyReportDao.getBaseReport(year, month, 
					report.getCountry().getId(), report.getRight().getId());
			
		} else {
			
			Integer week = report.getWeekFrom() - 1;
			
			if (week == 0) {
				week = 53;
				year--;
			}
			
			previousReport = weeklyReportDao.getBaseReport(year, week,
					report.getCountry().getId(), report.getRight().getId());
		}
		
		if (previousReport != null) {
			previousReport.getItems().size();
		}
		
		return previousReport;
	}
	
	
	public List<SummaryReportItem> getReportItems(Long countryId,
			Long rightId, SummaryReport report) {
		
		List<SummaryReportItem> items = new LinkedList<>();
		
		if (report.isMonthly()) {
			items = monthlyReportItemDao.getItems(
					countryId, rightId, report.getDateFrom(), report.getDateTo());
			
		} else {
			items = weeklyReportItemDao.getItems(
					countryId, rightId, report.getDateFrom(), report.getDateTo());
		}
		return items;
	}
	
	
	public SummaryReport getEnabledReport(SummaryReport report) {
		
		SummaryReport existingReport = null;
		if (report.isMonthly()) {
			
			existingReport = monthlyReportDao.getReport(
					report.getYear(), report.getWeekFrom(), report.getMonth(), 
					report.getCountry(), report.getRight(), report.getFilteredBySource());
			
		} else {
			existingReport = weeklyReportDao.getReport(
					report.getYear(), report.getWeekFrom(), report.getMonth(), 
					report.getCountry(), report.getRight(), report.getFilteredBySource());
		}
		
		return existingReport;
	}
	
	
	@Transactional
	public void save(SummaryReport report) {
		
		if (report.isMonthly()) {
			monthlyReportDao.save(report);
			
		} else {
			weeklyReportDao.save(report);
		}		
	}



	public List<SummaryReport> getReportsPaginatedAndFiltered(int inicio,
			int cantidadResultados, String filtro, String campoOrdenamiento,
			String direccionOrdenamiento, boolean isMonthly) {
		
		if (isMonthly) {
			return monthlyReportDao.getAllPaginatedAndFiltered(inicio, cantidadResultados, 
					filtro, campoOrdenamiento, direccionOrdenamiento);
			
		} else {
			return weeklyReportDao.getAllPaginatedAndFiltered(inicio, cantidadResultados, 
					filtro, campoOrdenamiento, direccionOrdenamiento);
		}
	}



	public long getReportsCount(String filtro, boolean isMonthly) {
		
		if (isMonthly) {
			return monthlyReportDao.getCount(filtro);
			
		} else {
			return weeklyReportDao.getCount(filtro);
		}
	}



	public BigDecimal getAggregateUnits(SummaryReport report, SummaryReportItem item) {
		
		if (report.isMonthly()) {
			return monthlyReportItemDao.getAggregateUnits(
					report.getCountry(), report.getRight(), 
					report.getDateFrom(), 
					report.getFilteredBySource(),
					item.getSongId(),
					item.getPerformerId());
			
		} else {
			return weeklyReportItemDao.getAggregateUnits(
					report.getCountry(), report.getRight(), 
					report.getDateFrom(), 
					report.getFilteredBySource(),
					item.getSongId(),
					item.getPerformerId());
		}
	}
}
