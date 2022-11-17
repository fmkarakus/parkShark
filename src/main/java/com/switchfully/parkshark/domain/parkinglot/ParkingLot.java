package com.switchfully.parkshark.domain.parkinglot;

import com.switchfully.parkshark.domain.contactperson.ContactPerson;
import com.switchfully.parkshark.domain.division.Division;
import com.switchfully.parkshark.domain.member.Address;

import javax.persistence.*;

@Entity
@Table(name = "PARKING_LOT")
public class ParkingLot {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parking_lot_seq")
    @SequenceGenerator(name = "parking_lot_seq", sequenceName = "parking_lot_seq",allocationSize = 1)
    private long id;

    @Column(name = "NAME")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "CATEGORY")
    private Category category;

    @Column(name = "MAX_CAPACITY")
    private int maxCapacity;

    @Column(name = "PRICE_PER_HOUR")
    private double pricePerHour;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CONTACT_PERSON_ID")
    private ContactPerson contactPerson;

    @Embedded
    private Address address;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "DIVISION_ID")
    private Division division;

    public ParkingLot() {
    }

    public ParkingLot(String name, Category category,
                      int maxCapacity, double pricePerHour, ContactPerson contactPerson,
                      Address address,Division division) {

        this.name = name;
        this.category = category;
        this.maxCapacity = maxCapacity;
        this.pricePerHour = pricePerHour;
        this.contactPerson = contactPerson;
        this.address = address;
        this.division= division;
    }

}
