package com.switchfully.parkshark.service.parkinglot;

import com.switchfully.parkshark.domain.contactperson.ContactPerson;
import com.switchfully.parkshark.domain.contactperson.ContactPersonRepository;
import com.switchfully.parkshark.domain.member.Address;
import com.switchfully.parkshark.domain.postalcode.PostalCode;
import com.switchfully.parkshark.domain.parkinglot.NewParkingLotDTO;
import com.switchfully.parkshark.domain.parkinglot.ParkingLot;
import com.switchfully.parkshark.domain.postalcode.PostalCodeRepository;
import org.springframework.stereotype.Component;

@Component
public class ParkingLotMapper {

    PostalCodeRepository postalCodeRepository;
    ContactPersonRepository contactPersonRepository;

    public ParkingLotMapper(PostalCodeRepository postalCodeRepository, ContactPersonRepository contactPersonRepository) {
        this.postalCodeRepository = postalCodeRepository;
        this.contactPersonRepository = contactPersonRepository;
    }

    public ParkingLot newParkingLotDTOToParkingLot(NewParkingLotDTO newParkingLotDTO) {
        PostalCode newPostalCode = postalCodeRepository.getReferenceById(newParkingLotDTO.getPostalCode());
        ContactPerson newContactPerson = contactPersonRepository.getReferenceById(newParkingLotDTO.getContactPersonId());
        Address newAddress = new Address(newParkingLotDTO.getStreetName(), newParkingLotDTO.getStreetNumber(),newPostalCode);
        return new ParkingLot(newParkingLotDTO.getName(),newParkingLotDTO.getCategory(),newParkingLotDTO.getMaxCapacity(), newParkingLotDTO.getPricePerHour(), newContactPerson,newAddress);
    }
}
