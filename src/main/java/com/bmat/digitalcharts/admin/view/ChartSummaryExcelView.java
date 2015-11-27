package com.bmat.digitalcharts.admin.view;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class ChartSummaryExcelView extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> params,
			HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HSSFSheet excelSheet = workbook.createSheet("Animal List");
		setExcelHeader(excelSheet);
		
//		List animalList = (List) model.get("animalList");
		setExcelRows(excelSheet, new LinkedList<>());
		
	}
	
	
	public void setExcelHeader(HSSFSheet excelSheet) {
		HSSFRow excelHeader = excelSheet.createRow(0);
		excelHeader.createCell(0).setCellValue("Id");
		excelHeader.createCell(1).setCellValue("Name");
		excelHeader.createCell(2).setCellValue("Type");
		excelHeader.createCell(3).setCellValue("Aggressive");
		excelHeader.createCell(4).setCellValue("Weight");
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
