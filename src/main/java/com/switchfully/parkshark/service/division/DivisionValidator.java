package com.switchfully.parkshark.service.division;

import com.switchfully.parkshark.service.division.dto.CreateDivisionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DivisionValidator {


    private final Logger logger = LoggerFactory.getLogger(DivisionValidator.class);

    public void CheckRequiredFields(CreateDivisionDTO createDivisionDTO) {
        if (createDivisionDTO.getName() == null || createDivisionDTO.getName().equals("")) {
            logger.error("No name provided");
            throw new IllegalArgumentException("Division name not provided");
        }
        if (createDivisionDTO.getOriginalName() == null || createDivisionDTO.getOriginalName().equals("")) {
            logger.error("No original name provided");
            throw new IllegalArgumentException("Original name not provided");
        }
        if (createDivisionDTO.getDirector() == null || createDivisionDTO.getDirector().equals("")) {
            logger.error("No director provided");
            throw new IllegalArgumentException("Director not provided");
        }

    }

}
