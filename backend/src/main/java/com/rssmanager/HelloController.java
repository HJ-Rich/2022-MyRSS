package com.rssmanager;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/hello")
@RestController
public class HelloController {

    @GetMapping
    public String hello() {
        return "hello from myrss";
    }
}
