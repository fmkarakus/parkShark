package com.switchfully.parkshark.service.parkinglot;

import com.switchfully.parkshark.domain.contactperson.ContactPersonRepository;
import com.switchfully.parkshark.domain.parkinglot.Category;
import com.switchfully.parkshark.domain.parkinglot.NewParkingLotDTO;
import com.switchfully.parkshark.domain.parkinglot.ParkingLotRepository;
import com.switchfully.parkshark.domain.postalcode.PostalCodeRepository;
import com.switchfully.parkshark.service.exceptions.MissingArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ParkingLotValidation {
    private final ParkingLotRepository parkingLotRepository;
    private final PostalCodeRepository postalCodeRepository;
    private final ContactPersonRepository contactPersonRepository;
    private final Logger logger = LoggerFactory.getLogger(ParkingLotValidation.class);


    public ParkingLotValidation(PostalCodeRepository postalCodeRepository, ParkingLotRepository parkingLotRepository, ContactPersonRepository contactPersonRepository) {
        this.postalCodeRepository = postalCodeRepository;
        this.parkingLotRepository = parkingLotRepository;
        this.contactPersonRepository = contactPersonRepository;
    }



    public void checkRequiredFields(NewParkingLotDTO newParkingLotDTO) {
        if (newParkingLotDTO.getName() == null || newParkingLotDTO.getName().equals("")) {
            logger.error("No Parking lot name provided");
            throw new MissingArgumentException("Parking lot name not provided");
        }
        if (newParkingLotDTO.getCategory() == null) {
            logger.error("No Category name provided");
            throw new MissingArgumentException("Category name not provided");
        }

        if (newParkingLotDTO.getMaxCapacity() < 0 ) {
            logger.error("Capacity can't be negative");
            throw new MissingArgumentException("Capacity can't be negative");
        }
        if (newParkingLotDTO.getPricePerHour() < 0 ) {
            logger.error("Price per hour can't be negative");
            throw new MissingArgumentException("Price per hour can't be negative");
        }
        if (!contactPersonRepository.existsById(newParkingLotDTO.getContactPersonId())){
            logger.error("This contact person doesn't exist");
            throw new IllegalArgumentException("This contact person doesn't exist");
        }
        if (newParkingLotDTO.getStreetName() == null || newParkingLotDTO.getStreetName().equals("")) {
            logger.error("Street name not provided");
            throw new MissingArgumentException("Street name not provided");
        }
        if (newParkingLotDTO.getStreetNumber() == null || newParkingLotDTO.getStreetNumber().equals("")) {
            logger.error("Street number not provided");
            throw new MissingArgumentException("Street number not provided");
        }
        if (!postalCodeRepository.existsById(newParkingLotDTO.getPostalCode())){
            logger.error("This Postal Code  doesn't exist");
            throw new IllegalArgumentException("This Postal Code doesn't exist");
        }

    }

}
