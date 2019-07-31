package com.github.grantjforrester.uservice.starter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static java.lang.String.format;

/*
 * Starts up application on random port (to avoid port clashes)
 * and runs RestAssured tests against running application.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"jwt.secret=Dummy-Dummy-Dummy-Dummy-Dummy-Dummy"})
public class ITTestHelloResource {

    private static final String URL_TEMPLATE = "http://localhost:%d" + "/resources/hello";
    private static final String NO_ROLE_JWT = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"
            + ".eyJzdWIiOiJKb2UgQmxvZ2dzIiwicm9sZXMiOltdfQ"
            + ".FQ6NrilryA6nUYS_GPlTO2Lsy42h87TOBRE-1d4iSCM";
    private static final String BAD_SIG_JWT = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"
            + ".eyJzdWIiOiJKb2UgQmxvZ2dzIiwicm9sZXMiOltdfQ"
            + ".8aJC6-fvPknM3ZgSe_OpjC2M37fePkVGkOoK8UV4HbM";
    private static final String GOOD_JWT = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"
            + ".eyJzdWIiOiJKb2UgQmxvZ2dzIiwicm9sZXMiOlsiUk9MRV9IZWxsb1JvbGUiXX0"
            + ".d0f3L2xDoWnI9WSK8tkYx5zHcXPbQATH2eHRUcY2KwA";

    @LocalServerPort
    private int serverPort;
    private String resourceUrl;


    @BeforeEach
    public void setupUrl() {
        resourceUrl = format(URL_TEMPLATE, serverPort);
    }

    @Test
    public void shouldReturn401IfNoJwt() throws Exception {
        when().
            get(resourceUrl).
        then().
            statusCode(401);
    }

    @Test
    public void shouldReturn401IfBadSig() throws Exception {
        given().
            header("Authorization", "Bearer " + BAD_SIG_JWT).
        when().
            get(resourceUrl).
        then().
            statusCode(401);
    }

    @Test
    public void shouldReturn403IfNoRole() throws Exception {
        given().
            header("Authorization", "Bearer " + NO_ROLE_JWT).
        when().
            get(resourceUrl).
        then().
            statusCode(403);
    }

    @Test
    public void shouldReturn200IfGoodRequest() throws Exception {
        given().
            header("Authorization", "Bearer " + GOOD_JWT).
        when().
            get(resourceUrl).
        then().
            statusCode(200);
    }
}
