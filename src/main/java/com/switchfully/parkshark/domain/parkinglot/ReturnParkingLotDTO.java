package com.switchfully.parkshark.domain.parkinglot;

import com.switchfully.parkshark.domain.division.Division;
import com.switchfully.parkshark.domain.member.Address;

public class ReturnParkingLotDTO {
    private long id;
    private String name;
    private Category category;
    private int maxCapacity;
    private int availableCapacity;
    private double pricePerHour;
    private Long contactPersonId;
    private Address address;
    private Division division;

    public ReturnParkingLotDTO(long id, String name, Category category, int maxCapacity, int availableCapacity, double pricePerHour, Long contactPersonId, Address address,Division division) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.maxCapacity = maxCapacity;
        this.availableCapacity = availableCapacity;
        this.pricePerHour = pricePerHour;
        this.contactPersonId = contactPersonId;
        this.address = address;
        this.division=division;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public int getAvailableCapacity() {
        return availableCapacity;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public Long getContactPersonId() {
        return contactPersonId;
    }

    public Address getAddress() {
        return address;
    }

    public Division getDivision() {
        return division;
    }
}
