package com.switchfully.parkshark.api;

import com.switchfully.parkshark.service.allocation.AllocationService;
import com.switchfully.parkshark.service.allocation.DTO.AllocationDTO;
import com.switchfully.parkshark.service.allocation.DTO.StartAllocationDTO;
import com.switchfully.parkshark.service.allocation.DTO.StopAllocationDTO;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;


@RestController
@RequestMapping(path = "/allocations")
public class ParkingSpotAllocationController {

    @Context
    SecurityContext sc;
    private final AllocationService allocationService;

    public ParkingSpotAllocationController(AllocationService allocationService) {
        this.allocationService = allocationService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('START_PARKING')")
    public AllocationDTO startParking(@RequestBody StartAllocationDTO startAllocationDTO) {
        return allocationService.createAllocation(startAllocationDTO);
    }

    @PutMapping(path = "/{allocationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('STOP_PARKING')")
    public StopAllocationDTO stopParking(@PathVariable Long allocationId) {
        return allocationService.stopAllocation(allocationId);
    }

}
