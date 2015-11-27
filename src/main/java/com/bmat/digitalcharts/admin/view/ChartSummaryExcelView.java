package com.bmat.digitalcharts.admin.view;

import java.text.SimpleDateFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.RichTextString;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.bmat.digitalcharts.admin.model.SummaryReport;
import com.bmat.digitalcharts.admin.model.SummaryReportItem;

public class ChartSummaryExcelView extends AbstractExcelView {
	
	private static SimpleDateFormat headerDateFormat = new SimpleDateFormat("dd/MM/yy");
	
	private static enum ReportColumnHeader {		
		
		COMPARISON {
			public String getHeader(SummaryReport report){
				return "";
			}
			
			@Override
			public RichTextString getValue(SummaryReportItem item) {
				return new HSSFRichTextString(item.getComparison());
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
		},
		
		PREVIOUS_POSITION {
			@Override
			public String getHeader(SummaryReport report) {
				return "SA Posicion";
			}
			
			@Override
			public RichTextString getValue(SummaryReportItem item) {
				return new HSSFRichTextString(String.valueOf(item.getPreviousPosition()));
			}
		},
		
		POSITION_BEFORE_PREVIOUS{
			@Override
			public String getHeader(SummaryReport report) {
				return "2S Posicion";
			}
			
			@Override
			public RichTextString getValue(SummaryReportItem item) {
				return new HSSFRichTextString(String.valueOf(item.getPositionBeforePrevious()));
			}
		},
		
		WEEKS {
			@Override
			public String getHeader(SummaryReport report) {
				return "Semanas";
			}
			
			@Override
			public RichTextString getValue(SummaryReportItem item) {
				// TODO FALTA IMPLEMENTAR
				return new HSSFRichTextString("FALTA IMPLEMENTAR");
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
				// TODO: FORMATEAR NUMERO
				return new HSSFRichTextString(String.valueOf(item.getPreviousAmount()));
			}
		},
		
		PERCENTAGE {
			@Override
			public String getHeader(SummaryReport report) {
				return "%";
			}
			
			@Override
			public RichTextString getValue(SummaryReportItem item) {
				return new HSSFRichTextString(item.getComparativePercentage());
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
				// TODO: FORMATEAR NUMERO
				return new HSSFRichTextString(String.valueOf(item.getCurrentAmount()));
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
		},
		
		MAX_IN_CHART {
			@Override
			public String getHeader(SummaryReport report) {
				return "Max";
			}
			
			@Override
			public RichTextString getValue(SummaryReportItem item) {
				// TODO FALTA IMPLEMENTAR
				return new HSSFRichTextString("FALTA IMPLEMENTAR");
			}
		};
		
		public abstract String getHeader(SummaryReport report);

		public abstract RichTextString getValue(SummaryReportItem item);
		
	}

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
        response.setHeader("Content-Disposition", "attachment; filename=\"reporte.xls\"");
        
        SummaryReport report = (SummaryReport) model.get("summaryReport");
		
		HSSFSheet excelSheet = workbook.createSheet("Animal List");
		excelSheet.setDefaultColumnWidth(30);         
		setExcelHeader(excelSheet, workbook, report);
		
		setExcelRows(excelSheet, report);
		
	}
	
	
	public void setExcelHeader(HSSFSheet excelSheet, HSSFWorkbook workbook, SummaryReport report) {
		
		CellStyle style = getColumnHeaderStyle(workbook); 
        
		HSSFRow excelHeader = excelSheet.createRow(0);
		excelHeader.createCell(1).setCellValue("Stream Service from " + report.getCountry().getName());
		
		excelHeader = excelSheet.createRow(2);
		excelHeader.createCell(1).setCellValue("Semana " + report.getWeekFrom());
		
		if (report.getWeekFrom().equals(report.getWeekTo())) {
			excelHeader.createCell(2).setCellValue("a la ");
			excelHeader.createCell(3).setCellValue("Semana " + report.getWeekTo());
		}
		
		excelHeader = excelSheet.createRow(3);
		excelHeader.createCell(1).setCellValue("del " + headerDateFormat.format(report.getDateFrom()));
		excelHeader.createCell(2).setCellValue("al " + headerDateFormat.format(report.getDateTo()));
		
		excelHeader = excelSheet.createRow(5);
		
		for (ReportColumnHeader header : ReportColumnHeader.values()) {
			
			excelHeader.createCell(header.ordinal()).setCellValue(header.getHeader(report));
			excelHeader.getCell(header.ordinal()).setCellStyle(style);
		}
	}


	private CellStyle getColumnHeaderStyle(HSSFWorkbook workbook) {
		CellStyle style = workbook.createCellStyle();       
        Font font = workbook.createFont();       
        font.setFontName("Arial");        
        style.setFillForegroundColor(HSSFColor.BLUE.index);      
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);       
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);       
        font.setColor(HSSFColor.WHITE.index);      
        style.setFont(font);
		return style;
	}
	
	public void setExcelRows(HSSFSheet excelSheet, SummaryReport report){
		
		int row = 6;
		for (SummaryReportItem item : report.getItems()) {
			
			HSSFRow excelRow = excelSheet.createRow(row++);
			
			for (ReportColumnHeader header : ReportColumnHeader.values()) {
				excelRow.createCell(header.ordinal()).setCellValue(header.getValue(item));
			}
		}
	}

}
