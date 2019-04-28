package com.george.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class CategoryNotFoundException extends Exception {
	
	private static final String MESSAGE = "Category not found";
	
	public CategoryNotFoundException() {
		super(MESSAGE);
	}
	
	public CategoryNotFoundException(String message) {
		super(message);
	}

}
