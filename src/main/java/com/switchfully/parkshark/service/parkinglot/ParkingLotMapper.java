package com.switchfully.parkshark.service.parkinglot;

import com.switchfully.parkshark.domain.contactperson.ContactPerson;
import com.switchfully.parkshark.domain.contactperson.ContactPersonRepository;
import com.switchfully.parkshark.domain.division.Division;
import com.switchfully.parkshark.domain.division.DivisionRepository;
import com.switchfully.parkshark.domain.member.Address;
import com.switchfully.parkshark.domain.parkinglot.*;
import com.switchfully.parkshark.domain.postalcode.PostalCode;
import com.switchfully.parkshark.domain.postalcode.PostalCodeRepository;
import com.switchfully.parkshark.service.division.DTO.DivisionDTO;
import org.springframework.stereotype.Component;

@Component
public class ParkingLotMapper {

    PostalCodeRepository postalCodeRepository;
    ContactPersonRepository contactPersonRepository;
    DivisionRepository divisionRepository;

    public ParkingLotMapper(PostalCodeRepository postalCodeRepository, ContactPersonRepository contactPersonRepository, DivisionRepository divisionRepository) {
        this.postalCodeRepository = postalCodeRepository;
        this.contactPersonRepository = contactPersonRepository;
        this.divisionRepository = divisionRepository;
    }

    public ParkingLot newParkingLotDTOToParkingLot(NewParkingLotDTO newParkingLotDTO) {
        PostalCode newPostalCode = postalCodeRepository.getReferenceById(newParkingLotDTO.getPostalCode());
        ContactPerson newContactPerson = contactPersonRepository.getReferenceById(newParkingLotDTO.getContactPersonId());
        Division division = divisionRepository.getReferenceById(newParkingLotDTO.getDivisionId());
        Address newAddress = new Address(newParkingLotDTO.getStreetName(), newParkingLotDTO.getStreetNumber(), newPostalCode);
        Category category = Category.findCategoryByName(newParkingLotDTO.getCategory());
        return new ParkingLot(newParkingLotDTO.getName(), category, newParkingLotDTO.getMaxCapacity(), newParkingLotDTO.getPricePerHour(), newContactPerson, newAddress, division);
    }

    public ParkingLotSimplifiedDTO parkingLotToParkingLotSimplifiedDTO(ParkingLot parkingLot) {
        return new ParkingLotSimplifiedDTO(parkingLot.getId(), parkingLot.getName(), parkingLot.getMaxCapacity(), parkingLot.getContactPerson().getEmail(), parkingLot.getContactPerson().getTelephoneNumber());
    }

    public ReturnParkingLotDTO mapParkingLotToReturnParkingLotDTO(ParkingLot parkingLot, DivisionDTO divisionDTO) {
        return new ReturnParkingLotDTO(parkingLot.getId(), parkingLot.getName(), parkingLot.getCategory().toString(), parkingLot.getMaxCapacity(), parkingLot.getAvailableCapacity(), parkingLot.getPricePerHour(), parkingLot.getContactPersonId(), parkingLot.getAddress().toString(), divisionDTO);
    }
}
