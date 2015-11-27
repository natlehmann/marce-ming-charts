package com.bmat.digitalcharts.admin.controllers;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bmat.digitalcharts.admin.model.SummaryReport;
import com.bmat.digitalcharts.admin.services.SummaryReportService;

@Controller
@RequestMapping("/report")
public class ReportController {
	
	private static Log log = LogFactory.getLog(ReportController.class);
	
	@Autowired
	private SummaryReportService service;


	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public ModelAndView getExcel(Map<String, Object> model) {
		
		
		SummaryReport report = service.getSummaryReport();
		return new ModelAndView("chartSummaryExcelView", "summaryReport", report);
	}
}
