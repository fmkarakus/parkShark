package com.switchfully.parkshark.api;

import com.switchfully.parkshark.service.member.memberDTO.CreateMemberDTO;
import com.switchfully.parkshark.service.member.memberDTO.MemberDTO;
import com.switchfully.parkshark.service.member.MemberService;
import com.switchfully.parkshark.service.member.memberDTO.SimplifiedMemberDTO;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.yaml.snakeyaml.scanner.ScannerImpl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class MemberControllerIntegrationTest {
    public static final String BASE_URI = "http://localhost";
    @LocalServerPort
    private int port;

    private final static String URL = "https://keycloak.switchfully.com/auth/realms/parkShark-babyshark/protocol/openid-connect/token";
    private static String response;

    @Autowired
    private MemberService memberService;

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
    void addNewMember_happyPath() {
        CreateMemberDTO createMemberDTO = new CreateMemberDTO(
                "first",
                "last",
                "streetname",
                "1",
                "1111",
                "city",
                "012 34 56 78",
                "test@email.be",
                "password",
                "123-abc",
                "B",
                "SILVER"
        );

        MemberDTO result = RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(port)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body(createMemberDTO)
                .when()
                .post("members")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(MemberDTO.class);

        assertThat(result.id()).isNotNull();
        assertThat(result.firstName()).isEqualTo(createMemberDTO.firstName());
        assertThat(result.lastName()).isEqualTo(createMemberDTO.lastName());
        assertThat(result.emailAddress()).isEqualTo(createMemberDTO.emailAddress());
        assertThat(result.telephoneNumber()).isEqualTo(createMemberDTO.telephoneNumber());
        assertThat(result.address()).isEqualTo(String.format("%s %s, %s %s", createMemberDTO.streetName(), createMemberDTO.streetNumber(), createMemberDTO.postalCode(), createMemberDTO.label()));
        assertThat(result.licensePlate()).isEqualTo(String.format("%s %s", createMemberDTO.licensePlateCountry(), createMemberDTO.licensePlateNumber()));
        assertThat(result.membershipLevel()).isEqualTo(createMemberDTO.memberShipLevel());
    }

    @Test
    void addNewMember_givenInvalidEmail() {
        CreateMemberDTO createMemberDTO = new CreateMemberDTO(
                "first",
                "last",
                "streetname",
                "1",
                "1111",
                "city",
                "012 34 56 78",
                "testemail.be",
                "password",
                "123-abc",
                "B",
                "BRONZE"
        );

        RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(port)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body(createMemberDTO)
                .when()
                .post("members")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void addNewMember_givenWithMembershipNull() {
        CreateMemberDTO createMemberDTO = new CreateMemberDTO(
                "first",
                "last",
                "streetname",
                "1",
                "1111",
                "city",
                "012 34 56 78",
                "test@email.be",
                "password",
                "123-abc",
                "B",
                null
        );

        MemberDTO result = RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(port)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body(createMemberDTO)
                .when()
                .post("members")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(MemberDTO.class);

        assertThat(result.id()).isNotNull();
        assertThat(result.firstName()).isEqualTo(createMemberDTO.firstName());
        assertThat(result.lastName()).isEqualTo(createMemberDTO.lastName());
        assertThat(result.emailAddress()).isEqualTo(createMemberDTO.emailAddress());
        assertThat(result.telephoneNumber()).isEqualTo(createMemberDTO.telephoneNumber());
        assertThat(result.address()).isEqualTo(String.format("%s %s, %s %s", createMemberDTO.streetName(), createMemberDTO.streetNumber(), createMemberDTO.postalCode(), createMemberDTO.label()));
        assertThat(result.licensePlate()).isEqualTo(String.format("%s %s", createMemberDTO.licensePlateCountry(), createMemberDTO.licensePlateNumber()));
        assertThat(result.membershipLevel()).isEqualTo("BRONZE");
    }

    @Test
    void addNewMember_givenEmailISNull() {
        CreateMemberDTO createMemberDTO = new CreateMemberDTO(
                "first",
                "last",
                "streetname",
                "1",
                "1111",
                "city",
                "012 34 56 78",
                null,
                "password",
                "123-abc",
                "B",
                "BRONZE"
        );

        RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(port)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body(createMemberDTO)
                .when()
                .post("members")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void addNewMember_givenNullFields() {
        CreateMemberDTO createMemberDTO = new CreateMemberDTO(
                "first",
                "last",
                "streetName",
                "1",
                "1111",
                "city",
                "012 34 56 78",
                "test@email.be",
                "password",
                "123-abc",
                null,
                "BRONZE"
        );

        RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(port)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body(createMemberDTO)
                .when()
                .post("members")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void addNewMember_givenEmptyFields() {
        CreateMemberDTO createMemberDTO = new CreateMemberDTO(
                "first",
                "last",
                "streetName",
                "1",
                "1111",
                "city",
                "012 34 56 78",
                "test@email.be",
                "password",
                "123-abc",
                " ",
                "BRONZE"
        );

        RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(port)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body(createMemberDTO)
                .when()
                .post("members")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void addNewMember_givenInvalidMembership() {
        CreateMemberDTO createMemberDTO = new CreateMemberDTO(
                "first",
                "last",
                "streetname",
                "1",
                "1111",
                "city",
                "012 34 56 78",
                "test@email.com",
                "password",
                "123-abc",
                "B",
                "invalid"
        );

        RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(port)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body(createMemberDTO)
                .when()
                .post("members")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void getAllMembers_happyPath() {
        CreateMemberDTO createMemberDTO = new CreateMemberDTO(
                "null",
                "Van Eyken",
                "first Street",
                "1",
                "1111",
                "city",
                "012 34 56 78",
                "test@test.be",
                "password",
                "M-AKS-417",
                "B",
                "BRONZE");
        memberService.registerANewMember(createMemberDTO);
        SimplifiedMemberDTO simplifiedMemberDTO = new SimplifiedMemberDTO(
                1,
                "null Van Eyken",
                "first Street 1, 1111 city",
                "012 34 56 78",
                "test@test.be",
                "M-AKS-417",
                LocalDate.of(2022, 11, 17)
        );

        SimplifiedMemberDTO[] results = RestAssured
                .given()
                .header("Authorization", "Bearer " + response)
                .baseUri("http://localhost")
                .baseUri(BASE_URI)
                .port(port)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("members")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(SimplifiedMemberDTO[].class);

        SimplifiedMemberDTO result = Arrays.stream(results).findFirst().orElseThrow();
        assertThat(result.id()).isNotNull();
        assertThat(result.name()).isEqualTo(simplifiedMemberDTO.name());
        assertThat(result.emailAddress()).isEqualTo(simplifiedMemberDTO.emailAddress());
        assertThat(result.telephoneNumber()).isEqualTo(simplifiedMemberDTO.telephoneNumber());
        assertThat(result.address()).isEqualTo(simplifiedMemberDTO.address());
        assertThat(result.licensePlateNumber()).isEqualTo(simplifiedMemberDTO.licensePlateNumber());
        assertThat(result.registrationDate()).isEqualTo(simplifiedMemberDTO.registrationDate());

    }
}