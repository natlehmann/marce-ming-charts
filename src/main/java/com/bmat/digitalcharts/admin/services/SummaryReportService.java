package com.bmat.digitalcharts.admin.services;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bmat.digitalcharts.admin.dao.CountryDao;
import com.bmat.digitalcharts.admin.dao.MonthlyReportDao;
import com.bmat.digitalcharts.admin.dao.RestSourceDao;
import com.bmat.digitalcharts.admin.dao.RightDao;
import com.bmat.digitalcharts.admin.dao.SummaryReportDaoFacade;
import com.bmat.digitalcharts.admin.dao.WeeklyReportDao;
import com.bmat.digitalcharts.admin.model.BmatSourceUriException;
import com.bmat.digitalcharts.admin.model.MonthlyReport;
import com.bmat.digitalcharts.admin.model.NoBmatSourceDefinedException;
import com.bmat.digitalcharts.admin.model.NoBmatSourceUri;
import com.bmat.digitalcharts.admin.model.RestSource;
import com.bmat.digitalcharts.admin.model.SummaryReport;
import com.bmat.digitalcharts.admin.model.SummaryReportItem;
import com.bmat.digitalcharts.admin.model.SummaryReportItem.SongIdComparator;
import com.bmat.digitalcharts.admin.model.TooManyBmatSourceUris;
import com.bmat.digitalcharts.admin.model.WeeklyReport;

@Service
public class SummaryReportService {
	
	@Autowired
	private CountryDao countryDao;
	
	@Autowired
	private RightDao rightDao;
	
	@Autowired
	private RestSourceDao restSourceDao;
	
	@Autowired
	private SummaryReportDaoFacade summaryReportDao;
	
	@Autowired
	private WeeklyReportDao weeklyReportDao;
	
	@Autowired
	private MonthlyReportDao monthlyReportDao;
	
	private RestSource bmatSource;
	
	@Transactional
	public SummaryReport getSummaryReport(Long countryId, Integer year, 
			Integer weekFrom, Integer weekTo, Integer month, Long rightId, Long sourceId) {
		
		SummaryReport report = buildSummaryReport(
				weekFrom, weekTo, month, year, rightId, countryId, sourceId);		
		
		SummaryReport previousReport = summaryReportDao.getPreviousReport(report);
		SummaryReport reportBeforePrevious = null;
		
		if (previousReport != null) {
			report.setPreviousDateFrom(previousReport.getDateFrom());
			report.setPreviousDateTo(previousReport.getDateTo());
			
			reportBeforePrevious = summaryReportDao.getPreviousReport(previousReport);
		}
		
		
		List<SummaryReportItem> items = buildItems(countryId, rightId, report, 
				previousReport, reportBeforePrevious);
		
		report.setItems(items);
		
		setSources(report, items);

		return report;
	}


	private void setSources(SummaryReport report, List<SummaryReportItem> items) {
		
		if (!items.isEmpty()) {
			
			List<Long> ids = new LinkedList<>();
			
			for (SummaryReportItem item : items) {
				ids.add(item.getSongId());
			}
			
			List<RestSource> sources = restSourceDao.getSources(ids, report.getDateFrom(), 
					report.getDateTo(), report.getCountry(), report.getRight());
			
			StringBuffer buffer = new StringBuffer();
			
			for (RestSource source : sources) {
				buffer.append(source.getName()).append(", ");
			}
			
			report.setSources(buffer.substring(0, buffer.length() - 2));
			
			
			if (report.getFilteredBySource() != null) {
				
				
				List<SummaryReportItem> amountsBySource = summaryReportDao.getAmountsBySource(
						report, ids);
				
				for (SummaryReportItem item : items) {
					int itemBySourceIndex = Collections.binarySearch(amountsBySource, item, 
							new SongIdComparator());
					
					if (itemBySourceIndex >= 0) {
						item.setAmountBySource(amountsBySource.get(itemBySourceIndex).getCurrentAmount());
					}
				}
			}
		}
		
	}
	


	private List<SummaryReportItem> buildItems(Long countryId, Long rightId,
			SummaryReport report, SummaryReport previousReport, SummaryReport reportBeforePrevious) {
		
		List<SummaryReportItem> items = summaryReportDao.getReportItems(countryId, rightId,
				report);
		
		int currentPosition = 1;
		for (SummaryReportItem item : items) {
			item.setCurrentPosition(currentPosition++);
			item.setReport(report);
			
			setAggregateUnits(item, report);
			
			setPreviousReportInfo(previousReport, reportBeforePrevious, item);
			
			if (reportBeforePrevious != null) {
				SummaryReportItem previousItem = findItem(item, reportBeforePrevious);
				if (previousItem != null) {
					
					item.setPositionBeforePrevious(previousItem.getCurrentPosition());
				}
			}
		}
		
		return items;
	}



	private void setAggregateUnits(SummaryReportItem item, SummaryReport report) {
		
		BigDecimal aggregate = summaryReportDao.getAggregateUnits(report, item);
		
		if (aggregate == null) {
			item.setAggregateUnits(new BigDecimal(0));
			
		} else {			
			item.setAggregateUnits(aggregate);
		}
		
		// se debe agregar la cantidad actual de unidades porque todavia no fue guardada
		// y no se recupera con la query
		item.setAggregateUnits(item.getAggregateUnits().add(new BigDecimal(item.getCurrentAmount())));
		
	}


	void setPreviousReportInfo(SummaryReport previousReport, SummaryReport reportBeforePrevious,
			SummaryReportItem item) {
		
		SummaryReportItem previousItem = null;
		SummaryReportItem itemBeforePrevious = null;
		
		if (previousReport != null) {
			
			previousItem = findItem(item, previousReport);
			if (previousItem != null) {
				
				item.setPreviousPosition(previousItem.getCurrentPosition());
				item.setPreviousAmount(previousItem.getCurrentAmount());
			}
		}
		
		if (reportBeforePrevious != null) {
			itemBeforePrevious = findItem(item, reportBeforePrevious);
		}
		
		boolean valuesSetInBefore = setWeekInRankingAndBestPosition(item, itemBeforePrevious);
		boolean valuesSetInPrevious = setWeekInRankingAndBestPosition(item, previousItem);
		
		if (!valuesSetInBefore && !valuesSetInPrevious) {
			
			item.setBestPosition(item.getCurrentPosition());
			item.setWeeksInRanking(1);
		}
	}
	
	
	private boolean setWeekInRankingAndBestPosition(SummaryReportItem item,
			SummaryReportItem previousItem) {
		
		if (previousItem != null) {
			
			if (item.getCurrentPosition() < previousItem.getBestPosition()) {
				item.setBestPosition(item.getCurrentPosition());
					
			} else {
				item.setBestPosition(previousItem.getBestPosition());
			}
			
			item.setWeeksInRanking(previousItem.getWeeksInRanking() + 1);
			
			return true;		
		}
		
		return false;
	}

	
	private SummaryReportItem findItem(SummaryReportItem item,
			SummaryReport previousReport) {
		
		SummaryReportItem result = null;
		for (SummaryReportItem sri : previousReport.getItems()) {
			if (sri.getSongId().equals(item.getSongId())) {
				result = sri;
			}
		}
		
		return result;
	}


	private SummaryReport buildSummaryReport(Integer weekFrom, Integer weekTo,
			Integer month, Integer year, Long rightId, Long countryId, Long sourceId) {
		
		if (weekTo == null) {
			weekTo = weekFrom;
		}
		
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
		
		report.setRight(rightDao.search(rightId));
		report.setCountry(countryDao.search(countryId));
		
		if (sourceId != null) {
			report.setFilteredBySource(restSourceDao.search(sourceId));
		}
		
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
		
		SummaryReport existingReport = summaryReportDao.getEnabledReport(report);				
		
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
	
	
	public SummaryReport getExistingReport(Long countryId, Integer year, 
			Integer weekFrom, Integer weekTo, Integer month, Long rightId, Long sourceId) {
		
		SummaryReport report = buildSummaryReport(weekFrom, weekTo, 
				month, year, rightId, countryId, sourceId);
		return summaryReportDao.getEnabledReport(report);
	}


	public List<SummaryReport> getWeeklyReportsPaginatedAndFiltered(int inicio, int cantidadResultados,
			String filtro, String campoOrdenamiento, String direccionOrdenamiento) {
		
		return summaryReportDao.getReportsPaginatedAndFiltered(inicio, cantidadResultados, 
				filtro, campoOrdenamiento, direccionOrdenamiento, false);
	}
	
	
	public List<SummaryReport> getMonthlyReportsPaginatedAndFiltered(int inicio, int cantidadResultados,
			String filtro, String campoOrdenamiento, String direccionOrdenamiento) {
		
		return summaryReportDao.getReportsPaginatedAndFiltered(inicio, cantidadResultados, 
				filtro, campoOrdenamiento, direccionOrdenamiento, true);
	}


	public long getWeeklyReportsCount(String filtro) {
		return summaryReportDao.getReportsCount(filtro, false);
	}
	
	
	public long getMonthlyReportsCount(String filtro) {
		return summaryReportDao.getReportsCount(filtro, true);
	}


	public void deleteWeeklyReport(Long id) {
		weeklyReportDao.delete(id);		
	}


	public void deleteMonthlyReport(Long id) {
		monthlyReportDao.delete(id);		
	}


	public SummaryReport getWeeklyCsvReport(Long id) throws BmatSourceUriException {
		
		if (bmatSource == null) {
			bmatSource = restSourceDao.getByName("BMatTopTracks");
			
			if (bmatSource == null) {
				throw new NoBmatSourceDefinedException();
			}
		}
		
		SummaryReport report = weeklyReportDao.getWithItems(id);
		
		StringBuffer buffer = new StringBuffer();
		
		for (SummaryReportItem item : report.getItems()) {
			
			try {
				List<String> sourceUris = weeklyReportDao.getBmatSourceUri(
						report, item, bmatSource.getId());
				
				if (sourceUris.isEmpty()) {
					throw new NoBmatSourceUri(item);
				}
				
				if (sourceUris.size() > 1) {
					throw new TooManyBmatSourceUris(item);
				}
				
				item.setBmatSourceUri(sourceUris.get(0));
				
			} catch (BmatSourceUriException e) {
				buffer.append(e.getMessage()).append("<br/>");
			}
		}
		
		
		if (!buffer.toString().equals("")) {
			throw new BmatSourceUriException(buffer.toString());
		}
		
		return report;
	}

	

}
