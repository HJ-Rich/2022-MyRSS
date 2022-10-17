package com.rssmanager.exception.dto;

import lombok.Getter;

@Getter
public class ExceptionResponse {
    private final String message;

    public ExceptionResponse(final String message) {
        this.message = message;
    }
}
