package com.rssmanager.util;

import com.rssmanager.member.domain.Member;
import javax.servlet.http.HttpSession;

public interface SessionManager {
    boolean isLoggedIn();

    HttpSession login(Member member);

    <T> T getAttribute(String key);

    void invalidate();
}
