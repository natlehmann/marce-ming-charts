package com.bmat.digitalcharts.admin.view;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.bmat.digitalcharts.admin.model.SummaryReport;
import com.bmat.digitalcharts.admin.model.SummaryReportItem;

public class ChartSummaryExcelView extends AbstractExcelView {
	
	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(ChartSummaryExcelView.class);
	
	private static SimpleDateFormat headerDateFormat = new SimpleDateFormat("dd/MM/yy");
	
	private static NumberFormat numberFormat = NumberFormat.getIntegerInstance(new Locale("es"));
	
	private static final String NEW = "NUEVO";
	private static final String RETURN = "REING";
	
	private ChartStyleManager styleManager;

	
	static {
		numberFormat.setGroupingUsed(true);
	}
	
	private static enum ReportColumnHeader {		
		
		COMPARISON {
			public String getHeader(SummaryReport report){
				return "";
			}
			
			@Override
			public RichTextString getValue(SummaryReportItem item) {
				return new HSSFRichTextString(item.getComparison());
			}
			
			@Override
			public CellStyle getStyle(ChartStyleManager styleManager) {
				return styleManager.getCenterCellStyle();
			}
		},
		
		CURRENT_POSITION {
			@Override
			public String getHeader(SummaryReport report) {
				return "Posición";
			}
			
			@Override
			public RichTextString getValue(SummaryReportItem item) {
				return new HSSFRichTextString(String.valueOf(item.getCurrentPosition()));
			}
			
			@Override
			public CellStyle getStyle(ChartStyleManager styleManager) {
				return styleManager.getCenterCellStyle();
			}
		},
		
		PREVIOUS_POSITION {
			@Override
			public String getHeader(SummaryReport report) {
				return "SA Posicion";
			}
			
			@Override
			public RichTextString getValue(SummaryReportItem item) {
				
				if (item.getPreviousPosition() != null) {
					return new HSSFRichTextString(String.valueOf(item.getPreviousPosition()));
					
				} else {
					
					if (item.getPositionBeforePrevious() != null) {
						return new HSSFRichTextString(RETURN);
						
					} else {
						return new HSSFRichTextString(NEW);
					}
				}
			}
			
			@Override
			public CellStyle getStyle(ChartStyleManager styleManager) {
				return styleManager.getCenterCellStyle();
			}
		},
		
		POSITION_BEFORE_PREVIOUS{
			@Override
			public String getHeader(SummaryReport report) {
				return "2S Posicion";
			}
			
			@Override
			public RichTextString getValue(SummaryReportItem item) {
				return new HSSFRichTextString(item.getPositionBeforePrevious() != null ?
						String.valueOf(item.getPositionBeforePrevious()) : "-");
			}
			
			@Override
			public CellStyle getStyle(ChartStyleManager styleManager) {
				return styleManager.getCenterCellStyle();
			}
		},
		
		WEEKS {
			@Override
			public String getHeader(SummaryReport report) {
				return "Semanas";
			}
			
			@Override
			public RichTextString getValue(SummaryReportItem item) {
				return new HSSFRichTextString(item.getWeeksInRanking() != null ?
						String.valueOf(item.getWeeksInRanking()) : "");
			}
			
			@Override
			public CellStyle getStyle(ChartStyleManager styleManager) {
				return styleManager.getCenterCellStyle();
			}
		},
		
		TRACK {
			@Override
			public String getHeader(SummaryReport report) {
				return "Track";
			}
			
			@Override
			public RichTextString getValue(SummaryReportItem item) {
				return new HSSFRichTextString(item.getSongName());
			}
			
			@Override
			public CellStyle getStyle(ChartStyleManager styleManager) {
				return styleManager.getLeftCellStyle();
			}
		},
		
		ARTIST {
			@Override
			public String getHeader(SummaryReport report) {
				return "Artist";
			}
			
			@Override
			public RichTextString getValue(SummaryReportItem item) {
				return new HSSFRichTextString(item.getPerformerName());
			}
			
			@Override
			public CellStyle getStyle(ChartStyleManager styleManager) {
				return styleManager.getLeftCellStyle();
			}
		},
		
		PREVIOUS_DATE_RANGE {
			@Override
			public String getHeader(SummaryReport report) {
				
				if (report.getPreviousDateFrom() != null && report.getPreviousDateTo() != null) {
					return headerDateFormat.format(report.getPreviousDateFrom()) 
							+ " al " + headerDateFormat.format(report.getPreviousDateTo());
				}
				
				return "";
			}
			
			@Override
			public RichTextString getValue(SummaryReportItem item) {
				return new HSSFRichTextString(item.getPreviousAmount() != null ?
						numberFormat.format(item.getPreviousAmount()) : "-");
			}
			
			@Override
			public CellStyle getStyle(ChartStyleManager styleManager) {
				return styleManager.getRightCellStyle();
			}
		},
		
		PERCENTAGE {
			@Override
			public String getHeader(SummaryReport report) {
				return "%";
			}
			
			@Override
			public RichTextString getValue(SummaryReportItem item) {
				return new HSSFRichTextString(item.getComparativePercentage() != null ?
						item.getComparativePercentage() : "-");
			}
			
			@Override
			public CellStyle getStyle(ChartStyleManager styleManager) {
				return styleManager.getCenterCellStyle();
			}
		},
		
		CURRENT_DATE_RANGE {
			@Override
			public String getHeader(SummaryReport report) {
				return headerDateFormat.format(report.getDateFrom()) 
						+ " al " + headerDateFormat.format(report.getDateTo());
			}
			
			@Override
			public RichTextString getValue(SummaryReportItem item) {
				return new HSSFRichTextString(numberFormat.format(item.getCurrentAmount()));
			}
			
			@Override
			public CellStyle getStyle(ChartStyleManager styleManager) {
				return styleManager.getRightCellStyle();
			}
		},
		
		LABEL_COMPANY {
			@Override
			public String getHeader(SummaryReport report) {
				return "Disqueras";
			}
			
			@Override
			public RichTextString getValue(SummaryReportItem item) {
				return new HSSFRichTextString(item.getLabelCompanyName());
			}
			
			@Override
			public CellStyle getStyle(ChartStyleManager styleManager) {
				return styleManager.getLeftCellStyle();
			}
		},
		
		MAX_IN_CHART {
			@Override
			public String getHeader(SummaryReport report) {
				return "Max";
			}
			
			@Override
			public RichTextString getValue(SummaryReportItem item) {
				return new HSSFRichTextString(item.getBestPosition() != null ?
						String.valueOf(item.getBestPosition()) : "");
			}
			
			@Override
			public CellStyle getStyle(ChartStyleManager styleManager) {
				return styleManager.getCenterCellStyle();
			}
		};
		
		


		public abstract String getHeader(SummaryReport report);

		public abstract RichTextString getValue(SummaryReportItem item);

		public abstract CellStyle getStyle(ChartStyleManager styleManager);
		
	}
	
	

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		SummaryReport report = (SummaryReport) model.get("summaryReport");
		
		String reportName = "reporte-" + report.getRight().getName() + "-Sem" 
					+ report.getWeekFrom() + "a" + report.getWeekTo() + ".xls";
		
        response.setHeader("Content-Disposition", "attachment; filename=\"" + reportName + "\"");
        
        this.styleManager = new ChartStyleManager(workbook);
		
		HSSFSheet excelSheet = workbook.createSheet(report.getRight().getName());
		excelSheet.setDefaultColumnWidth(7);         
		setExcelHeader(excelSheet, workbook, report);
		
		setExcelRows(workbook, excelSheet, report);
		
	}
	
	
	public void setExcelHeader(HSSFSheet excelSheet, HSSFWorkbook workbook, SummaryReport report) {
		
		CellStyle style = styleManager.getColumnHeaderStyle(); 
        
		HSSFRow excelHeader = excelSheet.createRow(0);
		excelHeader.createCell(1).setCellValue(
				report.getRight().getName() + " from " + report.getCountry().getName());
		excelHeader.getCell(1).setCellStyle(styleManager.getTitleCellStyle());
		excelHeader.setHeightInPoints(40);
		
		excelHeader = excelSheet.createRow(2);
		excelHeader.setHeightInPoints(22);
		excelHeader.createCell(1).setCellValue("Semana " + report.getWeekFrom());
		excelHeader.getCell(1).setCellStyle(styleManager.getSubtitleCellStyle());
		
		if (!report.getWeekFrom().equals(report.getWeekTo())) {
			excelHeader.createCell(3).setCellValue("a la Semana " + report.getWeekTo());
			excelHeader.getCell(3).setCellStyle(styleManager.getSubtitleCellStyle());
		}
		
		if (report.isMonthly()) {
			excelHeader.createCell(6).setCellValue("MES DE " + report.getMonthName().toUpperCase());
			excelHeader.getCell(6).setCellStyle(styleManager.getSubtitleBoldCellStyle());
		}
		
		excelHeader = excelSheet.createRow(3);
		excelHeader.setHeightInPoints(22);
		excelHeader.createCell(1).setCellValue("del " + headerDateFormat.format(report.getDateFrom()));
		excelHeader.getCell(1).setCellStyle(styleManager.getSubtitleCellStyle());
		
		excelHeader.createCell(3).setCellValue("al " + headerDateFormat.format(report.getDateTo()));
		excelHeader.getCell(3).setCellStyle(styleManager.getSubtitleCellStyle());
		
		excelHeader.createCell(11).setCellValue(report.getSources());
		excelHeader.getCell(11).setCellStyle(styleManager.getRightCellStyle());
		
		excelHeader = excelSheet.createRow(5);
		excelHeader.setHeightInPoints(30);
		
		for (ReportColumnHeader header : ReportColumnHeader.values()) {
			
			excelHeader.createCell(header.ordinal()).setCellValue(header.getHeader(report));
			excelHeader.getCell(header.ordinal()).setCellStyle(style);
		}
	}



	


	
	


	public void setExcelRows(HSSFWorkbook workbook, HSSFSheet excelSheet, SummaryReport report){
		
		
		int row = 6;
		for (SummaryReportItem item : report.getItems()) {
			
			HSSFRow excelRow = excelSheet.createRow(row);
			excelRow.setHeightInPoints(18);
			
			for (ReportColumnHeader header : ReportColumnHeader.values()) {
				excelRow.createCell(header.ordinal()).setCellValue(header.getValue(item));
				
				excelRow.getCell(header.ordinal()).setCellStyle(header.getStyle(styleManager));

				if (header.getValue(item).getString().equals(NEW)) {
					excelSheet.addMergedRegion(new CellRangeAddress(
							row,row,header.ordinal(),header.ordinal() + 1));
				}
			}
			
			row++;
		}

		excelSheet.setColumnWidth(5, excelSheet.getColumnWidth(0) * 8);
		excelSheet.setColumnWidth(6, excelSheet.getColumnWidth(0) * 5);
		excelSheet.setColumnWidth(7, excelSheet.getColumnWidth(0) * 2);
		excelSheet.setColumnWidth(8, excelSheet.getColumnWidth(0) * 2);
		excelSheet.setColumnWidth(9, excelSheet.getColumnWidth(0) * 2);
		excelSheet.setColumnWidth(10, excelSheet.getColumnWidth(0) * 5);
	}


}
