package com.switchfully.parkshark.service.member;

public record MemberDTO(
        long id,
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
