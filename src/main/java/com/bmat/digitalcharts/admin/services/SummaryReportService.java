package com.bmat.digitalcharts.admin.services;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import org.springframework.stereotype.Service;

import com.bmat.digitalcharts.admin.model.Country;
import com.bmat.digitalcharts.admin.model.SummaryReport;
import com.bmat.digitalcharts.admin.model.SummaryReportItem;

@Service
public class SummaryReportService {

	public SummaryReport getSummaryReport(Long countryId, Integer year, Integer weekFrom, Integer weekTo) {
		
		if (weekTo == null) {
			weekTo = weekFrom;
		}
		
		
		SummaryReport r = new SummaryReport();
		r.setDateFrom(getDateFrom(year, weekFrom));
		r.setDateTo(getDateTo(year, weekTo));
		r.setWeekFrom(weekFrom);
		r.setWeekTo(weekTo);
		
		r.setCountry(new Country(countryId, "pasi " + countryId));
		r.setPreviousDateFrom(new Date());
		r.setPreviousDateTo(new Date());
		r.setItems(new LinkedList<SummaryReportItem>());
		
		r.getItems().add(new SummaryReportItem());
		r.getItems().get(0).setCurrentAmount(3L);
		r.getItems().get(0).setCurrentPosition(2);
		r.getItems().get(0).setLabelCompanyId(2L);
		r.getItems().get(0).setLabelCompanyName("labelCompanyName");
		r.getItems().get(0).setPerformerId(3L);
		r.getItems().get(0).setPerformerName("performerName");
		r.getItems().get(0).setPositionBeforePrevious(1);
		r.getItems().get(0).setPreviousAmount(34L);
		r.getItems().get(0).setPreviousPosition(9);
		r.getItems().get(0).setSongId(2L);
		r.getItems().get(0).setSongName("songName");

		return r;
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

}
