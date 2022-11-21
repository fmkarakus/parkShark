package com.switchfully.parkshark.service.allocation.dto;

public record StopAllocationDTO(
        Long id,
        Long memberId,
        String licencePlateNumber,
        Long parkingLotId,
        String startingTime,
        String stoppingTime
) {
}
