package com.switchfully.parkshark.service.Division;

import com.switchfully.parkshark.domain.Division.CreateDivisionDTO;
import com.switchfully.parkshark.domain.Division.Division;

public class DivisionMapper {

    public Division toDivision(CreateDivisionDTO createDivisionDTO) {
        return new Division(createDivisionDTO.getName(), createDivisionDTO.getOriginalName(), createDivisionDTO.getDirector());
    }


}
