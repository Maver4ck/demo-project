package com.dstreltsov.testtask.util;

/**
 * Simple error class which is commonly used for JSON serialization/deserialization.
 *
 * @author dstreltsov
 * @since 11.02.2023
 */
public class Error {

    private final String field;
    private final String message;

    public Error(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
