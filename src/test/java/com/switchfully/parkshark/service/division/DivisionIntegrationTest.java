package com.switchfully.parkshark.service.division;

import com.switchfully.parkshark.service.division.DTO.CreateDivisionDTO;
import com.switchfully.parkshark.service.division.DTO.DivisionDTO;
import com.switchfully.parkshark.service.division.DTO.SubdivisionDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.test.annotation.DirtiesContext;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DivisionIntegrationTest {

    @LocalServerPort
    private int port;
    private final static String URL = "https://keycloak.switchfully.com/auth/realms/parkShark-babyshark/protocol/openid-connect/token";
    private static String response;

    @Autowired
    private DivisionService divisionService;

    @BeforeAll
    static void setUp() {

        response = RestAssured
                .given()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("grant_type", "password")
                .formParam("username", "testManager")
                .formParam("password", "password")
                .formParam("client_id", "parkShark")
                .formParam("client_secret", "d7692741-2a2f-42e3-8ac0-163ef4f247b9")
                .when()
                .post(URL)
                .then()
                .extract()
                .path("access_token")
                .toString();


    }
    @Test
    void addDivisionByManager_HappyPath() {
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
    void addDivisionByManager2_DivisionALreadyExists() {
        divisionService.createDivision(new CreateDivisionDTO("test", "Qpark", "me"));

        String body = "{\"name\":\"test\",\"originalName\":\"QPark\",\"director\":\"Director01\"}";
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
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void getAllDivisionsByManager_HappyPath() {
        divisionService.createDivision(new CreateDivisionDTO("test1", "Qpark", "me"));
        divisionService.createDivision(new CreateDivisionDTO("test2", "Qpark", "me"));


        List<String> expectedList = new ArrayList<>(List.of("test1", "test2"));

        DivisionDTO[] actualList = RestAssured
                .given()
                .header("Authorization", "Bearer " + response)
                .baseUri("http://localhost")
                .port(port)
                .when()
                .contentType(ContentType.JSON)
                .get("/divisions")
                .then()
                .extract()
                .as(DivisionDTO[].class);

        Assertions.assertThat(Arrays.stream(actualList).map(divisionDTO -> divisionDTO.name())).containsAll(expectedList);
    }


    @Test
    void getADivisionByManager_HappyPath() {
        divisionService.createDivision(new CreateDivisionDTO("testDivision", "Qpark", "me"));

        String expected = "testDivision";

        DivisionDTO actual = RestAssured
                .given()
                .header("Authorization", "Bearer " + response)
                .baseUri("http://localhost")
                .port(port)
                .when()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .get("/divisions?divisionId=5")
                .then()
                .extract()
                .as(DivisionDTO.class);

        org.junit.jupiter.api.Assertions.assertEquals(expected, actual.name());
    }

    @Test
    void addSubdivisionByManager_HappyPath() {
        divisionService.createDivision(new CreateDivisionDTO("testParentName","testoriginalName","bosser shark"));
        CreateDivisionDTO body=new CreateDivisionDTO("testSubName","testoriginalName","boss shark");
        SubdivisionDTO subdivisionDTO= RestAssured
                .given()
                .header("Authorization", "Bearer " + response)
                .baseUri("http://localhost")
                .port(port)
                .when()
                .accept(ContentType.JSON)
                .body(body)
                .contentType(ContentType.JSON)
                .post("/divisions/1")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(SubdivisionDTO.class);
        Assertions.assertThat(subdivisionDTO.parentId()).isEqualTo(1);
    }

    @Test
    void addSubdivisionByManager_whenGivenWrongId() {
        divisionService.createDivision(new CreateDivisionDTO("testParentName","testoriginalName","bosser shark"));
        CreateDivisionDTO body=new CreateDivisionDTO("testSubName","testoriginalName","boss shark");
        RestAssured
                .given()
                .header("Authorization", "Bearer " + response)
                .baseUri("http://localhost")
                .port(port)
                .when()
                .accept(ContentType.JSON)
                .body(body)
                .contentType(ContentType.JSON)
                .post("/divisions/100000")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());

    }
}
