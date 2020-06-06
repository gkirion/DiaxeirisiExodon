package com.george.app.exception;

public class CategoryAlreadyExistsException extends Exception {

    private static final String MESSAGE = "Category already exists";

    public CategoryAlreadyExistsException() {
        this(MESSAGE);
    }

    public CategoryAlreadyExistsException(String message) {
        super(message);
    }

}
