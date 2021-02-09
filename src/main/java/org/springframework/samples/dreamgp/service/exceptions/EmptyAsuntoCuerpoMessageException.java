package org.springframework.samples.dreamgp.service.exceptions;

public class EmptyAsuntoCuerpoMessageException extends Exception{

	private String message;
	
	public EmptyAsuntoCuerpoMessageException(String message) {
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
