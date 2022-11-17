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
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Column(name = "membership_level") //TODO: add column membership_level to member table in V3
    @Enumerated(value =EnumType.STRING)
    private MembershipLevel memberShipLevel;

    public Member() {
    }

    public Member(String firstName, String lastName, Address address, String telephoneNumber, String email, LicensePlate licensePlate, String membershipLevel) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.telephoneNumber = telephoneNumber;
        this.email = email;
        this.licensePlate = licensePlate;
        registrationDate = LocalDate.now();
        role = Role.MEMBER;
        this.memberShipLevel = setMembershipLevel(membershipLevel);
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

    public String getAddressAsString() {
        return String.format("%s %s, %s", address.getStreetName(), address.getStreetNumber(), address.getPostalCode());
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

    public String getLicensePlateAsString() {
        return licensePlate.toString();
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public Role getRole() {
        return role;
    }
    
    private MembershipLevel setMembershipLevel(String membershipLevel) {
        if (membershipLevel == null) {
            return MembershipLevel.BRONZE;
        }
        return MembershipLevel.findMembershipLevelByName(membershipLevel);
    }
}
