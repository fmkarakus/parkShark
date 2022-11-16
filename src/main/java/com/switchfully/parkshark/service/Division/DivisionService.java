package com.switchfully.parkshark.service.Division;

import com.switchfully.parkshark.domain.Division.CreateDivisionDTO;
import com.switchfully.parkshark.domain.Division.DivisionRepository;
import org.springframework.stereotype.Service;


@Service
public class DivisionService {

    DivisionRepository divisionRepository;
    DivisionMapper divisionMapper = new DivisionMapper();
    DivisionValidator divisionValidator = new DivisionValidator();

    public DivisionService(DivisionRepository divisionRepository) {
        this.divisionRepository = divisionRepository;
    }

    public void createDivision(CreateDivisionDTO createDivisionDTO) {
        divisionValidator.CheckRequiredFields(createDivisionDTO);
        if(divisionRepository.existsDivisionByName(createDivisionDTO.getName())) {
            throw new IllegalArgumentException("This division name is already in use");
        }
        divisionRepository.save(divisionMapper.toDivision(createDivisionDTO));
    }
}
