package com.rssmanager.util;

import com.rssmanager.member.domain.Member;
import java.util.Objects;
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
    public HttpSession login(final Member member) {
        final var httpSession = createSessionWithFlagParameter(true);
        httpSession.setAttribute("member", member);

        return httpSession;
    }

    @Override
    public <T> T getAttribute(final String key) {
        if (!isLoggedIn()) {
            return null;
        }

        final var session = createSessionWithFlagParameter(false);
        return (T) session.getAttribute(key);
    }

    @Override
    public void invalidate() {
        final var httpSession = createSessionWithFlagParameter(false);

        if (Objects.nonNull(httpSession)) {
            httpSession.invalidate();
        }
    }

    private HttpSession createSessionWithFlagParameter(boolean create) {
        final var httpServletRequest =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        return httpServletRequest.getSession(create);
    }
}
