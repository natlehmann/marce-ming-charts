package com.bmat.digitalcharts.admin.controllers;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(HomeController.class);
	
	
	@RequestMapping("/")
	public String redirectHome() {
		return "redirect:/report/filters";
	}
	
	
	@RequestMapping("/home")
	public String home(ModelMap model, HttpSession session) {
		return "home";
	}

	
	@RequestMapping("/admin")
	public String adminHome() {
		return "admin/home";
	}

}
