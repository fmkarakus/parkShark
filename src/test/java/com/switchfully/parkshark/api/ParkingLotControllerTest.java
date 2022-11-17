package com.switchfully.parkshark.api;

import com.switchfully.parkshark.domain.contactperson.ContactPerson;
import com.switchfully.parkshark.domain.contactperson.ContactPersonRepository;
import com.switchfully.parkshark.domain.parkinglot.Category;
import com.switchfully.parkshark.domain.parkinglot.NewParkingLotDTO;
import com.switchfully.parkshark.domain.postalcode.PostalCodeRepository;
import com.switchfully.parkshark.service.parkinglot.ParkingLotService;
import com.switchfully.parkshark.service.parkinglot.ParkingLotValidation;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.NoSuchElementException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class ParkingLotControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    ParkingLotService parkingLotService;
    @Autowired
    PostalCodeRepository postalCodeRepository;
    @Autowired
    ContactPersonRepository contactPersonRepository;

    ParkingLotValidation parkingLotValidation;


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
                .formParam("client_secret", "d7692741-2a2f-42e3-8ac0-163ef4f247b9")
                .when()
                .post(URL)
                .then()
                .extract()
                .path("access_token")
                .toString();
    }

    @Test
   void createNewParkingLot_HappyPath(){
       String requestedBody= "{\"name\":\"nae\",\"category\":\"UNDERGROUND\",\"maxCapacity\":100,\"pricePerHour\":10,\"contactPersonId\":1,\"streetName\":\"street\",\"streetNumber\":\"5\",\"postalCode\":\"1111\",\"divisionId\":1}";
        RestAssured
                .given()
                .header("Authorization", "Bearer " + response)
                .baseUri("http://localhost")
                .port(port)
                .when()
                .accept(ContentType.JSON)
                .body(requestedBody)
                .contentType(ContentType.JSON)
                .post("/parkinglots")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value());

    }

    @Test
    void validateNameOfParkingLot(){
        NewParkingLotDTO newParkingLotDTO = new NewParkingLotDTO("", "ABOVE_GROUND",100,10,1L,"street","5","1111",1L);
        Assertions.assertThrows(IllegalArgumentException.class, () -> parkingLotService.createParkingLot(newParkingLotDTO));
    }

    @Test
    void validateCategoryOfParkingLot(){
        NewParkingLotDTO newParkingLotDTO = new NewParkingLotDTO("name", null,100,10,1L,"street","5","1111",1L);
        Assertions.assertThrows(IllegalArgumentException.class, () -> parkingLotService.createParkingLot(newParkingLotDTO));
    }
    @Test
    void validateCategoryRandomOfParkingLot(){
        NewParkingLotDTO newParkingLotDTO = new NewParkingLotDTO("name", "random",100,10,1L,"street","5","1111",1L);
        Assertions.assertThrows(NoSuchElementException.class, () -> parkingLotService.createParkingLot(newParkingLotDTO));
    }
    @Test
    void validateCapacityNotNegativeOfParkingLot(){
        NewParkingLotDTO newParkingLotDTO = new NewParkingLotDTO("name", "ABOVE_GROUND",-1,10,1L,"street","5","1111",1L);
        Assertions.assertThrows(IllegalArgumentException.class, () -> parkingLotService.createParkingLot(newParkingLotDTO));
    }
    @Test
    void validatePricePerHourNotNegativeOfParkingLot(){
        NewParkingLotDTO newParkingLotDTO = new NewParkingLotDTO("name", "ABOVE_GROUND",100,-10,1L,"street","5","1111",1L);
        Assertions.assertThrows(IllegalArgumentException.class, () -> parkingLotService.createParkingLot(newParkingLotDTO));
    }
    @Test
    void validateContactPersonExistInParkingLot(){
        NewParkingLotDTO newParkingLotDTO = new NewParkingLotDTO("name", "ABOVE_GROUND",100,10,5016L,"street","5","1111",1L);
        Assertions.assertThrows(IllegalArgumentException.class, () -> parkingLotService.createParkingLot(newParkingLotDTO));
    }
    @Test
    void validateStreetNameOfParkingLot(){
        NewParkingLotDTO newParkingLotDTO = new NewParkingLotDTO("name", "ABOVE_GROUND",100,10,1L,"","5","1111",1L);
        Assertions.assertThrows(IllegalArgumentException.class, () -> parkingLotService.createParkingLot(newParkingLotDTO));
    }
    @Test
    void validateStreetNumberOfParkingLot(){
        NewParkingLotDTO newParkingLotDTO = new NewParkingLotDTO("name", "ABOVE_GROUND",100,10,1L,"street",null,"1111",1L);
        Assertions.assertThrows(IllegalArgumentException.class, () -> parkingLotService.createParkingLot(newParkingLotDTO));
    }

    @Test
    void validatePostalCodeExistInParkingLot(){
        NewParkingLotDTO newParkingLotDTO = new NewParkingLotDTO("name", "ABOVE_GROUND",100,10,1L,"street","5","6666",1L);
        Assertions.assertThrows(IllegalArgumentException.class, () -> parkingLotService.createParkingLot(newParkingLotDTO));
    }
}