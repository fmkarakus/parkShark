package com.switchfully.parkshark.service.member;

import com.sun.tools.jconsole.JConsoleContext;
import com.switchfully.parkshark.domain.member.MembershipLevel;
import com.switchfully.parkshark.service.exceptions.EmailNotValidException;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class MemberValidator {

    public void validateMember(CreateMemberDTO createMemberDTO){
        assertFieldNotNullOrEmpty("Firstname", createMemberDTO.firstName());
        assertFieldNotNullOrEmpty("Lastname", createMemberDTO.lastName());
        assertFieldNotNullOrEmpty("Street name", createMemberDTO.streetName());
        assertFieldNotNullOrEmpty("Street number", createMemberDTO.streetNumber());
        assertFieldNotNullOrEmpty("Postal code", createMemberDTO.postalCode());
        assertFieldNotNullOrEmpty("Label", createMemberDTO.label());
        assertFieldNotNullOrEmpty("Telephone number", createMemberDTO.telephoneNumber());
        isValidEmailAddress(createMemberDTO.emailAddress());
        assertFieldNotNullOrEmpty("Password", createMemberDTO.password());
        assertFieldNotNullOrEmpty("License plate number", createMemberDTO.licensePlateNumber());
        assertFieldNotNullOrEmpty("License plate country", createMemberDTO.licensePlateCountry());
        validateMembershipLevel(createMemberDTO.membershipLevel());
    }

    private void assertFieldNotNullOrEmpty(String fieldName, String value) {
        if (value == null || value.trim().length() < 1) {
            throw new IllegalArgumentException(fieldName + " can not be null or empty");
        }
    }

    private void isValidEmailAddress(String email) {
        if (email == null) {
            throw new EmailNotValidException();
        }
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            throw new EmailNotValidException();
        }
    }

    private void validateMembershipLevel(String membershipLevel) {
        if (membershipLevel == null) {
            return;
        }
        for (MembershipLevel level : MembershipLevel.values()) {
            if (level.toString().equals(membershipLevel.toUpperCase())) {
                return;
            }
        }
        throw new IllegalArgumentException("The provided membership level does not exist");
    }
}
