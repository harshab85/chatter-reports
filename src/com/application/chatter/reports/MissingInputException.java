package com.application.chatter.reports;

public class MissingInputException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6852984831558633014L;

	public MissingInputException() {
	}

	public MissingInputException(String message) {
		super(message);
	}

	public MissingInputException(Throwable cause) {
		super(cause);
	}

	public MissingInputException(String message, Throwable cause) {
		super(message, cause);
	}

}
