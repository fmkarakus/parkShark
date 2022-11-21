package com.switchfully.parkshark.service.division;

import com.switchfully.parkshark.domain.division.Division;
import com.switchfully.parkshark.service.division.dto.CreateDivisionDTO;
import com.switchfully.parkshark.service.division.dto.DivisionDTO;
import com.switchfully.parkshark.service.division.dto.SubdivisionDTO;

public class DivisionMapper {

    public Division toDivision(CreateDivisionDTO createDivisionDTO) {
        return new Division(createDivisionDTO.getName(), createDivisionDTO.getOriginalName(), createDivisionDTO.getDirector());
    }

    public DivisionDTO toDivisionDTO(Division division) {
        return new DivisionDTO(division.getId(), division.getName(), division.getOriginalName(), division.getDirector());
    }

    public SubdivisionDTO toSubdivisionDTO(Division division) {
        return new SubdivisionDTO(division.getId(), division.getName(), division.getOriginalName(), division.getDirector(),division.getParentDivision().getId());
    }
}
