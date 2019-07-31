package com.github.grantjforrester.uservice.starter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    private static final Logger LOG = LoggerFactory.getLogger(HelloService.class);

    @Secured("ROLE_HelloRole")
    public String readHello() {
        LOG.trace("Parameters: none");
        String hello = "Hello from service";
        LOG.trace("Returning: {}", hello);

        return hello;
    }
}
