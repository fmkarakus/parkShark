package com.switchfully.parkshark.domain.postalcode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "POSTAL_CODE")
public class PostalCode {

    @Id
    private String postalCode;

    @Column(name = "LABEL")
    private String label;

    public PostalCode(String postalCode, String label) {
        this.postalCode = postalCode;
        this.label = label;
    }

    public PostalCode() {

    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getLabel() {
        return label;
    }
}
