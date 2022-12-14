package com.switchfully.parkshark.service.member.dto;

import java.time.LocalDate;

public record MemberDTO(
        long id,
        String firstName,
        String lastName,
        String address,
        String telephoneNumber,
        String emailAddress,
        String licensePlate,
        String membershipLevel,

        LocalDate registrationDate
) {

}
