package com.switchfully.parkshark.divisionTests;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DivisionIntegrationTest {

    @LocalServerPort
    private int port;

    @Test
    void addDivisionByManager_HappyPath() {
        String body = "{\"name\":\"Division5\",\"originalName\":\"QPark\",\"director\":\"Director01\"}";
        RestAssured
                .given()
//                .auth()
//                .preemptive()
//                .basic("admin", "password")
//                .baseUri("http://localhost")
                .port(port)
                .when()
                .accept(ContentType.JSON)
                .body(body)
                .contentType(ContentType.JSON)
                .post("/divisions")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value());
    }
}
