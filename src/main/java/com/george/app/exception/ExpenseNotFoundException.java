package com.george.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ExpenseNotFoundException extends Exception {

	private static final String MESSAGE = "Expense not found";

	public ExpenseNotFoundException() {
		super(MESSAGE);
	}
	
	public ExpenseNotFoundException(String message) {
		super(message);
	}
	
}
