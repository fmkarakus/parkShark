package com.switchfully.parkshark.service;

import com.switchfully.parkshark.domain.CreateDivisionDTO;
import com.switchfully.parkshark.domain.Division;

public class DivisionMapper {

    public Division toDivision(CreateDivisionDTO createDivisionDTO) {
        return new Division(createDivisionDTO.getName(), createDivisionDTO.getOriginal_name(), createDivisionDTO.getDirector());
    }


}
