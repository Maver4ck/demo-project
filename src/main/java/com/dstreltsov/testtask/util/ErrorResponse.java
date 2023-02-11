package com.dstreltsov.testtask.util;

import com.fasterxml.jackson.annotation.JsonRawValue;

/**
 * HTTP error response class.
 *
 * @author dstreltsov
 * @since 11.02.2023
 */
public class ErrorResponse {

    @JsonRawValue
    private String message;
    private long   timestamp;

    public ErrorResponse(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
