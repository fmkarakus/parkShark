package com.switchfully.parkshark.api;

import com.switchfully.parkshark.service.allocation.AllocationService;
import com.switchfully.parkshark.service.allocation.dto.AllocationDTO;
import com.switchfully.parkshark.service.allocation.dto.StartAllocationDTO;
import com.switchfully.parkshark.service.allocation.dto.StopAllocationDTO;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/allocations")
public class ParkingSpotAllocationController {

    private final AllocationService allocationService;
    private final Logger logger = LoggerFactory.getLogger(ParkingSpotAllocationController.class);

    public ParkingSpotAllocationController(AllocationService allocationService) {
        this.allocationService = allocationService;
    }


    @PutMapping(path = "/{allocationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('STOP_PARKING')")
    public StopAllocationDTO stopParking(@PathVariable Long allocationId, KeycloakAuthenticationToken authentication) {
        String loggedInMember = authentication.getPrincipal().toString();
        logger.info("stopping an allocation");
        return allocationService.stopAllocation(allocationId, loggedInMember);
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('START_PARKING')")
    public AllocationDTO startParking(@RequestBody StartAllocationDTO startAllocationDTO, KeycloakAuthenticationToken authentication) {
        String loggedInMember = authentication.getPrincipal().toString();
        logger.info("creating new allocation");
        return allocationService.createAllocation(startAllocationDTO, loggedInMember);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('GET_ALL_ALLOCATIONS')")
    public List<AllocationDTO> getAllAllocations() {
        logger.info("getting requested allocations");
        return allocationService.getAllAllocations();
    }

    @GetMapping(params = {"limit", "status", "order"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('GET_ALL_ALLOCATIONS')")
    public List<AllocationDTO> getAllAllocationsFiltered(@RequestParam(defaultValue = "0") int limit, @RequestParam(defaultValue = "") String status, @RequestParam(defaultValue = "ASC") String order) {
        logger.info("getting requested allocations Filtered");
        return allocationService.getAllAllocationsFiltered(limit, status, order);
    }
}
