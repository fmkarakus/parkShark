package com.switchfully.parkshark.domain.postalcode;

import org.springframework.stereotype.Service;

@Service
public class PostalCodeValidator {

    private final PostalCodeRepository postalCodeRepository;

    public PostalCodeValidator(PostalCodeRepository postalCodeRepository) {
        this.postalCodeRepository = postalCodeRepository;
    }

    public void isPostalCodeInDatabase(String postalCode){
        if (!postalCodeRepository.existsById(postalCode)){
            throw new IllegalArgumentException("This Postal Code doesn't exist");
        }
    }
}
