package com.switchfully.parkshark.service.parkinglot;

import com.switchfully.parkshark.domain.member.Address;
import com.switchfully.parkshark.domain.parkinglot.Category;
import com.switchfully.parkshark.domain.parkinglot.NewParkingLotDTO;
import com.switchfully.parkshark.domain.parkinglot.ParkingLot;
import com.switchfully.parkshark.domain.parkinglot.ParkingLotSimplifiedDTO;
import com.switchfully.parkshark.service.contactperson.ContactPersonService;
import com.switchfully.parkshark.service.division.DivisionService;
import com.switchfully.parkshark.service.postalcode.PostalCodeService;
import org.springframework.stereotype.Component;

@Component
public class ParkingLotMapper {
    private final PostalCodeService postalCodeService;
    private final ContactPersonService contactPersonService;
    private final DivisionService divisionService;

    public ParkingLotMapper(PostalCodeService postalCodeService, ContactPersonService contactPersonService, DivisionService divisionService) {
        this.postalCodeService = postalCodeService;
        this.contactPersonService = contactPersonService;
        this.divisionService = divisionService;
    }


    public ParkingLot newParkingLotDTOToParkingLot(NewParkingLotDTO newParkingLotDTO) {

        Address newAddress = new Address(newParkingLotDTO.getStreetName()
                , newParkingLotDTO.getStreetNumber()
                , postalCodeService.findPostalCodById(newParkingLotDTO.getPostalCode()));

        Category category = Category.findCategoryByName(newParkingLotDTO.getCategory());
        return new ParkingLot(newParkingLotDTO.getName()
                , category
                , newParkingLotDTO.getMaxCapacity()
                , newParkingLotDTO.getPricePerHour()
                , contactPersonService.findContactPersonByID(newParkingLotDTO.getContactPersonId())
                , newAddress
                , divisionService.findDivisionById(newParkingLotDTO.getDivisionId()));
    }

    public ParkingLotSimplifiedDTO parkingLotToParkingLotSimplifiedDTO(ParkingLot parkingLot) {
        return new ParkingLotSimplifiedDTO(parkingLot.getId(), parkingLot.getName(), parkingLot.getMaxCapacity(), parkingLot.getContactPerson().getEmail(),parkingLot.getContactPerson().getTelephoneNumber());
    }
}
