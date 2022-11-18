package com.switchfully.parkshark.service.postalcode;

import com.switchfully.parkshark.domain.postalcode.PostalCode;
import com.switchfully.parkshark.domain.postalcode.PostalCodeRepository;
import org.springframework.stereotype.Service;

@Service
public class PostalCodeService {
    private final PostalCodeRepository postalCodeRepository;

    public PostalCodeService(PostalCodeRepository postalCodeRepository) {
        this.postalCodeRepository = postalCodeRepository;
    }

    public PostalCode findPostalCodById(String postalCode) {
       return postalCodeRepository.findById(postalCode).orElseThrow(()-> new IllegalArgumentException("This postal coda does not exist"));
    }
}
