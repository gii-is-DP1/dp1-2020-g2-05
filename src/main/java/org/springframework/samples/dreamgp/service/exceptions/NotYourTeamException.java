package org.springframework.samples.dreamgp.service.exceptions;

public class NotYourTeamException extends Exception {

	private String message;
	
	public NotYourTeamException(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return getMessage();
	}
	
	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}