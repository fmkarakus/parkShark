package com.switchfully.parkshark.api;

import com.switchfully.parkshark.domain.contactperson.ContactPersonRepository;
import com.switchfully.parkshark.domain.division.DivisionRepository;
import com.switchfully.parkshark.service.parkinglot.dto.NewParkingLotDTO;
import com.switchfully.parkshark.service.parkinglot.dto.ParkingLotSimplifiedDTO;
import com.switchfully.parkshark.service.parkinglot.dto.ReturnParkingLotDTO;
import com.switchfully.parkshark.domain.postalcode.PostalCodeRepository;
import com.switchfully.parkshark.service.division.DivisionMapper;
import com.switchfully.parkshark.service.parkinglot.ParkingLotService;
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
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ParkingLotControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    ParkingLotService parkingLotService;
    @Autowired
    PostalCodeRepository postalCodeRepository;
    @Autowired
    ContactPersonRepository contactPersonRepository;
    @Autowired
    DivisionRepository divisionRepository;
    DivisionMapper divisionMapper = new DivisionMapper();

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
    void createNewParkingLot_HappyPath() {
        String requestedBody = "{\"name\":\"nae\",\"category\":\"UNDERGROUND\",\"maxCapacity\":100,\"pricePerHour\":10,\"contactPersonId\":1,\"streetName\":\"street\",\"streetNumber\":\"5\",\"postalCode\":\"1111\",\"divisionId\":1}";
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
    void validateNameOfParkingLot() {
        NewParkingLotDTO newParkingLotDTO = new NewParkingLotDTO("", "ABOVE_GROUND", 100, 10, 1L, "street", "5", "1111", 1L);
        Assertions.assertThrows(IllegalArgumentException.class, () -> parkingLotService.createParkingLot(newParkingLotDTO));
    }

    @Test
    void validateCategoryOfParkingLot() {
        NewParkingLotDTO newParkingLotDTO = new NewParkingLotDTO("name", null, 100, 10, 1L, "street", "5", "1111", 1L);
        Assertions.assertThrows(IllegalArgumentException.class, () -> parkingLotService.createParkingLot(newParkingLotDTO));
    }

    @Test
    void validateCategoryRandomOfParkingLot() {
        NewParkingLotDTO newParkingLotDTO = new NewParkingLotDTO("name", "random", 100, 10, 1L, "street", "5", "1111", 1L);
        Assertions.assertThrows(NoSuchElementException.class, () -> parkingLotService.createParkingLot(newParkingLotDTO));
    }

    @Test
    void validateCapacityNotNegativeOfParkingLot() {
        NewParkingLotDTO newParkingLotDTO = new NewParkingLotDTO("name", "ABOVE_GROUND", -1, 10, 1L, "street", "5", "1111", 1L);
        Assertions.assertThrows(IllegalArgumentException.class, () -> parkingLotService.createParkingLot(newParkingLotDTO));
    }

    @Test
    void validatePricePerHourNotNegativeOfParkingLot() {
        NewParkingLotDTO newParkingLotDTO = new NewParkingLotDTO("name", "ABOVE_GROUND", 100, -10, 1L, "street", "5", "1111", 1L);
        Assertions.assertThrows(IllegalArgumentException.class, () -> parkingLotService.createParkingLot(newParkingLotDTO));
    }

    @Test
    void validateContactPersonExistInParkingLot() {
        NewParkingLotDTO newParkingLotDTO = new NewParkingLotDTO("name", "ABOVE_GROUND", 100, 10, 5016L, "street", "5", "1111", 1L);
        Assertions.assertThrows(IllegalArgumentException.class, () -> parkingLotService.createParkingLot(newParkingLotDTO));
    }

    @Test
    void validateStreetNameOfParkingLot() {
        NewParkingLotDTO newParkingLotDTO = new NewParkingLotDTO("name", "ABOVE_GROUND", 100, 10, 1L, "", "5", "1111", 1L);
        Assertions.assertThrows(IllegalArgumentException.class, () -> parkingLotService.createParkingLot(newParkingLotDTO));
    }

    @Test
    void validateStreetNumberOfParkingLot() {
        NewParkingLotDTO newParkingLotDTO = new NewParkingLotDTO("name", "ABOVE_GROUND", 100, 10, 1L, "street", null, "1111", 1L);
        Assertions.assertThrows(IllegalArgumentException.class, () -> parkingLotService.createParkingLot(newParkingLotDTO));
    }

    @Test
    void validatePostalCodeExistInParkingLot() {
        NewParkingLotDTO newParkingLotDTO = new NewParkingLotDTO("name", "ABOVE_GROUND", 100, 10, 1L, "street", "5", "6666", 1L);
        Assertions.assertThrows(IllegalArgumentException.class, () -> parkingLotService.createParkingLot(newParkingLotDTO));
    }

    @Test
    @DirtiesContext
    void getAllParkingLot_HappyPath() {
        NewParkingLotDTO parkingLotDTO = new NewParkingLotDTO("name", "ABOVE_GROUND", 100, 10, 1L, "street", "5", "9000", 1L);

        parkingLotService.createParkingLot(new NewParkingLotDTO("name", "ABOVE_GROUND", 100, 10, 1L, "street", "5", "9000", 1L));
        ParkingLotSimplifiedDTO[] parkingLotSimplifiedDTOS = RestAssured
                .given()
                .header("Authorization", "Bearer " + response)
                .baseUri("http://localhost")
                .port(port)
                .when()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .get("/parkinglots")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(ParkingLotSimplifiedDTO[].class);
        List<ParkingLotSimplifiedDTO> results = Arrays.stream(parkingLotSimplifiedDTOS).toList();
        ParkingLotSimplifiedDTO result = results.stream().findFirst().orElseThrow();
        assertThat(results.size()).isEqualTo(1);
        assertThat(result.getId()).isNotNull();
        assertThat(result.getName()).isEqualTo(parkingLotDTO.getName());
        assertThat(result.getCapacity()).isEqualTo(parkingLotDTO.getMaxCapacity());
        assertThat(result.getContactPersonEmail()).isNotNull();
    }

    @Test
    void getParkingLotById_HappyPath() {
        NewParkingLotDTO newParkingLotDTO = new NewParkingLotDTO("test", "underground", 100, 10.0, 1L, "streetName", "1", "1111", 1L);
        parkingLotService.createParkingLot(newParkingLotDTO);

        ReturnParkingLotDTO result = RestAssured
                .given()
                .header("Authorization", "Bearer " + response)
                .baseUri("http://localhost")
                .port(port)
                .when()
                .accept(ContentType.JSON)
                .get("/parkinglots/1")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(ReturnParkingLotDTO.class);

        assertThat(result.getName()).isEqualTo(newParkingLotDTO.getName());
        assertThat(result.getCategory()).isEqualTo(newParkingLotDTO.getCategory().toUpperCase());
        assertThat(result.getMaxCapacity()).isEqualTo(newParkingLotDTO.getMaxCapacity());
        assertThat(result.getPricePerHour()).isEqualTo(newParkingLotDTO.getPricePerHour());
        assertThat(result.getContactPersonId()).isEqualTo(newParkingLotDTO.getContactPersonId());
        assertThat(result.getAddress()).isNotNull();
        assertThat(result.getDivision()).isNotNull();
    }

    @Test
    void getParkingLotById_IdDoesNotExist() {
        RestAssured
                .given()
                .header("Authorization", "Bearer " + response)
                .baseUri("http://localhost")
                .port(port)
                .when()
                .accept(ContentType.JSON)
                .get("/parkinglots/9999999999")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", equalTo("Parking lot with id 9999999999 does not exist"));
    }
}