package com.switchfully.parkshark.domain.parkinglot;

public class NewParkingLotDTO {
    private String name;
    private String category;
    private int maxCapacity;
    private double pricePerHour;
    private Long contactPersonId;
    private String streetName;
    private String streetNumber;
    private String postalCode;
    private Long divisionId;

    public NewParkingLotDTO(String name, String category, int maxCapacity, double pricePerHour, Long contactPersonId, String streetName, String streetNumber, String postalCode, Long divisionId) {
        this.name = name;
        this.category = category;
        this.maxCapacity = maxCapacity;
        this.pricePerHour = pricePerHour;
        this.contactPersonId = contactPersonId;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.postalCode = postalCode;
        this.divisionId = divisionId;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public Long getContactPersonId() {
        return contactPersonId;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public Long getDivisionId() {
        return divisionId;
    }
}
