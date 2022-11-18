package com.switchfully.parkshark.api;

import com.switchfully.parkshark.domain.allocation.AllocationStatus;
import com.switchfully.parkshark.domain.parkinglot.NewParkingLotDTO;
import com.switchfully.parkshark.domain.parkinglot.ParkingLot;
import com.switchfully.parkshark.service.allocation.AllocationService;
import com.switchfully.parkshark.service.allocation.DTO.AllocationDTO;
import com.switchfully.parkshark.service.allocation.DTO.StartAllocationDTO;
import com.switchfully.parkshark.service.allocation.DTO.StopAllocationDTO;
import com.switchfully.parkshark.service.member.MemberService;
import com.switchfully.parkshark.service.member.memberDTO.CreateMemberDTO;
import com.switchfully.parkshark.service.member.memberDTO.MemberDTO;
import com.switchfully.parkshark.service.parkinglot.ParkingLotService;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
class ParkingSpotAllocationControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ParkingLotService parkingLotService;

    @Autowired
    private AllocationService allocationService;

    private final static String URL = "https://keycloak.switchfully.com/auth/realms/parkShark-babyshark/protocol/openid-connect/token";
    private static String token;

    private static final CreateMemberDTO createMemberDTO = new CreateMemberDTO(
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

    private static final NewParkingLotDTO newParkingLotDTO = new NewParkingLotDTO(
            "testParkingLot",
            "UNDERGROUND",
            100,
            4,
            1L,
            "street",
            "4",
            "1111",
            1L
    );

    @BeforeAll
    static void setUp() {
        token = given()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("grant_type", "password")
                .formParam("username", "testMember")
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
    void createNewParkingSpotAllocation_HappyPath() {
        MemberDTO memberDTO = memberService.registerANewMember(createMemberDTO);
        parkingLotService.createParkingLot(newParkingLotDTO);
        StartAllocationDTO requestedBody = new StartAllocationDTO(memberDTO.id(), "123-abc", 1L);

        AllocationDTO result = given()
                .header("Authorization", "Bearer " + token)
                .baseUri("http://localhost")
                .port(port)
                .when()
                .accept(ContentType.JSON)
                .body(requestedBody)
                .contentType(ContentType.JSON)
                .post("/allocations")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(AllocationDTO.class);
        assertThat(result.id()).isNotNull();
        assertThat(result.parkingLotId()).isEqualTo(1L);
        assertThat(result.memberId()).isEqualTo(memberDTO.id());
        assertThat(result.licencePlateNumber()).isEqualTo("123-abc");
        assertThat(result.startingTime()).isNotNull();
        ParkingLot parkingLot = parkingLotService.findParkingLotId(1L);
        assertThat(parkingLot.getAvailableCapacity()).isEqualTo(parkingLot.getMaxCapacity() - 1);

    }

    @Test
    void createNewParkingSpotAllocation_whenParkingLotIdIsInvalid() {
        MemberDTO memberDTO = memberService.registerANewMember(createMemberDTO);
        StartAllocationDTO requestedBody = new StartAllocationDTO(memberDTO.id(), "123-abc", 500L);

        given()
                .header("Authorization", "Bearer " + token)
                .baseUri("http://localhost")
                .port(port)
                .when()
                .accept(ContentType.JSON)
                .body(requestedBody)
                .contentType(ContentType.JSON)
                .post("/allocations")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", equalTo("Parking lot with the id 500 does not exist."));
    }

    @Test
    void createNewParkingSpotAllocation_whenMemberIdIsInvalid() {
        parkingLotService.createParkingLot(newParkingLotDTO);
        StartAllocationDTO requestedBody = new StartAllocationDTO(10000L, "123-abc", 1L);
        given()
                .header("Authorization", "Bearer " + token)
                .baseUri("http://localhost")
                .port(port)
                .when()
                .accept(ContentType.JSON)
                .body(requestedBody)
                .contentType(ContentType.JSON)
                .post("/allocations")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", equalTo("Member id does not exist"));
    }

    @Test
    void createNewParkingSpotAllocation_whenLicensePlateIsNotRegistered() {
        MemberDTO memberDTO = memberService.registerANewMember(createMemberDTO);
        parkingLotService.createParkingLot(newParkingLotDTO);
        StartAllocationDTO requestedBody = new StartAllocationDTO(memberDTO.id(), "NotRegisteredPlate", 1L);

        given()
                .header("Authorization", "Bearer " + token)
                .baseUri("http://localhost")
                .port(port)
                .when()
                .accept(ContentType.JSON)
                .body(requestedBody)
                .contentType(ContentType.JSON)
                .post("/allocations")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", equalTo("The license plate NotRegisteredPlate does not registered to member with id 1"));
    }

    @Test
    void stopParkingAllocation_happyPath() {
        MemberDTO memberDTO = memberService.registerANewMember(createMemberDTO);
        parkingLotService.createParkingLot(newParkingLotDTO);
        AllocationDTO allocationDTO = allocationService.createAllocation(new StartAllocationDTO(memberDTO.id(), "123-abc", 1L));
        StopAllocationDTO result = given()
                .header("Authorization", "Bearer " + token)
                .baseUri("http://localhost")
                .port(port)
                .when()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .put("/allocations/" + allocationDTO.id())
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(StopAllocationDTO.class);
        assertThat(result.id()).isNotNull();
        assertThat(result.startingTime()).isNotNull();
        assertThat(result.stoppingTime()).isNotNull();
        assertThat(allocationService.getAllocationById(result.id()).getStatus()).isEqualTo(AllocationStatus.STOPPED);
    }

    @Test
    void stopParkingAllocation_whenAllocationIdIsInvalid() {
        MemberDTO memberDTO = memberService.registerANewMember(createMemberDTO);
        parkingLotService.createParkingLot(newParkingLotDTO);
        given()
                .header("Authorization", "Bearer " + token)
                .baseUri("http://localhost")
                .port(port)
                .when()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .put("/allocations/9999999999")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", equalTo("No allocation exists with the id: 9999999999"));
    }

    @Test
    void stopParkingAllocation_whenAllocationIsAlreadyStopped() {
        MemberDTO memberDTO = memberService.registerANewMember(createMemberDTO);
        parkingLotService.createParkingLot(newParkingLotDTO);
        AllocationDTO allocationDTO = allocationService.createAllocation(new StartAllocationDTO(memberDTO.id(), "123-abc", 1L));
        allocationService.stopAllocation(allocationDTO.id());
        given()
                .header("Authorization", "Bearer " + token)
                .baseUri("http://localhost")
                .port(port)
                .when()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .put("/allocations/" + allocationDTO.id())
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", equalTo("The allocation is already stopped"));
    }
}