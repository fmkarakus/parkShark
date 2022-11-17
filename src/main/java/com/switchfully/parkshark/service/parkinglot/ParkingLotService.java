package com.switchfully.parkshark.service.parkinglot;

import com.switchfully.parkshark.domain.contactperson.ContactPerson;
import com.switchfully.parkshark.domain.contactperson.ContactPersonRepository;
import com.switchfully.parkshark.domain.division.Division;
import com.switchfully.parkshark.domain.division.DivisionRepository;
import com.switchfully.parkshark.domain.parkinglot.NewParkingLotDTO;
import com.switchfully.parkshark.domain.parkinglot.ParkingLot;
import com.switchfully.parkshark.domain.parkinglot.ParkingLotRepository;
import com.switchfully.parkshark.domain.parkinglot.ParkingLotSimplifiedDTO;
import com.switchfully.parkshark.domain.postalcode.PostalCode;
import com.switchfully.parkshark.domain.postalcode.PostalCodeRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class ParkingLotService {
    private final PostalCodeRepository postalCodeRepository;
    private final ContactPersonRepository contactPersonRepository;
    private final DivisionRepository divisionRepository;
    private final ParkingLotRepository parkingLotRepository;
    private final ParkingLotValidation parkingLotValidation;
    private final ParkingLotMapper parkingLotMapper;

    public ParkingLotService(PostalCodeRepository postalCodeRepository, ContactPersonRepository contactPersonRepository, DivisionRepository divisionRepository, ParkingLotRepository parkingLotRepository, ParkingLotValidation parkingLotValidation, ParkingLotMapper parkingLotMapper) {
        this.postalCodeRepository = postalCodeRepository;
        this.contactPersonRepository = contactPersonRepository;
        this.divisionRepository = divisionRepository;
        this.parkingLotRepository = parkingLotRepository;
        this.parkingLotValidation = parkingLotValidation;
        this.parkingLotMapper = parkingLotMapper;
    }

    public void createParkingLot(NewParkingLotDTO newParkingLotDTO) {
        parkingLotValidation.checkRequiredFields(newParkingLotDTO);
        ParkingLot parkingLot = parkingLotMapper.newParkingLotDTOToParkingLot(newParkingLotDTO);
        parkingLotRepository.save(parkingLot);

    }

    public List<ParkingLotSimplifiedDTO> getAllParkingLotsSimplified() {
        return parkingLotRepository.findAll()
                .stream()
                .map(parkingLot -> parkingLotMapper.parkingLotToParkingLotSimplifiedDTO(parkingLot))
                .collect(Collectors.toList());
    }
}
