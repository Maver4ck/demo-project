package com.dstreltsov.testtask.util;

/**
 * Simple HTTP message class to send to API users.
 *
 * @author dstreltsov
 * @since 11.02.2023
 */
public class HTTPMessage {

    private final String message;

    public HTTPMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
