package com.switchfully.parkshark.domain.parkinglot;


import javax.persistence.*;

public class NewParkingLotDTO {


    private String name;


    private Category category;


    private int maxCapacity;


    private double pricePerHour;


    private ContactPerson contactPerson;


    private String streetName;


    private String streetNumber;


    private PostalCode postalCode;

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

    public ContactPerson getContactPerson() {
        return contactPerson;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public PostalCode getPostalCode() {
        return postalCode;
    }
}
