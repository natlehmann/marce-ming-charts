package com.bmat.digitalcharts.admin.services;

import java.util.Date;
import java.util.LinkedList;

import org.springframework.stereotype.Service;

import com.bmat.digitalcharts.admin.model.Country;
import com.bmat.digitalcharts.admin.model.SummaryReport;
import com.bmat.digitalcharts.admin.model.SummaryReportItem;

@Service
public class SummaryReportService {

	public SummaryReport getSummaryReport(Long countryId, Integer weekFrom) {
		// TODO Auto-generated method stub
		SummaryReport r = new SummaryReport();
		r.setDateFrom(new Date());
		r.setDateTo(new Date());
		r.setWeekFrom(weekFrom);
		r.setWeekTo(weekFrom);
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

}
