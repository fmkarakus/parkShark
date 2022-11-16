package com.switchfully.parkshark.service.parkinglot;

import com.switchfully.parkshark.domain.parkinglot.NewParkingLotDTO;
import com.switchfully.parkshark.domain.parkinglot.ParkingLot;
import org.springframework.stereotype.Component;

@Component
public class ParkingLotMapper {


    public ParkingLot newParkingLotDTOToParkingLot(NewParkingLotDTO newParkingLotDTO) {
        return new ParkingLot(newParkingLotDTO.getName(),newParkingLotDTO.getCategory(),newParkingLotDTO.getMaxCapacity(), newParkingLotDTO.getPricePerHour(), newParkingLotDTO.getContactPerson(),newParkingLotDTO.getStreetName(),newParkingLotDTO.getStreetNumber(), newParkingLotDTO.getPostalCode());
    }
}
