package com.switchfully.parkshark.api;

import com.switchfully.parkshark.service.member.CreateMemberDTO;
import com.switchfully.parkshark.service.member.MemberDTO;
import com.switchfully.parkshark.service.member.MemberService;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class MemberControllerIntegrationTest {
    public static final String BASE_URI = "http://localhost";
    @LocalServerPort
    private int port;

    @Autowired
    private MemberService memberService;

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
                "new@test.be",
                "password",
                "123-abc",
                "B"
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
    }
}