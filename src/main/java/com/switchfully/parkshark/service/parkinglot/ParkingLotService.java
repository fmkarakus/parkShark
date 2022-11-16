package com.switchfully.parkshark.service.parkinglot;

import com.switchfully.parkshark.domain.parkinglot.NewParkingLotDTO;
import com.switchfully.parkshark.domain.parkinglot.ParkingLot;
import com.switchfully.parkshark.domain.parkinglot.ParkingLotRepository;
import com.switchfully.parkshark.service.parkinglot.ParkingLotValidation;
import com.switchfully.parkshark.domain.postalcode.PostalCodeValidator;
import com.switchfully.parkshark.service.parkinglot.ParkingLotMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Transactional
public class ParkingLotService {

    ParkingLotRepository parkingLotRepository;
    ParkingLotValidation parkingLotValidation;
    PostalCodeValidator postalCodeValidator;
    ParkingLotMapper parkingLotMapper;

    public ParkingLotService(ParkingLotRepository parkingLotRepository, ParkingLotValidation parkingLotValidation, PostalCodeValidator postalCodeValidator, ParkingLotMapper parkingLotMapper) {
        this.parkingLotRepository = parkingLotRepository;
        this.parkingLotValidation = parkingLotValidation;
        this.postalCodeValidator = postalCodeValidator;
        this.parkingLotMapper = parkingLotMapper;
    }

    public void createParkingLot(NewParkingLotDTO newParkingLotDTO) {
        postalCodeValidator.isPostalCodeInDatabase(newParkingLotDTO.getPostalCode());
        ParkingLot parkingLot = parkingLotMapper.newParkingLotDTOToParkingLot(newParkingLotDTO);
        parkingLotRepository.save(parkingLot);

    }
}
