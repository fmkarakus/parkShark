package com.switchfully.parkshark.service.member;

public record CreateMemberDTO (
    String firstName,
    String lastName,
    String streetName,
    String streetNumber,
    String postalCode,
    String telephoneNumber,
    String emailAddress,
    String licensePlateNumber,
    String licensePlateCountry
) {

}
