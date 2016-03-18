package com.bmat.digitalcharts.admin.model;

public class NoBmatSourceUri extends BmatSourceUriException {

	private static final long serialVersionUID = 1197876307957782875L;
	
	public NoBmatSourceUri(SummaryReportItem item) {
		this("No existe BmatId para '" 
				+ item.getSongName() + "' de " + item.getPerformerName());
	}

	public NoBmatSourceUri() {
	}

	public NoBmatSourceUri(String message) {
		super(message);
	}

	public NoBmatSourceUri(Throwable cause) {
		super(cause);
	}

	public NoBmatSourceUri(String message, Throwable cause) {
		super(message, cause);
	}

	public NoBmatSourceUri(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
