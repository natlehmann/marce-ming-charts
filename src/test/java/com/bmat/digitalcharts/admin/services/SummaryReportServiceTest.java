package com.bmat.digitalcharts.admin.services;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class SummaryReportServiceTest {
	
	private SummaryReportService service = new SummaryReportService();
	
	private static SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

	@Test
	public void getDateFromHappyPath() {
		
		Date date = service.getDateFrom(2015, 48);
		assertEquals("20/11/2015", format.format(date));
	}
	
	@Test
	public void getDateFromAtTheBeginningOfTheYear() {
		
		Date date = service.getDateFrom(2015, 1);
		assertEquals("26/12/2014", format.format(date));
	}
	
	@Test
	public void getDateFromAtTheEndOfTheYear() {
		
		Date date = service.getDateFrom(2015, 53);
		assertEquals("25/12/2015", format.format(date));
	}
	
	@Test
	public void getDateFromIsIt53rdOrFirst() {
		
		Date date = service.getDateFrom(2024, 53);
		assertEquals("27/12/2024", format.format(date));
		
		date = service.getDateFrom(2025, 1);
		assertEquals("27/12/2024", format.format(date));
	}
	
	
	@Test
	public void getDateToHappyPath() {
		
		Date date = service.getDateTo(2015, 48);
		assertEquals("26/11/2015", format.format(date));
	}
	
	@Test
	public void getDateToAtTheBeginningOfTheYear() {
		
		Date date = service.getDateTo(2015, 1);
		assertEquals("01/01/2015", format.format(date));
	}
	
	@Test
	public void getDateToAtTheEndOfTheYear() {
		
		Date date = service.getDateTo(2015, 53);
		assertEquals("31/12/2015", format.format(date));
	}
	
	@Test
	public void getDateToIsIt53rdOrFirst() {
		
		Date date = service.getDateTo(2024, 53);
		assertEquals("02/01/2025", format.format(date));
		
		date = service.getDateTo(2025, 1);
		assertEquals("02/01/2025", format.format(date));
	}

}
