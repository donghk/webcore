package com.donghk.core.poi;

public class PoiException extends Exception {

	private static final long serialVersionUID = 264138895652354060L;

	public PoiException() {
		super();
	}

	public PoiException(String msg) {
		super(msg);
	}

	public PoiException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public PoiException(Throwable cause) {
		super(cause);
	}
}
