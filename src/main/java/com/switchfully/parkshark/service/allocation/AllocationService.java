package com.switchfully.parkshark.service.allocation;

import com.switchfully.parkshark.domain.allocation.Allocation;
import com.switchfully.parkshark.domain.allocation.AllocationRepository;
import com.switchfully.parkshark.service.allocation.DTO.AllocationDTO;
import com.switchfully.parkshark.service.allocation.DTO.StartAllocationDTO;
import com.switchfully.parkshark.service.parkinglot.ParkingLotService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
}
