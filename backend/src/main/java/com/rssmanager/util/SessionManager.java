package com.rssmanager.util;

import javax.servlet.http.HttpSession;

public interface SessionManager {

    boolean isLoggedIn();

    HttpSession login(Long memberId);

    <T> T getAttribute(String key);
}
