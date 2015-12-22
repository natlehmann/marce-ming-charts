package com.bmat.digitalcharts.admin.services;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import com.bmat.digitalcharts.admin.dao.MonthlyReportDao;
import com.bmat.digitalcharts.admin.dao.WeeklyReportDao;
import com.bmat.digitalcharts.admin.model.MonthlyReport;
import com.bmat.digitalcharts.admin.model.WeeklyReport;

public class SummaryReportServiceTest {
	
	private SummaryReportService service = new SummaryReportService();
	
	private static SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	
	private WeeklyReportDao weeklyReportDaoMock;

	private MonthlyReportDao monthlyReportDaoMock;
	
	@Before
	public void setup() {
		
		weeklyReportDaoMock = Mockito.mock(WeeklyReportDao.class);
		monthlyReportDaoMock = Mockito.mock(MonthlyReportDao.class);
		
        ReflectionTestUtils.setField(service, "weeklyReportDao", weeklyReportDaoMock);
        ReflectionTestUtils.setField(service, "monthlyReportDao", monthlyReportDaoMock);
	}

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
	
	@Test
	public void getPreviousReportWeekly() {
		
		WeeklyReport report = new WeeklyReport();
		report.setYear(2015);
		report.setWeekFrom(3);
		service.getPreviousReport(report);
		
		Mockito.verify(weeklyReportDaoMock).getReport(2015, 2);
		
	}
	
	@Test
	public void getPreviousReportMonthly() {
		
		MonthlyReport report = new MonthlyReport();
		report.setYear(2015);
		report.setWeekFrom(3);
		report.setMonth(2);
		service.getPreviousReport(report);
		
		Mockito.verify(monthlyReportDaoMock).getReport(2015, 1);
		
	}
	
	@Test
	public void getPreviousReportWeeklyBorder() {
		
		WeeklyReport report = new WeeklyReport();
		report.setYear(2015);
		report.setWeekFrom(1);
		service.getPreviousReport(report);
		
		Mockito.verify(weeklyReportDaoMock).getReport(2014, 53);
		
	}
	
	@Test
	public void getPreviousReportMonthlyBorder() {
		
		MonthlyReport report = new MonthlyReport();
		report.setYear(2015);
		report.setWeekFrom(3);
		report.setMonth(1);
		service.getPreviousReport(report);
		
		Mockito.verify(monthlyReportDaoMock).getReport(2014, 12);
		
	}

}
