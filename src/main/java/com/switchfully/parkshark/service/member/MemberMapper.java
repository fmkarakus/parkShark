package com.switchfully.parkshark.service.member;

import com.switchfully.parkshark.domain.member.Address;
import com.switchfully.parkshark.domain.member.LicensePlate;
import com.switchfully.parkshark.domain.member.Member;
import com.switchfully.parkshark.domain.member.PostalCode;

public class MemberMapper {
    public Member mapDTOToMember(CreateMemberDTO createMemberDTO) {
        return new Member(
                createMemberDTO.firstName(),
                createMemberDTO.lastName(),
                new Address(
                        createMemberDTO.streetName(),
                        createMemberDTO.streetNumber(),
                        new PostalCode(
                                createMemberDTO.postalCode(),
                                createMemberDTO.label()
                        )
                ),
                createMemberDTO.telephoneNumber(),
                createMemberDTO.emailAddress(),
                new LicensePlate(
                        createMemberDTO.licensePlateNumber(),
                        createMemberDTO.licensePlateCountry()
                )
        );
    }

    public MemberDTO mapMemberToDTO(Member member) {
        return new MemberDTO(
                member.getId(),
                member.getFirstName(),
                member.getLastName(),
                member.getAddressAsString(),
                member.getTelephoneNumber(),
                member.getEmail(),
                member.getLicensePlateAsString()
        );
    }
}
