package com.switchfully.parkshark.service.member;

public record CreateMemberDTO (
    String firstName,
    String lastName,
    String streetName,
    String streetNumber,
    String postalCode,
    String label,
    String telephoneNumber,
    String emailAddress,
    String password,
    String licensePlateNumber,
    String licensePlateCountry
) {

}