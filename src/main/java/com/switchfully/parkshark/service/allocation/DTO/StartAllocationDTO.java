package com.switchfully.parkshark.service.allocation.DTO;

public record StartAllocationDTO(
        Long memberId,
        String licencePlateNumber,
        Long parkingLotId
) {
}
