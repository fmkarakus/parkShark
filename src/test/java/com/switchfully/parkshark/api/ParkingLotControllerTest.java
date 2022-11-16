package com.switchfully.parkshark.api;

import com.switchfully.parkshark.service.ParkingLotService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@DataJpaTest
class ParkingLotControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    ParkingLotService parkingLotService;

    @Test
   void createNewParkingLot_HappyPath(){



        /*RestAssured
                .given()
                .baseUri("http://localhost")
                .port(port)
                .body(requestedBody)
                .when()
                .accept(ContentType.JSON)
                .post("/parkinglots")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value());*/


    }

}