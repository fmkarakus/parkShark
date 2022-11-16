package com.switchfully.parkshark.service.parkinglot;

import com.switchfully.parkshark.domain.parkinglot.ParkingLotRepository;
import org.springframework.stereotype.Service;

@Service
public class ParkingLotValidation {
    private final ParkingLotRepository parkingLotRepository;

    public ParkingLotValidation(ParkingLotRepository parkingLotRepository) {
        this.parkingLotRepository = parkingLotRepository;
    }


}
