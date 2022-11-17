package com.switchfully.parkshark.service.parkinglot;

import com.switchfully.parkshark.domain.parkinglot.*;
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

    public ReturnParkingLotDTO createParkingLot(NewParkingLotDTO newParkingLotDTO) {
        parkingLotValidation.checkRequiredFields(newParkingLotDTO);
        ParkingLot parkingLot = parkingLotMapper.newParkingLotDTOToParkingLot(newParkingLotDTO);
        parkingLotRepository.save(parkingLot);
        return parkingLotMapper.mapParkingLotToReturnParkingLotDTO(parkingLot);

    }

    public List<ParkingLotSimplifiedDTO> getAllParkingLotsSimplified() {
        return parkingLotRepository.findAll()
                .stream()
                .map(parkingLot -> parkingLotMapper.parkingLotToParkingLotSimplifiedDTO(parkingLot))
                .collect(Collectors.toList());
    }
}
