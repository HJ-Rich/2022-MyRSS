package com.rssmanager.exception;

public class NotFoundException extends MyRSSException {
    private static final String DEFAULT_MESSAGE = "존재하지 않는 자원에 대한 요청입니다";

    public NotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public NotFoundException(final String message) {
        super(message);
    }
}
