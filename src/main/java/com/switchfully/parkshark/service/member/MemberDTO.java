package com.switchfully.parkshark.service.member;

public record MemberDTO(
        long id,
        String firstName,
        String lastName,
        String address,
        String telephoneNumber,
        String emailAddress,
        String licensePlate
) {

}
