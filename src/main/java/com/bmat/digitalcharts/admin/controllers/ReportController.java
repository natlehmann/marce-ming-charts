package com.bmat.digitalcharts.admin.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Autowired
	private SummaryReportService service;
	
	@Autowired
	private CountryDao countryDao;
	
	
	@RequestMapping("/filters")
	public String initReportFilters(ModelMap model) {
		
		model.addAttribute("countries", countryDao.getAll());
		
		return "report/filters";
	}


	@RequestMapping("/export")
	public ModelAndView getExcel(@RequestParam("country") Long countryId, ModelMap model) {
		
		model.addAttribute("selectedCountry", countryId);
		
		SummaryReport report = service.getSummaryReport(countryId);
		return new ModelAndView("chartSummaryExcelView", "summaryReport", report);
	}
}
