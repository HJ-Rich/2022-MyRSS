package com.rssmanager.exception;

public class UnauthorizedException extends MyRSSException {
    private static final String DEFAULT_MESSAGE = "인증 정보를 확인해주세요";

    public UnauthorizedException() {
        super(DEFAULT_MESSAGE);
    }

    public UnauthorizedException(final String message) {
        super(message);
    }
}
