package com.switchfully.parkshark.api;

import com.switchfully.parkshark.domain.Division.CreateDivisionDTO;
import com.switchfully.parkshark.service.Division.DivisionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/divisions")
public class DivisionController {

    DivisionService divisionService;
    private final Logger logger = LoggerFactory.getLogger(DivisionController.class);

    public DivisionController(DivisionService divisionService) {
        this.divisionService = divisionService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createDivision(@RequestBody CreateDivisionDTO createDivisionDTO) {
        //TODO add security
        logger.info("Adding new division to database");
        divisionService.createDivision(createDivisionDTO);
    }
}
