package com.github.grantjforrester.uservice.starter;

import com.github.grantjforrester.uservice.starter.service.HelloService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class HelloServiceTests {

    private HelloService testee;

    @BeforeEach
    public void setupTestee() throws Exception {
        testee = new HelloService();
    }

    @Test
    public void shouldReturnHelloMessage() throws Exception {
        assertThat(testee.readHello(), is(equalTo("Hello from service")));
    }
}
