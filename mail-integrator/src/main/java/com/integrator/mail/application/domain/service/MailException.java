package com.integrator.mail.application.domain.service;

public class MailException extends RuntimeException {

	private static final long serialVersionUID = -5854573975287921449L;

	public MailException() {
		super();
	}

	public MailException(String message) {
		super(message);
	}

	public MailException(String message, Throwable cause) {
		super(message, cause);
	}
}
