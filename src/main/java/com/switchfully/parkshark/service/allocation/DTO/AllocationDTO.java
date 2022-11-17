package com.switchfully.parkshark.service.allocation.DTO;

import java.time.LocalDateTime;

public record AllocationDTO(
        Long id,
        Long memberId,
        String licencePlateNumber,
        Long parkingLotId,
        LocalDateTime startingTime
) {

}
