package com.switchfully.parkshark.divisionTests;

import com.switchfully.parkshark.domain.Division.CreateDivisionDTO;
import com.switchfully.parkshark.service.Division.DivisionService;
import com.switchfully.parkshark.service.Division.DivisionValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

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