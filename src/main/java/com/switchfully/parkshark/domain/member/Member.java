package com.switchfully.parkshark.domain.member;

import com.switchfully.parkshark.security.Role;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(generator = "member_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "member_seq", sequenceName = "member_seq", initialValue = 1, allocationSize = 1)
    private long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Embedded
    private Address address;
    @Column(name = "telephone_number")
    private String telephoneNumber;
    @Column(name = "email")
    private String email;
    @Embedded
    private LicensePlate licensePlate;
    @Column(name = "registration_date")
    private LocalDate registrationDate;
    @Column(name = "role")
    private Role role;

    public Member() {
    }

    public Member(String firstName, String lastName, Address address, String telephoneNumber, String email, LicensePlate licensePlate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.telephoneNumber = telephoneNumber;
        this.email = email;
        this.licensePlate = licensePlate;
        registrationDate = LocalDate.now();
        role = Role.MEMBER;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Address getAddress() {
        return address;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public LicensePlate getLicensePlate() {
        return licensePlate;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public Role getRole() {
        return role;
    }
}
