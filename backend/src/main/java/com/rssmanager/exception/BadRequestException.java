package com.rssmanager.exception;

public class BadRequestException extends MyRSSException {
    private static final String DEFAULT_MESSAGE = "잘못된 요청입니다";

    public BadRequestException() {
        super(DEFAULT_MESSAGE);
    }

    public BadRequestException(final String message) {
        super(message);
    }
}
