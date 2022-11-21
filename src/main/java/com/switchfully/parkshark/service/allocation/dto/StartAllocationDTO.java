package com.switchfully.parkshark.service.allocation.dto;

public record StartAllocationDTO(
        Long memberId,
        String licencePlateNumber,
        Long parkingLotId
) {
}
