package com.application.chatter.profile;

public class ProfileReadException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3350156724763390386L;

	public ProfileReadException() {
	}

	public ProfileReadException(String message) {
		super(message);
	}

	public ProfileReadException(Throwable cause) {
		super(cause);
	}

	public ProfileReadException(String message, Throwable cause) {
		super(message, cause);
	}

}
