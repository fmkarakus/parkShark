package com.switchfully.parkshark.service.allocation;

import com.switchfully.parkshark.domain.allocation.Allocation;
import com.switchfully.parkshark.domain.allocation.AllocationRepository;
import com.switchfully.parkshark.service.allocation.DTO.AllocationDTO;
import com.switchfully.parkshark.service.allocation.DTO.StartAllocationDTO;
import com.switchfully.parkshark.service.parkinglot.ParkingLotService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

}
