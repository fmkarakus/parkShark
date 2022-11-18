package com.switchfully.parkshark.api;

import com.switchfully.parkshark.domain.parkinglot.NewParkingLotDTO;

import com.switchfully.parkshark.domain.parkinglot.ParkingLotSimplifiedDTO;
import com.switchfully.parkshark.domain.parkinglot.ReturnParkingLotDTO;
import com.switchfully.parkshark.service.parkinglot.ParkingLotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ReturnParkingLotDTO createParkingLot(@RequestBody NewParkingLotDTO newParkingLotDTO) {
        return parkingLotService.createParkingLot(newParkingLotDTO);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('VIEW_PARKINGLOTS')")
    public List<ParkingLotSimplifiedDTO> returnAllParkingLots() {
        return parkingLotService.getAllParkingLotsSimplified();
    }

    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('VIEW_PARKINGLOTS')")
    public ReturnParkingLotDTO getParkingLotById(@PathVariable Long id) {
        return parkingLotService.getParkingLotById(id);
    }
}
