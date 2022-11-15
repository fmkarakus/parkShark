package com.switchfully.parkshark.service;

import com.switchfully.parkshark.domain.CreateDivisionDTO;
import com.switchfully.parkshark.domain.Division;
import com.switchfully.parkshark.domain.DivisionRepository;
import org.springframework.stereotype.Service;


@Service
public class DivisionService {

    DivisionRepository divisionRepository;
    DivisionMapper divisionMapper;
    DivisionValidator divisionValidator;

    public DivisionService(DivisionRepository divisionRepository) {
        this.divisionRepository = divisionRepository;
    }

    public void createDivision(CreateDivisionDTO createDivisionDTO) {
        divisionValidator.CheckRequiredFields(createDivisionDTO);
        divisionRepository.save(divisionMapper.toDivision(createDivisionDTO));
    }
}
