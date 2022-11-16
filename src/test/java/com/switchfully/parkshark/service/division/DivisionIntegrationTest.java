package com.switchfully.parkshark.service.division;

import com.switchfully.parkshark.service.division.DTO.DivisionDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class DivisionIntegrationTest {

    @LocalServerPort
    private int port;
    private final static String URL = "https://keycloak.switchfully.com/auth/realms/parkShark-babyshark/protocol/openid-connect/token";
    private static String response;

    @BeforeAll
    static void setUp() {

        response = RestAssured
                .given()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("grant_type", "password")
                .formParam("username", "testManager")
                .formParam("password", "password")
                .formParam("client_id", "parkShark")
                .formParam("client_secret", "3592b0b2-c26c-4529-9ffa-c2d0b58687a5")
                .when()
                .post(URL)
                .then()
                .extract()
                .path("access_token")
                .toString();
    }
    @Test
    void addDivisionByManager1_DivisionAlreadyExists() {
        String body = "{\"name\":\"Division5\",\"originalName\":\"QPark\",\"director\":\"Director01\"}";
        RestAssured
                .given()
                .header("Authorization", "Bearer " + response)
                .baseUri("http://localhost")
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

    @Test
    void addDivisionByManager2_HappyPath() {
        String body = "{\"name\":\"Division5\",\"originalName\":\"QPark\",\"director\":\"Director01\"}";
        RestAssured
                .given()
                .header("Authorization", "Bearer " + response)
                .baseUri("http://localhost")
                .port(port)
                .when()
                .accept(ContentType.JSON)
                .body(body)
                .contentType(ContentType.JSON)
                .post("/divisions")
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }

    @Test
    void getAllDivisionsBymanager_HappyPath() {
        String body = "{\"name\":\"Division5\",\"originalName\":\"QPark\",\"director\":\"Director01\"}";
        List<String> expectedList = new ArrayList<>(List.of("Division5"));

        DivisionDTO[] actualList =RestAssured
                .given()
                .header("Authorization", "Bearer " + response)
                .baseUri("http://localhost")
                .port(port)
                .when()
                .accept(ContentType.JSON)
                .body(body)
                .contentType(ContentType.JSON)
                .get("/divisions")
                .then()
                .extract()
                .as(DivisionDTO[].class);

        Assertions.assertThat(Arrays.stream(actualList).map(divisionDTO -> divisionDTO.name())).containsAll(expectedList);
    }
}
