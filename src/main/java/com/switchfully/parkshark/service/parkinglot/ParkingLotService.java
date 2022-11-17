package com.switchfully.parkshark.service.parkinglot;

import com.switchfully.parkshark.domain.parkinglot.*;
import com.switchfully.parkshark.service.division.DTO.DivisionDTO;
import com.switchfully.parkshark.service.division.DivisionMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class ParkingLotService {

    private final ParkingLotRepository parkingLotRepository;
    private final ParkingLotValidation parkingLotValidation;
    private final ParkingLotMapper parkingLotMapper;
    private final DivisionMapper divisionMapper;

    public ParkingLotService(ParkingLotRepository parkingLotRepository, ParkingLotValidation parkingLotValidation, ParkingLotMapper parkingLotMapper) {
        this.parkingLotRepository = parkingLotRepository;
        this.parkingLotValidation = parkingLotValidation;
        this.parkingLotMapper = parkingLotMapper;
        divisionMapper = new DivisionMapper();
    }

    public ReturnParkingLotDTO createParkingLot(NewParkingLotDTO newParkingLotDTO) {
        parkingLotValidation.checkRequiredFields(newParkingLotDTO);
        ParkingLot parkingLot = parkingLotMapper.newParkingLotDTOToParkingLot(newParkingLotDTO);
        parkingLotRepository.save(parkingLot);
        DivisionDTO divisionDTO = divisionMapper.toDivisionDTO(parkingLot.getDivision());
        return parkingLotMapper.mapParkingLotToReturnParkingLotDTO(parkingLot, divisionDTO);

    }

    public List<ParkingLotSimplifiedDTO> getAllParkingLotsSimplified() {
        return parkingLotRepository.findAll()
                .stream()
                .map(parkingLot -> parkingLotMapper.parkingLotToParkingLotSimplifiedDTO(parkingLot))
                .collect(Collectors.toList());
    }

    public ReturnParkingLotDTO getParkingLotById(Long id) {
        ParkingLot parkingLot = parkingLotRepository.findParkingLotById(id);
        DivisionDTO divisionDTO = divisionMapper.toDivisionDTO(parkingLot.getDivision());
        return parkingLotMapper.mapParkingLotToReturnParkingLotDTO(parkingLot, divisionDTO);
    }
}
