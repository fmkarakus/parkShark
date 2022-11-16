package com.switchfully.parkshark.domain.parkinglot;

import javax.persistence.*;

@Entity
@Table(name = "CONTACT_PERSON")
public class ContactPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parkinglot_seq")
    @SequenceGenerator(name = "parkinglot_seq", sequenceName = "parkinglot_seq",allocationSize = 1)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL")
    private String email;

    @Column(name= "MOBILE_PHONE_NUMBER")
    private String mobilePhoneNumber;
    @Column(name= "TELEPHONE_NUMBER")
    private String telephoneNumber;
    @Column(name= "STREET_NAME")
    private String streetName;
    @Column(name= "STREET_NUMBER")
    private String streetNumber;

    @OneToOne
    @JoinColumn(name = "CONTACT_PERSON_ID")
    private PostalCode postalCode;

    public ContactPerson() {
    }
}
