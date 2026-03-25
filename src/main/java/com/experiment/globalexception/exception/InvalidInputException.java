package com.experiment.globalexception.exception;

public class InvalidInputException extends RuntimeException {

    private final String field;
    private final Object rejectedValue;

    public InvalidInputException(String field, Object rejectedValue, String message) {
        super(message);
        this.field = field;
        this.rejectedValue = rejectedValue;
    }

    public InvalidInputException(String message) {
        super(message);
        this.field = "unknown";
        this.rejectedValue = null;
    }

    public String getField() {
        return field;
    }

    public Object getRejectedValue() {
        return rejectedValue;
    }
}
