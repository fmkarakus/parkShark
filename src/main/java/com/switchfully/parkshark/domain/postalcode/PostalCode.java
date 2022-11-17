package com.switchfully.parkshark.domain.postalcode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "POSTAL_CODE")
public class PostalCode {
    @Id
    private String postal_code;
    @Column(name = "LABEL")
    private String label;

    public PostalCode() {
    }

    public PostalCode(String postal_code, String label) {
        this.postal_code = postal_code;
        this.label = label;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return String.format("%s %s", postal_code, label);
    }
}
