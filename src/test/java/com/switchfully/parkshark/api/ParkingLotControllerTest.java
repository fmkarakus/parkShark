package com.switchfully.parkshark.api;

import com.switchfully.parkshark.service.ParkingLotService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class ParkingLotControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    ParkingLotService parkingLotService;

    @Test
   void createNewParkingLot_HappyPath(){


       String requestedBody= "{\"name\":\"name\",\"category\":\"UNDERGROUND\",\"maxCapacity\":100,\"pricePerHour\":10,\"contactPerson\":{\"name\":\"contactperson\",\"email\":\"contactPerson@gmail.com\",\"mobilePhoneNumber\":\"0486555555\",\"streetName\":\"street\",\"streetNumber\":\"5\",\"postalCode\":{\"postalCode\":\"999\",\"label\":\"BestatNIet123\"}}}";
       RestAssured
                .given()
                .baseUri("http://localhost")
                .port(port)
                .header("Content-type", "application/json")
                .and()
                .body(requestedBody)
                .accept(ContentType.JSON)
                .when()
                .post("/parkinglots")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value());


    }

}