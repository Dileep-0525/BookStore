package com.dileep.ecommerce.ms.exceptions;
@SuppressWarnings("serial")
public class GlobalException extends RuntimeException {

	public GlobalException() {
		super();
	}

	public GlobalException(String message) {
		super(message);
	}
	
	public GlobalException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
}
