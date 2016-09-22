package com.donghk.core.exception;

@SuppressWarnings("serial")
public class BaseException extends RuntimeException {

	public BaseException() {
		super();
	}

	public BaseException(String msg) {
		super(msg);
	}

	public BaseException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public BaseException(Throwable cause) {
		super(cause);
	}
}
