package com.bmat.digitalcharts.admin.view;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class ChartSummaryExcelView extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> params,
			HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
        response.setHeader("Content-Disposition", "attachment; filename=\"reporte.xls\"");
		
		HSSFSheet excelSheet = workbook.createSheet("Animal List");
		excelSheet.setDefaultColumnWidth(30);         
		setExcelHeader(excelSheet, workbook);
		
//		List animalList = (List) model.get("animalList");
		setExcelRows(excelSheet, new LinkedList<>());
		
	}
	
	
	public void setExcelHeader(HSSFSheet excelSheet, HSSFWorkbook workbook) {
		
		CellStyle style = workbook.createCellStyle();       
        Font font = workbook.createFont();       
        font.setFontName("Arial");        
        style.setFillForegroundColor(HSSFColor.BLUE.index);      
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);       
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);       
        font.setColor(HSSFColor.WHITE.index);      
        style.setFont(font); 
        
		HSSFRow excelHeader = excelSheet.createRow(0);
		
		excelHeader.createCell(0).setCellValue("Id");
		excelHeader.getCell(0).setCellStyle(style);   
		
		excelHeader.createCell(1).setCellValue("Name");
		excelHeader.getCell(1).setCellStyle(style);   
		
		excelHeader.createCell(2).setCellValue("Type");
		excelHeader.getCell(2).setCellStyle(style);   
		
		excelHeader.createCell(3).setCellValue("Aggressive");
		excelHeader.getCell(3).setCellStyle(style);   
		
		excelHeader.createCell(4).setCellValue("Weight");
		excelHeader.getCell(4).setCellStyle(style);   
	}
	
	public void setExcelRows(HSSFSheet excelSheet, List animalList){
		int record = 1;
		for (int i = 0; i < 10; i++) {
			HSSFRow excelRow = excelSheet.createRow(record++);
			excelRow.createCell(0).setCellValue("id " + i);
			excelRow.createCell(1).setCellValue("nombre " + i);
			excelRow.createCell(2).setCellValue("tipo " + i);
			excelRow.createCell(3).setCellValue("agresivo " + i);
			excelRow.createCell(4).setCellValue("peso " + i);
		}
	}

}
