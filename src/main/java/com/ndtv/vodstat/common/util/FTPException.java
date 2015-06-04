package com.ndtv.vodstat.common.util;

public class FTPException extends RuntimeException {
	public FTPException() {
		super("FTP异常.");
	}

	public FTPException(String arg0) {
		super(arg0);
	}

	public FTPException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public FTPException(Throwable arg0) {
		super(arg0);
	}
}
