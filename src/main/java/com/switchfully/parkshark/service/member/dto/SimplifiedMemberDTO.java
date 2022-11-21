package com.switchfully.parkshark.service.member.dto;

import java.time.LocalDate;

public record SimplifiedMemberDTO (
        long id,
        String name,
        String address,
        String telephoneNumber,
        String emailAddress,
        String licensePlateNumber,
        LocalDate registrationDate

) {
}
