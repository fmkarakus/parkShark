package com.switchfully.parkshark.domain.parkinglot;




public class NewParkingLotDTO {


    private String name;


    private Category category;


    private int maxCapacity;


    private double pricePerHour;


    private Long contactPersonId;


    private String streetName;


    private String streetNumber;


    private String postalCode;

    public NewParkingLotDTO(String name, Category category, int maxCapacity, double pricePerHour, Long contactPersonId, String streetName, String streetNumber, String postalCode) {
        this.name = name;
        this.category = category;
        this.maxCapacity = maxCapacity;
        this.pricePerHour = pricePerHour;
        this.contactPersonId = contactPersonId;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.postalCode = postalCode;
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
}
