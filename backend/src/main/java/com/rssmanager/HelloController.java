package com.rssmanager;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/hello")
@RestController
public class HelloController {

    @GetMapping
    public String hello(@CookieValue(value = "SESSION", required = false) String cookie,
                        HttpServletRequest httpServletRequest) {
        if (Objects.nonNull(cookie)) {
            return "you are logged in!";
        }

        httpServletRequest.getSession();
        return "hello from myrss~!";
    }
}
