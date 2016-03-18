package com.bmat.digitalcharts.admin.model;

public class NonUniqueBmatSourceException extends BmatSourceUriException {

	private static final long serialVersionUID = -1186496816032448453L;

	public NonUniqueBmatSourceException() {
		this("Existe más de un source con el nombre 'BMatTopTracks'. "
				+ "Por favor verifique que este valor sea único en la tabla antes de intentar de nuevo.");
	}

	public NonUniqueBmatSourceException(String message) {
		super(message);
	}

	public NonUniqueBmatSourceException(Throwable cause) {
		super(cause);
	}

	public NonUniqueBmatSourceException(String message, Throwable cause) {
		super(message, cause);
	}

	public NonUniqueBmatSourceException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
