package com.switchfully.parkshark.service.allocation;

import com.switchfully.parkshark.domain.member.Member;
import com.switchfully.parkshark.domain.member.MembershipLevel;
import com.switchfully.parkshark.domain.parkinglot.ParkingLot;
import com.switchfully.parkshark.service.allocation.dto.StartAllocationDTO;
import com.switchfully.parkshark.service.member.MemberService;
import com.switchfully.parkshark.service.parkinglot.ParkingLotService;
import org.springframework.stereotype.Component;

@Component
public class AllocationValidation {
    private final MemberService memberService;
    private final ParkingLotService parkingLotService;

    public AllocationValidation(MemberService memberService, ParkingLotService parkingLotService) {
        this.memberService = memberService;
        this.parkingLotService = parkingLotService;
    }

    public void validateAllocation(StartAllocationDTO startAllocationDTO) {
        assertLicensePlateIsValid(startAllocationDTO);
        assertParkingLotHasCapacity(startAllocationDTO);
    }

    private void assertParkingLotHasCapacity(StartAllocationDTO startAllocationDTO) {
        ParkingLot parkingLot=parkingLotService.findParkingLotId(startAllocationDTO.parkingLotId());
        if(parkingLot.getAvailableCapacity()<1) throw new IllegalArgumentException("There is no available place in the parking lot.");
    }

    private void assertLicensePlateIsValid(StartAllocationDTO startAllocationDTO) {
        Member member=memberService.findMemberById(startAllocationDTO.memberId());
        if (!member.getMemberShipLevel().equals(MembershipLevel.GOLD) && !member.getLicensePlate().getLicensePlateNumber().equals(startAllocationDTO.licencePlateNumber())) {
            throw new IllegalArgumentException(String.format("The license plate %s does not registered to member with id %d", startAllocationDTO.licencePlateNumber(), startAllocationDTO.memberId()));
        }
    }
}
