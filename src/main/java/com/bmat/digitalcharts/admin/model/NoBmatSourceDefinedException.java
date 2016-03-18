package com.bmat.digitalcharts.admin.model;

public class NoBmatSourceDefinedException extends BmatSourceUriException {

	private static final long serialVersionUID = -6984440344255781540L;

	public NoBmatSourceDefinedException() {
		this("No existe un source con el nombre 'BMatTopTracks' (respetando mayúsculas y minúsculas). "
				+ "Por favor agregue este registro a la tabla de RestSource antes de intentar de nuevo.");
	}

	public NoBmatSourceDefinedException(String message) {
		super(message);
	}

	public NoBmatSourceDefinedException(Throwable cause) {
		super(cause);
	}

	public NoBmatSourceDefinedException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoBmatSourceDefinedException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
