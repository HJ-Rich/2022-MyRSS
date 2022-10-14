package com.rssmanager.exception;

public class InvalidRssUrlException extends BadRequestException {

    public InvalidRssUrlException(final Exception e) {
        super(e.getMessage(), e);
    }
}
