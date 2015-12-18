package com.bmat.digitalcharts.admin.controllers;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

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
import com.bmat.digitalcharts.admin.dao.RightDao;
import com.bmat.digitalcharts.admin.model.SummaryReport;
import com.bmat.digitalcharts.admin.services.SummaryReportService;

@Controller
@RequestMapping("/report")
public class ReportController {
	
	private static Log log = LogFactory.getLog(ReportController.class);
	
	public static final String EXPORT_ACTION = "Generar reporte";
	public static final String SAVE_ACTION = "Archivar reporte";
	
	@Value("${report.years.back}")
	private String YEARS_BACK;
	
	@Autowired
	private SummaryReportService service;
	
	@Autowired
	private CountryDao countryDao;
	
	@Autowired
	private RightDao rightDao;
	
	
	@RequestMapping("/filters")
	public ModelAndView initReportFilters(ModelMap model) {
		
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
		
		model.addAttribute("rights", rightDao.getAll());
		
		
		return new ModelAndView("report/filters", model);
	}
	
	
	@RequestMapping("/create")
	public ModelAndView createReport(@RequestParam("country") Long countryId, 
			@RequestParam("year") Integer year,
			@RequestParam("weekFrom") Integer weekFrom,
			@RequestParam(value="weekTo", required=false, defaultValue="") Integer weekTo,
			@RequestParam("right") Long rightId,
			@RequestParam("action") String action,
			ModelMap model, HttpSession session) {
		
		if (action.equals(EXPORT_ACTION)) {
			return getExcel(countryId, year, weekFrom, weekTo, rightId, model, session);
		}
		
		if (action.equals(SAVE_ACTION)) {
			return saveReport(model, session);
		}
		
		return null;
	}


	private ModelAndView saveReport(ModelMap model, HttpSession session) {
		
		SummaryReport report = (SummaryReport) session.getAttribute(
				Utils.SessionParams.ACTIVE_REPORT.toString());
		
		String msg = service.saveReport(report);
		
		model.put("selectedYear", report.getYear());
		model.put("selectedCountry", report.getCountry().getId());
		model.put("selectedWeekFrom", report.getWeekFrom());
		model.put("selectedWeekTo", report.getWeekTo());
		model.put("selectedRight", report.getRight().getId());
		model.put("msg", msg);
		
		return initReportFilters(model);
	}


	public ModelAndView getExcel(@RequestParam("country") Long countryId, 
			@RequestParam("year") Integer year,
			@RequestParam("weekFrom") Integer weekFrom,
			@RequestParam(value="weekTo", required=false, defaultValue="") Integer weekTo,
			@RequestParam("right") Long rightId,
			ModelMap model, HttpSession session) {
		
		SummaryReport report = service.getSummaryReport(countryId, year, weekFrom, weekTo, rightId);
		
		session.setAttribute(Utils.SessionParams.ACTIVE_REPORT.toString(), report);
		model.put("summaryReport", report);
		return new ModelAndView("chartSummaryExcelView", model);
	}
}
