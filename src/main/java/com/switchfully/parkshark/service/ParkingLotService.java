package com.switchfully.parkshark.service;

import com.switchfully.parkshark.domain.parkinglot.NewParkingLotDTO;
import com.switchfully.parkshark.domain.parkinglot.ParkingLot;
import com.switchfully.parkshark.domain.parkinglot.ParkingLotRepository;
import com.switchfully.parkshark.service.parkinglot.ParkingLotMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Transactional
public class ParkingLotService {

    ParkingLotRepository parkingLotRepository;

    ParkingLotMapper parkingLotMapper;

    public ParkingLotService(ParkingLotRepository parkingLotRepository, ParkingLotMapper parkingLotMapper) {
        this.parkingLotRepository = parkingLotRepository;
        this.parkingLotMapper = parkingLotMapper;
    }

    

    public void createParkingLot(NewParkingLotDTO newParkingLotDTO) {
        ParkingLot parkingLot = parkingLotMapper.newParkingLotDTOToParkingLot(newParkingLotDTO);
        parkingLotRepository.save(parkingLot);

    }
}
