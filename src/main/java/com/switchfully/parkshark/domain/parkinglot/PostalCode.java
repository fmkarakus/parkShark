package com.switchfully.parkshark.domain.parkinglot;

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
}
