package com.switchfully.parkshark.service.allocation;

import com.switchfully.parkshark.domain.allocation.Allocation;
import com.switchfully.parkshark.service.allocation.dto.AllocationDTO;
import com.switchfully.parkshark.service.allocation.dto.StartAllocationDTO;
import com.switchfully.parkshark.service.allocation.dto.StopAllocationDTO;
import com.switchfully.parkshark.service.member.MemberService;
import com.switchfully.parkshark.service.parkinglot.ParkingLotService;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class AllocationMapper {
    private final MemberService memberService;
    private final ParkingLotService parkingLotService;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
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
                allocation.getStartingTime().format(dateTimeFormatter)
        );
    }


    public List<AllocationDTO> mapAllocationToAllocationDTO(List<Allocation> allocationList){
        return allocationList.stream().map(this::mapAllocationToAllocationDTO).toList();
    }

    public StopAllocationDTO mapAllocationToStopAllocationDTO(Allocation allocation){
        return new StopAllocationDTO(
                allocation.getId(),
                allocation.getMember().getId(),
                allocation.getLicensePlateNumber(),
                allocation.getParkingLot().getId(),
                allocation.getStartingTime().format(dateTimeFormatter),
                allocation.getStoppingTime().format(dateTimeFormatter)
        );
    }
}
