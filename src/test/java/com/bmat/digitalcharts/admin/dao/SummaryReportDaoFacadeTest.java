package com.bmat.digitalcharts.admin.dao;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import com.bmat.digitalcharts.admin.model.MonthlyReport;
import com.bmat.digitalcharts.admin.model.WeeklyReport;

public class SummaryReportDaoFacadeTest {
	
	private SummaryReportDaoFacade service = new SummaryReportDaoFacade();
	
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
