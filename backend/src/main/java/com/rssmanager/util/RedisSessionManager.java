package com.rssmanager.util;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class RedisSessionManager implements SessionManager {

    private RedisSessionManager() {
    }

    @Override
    public boolean isLoggedIn() {
        return Objects.nonNull(createSessionWithFlagParameter(false));
    }

    @Override
    public HttpSession login(final Long memberId) {
        HttpSession httpSession = createSessionWithFlagParameter(true);
        httpSession.setAttribute("memberId", memberId);

        return httpSession;
    }

    private HttpSession createSessionWithFlagParameter(boolean create) {
        HttpServletRequest httpServletRequest =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        return httpServletRequest.getSession(create);
    }
}
