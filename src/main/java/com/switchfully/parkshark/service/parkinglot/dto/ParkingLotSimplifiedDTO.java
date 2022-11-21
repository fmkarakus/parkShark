package com.switchfully.parkshark.service.parkinglot.dto;

public class ParkingLotSimplifiedDTO {
    private Long id;
    private String name;
    private int capacity;
    private String contactPersonEmail;
    private String contactPersonTelephone;

    public ParkingLotSimplifiedDTO(Long id, String name, int capacity, String contactPersonEmail, String contactPersonTelephone) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.contactPersonEmail = contactPersonEmail;
        this.contactPersonTelephone = contactPersonTelephone;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getContactPersonEmail() {
        return contactPersonEmail;
    }

    public String getContactPersonTelephone() {
        return contactPersonTelephone;
    }
}
