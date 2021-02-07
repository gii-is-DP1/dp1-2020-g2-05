package org.springframework.samples.petclinic.service.exceptions;

public class DuplicatedRiderOnLineupException extends Exception {

	private String message;
	
	public DuplicatedRiderOnLineupException(String message) {
		this.message=message;
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
