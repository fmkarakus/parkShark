package com.switchfully.parkshark.service.allocation.DTO;

public record StopAllocationDTO(
        Long id,
        Long memberId,
        String licencePlateNumber,
        Long parkingLotId,
        String startingTime,
        String stoppingTime
) {
}
