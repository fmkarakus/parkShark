package com.switchfully.parkshark.service;

import com.switchfully.parkshark.domain.CreateDivisionDTO;

public class DivisionValidator {

    public void CheckRequiredFields(CreateDivisionDTO createDivisionDTO) {
        if (createDivisionDTO.getName() == null || createDivisionDTO.getName().equals("")) {
            throw new IllegalArgumentException("Division name not provided");
        }
        if (createDivisionDTO.getOriginal_name() == null || createDivisionDTO.getOriginal_name().equals("")) {
            throw new IllegalArgumentException("Original name not provided");
        }
        if (createDivisionDTO.getDirector() == null || createDivisionDTO.getDirector().equals("")) {
            throw new IllegalArgumentException("Director not provided");
        }

    }
}
