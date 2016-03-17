package com.bmat.digitalcharts.admin.view;

import com.bmat.digitalcharts.admin.model.SummaryReport;

public class ReportViewUtils {
	
	public static enum Extension {
		
		XLS {
			@Override
			public String getReportName(SummaryReport report) {
				return "reporte-" + report.getRight().getName() + "-Sem" 
						+ report.getWeekFrom() + "a" + report.getWeekTo() + ".xls";
			}
		},
		
		CSV {
			@Override
			public String getReportName(SummaryReport report) {
				return report.getCountry().getCode() + "-" + report.getRight().getName() + "-Sem"
						+ report.getWeekFrom() + ".csv";
			}
		};
		
		public abstract String getReportName(SummaryReport report);
	}

	public static String getReportName(SummaryReport report, Extension extension) {		
		return extension.getReportName(report);
	}
}
