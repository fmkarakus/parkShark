package com.switchfully.parkshark.service.Division;

import com.switchfully.parkshark.domain.Division.CreateDivisionDTO;

public class DivisionValidator {

    public void CheckRequiredFields(CreateDivisionDTO createDivisionDTO) {
        if (createDivisionDTO.getName() == null || createDivisionDTO.getName().equals("")) {
            throw new IllegalArgumentException("Division name not provided");
        }
        if (createDivisionDTO.getOriginalName() == null || createDivisionDTO.getOriginalName().equals("")) {
            throw new IllegalArgumentException("Original name not provided");
        }
        if (createDivisionDTO.getDirector() == null || createDivisionDTO.getDirector().equals("")) {
            throw new IllegalArgumentException("Director not provided");
        }

    }
}
