package com.switchfully.parkshark.service.division;

import com.switchfully.parkshark.service.division.dto.CreateDivisionDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DivisionValidatorTest {

    @Autowired
    DivisionService divisionService;

    @Test
    void givenNoName_throwException() {
        CreateDivisionDTO createDivisionDTO = new CreateDivisionDTO(
                "",
                "Qpark",
                "director01"
        );
        Assertions.assertThrows(IllegalArgumentException.class, () -> divisionService.createDivision(createDivisionDTO));
    }

    @Test
    void givenNoOriginalName_throwException() {
        CreateDivisionDTO createDivisionDTO = new CreateDivisionDTO(
                "division1",
                "",
                "director01"
        );
        Assertions.assertThrows(IllegalArgumentException.class, () -> divisionService.createDivision(createDivisionDTO));
    }

    @Test
    void givenNoDirector_throwException() {
        CreateDivisionDTO createDivisionDTO = new CreateDivisionDTO(
                "division1",
                "Qpark",
                ""
        );
        Assertions.assertThrows(IllegalArgumentException.class, () -> divisionService.createDivision(createDivisionDTO));
    }

}