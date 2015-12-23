package com.bmat.digitalcharts.admin.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SummaryReportItemTest {
	
	private SummaryReportItem item;
	
	@Before
	public void init() {
		item = new WeeklyReportItem();
	}

	@Test
	public void getComparisonEqual() {
		item.setCurrentPosition(1);
		item.setPreviousPosition(1);
		
		assertEquals(SummaryReportItem.EQUAL, item.getComparison());
	}
	
	@Test
	public void getComparisonIsBetter() {
		item.setCurrentPosition(1);
		item.setPreviousPosition(4);
		
		assertEquals(SummaryReportItem.OPEN_COMPARISON + "+3" + SummaryReportItem.CLOSE_COMPARISON, 
				item.getComparison());
		
	}
	
	@Test
	public void getComparisonIsWorse() {
		item.setCurrentPosition(11);
		item.setPreviousPosition(4);
		
		assertEquals(SummaryReportItem.OPEN_COMPARISON + "-7" + SummaryReportItem.CLOSE_COMPARISON, 
				item.getComparison());
		
	}
	
	@Test
	public void getComparisonDoesNotFailWhenNull() {
		item.setCurrentPosition(11);		
		assertEquals("", item.getComparison());
		
		item.setCurrentPosition(null);		
		assertEquals("", item.getComparison());
		
	}
	
	@Test
	public void getComparativePercentageIsCero() {
		item.setCurrentAmount(100L);
		item.setPreviousAmount(100L);
		
		assertEquals("0%", item.getComparativePercentage());		
	}
	
	@Test
	public void getComparativePercentageRoundNextToCero() {
		item.setCurrentAmount(222186L);
		item.setPreviousAmount(223211L);
		
		assertEquals("0%", item.getComparativePercentage());		
	}
	
	@Test
	public void getComparativePercentageIsPositive() {
		item.setCurrentAmount(121L);
		item.setPreviousAmount(100L);
		
		assertEquals("21%", item.getComparativePercentage());		
	}
	
	@Test
	public void getComparativePercentageIsPositiveWithoutDecimals() {
		item.setCurrentAmount(121L);
		item.setPreviousAmount(87L);
		
		assertEquals("39%", item.getComparativePercentage());		
	}
	
	@Test
	public void getComparativePercentageIsPositiveAndRoundsUp() {
		item.setCurrentAmount(429952L);
		item.setPreviousAmount(398738L);
		
		assertEquals("8%", item.getComparativePercentage());		
	}
	
	@Test
	public void getComparativePercentageIsNegative() {
		item.setCurrentAmount(367643L);
		item.setPreviousAmount(395458L);
		
		assertEquals("-7%", item.getComparativePercentage());		
	}

}
