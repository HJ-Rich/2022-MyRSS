package com.rssmanager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api")
@Controller
public class DocumentController {

    @GetMapping
    public String docs() {
        return "index.html";
    }
}
