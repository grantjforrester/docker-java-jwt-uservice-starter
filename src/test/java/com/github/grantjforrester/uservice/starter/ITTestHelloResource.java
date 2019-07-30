package com.github.grantjforrester.uservice.starter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.when;
import static java.lang.String.format;

/*
 * Starts up application on random port (to avoid port clashes)
 * and runs RestAssured tests against running application.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class ITTestHelloResource {

    private static final String URL_TEMPLATE = "http://localhost:%d" + "/resources/hello";

    @LocalServerPort
    private int serverPort;
    private String resourceUrl;


    @BeforeEach
    public void setupUrl() {
        resourceUrl = format(URL_TEMPLATE, serverPort);
    }

    @Test
    public void shouldReturn200IfGoodRequest() throws Exception {
        when().
            get(resourceUrl).
        then().
            statusCode(200);
    }
}
