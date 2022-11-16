package com.switchfully.parkshark.domain.member;

import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class Address {
    @Column(name = "street_name")
    private String streetName;
    @Column(name = "street_number")
    private String streetNumber;
    @ManyToOne
    @JoinColumn(name = "postal_code")
    private PostalCode postalCode;

    public Address() {
    }

    public Address(String streetName, String streetNumber, PostalCode postalCode) {
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.postalCode = postalCode;
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
