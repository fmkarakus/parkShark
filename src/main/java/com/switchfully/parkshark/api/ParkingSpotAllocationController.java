package com.switchfully.parkshark.api;

import com.switchfully.parkshark.service.allocation.AllocationService;
import com.switchfully.parkshark.service.allocation.DTO.AllocationDTO;
import com.switchfully.parkshark.service.allocation.DTO.StartAllocationDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/allocations")
public class ParkingSpotAllocationController {
        private final AllocationService allocationService;

        public ParkingSpotAllocationController(AllocationService allocationService) {
                this.allocationService = allocationService;
        }

        @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces =MediaType.APPLICATION_JSON_VALUE)
        @ResponseStatus(HttpStatus.CREATED)
        @PreAuthorize("hasAuthority('START_PARKING')")
        public AllocationDTO startParking(@RequestBody StartAllocationDTO startAllocationDTO) {
               return allocationService.createAllocation(startAllocationDTO);
        }
}
