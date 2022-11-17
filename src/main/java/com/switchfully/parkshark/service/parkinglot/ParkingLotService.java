package com.switchfully.parkshark.service.parkinglot;

import com.switchfully.parkshark.domain.parkinglot.NewParkingLotDTO;
import com.switchfully.parkshark.domain.parkinglot.ParkingLot;
import com.switchfully.parkshark.domain.parkinglot.ParkingLotRepository;
import com.switchfully.parkshark.domain.parkinglot.ParkingLotSimplifiedDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class ParkingLotService {

    ParkingLotRepository parkingLotRepository;
    ParkingLotValidation parkingLotValidation;
    ParkingLotMapper parkingLotMapper;

    public ParkingLotService(ParkingLotRepository parkingLotRepository, ParkingLotValidation parkingLotValidation, ParkingLotMapper parkingLotMapper) {
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

    public ParkingLot findParkingLotId(Long parkingLotId) {
        return parkingLotRepository.findById(parkingLotId).orElseThrow(()->new IllegalArgumentException(String.format("Parking lot with the id %d does not exist.",parkingLotId)));
    }
}
