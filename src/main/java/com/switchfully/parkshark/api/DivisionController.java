package com.switchfully.parkshark.api;

import com.switchfully.parkshark.service.division.dto.CreateDivisionDTO;
import com.switchfully.parkshark.service.division.dto.DivisionDTO;
import com.switchfully.parkshark.service.division.dto.SubdivisionDTO;
import com.switchfully.parkshark.service.division.DivisionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PreAuthorize("hasAuthority('CREATE_DIVISION')")
    public void createDivision(@RequestBody CreateDivisionDTO createDivisionDTO) {
        logger.info("Adding new division to database");
        divisionService.createDivision(createDivisionDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('GET_ALL_DIVISIONS')")
    public List<DivisionDTO> getAllDivisions() {
        logger.info("Getting all divisions");
        return divisionService.getAllDivisions();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(params = {"divisionId"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('GET_A_DIVISION_BY_ID')")
    public DivisionDTO getADivision(@RequestParam(required = false) Long divisionId) {
        logger.info("Getting division " + divisionId);
        return divisionService.getADivisionById(divisionId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path="{parentId}",consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('CREATE_SUBDIVISION')")
    public SubdivisionDTO createSubdivision(@PathVariable long parentId, @RequestBody CreateDivisionDTO createDivisionDTO) {
        logger.info("Adding new subdivision to database");
        return divisionService.createSubdivision(parentId,createDivisionDTO);
    }
}
