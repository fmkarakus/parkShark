package com.switchfully.parkshark.domain.parkinglot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLot,Long> {
Optional<ParkingLot> findById(Long id);
}
