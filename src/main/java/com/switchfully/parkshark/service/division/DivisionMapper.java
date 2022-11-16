package com.switchfully.parkshark.service.division;

import com.switchfully.parkshark.domain.division.Division;

public class DivisionMapper {

    public Division toDivision(CreateDivisionDTO createDivisionDTO) {
        return new Division(createDivisionDTO.getName(), createDivisionDTO.getOriginalName(), createDivisionDTO.getDirector());
    }


}
