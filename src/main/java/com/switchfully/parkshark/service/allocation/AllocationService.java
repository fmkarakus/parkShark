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
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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

    public List<AllocationDTO> getAllAllocations() {
        return allocationMapper.mapAllocationToAllocationDTO(allocationRepository.findAll());
    }
    public List<AllocationDTO> getAllAllocationsFiltered(int limit, String status, String order) {

        List<Allocation> allocationList = allocationRepository.findAll();
        int resolvedLimit =setLimit(limit);

        if (order.equals("DESC")){
           Collections.reverse(allocationList);
        }
        if (status.isEmpty()){
            return allocationMapper.mapAllocationToAllocationDTO(allocationList.stream()
                    .limit(resolvedLimit)
                    .toList());
        }


        return allocationMapper.mapAllocationToAllocationDTO(allocationList.stream()
                .filter(allocation -> allocation.getStatus().name().equals(status))
                .limit(resolvedLimit)
                .toList()
        );
    }
    private int setLimit(int limit){
        if(limit<=0){
            return allocationRepository.findAll().size();
        }
        return limit;
    }


    public StopAllocationDTO stopAllocation(long allocationId, String userName) {
        Allocation allocation = getAllocationById(allocationId);
        assertTheAllocationIsActive(allocation);
        assertMember(allocation.getMember().getEmail(),userName);
        allocation.setStoppingTime(LocalDateTime.now());
        allocation.setStatus(AllocationStatus.STOPPED);
        allocation.getParkingLot().increaseAvailableCapacity();
        return allocationMapper.mapAllocationToStopAllocationDTO(allocation);
    }

    private void assertMember(String eMail, String userName) {
        if(!eMail.equals(userName)) throw new IllegalArgumentException("You have no authority to close this allocation.");
    }

    public Allocation getAllocationById(long allocationId) {
        return allocationRepository.findById(allocationId).orElseThrow(() -> new IllegalArgumentException("No allocation exists with the id: " + allocationId));
    }


    private void assertTheAllocationIsActive(Allocation allocation) {
        if (!allocation.getStatus().equals(AllocationStatus.ACTIVE))
            throw new IllegalArgumentException("The allocation is already stopped");
    }

}
