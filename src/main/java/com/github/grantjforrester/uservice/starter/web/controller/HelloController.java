package com.github.grantjforrester.uservice.starter.web.controller;

import com.github.grantjforrester.uservice.starter.service.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resources/hello")
public class HelloController {

    private static final Logger LOG = LoggerFactory.getLogger(HelloController.class);
    private final HelloService service;

    public HelloController(HelloService service) {
        LOG.trace("Parameters: service={}", service);
        this.service = service;
        LOG.trace("Returning: none");
    }

    @GetMapping
    public String readHello() {
        LOG.trace("Parameters: none");
        String hello = service.readHello();
        LOG.trace("Returning: {}", hello);

        return hello;
    }
}
