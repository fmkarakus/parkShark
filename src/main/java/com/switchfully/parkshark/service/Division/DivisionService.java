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
        divisionRepository.save(divisionMapper.toDivision(createDivisionDTO));
    }
}
