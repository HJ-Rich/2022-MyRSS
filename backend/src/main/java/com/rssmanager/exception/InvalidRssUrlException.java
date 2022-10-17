package com.rssmanager.exception;

public class InvalidRssUrlException extends BadRequestException {
    private static final String DEFAULT_MESSAGE = "RSS 주소를 확인해주세요";

    public InvalidRssUrlException() {
        super(DEFAULT_MESSAGE);
    }

    public InvalidRssUrlException(final Exception e) {
        super(e.getMessage(), e);
    }
}
