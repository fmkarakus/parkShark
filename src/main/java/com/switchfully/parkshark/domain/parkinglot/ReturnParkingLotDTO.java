package com.switchfully.parkshark.domain.parkinglot;

public class ReturnParkingLotDTO {
    private long id;
    private String name;
    private String category;
    private int maxCapacity;
    private int availableCapacity;
    private double pricePerHour;
    private Long contactPersonId;
    private String address;
    private String division;

    public ReturnParkingLotDTO(long id, String name, String category, int maxCapacity, int availableCapacity, double pricePerHour, Long contactPersonId, String address, String division) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.maxCapacity = maxCapacity;
        this.availableCapacity = availableCapacity;
        this.pricePerHour = pricePerHour;
        this.contactPersonId = contactPersonId;
        this.address = address;
        this.division = division;
    }

    public long getId() {
        return id;
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

    public int getAvailableCapacity() {
        return availableCapacity;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public Long getContactPersonId() {
        return contactPersonId;
    }

    public String getAddress() {
        return address;
    }

    public String getDivision() {
        return division;
    }
}
