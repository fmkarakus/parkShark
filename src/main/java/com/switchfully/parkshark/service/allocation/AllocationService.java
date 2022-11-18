package com.switchfully.parkshark.service.allocation;

import com.switchfully.parkshark.domain.allocation.Allocation;
import com.switchfully.parkshark.domain.allocation.AllocationRepository;
import com.switchfully.parkshark.domain.allocation.AllocationStatus;
import com.switchfully.parkshark.service.allocation.DTO.AllocationDTO;
import com.switchfully.parkshark.service.allocation.DTO.StartAllocationDTO;
import com.switchfully.parkshark.service.allocation.DTO.StopAllocationDTO;
import com.switchfully.parkshark.service.parkinglot.ParkingLotService;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.IDToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.time.LocalDateTime;

@Service
@Transactional
public class AllocationService {

    private final AllocationValidation allocationValidation;
    private final AllocationMapper allocationMapper;
    private final AllocationRepository allocationRepository;

    public AllocationService(AllocationValidation allocationValidation, AllocationMapper allocationMapper, AllocationRepository allocationRepository) {
        this.allocationValidation = allocationValidation;
        this.allocationMapper = allocationMapper;
        this.allocationRepository = allocationRepository;
    }

    public AllocationDTO createAllocation(StartAllocationDTO startAllocationDTO) {
        allocationValidation.validateAllocation(startAllocationDTO);
        Allocation allocation = allocationRepository.save(allocationMapper.mapStartAllocationDTOToAllocation(startAllocationDTO));
        allocation.getParkingLot().decreaseAvailableCapacity();
        return allocationMapper.mapAllocationToAllocationDTO(allocation);
    }

    public StopAllocationDTO stopAllocation(long allocationId, String userName) {
        Allocation allocation = getAllocationById(allocationId);
        assertTheAllocationIsActive(allocation);
        assertMember(allocation,userName);
        allocation.setStoppingTime(LocalDateTime.now());
        allocation.setStatus(AllocationStatus.STOPPED);
        allocation.getParkingLot().increaseAvailableCapacity();
        return allocationMapper.mapAllocationToStopAllocationDTO(allocation);
    }

    private void assertMember(Allocation allocation, String userName) {
        if(!allocation.getMember().getEmail().equals(userName)) throw new IllegalArgumentException("You have no authority to close this allocation.");
    }

    public Allocation getAllocationById(long allocationId) {
        return allocationRepository.findById(allocationId).orElseThrow(() -> new IllegalArgumentException("No allocation exists with the id: " + allocationId));
    }


    private void assertTheAllocationIsActive(Allocation allocation) {
        if (!allocation.getStatus().equals(AllocationStatus.ACTIVE))
            throw new IllegalArgumentException("The allocation is already stopped");
    }

}
