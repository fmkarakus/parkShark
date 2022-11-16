package com.switchfully.parkshark.domain.member;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class LicensePlate {
    @Column(name = "license_plate_number")
    private String licensePlateNumber;
    @Column(name = "license_plate_country")
    private String licensePlateCountry;

    public LicensePlate() {
    }

    public LicensePlate(String licensePlateNumber, String licensePlateCountry) {
        this.licensePlateNumber = licensePlateNumber;
        this.licensePlateCountry = licensePlateCountry;
    }

    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public String getLicensePlateCountry() {
        return licensePlateCountry;
    }

    @Override
    public String toString() {
        return String.format("%s %s", licensePlateCountry, licensePlateNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LicensePlate that = (LicensePlate) o;
        return Objects.equals(licensePlateNumber, that.licensePlateNumber) && Objects.equals(licensePlateCountry, that.licensePlateCountry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(licensePlateNumber, licensePlateCountry);
    }
}
