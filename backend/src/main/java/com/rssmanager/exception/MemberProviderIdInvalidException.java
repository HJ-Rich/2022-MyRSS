package com.rssmanager.exception;

public class MemberProviderIdInvalidException extends BadRequestException {
    private static final String DEFAULT_MESSAGE_FORMAT = "Provider Id를 확인해주세요 : %s";

    public MemberProviderIdInvalidException(final Long invalidProviderId) {
        super(String.format(DEFAULT_MESSAGE_FORMAT, invalidProviderId));
    }
}
