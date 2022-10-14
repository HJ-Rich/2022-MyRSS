package com.rssmanager.exception;

public class MyRSSException extends RuntimeException {

    public MyRSSException(final String message) {
        super(message);
    }


    public MyRSSException(final String message, final Exception e) {
        super(message, e);
    }
}
