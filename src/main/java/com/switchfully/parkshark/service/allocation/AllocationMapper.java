package com.switchfully.parkshark.service.allocation;

import com.switchfully.parkshark.domain.allocation.Allocation;
import com.switchfully.parkshark.service.allocation.DTO.AllocationDTO;
import com.switchfully.parkshark.service.allocation.DTO.StartAllocationDTO;
import com.switchfully.parkshark.service.member.MemberService;
import com.switchfully.parkshark.service.parkinglot.ParkingLotService;
import org.springframework.stereotype.Component;

@Component
public class AllocationMapper {
    private final MemberService memberService;
    private final ParkingLotService parkingLotService;

    public AllocationMapper(MemberService memberService, ParkingLotService parkingLotService) {
        this.memberService = memberService;
        this.parkingLotService = parkingLotService;
    }

    public Allocation mapStartAllocationDTOToAllocation(StartAllocationDTO startAllocationDTO) {
        return new Allocation(
                memberService.findMemberById(startAllocationDTO.memberId()),
                startAllocationDTO.licencePlateNumber(),
                parkingLotService.findParkingLotId(startAllocationDTO.parkingLotId())
        );
    }

    public AllocationDTO mapAllocationToAllocationDTO(Allocation allocation){
        return new AllocationDTO(
                allocation.getId(),
                allocation.getMember().getId(),
                allocation.getLicensePlateNumber(),
                allocation.getParkingLot().getId(),
                allocation.getStartingTime()
        );
    }
}
