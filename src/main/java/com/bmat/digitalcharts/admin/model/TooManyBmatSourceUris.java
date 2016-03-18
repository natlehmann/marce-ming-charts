package com.bmat.digitalcharts.admin.model;

public class TooManyBmatSourceUris extends BmatSourceUriException {

	private static final long serialVersionUID = 1197876307957782875L;
	
	public TooManyBmatSourceUris(SummaryReportItem item) {
		this("Hay más de un BmatId para '" 
				+ item.getSongName() + "' de " + item.getPerformerName());
	}

	public TooManyBmatSourceUris() {
	}

	public TooManyBmatSourceUris(String message) {
		super(message);
	}

	public TooManyBmatSourceUris(Throwable cause) {
		super(cause);
	}

	public TooManyBmatSourceUris(String message, Throwable cause) {
		super(message, cause);
	}

	public TooManyBmatSourceUris(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
