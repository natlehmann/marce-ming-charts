package com.bmat.digitalcharts.admin.view;

import java.io.BufferedWriter;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import com.bmat.digitalcharts.admin.model.SummaryReport;
import com.bmat.digitalcharts.admin.model.SummaryReportItem;
import com.bmat.digitalcharts.admin.view.ReportViewUtils.Extension;

public class ChartSummaryCsvView extends AbstractView {
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	protected void renderMergedOutputModel(Map<String, Object> modelMap,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		SummaryReport report = (SummaryReport) modelMap.get("summaryReport");

	    BufferedWriter writer = new BufferedWriter(response.getWriter());

	    response.setHeader("Content-Disposition","attachment; filename=\"" 
	    		+ ReportViewUtils.getReportName(report, Extension.CSV) + "\"");

	    for (SummaryReportItem item : report.getItems()) {
	    	
	    	writer.write(dateFormat.format(report.getDateTo()));
	    	writer.write(",");
	    	writer.write(report.getRight().getCode());
	    	writer.write(",");
	    	writer.write(item.getBmatSourceUri());
	    	writer.write(",");
	    	writer.write(String.valueOf(item.getCurrentAmount()));
	    	writer.newLine();
	    	
	    }

	    writer.flush(); 
	    writer.close();

	}

}
