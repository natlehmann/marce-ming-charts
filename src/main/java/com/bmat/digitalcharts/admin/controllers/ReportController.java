package com.bmat.digitalcharts.admin.controllers;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bmat.digitalcharts.admin.dao.CountryDao;
import com.bmat.digitalcharts.admin.model.SummaryReport;
import com.bmat.digitalcharts.admin.services.SummaryReportService;

@Controller
@RequestMapping("/report")
public class ReportController {
	
	private static Log log = LogFactory.getLog(ReportController.class);
	
	@Value("${report.years.back}")
	private String YEARS_BACK;
	
	@Autowired
	private SummaryReportService service;
	
	@Autowired
	private CountryDao countryDao;
	
	
	@RequestMapping("/filters")
	public String initReportFilters(ModelMap model) {
		
		model.addAttribute("countries", countryDao.getAll());
		
		List<Integer> weeks = new LinkedList<>();
		for (int i = 1; i <= 53; i++) {
			weeks.add(i);
		}
		model.addAttribute("weeks", weeks);
		
		List<Integer> years = new LinkedList<>();
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		years.add(currentYear);
		for (int i = 1; i < Integer.parseInt(YEARS_BACK); i++) {
			years.add(currentYear - i);
		}
		model.addAttribute("years", years);
		
		
		return "report/filters";
	}


	@RequestMapping("/export")
	public ModelAndView getExcel(@RequestParam("country") Long countryId, 
			@RequestParam("year") Integer year,
			@RequestParam("weekFrom") Integer weekFrom,
			@RequestParam(value="weekTo", required=false, defaultValue="") Integer weekTo,
			ModelMap model) {
		
		SummaryReport report = service.getSummaryReport(countryId, year, weekFrom, weekTo);
		return new ModelAndView("chartSummaryExcelView", "summaryReport", report);
	}
}
