package com.switchfully.parkshark.domain.allocation;

import com.switchfully.parkshark.domain.member.Member;
import com.switchfully.parkshark.domain.parkinglot.ParkingLot;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "allocation")
public class Allocation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parking_lot_seq")
    @SequenceGenerator(name = "parking_lot_seq", sequenceName = "parking_lot_seq", allocationSize = 1)
    private long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name="license_plate_number")
    private String licensePlateNumber;

    @ManyToOne
    @JoinColumn(name = "parking_lot_id")
    private ParkingLot parkingLot;

    @Column(name="starting_time")
    private LocalDateTime startingTime;

    @Column(name="status")
    @Enumerated(value =EnumType.STRING)
    private AllocationStatus status;

    public Allocation(Member member, String licensePlateNumber, ParkingLot parkingLot) {
        this.member = member;
        this.licensePlateNumber = licensePlateNumber;
        this.parkingLot = parkingLot;
        this.startingTime = LocalDateTime.now();
        this.status=AllocationStatus.ACTIVE;
    }

    public Allocation() {

    }

    public long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public LocalDateTime getStartingTime() {
        return startingTime;
    }

    public AllocationStatus getStatus() {
        return status;
    }
}
