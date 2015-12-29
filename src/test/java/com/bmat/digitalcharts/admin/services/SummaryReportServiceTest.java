package com.bmat.digitalcharts.admin.services;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.bmat.digitalcharts.admin.model.SummaryReport;
import com.bmat.digitalcharts.admin.model.SummaryReportItem;
import com.bmat.digitalcharts.admin.model.WeeklyReport;
import com.bmat.digitalcharts.admin.model.WeeklyReportItem;

public class SummaryReportServiceTest {
	
	private static final long SONG_ID_1 = 9L;
	private static final long SONG_ID_2 = 10L;

	private SummaryReportService service = new SummaryReportService();
	
	private static SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	
	private SummaryReport report1;
	private SummaryReport report2;
	private SummaryReportItem itemReport1;
	private SummaryReportItem itemReport2;
	private SummaryReportItem item;
	
	@Before
	public void setup() {
		
        report1 = new WeeklyReport();
		itemReport1 = new WeeklyReportItem();
		itemReport1.setCurrentPosition(5);
		itemReport1.setCurrentAmount(12322L);
		itemReport1.setWeeksInRanking(3);
		itemReport1.setBestPosition(5);
		itemReport1.setSongId(SONG_ID_1);
		report1.addItem(itemReport1);
		
		report2 = new WeeklyReport();
		itemReport2 = new WeeklyReportItem();
		itemReport2.setCurrentPosition(23);
		itemReport2.setCurrentAmount(12L);
		itemReport2.setWeeksInRanking(2);
		itemReport2.setBestPosition(23);
		itemReport2.setSongId(SONG_ID_1);
		report2.addItem(itemReport2);
		
		item = new WeeklyReportItem();
		item.setCurrentPosition(2);
		item.setCurrentAmount(123123L);
		item.setSongId(SONG_ID_1);
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
	public void getBestPositionNowIsBetter() {
		
		itemReport1.setCurrentPosition(5);
		itemReport1.setBestPosition(5);
		
		itemReport2.setCurrentPosition(23);
		itemReport2.setBestPosition(23);
		
		item.setCurrentPosition(2);
		
		service.setPreviousReportInfo(report1, report2, item);
		
		assertEquals(Integer.valueOf(2), item.getBestPosition());
	}
	
	@Test
	public void getBestPositionPreviousIsBetter() {
		
		itemReport1.setCurrentPosition(5);
		itemReport1.setBestPosition(5);
		
		itemReport2.setCurrentPosition(23);
		itemReport2.setBestPosition(23);
		
		item.setCurrentPosition(20);
		
		service.setPreviousReportInfo(report1, report2, item);
		
		assertEquals(Integer.valueOf(5), item.getBestPosition());
	}
	
	@Test
	public void getBestPositionPreviousIsBetterAndThereIsNoBefore() {
		
		itemReport1.setCurrentPosition(5);
		itemReport1.setBestPosition(5);
		
		itemReport2.setSongId(SONG_ID_2);
		
		item.setCurrentPosition(20);
		
		service.setPreviousReportInfo(report1, report2, item);
		
		assertEquals(Integer.valueOf(5), item.getBestPosition());
	}
	
	@Test
	public void getBestPositionBeforePreviousIsBetter() {
		
		itemReport1.setCurrentPosition(5);
		itemReport1.setBestPosition(1);
		
		itemReport2.setCurrentPosition(1);
		itemReport2.setBestPosition(1);
		
		item.setCurrentPosition(20);
		
		service.setPreviousReportInfo(report1, report2, item);
		
		assertEquals(Integer.valueOf(1), item.getBestPosition());
	}
	
	@Test
	public void getBestPositionForANewItem() {
		
		item.setCurrentPosition(20);
		item.setSongId(SONG_ID_2);
		
		service.setPreviousReportInfo(report1, report2, item);
		
		assertEquals(Integer.valueOf(20), item.getBestPosition());
	}
	
	@Test
	public void getBestPositionForAReturningItemNowIsBetter() {
		
		itemReport1.setSongId(SONG_ID_2);
		
		itemReport2.setCurrentPosition(10);
		itemReport2.setBestPosition(10);
		
		item.setCurrentPosition(2);
		
		service.setPreviousReportInfo(report1, report2, item);
		
		assertEquals(Integer.valueOf(2), item.getBestPosition());
	}
	
	@Test
	public void getBestPositionForAReturningItemBeforeWasBetter() {
		
		itemReport1.setSongId(SONG_ID_2);
		
		itemReport2.setCurrentPosition(10);
		itemReport2.setBestPosition(10);
		
		item.setCurrentPosition(20);
		
		service.setPreviousReportInfo(report1, report2, item);
		
		assertEquals(Integer.valueOf(10), item.getBestPosition());
	}
	
	@Test
	public void getWeeksInRankingHappyPath() {
		
		itemReport1.setWeeksInRanking(2);
		itemReport2.setWeeksInRanking(1);
		
		service.setPreviousReportInfo(report1, report2, item);
		
		assertEquals(Integer.valueOf(3), item.getWeeksInRanking());
	}
	
	@Test
	public void getWeeksInRankingForANewItem() {
		
		item.setSongId(SONG_ID_2);
		
		service.setPreviousReportInfo(report1, report2, item);
		
		assertEquals(Integer.valueOf(1), item.getWeeksInRanking());
	}
	
	@Test
	public void getWeeksInRankingForAReturningItem() {
		
		itemReport1.setSongId(SONG_ID_2);
		
		itemReport2.setWeeksInRanking(5);
		
		service.setPreviousReportInfo(report1, report2, item);
		
		assertEquals(Integer.valueOf(6), item.getWeeksInRanking());
	}

}
