package com.switchfully.parkshark.api;

import com.switchfully.parkshark.domain.parkinglot.NewParkingLotDTO;

import com.switchfully.parkshark.service.parkinglot.ParkingLotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("parkinglots")
public class ParkingLotController {


    ParkingLotService parkingLotService;


    public ParkingLotController(ParkingLotService parkingLotService) {
        this.parkingLotService = parkingLotService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
   @PreAuthorize("hasAuthority('CREATE_PARKING_LOT')")
    public void createParkingLot(@RequestBody NewParkingLotDTO newParkingLotDTO){

        parkingLotService.createParkingLot(newParkingLotDTO);

    }
}
