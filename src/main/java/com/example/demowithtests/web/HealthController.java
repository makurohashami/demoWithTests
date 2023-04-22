package com.example.demowithtests.web;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class HealthController {
    @GetMapping("/healthcheck")
    @ResponseStatus(HttpStatus.OK)
    public void healthcheck() {
    }
}
