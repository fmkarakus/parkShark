package com.switchfully.parkshark.domain.contactperson;

import com.switchfully.parkshark.domain.member.Address;
import com.switchfully.parkshark.domain.postalcode.PostalCode;

import javax.persistence.*;

@Entity
@Table(name = "CONTACT_PERSON")
public class ContactPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contact_person_seq")
    @SequenceGenerator(name = "contact_person_seq", sequenceName = "contact_person_seq",allocationSize = 1)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL")
    private String email;

    @Column(name= "MOBILE_PHONE_NUMBER")
    private String mobilePhoneNumber;
    @Column(name= "TELEPHONE_NUMBER")
    private String telephoneNumber;

    @Embedded
    private Address address;

    public Address getAddress() {
        return address;
    }

    public ContactPerson() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }


}
