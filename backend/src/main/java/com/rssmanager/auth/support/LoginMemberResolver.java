package com.rssmanager.auth.support;

import com.rssmanager.exception.UnauthorizedException;
import com.rssmanager.util.SessionManager;
import java.util.Objects;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class LoginMemberResolver implements HandlerMethodArgumentResolver {
    private final SessionManager sessionManager;

    public LoginMemberResolver(final SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginMember.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory)
            throws Exception {

        final var member = sessionManager.getLoginMember();

        if (Objects.isNull(member)) {
            throw new UnauthorizedException();
        }

        return member;
    }
}
