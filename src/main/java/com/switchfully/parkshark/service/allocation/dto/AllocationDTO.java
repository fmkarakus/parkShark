package com.switchfully.parkshark.service.allocation.dto;

public record AllocationDTO(
        Long id,
        Long memberId,
        String licencePlateNumber,
        Long parkingLotId,
        String startingTime
) {

}
