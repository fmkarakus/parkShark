package com.switchfully.parkshark.service.member;

import com.switchfully.parkshark.domain.member.Address;
import com.switchfully.parkshark.domain.member.LicensePlate;
import com.switchfully.parkshark.domain.member.Member;
import com.switchfully.parkshark.domain.postalcode.PostalCode;
import com.switchfully.parkshark.service.member.memberDTO.CreateMemberDTO;
import com.switchfully.parkshark.service.member.memberDTO.MemberDTO;
import com.switchfully.parkshark.service.member.memberDTO.SimplifiedMemberDTO;

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
                ),
                createMemberDTO.memberShipLevel()
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
                member.getLicensePlateAsString(),
                member.getMemberShipLevel().name()
        );
    }

    public SimplifiedMemberDTO mapMemberToSimplifiedDTO(Member member) {
        return new SimplifiedMemberDTO(
                member.getId(),
                member.getFirstName() + " " + member.getLastName(),
                member.getAddressAsString(),
                member.getTelephoneNumber(),
                member.getEmail(),
                member.getLicensePlate().getLicensePlateNumber(),
                member.getRegistrationDate()
        );
    }
}
