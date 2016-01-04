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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bmat.digitalcharts.admin.dao.CountryDao;
import com.bmat.digitalcharts.admin.dao.RestSourceDao;
import com.bmat.digitalcharts.admin.dao.RightDao;
import com.bmat.digitalcharts.admin.model.Month;
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
	
	@Autowired
	private RestSourceDao restSourceDao;
	
	
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
		
		model.addAttribute("sources", restSourceDao.getAll());
		
		model.addAttribute("months", Month.getMonths());
		
		
		return new ModelAndView("report/filters", model);
	}
	
	
	@RequestMapping("/create")
	public ModelAndView createReport(@RequestParam("country") Long countryId, 
			@RequestParam("year") Integer year,
			@RequestParam(value="weekFrom", required=false, defaultValue="") Integer weekFrom,
			@RequestParam(value="weekTo", required=false, defaultValue="") Integer weekTo,
			@RequestParam(value="month", required=false, defaultValue="") Integer month,
			@RequestParam("right") Long rightId,
			@RequestParam(value="source", required=false, defaultValue="") Long sourceId,
			@RequestParam("action") String action,
			ModelMap model, HttpSession session) {

		setConversationParameters(model, year, countryId, weekFrom, weekTo, rightId, sourceId, month);
		
		if (!validFilters(model, weekFrom, weekTo, month)) {
			return initReportFilters(model);
		}
		
		if (action.equals(EXPORT_ACTION)) {
			return getExcel(countryId, year, weekFrom, weekTo, 
					month, rightId, sourceId, model, session);
		}
		
		if (action.equals(SAVE_ACTION)) {
			return saveReport(model, session);
		}
		
		return null;
	}


	private boolean validFilters(ModelMap model, Integer weekFrom,
			Integer weekTo, Integer month) {
		
		boolean valid = true;
		
		if (weekFrom == null) {
			model.addAttribute("msg", "Debe elegir una semana inicial.");
			valid = false;
			
		} else {
		
			if (month != null) {
			
				if (weekTo == null) {
					model.addAttribute("msg", "Debe elegir una semana final.");
					valid = false;
					
				} else {
					if (weekTo < weekFrom) {
						model.addAttribute("msg", "La semana final debe ser mayor o igual a la semana de inicio.");
						valid = false;
					}
				}
			}
		}
		
		return valid;
		
		
	}


	private ModelAndView saveReport(ModelMap model, HttpSession session) {
		
		SummaryReport report = (SummaryReport) session.getAttribute(
				Utils.SessionParams.ACTIVE_REPORT.toString());
		
		if (report != null) {
		
			String msg = service.saveReport(report);
			
			setConversationParameters(model, report.getYear(), report.getCountry().getId(),
					report.getWeekFrom(), report.getWeekTo(), report.getRight().getId(),
					report.getFilteredBySource() != null ? report.getFilteredBySource().getId() :  null, 
					report.getMonth());
			model.put("msg", msg);
			
			session.removeAttribute(Utils.SessionParams.ACTIVE_REPORT.toString());
			
		} else {
			model.put("msg", "Genere un reporte a partir de estos filtros.");
		}
		
		return initReportFilters(model);
	}


	private void setConversationParameters(ModelMap model, Integer year, Long countryId, Integer weekFrom,
			Integer weekTo, Long rightId, Long sourceId, Integer month) {
		model.put("selectedYear", year);
		model.put("selectedCountry", countryId);
		model.put("selectedWeekFrom", weekFrom);
		model.put("selectedWeekTo", weekTo);
		model.put("selectedRight", rightId);
		model.put("selectedMonth", month);
		model.put("selectedSource", sourceId);		
		model.put("selectedIsMonthly", month != null);
	}


	public ModelAndView getExcel(Long countryId, Integer year, Integer weekFrom,
			Integer weekTo, Integer month, Long rightId,
			Long sourceId, ModelMap model, HttpSession session) {

		SummaryReport report = service.getSummaryReport(
				countryId, year, weekFrom, weekTo, month, rightId, sourceId);
		
		session.setAttribute(Utils.SessionParams.ACTIVE_REPORT.toString(), report);
		model.put("summaryReport", report);
		return new ModelAndView("chartSummaryExcelView", model);
	}
	
	@RequestMapping("/isReady")
	@ResponseBody
	public boolean isReady(HttpSession session) {
		
		return session.getAttribute(Utils.SessionParams.ACTIVE_REPORT.toString()) != null;
	}
}
